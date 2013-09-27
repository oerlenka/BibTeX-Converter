package de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Import;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.gui.CustomizeGui;

public class ImportFormat {

	String input;

	public ImportFormat() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Bibtex Converter Format", "bcf"));
		fc.showOpenDialog(CustomizeGui.contentPane);

		input = getImportString(fc.getSelectedFile());

	}

	public String[] getFormat() {

		final String format = getTagContent("Format", input);

		final String[] output = new String[] { getTagContent("Article", format), getTagContent("Book", format), getTagContent("Booklet", format), getTagContent("Conference", format),
				getTagContent("Inbook", format), getTagContent("Incollection", format), getTagContent("Inproceedings", format), getTagContent("Manual", format),
				getTagContent("Mastersthesis", format), getTagContent("Misc", format), getTagContent("Phdthesis", format), getTagContent("Proceedings", format), getTagContent("Techreport", format),
				getTagContent("Unpublished", format)

		};

		return output;

	}

	public String[] getNames() {
		final String[] output = new String[] { getTagContent("Shortname", input), getTagContent("Name", input) };
		return output;

	}

	public String[][] getPrePost() {
		final String[][] result = new String[][] { new String[] { getTagContent("Prefix", getTagContent("Address", input)), getTagContent("Postfix", getTagContent("Address", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Author", input)), getTagContent("Postfix", getTagContent("Author", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Booktitle", input)), getTagContent("Postfix", getTagContent("Booktitle", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Chapter", input)), getTagContent("Postfix", getTagContent("Chapter", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Edition", input)), getTagContent("Postfix", getTagContent("Edition", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Editor", input)), getTagContent("Postfix", getTagContent("Editor", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Howpublished", input)), getTagContent("Postfix", getTagContent("Howpublished", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Institution", input)), getTagContent("Postfix", getTagContent("Institution", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Journal", input)), getTagContent("Postfix", getTagContent("Journal", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Month", input)), getTagContent("Postfix", getTagContent("Month", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Note", input)), getTagContent("Postfix", getTagContent("Note", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Number", input)), getTagContent("Postfix", getTagContent("Number", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Organization", input)), getTagContent("Postfix", getTagContent("Organization", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Pages", input)), getTagContent("Postfix", getTagContent("Pages", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Publisher", input)), getTagContent("Postfix", getTagContent("Publisher", input)) },
				new String[] { getTagContent("Prefix", getTagContent("School", input)), getTagContent("Postfix", getTagContent("School", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Series", input)), getTagContent("Postfix", getTagContent("Series", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Title", input)), getTagContent("Postfix", getTagContent("Title", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Type", input)), getTagContent("Postfix", getTagContent("Type", input)) },
				new String[] { getTagContent("Prefix", getTagContent("URL", input)), getTagContent("Postfix", getTagContent("URL", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Volume", input)), getTagContent("Postfix", getTagContent("Volume", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Year", input)), getTagContent("Postfix", getTagContent("Year", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Index", input)), getTagContent("Postfix", getTagContent("Index", input)) },
				new String[] { getTagContent("Prefix", getTagContent("Location", input)), getTagContent("Postfix", getTagContent("Location", input)) },
				new String[] { getTagContent("Prefix", getTagContent("DOI", input)), getTagContent("Postfix", getTagContent("DOI", input)) },
				new String[] { getTagContent("Prefix", getTagContent("ISBN", input)), getTagContent("Postfix", getTagContent("ISBN", input)) },
				new String[] { getTagContent("Prefix", getTagContent("ENUM", input)), getTagContent("Postfix", getTagContent("ENUM", input)) }

		};
		return result;
	}

	public String[] getSettings() {

		final String settings = getTagContent("Settings", input);

		final String[] s = new String[] { getTagContent("AuthorsFormat", settings), getTagContent("AuthorsSeparator", settings), getTagContent("AbbreviateAuthors", settings),
				getTagContent("UseEtAl", settings), getTagContent("EtAlString", settings), getTagContent("#EtAl", settings), getTagContent("#ThenShow", settings), };
		return s;
	}

	private String getImportString(File file) {
		String output = "";

		{
			FileInputStream fr;
			try {
				fr = new FileInputStream(file);
				final BufferedReader br = new BufferedReader((new InputStreamReader(fr)));
				String s = null;
				while ((s = br.readLine()) != null) {
					output += s;
				}
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return output;
	}

	private String getTagContent(String tag, String in) {
		String result = "";
		final String openTag = "<" + tag + ">";
		final String closeTag = "</" + tag + ">";

		result = in.substring(in.indexOf(openTag) + 2 + tag.length(), in.indexOf(closeTag));

		return result;
	}

}
