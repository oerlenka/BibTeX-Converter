package de.uni_mannheim.informatik.swt.bibTexConverter.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.settings.Settings;

/**
 * @author Oliver Erlenkämper
 */
public class Citation {
	public List<String> additionalEntries = new ArrayList<String>();
	public String address = "";
	// public String author = "";
	public List<String[]> authors = new ArrayList<String[]>();
	public String booktitle = "";
	public String chapter = "";
	public String citationType;
	public String doi = "";
	public String edition = "";
	public List<String[]> editors = new ArrayList<String[]>();
	public String enumeration = "";
	public String format;
	public String howpublished = "";
	public Integer id = 0;
	public String index = "";
	public String institution = "";
	public String isbn = "";
	public String issn = "";
	public String journal = "";
	public String location = "";
	public String month = "";
	public String note = "";
	public String number = "";
	public String numpages = "";
	public String organization = "";
	public String pages = "";
	public String[][] prePost;
	public String publisher = "";
	public String school = "";
	public String series = "";
	public String[] settings;
	public String title = "";
	public String type = "";
	public String url = "";

	public String volume = "";
	public String xref = "";

	public String year = "";
	Boolean authorErrorReported = false;

	public Citation(int id, String[] valuePairArray) {
		createCitation(id, valuePairArray);

	}

	/**
	 * returns the BibTeX formatted string of the citation
	 * 
	 * @return
	 */
	public String getBibtex() {
		String result = "";

		if (xref.isEmpty()) {
			xref = year + authors.get(0)[1].substring(0, 3) + id;
		}

		result += "@" + citationType.toUpperCase() + '{' + xref + ',' + "\n";

		String bibAuthors = "";
		String comma = " ";
		for (final String[] a : authors) {
			if (!a[0].equals("")) {
				comma = ", ";
			} else {
				comma = " ";
			}
			bibAuthors += a[1] + comma + a[0] + " AND ";
		}

		if (!bibAuthors.isEmpty()) {
			result += "author = " + '{' + bibAuthors.substring(0, bibAuthors.lastIndexOf(" AND ")) + '}' + ",\n";
		}
		if (!title.isEmpty()) {
			result += "title = " + '{' + title + '}' + ",\n";
		}
		if (!year.isEmpty()) {
			result += "year = " + '{' + year + '}' + ",\n";
		}
		if (!booktitle.isEmpty()) {
			result += "booktitle = " + '{' + booktitle + '}' + ",\n";
		}
		if (!publisher.isEmpty()) {
			result += "publisher = " + '{' + publisher + '}' + ",\n";
		}
		if (!school.isEmpty()) {
			result += "school = " + '{' + school + '}' + ",\n";
		}
		if (!chapter.isEmpty()) {
			result += "chapter = " + '{' + chapter + '}' + ",\n";
		}
		if (!institution.isEmpty()) {
			result += "institution = " + '{' + institution + '}' + ",\n";
		}
		if (!journal.isEmpty()) {
			result += "journal = " + '{' + journal + '}' + ",\n";
		}
		if (!note.isEmpty()) {
			result += "note = " + '{' + note + '}' + ",\n";
		}
		if (!pages.isEmpty()) {
			result += "pages = " + '{' + pages + '}' + ",\n";
		}
		if (!address.isEmpty()) {
			result += "address = " + '{' + address + '}' + ",\n";
		}
		if (!edition.isEmpty()) {
			result += "edition = " + '{' + edition + '}' + ",\n";
		}
		if (!howpublished.isEmpty()) {
			result += "howpublished = " + '{' + howpublished + '}' + ",\n";
		}
		if (!month.isEmpty()) {
			result += "month = " + '{' + month + '}' + ",\n";
		}
		if (!number.isEmpty()) {
			result += "number = " + '{' + number + '}' + ",\n";
		}
		if (!organization.isEmpty()) {
			result += "organization = " + '{' + organization + '}' + ",\n";
		}
		if (!series.isEmpty()) {
			result += "series = " + '{' + series + '}' + ",\n";
		}
		if (!type.isEmpty()) {
			result += "type = " + '{' + type + '}' + ",\n";
		}
		if (!volume.isEmpty()) {
			result += "volume = " + '{' + volume + '}' + ",\n";
		}
		if (!issn.isEmpty()) {
			result += "issn = " + '{' + issn + '}' + ",\n";
		}
		if (!isbn.isEmpty()) {
			result += "isbn = " + '{' + isbn + '}' + ",\n";
		}
		if (!doi.isEmpty()) {
			result += "doi = " + '{' + doi + '}' + ",\n";
		}
		if (!url.isEmpty()) {
			result += "url = " + '{' + url + '}' + ",\n";
		}
		if (!location.isEmpty()) {
			result += "location = " + '{' + location + '}' + ",\n";
		}

		String bibEditors = "";
		for (final String[] e : editors) {
			bibEditors += e[1] + ", " + e[0] + " AND ";
		}

		if (!bibEditors.isEmpty()) {
			result += "editor = " + '{' + bibEditors.substring(0, bibEditors.lastIndexOf(" AND ")) + '}' + ",\n";
		}

		if (!additionalEntries.isEmpty()) {
			for (final String s : additionalEntries) {
				result += s.substring(0, s.indexOf(":")) + " = " + '{' + s.substring(s.indexOf(":") + 1, s.length()) + '}' + ", \n";

			}
		}

		result = result.substring(0, result.lastIndexOf(',')) + '}';

		result = result.replaceAll("ü", "\\{\\\\\"u\\}");
		result = result.replaceAll("ä", "\\{\\\\\"a\\}");
		result = result.replaceAll("ö", "\\{\\\\\"o\\}");
		result = result.replaceAll("Ü", "\\{\\\\\"U\\}");
		result = result.replaceAll("Ä", "\\{\\\\\"A\\}");
		result = result.replaceAll("Ö", "\\{\\\\\"O\\}");
		result = result.replaceAll("ß", "\\{\\\\ss\\}");

		result = result.replaceAll("ü", "\\{\\\\\"u\\}"); // colons
		result = result.replaceAll("ä", "\\{\\\\\"a\\}");
		result = result.replaceAll("ö", "\\{\\\\\"o\\}");
		result = result.replaceAll("ë", "\\{\\\\\"e\\}");
		result = result.replaceAll("ï", "\\{\\\\\"i\\}");
		result = result.replaceAll("ÿ", "\\{\\\\\"y\\}");

		result = result.replaceAll("Ü", "\\{\\\\\"U\\}");
		result = result.replaceAll("Ä", "\\{\\\\\"A\\}");
		result = result.replaceAll("Ë", "\\{\\\\\"E\\}");
		result = result.replaceAll("Ï", "\\{\\\\\"I\\}");
		result = result.replaceAll("Ö", "\\{\\\\\"O\\}");

		result = result.replaceAll("ù", "\\{\\\\`u\\}");// accents
		result = result.replaceAll("à", "\\{\\\\`a\\}");
		result = result.replaceAll("ò", "\\{\\\\`o\\}");
		result = result.replaceAll("è", "\\{\\\\`e\\}");
		result = result.replaceAll("ì", "\\{\\\\`i\\}");

		result = result.replaceAll("Ù", "\\{\\\\`U\\}");
		result = result.replaceAll("À", "\\{\\\\`A\\}");
		result = result.replaceAll("È", "\\{\\\\`E\\}");
		result = result.replaceAll("Ì", "\\{\\\\`I\\}");
		result = result.replaceAll("Ò", "\\{\\\\`O\\}");

		result = result.replaceAll("ú", "\\{\\\\'u\\}");// reverse accents
		result = result.replaceAll("á", "\\{\\\\'a\\}");
		result = result.replaceAll("ó", "\\{\\\\'o\\}");
		result = result.replaceAll("é", "\\{\\\\'e\\}");
		result = result.replaceAll("í", "\\{\\\\'i\\}");
		result = result.replaceAll("ý", "\\{\\\\'y\\}");
		result = result.replaceAll("ć", "\\{\\\\'c\\}");

		result = result.replaceAll("Ú", "\\{\\\\'U\\}");
		result = result.replaceAll("Á", "\\{\\\\'A\\}");
		result = result.replaceAll("É", "\\{\\\\'E\\}");
		result = result.replaceAll("Í", "\\{\\\\'I\\}");
		result = result.replaceAll("Ó", "\\{\\\\'O\\}");
		result = result.replaceAll("Ý", "\\{\\\\'Y\\}");
		result = result.replaceAll("Ć", "\\{\\\\'C\\}");

		result = result.replaceAll("û", "\\{\\\\^u\\}");// top accents
		result = result.replaceAll("â", "\\{\\\\^a\\}");
		result = result.replaceAll("ô", "\\{\\\\^o\\}");
		result = result.replaceAll("ê", "\\{\\\\^e\\}");
		result = result.replaceAll("ĉ", "\\{\\\\^c\\}");
		result = result.replaceAll("î", "\\{\\\\^i\\}");

		result = result.replaceAll("Û", "\\{\\\\^U\\}");
		result = result.replaceAll("Ĉ", "\\{\\\\^C\\}");
		result = result.replaceAll("Â", "\\{\\\\^A\\}");
		result = result.replaceAll("Ê", "\\{\\\\^E\\}");
		result = result.replaceAll("Î", "\\{\\\\^I\\}");
		result = result.replaceAll("Ô", "\\{\\\\^O\\}");

		result = result.replaceAll("ñ", "\\{\\\\~n\\}");// top tildas
		result = result.replaceAll("ã", "\\{\\\\~a\\}");
		result = result.replaceAll("õ", "\\{\\\\~o\\}");

		result = result.replaceAll("Ñ", "\\{\\\\~N\\}");
		result = result.replaceAll("Ã", "\\{\\\\~A\\}");
		result = result.replaceAll("Õ", "\\{\\\\~O\\}");

		result = result.replaceAll("å", "\\{\\\\ra\\}");// top cirlce
		result = result.replaceAll("Å", "\\{\\\\rA\\}");

		result = result.replaceAll("æ", "\\{\\\\ae\\}");// special french symbols
		result = result.replaceAll("Æ", "\\{\\\\AE\\}");
		result = result.replaceAll("œ", "\\{\\\\oe\\}");
		result = result.replaceAll("Œ", "\\{\\\\OE\\}");
		result = result.replaceAll("ø", "\\{\\\\o\\}");
		result = result.replaceAll("Ø", "\\{\\\\O\\}");

		return result;

	}

	/*
	 * returns the formatted String, according to the set format
	 */
	@Override
	public String toString() {
		return getFormattedString();

	}

	/**
	 * parses and adds values to the different Citation attributes
	 * 
	 * @param citationString
	 *            String with values to assign
	 */
	private void addAttributes(String[] citationString) {
		// reset();
		Log.log("CITATION.addAttributes: adding attributes for " + this.getClass());
		final List<String> additionalEntries = new ArrayList<String>();
		// find attributes and values and write to Citation
		String attribute = "";
		String value = "";
		for (final String element : citationString) {// Log.log("CITATION.addAttributes: "+citationString[i]);
			final int equalsPos = element.indexOf("=");
			// Log.log("CITATION.addAttributes.indexOf('='): "+equalsPos);
			if (equalsPos > -1) {
				attribute = element.substring(0, equalsPos).trim().toLowerCase();
				value = element.substring(equalsPos + 1).trim();

			}
			if (!value.equals("") && !value.equals(" ")) {
				Log.log("CITATION.addAttributes.ValuePair: " + attribute + ": " + value);

				if (attribute.toLowerCase().equals("title")) {
					title = value;

					additionalEntries.add(attribute + ":" + value);

				} else if (attribute.toLowerCase().equals("xref")) {
					xref = value;

				} else if (attribute.toLowerCase().equals("author")) {
					splitAuthors(value);// c.author = value;

				} else if (attribute.toLowerCase().equals("year")) {
					year = value;

				} else if (attribute.toLowerCase().equals("booktitle")) {
					booktitle = value;

				} else if (attribute.toLowerCase().equals("pages")) {
					pages = value;

				} else if (attribute.toLowerCase().equals("series")) {
					series = value;

				} else if (attribute.toLowerCase().equals("publisher")) {
					publisher = value;

				} else if (attribute.toLowerCase().equals("address")) {
					address = value;

				} else if (attribute.toLowerCase().equals("editor")) {
					splitEditors(value);

				} else if (attribute.toLowerCase().equals("school")) {
					school = value;

				} else if (attribute.toLowerCase().equals("url")) {
					url = value;

				} else if (attribute.toLowerCase().equals("chapter")) {
					chapter = value;

				} else if (attribute.toLowerCase().equals("institution")) {
					institution = value;

				} else if (attribute.toLowerCase().equals("journal")) {
					journal = value;

				} else if (attribute.toLowerCase().equals("note")) {
					note = value;

				} else if (attribute.toLowerCase().equals("edition")) {
					edition = value;

				} else if (attribute.toLowerCase().equals("howpublished")) {
					howpublished = value;

				} else if (attribute.toLowerCase().equals("month")) {
					month = value;

				} else if (attribute.toLowerCase().equals("number")) {
					number = value;

				} else if (attribute.toLowerCase().equals("organization")) {
					organization = value;

				} else if (attribute.toLowerCase().equals("type")) {
					type = value;

				} else if (attribute.toLowerCase().equals("volume")) {
					volume = value;

				} else if (attribute.toLowerCase().equals("location")) {
					location = value;

				} else if (attribute.toLowerCase().equals("isbn")) {
					isbn = value;

				} else if (attribute.toLowerCase().equals("numpages")) {
					numpages = value;

				} else if (attribute.toLowerCase().equals("doi")) {
					doi = value;

				} else if (attribute.toLowerCase().equals("issn")) {
					issn = value;

				} else {
					additionalEntries.add(attribute + ":" + value);
					this.additionalEntries = additionalEntries;
				}
			}

		}

		// / eliminate eventually double parsed entries
		final HashSet h = new HashSet(additionalEntries);
		additionalEntries.clear();
		additionalEntries.addAll(h);
	}

	private void createCitation(final int identification, final String[] valuePairArray) {

		id = identification;
		addAttributes(valuePairArray);
		checkRequired();

	}

	private String getEditorsString() {

		String editorsString = "";
		try {
			if (editors.isEmpty() || (editors.get(0)[1] == ""))  // no editors

			{

				editorsString = " ";
			} else if (Boolean.parseBoolean(settings[3]) && (editors.size() > Integer.parseInt(settings[5]))) // use et al
			{
				int toShow = Integer.parseInt(settings[6]);

				if (toShow > editors.size()) {
					toShow = editors.size();
				}

				for (int i = 0; i < toShow; i++) {
					String tempeditorsString = settings[0];

					if (Boolean.parseBoolean(settings[2])) { // if abbreviate
						try {
							tempeditorsString = tempeditorsString.replaceAll("\\{first\\}", editors.get(i)[0].substring(0, 1).toUpperCase() + ".");
							tempeditorsString = tempeditorsString.replaceAll("\\{last\\}", editors.get(i)[1]);
						} catch (Exception e) {
						}

					} else { // if NOT abbreviate
						tempeditorsString = tempeditorsString.replaceAll("\\{first\\}", editors.get(i)[0]);
						tempeditorsString = tempeditorsString.replaceAll("\\{last\\}", editors.get(i)[1]);

					}
					editorsString += (tempeditorsString + ", ");
				}

				editorsString = editorsString + " " + settings[4];
			} else { // no use of et al
				for (int a = 0; a < editors.size(); a++) {
					String tempeditorsString = settings[0];

					if (Boolean.parseBoolean(settings[2])) {// abbreviate

						if (!editors.get(a)[0].isEmpty()) {
							try {
								tempeditorsString = tempeditorsString.replaceAll("\\{first\\}", editors.get(a)[0].substring(0, 1).toUpperCase() + ".");
							} catch (Exception e) {
							}
						} else {
							tempeditorsString = tempeditorsString.replaceAll("\\{first\\}", "");
						}

						tempeditorsString = tempeditorsString.replaceAll("\\{last\\}", editors.get(a)[1]);

					} else {  // not abbreviate
						tempeditorsString = tempeditorsString.replaceAll("\\{first\\}", editors.get(a)[0]);
						tempeditorsString = tempeditorsString.replaceAll("\\{last\\}", editors.get(a)[1]);

					}

					if ((editors.get(a)[0].equals("") | editors.get(a)[1].equals("")) & tempeditorsString.contains(",")) {
						tempeditorsString.replaceAll(",", "");
					}
					if (editors.size() == 1) {
						editorsString = tempeditorsString;
					}
					if ((editors.size() > 1) & (a < (editors.size() - 1))) {
						editorsString += tempeditorsString + ", ";
					}
					if ((editors.size() > 1) & (a == (editors.size() - 1))) {
						editorsString += settings[1] + tempeditorsString;
					}
				}

// last
// ", "
			}
		} catch (final Exception e) {
			System.err.println("Exception occured parsing editors. (" + id + ")");
			e.printStackTrace();
		}

		if (editorsString.endsWith(",")) editorsString = editorsString.substring(0, editorsString.lastIndexOf(','));

		return editorsString;

	}

	// indexfunktion zum Parser!
	private String getIndex() {

		String result = "";

		if (Settings.indexFormat == 0) return "";
		else {
			result = index.toString();
		}

		return prePost[22][0] + result + prePost[22][1];
	}

	/**
	 * @param authors
	 * @return <code>true</code>, if more than one author, <code>false</code> otherwise
	 */
	private Boolean moreThanOneAuthor(String authors) {
		Log.log("CITATION.moreThanOneAuthor: multiple authors found: " + authors.contains(" and "));
		return (authors.contains(" and ") | authors.contains(" AND "));
	}

	/**
	 * @param editors
	 * @return <code>true</code>, if more than one editor, <code>false</code> otherwise
	 */
	private Boolean moreThanOneEditor(String editors) {
		Log.log("CITATION.moreThanOneEditor: multiple editors found: " + editors.contains(" and "));
		return (editors.contains(" and "));
	}

	/**
	 * Splits and adds one or multiple author(s) to the Citation <code>c</code>
	 * 
	 * @param c
	 * @param author
	 */
	private void splitAuthors(String author) {

		Log.log("CITATION.splitAuthors: splitting authors...");

		String[] tempAuthorNames = null;

		// +++++++++++++++++++++ ONLY ONE AUTHOR ++++++++++++++++++++++++++
		if (moreThanOneAuthor(author) == false) // only one author, no ' and '
												// in String
		{
			tempAuthorNames = new String[2];

			if (author.trim().lastIndexOf(" ") == -1) // only single word
			{
				tempAuthorNames[0] = "";
				tempAuthorNames[1] = author.trim();
				Log.log("CITATION.splitAuthors: only single name found: " + author);
			} else if (author.contains(",")) // author name like "last, first"
			{
				tempAuthorNames[0] = author.substring(author.indexOf(",") + 1, author.length()).trim(); // first
																										// name
				tempAuthorNames[1] = author.substring(0, author.indexOf(",")).trim(); // last
																						// name

			}

			else { // author name like "first last"

				tempAuthorNames[0] = author.substring(0, author.indexOf(" ")).trim(); // first
																						// name
				tempAuthorNames[1] = author.substring(author.indexOf(" "), author.length()).trim(); // last
																									// name
			}
			authors.add(tempAuthorNames);

			Log.log("CITATION.spltAuthors: added " + tempAuthorNames.toString());
		}

		else {
			if (author.contains(" and "))

			{
				splitAuthors(author.substring(0, author.indexOf(" and ")).trim());
				splitAuthors(author.substring(author.indexOf(" and ") + 5, author.length()).trim());
			} else if (author.contains(" AND "))

			{
				splitAuthors(author.substring(0, author.indexOf(" AND ")).trim());
				splitAuthors(author.substring(author.indexOf(" AND ") + 5, author.length()).trim());
			}

		}

		// --------------------- ONLY ONE AUTHOR --------------------------

		/* +++++++++++++++++++++ MORE AUTHORS ++++++++++++++++++++++++++ */

	}

	/**
	 * Splits and adds one or multiple author(s) to the Citation <code>c</code>
	 * 
	 * @param c
	 * @param editor
	 */
	private void splitEditors(String editor) {

		Log.log("CITATION.splitEdiors: splitting editors...");

		// Log.log("CITATION:splitAuthors :"+author);
		String[] tempEditorNames = null;

		// +++++++++++++++++++++ ONLY ONE AUTHOR ++++++++++++++++++++++++++
		if (moreThanOneEditor(editor) == false) // only one author, no ' and '
												// in String
		{
			tempEditorNames = new String[2];

			if (editor.trim().lastIndexOf(" ") == -1) // only single word
			{
				tempEditorNames[0] = "";
				tempEditorNames[1] = editor.trim();
				Log.log("CITATION.splitEditors: only single name found: " + editor);
			} else if (editor.contains(",")) // author like "last, first"
			{
				tempEditorNames[0] = editor.substring(editor.indexOf(",") + 1, editor.length()).trim(); // first
																										// name
				tempEditorNames[1] = editor.substring(0, editor.indexOf(",")).trim(); // last
																						// name

				// Log.log("ONE AUTHOR LAST,FIRST: "+tempAuthorNames[0]+" "+tempAuthorNames[1]);
			}

			else { // author name like "first last"

				tempEditorNames[0] = editor.substring(0, editor.lastIndexOf(" ")).trim(); // first
																							// name
				tempEditorNames[1] = editor.substring(editor.lastIndexOf(" "), editor.length()).trim(); // last
																										// name
			}
			editors.add(tempEditorNames);

			Log.log("CITATION.splitEditors: added " + tempEditorNames.toString());
		}

		else {
			splitEditors(editor.substring(0, editor.indexOf(" and ")));
			splitEditors(editor.substring(editor.indexOf(" and ") + 5, editor.length()));

		}

		// --------------------- ONLY ONE AUTHOR --------------------------

		/* +++++++++++++++++++++ MORE AUTHORS ++++++++++++++++++++++++++ */

	}

	private String trimFailSafe(String s) // TODO
	{

		String temp = s;
		// find two non-alphabetic characters in a row and remove it
		final Pattern p2 = Pattern.compile("\\s[.]+");
		final Matcher m2 = p2.matcher(temp);
		while (m2.find()) {
			// Log.log("-----------PATTERN: '<...>,. <...>' found: "+m2.group(0));
			temp = m2.replaceAll("");
		}

		// find two non-alphabetic characters in a row and remove it
		final Pattern p1 = Pattern.compile("\\s[,]+");
		final Matcher m1 = p1.matcher(temp);
		while (m1.find()) {
			// Log.log("-----------PATTERN: '<...>,. <...>' found: "+m2.group(0));
			temp = m2.replaceAll("");
		}

		// find two whitespaces characters in a row and remove it
		final Pattern p20 = Pattern.compile("[\\s\\:]{8,10}");
		final Matcher m20 = p20.matcher(temp);
		while (m20.find()) {
			// Log.log("-----------PATTERN: '<...>,. <...>' found: "+m2.group(0));
			temp = m20.replaceAll(" ");
		}

		// find two non-alphabetic characters in a row and remove it
		final Pattern p21 = Pattern.compile("[\\[\\]\\(\\)]{2,10}");
		final Matcher m21 = p21.matcher(temp);
		while (m21.find()) {
			// Log.log("-----------PATTERN: '<...>,. <...>' found: "+m2.group(0));
			temp = m21.replaceAll("");
		}

// remove characters between unassigned patterns, ex. <xxx>,.-<xxx>
		final Pattern p3 = Pattern.compile("\\{\\w+\\}[,\\.\\-\\_\\s\\:]+\\{\\w+\\}"); // find signs between empty <tags> and remove it

		final Matcher m3 = p3.matcher(temp);
		while (m3.find()) {
			// Log.log("-----------PATTERN: '<...>xxx<...>' found: "+m3.group(0));
			temp = m3.replaceAll("");
			m3.reset(temp);
		}

// remove patterns, ex. (<xxx>)
		final Pattern p4 = Pattern.compile("\\(\\{\\w+\\}\\)");
		final Matcher m4 = p4.matcher(temp);
		while (m4.find()) {
			// Log.log("-----------PATTERN: '(<...>)' found: "+m4.group(0));
			temp = m4.replaceAll("");
			m4.reset(temp);
		}

// remove unassigned or already replaces patterns, ex. <xxx>
		final Pattern p = Pattern.compile("\\{\\w{3,10}\\}"); // find <...> patterns and
		// remove it
		final Matcher m = p.matcher(temp);
		while (m.find()) {
			// Log.log("-----------PATTERN: '<...>' found:"+m.group(0));
			temp = m.replaceAll("");
		}

// remove trailing characters, ex. ' ,:-_'
		final Pattern p5 = Pattern.compile("[\\s,\\:-_]+\\$");
		final Matcher m5 = p5.matcher(temp);

		while (m5.find()) {
			// Log.log("-----------PATTERN: ',.-{EOF}' found:"+m5.group(0));
			temp = m5.replaceAll("");
		}

// temp=temp.replaceAll("<>", "");

		// Log.log("CITATION.trimFailSafe sucessful.");
		temp = temp.replace("[]", ""); // catch empty [] brackets, since matcher, does not work! (?)

		temp = temp.replace("\\{\\w+\\}", "");

		temp = temp.replace("[.,]+\\w", "[.,]+ \\w");

		return temp;
	}

	/**
	 * checks, if all mandatory fiels exist
	 * 
	 * @return <code>true</code> if all required fields exists, <code>false</code> else
	 */
	protected Boolean checkRequired() {
		Log.log("CITATION.checkRequired: checking mandatory entries...");

		if (authors.isEmpty()) {
			final String[] s = new String[2];
			s[0] = "none";
			s[1] = "none";
			authors.add(s);
		}
		if (editors.isEmpty()) {
			final String[] s = new String[2];
			s[0] = "none";
			s[1] = "none";
			editors.add(s);
		}
		return true;
	}

	protected String getAuthorsString() {
		String authorsString = "";
		try {
			if (authors.isEmpty() || (authors.get(0)[1] == ""))  // no authors

			{

				authorsString = " ";
			} else if (Boolean.parseBoolean(settings[3]) && (authors.size() > Integer.parseInt(settings[5]))) // use et al
			{
				int toShow = Integer.parseInt(settings[6]);

				if (toShow > authors.size()) {
					toShow = authors.size();
				}

				for (int i = 0; i < toShow; i++) {
					String tempAuthorsString = settings[0];

					if (Boolean.parseBoolean(settings[2])) { // if abbreviate
						try {
							tempAuthorsString = tempAuthorsString.replaceAll("\\{first\\}", authors.get(i)[0].substring(0, 1).toUpperCase() + ".");
							tempAuthorsString = tempAuthorsString.replaceAll("\\{last\\}", authors.get(i)[1]);
						} catch (Exception e) {
						}

					} else { // if NOT abbreviate
						tempAuthorsString = tempAuthorsString.replaceAll("\\{first\\}", authors.get(i)[0]);
						tempAuthorsString = tempAuthorsString.replaceAll("\\{last\\}", authors.get(i)[1]);

					}
					if (i == (toShow - 1)) {
						authorsString += tempAuthorsString;
					} else {
						authorsString += (tempAuthorsString + ", ");
					}
				}

				authorsString = authorsString + " " + settings[4];

			} else { // no use of et al
				for (int a = 0; a < authors.size(); a++) {
					String tempAuthorsString = settings[0];

					if (Boolean.parseBoolean(settings[2])) {// abbreviate

						if (!authors.get(a)[0].isEmpty()) {
							try {
								tempAuthorsString = tempAuthorsString.replaceAll("\\{first\\}", authors.get(a)[0].substring(0, 1).toUpperCase() + ".");
							} catch (Exception e) {
							}
						} else {
							tempAuthorsString = tempAuthorsString.replaceAll("\\{first\\}", "");
						}

						tempAuthorsString = tempAuthorsString.replaceAll("\\{last\\}", authors.get(a)[1]);

					} else {  // not abbreviate
						tempAuthorsString = tempAuthorsString.replaceAll("\\{first\\}", authors.get(a)[0]);
						tempAuthorsString = tempAuthorsString.replaceAll("\\{last\\}", authors.get(a)[1]);

					}

					if ((authors.get(a)[0].equals("") | authors.get(a)[1].equals("")) & tempAuthorsString.contains(",")) {
						tempAuthorsString.replaceAll(",", "");
					}
					if (authors.size() == 1) {
						authorsString = tempAuthorsString;
					} else if ((authors.size() > 1) & (a < (authors.size() - 1))) {
						authorsString += tempAuthorsString + ", ";
					} else if ((authors.size() > 1) & (a == (authors.size() - 1))) {
						authorsString = authorsString.substring(0, authorsString.lastIndexOf(',')) + settings[1] + tempAuthorsString;
					}

				}

// last
// ", "
			}
		} catch (final Exception e) {
			System.err.println("Exception occured parsing authors. (" + id + ")");
			e.printStackTrace();
		}

		if (authorsString.endsWith(",")) authorsString = authorsString.substring(0, authorsString.lastIndexOf(','));

		return authorsString;

	}

	/**
	 * formats Citation according to <code>sample</code>.
	 * 
	 * @param sample
	 *            the sample format String
	 * @return the formatted String
	 */
	protected String getFormattedString() {

		// this.prePost = Settings.prePost;
		// this.settings = Settings.settings;
		String formattedString = format;

		if (!title.equals("")) {
			formattedString = formattedString.replace("{title}", prePost[17][0] + title + prePost[17][1]);
		} else {
			formattedString = formattedString.replace("{title}", " ");
		}

		if (!authors.isEmpty()) {
			formattedString = formattedString.replace("{author}", prePost[1][0] + getAuthorsString() + prePost[1][1]);
		} else {
			formattedString = formattedString.replace("{author}", " ");
		}

		if (!year.equals("")) {
			formattedString = formattedString.replace("{year}", prePost[21][0] + year + prePost[21][1]);
		} else {
			formattedString = formattedString.replace("{year}", " ");
		}

		if (!journal.equals("")) {
			formattedString = formattedString.replace("{journal}", prePost[8][0] + journal + prePost[8][1]);
		} else {
			formattedString = formattedString.replace("{journal}", " ");
		}

		if (!number.equals("")) {
			formattedString = formattedString.replace("{number}", prePost[11][0] + number + prePost[11][1]);
		} else {
			formattedString = formattedString.replace("{number}", " ");
		}

		if (!pages.equals("")) {
			formattedString = formattedString.replace("{pages}", prePost[13][0] + pages + prePost[13][1]);
		} else {
			formattedString = formattedString.replace("{pages}", " ");
		}

		if (!(volume.equals("")) & (volume != null)) {
			formattedString = formattedString.replace("{volume}", prePost[20][0] + volume + prePost[20][1]);
		} else {
			formattedString = formattedString.replace("{volume}", " ");
		}

		if (!month.equals("")) {
			formattedString = formattedString.replace("{month}", prePost[9][0] + month + prePost[9][1]);
		} else {
			formattedString = formattedString.replace("{month}", " ");
		}

		if (!note.equals("")) {
			formattedString = formattedString.replace("{note}", prePost[10][0] + note + prePost[10][1]);
		} else {
			formattedString = formattedString.replace("{note}", " ");
		}

		if (!editors.isEmpty()) {
			formattedString = formattedString.replace("{editor}", prePost[5][0] + getEditorsString() + prePost[5][1]);
		} else {
			formattedString = formattedString.replace("{editor}", " ");
		}

		if (!publisher.equals("")) {
			formattedString = formattedString.replace("{publisher}", prePost[14][0] + publisher + prePost[14][1]);
		} else {
			formattedString = formattedString.replace("{publisher}", " ");
		}

		if (!series.equals("")) {
			formattedString = formattedString.replace("{series}", prePost[16][0] + series + prePost[16][1]);
		} else {
			formattedString = formattedString.replace("{series}", " ");
		}

		if (!edition.equals("")) {
			formattedString = formattedString.replace("{edition}", prePost[4][0] + edition + prePost[4][1]);
		} else {
			formattedString = formattedString.replace("{edition}", " ");
		}

		if (!booktitle.equals("")) {
			formattedString = formattedString.replace("{booktitle}", prePost[2][0] + booktitle + prePost[2][1]);
		} else {
			formattedString = formattedString.replace("{booktitle}", " ");
		}

		if (!address.equals("")) {
			formattedString = formattedString.replace("{address}", prePost[0][0] + address + prePost[0][1]);
		} else {
			formattedString = formattedString.replace("{address}", " ");
		}

		if (!organization.equals("")) {
			formattedString = formattedString.replace("{organization}", prePost[12][0] + organization + prePost[12][1]);
		} else {
			formattedString = formattedString.replace("{organization}", " ");
		}

		if (!school.equals("")) {
			formattedString = formattedString.replace("{school}", prePost[15][0] + school + prePost[15][1]);
		} else {
			formattedString = formattedString.replace("{school}", " ");
		}

		if (!institution.equals("")) {
			formattedString = formattedString.replace("{institution}", prePost[7][0] + institution + prePost[7][1]);
		} else {
			formattedString = formattedString.replace("{institution}", " ");
		}

		if (!type.equals("")) {
			formattedString = formattedString.replace("{type}", prePost[18][0] + type + prePost[18][1]);
		} else {
			formattedString = formattedString.replace("{type}", " ");
		}

		if (!chapter.equals("")) {
			formattedString = formattedString.replace("{chapter}", prePost[3][0] + chapter + prePost[3][1]);
		} else {
			formattedString = formattedString.replace("{chapter}", " ");
		}

		if (!howpublished.equals("")) {
			formattedString = formattedString.replace("{howpublished}", prePost[6][0] + howpublished + prePost[6][1]);
		} else {
			formattedString = formattedString.replace("{howpublished}", " ");
		}

		if (!doi.equals("")) {
			formattedString = formattedString.replace("{doi}", prePost[24][0] + doi + prePost[24][1]);
		} else {
			formattedString = formattedString.replace("{doi}", " ");
		}

		if (!enumeration.equals("")) {
			formattedString = formattedString.replace("{enum}", prePost[26][0] + enumeration + prePost[26][1]);
		} else {
			formattedString = formattedString.replace("{enum}", prePost[26][0] + "" + prePost[26][1]);
		}

		if (!isbn.equals("")) {
			formattedString = formattedString.replace("{isbn}", prePost[25][0] + isbn + prePost[25][1]);
		} else {
			formattedString = formattedString.replace("{isbn}", " ");
		}

		if (!location.equals("")) {
			formattedString = formattedString.replace("{location}", prePost[23][0] + location + prePost[23][1]);
		} else {
			formattedString = formattedString.replace("{location}", " ");
		}

		if (!url.equals("")) {
			formattedString = formattedString.replace("{url}", prePost[19][0] + url + prePost[19][1]);
		} else {
			formattedString = formattedString.replace("{url}", " ");
		}

		while (formattedString.contains("<X>") && formattedString.contains("</X>")) {
			final int x = formattedString.indexOf("<X>");
			final int x2 = formattedString.indexOf("</X>");
			formattedString = formattedString.substring(0, x) + formattedString.substring(x + 3, x2).toUpperCase() + formattedString.substring(x2 + 4, formattedString.length());
		}

		formattedString = trimFailSafe(formattedString);
		if (index != null) {
			formattedString = getIndex() + " " + formattedString;
		}
		return formattedString;
	}

	protected String getSortString() {
		String sortString = "";

		for (int i = 0; i < authors.size(); i++) {
			sortString += authors.get(i)[1] + authors.size();
		}

		sortString += year; // add year
		sortString += title; // add title
		sortString += citationType; // add type

		if (!Settings.eliminateDuplicates) {
			sortString += hashCode(); // (safety)
		}

		return sortString;

	}

}
