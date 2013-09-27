package de.uni_mannheim.informatik.swt.bibTexConverter.src.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.SystemTray;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.Citation;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.Converter;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.settings.Settings;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	@SuppressWarnings("rawtypes")
	public final static JComboBox cbbCitationFormat = new JComboBox();
	public static boolean clearInput;
	public final static JPanel contentPane = new JPanel();
	private static JButton btnConvert;

	private static JButton btnCopyToClipboard;
	// BUTTONS
	private static JButton btnEdit = new JButton(Converter.translate("Edit"));
	private static JButton btnRemove = new JButton(Converter.translate("Remove"));
	private static JButton btnReset = new JButton("Reset");
	private static JCheckBoxMenuItem chckbxmntmAutomaticClear = new JCheckBoxMenuItem(Converter.translate("Clear input after conversion"));
	private static JTextArea inputPane = new JTextArea();
	// PANES, TEXTAREAS, LISTS
	static JList<Citation> list = new JList<Citation>();
	private static JScrollPane listScrollPane;
	private static TextField statusPane = new TextField();

	protected static final int COPY = 1;
	protected static final int EDIT = 0;
	protected static final int NEW = 2;
	final static JPanel conversionPanel = new JPanel();
	static Image image = Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("BTC_logo.png"));
	final static JPanel inputPanel = new JPanel();
	// PANELS
	final static JPanel optionsPanel = new JPanel();

	/**
	 * reads the content of the input area
	 */
	public static String getInput() {
		return inputPane.getText();
	}

	// OTHER

	/**
	 * returns the actual collection of citations
	 * 
	 * @return a list of citations
	 */
	public static List<Citation> getOutput() {

		final LinkedList<Citation> result = new LinkedList<Citation>();

		for (int i = 0; i < list.getModel().getSize(); i++) {
			result.add(list.getModel().getElementAt(i));
		}

		return result;
	}

	/**
	 * resets the GUI
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void reset() {
		inputPane.setText(null);
		inputPane.setLineWrap(true);
		setList(new JList());
		list.setCellRenderer(new DefaultListCellRenderer() { // Setting the DefaultListCellRenderer

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				final JTextArea j = new JTextArea();
				j.setText((String) value);
				return this;
			}
		});
		listScrollPane.setViewportView(list);
		list.setEnabled(false);

		inputPane.validate();

		statusPane.setText(Converter.translate(Settings.WELCOME_MESSAGE));
		statusPane.validate();
	}

	/**
	 * sets the cursor to WaitCursor, if busy=true, normal cursor otherwise
	 * 
	 * @param busy
	 */
	public static void setCursor(Boolean busy) {
		final Window[] wins = Window.getWindows();
		inputPane.setEnabled(!busy);

		final Component[] compConv = GUI.conversionPanel.getComponents();
		final Component[] compOpt = GUI.optionsPanel.getComponents();
		final Component[] compInp = GUI.inputPanel.getComponents();
		final Component[] compCP = GUI.contentPane.getComponents();

		btnCopyToClipboard.setEnabled(!busy);
		btnReset.setEnabled(!busy);
		btnEdit.setEnabled(!busy);
		btnRemove.setEnabled(!busy);

		Cursor curs = new Cursor(Cursor.DEFAULT_CURSOR);

		if (busy) {
			curs = new Cursor(Cursor.WAIT_CURSOR);

		} else {
			curs = new Cursor(Cursor.DEFAULT_CURSOR);
		}

		for (final Window w : wins)

		{
			w.setCursor(curs);
		}
		for (final Component c : compConv) {
			c.setEnabled(!busy);
		}
		for (final Component c : compOpt) {
			c.setEnabled(!busy);
		}
		for (final Component c : compInp) {
			c.setEnabled(!busy);
		}
		for (final Component c : compCP) {
			c.setEnabled(!busy);
		}
	}

	/**
	 * updates the inputPane-area, if references were imported from a file
	 * 
	 * @param bibTexStrng
	 *            a string containing the raw bibtext references
	 */
	public static void setInput(String bibTexString) {
		if (clearInput) {
			final CaretListener[] clis = inputPane.getCaretListeners();
			final CaretListener cli = clis[0];
			inputPane = new JTextArea();
			inputPane.setText(bibTexString);
			inputPane.addCaretListener(cli);
		} else

		{
			inputPane.setText(bibTexString);
		}
		inputPane.validate();

	}

	/**
	 * sets the text of the status pane
	 * 
	 * @param status
	 */
	public static void setStatus(String status) {
		statusPane.setText(status);
	}

	/**
	 * updates the outputPane-editorpane to show the converted references
	 * 
	 * @param s
	 *            contains the converted references
	 */
	@SuppressWarnings("unchecked")
	public static void updateOutput() {

		final List<Citation> cL = new LinkedList<Citation>(Converter.getCitationList());

		final DefaultListModel<Citation> lM = new DefaultListModel<Citation>();
		for (final Citation c : cL) {
			lM.addElement(c);
		}
		setList(new JList<Citation>(lM));

		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				list.setSelectedIndex(list.locationToIndex(e.getPoint()));

				if (e.isPopupTrigger()) {

					final JPopupMenu popMenu = popUpMenu();
					popMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (e.isPopupTrigger()) {
					list.locationToIndex(e.getPoint());

					final JPopupMenu popMenu = popUpMenu();
					popMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

		});

		@SuppressWarnings("rawtypes")
		class MyListCellRenderer extends JEditorPane implements ListCellRenderer {
			@Override
			public Component getListCellRendererComponent(JList table, Object value, int index, boolean isSelected, boolean hasFocus) {

				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				} else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}

				setText(value.toString());
				setContentType("text/html");
				return this;
			}

		}

		listScrollPane.setViewportView(list);
		listScrollPane.validate();

		final MyListCellRenderer m = new MyListCellRenderer();
		list.setSelectionBackground(Color.orange);
		list.setCellRenderer(m);

		list.setVisible(true);

		list.validate();
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnEdit.setEnabled(true);
				btnRemove.setEnabled(true);
				if ((arg0.getClickCount() >= 2) & (list.getSelectedValue() != null)) {
					createEditGui();
				}
			}
		});

		if (Settings.automaticallyCopyToClipboard) {

			setClipboardContents();
		}

		Converter.setStatus(Converter.translate("done."));

	}

	private static void createEditGui() {
		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		final EditGui e = new EditGui(list.getSelectedValue());
		e.setBounds(((dim.width - e.getSize().width) / 2), ((dim.height - e.getSize().height) / 2), e.getSize().width, e.getSize().height);
		e.setVisible(true);

	}

	private static String getOutputString() {
		final String output = Converter.getFormattedStrings();
		final JEditorPane p = new JEditorPane();
		p.setVisible(false);

		p.setContentType("text/html");
		p.setText(output);
		return p.getText();
	}

	private static JPopupMenu popUpMenu() {
		final JPopupMenu popMenu = new JPopupMenu();
		final JMenuItem copyClipboard = new JMenuItem(Converter.translate("Copy to clipboard"));
		copyClipboard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JEditorPane copy = new JEditorPane();
				copy.setContentType("text/html");
				copy.setText(list.getSelectedValue().toString());
				copy.selectAll();
				copy.copy();

			}
		});

		final JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Converter.reset();

			}
		});

		final JMenu export = new JMenu(Converter.translate("Export selected entry..."));
		final JMenuItem export2word = new JMenuItem("Word");
		export2word.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Converter.export2Word(list.getSelectedValue().toString());

			}

		});
		export.add(export2word);

		final JMenuItem export2odt = new JMenuItem("Open Office");
		export2odt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Converter.export2ODT(getOutputString());

			}
		});
		export.add(export2odt);

		final JMenuItem edit = new JMenuItem(Converter.translate("Edit"));
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createEditGui();

			}
		});

		final JMenuItem remove = new JMenuItem(Converter.translate("Remove"));
		remove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (list.getSelectedValue() != null) {
					final int index = list.getSelectedIndex();
					Converter.removeFromCitationList(list.getSelectedValue());
					list.setSelectedIndex(index); // TODO

				}
			}

		});

		final JMenuItem customize = new JMenuItem(Converter.translate("Edit output format"));
		customize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				createCustomizeGui(EDIT);

			}
		});

		popMenu.add(edit);
		popMenu.add(remove);
		popMenu.add(new JSeparator());
		popMenu.add(copyClipboard);
		popMenu.add(export);
		popMenu.add(new JSeparator());
		popMenu.add(customize);
		popMenu.add(new JSeparator());
		popMenu.add(reset);

		return popMenu;
	}

	private static void setClipboardContents() {

		final String output = Converter.getFormattedStrings();
		final JEditorPane p = new JEditorPane();
		p.setVisible(false);

		p.setContentType("text/html");
		p.setText(output);
		p.selectAll();
		p.copy();

	}

	private static void setList(JList<Citation> list) {
		GUI.list = list;
	}

	protected static void createCustomizeGui(int mode) {
		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		final int index = cbbCitationFormat.getSelectedIndex();
		CustomizeGui cg = null;
		if (mode == EDIT) {
			cg = new CustomizeGui(mode, Settings.patternFormats.get(index), Settings.patternSettings.get(index), Settings.patternPrePost.get(index), Settings.patternNames.get(index));
		} else if (mode == NEW) {
			final String[][] EMPTY_PrePost = new String[28][2];

			for (int s = 0; s < EMPTY_PrePost.length; s++) {
				EMPTY_PrePost[s] = new String[] { "", "" };
			}

			final String[] EMPTY_Formats = new String[14];
			for (int s = 0; s < EMPTY_Formats.length; s++) {
				EMPTY_Formats[s] = new String("");
			}

			final String[] EMPTY_Settings = new String[] { "{first} {last}", " and ", "true", "true", "et al.", "3", "3"

			};

			cg = new CustomizeGui(mode, EMPTY_Formats, EMPTY_Settings, EMPTY_PrePost, new String[] { "", "" });

		}

		else if (mode == COPY) {
			cg = new CustomizeGui(mode, Settings.patternFormats.get(index), Settings.patternSettings.get(index), Settings.patternPrePost.get(index), new String[] { "", "" });
		}
		cg.setTitle("BibTexConverter - Format Editor");

		if (cg != null) {
			cg.setBounds(((dim.width - cg.getSize().width) / 2), ((dim.height - cg.getSize().height) / 2), cg.getSize().width, cg.getSize().height);
			cg.setVisible(true);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void updateCbbFormats() {
		cbbCitationFormat.setModel(new DefaultComboBoxModel(Settings.getFormatNames()));
		Settings.setFormat(0);
		getOutput();

	}

	private final JButton btnAdd = new JButton(Converter.translate("Add"));

	private final JCheckBox chckbxAutomaticallyCopyTo = new JCheckBox(Converter.translate("automatically"));
	private final JCheckBox chckbxDuplicates = new JCheckBox("unify");

	private final JCheckBox chckbxSortResult = new JCheckBox(Converter.translate(Converter.translate("sort")));

	// MENU
	private final JMenu fileMenu;

	private final Box horizontalBox = Box.createHorizontalBox();

	private final Component horizontalGlue = Box.createHorizontalGlue();

	private final Component horizontalGlue_1 = Box.createHorizontalGlue();

	private final JMenu languageMenu;

	private final JMenuBar menuBar = new JMenuBar();

	private final JMenuItem menuItem;

	private final JMenu mnExport = new JMenu("Export");

	private final JMenu mnImport = new JMenu("Import");

	private final JMenuItem mntmAbout = new JMenuItem(Converter.translate("About"));

	private final JMenuItem mntmHtml = new JMenuItem("HTML");

	private final JMenuItem mntmOpenOffice = new JMenuItem("Open Office .odt");

	private final JMenuItem mntmRtf = new JMenuItem("RTF");

	private final JMenuItem mntmWord = new JMenuItem("Microsoft Word .doc");

	private final JRadioButtonMenuItem rbMenuDeutsch = new JRadioButtonMenuItem("Deutsch");

	private final JRadioButtonMenuItem rbMenuEnglish = new JRadioButtonMenuItem("English");
	Timer autoCollection = new Timer(500, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent a) {
			final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			final Transferable content = clipboard.getContents(null);

			if (content.isDataFlavorSupported(DataFlavor.stringFlavor)) {

				try {
					clipboardString = (String) content.getTransferData(DataFlavor.stringFlavor);

				} catch (final Exception e) {

				}

				final Pattern p = Pattern.compile("(@[\\w]+)");
				final Matcher m = p.matcher(clipboardString);

				if (m.find()) if (m.group(1).toLowerCase().startsWith("@article") | m.group(1).toLowerCase().startsWith("@book") | m.group(1).toLowerCase().startsWith("@booklet") | m.group(1)
						.toLowerCase().startsWith("@conference") | m.group(1).toLowerCase().startsWith("@inbook") | m.group(1).toLowerCase().startsWith("@incollection") | m.group(1).toLowerCase()
						.startsWith("@inproceedings") | m.group(1).toLowerCase().startsWith("@manual") | m.group(1).toLowerCase().startsWith("@mastersthesis") | m.group(1).toLowerCase().startsWith(
						"@misc") | m.group(1).toLowerCase().startsWith("@phdthesis") | m.group(1).toLowerCase().startsWith("@proceedings") | m.group(1).toLowerCase().startsWith("@techreport") | m
						.group(1).toLowerCase().startsWith("@unpublished")) {

					clipboard.setContents(new Transferable() {

						@Override
						public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException {

							return null;
						}

						@Override
						public DataFlavor[] getTransferDataFlavors() {

							return null;
						}

						@Override
						public boolean isDataFlavorSupported(DataFlavor arg0) {

							return false;
						}
					}, null);

					automaticModeAddCitation(clipboardString);

				}
			}
			System.out.print(".");

		}
	});

	Timer autoConversion = new Timer(500, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent a) {
			final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			final Transferable content = clipboard.getContents(null);

			if (content.isDataFlavorSupported(DataFlavor.stringFlavor)) {

				try {
					clipboardString = (String) content.getTransferData(DataFlavor.stringFlavor);

				} catch (final Exception e) {

				}

				final Pattern p = Pattern.compile("(@[\\w]+)");
				final Matcher m = p.matcher(clipboardString);

				if (m.find()) if (m.group(1).toLowerCase().startsWith("@article") | m.group(1).toLowerCase().startsWith("@book") | m.group(1).toLowerCase().startsWith("@booklet") | m.group(1)
						.toLowerCase().startsWith("@conference") | m.group(1).toLowerCase().startsWith("@inbook") | m.group(1).toLowerCase().startsWith("@incollection") | m.group(1).toLowerCase()
						.startsWith("@inproceedings") | m.group(1).toLowerCase().startsWith("@manual") | m.group(1).toLowerCase().startsWith("@mastersthesis") | m.group(1).toLowerCase().startsWith(
						"@misc") | m.group(1).toLowerCase().startsWith("@phdthesis") | m.group(1).toLowerCase().startsWith("@proceedings") | m.group(1).toLowerCase().startsWith("@techreport") | m
						.group(1).toLowerCase().startsWith("@unpublished")) {

					clipboard.setContents(new Transferable() {

						@Override
						public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException {

							return null;
						}

						@Override
						public DataFlavor[] getTransferDataFlavors() {

							return null;
						}

						@Override
						public boolean isDataFlavorSupported(DataFlavor arg0) {

							return false;
						}
					}, null);

					final Thread t = new Thread() {
						@Override
						public void run() {

							Converter.convert(clipboardString);
							setClipboardContents();
						}

					};

					t.start();

				}
			}
			System.out.print(".");
			// timer.cancel();

		}
	});
	final JCheckBoxMenuItem chckbxmntmAutomaticConversion = new JCheckBoxMenuItem(Converter.translate("Automatic Conversion"));
	String clipboardString = null;
	PopupMenu pm = trayMenu();
	final JScrollPane scrollPane = new JScrollPane();
	TrayIcon trayIcon = new TrayIcon(image, null, pm);

	UndoManager undoManager = new UndoManager();

	Box verticalBox = Box.createVerticalBox();

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GUI() {
		setVisible(true);

		setMinimumSize(new Dimension(1024, 768));

		setSize(new Dimension(1090, 769));

		setTitle("BibTexConverter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(((dim.width - 1024) / 2), ((dim.height - 768) / 2), 1024, 768);
		contentPane.setBounds(new Rectangle(0, 0, 1024, 768));
		contentPane.setPreferredSize(new Dimension(1024, 748));

		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		// Menus
		fileMenu = new JMenu(Converter.translate("File"));
		fileMenu.setBackground(SystemColor.inactiveCaptionBorder);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.setDoubleBuffered(true);
		menuBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		menuBar.setBackground(SystemColor.inactiveCaptionBorder);
		menuBar.add(fileMenu);

		fileMenu.add(mnImport);
		menuItem = new JMenuItem(Converter.translate("Import from file"));
		mnImport.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showOpenFileDialog();
			}
		});

		fileMenu.add(mnExport);
		mntmWord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final String output = Converter.getFormattedStrings();
				final JEditorPane p = new JEditorPane();
				p.setVisible(false);

				p.setContentType("text/html");
				p.setText(output);

				Converter.export2Word(p.getText());
			}
		});

		mnExport.add(mntmWord);
		mntmOpenOffice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final String output = Converter.getFormattedStrings();
				final JEditorPane p = new JEditorPane();
				p.setVisible(false);

				p.setContentType("text/html");
				p.setText(output);

				Converter.export2ODT(p.getText());
			}
		});

		mnExport.add(mntmOpenOffice);
		mntmRtf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final String output = Converter.getFormattedStrings();
				final JEditorPane p = new JEditorPane();
				p.setVisible(false);

				p.setContentType("text/html");
				p.setText(output);

				Converter.export2RTF(p.getText());
			}
		});

		mnExport.add(mntmRtf);
		mntmHtml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final String output = Converter.getFormattedStrings();
				final JEditorPane p = new JEditorPane();
				p.setVisible(false);

				p.setContentType("text/html");
				p.setText(output);

				Converter.export2HTML(p.getText());
			}
		});

		mnExport.add(mntmHtml);

		final JMenuItem mntmBibtex = new JMenuItem("BibTeX");
		mntmBibtex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Converter.export2BibTeX();
			}
		});

		mnExport.addSeparator();
		mnExport.add(mntmBibtex);

		final JMenu mnSettings = new JMenu(Converter.translate("Settings"));
		menuBar.add(mnSettings);

		chckbxmntmAutomaticConversion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// chckbxmntmAutomaticClear.setEnabled(chckbxmntmAutomaticConversion.isSelected());
				// if (!chckbxmntmAutomaticConversion.isSelected()) chckbxmntmAutomaticClear.setSelected(false);
				mnSettings.doClick();
			}
		});
		chckbxmntmAutomaticConversion.setToolTipText(Converter.translate("Automatically start conversion when input changes?"));
		mnSettings.add(chckbxmntmAutomaticConversion);
		chckbxmntmAutomaticClear.setVisible(false);
		chckbxmntmAutomaticClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mnSettings.doClick();
			}
		});
		chckbxmntmAutomaticClear.setToolTipText(Converter.translate("Clear input after conversion?"));

		mnSettings.add(chckbxmntmAutomaticClear);
		languageMenu = new JMenu(Converter.translate("Language"));
		languageMenu.setVisible(false);
		languageMenu.setMnemonic(KeyEvent.VK_L);
		menuBar.add(languageMenu);
		final ButtonGroup group = new ButtonGroup();
		rbMenuEnglish.setSelected(true);
		rbMenuEnglish.setMnemonic(KeyEvent.VK_E);
		rbMenuEnglish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Log.log("Englisch gewählt: " + Settings.language);
				updateElements("English");

			}
		});
		group.add(rbMenuEnglish);
		languageMenu.add(rbMenuEnglish);
		rbMenuDeutsch.setMnemonic(KeyEvent.VK_D);
		rbMenuDeutsch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Log.log("Deutsch gewählt: " + Settings.language);
				updateElements("Deutsch");

			}
		});
		group.add(rbMenuDeutsch);
		languageMenu.add(rbMenuDeutsch);
		setJMenuBar(menuBar);

		final JMenu mnAutomaticMode = new JMenu(Converter.translate("Automatic Modes"));
		menuBar.add(mnAutomaticMode);

		final JMenuItem mntmAutoCollectionMode = new JMenuItem(Converter.translate("Automatic Collection Mode"));
		mntmAutoCollectionMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final int choice = JOptionPane
						.showOptionDialog(
								mntmAutoCollectionMode,
								"This sets the Application to Auto Collection Mode." + "\n" + "\nWhile active, you can collect citations by \"copying\" bibTex-references\n" + "from any textual source (e.g. documents, websites, textfiles...)." + "\n" + "\nTo add a reference to the collection, simply copy the required text (e.g. Ctrl+C)." + "\n" + "\nTo restore the application window, please use the context menu " + "\nof the system tray icon.",
								"Auto Collection Mode", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.OK_OPTION);

				if (choice == 0) {
					autoCollectionMode(true);
				}
				Converter.showErrors = false;
				Converter.showWarnings = false;
			}
		});
		mnAutomaticMode.add(mntmAutoCollectionMode);

		inputPane.getDocument().addUndoableEditListener(new UndoableEditListener() {

			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());

			}
		});
		inputPane.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				if (chckbxmntmAutomaticClear.isSelected()) {

					clearInput = true;
				}
				if (chckbxmntmAutomaticConversion.isSelected()) {
					Converter.convert(getInput());
				}

			}
		});

		inputPane.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				if ((arg0.isControlDown()) & (arg0.getKeyCode() == 90)) { // Ctrl+z-key

					try {
						undoManager.undo();
					} catch (final Exception u) {
					}
				} else if ((arg0.isControlDown()) & (arg0.getKeyCode() == 89)) { // Ctrl+y-key)
					try {
						undoManager.redo();
					} catch (final Exception u) {
					}

				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}
		});

		final JMenuItem mntmAutoConversionMode = new JMenuItem(Converter.translate("Automatic Conversion Mode"));
		mntmAutoConversionMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				final int choice = JOptionPane
						.showOptionDialog(
								mntmAutoCollectionMode,
								"This sets the Application to Auto Conversion Mode." + "\n" + "\nWhile active, you can copy and paste citations directly from" + "\nany textual source (e.g. documents, websites) into another " + "\ndocument (e.g. Word document) in the format set in the" + "\nmain application window." + "\n" + "\nTo change the desired output format, set the new format in the" + "\nsystem tray icon context menu." + "\n" + "\nTo restore the application window, please use the context menu " + "\nof the system tray icon.",

								Converter.translate("Auto Conversion Mode"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.OK_OPTION);

				if (choice == 0) {
					autoConversionMode(true);
				}
			}

		});
		mnAutomaticMode.add(mntmAutoConversionMode);

		final JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);

		final JMenuItem mntmEliminateDuplicatesIn = new JMenuItem(Converter.translate("Clean up file"));
		mntmEliminateDuplicatesIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				chckbxmntmAutomaticConversion.setSelected(false);
				chckbxmntmAutomaticClear.setSelected(false);
				Converter.showErrors = false;
				Converter.showWarnings = false;
				Converter.cleanUpFile();

			}
		});
		mntmEliminateDuplicatesIn.setToolTipText("eliminates duplicates in a specific file");
		mnTools.add(mntmEliminateDuplicatesIn);

		final JMenu mnAbout = new JMenu("?");

		menuBar.add(mnAbout);
		mntmAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								getContentPane(),
								"BibTexConverter (c) 2012" + "\n" + "\nProf. Dr. Oliver Hummel" + "\nJuniorprofessur für Software Engineering" + "\nUniversität Mannheim" + "\n68131 Mannheim" + "\n" + "\nDeveloped by Oliver Erlenkämper" + "\nas part of a student research project.\n\n",
								"About", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		mnAbout.add(mntmAbout);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setOpaque(false);
		inputPane.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPane.setViewportView(inputPane);
		inputPane.setLineWrap(true);
		contentPane.add(scrollPane);
		scrollPane.setMaximumSize(new Dimension(3000, 3000));
		scrollPane.setPreferredSize(new Dimension(856, 360));
		scrollPane.setMinimumSize(new Dimension(856, 360));

		verticalBox.setBounds(new Rectangle(getWidth() - verticalBox.getSize().width, 0, 350, 130));
		verticalBox.setAlignmentX(Component.RIGHT_ALIGNMENT);
		contentPane.add(verticalBox);
		verticalBox.setMaximumSize(new Dimension(130, 350));
		verticalBox.setPreferredSize(new Dimension(130, 350));
		verticalBox.setMinimumSize(new Dimension(130, 330));
		conversionPanel.setMaximumSize(new Dimension(130, 92));
		conversionPanel.setMinimumSize(new Dimension(130, 92));
		verticalBox.add(conversionPanel);
		conversionPanel.setPreferredSize(new Dimension(130, 92));
		conversionPanel.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		conversionPanel.setLayout(null);

		btnConvert = new JButton(Converter.translate("Convert"));
		btnConvert.setBounds(6, 16, 116, 23);
		conversionPanel.add(btnConvert);
		btnConvert.setToolTipText(Converter.translate("start the conversion..."));
		btnAdd.setToolTipText(Converter.translate("Add BibTeX citations to collection"));
		btnAdd.setBounds(6, 39, 116, 23);
		conversionPanel.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				addCitation(getInput());
			}
		});

		btnAdd.setMnemonic(KeyEvent.VK_A);

		final JButton btnClear = new JButton(Converter.translate("Clear"));
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearInput();
			}
		});
		btnClear.setBounds(6, 62, 116, 23);
		conversionPanel.add(btnClear);
		btnAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		btnConvert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				convert();
			}
		});
		optionsPanel.setMaximumSize(new Dimension(130, 162));
		optionsPanel.setMinimumSize(new Dimension(130, 162));
		verticalBox.add(optionsPanel);
		optionsPanel.setPreferredSize(new Dimension(130, 162));
		optionsPanel.setBorder(new TitledBorder(null, Converter.translate("Output format"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		optionsPanel.setLayout(null);

		final JLabel lblFormat = new JLabel(Converter.translate("Reference format"));
		lblFormat.setBounds(8, 18, 114, 14);
		optionsPanel.add(lblFormat);

		cbbCitationFormat.setBounds(8, 35, 114, 20);
		optionsPanel.add(cbbCitationFormat);

		cbbCitationFormat.setToolTipText(Converter.translate("Please choose output format"));

		cbbCitationFormat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Settings.setFormat(cbbCitationFormat.getSelectedIndex());
				cbbCitationFormat.setToolTipText(Settings.getFormatTooltip()[cbbCitationFormat.getSelectedIndex()]);
				Converter.updateGui();
			}
		});
		cbbCitationFormat.setModel(new DefaultComboBoxModel(Settings.getFormatNames()));

		final JButton btnNew = new JButton(Converter.translate("New"));
		btnNew.setToolTipText(Converter.translate("Create new format"));
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createCustomizeGui(NEW);
			}
		});
		btnNew.setBounds(8, 107, 116, 22);
		optionsPanel.add(btnNew);

		final JButton btnCopy = new JButton(Converter.translate("Copy"));
		btnCopy.setToolTipText(Converter.translate("Copy selected format into new reference entry"));
		btnCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createCustomizeGui(COPY);
			}
		});
		btnCopy.setBounds(8, 84, 116, 22);
		optionsPanel.add(btnCopy);

		final JButton btnEdit_1 = new JButton(Converter.translate("Edit..."));
		btnEdit_1.setToolTipText("Edit selected format");
		btnEdit_1.setBounds(8, 61, 116, 22);
		optionsPanel.add(btnEdit_1);
		btnEdit_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				createCustomizeGui(EDIT);
			}

		});

		final JButton btnDelete = new JButton(Converter.translate("Delete"));
		btnDelete.setToolTipText(Converter.translate("Delete selected entry"));
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final int selection = JOptionPane.showConfirmDialog(contentPane, Converter.translate("Are you sure to delete ") + (String) cbbCitationFormat.getSelectedItem() + "?", "Confirm delete",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (selection == 0) {
					@SuppressWarnings("unused")
					int index;
					try {
						index = cbbCitationFormat.getSelectedIndex();
					} catch (final IndexOutOfBoundsException iobe) {
					} finally {
						if (cbbCitationFormat.getSelectedIndex() >= 0) {
							Settings.patternFormats.remove(cbbCitationFormat.getSelectedIndex());
							Settings.patternNames.remove(cbbCitationFormat.getSelectedIndex());
							Settings.patternPrePost.remove(cbbCitationFormat.getSelectedIndex());
							Settings.patternSettings.remove(cbbCitationFormat.getSelectedIndex());
							updateCbbFormats();
						}
					}
				}
			}
		});
		btnDelete.setBounds(8, 130, 116, 22);
		optionsPanel.add(btnDelete);
		chckbxSortResult.setPreferredSize(new Dimension(52, 23));
		chckbxSortResult.setAlignmentX(Component.RIGHT_ALIGNMENT);
		chckbxSortResult.setToolTipText(Converter.translate("sort the citations"));
		chckbxSortResult.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Settings.sortCitations = chckbxSortResult.isSelected();

				chckbxDuplicates.setEnabled(chckbxSortResult.isSelected());
				Converter.updateGui();
			}
		});

		chckbxSortResult.setSelected(Settings.sortCitations);

		final JComboBox cbbIndexFormat = new JComboBox();
		cbbIndexFormat.setPreferredSize(new Dimension(116, 20));
		inputPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		inputPanel.setPreferredSize(new Dimension(128, 100));
		verticalBox.add(inputPanel);
		inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		final JLabel lblIndexFormat = new JLabel(Converter.translate("Index format:"));
		lblIndexFormat.setPreferredSize(new Dimension(116, 14));
		inputPanel.add(lblIndexFormat);
		inputPanel.add(cbbIndexFormat);
		cbbIndexFormat.setToolTipText("Select indexing format");
		cbbIndexFormat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (cbbIndexFormat.getSelectedIndex() == 0) {
					Settings.indexCitations = false;
				} else {
					Settings.indexCitations = true;
				}

				Settings.indexFormat = cbbIndexFormat.getSelectedIndex();
				Converter.updateGui();
			}
		});
		cbbIndexFormat.setModel(new DefaultComboBoxModel(new String[] { "- None -", "Number", "[Author (abr.) Year]" }));
		listScrollPane = new JScrollPane();
		contentPane.add(listScrollPane);
		listScrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
		listScrollPane.setBounds(new Rectangle(10, 0, 998, 284));
		listScrollPane.setPreferredSize(new Dimension(998, 275));
		listScrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		listScrollPane.setAutoscrolls(true);
		list.setAlignmentY(Component.TOP_ALIGNMENT);
		listScrollPane.setViewportView(list);
		list.setBounds(new Rectangle(0, 0, 260, 132));
		list.setOpaque(false);
		list.setPreferredSize(new Dimension(260, 100));
		contentPane.add(horizontalBox);
		horizontalBox.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		horizontalBox.setPreferredSize(new Dimension(998, 25));
		horizontalBox.setBounds(new Rectangle(0, 0, 998, 0));

		btnCopyToClipboard = new JButton(Converter.translate("Copy to Clipboard"));
		horizontalBox.add(btnCopyToClipboard);
		btnCopyToClipboard.setToolTipText(Converter.translate("Copy the output to the clipboard (for pasting into other applications, e.g. MS Word)"));
		btnCopyToClipboard.setEnabled(false);
		btnCopyToClipboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// outputPane.requestFocusInWindow();
				// outputPane.selectAll();
				setClipboardContents();
				Converter.setStatus(Converter.translate("Copied to clipboard."));
			}
		});
		horizontalBox.add(chckbxAutomaticallyCopyTo);
		chckbxAutomaticallyCopyTo.setToolTipText(Converter.translate("copy the output automatically to the clipboard (after the conversion has finished)"));
		// statusScrollPane.add(statusPane);
		chckbxAutomaticallyCopyTo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.automaticallyCopyToClipboard = chckbxAutomaticallyCopyTo.isSelected();
			}
		});
		chckbxAutomaticallyCopyTo.setSelected(Settings.automaticallyCopyToClipboard);

		horizontalBox.add(horizontalGlue_1);
		btnEdit.setPreferredSize(new Dimension(97, 23));
		btnEdit.setMinimumSize(new Dimension(97, 23));
		btnEdit.setMaximumSize(new Dimension(97, 23));
		horizontalBox.add(btnEdit);

		btnEdit.setEnabled(false);
		btnEdit.setToolTipText(Converter.translate("edit selected entry"));
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (list.getSelectedValue() != null) {
					createEditGui();
				}
			}
		});
		btnRemove.setMaximumSize(new Dimension(97, 23));
		btnRemove.setMinimumSize(new Dimension(97, 23));
		btnRemove.setPreferredSize(new Dimension(97, 23));
		horizontalBox.add(btnRemove);
		btnRemove.setEnabled(false);
		btnRemove.setToolTipText(Converter.translate("remove selected entry"));

		btnRemove.addActionListener(new ActionListener() {// TODO

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (list.getSelectedValue() != null) {
							final int index = list.getSelectedIndex();
							Converter.removeFromCitationList(list.getSelectedValue());
							list.setSelectedIndex(index);

						}
					}

				});

		horizontalBox.add(horizontalGlue);
		btnReset.setPreferredSize(new Dimension(97, 23));
		btnReset.setMaximumSize(new Dimension(97, 23));
		btnReset.setMinimumSize(new Dimension(97, 23));
		btnReset.setBounds(new Rectangle(0, 0, 97, 23));
		horizontalBox.add(btnReset);

		btnReset.setToolTipText(Converter.translate("Resets the converter"));
		statusPane.setEditable(false);
		statusPane.setMaximumSize(new Dimension(10000, 10000));
		statusPane.setPreferredSize(new Dimension(998, 20));
		contentPane.add(statusPane);

		statusPane.setFont(new Font("Tahoma", Font.BOLD, 11));
		statusPane.setBackground(UIManager.getColor("CheckBox.background"));
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Converter.reset();
				reset();
			}
		});

		scrollPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				list.setPreferredSize(scrollPane.getSize());
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				final Dimension windowSize = e.getComponent().getSize();

				contentPane.setBounds(0, menuBar.getSize().height, windowSize.width, windowSize.height);
				contentPane.validate();
				verticalBox.setBounds(new Rectangle(windowSize.width - verticalBox.getSize().width - 20, 10, 130, 350));

				scrollPane.setBounds(5, 5, windowSize.getSize().width - verticalBox.getSize().width - 40, windowSize.height - listScrollPane.getBounds().height - 140);
				scrollPane.setViewportView(inputPane);
				listScrollPane.setBounds(5, scrollPane.getBounds().y + scrollPane.getBounds().height + 5, windowSize.width - 30, 275);
				listScrollPane.setViewportView(list);
				horizontalBox.setBounds(5, listScrollPane.getBounds().y + listScrollPane.getBounds().height, windowSize.width - 30, 40);
				horizontalBox.validate();

				statusPane.setBounds(5, horizontalBox.getBounds().y + 40, listScrollPane.getBounds().width, 20);
				menuBar.setVisible(true);
				for (final Component c : contentPane.getComponents()) {
					c.validate();
				}

			}
		});
		repaint();
		validate();

		setSize(getWidth() + 1, getHeight() + 1);
		updateElements(Settings.language);
	}

	private void autoCollectionMode(Boolean active) {
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		clipboard.setContents(new Transferable() {

			@Override
			public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException {

				return null;
			}

			@Override
			public DataFlavor[] getTransferDataFlavors() {

				return null;
			}

			@Override
			public boolean isDataFlavorSupported(DataFlavor arg0) {

				return false;
			}
		}, null);

		if (active) {
			autoCollection.start();
			minimizeToTray(Converter.translate("Auto Collection Mode"));
		} else if (!active) {
			autoCollection.stop();
		}

	}

	private void autoConversionMode(Boolean active) {
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Settings.indexCitations = false;

		clipboard.setContents(new Transferable() {

			@Override
			public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException {

				return null;
			}

			@Override
			public DataFlavor[] getTransferDataFlavors() {

				return null;
			}

			@Override
			public boolean isDataFlavorSupported(DataFlavor arg0) {

				return false;
			}
		}, null);

		if (active) {
			autoConversion.start();

			minimizeToTray(Converter.translate("Auto Conversion Mode"));
		} else if (!active) {
			autoConversion.stop();
		}
	}

	private void automaticModeAddCitation(String s) {
		final String c = s;

		addCitation(c);
		trayIcon.displayMessage(Converter.translate("Citation added"), Converter.translate("Collection now contains ") + Converter.getCitationList().size() + Converter.translate(" citations."),
				TrayIcon.MessageType.INFO);

	}

	private void clearInput() {
		final CaretListener[] clis = inputPane.getCaretListeners();
		final CaretListener cli = clis[0];

		inputPane.removeCaretListener(cli);
		inputPane.setText("");
		inputPane.addCaretListener(cli);
	}

	/**
	 * sets the <code>enabled</code> flag of the GUI's buttons to <code>state</code>
	 * 
	 * @param state
	 */
	private void enableButtons(Boolean state) {
		btnCopyToClipboard.setEnabled(state);
		btnRemove.setEnabled(state);
		btnEdit.setEnabled(state);
	}

	private void minimizeToTray(String header) {
		pm = trayMenu();
		trayIcon = new TrayIcon(image, "BibTexConverter\n" + header, pm);

		trayIcon.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {
					setVisible(true);
					autoConversionMode(false);
					autoCollectionMode(false);
					SystemTray.getSystemTray().remove(trayIcon);
				}

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}
		});

		try {
			SystemTray.getSystemTray().add(trayIcon);
		} catch (final AWTException e1) {

			e1.printStackTrace();
		}

		trayIcon.displayMessage(header, Converter.translate("Double klick to restore BibTextConverter.\nRight click for options."), TrayIcon.MessageType.INFO);
		setVisible(false);

	}

	private PopupMenu trayMenu() {
		final PopupMenu menu = new PopupMenu("Tray Menu");

		final MenuItem miRestore = new MenuItem("Restore");
		miRestore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				setVisible(true);
				autoConversionMode(false);
				autoCollectionMode(false);
				SystemTray.getSystemTray().remove(trayIcon);
			}
		});

		final MenuItem miReset = new MenuItem(Converter.translate("Reset"));
		miReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Converter.reset();

			}
		});

		final MenuItem miExit = new MenuItem("Exit");
		miExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}
		});

		final Menu miChange = new Menu(Converter.translate("Change format"));

		for (int i = 0; i < Settings.patternNames.size(); i++) {
			final int selected = i;
			final MenuItem mi = new MenuItem(Settings.patternNames.get(i)[0]);
			mi.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Settings.setFormat(selected);
					cbbCitationFormat.setSelectedIndex(selected);
					trayIcon.displayMessage(Converter.translate("Format changed"), Converter.translate("New Format: ") + Settings.patternNames.get(selected)[0], TrayIcon.MessageType.INFO);
				}
			});
			miChange.add(mi);
		}

		miRestore.setFont(new Font("Arial", Font.BOLD, 12));
		menu.add(miRestore);
		menu.add(miReset);
		menu.add(miChange);

		menu.add(new MenuItem("-"));
		menu.add(miExit);

		return menu;

	}

	/**
	 * updates the language variable in the Settings Class
	 * 
	 * @param l
	 *            the laguage to use
	 */
	private void updateElements(String l) {
		Settings.setLanguage(l);
		// update components

		btnConvert.setText(Converter.translate("Convert"));
		btnConvert.validate();
		languageMenu.setText(Converter.translate("Language"));
		if (l == "English") {
			languageMenu.setMnemonic(KeyEvent.VK_L);
		}
		if (l == "Deutsch") {
			languageMenu.setMnemonic(KeyEvent.VK_S);
		}
		languageMenu.validate();
		menuItem.setText(Converter.translate("Import from file"));
		menuItem.validate();
		fileMenu.setText("Import/Export");
		fileMenu.validate();
		btnCopyToClipboard.setText(Converter.translate("Copy to Clipboard"));

		chckbxAutomaticallyCopyTo.setText(Converter.translate("automatically"));

		inputPanel.setBorder(new TitledBorder(null, "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		inputPanel.add(chckbxSortResult);

		chckbxSortResult.setText(Converter.translate("sort"));
		chckbxSortResult.setSelected(Settings.sortCitations);
		chckbxDuplicates.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.eliminateDuplicates = chckbxDuplicates.isSelected();
				Converter.updateGui();
			}
		});
		chckbxDuplicates.setSelected(Settings.eliminateDuplicates);

		inputPanel.add(chckbxDuplicates);
		chckbxSortResult.validate();
		btnRemove.validate();
		btnAdd.validate();

	}

	protected void addCitation(final String s) {
		Converter.add(s);
		enableButtons(true);
	}

	protected void convert() {
		enableButtons(true);

		Converter.convert(getInput());

		if (chckbxmntmAutomaticClear.isSelected()) {
			inputPane.setText("");
		}

	}

	/**
	 * Shows the Open-File-Dialogue and lets the user import references from an existing .bib-file
	 */
	protected void showOpenFileDialog() {
		Converter.readFromFile();
	}

}
