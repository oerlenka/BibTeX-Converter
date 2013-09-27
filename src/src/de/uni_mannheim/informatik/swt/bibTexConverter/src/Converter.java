package de.uni_mannheim.informatik.swt.bibTexConverter.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.gui.CustomizeGui;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.gui.GUI;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Export.Export;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Import.Import;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Import.ImportFormat;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.settings.Language;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.settings.Settings;

/**
 * @author Oliver Erlenkämper @
 */
public final class Converter {
	public static Export io;
	public static Parser p;
	public static Boolean showErrors = true;
	public static Boolean showWarnings = true;
	private static Boolean count = false;
	private static Thread ExportThread;
	private static Thread GUIThread;
	private static List<Citation> sortedCitationList = new LinkedList<Citation>();
	static List<Citation> citationsList = new LinkedList<Citation>();
	static GUI g = null;
	static final Log l = new Log();

	static Settings s = null;

	/**
	 * Adds the references in the input field to the reference list
	 * 
	 * @param newString
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void add(String newString) {

		busy(true);
		final List<Citation> oldCL = new ArrayList(getCitationList());
		int oldLength = oldCL.size();
		reset();

		final List<Citation> newCL = new ArrayList(Parser.parseCitations(newString));

		oldCL.addAll(newCL);

		setCitationsList(oldCL);
		updateGui();
		Log.log("done.");
		System.gc();

		if (showWarnings) {
			System.err.println(Log.getWarnings());
		}
		if (showErrors) {
			System.err.println(Log.getErrors());
		}
		busy(false);
		if (count) {
			countAttributes();
		}
		if (oldLength > citationsList.size()) {
			oldLength = citationsList.size();
		}
		Converter.setStatus("Finished. " + (citationsList.size() - oldLength) + " citations added. Total: " + citationsList.size() + " entries.");
	}

	/**
	 * starts the clean up mode for BibTeX files
	 */
	public static void cleanUpFile() {
		Export.cleanUpFile();
	}

	/**
	 * converts the <code>bibTexString</code> from the input field
	 * 
	 * @param bibTexString
	 */
	public static void convert(String bibTexString) {
		reset();
		busy(true);
		Settings.setFormat(GUI.cbbCitationFormat.getSelectedIndex());
		Converter.setStatus("Parsing citations string...");
		citationsList = Parser.parseCitations(bibTexString);

		updateGui();
		Log.log("done.");
		System.gc();

		if (showWarnings) {
			System.err.println(Log.getWarnings());
		}
		if (showErrors) {
			System.err.println(Log.getErrors());
		}
		busy(false);
		if (count) {
			countAttributes();
		}
		Converter.setStatus("Finished. Total: " + citationsList.size() + " entries.");

	}

	/**
	 * exports the collection to a BibTeX file
	 * 
	 * @param bibtexOutput
	 */
	public static void export2BibTeX() {
		Export.export2BibTeX(getBibtexOutput());

	}

	/**
	 * exports the collection to a HTML file
	 * 
	 * @param formattedString
	 */
	public static void export2HTML(String formattedString) {
		Export.export2HTML(formattedString);

	}

	/**
	 * exports the collection to a OpenOffice file
	 * 
	 * @param formattedString
	 */
	public static void export2ODT(String formattedString) {
		Export.export2ODT(formattedString);
	}

	/**
	 * exports the collection to a RTF file
	 * 
	 * @param text
	 */
	public static void export2RTF(String text) {
		Export.export2RTF(text);
	}

	/**
	 * exports the collection to a Word file
	 * 
	 * @param formattedString
	 */
	public static void export2Word(String formattedString) {
		Export.export2Word(formattedString);

	}

	/**
	 * exports a format to a .bcf file
	 * 
	 * @param f
	 *            formats
	 * @param s
	 *            settings
	 * @param p
	 *            pre-/posfixes
	 * @param n
	 *            names
	 */
	public static void exportFormat(String[] f, String[] s, String[][] p, String[] n) {
		Export.exportPattern(f, s, p, n);
	}

	/**
	 * creates a BibTeX output from the collection
	 * 
	 * @return BibTeX string
	 */
	public static String getBibtexOutput() {
		String result = "";
		List<Citation> resultList = null;
		if (Settings.eliminateDuplicates) {
			resultList = GUI.getOutput();
		} else {
			resultList = getCitationList();
		}

		for (final Citation c : resultList) {
			result += c.getBibtex() + ",\n\n";
		}
		return result;
	}

	/**
	 * returns the Citation list
	 * 
	 * @return
	 */
	public static List<Citation> getCitationList() {
		Converter.setStatus("Building citation list...");
		List<Citation> result = citationsList;
		Log.log("PARSER.getCitationList (original): " + citationsList);
		Log.log("PARSER.getCitationList (result): " + result);
		enumerate(result);
		index(result);
		result = sort(result);
		Log.log("PARSER.getCitationList (sorted): " + result);

		Log.log("PARSER.getCitationList (indexed): " + result);

		Log.log("PARSER.getCitationList: returning list...");
		Log.log("PARSER.getCitationList (new): " + result);

		return result;
	}

	/**
	 * generates the formatted output
	 * 
	 * @return the formatted strings
	 */
	public static String getFormattedStrings() {

		Converter.setStatus("Formatting output string ...");
		String output = new String("");

		for (final Citation c : getCitationList()) {
			output = output + "<p>" + c.getFormattedString() + "<br>";
		}
		Converter.setStatus("");
		return output;
	}

	/**
	 * imports a format .bcf file
	 */
	public static void importFormat() {
		final ImportFormat fip = new ImportFormat();
		CustomizeGui.setNames(fip.getNames());
		CustomizeGui.setFormat(fip.getFormat());
		CustomizeGui.setSettings(fip.getSettings());
		CustomizeGui.setPrePost(fip.getPrePost());
	}

	public static void main(String[] args) {
		s = new Settings();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (final Exception e1) {

			}
		}
		GUIThread = new Thread() {

			@Override
			public void run() {
				g = new GUI();
			}
		};
		GUIThread.start();

		ExportThread = new Thread() {

			@Override
			public void run() {
				io = new Export();
			}
		};
		ExportThread.start();
	}

	/**
	 * reads content from bibTex file
	 * 
	 * @param f
	 *            .bib-File with bibTex Citations
	 * @throws Exception
	 */
	public static String readFromFile() {
		busy(true);
		String path = "";
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Bibtex Files", "bib"));
		@SuppressWarnings("unused")
		final int value = fc.showOpenDialog(GUI.contentPane);
		String bibTexString = "";
		if (value == JFileChooser.APPROVE_OPTION) {
			try {
				final Import btfr = new Import(fc.getSelectedFile());
				bibTexString = btfr.toString();
				btfr.close();
				path = fc.getSelectedFile().getPath();
				setStatus("File sucessfully imported: " + path);

			} catch (final Exception e) {

			}
		}

		Log.log("CONVERTER.readFromFile: reading done.");

		GUI.setInput(bibTexString);
		busy(false);
		return path;
	}

	/**
	 * removes specified <code>citation</code> from the citations list
	 * 
	 * @param citation
	 */
	public static void removeFromCitationList(Citation citation) {

		final Boolean success = citationsList.remove(citation);

		Log.log("Parser.removeCitation: removed (" + success + "): " + citation.toString());
		Log.log("--> Citations-List: " + citationsList);
		Converter.updateGui();
		setStatus("Removed.");
	}

	/**
	 * resets the application
	 */
	@SuppressWarnings("unused")
	public static void reset() {

		Log.log("Resetting....");

		for (Citation c : citationsList) {
			c = null;
		}

		citationsList = new ArrayList<Citation>();
		sortedCitationList = new ArrayList<Citation>();

		Parser.reset();
		p = new Parser();
		Log.reset();
		Log.log("done.\n");
		setStatus("Resetted.");

	}

	/**
	 * sets the GUI status panel
	 * 
	 * @param message
	 */
	public static void setStatus(String message) {
		GUI.setStatus(" " + message);
		Log.log("GUI status updated: " + message);
	}

	/**
	 * translates the <code>message</code>
	 * 
	 * @param message
	 * @return tranlated message
	 */
	public static String translate(String message) {
		return Language.getTranslatedString(message);
	}

	/**
	 * updates the graphical user interface (e.g. after performed changes)
	 */
	public static void updateGui() {
		Log.log("CONVERTER.updateGui...");
		if (GUI.clearInput) {
		}
		GUI.updateOutput();
		if (count) {
			countAttributes();
		}

	}

	private static void busy(Boolean state) {

		GUI.setCursor(state);

	}

	private static void countAttributes() {

		final ArrayList<String> attribute = new ArrayList<String>();
		final ArrayList<Integer> count = new ArrayList<Integer>();

		final ArrayList<String> cType = new ArrayList<String>();
		final ArrayList<Integer> ccount = new ArrayList<Integer>();

		final List<Citation> resultList = getCitationList();

		for (final Citation c : resultList) {
			if (!cType.contains(c.citationType)) {
				cType.add(c.citationType);
				ccount.add(1);
			} else {
				ccount.set(cType.indexOf(c.citationType), ccount.get(cType.indexOf(c.citationType)) + 1);
			}

			try {
				for (final String s : c.additionalEntries) {

					final String attr = s.substring(0, s.indexOf(":"));
					// System.out.println(attr);

					if (!attribute.contains(c.citationType + "," + attr)) {
						attribute.add(c.citationType + "," + attr);
						count.add(1);
					} else {
						count.set(attribute.indexOf(c.citationType + "," + attr), count.get(attribute.indexOf(c.citationType + "," + attr)) + 1);
					}

				}
			} catch (final Exception e) {
				System.err.println("Error while counting! (" + c.id + ")");
			}
		}
		System.out.println("\n\n*************** Attributes count: *******************");
		for (int s = 0; s < attribute.size(); s++) {
			System.out.println(attribute.get(s) + "," + count.get(s));
		}
		System.out.println("\n\n");

		System.out.println("*************** Citations count: *******************");
		System.out.println("*************** " + resultList.size() + " citations examined *******************");
		for (int s = 0; s < cType.size(); s++) {
			System.out.println(cType.get(s) + "," + ccount.get(s));
		}
	}

	private static void enumerate(List<Citation> list) {

		final String[] enums = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

		final List<String> foundYears = new ArrayList<String>();

		for (int l = 0; l < list.size(); l++) {

			final Citation c = list.get(l);
			final String authorString = c.getAuthorsString();

			if (!foundYears.contains(authorString + c.year + c.enumeration)) {
				foundYears.add(authorString + c.year + c.enumeration);
			} else {
				int count = 0;
				final String match = authorString + c.year + c.enumeration;
				for (int i = 0; i < list.size(); i++) {

					final Citation c2 = list.get(i);

					final String authorString2 = c2.getAuthorsString();

					if ((authorString2 + c2.year + c2.enumeration).equals(match)) {
						c2.enumeration = enums[count];
						count++;
					}
				}
			}
		}

	}

	/**
	 * indexes the citationsStrings and sets the index attribute in the Citation instances
	 * 
	 * @param list
	 */

	private static void index(List<Citation> list) {
		Converter.setStatus("Indexing ...");
		Log.log("PARSER.indexCitationList: indexing...");
		for (final Citation c : list) {
			c.index = null;
		}

		if (Settings.indexCitations) {
			if (Settings.indexFormat == 1) {

				for (int i = 0; i < list.size(); i++) {
					list.get(i).index = String.valueOf(i + 1);
				}
			}

			else if (Settings.indexFormat == 2) {

				String index = "";
				try {
					for (int i = 0; i < list.size(); i++)

					{
						final Citation c = list.get(i);
						String a = "";

						if (c.authors.size() == 1) {
							if (c.authors.get(0)[1].length() >= 4) a = c.authors.get(0)[1].substring(0, 4);
							else
								a = c.authors.get(0)[1];
						} else if (c.authors.size() == 2) {
							a = c.authors.get(0)[1].substring(0, 2) + c.authors.get(1)[1].substring(0, 2);
						} else if (c.authors.size() == 3) {
							a = c.authors.get(0)[1].substring(0, 2) + c.authors.get(1)[1].substring(0, 1) + c.authors.get(2)[1].substring(0, 1);
						} else if (c.authors.size() >= 4) {
							for (int j = 0; j < 4; j++) {
								a += c.authors.get(j)[1].substring(0, 1);
							}
						}

						index = a + c.year.substring(2, 4) + c.enumeration;

						list.get(i).index = index;
					}
				} catch (Exception e) {
				}

			}
		}
		Converter.setStatus("");

	}

	private static void setCitationsList(List<Citation> newList) {
		citationsList = newList;
	}

	/**
	 * sorts citation list
	 * 
	 * @param list
	 * @return
	 * @return sortedList
	 */
	private static List<Citation> sort(List<Citation> list) {
		// Log.log(citationList);
		Converter.setStatus("Sorting ...");
		Log.log("PARSER.sortCitations: sorting...");

		if (!Settings.sortCitations) return citationsList;

		else

		{

			final Map<String, String> citationMap = new LinkedHashMap<String, String>();

			for (int i = 0; i < list.size(); i++) {

				if (list.get(i) == null) {
					Log.error("NULL found in citationList!");
				}

				try

				{

					citationMap.put(list.get(i).getSortString(), "" + i);
				} catch (final Exception e) {
					Log.error("CONVERTER: Unable to build map for " + list.get(i).title + ":");
					e.printStackTrace();
				}
			}

			final TreeMap<String, String> citationTree = new TreeMap<String, String>();
			citationTree.putAll(citationMap);

			String[] tempCitationStringArray = null;
			tempCitationStringArray = citationTree.values().toArray(new String[0]);

			final Citation[] citationArray = new Citation[tempCitationStringArray.length];

			for (int i = 0; i < tempCitationStringArray.length; i++) {
				citationArray[i] = list.get(Integer.parseInt(tempCitationStringArray[i]));
			}

			sortedCitationList = Arrays.asList(citationArray);
			Converter.setStatus("");
			return sortedCitationList;

		}
	}

}
