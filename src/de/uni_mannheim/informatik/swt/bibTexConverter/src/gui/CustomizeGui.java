package de.uni_mannheim.informatik.swt.bibTexConverter.src.gui;

import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.Citation;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.Converter;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.settings.Settings;

@SuppressWarnings("serial")
public class CustomizeGui extends JFrame {

	public static Citation citation;
	public static JPanel contentPane;

	private static String[] format;
	private static TextField namePane = new TextField();
	private static String[][] prePost;
	private static String[] settings;
	private static TextField shortNamePane = new TextField();
	private static JTable table;
	protected static final int COPY = 1;
	protected static final int EDIT = 0;
	protected static final int NEW = 2;
	static ImageIcon ok = new javax.swing.ImageIcon(CustomizeGui.class.getResource("ok.gif"));
	static JEditorPane previewPane = new JEditorPane();

	static JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	static ImageIcon x = new javax.swing.ImageIcon(CustomizeGui.class.getResource("x.gif"));

	/**
	 * returns the embedded reference format
	 * 
	 * @return
	 */
	public static String[] getFormat() {
		return format;
	}

	/**
	 * returns the Pre-/Postfix values
	 * 
	 * @return
	 */
	public static String[][] getPrePost() {
		return prePost;
	}

	/**
	 * returns the settings
	 * 
	 * @return
	 */
	public static String[] getSettings() {
		return settings;
	}

	/**
	 * sets the embedded reference format
	 * 
	 * @param format
	 */
	public static void setFormat(String[] format) {
		CustomizeGui.format = format;
	}

	/**
	 * sets new names for the pattern
	 * 
	 * @param names
	 */
	public static void setNames(String[] names) {
		namePane.setText(names[1]);
		shortNamePane.setText(names[0]);
	}

	public static void setPrePost(String[][] prePost) {
		CustomizeGui.prePost = prePost;
	}

	/**
	 * sets the new settings
	 * 
	 * @param settings
	 */
	public static void setSettings(String[] settings) {
		CustomizeGui.settings = settings;
	}

	private static String[][] buildPrePost() {
		final String[][] pP = new String[][] {

		{ (String) table.getValueAt(0, 0), (String) table.getValueAt(0, 2) }, { (String) table.getValueAt(1, 0), (String) table.getValueAt(1, 2) },
				{ (String) table.getValueAt(2, 0), (String) table.getValueAt(2, 2) }, { (String) table.getValueAt(3, 0), (String) table.getValueAt(3, 2) },
				{ (String) table.getValueAt(5, 0), (String) table.getValueAt(5, 2) }, { (String) table.getValueAt(6, 0), (String) table.getValueAt(6, 2) },
				{ (String) table.getValueAt(7, 0), (String) table.getValueAt(7, 2) }, { (String) table.getValueAt(9, 0), (String) table.getValueAt(9, 2) },
				{ (String) table.getValueAt(11, 0), (String) table.getValueAt(11, 2) }, { (String) table.getValueAt(13, 0), (String) table.getValueAt(13, 2) },
				{ (String) table.getValueAt(14, 0), (String) table.getValueAt(14, 2) }, { (String) table.getValueAt(15, 0), (String) table.getValueAt(15, 2) },
				{ (String) table.getValueAt(16, 0), (String) table.getValueAt(16, 2) }, { (String) table.getValueAt(17, 0), (String) table.getValueAt(17, 2) },
				{ (String) table.getValueAt(18, 0), (String) table.getValueAt(18, 2) }, { (String) table.getValueAt(19, 0), (String) table.getValueAt(19, 2) },
				{ (String) table.getValueAt(20, 0), (String) table.getValueAt(20, 2) }, { (String) table.getValueAt(21, 0), (String) table.getValueAt(21, 2) },
				{ (String) table.getValueAt(22, 0), (String) table.getValueAt(22, 2) }, { (String) table.getValueAt(23, 0), (String) table.getValueAt(23, 2) },
				{ (String) table.getValueAt(24, 0), (String) table.getValueAt(24, 2) }, { (String) table.getValueAt(25, 0), (String) table.getValueAt(25, 2) },
				{ (String) table.getValueAt(8, 0), (String) table.getValueAt(8, 2) }, { (String) table.getValueAt(12, 0), (String) table.getValueAt(12, 2) },
				{ (String) table.getValueAt(4, 0), (String) table.getValueAt(4, 2) }, { (String) table.getValueAt(10, 0), (String) table.getValueAt(10, 2) }, getPrePost()[26]

		};

		return pP;

	}

	static void applyForAll(String lastFormat) {

		for (int t = 0; t < tabbedPane.getComponentCount(); t++) {
			((tabbedPanel) tabbedPane.getComponent(t)).format = lastFormat;

			((tabbedPanel) tabbedPane.getComponent(t)).tfPattern.setText(((tabbedPanel) tabbedPane.getComponent(t)).format);
			Log.log("CUSTOMIZEGUI: set format of pane " + t + " to " + lastFormat);

		}
		setIcons();

	}

	static void setIcons() {

		if (((tabbedPanel) tabbedPane.getComponentAt(0)).patternSet) {
			tabbedPane.setIconAt(0, ok);
		} else {
			tabbedPane.setIconAt(0, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(1)).patternSet) {
			tabbedPane.setIconAt(1, ok);
		} else {
			tabbedPane.setIconAt(1, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(2)).patternSet) {
			tabbedPane.setIconAt(2, ok);
		} else {
			tabbedPane.setIconAt(2, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(3)).patternSet) {
			tabbedPane.setIconAt(3, ok);
		} else {
			tabbedPane.setIconAt(3, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(4)).patternSet) {
			tabbedPane.setIconAt(4, ok);
		} else {
			tabbedPane.setIconAt(4, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(5)).patternSet) {
			tabbedPane.setIconAt(5, ok);
		} else {
			tabbedPane.setIconAt(5, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(6)).patternSet) {
			tabbedPane.setIconAt(6, ok);
		} else {
			tabbedPane.setIconAt(6, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(7)).patternSet) {
			tabbedPane.setIconAt(7, ok);
		} else {
			tabbedPane.setIconAt(7, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(8)).patternSet) {
			tabbedPane.setIconAt(8, ok);
		} else {
			tabbedPane.setIconAt(8, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(9)).patternSet) {
			tabbedPane.setIconAt(9, ok);
		} else {
			tabbedPane.setIconAt(9, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(10)).patternSet) {
			tabbedPane.setIconAt(10, ok);
		} else {
			tabbedPane.setIconAt(10, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(11)).patternSet) {
			tabbedPane.setIconAt(11, ok);
		} else {
			tabbedPane.setIconAt(11, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(12)).patternSet) {
			tabbedPane.setIconAt(12, ok);
		} else {
			tabbedPane.setIconAt(12, x);
		}
		if (((tabbedPanel) tabbedPane.getComponentAt(13)).patternSet) {
			tabbedPane.setIconAt(13, ok);
		} else {
			tabbedPane.setIconAt(13, x);
		}
	}

	static void setPreview(String formats) {
		citation.format = formats;
		citation.prePost = buildPrePost();
		citation.settings = getSettings();
		previewPane.setText(citation.toString());
		setIcons();

	}

	private final JButton btnCancel;

	private final JButton btnExport;
	private final JButton btnImport;
	private final JButton btnSave;
	private final JPanel formatNamePanel;
	private final JLabel lblShortName;
	private String longName;

	private final int operationMode;

	private final JScrollPane scrollPane;

	private String shortName;
	final String BOLD = "b";
	JCheckBox chckbxAbbreviateNames = new JCheckBox("abbreviate names");

	final JCheckBox chckbxUseEtAl = new JCheckBox("use et al.");

	final String ITALIC = "i";

	TextField tfAuthorsFormat = new TextField();

	TextField tfAuthorsSeparator = new TextField();

	final TextField tfEtAlFormat = new TextField();

	final TextField tfEtAlNumber = new TextField();

	TextField tfEtAlThenShow = new TextField();

	final String UPPERCASE = "X";

	/**
	 * Create the frame.
	 * 
	 * @param formats
	 * @param mode
	 */
	public CustomizeGui(int mode, String[] formats, final String[] settings, String[][] pP, String[] nameArray) {
		setAlwaysOnTop(true);
		setResizable(false);

		CustomizeGui.setPrePost(pP);
		CustomizeGui.setFormat(formats);
		CustomizeGui.setSettings(settings);
		longName = nameArray[1];
		shortName = nameArray[0];
		operationMode = mode;
		initTable();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		CustomizeGui.setFormat(formats);
		CustomizeGui.setPrePost(pP);
		previewPane.setText(CustomizeGui.getFormat()[0]);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1024, 706);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if ((shortNamePane.getText().isEmpty() | namePane.getText().isEmpty() | Settings.formatAlreadyExists(shortNamePane.getText())) & (operationMode != EDIT)) {
					JOptionPane.showMessageDialog(contentPane, "Please use unique names and shortnames for format patterns!", "Unique identifier needed", JOptionPane.WARNING_MESSAGE);
					namePane.requestFocusInWindow();
				} else {
					save();
					dispose();
				}
			}
		});
		btnSave.setBounds(816, 652, 89, 23);
		contentPane.add(btnSave);

		tabbedPane = new JTabbedPane();
		tabbedPane.setLocation(234, 199);
		tabbedPane.setSize(772, 276);
		tabbedPane.setBorder(null);

		final tabbedPanel tabbedPanel_ = new tabbedPanel(formats[0], "Article");
		tabbedPanel_.tfPattern.setEditable(true);
		tabbedPane.addTab("Article", (Icon) null, tabbedPanel_, null);
		tabbedPane.addTab("Book", null, new tabbedPanel(formats[1], "Book"), null);
		tabbedPane.addTab("InBook", null, new tabbedPanel(formats[4], "Bookchapter"), null);
		tabbedPane.addTab("Bookchapter", null, new tabbedPanel(formats[2], "Booklet"), null);
		tabbedPane.addTab("Conference", null, new tabbedPanel(formats[3], "Conference"), null);
		tabbedPane.addTab("Collection", null, new tabbedPanel(formats[5], "InCollection"), null);
		tabbedPane.addTab("Manual", null, new tabbedPanel(formats[7], "Manual"), null);
		tabbedPane.addTab("Mastersthesis", null, new tabbedPanel(formats[8], "Mastersthesis"), null);
		tabbedPane.addTab("PhdThesis", null, new tabbedPanel(formats[10], "PhdThesis"), null);
		tabbedPane.addTab("Proceedings", null, new tabbedPanel(formats[11], "Proceedings"), null);
		tabbedPane.addTab("InProceedings", null, new tabbedPanel(formats[6], "In Proceedings"), null);
		tabbedPane.addTab("Techreport", null, new tabbedPanel(formats[12], "Techreport"), null);
		tabbedPane.addTab("Unpublished", null, new tabbedPanel(formats[13], "Unpublished"), null);
		tabbedPane.addTab("Misc", null, new tabbedPanel(formats[9], "Misc"), null);

		tabbedPane.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				setPreview(((tabbedPanel) tabbedPane.getSelectedComponent()).format);
				((tabbedPanel) tabbedPane.getSelectedComponent()).init();

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setPreview(((tabbedPanel) tabbedPane.getSelectedComponent()).format);
				// ((tabbedPanel) tabbedPane.getSelectedComponent()).update();

			}
		});

		final String[] valuePairArray = new String[] { "title = Title", "author = Oren Patashnik and Leslie Lamport", "year = 2012", "booktitle = Booktitle", "publisher = Publisher Press",
				"editor = Konrad Zuse and Johannes Gutenberg", "school = University of Mannheim", "chapter = 1", "institution = Institute of Informatics", "journal = Journal of Advanced Informatics",
				"note = Note on the citation", "pages = 1-2", "address = Mannheim, Germany", "edition = first", "howpublished = Computer program", "month = JAN", "number = 17",
				"organization = ACME Inc.", "series = Lecture Notes in Computer Science", "type = Thesis", "volume = 1", "url = http://www.uni-mannheim.de", "numpages = 13", "isbn = 12345-67890",
				"location = A specific location", "doi = 12345/678.90"

		};

		citation = new Citation(0, valuePairArray);
		setPreview(formats[0]);

		contentPane.add(tabbedPane);

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "Preview", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 85, 996, 103);
		contentPane.add(panel);

		previewPane.setContentType("text/html");
		previewPane.setBounds(10, 24, 976, 69);
		previewPane.setEditable(false);
		panel.add(previewPane);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 199, 214, 444);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(table);

		formatNamePanel = new JPanel();
		formatNamePanel.setBorder(new TitledBorder(null, "Format Name", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		formatNamePanel.setBounds(10, 8, 471, 74);
		contentPane.add(formatNamePanel);
		formatNamePanel.setLayout(null);

		namePane = new TextField();
		namePane.addTextListener(new TextListener() {
			@Override
			public void textValueChanged(TextEvent arg0) {
				longName = namePane.getText();
			}
		});
		namePane.setText(longName);
		namePane.setBounds(180, 16, 279, 20);
		formatNamePanel.add(namePane);
		namePane.setFont(new Font("Tahoma", Font.BOLD, 11));

		final JLabel lblBibliographyFormatName = new JLabel("Bibliography format name:");
		lblBibliographyFormatName.setLabelFor(namePane);

		lblBibliographyFormatName.setBounds(10, 16, 164, 20);
		formatNamePanel.add(lblBibliographyFormatName);

		lblShortName = new JLabel("Short Name:");
		lblShortName.setBounds(10, 40, 164, 20);
		formatNamePanel.add(lblShortName);
		lblShortName.setLabelFor(shortNamePane);

		shortNamePane = new TextField();
		shortNamePane.addTextListener(new TextListener() {
			@Override
			public void textValueChanged(TextEvent e) {
				shortName = shortNamePane.getText();
			}
		});
		shortNamePane.setText(shortName);
		shortNamePane.setBounds(180, 40, 279, 20);
		formatNamePanel.add(shortNamePane);
		shortNamePane.setFont(new Font("Tahoma", Font.BOLD, 11));

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(911, 652, 91, 23);
		contentPane.add(btnCancel);

		final JSeparator separator = new JSeparator();
		separator.setBounds(3, 645, 1008, 1);
		contentPane.add(separator);

		btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportFormat();
			}
		});
		btnExport.setBounds(426, 652, 91, 23);
		contentPane.add(btnExport);

		btnImport = new JButton("Import");
		btnImport.setEnabled(false);
		btnImport.setToolTipText("Please create NEW Format to use the import function.");
		if (mode == NEW) {
			btnImport.setEnabled(true);
		}
		btnImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				importFormat();
			}
		});
		btnImport.setBounds(325, 652, 91, 23);
		contentPane.add(btnImport);

		final JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Notation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(784, 486, 218, 126);
		contentPane.add(panel_1);
		chckbxUseEtAl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings[3] = String.valueOf(chckbxUseEtAl.isSelected());
				((tabbedPanel) tabbedPane.getSelectedComponent()).updatePreview();
			}
		});

		chckbxUseEtAl.setToolTipText("display authors with \"et al.\"?");
		chckbxUseEtAl.setSelected(true);
		chckbxUseEtAl.setBounds(6, 16, 87, 23);
		panel_1.add(chckbxUseEtAl);
		tfEtAlNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				int value;
				try {
					value = Integer.parseInt(tfEtAlNumber.getText());
				} catch (final Exception i) {
					value = -1;
				}
				;
				if (tfEtAlNumber.getText().equals("") | (value == -1)) {
					tfEtAlNumber.setText("0");
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				int value;
				try {
					value = Integer.parseInt(tfEtAlNumber.getText());
				} catch (final Exception i) {
					value = -1;
				}
				;
				if (tfEtAlNumber.getText().equals("") | (value == -1)) {
					tfEtAlNumber.setText("0");
				}

			}
		});
		tfEtAlNumber.addTextListener(new TextListener() {
			@Override
			public void textValueChanged(TextEvent arg0) {
				int value;
				try {
					value = Integer.parseInt(tfEtAlNumber.getText());
				} catch (final Exception i) {
					value = -1;
				}
				;
				if (tfEtAlNumber.getText().equals("") | (value == -1)) {
					tfEtAlNumber.setText("0");
				}
				settings[5] = tfEtAlNumber.getText();
				((tabbedPanel) tabbedPane.getSelectedComponent()).updatePreview();

			}
		});

		tfEtAlNumber.setText((String) null);
		tfEtAlNumber.setColumns(10);
		tfEtAlNumber.setBounds(167, 17, 38, 22);
		panel_1.add(tfEtAlNumber);
		tfEtAlFormat.addTextListener(new TextListener() {
			@Override
			public void textValueChanged(TextEvent e) {
				settings[4] = tfEtAlFormat.getText();
				((tabbedPanel) tabbedPane.getSelectedComponent()).updatePreview();
			}
		});

		tfEtAlFormat.setText((String) null);
		tfEtAlFormat.setEnabled(true);
		tfEtAlFormat.setBounds(103, 67, 102, 22);
		panel_1.add(tfEtAlFormat);

		final JLabel label = new JLabel("et al.- format:");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(6, 67, 87, 22);
		panel_1.add(label);
		tfEtAlThenShow.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int value;
				try {
					value = Integer.parseInt(tfEtAlThenShow.getText());
				} catch (final Exception i) {
					value = -1;
				}
				;

				if (tfEtAlThenShow.getText().equals("") | (value == -1)) {
					tfEtAlThenShow.setText("0");
				}
				if (Integer.parseInt(tfEtAlThenShow.getText()) > Integer.parseInt(tfEtAlNumber.getText())) {
					tfEtAlThenShow.setText(tfEtAlNumber.getText());
				}

			}

			@Override
			public void keyTyped(KeyEvent e) {
				int value;
				try {
					value = Integer.parseInt(tfEtAlThenShow.getText());
				} catch (final Exception i) {
					value = -1;
				}
				;
				if (tfEtAlThenShow.getText().equals("") | (value == -1)) {
					tfEtAlThenShow.setText("0");
				}
			}
		});
		tfEtAlThenShow.addTextListener(new TextListener() {
			@Override
			public void textValueChanged(TextEvent e) {
				int value;
				try {
					value = Integer.parseInt(tfEtAlThenShow.getText());
				} catch (final Exception i) {
					value = -1;
				}
				;
				if (tfEtAlThenShow.getText().equals("") | (value == -1)) {
					tfEtAlThenShow.setText("0");
				}
				settings[6] = tfEtAlThenShow.getText();
				((tabbedPanel) tabbedPane.getSelectedComponent()).updatePreview();
			}
		});

		tfEtAlThenShow.setText((String) null);
		tfEtAlThenShow.setBounds(167, 39, 38, 22);
		panel_1.add(tfEtAlThenShow);

		final JLabel label_1 = new JLabel("then show");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(103, 39, 58, 22);
		panel_1.add(label_1);

		final JLabel lblIf = new JLabel("if authors >");
		lblIf.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIf.setBounds(99, 16, 62, 22);
		panel_1.add(lblIf);

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(null, "Authors/Editors format", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(234, 486, 447, 126);
		contentPane.add(panel_2);
		tfAuthorsFormat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				selectWholeWord(tfAuthorsFormat);
			}
		});
		tfAuthorsFormat.addTextListener(new TextListener() {
			@Override
			public void textValueChanged(TextEvent e) {
				settings[0] = tfAuthorsFormat.getText();
				((tabbedPanel) tabbedPane.getSelectedComponent()).updatePreview();
			}
		});
		tfAuthorsFormat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectWholeWord(tfAuthorsFormat);
			}
		});

		tfAuthorsFormat.setText((String) null);
		tfAuthorsFormat.setBounds(14, 36, 290, 19);
		panel_2.add(tfAuthorsFormat);

		final JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(144, 73, 162, 23);
		panel_2.add(panel_3);

		final JToggleButton toggleButton = new JToggleButton("i");
		toggleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				format(ITALIC, tfAuthorsFormat);
			}
		});
		toggleButton.setToolTipText("italic");
		toggleButton.setBounds(55, 0, 48, 23);
		panel_3.add(toggleButton);

		final JToggleButton toggleButton_1 = new JToggleButton("X");
		toggleButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				format(UPPERCASE, tfAuthorsFormat);
			}
		});
		toggleButton_1.setToolTipText("to Upper Case");
		toggleButton_1.setBounds(114, 0, 48, 23);
		panel_3.add(toggleButton_1);

		final JToggleButton toggleButton_2 = new JToggleButton("b");
		toggleButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				format(BOLD, tfAuthorsFormat);
			}
		});
		toggleButton_2.setToolTipText("bold");
		toggleButton_2.setBounds(0, 0, 48, 23);
		panel_3.add(toggleButton_2);

		final JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfAuthorsFormat.setText((String) comboBox.getSelectedItem());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "{first} {last}", "{last}, {first}", "<X>{last}</X>, {first}" }));
		comboBox.setToolTipText("sample formats");
		comboBox.setBounds(14, 73, 120, 22);
		panel_2.add(comboBox);
		tfAuthorsSeparator.addTextListener(new TextListener() {
			@Override
			public void textValueChanged(TextEvent e) {
				settings[1] = tfAuthorsSeparator.getText();
				((tabbedPanel) tabbedPane.getSelectedComponent()).updatePreview();
			}
		});

		tfAuthorsSeparator.setText((String) null);
		tfAuthorsSeparator.setBounds(316, 36, 67, 19);
		panel_2.add(tfAuthorsSeparator);

		final JLabel label_3 = new JLabel("Name format:");
		label_3.setBounds(14, 11, 162, 26);
		panel_2.add(label_3);

		final JLabel lblLastAuthorSeparator = new JLabel("Last Author Separator:");
		lblLastAuthorSeparator.setBounds(316, 11, 121, 26);
		panel_2.add(lblLastAuthorSeparator);

		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfAuthorsSeparator.setText((String) comboBox_1.getSelectedItem());
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { " AND ", " & ", ", " }));
		comboBox_1.setToolTipText("sample formats");
		comboBox_1.setBounds(316, 73, 67, 22);
		panel_2.add(comboBox_1);
		chckbxAbbreviateNames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settings[2] = String.valueOf(chckbxAbbreviateNames.isSelected());
				((tabbedPanel) tabbedPane.getSelectedComponent()).updatePreview();
			}
		});

		chckbxAbbreviateNames.setSelected(false);
		chckbxAbbreviateNames.setBounds(14, 96, 193, 23);
		panel_2.add(chckbxAbbreviateNames);

		new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxUseEtAl.isSelected()) {
					setFormat(3, "true");
					tfEtAlFormat.setEnabled(true);
				} else {
					tfEtAlFormat.setEnabled(false);
					setFormat(4, "false");
				}

				final int approve = JOptionPane.showConfirmDialog(CustomizeGui.contentPane, "Do you want to apply these\nchanges to all entry types?", "Apply for all?", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (approve == 0) {
				}

				Log.log("CustomizeGUI: useEtAl changed! et al:" + chckbxUseEtAl.isSelected() + ", string: " + settings[4] + ", from: " + tfEtAlNumber.getText());

				// setPreview(format);
			}

		};

		tfAuthorsFormat.setText(settings[0]);
		tfAuthorsSeparator.setText(settings[1]);
		chckbxAbbreviateNames.setSelected(Boolean.parseBoolean(settings[2]));
		chckbxUseEtAl.setSelected(Boolean.parseBoolean(settings[3]));
		tfEtAlFormat.setText(settings[4]);
		tfEtAlNumber.setText(settings[5]);
		tfEtAlThenShow.setText(settings[6]);

		setPreview(((tabbedPanel) tabbedPane.getComponent(0)).format);
		setIcons();

	}

	private String[] buildFormats() {
		final String[] newFormats = new String[14];

		newFormats[0] = ((tabbedPanel) tabbedPane.getComponentAt(0)).getFormat();
		newFormats[1] = ((tabbedPanel) tabbedPane.getComponentAt(1)).getFormat();
		newFormats[2] = ((tabbedPanel) tabbedPane.getComponentAt(3)).getFormat();
		newFormats[3] = ((tabbedPanel) tabbedPane.getComponentAt(4)).getFormat();
		newFormats[4] = ((tabbedPanel) tabbedPane.getComponentAt(2)).getFormat();
		newFormats[5] = ((tabbedPanel) tabbedPane.getComponentAt(5)).getFormat();
		newFormats[6] = ((tabbedPanel) tabbedPane.getComponentAt(10)).getFormat();
		newFormats[7] = ((tabbedPanel) tabbedPane.getComponentAt(6)).getFormat();
		newFormats[8] = ((tabbedPanel) tabbedPane.getComponentAt(7)).getFormat();
		newFormats[9] = ((tabbedPanel) tabbedPane.getComponentAt(13)).getFormat();
		newFormats[10] = ((tabbedPanel) tabbedPane.getComponentAt(8)).getFormat();
		newFormats[11] = ((tabbedPanel) tabbedPane.getComponentAt(9)).getFormat();
		newFormats[12] = ((tabbedPanel) tabbedPane.getComponentAt(11)).getFormat();
		newFormats[13] = ((tabbedPanel) tabbedPane.getComponentAt(12)).getFormat();

		return newFormats;
	}

	private String[] buildSettings() {

		return getSettings();
	}

	private String cleanTags(String text, String tag) {
		String result = text;
		result = result.replaceAll("<" + tag + "></" + tag + ">", ""); // empty tags
		result = result.replaceAll("</" + tag + "><" + tag + ">", ""); // short tags
		result = result.replaceAll("<" + tag + "><" + tag + ">", "<" + tag + ">"); // double open tags
		result = result.replaceAll("</" + tag + "></" + tag + ">", "</" + tag + ">"); // double close tags

		final Pattern p = Pattern.compile("<(\\S+?)>(<\\S?>)</\\1>");
		final Matcher m = p.matcher(result);

		while (m.find()) {
			result = m.replaceAll(m.group(2));
		}

		final Pattern p2 = Pattern.compile("<(\\S+?)>(</\\S?>)</\\1>");
		final Matcher m2 = p2.matcher(result);

		while (m2.find()) {
			result = m2.replaceAll(m2.group(2));
		}

		final Pattern p3 = Pattern.compile("<(\\S+?)>(<\\S?><\\S?>)</\\1>");
		final Matcher m3 = p3.matcher(result);

		while (m3.find()) {
			result = m3.replaceAll(m3.group(2));
		}

		final Pattern p4 = Pattern.compile("<(\\S+?)>(</\\S?></\\S?>)</\\1>");
		final Matcher m4 = p4.matcher(result);

		while (m4.find()) {
			result = m4.replaceAll(m4.group(2));
		}

		return result;
	}

	private ArrayList<int[]> getTagsList(String text, String tag) {
		final ArrayList<int[]> tagRanges = new ArrayList<int[]>();
		final Pattern p = Pattern.compile("<(" + tag + ")>(.*?)</\\1>");
		final Matcher m = p.matcher(text);

		while (m.find()) {
			final int[] i = { m.start(2), m.end(2) };
			tagRanges.add(i);
		}
		return tagRanges;

	}

	private void initTable() {
		table = new JTable();
		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				((tabbedPanel) tabbedPane.getSelectedComponent()).updatePreview();
			}
		});

		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				((tabbedPanel) tabbedPane.getSelectedComponent()).updatePreview();

			}
		});
		table.setShowVerticalLines(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] { { null, "address", null }, { null, "author", null }, { null, "booktitle", null }, { null, "chapter", null }, { null, "doi", null },
				{ null, "edition", null }, { null, "editor", null }, { null, "howpublished", null }, { null, "index", null }, { null, "institution", null }, { null, "isbn", null },
				{ null, "journal", null }, { null, "location", null }, { null, "month", null }, { null, "note", null }, { null, "number", null }, { null, "organization", null },
				{ null, "pages", null }, { null, "publisher", null }, { null, "school", null }, { null, "series", null }, { null, "title", null }, { null, "type", null }, { null, "url", null },
				{ null, "volume", null }, { null, "year", null }, }, new String[] { "PreFix", "Attribute", "PostFix" }) {
			boolean[] columnEditables = new boolean[] { true, false, true };

			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, String.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		table.setValueAt(getPrePost()[0][0], 0, 0);
		table.setValueAt(getPrePost()[0][1], 0, 2);

		table.setValueAt(getPrePost()[1][0], 1, 0);
		table.setValueAt(getPrePost()[1][1], 1, 2);

		table.setValueAt(getPrePost()[2][0], 2, 0);
		table.setValueAt(getPrePost()[2][1], 2, 2);

		table.setValueAt(getPrePost()[3][0], 3, 0);
		table.setValueAt(getPrePost()[3][1], 3, 2);

		table.setValueAt(getPrePost()[24][0], 4, 0);
		table.setValueAt(getPrePost()[24][1], 4, 2);

		table.setValueAt(getPrePost()[4][0], 5, 0);
		table.setValueAt(getPrePost()[4][1], 5, 2);

		table.setValueAt(getPrePost()[5][0], 6, 0);
		table.setValueAt(getPrePost()[5][1], 6, 2);

		table.setValueAt(getPrePost()[6][0], 7, 0);
		table.setValueAt(getPrePost()[6][1], 7, 2);

		table.setValueAt(getPrePost()[22][0], 8, 0);
		table.setValueAt(getPrePost()[22][1], 8, 2);

		table.setValueAt(getPrePost()[7][0], 9, 0);
		table.setValueAt(getPrePost()[7][1], 9, 2);

		table.setValueAt(getPrePost()[25][0], 10, 0);
		table.setValueAt(getPrePost()[25][1], 10, 2);

		table.setValueAt(getPrePost()[8][0], 11, 0);
		table.setValueAt(getPrePost()[8][1], 11, 2);

		table.setValueAt(getPrePost()[23][0], 12, 0);
		table.setValueAt(getPrePost()[23][1], 12, 2);

		table.setValueAt(getPrePost()[9][0], 13, 0);
		table.setValueAt(getPrePost()[9][1], 13, 2);

		table.setValueAt(getPrePost()[10][0], 14, 0);
		table.setValueAt(getPrePost()[10][1], 14, 2);

		table.setValueAt(getPrePost()[11][0], 15, 0);
		table.setValueAt(getPrePost()[11][1], 15, 2);

		table.setValueAt(getPrePost()[12][0], 16, 0);
		table.setValueAt(getPrePost()[12][1], 16, 2);

		table.setValueAt(getPrePost()[13][0], 17, 0);
		table.setValueAt(getPrePost()[13][1], 17, 2);

		table.setValueAt(getPrePost()[14][0], 18, 0);
		table.setValueAt(getPrePost()[14][1], 18, 2);

		table.setValueAt(getPrePost()[15][0], 19, 0);
		table.setValueAt(getPrePost()[15][1], 19, 2);

		table.setValueAt(getPrePost()[16][0], 20, 0);
		table.setValueAt(getPrePost()[16][1], 20, 2);

		table.setValueAt(getPrePost()[17][0], 21, 0);
		table.setValueAt(getPrePost()[17][1], 21, 2);

		table.setValueAt(getPrePost()[18][0], 22, 0);
		table.setValueAt(getPrePost()[18][1], 22, 2);

		table.setValueAt(getPrePost()[19][0], 23, 0);
		table.setValueAt(getPrePost()[19][1], 23, 2);

		table.setValueAt(getPrePost()[20][0], 24, 0);
		table.setValueAt(getPrePost()[20][1], 24, 2);

		table.setValueAt(getPrePost()[21][0], 25, 0);
		table.setValueAt(getPrePost()[21][1], 25, 2);

	}

	private String selectionInTag(String text, int from, int to, String tag) {
		Boolean tagged = false;
		String result = text;
		final ArrayList<int[]> taggedStrings = getTagsList(text, tag);

		for (int i = 0; i < taggedStrings.size(); i++)
			if ((taggedStrings.get(i)[0] == from) && (taggedStrings.get(i)[1] == to)) {
				result = text.substring(0, from - 3) + text.substring(from, to) + text.substring(to + 4);
				tagged = true;
			} else if ((taggedStrings.get(i)[0] <= from) & (taggedStrings.get(i)[1] >= to)) {
				result = text.substring(0, from) + "</" + tag + ">" + text.substring(from, to) + "<" + tag + ">" + text.substring(to);
				tagged = true;

			}
		if (!tagged) {
			result = text.substring(0, from) + "<" + tag + ">" + text.substring(from, to) + "</" + tag + ">" + text.substring(to);
		}

		return cleanTags(result, tag);
	}

	private void selectWholeWord(TextField pane) {
		final String text = pane.getText();
		final int caret = pane.getCaretPosition();

		String preText;
		String postText;

		if (caret >= 1) {
			preText = pane.getText().substring(caret - 1, caret);
		} else {
			preText = " ";
		}

		if (caret == text.length()) {
			postText = " ";
		} else {
			postText = text.substring(caret, caret + 1);
		}

		int start = caret;
		int end = caret;

		if ((preText != " ") | (postText != " ")) {
			try {
				for (int s = caret; s >= 0; s--)
					if ((text.charAt(s) == ' ') | (text.charAt(s) == '}')) {
						break;
					} else if (text.charAt(s) == '{') {
						start = s;
						break;
					}

				for (int e = caret; e < text.length(); e++)
					if ((text.charAt(e) == ' ') | (text.charAt(e) == '{')) {
						break;
					} else if (text.charAt(e) == '}') {
						end = e + 1;
						break;
					}
			} catch (final IndexOutOfBoundsException e) {

			}
		}
		pane.select(start, end);
	}

	protected void exportFormat() {
		// TODO Auto-generated method stub

		final String[] f = buildFormats();
		final String[][] p = buildPrePost();
		final String[] s = buildSettings();
		final String[] n = new String[] { shortName, longName };

		Converter.exportFormat(f, s, p, n);

		Converter.setStatus("Format sucessfully exported.");

	}

	protected void format(String tag, TextField pane) {// TODO

		final int start = pane.getSelectionStart();
		final int end = pane.getSelectionEnd();

		final String text = pane.getText();

		pane.setText(selectionInTag(text, start, end, tag));

	}

	protected void importFormat() {// TODO

		Converter.importFormat();

		((tabbedPanel) tabbedPane.getComponentAt(0)).format = CustomizeGui.getFormat()[0];
		((tabbedPanel) tabbedPane.getComponentAt(1)).format = CustomizeGui.getFormat()[1];
		((tabbedPanel) tabbedPane.getComponentAt(2)).format = CustomizeGui.getFormat()[4];
		((tabbedPanel) tabbedPane.getComponentAt(3)).format = CustomizeGui.getFormat()[2];
		((tabbedPanel) tabbedPane.getComponentAt(4)).format = CustomizeGui.getFormat()[3];
		((tabbedPanel) tabbedPane.getComponentAt(5)).format = CustomizeGui.getFormat()[5];
		((tabbedPanel) tabbedPane.getComponentAt(6)).format = CustomizeGui.getFormat()[7];
		((tabbedPanel) tabbedPane.getComponentAt(7)).format = CustomizeGui.getFormat()[8];
		((tabbedPanel) tabbedPane.getComponentAt(8)).format = CustomizeGui.getFormat()[10];
		((tabbedPanel) tabbedPane.getComponentAt(9)).format = CustomizeGui.getFormat()[11];
		((tabbedPanel) tabbedPane.getComponentAt(10)).format = CustomizeGui.getFormat()[6];
		((tabbedPanel) tabbedPane.getComponentAt(11)).format = CustomizeGui.getFormat()[12];
		((tabbedPanel) tabbedPane.getComponentAt(12)).format = CustomizeGui.getFormat()[13];
		((tabbedPanel) tabbedPane.getComponentAt(13)).format = CustomizeGui.getFormat()[9];

		table.setValueAt(getPrePost()[0][0], 0, 0);
		table.setValueAt(getPrePost()[0][1], 0, 2);

		table.setValueAt(getPrePost()[1][0], 1, 0);
		table.setValueAt(getPrePost()[1][1], 1, 2);

		table.setValueAt(getPrePost()[2][0], 2, 0);
		table.setValueAt(getPrePost()[2][1], 2, 2);

		table.setValueAt(getPrePost()[3][0], 3, 0);
		table.setValueAt(getPrePost()[3][1], 3, 2);

		table.setValueAt(getPrePost()[24][0], 4, 0);
		table.setValueAt(getPrePost()[24][1], 4, 2);

		table.setValueAt(getPrePost()[4][0], 5, 0);
		table.setValueAt(getPrePost()[4][1], 5, 2);

		table.setValueAt(getPrePost()[5][0], 6, 0);
		table.setValueAt(getPrePost()[5][1], 6, 2);

		table.setValueAt(getPrePost()[6][0], 7, 0);
		table.setValueAt(getPrePost()[6][1], 7, 2);

		table.setValueAt(getPrePost()[22][0], 8, 0);
		table.setValueAt(getPrePost()[22][1], 8, 2);

		table.setValueAt(getPrePost()[7][0], 9, 0);
		table.setValueAt(getPrePost()[7][1], 9, 2);

		table.setValueAt(getPrePost()[25][0], 10, 0);
		table.setValueAt(getPrePost()[25][1], 10, 2);

		table.setValueAt(getPrePost()[8][0], 11, 0);
		table.setValueAt(getPrePost()[8][1], 11, 2);

		table.setValueAt(getPrePost()[23][0], 12, 0);
		table.setValueAt(getPrePost()[23][1], 12, 2);

		table.setValueAt(getPrePost()[9][0], 13, 0);
		table.setValueAt(getPrePost()[9][1], 13, 2);

		table.setValueAt(getPrePost()[10][0], 14, 0);
		table.setValueAt(getPrePost()[10][1], 14, 2);

		table.setValueAt(getPrePost()[11][0], 15, 0);
		table.setValueAt(getPrePost()[11][1], 15, 2);

		table.setValueAt(getPrePost()[12][0], 16, 0);
		table.setValueAt(getPrePost()[12][1], 16, 2);

		table.setValueAt(getPrePost()[13][0], 17, 0);
		table.setValueAt(getPrePost()[13][1], 17, 2);

		table.setValueAt(getPrePost()[14][0], 18, 0);
		table.setValueAt(getPrePost()[14][1], 18, 2);

		table.setValueAt(getPrePost()[15][0], 19, 0);
		table.setValueAt(getPrePost()[15][1], 19, 2);

		table.setValueAt(getPrePost()[16][0], 20, 0);
		table.setValueAt(getPrePost()[16][1], 20, 2);

		table.setValueAt(getPrePost()[17][0], 21, 0);
		table.setValueAt(getPrePost()[17][1], 21, 2);

		table.setValueAt(getPrePost()[18][0], 22, 0);
		table.setValueAt(getPrePost()[18][1], 22, 2);

		table.setValueAt(getPrePost()[19][0], 23, 0);
		table.setValueAt(getPrePost()[19][1], 23, 2);

		table.setValueAt(getPrePost()[20][0], 24, 0);
		table.setValueAt(getPrePost()[20][1], 24, 2);

		table.setValueAt(getPrePost()[21][0], 25, 0);
		table.setValueAt(getPrePost()[21][1], 25, 2);

		for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
			((tabbedPanel) tabbedPane.getComponentAt(i)).init();
		}
		Converter.setStatus("Format sucessfully imported.");
	}

	protected void save() {

		final String[] newFormats = buildFormats();
		final String[] newSettings = buildSettings();

		newFormats[0] = ((tabbedPanel) tabbedPane.getComponentAt(0)).getFormat();
		newFormats[1] = ((tabbedPanel) tabbedPane.getComponentAt(1)).getFormat();
		newFormats[2] = ((tabbedPanel) tabbedPane.getComponentAt(3)).getFormat();
		newFormats[3] = ((tabbedPanel) tabbedPane.getComponentAt(4)).getFormat();
		newFormats[4] = ((tabbedPanel) tabbedPane.getComponentAt(2)).getFormat();
		newFormats[5] = ((tabbedPanel) tabbedPane.getComponentAt(5)).getFormat();
		newFormats[6] = ((tabbedPanel) tabbedPane.getComponentAt(10)).getFormat();
		newFormats[7] = ((tabbedPanel) tabbedPane.getComponentAt(6)).getFormat();
		newFormats[8] = ((tabbedPanel) tabbedPane.getComponentAt(7)).getFormat();
		newFormats[9] = ((tabbedPanel) tabbedPane.getComponentAt(13)).getFormat();
		newFormats[10] = ((tabbedPanel) tabbedPane.getComponentAt(8)).getFormat();
		newFormats[11] = ((tabbedPanel) tabbedPane.getComponentAt(9)).getFormat();
		newFormats[12] = ((tabbedPanel) tabbedPane.getComponentAt(11)).getFormat();
		newFormats[13] = ((tabbedPanel) tabbedPane.getComponentAt(12)).getFormat();

		final String[] newName = new String[] { shortNamePane.getText(), namePane.getText() };

		final int index = GUI.cbbCitationFormat.getSelectedIndex();

		if (operationMode == EDIT) {

			Settings.patternFormats.set(index, newFormats);
			Settings.patternNames.set(index, newName);
			Settings.patternPrePost.set(index, buildPrePost());
			Settings.patternSettings.set(index, newSettings);
			GUI.updateCbbFormats();
			GUI.cbbCitationFormat.setSelectedIndex(index);

		} else if ((operationMode == NEW) | (operationMode == COPY)) {
			Settings.patternFormats.add(newFormats);
			Settings.patternNames.add(newName);
			Settings.patternPrePost.add(buildPrePost());
			Settings.patternSettings.add(newSettings);
			GUI.updateCbbFormats();
			GUI.cbbCitationFormat.setSelectedIndex(Settings.patternFormats.size() - 1);
		}

		Converter.setStatus("Saved.");
	}

	protected void setFormat(int i, String value) {

		getSettings()[i] = value;
	}
}
