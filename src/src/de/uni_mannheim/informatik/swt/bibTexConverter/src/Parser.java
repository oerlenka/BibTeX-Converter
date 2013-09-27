package de.uni_mannheim.informatik.swt.bibTexConverter.src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Article;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Book;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Booklet;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Conference;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Inbook;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Incollection;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Inproceedings;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Manual;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Mastersthesis;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Misc;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Phdthesis;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Proceedings;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Techreport;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.citations.Unpublished;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;

public class Parser {
	// list with citations
	public static List<Citation> citationsList = new LinkedList<Citation>();
	private static int citationID = 1;

	/**
	 * Parses the input text for bibtex references
	 * 
	 * @param an
	 *            <code>input</code> text containing bibtex references
	 */
	public static List<Citation> parseCitations(String input) {

		Converter.setStatus("Parsing citations...");
		Log.log("PARSER:parseCitations - parsing citationsStrings...");

		final LinkedList<String> sC = splitCitations(input);
		final int size = sC.size();

		while (!sC.isEmpty()) {

			Converter.setStatus("Creating citiation " + ((size - sC.size()) + 1) + "/" + size);
			createCitationInstance(sC.getFirst());
			sC.removeFirst();

		}

		Converter.setStatus(citationsList.size() + " citations created.");

		return citationsList;
	}

	public static void reset() {
		citationID = 1;
		citationsList = new ArrayList<Citation>();
		Log.log("Parser resetted.\n");

	}

	private static String cleanString(String s) {

		Log.log("PARSER.cleanString - original string: " + s);

		s = s.replaceAll("\\\\([\\S]{0,1})\\{([\\w]){1,2}\\}", "\\{\\\\$1$2\\}");

		s = escapeChars(s);
		s = s.replaceAll("\\\\ss", "ß");
		s = s.replaceAll("\\{\\\\([\\w]+)\\}", "$1");
		s = s.replaceAll("\n", "");
		s = s.replaceAll("\\$", "");

		s = s.replaceAll("\\\\", "");

		Log.log("PARSER.cleanString - final string: " + s);

		return trim(escapeChars(s));
	}

	/**
	 * determines the citationType of the Citation <code>c</code> and creates specific Citation subtype
	 * 
	 * @param c
	 *            Citation string, from which an Citation subinstance has to be built
	 */
	private static void createCitationInstance(String c) {

		Log.log("PARSER.createCitationInstances - creating Citation...");

		String citationType = c.substring(0, c.indexOf("{"));
		citationType = citationType.trim();

		// remove citation citationType
		c = c.substring(c.indexOf("{") + 1, c.lastIndexOf("}") + 1);

		final String xref = c.substring(0, c.indexOf(","));

		// remove "header" including: citationType of reference,identifier
		c = c.substring(c.indexOf(",") + 1);

		// split entries into substrings
		String[] valuePairArrayWithoutXref = null;
		valuePairArrayWithoutXref = splitValues(c);
		final String[] valuePairArray = new String[valuePairArrayWithoutXref.length + 1];
		valuePairArray[0] = "xref=" + xref;
		for (int i = 1; i < valuePairArray.length; i++) {
			valuePairArray[i] = valuePairArrayWithoutXref[i - 1];
		}

		if (valuePairArray.length < 1) // nothing in there
		{
			citationType = "Error";
			Log.error("Error: malformed citation found (id=" + citationID + ")\n");
		}

		if (citationType.toLowerCase().equals("@article")) {
			final Article a = new Article(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@book")) {
			final Book a = new Book(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@booklet")) {
			final Booklet a = new Booklet(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@conference")) {
			final Conference a = new Conference(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@inbook")) {
			final Inbook a = new Inbook(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@incollection")) {
			final Incollection a = new Incollection(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@inproceedings")) {
			final Inproceedings a = new Inproceedings(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@manual")) {
			final Manual a = new Manual(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@mastersthesis")) {
			final Mastersthesis a = new Mastersthesis(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@misc")) {
			final Misc a = new Misc(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@phdthesis")) {
			final Phdthesis a = new Phdthesis(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@proceedings")) {
			final Proceedings a = new Proceedings(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@techreport")) {
			final Techreport a = new Techreport(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else if (citationType.toLowerCase().equals("@unpublished")) {
			final Unpublished a = new Unpublished(citationID, valuePairArray);
			citationID++;
			citationsList.add(a);

		} else {
			Log.error("Additional entry type found: " + citationType + "\n");
		}
	}

	private static String escapeChars(String text) {

		String s = new String(text);

		s = s.replaceAll("[\\{]?\\\\[\\{]?\"u[\\}]?[\\}]?", "ü"); // colons
		s = s.replaceAll("[\\{]?\\\\[\\{]?\"a[\\}]?[\\}]?", "ä");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\"o[\\}]?[\\}]?", "ö");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\"e[\\}]?[\\}]?", "ë");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\"i[\\}]?[\\}]?", "ï");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\"y[\\}]?[\\}]?", "ÿ");

		s = s.replaceAll("[\\{]?\\\\[\\{]?\"U[\\}]?[\\}]?", "Ü");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\"A[\\}]?[\\}]?", "Ä");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\"E[\\}]?[\\}]?", "Ë");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\"I[\\}]?[\\}]?", "Ï");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\"O[\\}]?[\\}]?", "Ö");

		s = s.replaceAll("[\\{]?\\\\[\\{]?`u[\\}]?[\\}]?", "ù");// accents
		s = s.replaceAll("[\\{]?\\\\[\\{]?`a[\\}]?[\\}]?", "à");
		s = s.replaceAll("[\\{]?\\\\[\\{]?`o[\\}]?[\\}]?", "ò");
		s = s.replaceAll("[\\{]?\\\\[\\{]?`e[\\}]?[\\}]?", "è");
		s = s.replaceAll("[\\{]?\\\\[\\{]?`i[\\}]?[\\}]?", "ì");

		s = s.replaceAll("[\\{]?\\\\[\\{]?`U[\\}]?[\\}]?", "Ù");
		s = s.replaceAll("[\\{]?\\\\[\\{]?`A[\\}]?[\\}]?", "À");
		s = s.replaceAll("[\\{]?\\\\[\\{]?`E[\\}]?[\\}]?", "È");
		s = s.replaceAll("[\\{]?\\\\[\\{]?`I[\\}]?[\\}]?", "Ì");
		s = s.replaceAll("[\\{]?\\\\[\\{]?`O[\\}]?[\\}]?", "Ò");

		s = s.replaceAll("[\\{]?\\\\[\\{]?'u[\\}]?[\\}]?", "ú");// reverse accents
		s = s.replaceAll("[\\{]?\\\\[\\{]?'a[\\}]?[\\}]?", "á");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'o[\\}]?[\\}]?", "ó");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'e[\\}]?[\\}]?", "é");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'i[\\}]?[\\}]?", "í");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'y[\\}]?[\\}]?", "ý");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'c[\\}]?[\\}]?", "ć");

		s = s.replaceAll("[\\{]?\\\\[\\{]?'U[\\}]?[\\}]?", "Ú");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'A[\\}]?[\\}]?", "Á");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'E[\\}]?[\\}]?", "É");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'I[\\}]?[\\}]?", "Í");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'O[\\}]?[\\}]?", "Ó");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'Y[\\}]?[\\}]?", "Ý");
		s = s.replaceAll("[\\{]?\\\\[\\{]?'C[\\}]?[\\}]?", "Ć");

		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^u[\\}]?[\\}]?", "û");// top accents
		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^a[\\}]?[\\}]?", "â");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^o[\\}]?[\\}]?", "ô");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^e[\\}]?[\\}]?", "ê");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^c[\\}]?[\\}]?", "ĉ");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^i[\\}]?[\\}]?", "î");

		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^U[\\}]?[\\}]?", "Û");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^C[\\}]?[\\}]?", "Ĉ");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^A[\\}]?[\\}]?", "Â");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^E[\\}]?[\\}]?", "Ê");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^I[\\}]?[\\}]?", "Î");
		s = s.replaceAll("[\\{]?\\\\[\\{]?\\^O[\\}]?[\\}]?", "Ô");

		s = s.replaceAll("[\\{]?\\\\[\\{]?~n[\\}]?[\\}]?", "ñ");// top tildas
		s = s.replaceAll("[\\{]?\\\\[\\{]?~a[\\}]?[\\}]?", "ã");
		s = s.replaceAll("[\\{]?\\\\[\\{]?~o[\\}]?[\\}]?", "õ");

		s = s.replaceAll("[\\{]?\\\\[\\{]?~N[\\}]?[\\}]?", "Ñ");
		s = s.replaceAll("[\\{]?\\\\[\\{]?~A[\\}]?[\\}]?", "Ã");
		s = s.replaceAll("[\\{]?\\\\[\\{]?~O[\\}]?[\\}]?", "Õ");

		s = s.replaceAll("[\\{]?\\\\[\\{]?ra[\\}]?[\\}]?", "å");// top cirlce
		s = s.replaceAll("[\\{]?\\\\[\\{]?rA[\\}]?[\\}]?", "Å");

		s = s.replaceAll("[\\{]?\\\\[\\{]?ae[\\}]?[\\}]?", "æ");// special french symbols
		s = s.replaceAll("[\\{]?\\\\[\\{]?AE[\\}]?[\\}]?", "Æ");
		s = s.replaceAll("[\\{]?\\\\[\\{]?oe[\\}]?[\\}]?", "œ");
		s = s.replaceAll("[\\{]?\\\\[\\{]?OE[\\}]?[\\}]?", "Œ");
		s = s.replaceAll("[\\{]?\\\\[\\{]?o[\\}]?[\\}]?", "ø");
		s = s.replaceAll("[\\{]?\\\\[\\{]?O[\\}]?[\\}]?", "Ø");

		return s;
	}

	private static LinkedList<String> splitCitations(String input) {
		final LinkedList<String> citations = new LinkedList<String>();

		input = input + ",";
		input = cleanString(input);

		final LinkedList<Integer> safetyIndexList = new LinkedList<Integer>();
		final Pattern p = Pattern.compile("@[\\w]+[\\s]*\\{");
		final Matcher m = p.matcher(input);

		while (m.find()) {
			Log.log("found citation!");
			safetyIndexList.add(m.start());
		}
		safetyIndexList.add(input.length()); // up to the end

		final int[] indices = new int[safetyIndexList.size()];
		for (int i = 0; i < safetyIndexList.size(); i++) {
			indices[i] = safetyIndexList.get(i);
		}
		java.util.Arrays.sort(indices);

		for (int endpos = 1; endpos < indices.length; endpos++) {
			final int startpos = endpos - 1;
			final String c = new String(input.substring(indices[startpos], indices[endpos]));
			citations.add(c);
		}

		return citations;

	}

	private static String[] splitValues(String input) {
		final ArrayList<String> values = new ArrayList<String>();

		Log.log("PARSER.splitValues Original string: " + input);

		input = input.substring(0, input.lastIndexOf('}')) + ",}";

		int countOpenBrackets = 0;
		int countCloseBrackets = 0;

		int lastI = 0;

		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '{') {
				countOpenBrackets++;
			}
			if (input.charAt(i) == '}') {
				countCloseBrackets++;
			}

			if ((countOpenBrackets == countCloseBrackets) & (input.charAt(i) == ',')) {

				if (!input.substring(lastI, i).isEmpty()) {
					Log.log("PARSER.splitValues: value-pair found: " + trimAttributes(input.substring(lastI, i)));
					values.add(trimAttributes(input.substring(lastI, i)));
				}

				countOpenBrackets = 0;
				countCloseBrackets = 0;
				lastI = i + 1;
				i++;
			}

		}

		final String[] result = new String[values.size()];
		for (int s = 0; s < values.size(); s++) {
			result[s] = values.get(s).trim();
		}
		return result;
	}

	/**
	 * removes trailing whitespaces and commas in string <code>string</code>
	 * 
	 * @param string
	 * @return trimmed string
	 */
	private static String trim(String string) {
		Log.log("PARSER.trim: trimming string...");
		final StringBuilder s2 = new StringBuilder(string);
		for (int i = s2.length() - 1; i >= 0; i--)
			if ((s2.charAt(i) == ' ') | (s2.charAt(i) == ',')) {
				s2.deleteCharAt(i);
			} else {
				i = -1;
			}
		return s2.toString();

	}

	private static String trimAttributes(String substring) {

		String result = substring;
		if (result.contains("{") & result.contains("}")) {
			result = result.replaceAll("\\{", "");
			result = result.replaceAll("\\}", "");
		} else {
			result = result.trim();
		}
		return result;
	}

}
