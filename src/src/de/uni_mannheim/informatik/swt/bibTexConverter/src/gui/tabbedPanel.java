package de.uni_mannheim.informatik.swt.bibTexConverter.src.gui;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;

@SuppressWarnings("serial")
public class tabbedPanel extends JPanel {

	public static String lastEtAl;
	public String format = "";
	public Boolean patternSet = false;
	public TextField tfPattern = new TextField();
	private final JButton btnApplyForAll = new JButton("Apply for all");
	private final JPanel FormatButtonsPanel = new JPanel();

	protected String stringSeparator = "";
	final String[] basicFormat = null;
	final String BOLD = "b";
	JToggleButton btnAddress = new JToggleButton("Address");
	JToggleButton btnAuthor = new JToggleButton("Author");
	JToggleButton btnB = new JToggleButton("b");
	JToggleButton btnBooktitle = new JToggleButton("Booktitle");
	JToggleButton btnChapter = new JToggleButton("Chapter");
	JToggleButton btnEdition = new JToggleButton("Edition");
	JToggleButton btnEditor = new JToggleButton("Editor");
	JToggleButton btnHowpublished = new JToggleButton("HowPublished");
	JToggleButton btnI = new JToggleButton("i");
	JToggleButton btnIndex = new JToggleButton("Index");
	JToggleButton btnInstitution = new JToggleButton("Institution");
	JToggleButton btnJournal = new JToggleButton("Journal");
	JToggleButton btnMonth = new JToggleButton("Month");
	JToggleButton btnNote = new JToggleButton("Note");
	JToggleButton btnNumber = new JToggleButton("Number");
	JToggleButton btnOrganization = new JToggleButton("Organization");
	JToggleButton btnPages = new JToggleButton("Pages");
	JToggleButton btnPublisher = new JToggleButton("Publisher");
	JToggleButton btnSchool = new JToggleButton("School");
	JToggleButton btnSeries = new JToggleButton("Series");
	JToggleButton btnTitle = new JToggleButton("Title");
	JToggleButton btnType = new JToggleButton("Type");

	JToggleButton btnUrl = new JToggleButton("URL");

	JToggleButton btnVolume = new JToggleButton("Volume");

	JToggleButton btnX = new JToggleButton("X");

	JToggleButton btnYear = new JToggleButton("Year");

	JCheckBox chckbxSeperateValuesWith = new JCheckBox("seperate with:");

	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();
	final String ITALIC = "i";

	/**
	 * Create the tabbedPanel.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected tabbedPanel(String formats, String type) {
		setBounds(new Rectangle(0, 0, 772, 0));
		format = formats;
		tfPattern.setText(format);

		setLayout(null);

		final JPanel PatternPanel = new JPanel();
		PatternPanel.setBounds(10, 11, 696, 92);
		add(PatternPanel);
		PatternPanel.setLayout(null);
		PatternPanel.setBorder(new TitledBorder(null, "Pattern", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tfPattern.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		tfPattern.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {
				updatePatternButtons();
				setFormat(0, tfPattern.getText());
				patternSet = (!tfPattern.getText().isEmpty());
				updatePreview();

			}
		});

		tfPattern.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// selectWholeWord(tfPattern);
			}

			@Override
			public void mouseReleased(MouseEvent m) {
				selectWholeWord(tfPattern);

			}
		});

		tfPattern.setBounds(10, 14, 675, 20);
		PatternPanel.add(tfPattern);

		final JPanel ValueSeparatorPanel = new JPanel();
		ValueSeparatorPanel.setVisible(false);
		ValueSeparatorPanel.setBounds(342, 51, 180, 23);
		PatternPanel.add(ValueSeparatorPanel);
		ValueSeparatorPanel.setLayout(null);

		chckbxSeperateValuesWith.setBounds(0, 0, 104, 23);
		ValueSeparatorPanel.add(chckbxSeperateValuesWith);
		chckbxSeperateValuesWith.setSelected(true);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { " ", ", ", ". " }));

		comboBox.setBounds(110, 1, 66, 20);
		ValueSeparatorPanel.add(comboBox);
		FormatButtonsPanel.setBounds(10, 51, 153, 23);
		PatternPanel.add(FormatButtonsPanel);
		FormatButtonsPanel.setLayout(null);
		btnB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				format(BOLD, tfPattern);
			}
		});
		btnB.setBounds(0, 0, 50, 23);
		FormatButtonsPanel.add(btnB);
		btnB.setToolTipText("bold");
		btnI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				format(ITALIC, tfPattern);
			}
		});
		btnI.setBounds(51, 0, 50, 23);
		FormatButtonsPanel.add(btnI);
		btnI.setToolTipText("italic");
		btnX.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				format("X", tfPattern);
			}
		});
		btnX.setBounds(102, 0, 50, 23);
		FormatButtonsPanel.add(btnX);
		btnX.setToolTipText("to Upper Case");

		final JPanel panel = new JPanel();
		panel.setBounds(191, 51, 141, 23);
		PatternPanel.add(panel);
		panel.setLayout(null);

		final JButton btnClear = new JButton("clear");
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tfPattern.setText("");
				updatePatternButtons();
			}
		});
		btnClear.setBounds(0, 0, 57, 23);
		panel.add(btnClear);

		final JButton btnCopy = new JButton("copy");
		btnCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tfPattern.selectAll();

				final JEditorPane tmp = new JEditorPane();
				tmp.setText(tfPattern.getText());

				tmp.copy();
			}
		});
		btnCopy.setBounds(57, 0, 57, 23);
		panel.add(btnCopy);
		btnApplyForAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				applyForAll();
			}
		});
		btnApplyForAll.setToolTipText("apply for all types");
		btnApplyForAll.setBounds(600, 69, 96, 23);
		PatternPanel.add(btnApplyForAll);

		final JPanel AttributePanel = new JPanel();
		AttributePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		AttributePanel.setBorder(new TitledBorder(null, "Attributes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		AttributePanel.setName("Attributes");
		AttributePanel.setBounds(10, 104, 696, 100);
		add(AttributePanel);
		final FlowLayout fl_AttributePanel = new FlowLayout(FlowLayout.LEADING, 0, 0);
		fl_AttributePanel.setAlignOnBaseline(true);
		AttributePanel.setLayout(fl_AttributePanel);
		btnIndex.setVisible(false);
		btnIndex.setToolTipText("{index}");
		btnIndex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggleAttributes(btnIndex);
			}
		});

		AttributePanel.add(btnIndex);
		btnAddress.setToolTipText("{address}");
		btnAddress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggleAttributes(btnAddress);
			}
		});
		AttributePanel.add(btnAddress);
		btnAuthor.setToolTipText("{author}");
		btnAuthor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggleAttributes(btnAuthor);
			}
		});
		AttributePanel.add(btnAuthor);
		btnBooktitle.setToolTipText("{booktitle}");
		btnBooktitle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnBooktitle);
			}
		});
		AttributePanel.add(btnBooktitle);
		btnChapter.setToolTipText("{chapter}");
		btnChapter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnChapter);
			}
		});

		AttributePanel.add(btnChapter);
		btnEdition.setToolTipText("{edition}");
		btnEdition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnEdition);
			}
		});
		AttributePanel.add(btnEdition);
		btnEditor.setToolTipText("{editor}");
		btnEditor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnEditor);
			}
		});
		AttributePanel.add(btnEditor);
		btnHowpublished.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnHowpublished);
			}
		});
		btnHowpublished.setToolTipText("{howpublished}");

		AttributePanel.add(btnHowpublished);
		btnInstitution.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnInstitution);
			}
		});
		btnInstitution.setToolTipText("{institution}");
		AttributePanel.add(btnInstitution);
		btnJournal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnJournal);
			}
		});
		btnJournal.setToolTipText("{journal}");
		AttributePanel.add(btnJournal);
		btnMonth.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnMonth);
			}
		});
		btnMonth.setToolTipText("{month}");
		AttributePanel.add(btnMonth);
		btnNote.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnNote);
			}
		});
		btnNote.setToolTipText("{note}");
		AttributePanel.add(btnNote);
		btnNumber.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnNumber);
			}
		});
		btnNumber.setToolTipText("{number}");
		AttributePanel.add(btnNumber);
		btnOrganization.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnOrganization);
			}
		});
		btnOrganization.setToolTipText("{organization}");
		AttributePanel.add(btnOrganization);
		btnPages.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnPages);
			}
		});
		btnPages.setToolTipText("{pages}");
		AttributePanel.add(btnPages);
		btnPublisher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnPublisher);
			}
		});
		btnPublisher.setToolTipText("{publisher}");
		AttributePanel.add(btnPublisher);
		btnSchool.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnSchool);
			}
		});
		btnSchool.setToolTipText("{school}");
		AttributePanel.add(btnSchool);
		btnSeries.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnSeries);
			}
		});
		btnSeries.setToolTipText("{series}");
		AttributePanel.add(btnSeries);
		btnTitle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnTitle);
			}
		});
		btnTitle.setToolTipText("{title}");
		AttributePanel.add(btnTitle);
		btnType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnType);
			}
		});
		btnType.setToolTipText("{type}");
		AttributePanel.add(btnType);
		btnUrl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnUrl);
			}
		});
		btnUrl.setToolTipText("{url}");
		AttributePanel.add(btnUrl);
		btnVolume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnVolume);
			}
		});
		btnVolume.setToolTipText("{volume}");
		AttributePanel.add(btnVolume);
		btnYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAttributes(btnYear);
			}
		});
		btnYear.setToolTipText("{year}");
		AttributePanel.add(btnYear);

		init();
	}

	private void applyForAll() {
		Log.log("CUSTOMIMZE_GUI.applyForAll: set format  to " + format);

		CustomizeGui.applyForAll(format);

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

	private void insertIntoPattern(String string) {
		int caretPosition = tfPattern.getCaretPosition();
		String separator = "";
		if (chckbxSeperateValuesWith.isSelected()) {
			separator = (String) comboBox.getSelectedItem();
		}

		if (caretPosition == 0) {
			caretPosition = tfPattern.getText().length();
		}

		separator = "";
		tfPattern.setText(tfPattern.getText().substring(0, caretPosition) + string + separator + tfPattern.getText().substring(caretPosition, tfPattern.getText().length()));
		updatePatternButtons();
	}

	private Boolean isTagged(String tag) {
		Boolean tagged = false;

		final ArrayList<int[]> taggedStrings = getTagsList(tfPattern.getText(), tag);

		for (int i = 0; i < taggedStrings.size(); i++)
			if ((taggedStrings.get(i)[0] <= tfPattern.getSelectionStart()) && (taggedStrings.get(i)[1] >= tfPattern.getSelectionEnd())) {
				tagged = true;
			}

		return tagged;
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

	private void toggleAttributes(JToggleButton button) {
		String text = tfPattern.getText();
		if ((button != null) & (text != null)) if (text.contains(button.getToolTipText())) {
			text = text.replace(button.getToolTipText(), "");
			button.setSelected(false);
			tfPattern.setText(text);
		} else {
			insertIntoPattern(button.getToolTipText());
			button.setSelected(true);
		}
	}

	private void updatePatternButtons() {
		btnB.setSelected(isTagged(BOLD));
		btnI.setSelected(isTagged(ITALIC));
		btnX.setSelected(isTagged("X"));

		final String text = tfPattern.getText();

		btnIndex.setSelected(text.contains("{index}"));
		btnAddress.setSelected(text.contains("{address}"));
		btnAuthor.setSelected(text.contains("{author}"));
		btnBooktitle.setSelected(text.contains("{booktitle}"));
		btnChapter.setSelected(text.contains("{chapter}"));
		btnEdition.setSelected(text.contains("{edition}"));
		btnEditor.setSelected(text.contains("{editor}"));
		btnHowpublished.setSelected(text.contains("{howpublished}"));
		btnInstitution.setSelected(text.contains("{institution}"));
		btnJournal.setSelected(text.contains("{journal}"));
		btnMonth.setSelected(text.contains("{month}"));
		btnNote.setSelected(text.contains("{note}"));
		btnNumber.setSelected(text.contains("{number}"));
		btnOrganization.setSelected(text.contains("{organization}"));
		btnPages.setSelected(text.contains("{pages}"));
		btnPublisher.setSelected(text.contains("{publisher}"));
		btnSchool.setSelected(text.contains("{school}"));
		btnSeries.setSelected(text.contains("{series}"));
		btnTitle.setSelected(text.contains("{title}"));
		btnType.setSelected(text.contains("{citationType}"));
		btnUrl.setSelected(text.contains("{url}"));
		btnVolume.setSelected(text.contains("{volume}"));
		btnYear.setSelected(text.contains("{year}"));
		btnType.setSelected(text.contains("{type}"));

	}

	private void updatePatternSet() {
		patternSet = (!tfPattern.getText().isEmpty());
	}

	protected void addToTemplate() {

		if (chckbxSeperateValuesWith.isSelected()) {

			// tfPattern.setText(tfPattern.getText().substring(0, tfPattern.getSelectionStart()) + list.getSelectedValue() + tfPattern.getText().substring(tfPattern.getSelectionEnd(),
			// tfPattern.getText().length()) + stringSeparator);
		}
	}

	protected void format(String tag, TextField pane) {// TODO

		final int start = pane.getSelectionStart();
		final int end = pane.getSelectionEnd();

		final String text = pane.getText();

		pane.setText(selectionInTag(text, start, end, tag));

	}

	protected String getFormat() {

		final String newFormat = new String(tfPattern.getText());

		return newFormat;
	}

	protected void init() {
		tfPattern.setText(format);

		updatePatternButtons();
		updatePatternSet();

	}

	protected void setFormat(int i, String value) {
		format = value;
	}

	protected void updatePreview() {
		format = tfPattern.getText();

		CustomizeGui.setPreview(format);
		CustomizeGui.setIcons();
	}
}
