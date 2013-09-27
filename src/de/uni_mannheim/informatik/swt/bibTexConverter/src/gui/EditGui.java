package de.uni_mannheim.informatik.swt.bibTexConverter.src.gui;

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.Citation;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.Converter;

@SuppressWarnings("serial")
public class EditGui extends JFrame {

	private final JButton btnCancel;
	private final JButton btnSave;

	private final JPanel contentPane;
	private Citation parentCitation = null;
	private final JTextField tfAddress;
	private JTable tfAuthors;
	private final JScrollPane tfAuthorSP;
	private final JTextField tfBooktitle;
	private final JTextField tfChapter;
	private final JTextField tfDoi;
	private final JTextField tfEdition;
	private JTable tfEditors;
	private final JScrollPane tfEditorSP;
	private final JTextField tfHowpublished;
	private final JTextField tfInstitution;
	private final JTextField tfISBN;
	private final JTextField tfISSN;
	private final JTextField tfJournal;
	private final JTextField tfLocation;
	private final JTextField tfMonth;
	private final JTextField tfNote;
	private final JTextField tfNumber;
	private final JTextField tfOrganization;
	private final JTextField tfPages;
	private final JTextField tfPublisher;
	private final JTextField tfSchool;
	private final JTextField tfSeries;
	private final JTextField tfTitle;
	private final JTextField tfType;
	private final JTextField tfURL;
	private final JTextField tfVolume;
	private final JTextField tfYear;
	private final JTextField txtAddress;
	private final JTextField txtAuthors;
	private final JTextField txtBooktitle;
	private final JTextField txtChapter;
	private final JTextField txtDoi;
	private final JTextField txtEdition;
	private final JTextField txtEditor;
	private final JTextField txtHowpublished;
	private final JTextField txtInstitution;
	private final JTextField txtIsbn;
	private final JTextField txtIssn;
	private final JTextField txtJournal;
	private final JTextField txtLocation;
	private final JTextField txtMonth;
	private final JTextField txtNote;
	private final JTextField txtNumber;
	private final JTextField txtOrganization;
	private final JTextField txtPages;
	private final JTextField txtPublisher;
	private final JTextField txtSchool;
	private final JTextField txtSeries;
	private final JTextField txtTitle;
	private final JTextField txtType;
	private final JTextField txtUrl;
	private final JTextField txtVolume;
	private final JTextField txtYear;

	/**
	 * Create the frame.
	 */
	public EditGui(Citation citation) {
		setResizable(false);
		setAlwaysOnTop(true);
		parentCitation = citation;
		setTitle("BibTexConverter - Citation Editor (" + parentCitation.citationType + ")");
		setDefaultCloseOperation(getDefaultCloseOperation());

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(637, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		final JPanel panel = new JPanel();
		panel.setBounds(0, 11, 629, 681);
		contentPane.add(panel);
		panel.setBorder(null);
		panel.setLayout(null);

		txtTitle = new JTextField();
		txtTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTitle.setBorder(null);
		txtTitle.setEditable(false);
		txtTitle.setText("Title:");
		txtTitle.setBounds(0, 0, 88, 34);
		panel.add(txtTitle);
		txtTitle.setColumns(10);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(95, 0, 510, 34);
		panel.add(scrollPane);

		tfTitle = new JTextField();
		tfTitle.setAutoscrolls(false);
		scrollPane.setViewportView(tfTitle);
		tfTitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfTitle.setBorder(null);
		tfTitle.setColumns(10);

		txtAuthors = new JTextField();
		txtAuthors.setHorizontalAlignment(SwingConstants.RIGHT);
		txtAuthors.setBorder(null);
		txtAuthors.setEditable(false);
		txtAuthors.setText("Authors:");
		txtAuthors.setBounds(0, 37, 88, 76);
		panel.add(txtAuthors);
		txtAuthors.setColumns(10);

		txtYear = new JTextField();
		txtYear.setHorizontalAlignment(SwingConstants.RIGHT);
		txtYear.setBorder(null);
		txtYear.setEditable(false);
		txtYear.setText("Year:");
		txtYear.setBounds(0, 118, 88, 20);
		panel.add(txtYear);
		txtYear.setColumns(10);

		tfAuthorSP = new JScrollPane();
		tfAuthorSP.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		tfAuthorSP.setBounds(95, 37, 510, 76);
		panel.add(tfAuthorSP);

		tfAuthors = new JTable();
		tfAuthorSP.setViewportView(tfAuthors);

		tfSchool = new JTextField();
		tfSchool.setBorder(null);
		tfSchool.setBounds(95, 489, 510, 20);
		panel.add(tfSchool);
		tfSchool.setColumns(10);

		txtSchool = new JTextField();
		txtSchool.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSchool.setBorder(null);
		txtSchool.setEditable(false);
		txtSchool.setText("School:");
		txtSchool.setBounds(0, 489, 88, 20);
		panel.add(txtSchool);
		txtSchool.setColumns(10);

		txtChapter = new JTextField();
		txtChapter.setHorizontalAlignment(SwingConstants.RIGHT);
		txtChapter.setBorder(null);
		txtChapter.setEditable(false);
		txtChapter.setText("Chapter:");
		txtChapter.setBounds(0, 265, 88, 20);
		panel.add(txtChapter);
		txtChapter.setColumns(10);

		txtJournal = new JTextField();
		txtJournal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtJournal.setBorder(null);
		txtJournal.setEditable(false);
		txtJournal.setText("Journal:");
		txtJournal.setBounds(0, 160, 88, 20);
		panel.add(txtJournal);
		txtJournal.setColumns(10);

		txtPages = new JTextField();
		txtPages.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPages.setBorder(null);
		txtPages.setEditable(false);
		txtPages.setText("Pages:");
		txtPages.setBounds(0, 286, 88, 20);
		panel.add(txtPages);
		txtPages.setColumns(10);

		txtEdition = new JTextField();
		txtEdition.setHorizontalAlignment(SwingConstants.RIGHT);
		txtEdition.setBorder(null);
		txtEdition.setEditable(false);
		txtEdition.setText("Edition:");
		txtEdition.setBounds(0, 307, 88, 20);
		panel.add(txtEdition);
		txtEdition.setColumns(10);

		tfChapter = new JTextField();
		tfChapter.setBorder(null);
		tfChapter.setBounds(95, 265, 510, 20);
		panel.add(tfChapter);
		tfChapter.setColumns(10);

		tfJournal = new JTextField();
		tfJournal.setBorder(null);
		tfJournal.setBounds(95, 160, 510, 20);
		panel.add(tfJournal);
		tfJournal.setColumns(10);

		tfPages = new JTextField();
		tfPages.setBorder(null);
		tfPages.setBounds(95, 286, 510, 20);
		panel.add(tfPages);
		tfPages.setColumns(10);

		tfEdition = new JTextField();
		tfEdition.setBorder(null);
		tfEdition.setText("");
		tfEdition.setBounds(95, 307, 510, 20);
		panel.add(tfEdition);
		tfEdition.setColumns(10);

		txtMonth = new JTextField();
		txtMonth.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMonth.setBorder(null);
		txtMonth.setEditable(false);
		txtMonth.setText("Month:");
		txtMonth.setBounds(0, 139, 88, 20);
		panel.add(txtMonth);
		txtMonth.setColumns(10);

		txtNumber = new JTextField();
		txtNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNumber.setBorder(null);
		txtNumber.setEditable(false);
		txtNumber.setText("Number:");
		txtNumber.setBounds(0, 223, 88, 20);
		panel.add(txtNumber);
		txtNumber.setColumns(10);

		txtOrganization = new JTextField();
		txtOrganization.setHorizontalAlignment(SwingConstants.RIGHT);
		txtOrganization.setBorder(null);
		txtOrganization.setEditable(false);
		txtOrganization.setText("Organization:");
		txtOrganization.setBounds(0, 468, 88, 20);
		panel.add(txtOrganization);
		txtOrganization.setColumns(10);

		txtSeries = new JTextField();
		txtSeries.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSeries.setBorder(null);
		txtSeries.setEditable(false);
		txtSeries.setText("Series:");
		txtSeries.setBounds(0, 181, 88, 20);
		panel.add(txtSeries);
		txtSeries.setColumns(10);

		txtVolume = new JTextField();
		txtVolume.setHorizontalAlignment(SwingConstants.RIGHT);
		txtVolume.setBorder(null);
		txtVolume.setEditable(false);
		txtVolume.setText("Volume:");
		txtVolume.setBounds(0, 202, 88, 20);
		panel.add(txtVolume);
		txtVolume.setColumns(10);

		txtType = new JTextField();
		txtType.setHorizontalAlignment(SwingConstants.RIGHT);
		txtType.setBorder(null);
		txtType.setEditable(false);
		txtType.setText("Type:");
		txtType.setBounds(0, 552, 88, 20);
		panel.add(txtType);
		txtType.setColumns(10);

		txtUrl = new JTextField();
		txtUrl.setHorizontalAlignment(SwingConstants.RIGHT);
		txtUrl.setBorder(null);
		txtUrl.setEditable(false);
		txtUrl.setText("URL:");
		txtUrl.setBounds(0, 573, 88, 20);
		panel.add(txtUrl);
		txtUrl.setColumns(10);

		txtHowpublished = new JTextField();
		txtHowpublished.setHorizontalAlignment(SwingConstants.RIGHT);
		txtHowpublished.setBorder(null);
		txtHowpublished.setEditable(false);
		txtHowpublished.setText("Howpublished:");
		txtHowpublished.setBounds(0, 531, 88, 20);
		panel.add(txtHowpublished);
		txtHowpublished.setColumns(10);

		txtNote = new JTextField();
		txtNote.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNote.setBorder(null);
		txtNote.setEditable(false);
		txtNote.setText("Note:");
		txtNote.setBounds(0, 657, 88, 20);
		panel.add(txtNote);
		txtNote.setColumns(10);

		tfMonth = new JTextField();
		tfMonth.setBorder(null);
		tfMonth.setBounds(95, 139, 510, 20);
		panel.add(tfMonth);
		tfMonth.setColumns(10);

		tfEditorSP = new JScrollPane();
		tfEditorSP.setBounds(95, 328, 510, 76);
		panel.add(tfEditorSP);

		tfNumber = new JTextField();
		tfNumber.setBorder(null);
		tfNumber.setBounds(95, 223, 510, 20);
		panel.add(tfNumber);
		tfNumber.setColumns(10);

		tfOrganization = new JTextField();
		tfOrganization.setBorder(null);
		tfOrganization.setText("");
		tfOrganization.setBounds(95, 468, 510, 20);
		panel.add(tfOrganization);
		tfOrganization.setColumns(10);

		tfSeries = new JTextField();
		tfSeries.setBorder(null);
		tfSeries.setBounds(95, 181, 510, 20);
		panel.add(tfSeries);
		tfSeries.setColumns(10);

		tfVolume = new JTextField();
		tfVolume.setBorder(null);
		tfVolume.setBounds(95, 202, 510, 20);
		panel.add(tfVolume);
		tfVolume.setColumns(10);

		tfType = new JTextField();
		tfType.setBorder(null);
		tfType.setBounds(95, 552, 510, 20);
		panel.add(tfType);
		tfType.setColumns(10);

		tfURL = new JTextField();
		tfURL.setBorder(null);
		tfURL.setText("");
		tfURL.setBounds(95, 573, 510, 20);
		panel.add(tfURL);
		tfURL.setColumns(10);

		tfHowpublished = new JTextField();
		tfHowpublished.setBorder(null);
		tfHowpublished.setBounds(95, 531, 510, 20);
		panel.add(tfHowpublished);
		tfHowpublished.setColumns(10);

		tfNote = new JTextField();
		tfNote.setBorder(null);
		tfNote.setText("");
		tfNote.setBounds(95, 657, 510, 20);
		panel.add(tfNote);
		tfNote.setColumns(10);

		tfYear = new JTextField();
		tfYear.setBounds(95, 118, 510, 20);
		panel.add(tfYear);
		tfYear.setBorder(null);
		tfYear.setColumns(10);

		txtBooktitle = new JTextField();
		txtBooktitle.setHorizontalAlignment(SwingConstants.RIGHT);
		txtBooktitle.setBounds(0, 244, 88, 20);
		panel.add(txtBooktitle);
		txtBooktitle.setBorder(null);
		txtBooktitle.setEditable(false);
		txtBooktitle.setText("Booktitle:");
		txtBooktitle.setColumns(10);

		tfAddress = new JTextField();
		tfAddress.setBounds(95, 426, 510, 20);
		panel.add(tfAddress);
		tfAddress.setBorder(null);
		tfAddress.setColumns(10);

		tfBooktitle = new JTextField();
		tfBooktitle.setBounds(95, 244, 510, 20);
		panel.add(tfBooktitle);
		tfBooktitle.setBorder(null);
		tfBooktitle.setColumns(10);

		txtEditor = new JTextField();
		txtEditor.setHorizontalAlignment(SwingConstants.RIGHT);
		txtEditor.setBounds(0, 328, 88, 73);
		panel.add(txtEditor);
		txtEditor.setBorder(null);
		txtEditor.setEditable(false);
		txtEditor.setText("Editors:");
		txtEditor.setColumns(10);

		txtInstitution = new JTextField();
		txtInstitution.setHorizontalAlignment(SwingConstants.RIGHT);
		txtInstitution.setBounds(0, 510, 88, 20);
		panel.add(txtInstitution);
		txtInstitution.setBorder(null);
		txtInstitution.setEditable(false);
		txtInstitution.setText("Institution:");
		txtInstitution.setColumns(10);

		tfInstitution = new JTextField();
		tfInstitution.setBounds(95, 510, 510, 20);
		panel.add(tfInstitution);
		tfInstitution.setBorder(null);
		tfInstitution.setColumns(10);

		txtPublisher = new JTextField();
		txtPublisher.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPublisher.setBounds(0, 405, 88, 20);
		panel.add(txtPublisher);
		txtPublisher.setBorder(null);
		txtPublisher.setEditable(false);
		txtPublisher.setText("Publisher:");
		txtPublisher.setColumns(10);

		tfPublisher = new JTextField();
		tfPublisher.setBounds(96, 405, 509, 20);
		panel.add(tfPublisher);
		tfPublisher.setBorder(null);
		tfPublisher.setColumns(10);

		txtAddress = new JTextField();
		txtAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		txtAddress.setBounds(0, 426, 88, 20);
		panel.add(txtAddress);
		txtAddress.setBorder(null);
		txtAddress.setEditable(false);
		txtAddress.setText("Address:");
		txtAddress.setColumns(10);

		txtLocation = new JTextField();
		txtLocation.setText("Location:");
		txtLocation.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLocation.setEditable(false);
		txtLocation.setColumns(10);
		txtLocation.setBorder(null);
		txtLocation.setBounds(0, 447, 88, 20);
		panel.add(txtLocation);

		tfLocation = new JTextField();
		tfLocation.setText((String) null);
		tfLocation.setColumns(10);
		tfLocation.setBorder(null);
		tfLocation.setBounds(95, 447, 510, 20);
		panel.add(tfLocation);

		txtDoi = new JTextField();
		txtDoi.setText("DOI:");
		txtDoi.setHorizontalAlignment(SwingConstants.RIGHT);
		txtDoi.setEditable(false);
		txtDoi.setColumns(10);
		txtDoi.setBorder(null);
		txtDoi.setBounds(0, 594, 88, 20);
		panel.add(txtDoi);

		tfDoi = new JTextField();
		tfDoi.setText("");
		tfDoi.setColumns(10);
		tfDoi.setBorder(null);
		tfDoi.setBounds(95, 594, 510, 20);
		panel.add(tfDoi);

		txtIssn = new JTextField();
		txtIssn.setText("ISSN:");
		txtIssn.setHorizontalAlignment(SwingConstants.RIGHT);
		txtIssn.setEditable(false);
		txtIssn.setColumns(10);
		txtIssn.setBorder(null);
		txtIssn.setBounds(0, 615, 88, 20);
		panel.add(txtIssn);

		tfISSN = new JTextField();
		tfISSN.setText("");
		tfISSN.setColumns(10);
		tfISSN.setBorder(null);
		tfISSN.setBounds(95, 615, 510, 20);
		panel.add(tfISSN);

		txtIsbn = new JTextField();
		txtIsbn.setText("ISBN:");
		txtIsbn.setHorizontalAlignment(SwingConstants.RIGHT);
		txtIsbn.setEditable(false);
		txtIsbn.setColumns(10);
		txtIsbn.setBorder(null);
		txtIsbn.setBounds(0, 636, 88, 20);
		panel.add(txtIsbn);

		tfISBN = new JTextField();
		tfISBN.setText("");
		tfISBN.setColumns(10);
		tfISBN.setBorder(null);
		tfISBN.setBounds(95, 636, 510, 20);
		panel.add(tfISBN);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnSave.setBounds(423, 703, 89, 23);
		contentPane.add(btnSave);

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(522, 703, 89, 23);
		contentPane.add(btnCancel);

		final JSeparator separator = new JSeparator();
		separator.setBounds(0, 698, 629, 2);
		contentPane.add(separator);

		initialize();

	}

	private void initialize() {
		tfTitle.setText(parentCitation.title);
		tfAddress.setText(parentCitation.address);
		tfBooktitle.setText(parentCitation.booktitle);
		tfChapter.setText(parentCitation.chapter);
		tfEdition.setText(parentCitation.edition);
		tfHowpublished.setText(parentCitation.howpublished);
		tfInstitution.setText(parentCitation.institution);
		tfJournal.setText(parentCitation.journal);
		tfMonth.setText(parentCitation.month);
		tfNote.setText(parentCitation.note);
		tfNumber.setText(parentCitation.number);
		tfOrganization.setText(parentCitation.organization);
		tfPages.setText(parentCitation.pages);
		tfPublisher.setText(parentCitation.publisher);
		tfSchool.setText(parentCitation.school);
		tfSeries.setText(parentCitation.series);
		tfType.setText(parentCitation.citationType);
		tfURL.setText(parentCitation.url);
		tfVolume.setText(parentCitation.volume);
		tfYear.setText(parentCitation.year);
		tfType.setText(parentCitation.type);
		tfLocation.setText(parentCitation.location);
		tfDoi.setText(parentCitation.doi);
		tfISSN.setText(parentCitation.issn);
		tfISBN.setText(parentCitation.isbn);

		Object[][] authors = { { "", "" } };

		if ((parentCitation.authors != null) | !parentCitation.authors.isEmpty()) {
			authors = new Object[parentCitation.authors.size() + 1][2];
		}

		final String[] colums = { "First Name", "Last Name" };

		try {
			for (int i = 0; i < (authors.length - 1); i++) {
				authors[i][0] = parentCitation.authors.get(i)[0];
				authors[i][1] = parentCitation.authors.get(i)[1];

			}
			authors[authors.length - 1][0] = "";
			authors[authors.length - 1][1] = "";

		} catch (final Exception e) {
		}
		tfAuthors = new JTable(authors, colums);
		tfAuthors.setRowSelectionAllowed(false);
		tfAuthors.setToolTipText("start editing with DOUBLECLICK - acknowledge with ENTER");

		tfAuthorSP.setViewportView(tfAuthors);
		tfAuthors.validate();
		tfAuthors.setVisible(true);

		Object[][] editors = { { "", "" } };

		if ((parentCitation.editors != null) | !parentCitation.editors.isEmpty()) {
			editors = new Object[parentCitation.editors.size() + 1][2];
		}

		try {
			for (int i = 0; i < (editors.length - 1); i++) {
				editors[i][0] = parentCitation.editors.get(i)[0];
				editors[i][1] = parentCitation.editors.get(i)[1];

			}
			editors[editors.length - 1][0] = "";
			editors[editors.length - 1][1] = "";
		} catch (final Exception e) {
		}

		tfEditors = new JTable(editors, colums);
		tfEditorSP.setViewportView(tfEditors);
		tfEditors.setVisible(true);

	}

	private void save() {
		parentCitation.title = tfTitle.getText();
		parentCitation.year = tfYear.getText();
		parentCitation.journal = tfJournal.getText();

		parentCitation.address = tfAddress.getText();
		parentCitation.booktitle = tfBooktitle.getText();
		parentCitation.chapter = tfChapter.getText();
		parentCitation.edition = tfEdition.getText();
		parentCitation.howpublished = tfHowpublished.getText();
		parentCitation.institution = tfInstitution.getText();
		parentCitation.journal = tfJournal.getText();
		parentCitation.month = tfMonth.getText();
		parentCitation.note = tfNote.getText();
		parentCitation.number = tfNumber.getText();
		parentCitation.organization = tfOrganization.getText();
		parentCitation.pages = tfPages.getText();
		parentCitation.publisher = tfPublisher.getText();
		parentCitation.school = tfSchool.getText();
		parentCitation.series = tfSeries.getText();
		parentCitation.type = tfType.getText();
		parentCitation.url = tfURL.getText();
		parentCitation.volume = tfVolume.getText();
		parentCitation.location = tfLocation.getText();
		parentCitation.doi = tfDoi.getText();
		parentCitation.isbn = tfISBN.getText();
		parentCitation.issn = tfISSN.getText();

		final List<String[]> authorsNew = new ArrayList<String[]>();

		for (int i = 0; i < tfAuthors.getRowCount(); i++) {
			final String first = (String) tfAuthors.getValueAt(i, 0);

			final String last = (String) tfAuthors.getValueAt(i, 1);
			if (!(first.isEmpty() & last.isEmpty())) {
				authorsNew.add(new String[] { first, last });
			}

		}
		parentCitation.authors = new ArrayList<String[]>(authorsNew);

		final List<String[]> editorsNew = new ArrayList<String[]>();

		for (int i = 0; i < tfEditors.getRowCount(); i++) {
			final String first = (String) tfEditors.getValueAt(i, 0);

			final String last = (String) tfEditors.getValueAt(i, 1);
			if (!(first.isEmpty() & last.isEmpty())) {
				editorsNew.add(new String[] { first, last });
			}

		}
		parentCitation.editors = new ArrayList<String[]>(editorsNew);

		Converter.updateGui();
		GUI.list.setSelectedValue(parentCitation, true);
		dispose();
	}
}
