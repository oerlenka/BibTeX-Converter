package de.uni_mannheim.informatik.swt.bibTexConverter.src.settings;

import java.util.ArrayList;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;

/**
 * @author Oliver Erlenkämper
 */
public class Settings {
	public static String ArticleFormat;
	public static Boolean automaticallyCopyToClipboard = false;
	public static String BookFormat;
	public static String BookletFormat;
	public static String ConferenceFormat;
	public static boolean eliminateDuplicates = true;
	public static String entrySeparator = ",";
	public static String InbookFormat;
	public static String IncollectionFormat;
	public static boolean indexCitations = true;
	public static int indexFormat = 0;
	public static String InproceedingsFormat;
	public static String language = "English";
	public static String ManualFormat;
	public static String MasterthesisFormat;
	public static String MiscFormat;
	public static ArrayList<String[]> patternFormats = new ArrayList<String[]>();
	public static ArrayList<String[]> patternNames = new ArrayList<String[]>();
	public static ArrayList<String[][]> patternPrePost = new ArrayList<String[][]>(); // [attribute][prepost]
	public static ArrayList<String[]> patternSettings = new ArrayList<String[]>();
	public static String PhdthesisFormat;
	public static String[][] prePost;
	public static String ProceedingsFormat;
	public static String[] settings;
	public static boolean sortCitations = true;
	public static String TechreportFormat;
	public static String UnpublishedFormat;
	public final static String WELCOME_MESSAGE = "Welcome to BibTexConverter!\n\n" + "Please paste your BibTex References into the left Textarea or load references from an existing .bib-file.\n\n" + "Please set your preferences below and start conversion with the 'Convert' Button.";

	public static boolean formatAlreadyExists(String format) {
		boolean found = false;
		for (int i = 0; i < patternNames.size(); i++)
			if (patternNames.get(i)[0].equals(format)) {
				found = true;
			}
		return found;
	}

	public static String[] getFormatNames() {
		final String[] names = new String[patternNames.size()];

		for (int i = 0; i < patternNames.size(); i++) {
			names[i] = patternNames.get(i)[0];
		}
		return names;

	}

	public static String[] getFormatTooltip() {
		final String[] tooltips = new String[patternNames.size()];

		for (int i = 0; i < patternNames.size(); i++) {
			tooltips[i] = patternNames.get(i)[1];
		}
		return tooltips;
	}

	public static void setFormat(int index) {
		if (!patternNames.isEmpty()) {
			Log.log("SETTINGS.setFormat set to: " + patternNames.get(index)[0]);

			ArticleFormat = patternFormats.get(index)[0];
			BookFormat = patternFormats.get(index)[1];
			BookletFormat = patternFormats.get(index)[2];
			ConferenceFormat = patternFormats.get(index)[3];
			InbookFormat = patternFormats.get(index)[4];
			IncollectionFormat = patternFormats.get(index)[5];
			InproceedingsFormat = patternFormats.get(index)[6];
			ManualFormat = patternFormats.get(index)[7];
			MasterthesisFormat = patternFormats.get(index)[8];
			MiscFormat = patternFormats.get(index)[9];
			PhdthesisFormat = patternFormats.get(index)[10];
			ProceedingsFormat = patternFormats.get(index)[11];
			TechreportFormat = patternFormats.get(index)[12];
			UnpublishedFormat = patternFormats.get(index)[13];

			prePost = patternPrePost.get(index);

			settings = patternSettings.get(index);
		} else {
			Log.error("No patterns!");
		}

	}

	/*
	 * 
	 * 
	 * ACM Springer APA Harvard MLA CMS DIN 1505 AMA IEEE
	 */

	/**
	 * setter method for the language
	 * 
	 * @param l
	 */
	public static void setLanguage(String l) {
		language = l;
	}

	public Settings() {
		initBasicPatterns();
		setFormat(0);
	}

	public void addFormat(String[] pattern, String[] settings, String name, String tooltip) {
		patternFormats.add(pattern);
		patternNames.add(new String[] { name, tooltip });
		patternSettings.add(settings);
	}

	private void initBasicPatterns() {

		final String[][] EMPTY_PrePost = new String[28][2];
		final String[] EMPTY_Settings = new String[7];

		for (int p = 0; p < EMPTY_PrePost.length; p++) {
			EMPTY_PrePost[p] = new String[] { "", "" };
		}

		for (int s = 0; s < EMPTY_Settings.length; s++) {
			EMPTY_Settings[s] = "";
		}

		/*
		 * [0] address, [1] author, [2] booktitle, [3] chapter, [4] edition, [5] editor, [6] howpublished, [7] institution, [8] journal, [9] month, [10] note, [11] number, [12] organization, [13] pages, [14] publisher, [15] school, [16] series, [17] title, [18] citationType, [19] url, [20] volume, [21] year
		 */

		// ************************************************ ACM_Format Pattern ***********************************************

		final String[] ACM_Format = new String[14];
		final String[][] ACM_PrePost = new String[28][2];
		final String[] ACM_Settings = new String[7];

		ACM_Settings[0] = "<X>{last}</X>, <X>{first}</X>"; // Authors Format
		ACM_Settings[1] = " AND "; // append last author with
		ACM_Settings[2] = "true"; // Abbreviate Authors?
		ACM_Settings[3] = "true"; // Use et al.?
		ACM_Settings[4] = "et al."; // et al string
		ACM_Settings[5] = "3"; // # for et al
		ACM_Settings[6] = "3"; // # then show

		// ADDRESS
		ACM_PrePost[0][0] = "";
		ACM_PrePost[0][1] = ", "; // POST

		// AUTHOR
		ACM_PrePost[1][0] = "";
		ACM_PrePost[1][1] = " "; // POST

		// BOOKTITLE
		ACM_PrePost[2][0] = "In ";
		ACM_PrePost[2][1] = ", "; // POST

		// CHAPTER
		ACM_PrePost[3][0] = "";
		ACM_PrePost[3][1] = " "; // POST

		// DOI
		ACM_PrePost[24][0] = "";
		ACM_PrePost[24][0] = "";

		// EDITION
		ACM_PrePost[4][0] = "";
		ACM_PrePost[4][1] = " "; // POST

		// EDITOR
		ACM_PrePost[5][0] = "";
		ACM_PrePost[5][1] = ", Eds. "; // POST

		// HOWPUBLISHED
		ACM_PrePost[6][0] = "";
		ACM_PrePost[6][1] = " "; // POST

		// INSTITUTION
		ACM_PrePost[7][0] = "";
		ACM_PrePost[7][1] = ". "; // POST

		// JOURNAL
		ACM_PrePost[8][0] = "";
		ACM_PrePost[8][1] = " "; // POST

		// LOCATION
		ACM_PrePost[23][0] = "";
		ACM_PrePost[23][1] = ", ";

		// MONTH
		ACM_PrePost[9][0] = "";
		ACM_PrePost[9][1] = " "; // POST

		// NOTE
		ACM_PrePost[10][0] = "";
		ACM_PrePost[10][1] = " "; // POST

		// NUMBER
		ACM_PrePost[11][0] = "";
		ACM_PrePost[11][1] = " "; // POST

		// ORGANIZATION
		ACM_PrePost[12][0] = "";
		ACM_PrePost[12][1] = ", "; // POST

		// PAGES
		ACM_PrePost[13][0] = "";
		ACM_PrePost[13][1] = " "; // POST

		// PUBLISHER
		ACM_PrePost[14][0] = "";
		ACM_PrePost[14][1] = ", "; // POST

		// SCHOOL
		ACM_PrePost[15][0] = "";
		ACM_PrePost[15][1] = ", "; // POST

		// SERIES
		ACM_PrePost[16][0] = "";
		ACM_PrePost[16][1] = " "; // POST

		// TITLE
		ACM_PrePost[17][0] = "";
		ACM_PrePost[17][1] = ". "; // POST

		// TYPE
		ACM_PrePost[18][0] = "";
		ACM_PrePost[18][1] = ". "; // POST

		// URL
		ACM_PrePost[19][0] = "";
		ACM_PrePost[19][1] = ". "; // POST

		// VOLUME
		ACM_PrePost[20][0] = "";
		ACM_PrePost[20][1] = ", "; // POST

		// YEAR
		ACM_PrePost[21][0] = "";
		ACM_PrePost[21][1] = ". "; // POST

		// INDEX
		ACM_PrePost[22][0] = "[";
		ACM_PrePost[22][1] = "]";

		ACM_PrePost[23][0] = "";
		ACM_PrePost[23][1] = "";

		// DOI
		ACM_PrePost[24][0] = "";
		ACM_PrePost[24][1] = " ";

		// ISBN
		ACM_PrePost[25][0] = "";
		ACM_PrePost[25][1] = "";

		// ENUM
		ACM_PrePost[26][0] = "";
		ACM_PrePost[26][1] = "";

		ACM_Format[0] = "{author}{year}{title}<i>{journal}{volume}</i>{pages}"; // Article
		ACM_Format[1] = "{author}{year}<i>{title}</i>{publisher}{address}"; // Book
		ACM_Format[2] = "{author}{year}{title}{organization}{address}"; // Booklet
		ACM_Format[3] = "{author}{year}{title}<i>{booktitle}</i>{location}{month}{year}{editor}{publisher}{address}{pages}"; // Conference
		ACM_Format[4] = "{author}{year}{title}<i>{booktitle}</i>{editor}{publisher}{address}{pages}"; // Inbook
		ACM_Format[5] = "{author}{year}{title}<i>{booktitle}</i>{editor}{publisher}{address}{pages}";// InCollection
		ACM_Format[6] = "{author}{year}{title}<i>{booktitle}</i>{location}{month}{year}{editor}{publisher}{address}{pages}"; // InProceedings
		ACM_Format[7] = "{author}{year}{title}{organization}{address}"; // Manual
		ACM_Format[8] = "{author}{year}{title}{type}{school}{address}"; // Mastersthesis
		ACM_Format[9] = "{author}{year}{title}{url}{doi}{note}"; // Misc
		ACM_Format[10] = "{author}{year}{title}{type}{school}{address}"; // PhdThesis
		ACM_Format[11] = "{author}{editor}{year}<i>{title}</i>{location}{month}{year}{address}"; // Proceedings
		ACM_Format[12] = "{author}{year}{title}{type}{institution}{note}"; // Techreport
		ACM_Format[13] = "{author}{month}{year}{title}{note}"; // Unpublished

// ************************************************ Springer_Format Pattern ***********************************************

		final String[] Springer_Format = new String[14];
		final String[][] Springer_PrePost = new String[28][2];

		// ADDRESS
		Springer_PrePost[0][0] = "";
		Springer_PrePost[0][1] = ", "; // POST

		// AUTHOR
		Springer_PrePost[1][0] = "";
		Springer_PrePost[1][1] = " "; // POST

		// BOOKTITLE
		Springer_PrePost[2][0] = "";
		Springer_PrePost[2][1] = ", "; // POST

		// CHAPTER
		Springer_PrePost[3][0] = "";
		Springer_PrePost[3][1] = ". "; // POST

		// DOI
		Springer_PrePost[24][0] = "doi:";
		Springer_PrePost[24][1] = "";

		// EDITION
		Springer_PrePost[4][0] = "";
		Springer_PrePost[4][1] = ". edn. "; // POST

		// EDITOR
		Springer_PrePost[5][0] = "In: ";
		Springer_PrePost[5][1] = " (ed) "; // POST

		// HOWPUBLISHED
		Springer_PrePost[6][0] = "";
		Springer_PrePost[6][1] = ". "; // POST

		// INSTITUTION
		Springer_PrePost[7][0] = "";
		Springer_PrePost[7][1] = " "; // POST

		// ISBN
		Springer_PrePost[25][0] = "";
		Springer_PrePost[25][1] = "";

		// JOURNAL
		Springer_PrePost[8][0] = "";
		Springer_PrePost[8][1] = " "; // POST

		// MONTH
		Springer_PrePost[9][0] = "";
		Springer_PrePost[9][1] = " "; // POST

		// NOTE
		Springer_PrePost[10][0] = "";
		Springer_PrePost[10][1] = " "; // POST

		// NUMBER
		Springer_PrePost[11][0] = "(";
		Springer_PrePost[11][1] = "): "; // POST

		// ORGANIZATION
		Springer_PrePost[12][0] = "";
		Springer_PrePost[12][1] = ", "; // POST

		// PAGES
		Springer_PrePost[13][0] = "p. ";
		Springer_PrePost[13][1] = ". "; // POST

		// PUBLISHER
		Springer_PrePost[14][0] = "";
		Springer_PrePost[14][1] = ", "; // POST

		// SCHOOL
		Springer_PrePost[15][0] = "";
		Springer_PrePost[15][1] = " "; // POST

		// SERIES
		Springer_PrePost[16][0] = "";
		Springer_PrePost[16][1] = ", "; // POST

		// TITLE
		Springer_PrePost[17][0] = "";
		Springer_PrePost[17][1] = ". "; // POST

		// TYPE
		Springer_PrePost[18][0] = "";
		Springer_PrePost[18][1] = ", "; // POST

		// URL
		Springer_PrePost[19][0] = "";
		Springer_PrePost[19][1] = ". "; // POST

		// VOLUME
		Springer_PrePost[20][0] = "";
		Springer_PrePost[20][1] = ", "; // POST

		// YEAR
		Springer_PrePost[21][0] = "(";
		Springer_PrePost[21][1] = "). "; // POST

		// INDEX
		Springer_PrePost[22][0] = "[";
		Springer_PrePost[22][1] = "]";

		Springer_PrePost[26][0] = "";
		Springer_PrePost[26][1] = "";

		final String[] Springer_Settings = new String[7];

		Springer_Settings[0] = "{last} {first}"; // Authors Format
		Springer_Settings[1] = ", "; // append last author with
		Springer_Settings[2] = "true"; // Abbreviate Authors?
		Springer_Settings[3] = "true"; // Use et al.?
		Springer_Settings[4] = "et al."; // et al string
		Springer_Settings[5] = "3"; // # for et al
		Springer_Settings[6] = "1"; // # then show

		Springer_Format[0] = "{author}{year}{title}{journal}{volume}{number}{pages}{doi}"; // Article
		Springer_Format[1] = "{author}{year}{title}{publisher}{address}"; // Book
		Springer_Format[2] = "{author}{year}<i>{title}</i>{organization}{address}"; // Booklet
		Springer_Format[3] = "{author}{year}{title}{booktitle}{publisher}{address}{month}{year}"; // Conference
		Springer_Format[4] = "{author}{year}{chapter}{editor}{title}{edition}{publisher}{address}{pages}"; // Inbook
		Springer_Format[5] = "{author}{year}{title}{booktitle}{publisher}{address}{month}{year}";// InCollection
		Springer_Format[6] = "{author}{year}{title}{editor}{booktitle}{publisher}{address}{month}{year}"; // InProceedings
		Springer_Format[7] = "{author}{year}{title}{organization}{address}"; // Manual
		Springer_Format[8] = "{author}{year}{title}{type}{school}"; // Mastersthesis
		Springer_Format[9] = "{author}{year}{title}{howpublished}{url}{note}"; // Misc
		Springer_Format[10] = "{author}{year}{title}{type}{school}"; // PhdThesis
		Springer_Format[11] = "{author}{year}{title}{editor}{series}{volume}{publisher}{address}{month}{year}"; // Proceedings
		Springer_Format[12] = "{author}{year}{title}{type}{institution}"; // Techreport
		Springer_Format[13] = "{author}{year}{title}{url}{note}"; // Unpublished

		// ************************************************ APA_Format Pattern ***********************************************

		final String[] APA_Format = new String[14];
		final String[][] APA_PrePost = new String[28][2];

		// ADDRESS
		APA_PrePost[0][0] = "";
		APA_PrePost[0][1] = ": "; // POST

		// AUTHOR
		APA_PrePost[1][0] = "";
		APA_PrePost[1][1] = " "; // POST

		// BOOKTITLE
		APA_PrePost[2][0] = "";
		APA_PrePost[2][1] = ", "; // POST

		// CHAPTER
		APA_PrePost[3][0] = "";
		APA_PrePost[3][1] = " "; // POST

		// DOI
		APA_PrePost[24][0] = "doi:";
		APA_PrePost[24][0] = "";

		// EDITION
		APA_PrePost[4][0] = "(";
		APA_PrePost[4][1] = "). "; // POST

		// EDITOR
		APA_PrePost[5][0] = "";
		APA_PrePost[5][1] = " (Eds.), "; // POST

		// HOWPUBLISHED
		APA_PrePost[6][0] = "";
		APA_PrePost[6][1] = " "; // POST

		// INSTITUTION
		APA_PrePost[7][0] = "";
		APA_PrePost[7][1] = ". "; // POST

		// JOURNAL
		APA_PrePost[8][0] = "";
		APA_PrePost[8][1] = ", "; // POST

		// LOCATION
		APA_PrePost[23][0] = "";
		APA_PrePost[23][1] = "";

		// MONTH
		APA_PrePost[9][0] = "";
		APA_PrePost[9][1] = " "; // POST

		// NOTE
		APA_PrePost[10][0] = "";
		APA_PrePost[10][1] = " "; // POST

		// NUMBER
		APA_PrePost[11][0] = "(";
		APA_PrePost[11][1] = "), "; // POST

		// ORGANIZATION
		APA_PrePost[12][0] = "";
		APA_PrePost[12][1] = ", "; // POST

		// PAGES
		APA_PrePost[13][0] = "p. ";
		APA_PrePost[13][1] = ". "; // POST

		// PUBLISHER
		APA_PrePost[14][0] = "";
		APA_PrePost[14][1] = ". "; // POST

		// SCHOOL
		APA_PrePost[15][0] = "";
		APA_PrePost[15][1] = ", "; // POST

		// SERIES
		APA_PrePost[16][0] = "";
		APA_PrePost[16][1] = " "; // POST

		// TITLE
		APA_PrePost[17][0] = "";
		APA_PrePost[17][1] = ". "; // POST

		// TYPE
		APA_PrePost[18][0] = "(";
		APA_PrePost[18][1] = "). "; // POST

		// URL
		APA_PrePost[19][0] = "Retrieved from ";
		APA_PrePost[19][1] = ". "; // POST

		// VOLUME
		APA_PrePost[20][0] = "Vol. ";
		APA_PrePost[20][1] = " "; // POST

		// YEAR
		APA_PrePost[21][0] = "(";
		APA_PrePost[21][1] = ""; // POST

		// INDEX
		APA_PrePost[22][0] = "[";
		APA_PrePost[22][1] = "]";

		// ENUM
		APA_PrePost[26][0] = "";
		APA_PrePost[26][1] = "). ";

		final String[] APA_Settings = new String[7];

		APA_Settings[0] = "{last}, {first}"; // Authors Format
		APA_Settings[1] = " & "; // append last author with
		APA_Settings[2] = "true"; // Abbreviate Authors?
		APA_Settings[3] = "true"; // Use et al.?
		APA_Settings[4] = "et al."; // et al string
		APA_Settings[5] = "4"; // # for et al
		APA_Settings[6] = "3"; // # then show

		APA_Format[0] = "{author}{year}{enum}{title}<i>{journal}</i>{volume}{number}{pages}"; // Article
		APA_Format[1] = "{author}{year}{enum}<i>{title}</i>{editor}{series}{edition}{volume}{address}{publisher}{url}"; // Book
		APA_Format[2] = "{author}{year}{enum}{title}{publisher}{address}"; // Booklet
		APA_Format[3] = "{author}{year}{enum}{title}{editor}<i>{booktitle}</i>{series}{volume}{pages}{address}{publisher}"; // Conference
		APA_Format[4] = "{author}{year}{enum}{title}{editor}<i>{booktitle}</i>{series}{edition}{volume}{pages}{address}{publisher}{url}"; // Inbook
		APA_Format[5] = "{author}{year}{enum}{title}{editor}<i>{booktitle}</i>{series}{volume}{pages}{address}{publisher}";// InCollection
		APA_Format[6] = "{author}{year}{enum}{title}{editor}<i>{booktitle}</i>{series}{volume}{pages}{address}{publisher}"; // InProceedings
		APA_Format[7] = "{author}{year}{enum}{title}{organization}{address}"; // Manual
		APA_Format[8] = "{author}{year}{enum}<i>{title}</i>{type}{school}{address}{location}"; // Mastersthesis
		APA_Format[9] = "{author}{year}{enum}{title}{publisher}{url}"; // Misc
		APA_Format[10] = "{author}{year}{enum}<i>{title}</i>{type}{school}{address}{location}"; // PhdThesis
		APA_Format[11] = "{editor}{year}{enum}{title}{series}{volume}{pages}{address}{publisher}"; // Proceedings
		APA_Format[12] = "{author}{year}{enum}{title}{institution}"; // Techreport
		APA_Format[13] = "{author}{year}{enum}<i>{title}</i>{address}{url}"; // Unpublished

		// ************************************************ Harvard_Format Pattern ***********************************************

		final String[] Harvard_Format = new String[14];
		final String[][] Harvard_PrePost = new String[28][2];

		// ADDRESS
		Harvard_PrePost[0][0] = "";
		Harvard_PrePost[0][1] = ". "; // POST

		// AUTHOR
		Harvard_PrePost[1][0] = "";
		Harvard_PrePost[1][1] = ", "; // POST

		// BOOKTITLE
		Harvard_PrePost[2][0] = "";
		Harvard_PrePost[2][1] = ", "; // POST

		// CHAPTER
		Harvard_PrePost[3][0] = "";
		Harvard_PrePost[3][1] = " "; // POST

		// EDITION
		Harvard_PrePost[4][0] = "";
		Harvard_PrePost[4][1] = ". "; // POST

		// EDITOR
		Harvard_PrePost[5][0] = "in: ";
		Harvard_PrePost[5][1] = " (Eds.) "; // POST

		// HOWPUBLISHED
		Harvard_PrePost[6][0] = "";
		Harvard_PrePost[6][1] = " "; // POST

		// INSTITUTION
		Harvard_PrePost[7][0] = "";
		Harvard_PrePost[7][1] = " "; // POST

		// JOURNAL
		Harvard_PrePost[8][0] = "";
		Harvard_PrePost[8][1] = " "; // POST

		// MONTH
		Harvard_PrePost[9][0] = "";
		Harvard_PrePost[9][1] = " "; // POST

		// NOTE
		Harvard_PrePost[10][0] = "";
		Harvard_PrePost[10][1] = " "; // POST

		// NUMBER
		Harvard_PrePost[11][0] = "";
		Harvard_PrePost[11][1] = " "; // POST

		// ORGANIZATION
		Harvard_PrePost[12][0] = "";
		Harvard_PrePost[12][1] = ", "; // POST

		// PAGES
		Harvard_PrePost[13][0] = "p. ";
		Harvard_PrePost[13][1] = " "; // POST

		// PUBLISHER
		Harvard_PrePost[14][0] = "";
		Harvard_PrePost[14][1] = ", "; // POST

		// SCHOOL
		Harvard_PrePost[15][0] = "";
		Harvard_PrePost[15][1] = " "; // POST

		// SERIES
		Harvard_PrePost[16][0] = "";
		Harvard_PrePost[16][1] = ". "; // POST

		// TITLE
		Harvard_PrePost[17][0] = "";
		Harvard_PrePost[17][1] = ". "; // POST

		// TYPE
		Harvard_PrePost[18][0] = "(";
		Harvard_PrePost[18][1] = "). "; // POST

		// URL
		Harvard_PrePost[19][0] = "";
		Harvard_PrePost[19][1] = " "; // POST

		// VOLUME
		Harvard_PrePost[20][0] = "";
		Harvard_PrePost[20][1] = ", "; // POST

		// YEAR
		Harvard_PrePost[21][0] = "";
		Harvard_PrePost[21][1] = " "; // POST

		// INDEX
		Harvard_PrePost[22][0] = "[";
		Harvard_PrePost[22][1] = "]";

		// ENUM
		Harvard_PrePost[26][0] = "";
		Harvard_PrePost[26][1] = ". ";

		final String[] Harvard_Settings = new String[7];

		Harvard_Settings[0] = "{last}, {first}"; // Authors Format
		Harvard_Settings[1] = " & "; // append last author with
		Harvard_Settings[2] = "true"; // Abbreviate Authors?
		Harvard_Settings[3] = "false"; // Use et al.?
		Harvard_Settings[4] = ""; // et al string
		Harvard_Settings[5] = ""; // # for et al
		Harvard_Settings[6] = ""; // # then show

		Harvard_Format[0] = "{author}{year}{enum}{title}{journal}{volume}{pages}"; // Article
		Harvard_Format[1] = "{author}{year}{enum}{title}{edition}{series}{publisher}{address}"; // Book
		Harvard_Format[2] = "{author}{year}{enum}{title}{address}"; // Booklet
		Harvard_Format[3] = "{author}{year}{enum}{title}{editor}{booktitle}{series}{publisher}{address}{pages}"; // Conference
		Harvard_Format[4] = "{author}{year}{enum}{title}{editor}{booktitle}{series}{publisher}{address}{pages}"; // Inbook
		Harvard_Format[5] = "{author}{year}{enum}{title}{editor}{booktitle}{series}{publisher}{address}{pages}";// InCollection
		Harvard_Format[6] = "{author}{year}{enum}{title}{editor}{booktitle}{series}{publisher}{address}{pages}"; // InProceedings
		Harvard_Format[7] = "{author}{year}{enum}{title}{organization}{address}"; // Manual
		Harvard_Format[8] = "{author}{year}{enum}{title}{type}"; // Mastersthesis
		Harvard_Format[9] = "{author}{year}{enum}{title}"; // Misc
		Harvard_Format[10] = "{author}{year}{enum}{title}{type}"; // PhdThesis
		Harvard_Format[11] = "{editor}{year}{enum}{title}{series}{publisher}{address}"; // Proceedings
		Harvard_Format[12] = "{author}{year}{enum}{title}{institution}"; // Techreport
		Harvard_Format[13] = "{author}{year}{enum}{title}"; // Unpublished

		// ************************************************ MLA_Format Pattern ***********************************************

		final String[] MLA_Format = new String[14];
		final String[][] MLA_PrePost = new String[28][2];

		// ADDRESS
		MLA_PrePost[0][0] = "";
		MLA_PrePost[0][1] = ": "; // POST

		// AUTHOR
		MLA_PrePost[1][0] = "";
		MLA_PrePost[1][1] = " "; // POST

		// BOOKTITLE
		MLA_PrePost[2][0] = "";
		MLA_PrePost[2][1] = ". "; // POST

		// CHAPTER
		MLA_PrePost[3][0] = "";
		MLA_PrePost[3][1] = " "; // POST

		// EDITION
		MLA_PrePost[4][0] = "";
		MLA_PrePost[4][1] = ". "; // POST

		// EDITOR
		MLA_PrePost[5][0] = "Ed. ";
		MLA_PrePost[5][1] = ". "; // POST

		// HOWPUBLISHED
		MLA_PrePost[6][0] = "";
		MLA_PrePost[6][1] = " "; // POST

		// INSTITUTION
		MLA_PrePost[7][0] = "";
		MLA_PrePost[7][1] = " "; // POST

		// JOURNAL
		MLA_PrePost[8][0] = "";
		MLA_PrePost[8][1] = " "; // POST

		// LOCATION
		MLA_PrePost[23][0] = "";
		MLA_PrePost[23][1] = ". ";

		// MONTH
		MLA_PrePost[9][0] = "";
		MLA_PrePost[9][1] = " "; // POST

		// NOTE
		MLA_PrePost[10][0] = "";
		MLA_PrePost[10][1] = ". "; // POST

		// NUMBER
		MLA_PrePost[11][0] = "";
		MLA_PrePost[11][1] = " "; // POST

		// ORGANIZATION
		MLA_PrePost[12][0] = "";
		MLA_PrePost[12][1] = ", "; // POST

		// PAGES
		MLA_PrePost[13][0] = ": ";
		MLA_PrePost[13][1] = ". "; // POST

		// PUBLISHER
		MLA_PrePost[14][0] = "";
		MLA_PrePost[14][1] = ", "; // POST

		// SCHOOL
		MLA_PrePost[15][0] = "Diss. ";
		MLA_PrePost[15][1] = ", "; // POST

		// SERIES
		MLA_PrePost[16][0] = "";
		MLA_PrePost[16][1] = ". "; // POST

		// TITLE
		MLA_PrePost[17][0] = "\"";
		MLA_PrePost[17][1] = "\". "; // POST

		// TYPE
		MLA_PrePost[18][0] = "";
		MLA_PrePost[18][1] = " "; // POST

		// URL
		MLA_PrePost[19][0] = "";
		MLA_PrePost[19][1] = " "; // POST

		// VOLUME
		MLA_PrePost[20][0] = "";
		MLA_PrePost[20][1] = ". "; // POST

		// YEAR
		MLA_PrePost[21][0] = "";
		MLA_PrePost[21][1] = ". "; // POST

		// INDEX
		MLA_PrePost[22][0] = "[";
		MLA_PrePost[22][1] = "]";

		final String[] MLA_Settings = new String[7];

		MLA_Settings[0] = "{last}, {first}"; // Authors Format
		MLA_Settings[1] = ", "; // append last author with
		MLA_Settings[2] = "false"; // Abbreviate Authors?
		MLA_Settings[3] = "true"; // Use et al.?
		MLA_Settings[4] = "et al."; // et al string
		MLA_Settings[5] = "4"; // # for et al
		MLA_Settings[6] = "1"; // # then show

		MLA_Format[0] = "{author}{title}<i>{journal}</i>{month}{year}{pages}Print."; // Article
		MLA_Format[1] = "{author}<i>{title}</i>{address}{publisher}{year}"; // Article
		MLA_Format[2] = "{author}<i>{title}</i>{organization}{address}{year}"; // Booklet
		MLA_Format[3] = "{author}{title}<i>{booktitle}</i>{location}{editor}{address}{publisher}{month}{year}{pages}"; // Conference
		MLA_Format[4] = "{author}{chapter}<i>{title}</i>{editor}{address}{publisher}{year}{pages}Print."; // Inbook
		MLA_Format[5] = "{author}{title}<i>{booktitle}</i>{edition}{editor}{volume}{address}{publisher}{year}{pages}{series}{number}";// InCollection
		MLA_Format[6] = "{author}{title}<i>{booktitle}</i>{volume}{editor}{address}{publisher}{year}{pages}{series}"; // InProceedings
		MLA_Format[7] = "{author}<i>{title}</i>{address}{organization}{year}"; // Manual
		MLA_Format[8] = "{author}<i>{title}</i>{school}{address}{year}"; // Mastersthesis
		MLA_Format[9] = "{author}{title}{month}{year}{note}"; // Misc
		MLA_Format[10] = "{author}{title}{school}{address}{year}"; // PhdThesis
		MLA_Format[11] = "{title}{volume}{editor}{address}{publisher}{year}."; // Proceedings
		MLA_Format[12] = "{author}<i>{title}</i>{note}{institution}{year}."; // Techreport
		MLA_Format[13] = "{author}<i>{title}</i>{year}{note}"; // Unpublished

		// ************************************************ CMS_Format Pattern ***********************************************
		final String[] CMS_Format = new String[14];
		final String[][] CMS_PrePost = new String[28][2];

		// ADDRESS
		CMS_PrePost[0][0] = "";
		CMS_PrePost[0][1] = ", "; // POST

		// AUTHOR
		CMS_PrePost[1][0] = "";
		CMS_PrePost[1][1] = ". "; // POST

		// BOOKTITLE
		CMS_PrePost[2][0] = "In ";
		CMS_PrePost[2][1] = ", "; // POST

		// CHAPTER
		CMS_PrePost[3][0] = "";
		CMS_PrePost[3][1] = " "; // POST

		// EDITION
		CMS_PrePost[4][0] = "";
		CMS_PrePost[4][1] = " "; // POST

		// EDITOR
		CMS_PrePost[5][0] = "Edited by ";
		CMS_PrePost[5][1] = ", "; // POST

		// HOWPUBLISHED
		CMS_PrePost[6][0] = "";
		CMS_PrePost[6][1] = " "; // POST

		// INSTITUTION
		CMS_PrePost[7][0] = "";
		CMS_PrePost[7][1] = ". "; // POST

		// JOURNAL
		CMS_PrePost[8][0] = "";
		CMS_PrePost[8][1] = " "; // POST

		// MONTH
		CMS_PrePost[9][0] = "";
		CMS_PrePost[9][1] = " "; // POST

		// NOTE
		CMS_PrePost[10][0] = "";
		CMS_PrePost[10][1] = ". "; // POST

		// NUMBER
		CMS_PrePost[11][0] = "";
		CMS_PrePost[11][1] = " "; // POST

		// ORGANIZATION
		CMS_PrePost[12][0] = "";
		CMS_PrePost[12][1] = ", "; // POST

		// PAGES
		CMS_PrePost[13][0] = ": ";
		CMS_PrePost[13][1] = ". "; // POST

		// PUBLISHER
		CMS_PrePost[14][0] = "";
		CMS_PrePost[14][1] = ", "; // POST

		// SCHOOL
		CMS_PrePost[15][0] = "";
		CMS_PrePost[15][1] = ", "; // POST

		// SERIES
		CMS_PrePost[16][0] = "";
		CMS_PrePost[16][1] = " "; // POST

		// TITLE
		CMS_PrePost[17][0] = "\"";
		CMS_PrePost[17][1] = "\". "; // POST

		// TYPE
		CMS_PrePost[18][0] = "";
		CMS_PrePost[18][1] = " "; // POST

		// URL
		CMS_PrePost[19][0] = "";
		CMS_PrePost[19][1] = " "; // POST

		// VOLUME
		CMS_PrePost[20][0] = "";
		CMS_PrePost[20][1] = ", "; // POST

		// YEAR
		CMS_PrePost[21][0] = "";
		CMS_PrePost[21][1] = " "; // POST

		// INDEX
		CMS_PrePost[22][0] = "[";
		CMS_PrePost[22][1] = "]";

		// LOCATION
		CMS_PrePost[23][0] = "";
		CMS_PrePost[23][1] = ", ";

		final String[] CMS_Settings = new String[7];

		CMS_Settings[0] = "{last}, {first}"; // Authors Format
		CMS_Settings[1] = " and "; // append last author with
		CMS_Settings[2] = "false"; // Abbreviate Authors?
		CMS_Settings[3] = "true"; // Use et al.?
		CMS_Settings[4] = "and Others"; // et al string
		CMS_Settings[5] = "4"; // # for et al
		CMS_Settings[6] = "1"; // # then show

		CMS_Format[0] = "{author}{title}<i>{journal}</i>{volume}{month}{year}{pages}"; // Article
		CMS_Format[1] = "{author}{title}{editor}{address}{publisher}{year}"; // Book
		CMS_Format[2] = "{author}<i>{title}</i>{organization}{address}{year}"; // Booklet
		CMS_Format[3] = "{author}{title}<i>{booktitle}</i>{location}{editor}{pages}{address}{publisher}{month}{year}"; // Conference
		CMS_Format[4] = "{author}{title}<i>{booktitle}</i>{editor}{pages}{address}{publisher}{year}"; // Inbook
		CMS_Format[5] = "{author}{title}<i>{booktitle}</i>{editor}{pages}{address}{publisher}{year}";// InCollection
		CMS_Format[6] = "{author}{title}<i>{booktitle}</i>{volume}{publisher}{month}{year}"; // InProceedings
		CMS_Format[7] = "{author}<i>{title}</i>{address}{organization}{year}"; // Manual
		CMS_Format[8] = "{author}{title}{school}{address}{year}"; // Mastersthesis
		CMS_Format[9] = "{author}<i>{title}</i>{note}{month}{year}{url}{doi}"; // Misc
		CMS_Format[10] = "{author}{title}PhD diss., {school}{address}{year}"; // PhdThesis
		CMS_Format[11] = "{title}{volume}{editor}{address}{publisher}{year}"; // Proceedings
		CMS_Format[12] = "{author}{title}{note}{institution}{year}"; // Techreport
		CMS_Format[13] = "{author}<i>{title}</i>{note}{month}{year} "; // Unpublished

		// ************************************************ DIN1505_Format Pattern ***********************************************
		final String[] DIN1505_Format = new String[14];
		final String[][] DIN1505_PrePost = new String[28][2];

		// ADDRESS
		DIN1505_PrePost[0][0] = "";
		DIN1505_PrePost[0][1] = " : "; // POST

		// AUTHOR
		DIN1505_PrePost[1][0] = "";
		DIN1505_PrePost[1][1] = ": "; // POST

		// BOOKTITLE
		DIN1505_PrePost[2][0] = "In ";
		DIN1505_PrePost[2][1] = ", "; // POST

		// CHAPTER
		DIN1505_PrePost[3][0] = "";
		DIN1505_PrePost[3][1] = ". "; // POST

		// EDITION
		DIN1505_PrePost[4][0] = "";
		DIN1505_PrePost[4][1] = " "; // POST

		// EDITOR
		DIN1505_PrePost[5][0] = "/ ";
		DIN1505_PrePost[5][1] = " (Bearb.) "; // POST

		// HOWPUBLISHED
		DIN1505_PrePost[6][0] = "";
		DIN1505_PrePost[6][1] = " "; // POST

		// INSTITUTION
		DIN1505_PrePost[7][0] = "";
		DIN1505_PrePost[7][1] = ". "; // POST

		// ISBN
		DIN1505_PrePost[25][0] = "ISBN ";
		DIN1505_PrePost[25][1] = "";

		// JOURNAL
		DIN1505_PrePost[8][0] = "In ";
		DIN1505_PrePost[8][1] = ", "; // POST

		// MONTH
		DIN1505_PrePost[9][0] = "";
		DIN1505_PrePost[9][1] = " "; // POST

		// NOTE
		DIN1505_PrePost[10][0] = "";
		DIN1505_PrePost[10][1] = ". "; // POST

		// NUMBER
		DIN1505_PrePost[11][0] = "Nr. ";
		DIN1505_PrePost[11][1] = ". "; // POST

		// ORGANIZATION
		DIN1505_PrePost[12][0] = "";
		DIN1505_PrePost[12][1] = " "; // POST

		// PAGES
		DIN1505_PrePost[13][0] = "S. ";
		DIN1505_PrePost[13][1] = " "; // POST

		// PUBLISHER
		DIN1505_PrePost[14][0] = "";
		DIN1505_PrePost[14][1] = ", "; // POST

		// SCHOOL
		DIN1505_PrePost[15][0] = "";
		DIN1505_PrePost[15][1] = ", "; // POST

		// SERIES
		DIN1505_PrePost[16][0] = "(";
		DIN1505_PrePost[16][1] = ") "; // POST

		// TITLE
		DIN1505_PrePost[17][0] = "";
		DIN1505_PrePost[17][1] = ". "; // POST

		// TYPE
		DIN1505_PrePost[18][0] = "";
		DIN1505_PrePost[18][1] = ". "; // POST

		// URL
		DIN1505_PrePost[19][0] = "";
		DIN1505_PrePost[19][1] = ". "; // POST

		// VOLUME
		DIN1505_PrePost[20][0] = "Ausg. ";
		DIN1505_PrePost[20][1] = ", "; // POST

		// YEAR
		DIN1505_PrePost[21][0] = "";
		DIN1505_PrePost[21][1] = ". "; // POST

		// INDEX
		DIN1505_PrePost[22][0] = "";
		DIN1505_PrePost[22][1] = "";

		final String[] DIN1505_Settings = new String[7];

		DIN1505_Settings[0] = "<X>{last}</X>, {first}"; // Authors Format
		DIN1505_Settings[1] = ", "; // append last author with
		DIN1505_Settings[2] = "true"; // Abbreviate Authors?
		DIN1505_Settings[3] = "false"; // Use et al.?
		DIN1505_Settings[4] = ""; // et al string
		DIN1505_Settings[5] = ""; // # for et al
		DIN1505_Settings[6] = ""; // # then show

		DIN1505_Format[0] = "{author}{title}<i>{journal}</i>{volume}{year}{number}{pages}"; // Article
		DIN1505_Format[1] = "{author}<i>{title}</i>{editor}{address}{publisher}{year}{series}{number}{isbn}"; // Book
		DIN1505_Format[2] = "{author}<i>{title}</i>{address}{publisher}{year}"; // Booklet
		DIN1505_Format[3] = "{author}{title}<i>{booktitle}</i>{year}{series}{volume}{pages}"; // Conference
		DIN1505_Format[4] = "{author}<i>{title}</i>{editor}{volume}{pages}<i>{chapter}</i>{address}{publisher}{year}{series}{number}{isbn}"; // Inbook
		DIN1505_Format[5] = "{author}{title}<i>{booktitle}</i>{series}{volume}{address}{publisher}{year}";// InCollection
		DIN1505_Format[6] = "{author}{title}<i>{booktitle}</i>{year}{series}{volume}{pages}"; // InProceedings
		DIN1505_Format[7] = "{author}<i>{title}</i>{address}{year}{organization}."; // Manual
		DIN1505_Format[8] = "{author}<i>{title}</i>{type}{school}{year}"; // Mastersthesis
		DIN1505_Format[9] = "{author}<i>{title}</i>{year}{url}{note}"; // Misc
		DIN1505_Format[10] = "{author}<i>{title}</i>{type}{school}{year}"; // PhdThesis
		DIN1505_Format[11] = "{author}<i>{title}</i>{editor}{volume}{pages}{address}{publisher}{year}{series}{number}"; // Proceedings
		DIN1505_Format[12] = "{author}<i>{title}</i>{type}{address}{year}{institution}"; // Techreport
		DIN1505_Format[13] = "{author}<i>{title}<i>{year}{note}"; // Unpublished

		// ************************************************ AMA_Format Pattern ***********************************************
		final String[] AMA_Format = new String[14];
		final String[][] AMA_PrePost = new String[28][2];

		// ADDRESS
		AMA_PrePost[0][0] = "";
		AMA_PrePost[0][1] = ": "; // POST

		// AUTHOR
		AMA_PrePost[1][0] = "";
		AMA_PrePost[1][1] = " "; // POST

		// BOOKTITLE
		AMA_PrePost[2][0] = "In: ";
		AMA_PrePost[2][1] = ", "; // POST

		// CHAPTER
		AMA_PrePost[3][0] = "In: ";
		AMA_PrePost[3][1] = " "; // POST

		// EDITION
		AMA_PrePost[4][0] = "";
		AMA_PrePost[4][1] = ". ed. "; // POST

		// EDITOR
		AMA_PrePost[5][0] = "eds. ";
		AMA_PrePost[5][1] = " "; // POST

		// HOWPUBLISHED
		AMA_PrePost[6][0] = "";
		AMA_PrePost[6][1] = " "; // POST

		// INSTITUTION
		AMA_PrePost[7][0] = "";
		AMA_PrePost[7][1] = " "; // POST

		// JOURNAL
		AMA_PrePost[8][0] = "";
		AMA_PrePost[8][1] = ". "; // POST

		// LOCATION
		AMA_PrePost[23][0] = "Retrieved from ";
		AMA_PrePost[23][1] = "";

		// MONTH
		AMA_PrePost[9][0] = "";
		AMA_PrePost[9][1] = " "; // POST

		// NOTE
		AMA_PrePost[10][0] = "";
		AMA_PrePost[10][1] = " "; // POST

		// NUMBER
		AMA_PrePost[11][0] = "";
		AMA_PrePost[11][1] = " "; // POST

		// ORGANIZATION
		AMA_PrePost[12][0] = "";
		AMA_PrePost[12][1] = ", "; // POST

		// PAGES
		AMA_PrePost[13][0] = "";
		AMA_PrePost[13][1] = " "; // POST

		// PUBLISHER
		AMA_PrePost[14][0] = "";
		AMA_PrePost[14][1] = "; "; // POST

		// SCHOOL
		AMA_PrePost[15][0] = "";
		AMA_PrePost[15][1] = " "; // POST

		// SERIES
		AMA_PrePost[16][0] = "";
		AMA_PrePost[16][1] = " "; // POST

		// TITLE
		AMA_PrePost[17][0] = "";
		AMA_PrePost[17][1] = ". "; // POST

		// TYPE
		AMA_PrePost[18][0] = "";
		AMA_PrePost[18][1] = ", "; // POST

		// URL
		AMA_PrePost[19][0] = "Available at: ";
		AMA_PrePost[19][1] = ". "; // POST

		// VOLUME
		AMA_PrePost[20][0] = "";
		AMA_PrePost[20][1] = ": "; // POST

		// YEAR
		AMA_PrePost[21][0] = "";
		AMA_PrePost[21][1] = " "; // POST

		// INDEX
		AMA_PrePost[22][0] = "[";
		AMA_PrePost[22][1] = "]";

		final String[] AMA_Settings = new String[7];

		AMA_Settings[0] = "{last} {first}"; // Authors Format
		AMA_Settings[1] = ", "; // append last author with
		AMA_Settings[2] = "true"; // Abbreviate Authors?
		AMA_Settings[3] = "true"; // Use et al.?
		AMA_Settings[4] = "et al."; // et al string
		AMA_Settings[5] = "6"; // # for et al
		AMA_Settings[6] = "3"; // # then show

		AMA_Format[0] = "{author}{title}<i>{journal}</i>{year}{volume}{pages}"; // Article
		AMA_Format[1] = "{author}{editor}<i>{title}</i>{edition}{address}{publisher}{year}"; // Book
		AMA_Format[2] = "{author}{title}{organization}{address} {year}"; // Booklet
		AMA_Format[3] = "{author}{title}<i>{booktitle}</i>{year}{pages}"; // Conference
		AMA_Format[4] = "{author}{title}<i>{booktitle}</i>{editor}{edition}{address}{publisher}{year}{pages}"; // Inbook
		AMA_Format[5] = "{author}{title}<i>{booktitle}</i>{editor}{edition}{address}{publisher}{year}{pages}";// InCollection
		AMA_Format[6] = "{author}{title}<i>{booktitle}</i>{editor}{edition}{address}{publisher}{year}{pages}"; // InProceedings
		AMA_Format[7] = "{author}<i>{title}</i>{organization}{address}{year}"; // Manual
		AMA_Format[8] = "{author}<i>{title}</i>{type}{school}{year}"; // Mastersthesis
		AMA_Format[9] = "{author}{title}{year}{url}{note}"; // Misc
		AMA_Format[10] = "{author}<i>{title}</i>{type}{school}{year}"; // PhdThesis
		AMA_Format[11] = "<i>{title}</i>{editor}{edition} {address}{publisher}{year}{pages}"; // Proceedings
		AMA_Format[12] = "{author}<i>{title}</i>{type}{institution}{year}"; // Techreport
		AMA_Format[13] = "{author}<i>{title}</i>{note}"; // Unpublished

		// ************************************************ IEEE_Format Pattern ***********************************************
		final String[] IEEE_Format = new String[14];
		final String[][] IEEE_PrePost = new String[28][2];

		// ADDRESS
		IEEE_PrePost[0][0] = "";
		IEEE_PrePost[0][1] = ": "; // POST

		// AUTHOR
		IEEE_PrePost[1][0] = "";
		IEEE_PrePost[1][1] = ", "; // POST

		// BOOKTITLE
		IEEE_PrePost[2][0] = "in ";
		IEEE_PrePost[2][1] = ", "; // POST

		// CHAPTER
		IEEE_PrePost[3][0] = "in ";
		IEEE_PrePost[3][1] = " "; // POST

		// EDITION
		IEEE_PrePost[4][0] = "";
		IEEE_PrePost[4][1] = "., "; // POST

		// EDITOR
		IEEE_PrePost[5][0] = "";
		IEEE_PrePost[5][1] = ", Eds. "; // POST

		// HOWPUBLISHED
		IEEE_PrePost[6][0] = "";
		IEEE_PrePost[6][1] = " "; // POST

		// INSTITUTION
		IEEE_PrePost[7][0] = "";
		IEEE_PrePost[7][1] = ",  "; // POST

		// JOURNAL
		IEEE_PrePost[8][0] = "";
		IEEE_PrePost[8][1] = ", "; // POST

		// LOCATION
		IEEE_PrePost[23][0] = "";
		IEEE_PrePost[23][1] = "";

		// MONTH
		IEEE_PrePost[9][0] = "";
		IEEE_PrePost[9][1] = " "; // POST

		// NOTE
		IEEE_PrePost[10][0] = "";
		IEEE_PrePost[10][1] = ", "; // POST

		// NUMBER
		IEEE_PrePost[11][0] = "no. ";
		IEEE_PrePost[11][1] = " "; // POST

		// ORGANIZATION
		IEEE_PrePost[12][0] = "";
		IEEE_PrePost[12][1] = ", "; // POST

		// PAGES
		IEEE_PrePost[13][0] = "p. ";
		IEEE_PrePost[13][1] = ". "; // POST

		// PUBLISHER
		IEEE_PrePost[14][0] = "";
		IEEE_PrePost[14][1] = ", "; // POST

		// SCHOOL
		IEEE_PrePost[15][0] = "";
		IEEE_PrePost[15][1] = ", "; // POST

		// SERIES
		IEEE_PrePost[16][0] = "";
		IEEE_PrePost[16][1] = " "; // POST

		// TITLE
		IEEE_PrePost[17][0] = "\"";
		IEEE_PrePost[17][1] = "\", "; // POST

		// TYPE
		IEEE_PrePost[18][0] = "";
		IEEE_PrePost[18][1] = ", "; // POST

		// URL
		IEEE_PrePost[19][0] = "";
		IEEE_PrePost[19][1] = " "; // POST

		// VOLUME
		IEEE_PrePost[20][0] = "vol. ";
		IEEE_PrePost[20][1] = ", "; // POST

		// YEAR
		IEEE_PrePost[21][0] = "";
		IEEE_PrePost[21][1] = ". "; // POST

		// INDEX
		IEEE_PrePost[22][0] = "[";
		IEEE_PrePost[22][1] = "]";

		final String[] IEEE_Settings = new String[7];

		IEEE_Settings[0] = "{first} {last}"; // Authors Format
		IEEE_Settings[1] = ", "; // append last author with
		IEEE_Settings[2] = "true"; // Abbreviate Authors?
		IEEE_Settings[3] = "true"; // Use et al.?
		IEEE_Settings[4] = "et al."; // et al string
		IEEE_Settings[5] = "4"; // # for et al
		IEEE_Settings[6] = "3"; // # then show

		IEEE_Format[0] = "{author}{title}<i>{journal}</i>{volume}{number}{pages}{month}{year}"; // Article
		IEEE_Format[1] = "{author}<i>{title}</i>{edition}{volume}{address}{publisher}{year}"; // Book
		IEEE_Format[2] = "{author}{title}{organization}{address}"; // Booklet
		IEEE_Format[3] = "{author}{title}<i>{booktitle}</i>{address}{year}{volume}{pages}"; // Conference
		IEEE_Format[4] = "{author}{title}<i>{chapter}<i>{edition}{volume}{pages} {address}{publisher}{year}"; // Inbook
		IEEE_Format[5] = "{author}{title}<i>{booktitle}</i>{edition}{volume}{editor}{address}{publisher}{year}{pages}";// InCollection
		IEEE_Format[6] = "{author}{title}<i>{booktitle}</i>{address}{year}{volume}{pages}"; // InProceedings
		IEEE_Format[7] = "{author}{title}{organization}{address}"; // Manual
		IEEE_Format[8] = "{author}{title}{type}{school}{address}{year}"; // Mastersthesis
		IEEE_Format[9] = "{author}{title}{publisher}{month}{year}"; // Misc
		IEEE_Format[10] = "{author}{title}{type}{school}{address}{year}"; // PhdThesis
		IEEE_Format[11] = "{editor}{title}{address}{year}{volume}{pages}"; // Proceedings
		IEEE_Format[12] = "{author}{title}{institution}{year}"; // Techreport
		IEEE_Format[13] = "{author}{title}{address}{month}{year}"; // Unpublished

		patternFormats.add(ACM_Format);
		patternSettings.add(ACM_Settings);
		patternNames.add(new String[] { "ACM", "Association for Computer Machinery" });
		patternPrePost.add(ACM_PrePost);

		patternFormats.add(AMA_Format);
		patternSettings.add(AMA_Settings);
		patternNames.add(new String[] { "AMA", "American Medical Association" });
		patternPrePost.add(AMA_PrePost);

		patternFormats.add(APA_Format);
		patternSettings.add(APA_Settings);
		patternNames.add(new String[] { "APA", "American Psychogolical Association" });
		patternPrePost.add(APA_PrePost);

		patternFormats.add(CMS_Format);
		patternSettings.add(CMS_Settings);
		patternNames.add(new String[] { "CMS", "Chicago Manual of Style" });
		patternPrePost.add(CMS_PrePost);

		patternFormats.add(DIN1505_Format);
		patternSettings.add(DIN1505_Settings);
		patternNames.add(new String[] { "DIN 1505", "Deutsche Industrie Norm 1505 2. Teil" });
		patternPrePost.add(DIN1505_PrePost);

		patternFormats.add(Harvard_Format);
		patternSettings.add(Harvard_Settings);
		patternNames.add(new String[] { "Harvard", "Harvard University" });
		patternPrePost.add(Harvard_PrePost);

		patternFormats.add(IEEE_Format);
		patternSettings.add(IEEE_Settings);
		patternNames.add(new String[] { "IEEE", "Institute of Electrical and Electronics Engineers" });
		patternPrePost.add(IEEE_PrePost);

		patternFormats.add(MLA_Format);
		patternSettings.add(MLA_Settings);
		patternNames.add(new String[] { "MLA", "Modern Language Association" });
		patternPrePost.add(MLA_PrePost);

		patternFormats.add(Springer_Format);
		patternSettings.add(Springer_Settings);
		patternNames.add(new String[] { "Springer", "Springer Verlag" });
		patternPrePost.add(Springer_PrePost);

		for (final String[] s : patternFormats) {
			if (s[0] == null) {
				s[0] = "";
			}
			if (s[1] == null) {
				s[1] = "";
			}

		}

		for (final String[][] p : patternPrePost) {
			for (final String[] pp : p) {
				if (pp[0] == null) {
					pp[0] = "";
				}
				if (pp[1] == null) {
					pp[1] = "";
				}
			}
		}

	}

}
