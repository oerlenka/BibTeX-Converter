package de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.Converter;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.gui.CustomizeGui;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.gui.GUI;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.settings.Settings;

public class Export {

	public static void cleanUpFile() {
		Settings.eliminateDuplicates = true;

		final int ok = JOptionPane.showConfirmDialog(GUI.contentPane, "Note: The selected file will be cleaned up,  \ni.e. all duplicates will be deleted.\n\nThis can't be undone!\n\nProceed?",
				"Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (ok == 0) {

			final String path = Converter.readFromFile();

			Converter.setStatus("Condensing...");
			Converter.convert(GUI.getInput());

			String ex = Converter.getBibtexOutput();

			ex = ex.replaceAll("\n", System.getProperty("line.separator"));

			try {
				final BufferedWriter out = new BufferedWriter(new FileWriter(path));
				out.write(ex);

				out.close();
			} catch (final IOException e) {
				System.out.println("Exception ");
			}

			Converter.reset();

			GUI.reset();

			if (!path.isEmpty()) {
				Converter.setStatus("File cleaned up: " + path);
			}
			JOptionPane.showMessageDialog(GUI.contentPane, "File " + path + "\nsucessfully cleaned up.", "Clean up succesfull", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public static void export2BibTeX(String s) {
		String ex = s;
		ex = ex.replaceAll("\n", System.getProperty("line.separator"));
		final JFileChooser fc = new JFileChooser();

		final File fil = new File("BibTeX Entries.bib");
		fc.setSelectedFile(fil);

		final int state = fc.showSaveDialog(GUI.contentPane);

		Converter.setStatus("File saved to " + fc.getSelectedFile().getPath());

		if (state == JFileChooser.APPROVE_OPTION) {
			try {
				final BufferedWriter out = new BufferedWriter(new FileWriter(fc.getSelectedFile().getPath()));
				out.write(ex);

				out.close();
			} catch (final IOException e) {
				System.out.println("Exception ");
			}
		}
	}

	public static void export2HTML(String content) {
		final String temp = System.getProperty("java.io.tmpdir");

		final DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
		final Long date = new Date().getTime();
		df.format(date);

		final String tempFileName = temp + "bibtexConverter_" + date + ".html";
		final String fileContent = "<html>\n\n<head>\n</head>\n\n<body>\n" + content + "</body>\n\n</html>";

		try {
			final FileWriter fw = new FileWriter(tempFileName);
			final BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fileContent);
			bw.close();
		} catch (final IOException e) {

			e.printStackTrace();
		}
		try {
			final Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + tempFileName);
			p.waitFor();
		} catch (final Exception e) {

		}

		System.out.println(tempFileName);
	}

	public static void export2ODT(String content) {
		final String temp = System.getProperty("java.io.tmpdir");

		final DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
		final Long date = new Date().getTime();
		df.format(date);

		final String tempFileName = temp + "bibtexConverter_" + date + ".odt";
		final String fileContent = "<html>\n\n<head>\n</head>\n\n<body>\n" + content + "</body>\n\n</html>";

		try {
			final FileWriter fw = new FileWriter(tempFileName);
			final BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fileContent);
			bw.close();
		} catch (final IOException e) {

			e.printStackTrace();
		}
		try {
			final Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + tempFileName);
			p.waitFor();
		} catch (final Exception e) {

		}

		System.out.println(tempFileName);
	}

	public static void export2RTF(String content) {
		final String temp = System.getProperty("java.io.tmpdir");

		final DateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
		final Long date = new Date().getTime();
		df.format(date);

		final String tempFileName = temp + "bibtexConverter_" + date + ".rtf";
		final String fileContent = "<html>\n\n<head>\n</head>\n\n<body>\n" + content + "</body>\n\n</html>";

		try {
			final FileWriter fw = new FileWriter(tempFileName);
			final BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fileContent);
			bw.close();
		} catch (final IOException e) {

			e.printStackTrace();
		}
		try {
			final Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + tempFileName);
			p.waitFor();
		} catch (final Exception e) {

		}

		System.out.println(tempFileName);
	}

	public static void export2Word(String content) {
		final String temp = System.getProperty("java.io.tmpdir");

		final Long date = new Date().getTime();

		final String tempFileName = temp + "bibtexConverter_" + date + ".doc";

		final String fileContent = "<html>\n\n<head>\n</head>\n\n<body>\n" + content + "</body>\n\n</html>";

		try {
			final FileWriter fw = new FileWriter(tempFileName);
			final BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fileContent);
			bw.close();
		} catch (final IOException e) {

			e.printStackTrace();
		}

		final String[] cmd = { "cmd", "/c", "start", "WINWORD.exe", "/f", tempFileName };
		try {
			final Process proc = Runtime.getRuntime().exec(cmd);
			proc.waitFor();
		} catch (final Exception e1) {

		}
	}

	public static void exportPattern(String[] f, String[] s, String[][] p, String[] names) {

		String ex = "";
		ex = "" + "\n\t<Name>" + names[1] + "</Name>" + "\n\t<Shortname>" + names[0] + "</Shortname>\n";
		ex += addAttribute("Format",
				addAttribute("Article", f[0]) + addAttribute("Book", f[1]) + addAttribute("Booklet", f[2]) + addAttribute("Conference", f[3]) + addAttribute("Inbook", f[4]) + addAttribute(
						"Incollection", f[5]) + addAttribute("Inproceedings", f[6]) + addAttribute("Manual", f[7]) + addAttribute("Mastersthesis", f[8]) + addAttribute("Misc", f[9]) + addAttribute(
						"Phdthesis", f[10]) + addAttribute("Proceedings", f[11]) + addAttribute("Techreport", f[12]) + addAttribute("Unpublished", f[13]));
		ex += "\n\n";

		ex += addAttribute("Settings",
				addAttribute("AuthorsFormat", s[0]) + addAttribute("AuthorsSeparator", s[1]) + addAttribute("AbbreviateAuthors", s[2]) + addAttribute("UseEtAl", s[3]) + addAttribute("EtAlString",
						s[4]) + addAttribute("#EtAl", s[5]) + addAttribute("#ThenShow", s[6]));

		ex += "\n\n";

		ex += "\n<PrePost>" + "\n\t<Address>" + "\n\t\t<Prefix>" + p[0][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[0][1] + "</Postfix>" + "\n</Address>" + "\n\t<Author>" + "\n\t\t<Prefix>" + p[1][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[1][1] + "</Postfix>" + "\n</Author>" + "\n\t<Booktitle>" + "\n\t\t<Prefix>" + p[2][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[2][1] + "</Postfix>" + "\n</Booktitle>" + "\n\t<Chapter>" + "\n\t\t<Prefix>" + p[3][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[3][1] + "</Postfix>" + "\n</Chapter>" + "\n\t<Edition>" + "\n\t\t<Prefix>" + p[4][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[4][1] + "</Postfix>" + "\n</Edition>" + "\n\t<Editor>" + "\n\t\t<Prefix>" + p[5][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[5][1] + "</Postfix>" + "\n</Editor>" + "\n\t<Howpublished>" + "\n\t\t<Prefix>" + p[6][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[6][1] + "</Postfix>" + "\n</Howpublished>" + "\n\t<Institution>" + "\n\t\t<Prefix>" + p[7][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[7][1] + "</Postfix>" + "\n</Institution>" + "\n\t<Journal>" + "\n\t\t<Prefix>" + p[8][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[8][1] + "</Postfix>" + "\n</Journal>" + "\n\t<Month>" + "\n\t\t<Prefix>" + p[9][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[9][1] + "</Postfix>" + "\n</Month>" + "\n\t<Note>" + "\n\t\t<Prefix>" + p[10][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[10][1] + "</Postfix>" + "\n</Note>" + "\n\t<Number>" + "\n\t\t<Prefix>" + p[11][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[11][1] + "</Postfix>" + "\n</Number>" + "\n\t<Organization>" + "\n\t\t<Prefix>" + p[12][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[12][1] + "</Postfix>" + "\n</Organization>" + "\n\t<Pages>" + "\n\t\t<Prefix>" + p[13][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[13][1] + "</Postfix>" + "\n</Pages>" + "\n\t<Publisher>" + "\n\t\t<Prefix>" + p[14][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[14][1] + "</Postfix>" + "\n</Publisher>" + "\n\t<School>" + "\n\t\t<Prefix>" + p[15][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[15][1] + "</Postfix>" + "\n</School>" + "\n\t<Series>" + "\n\t\t<Prefix>" + p[16][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[16][1] + "</Postfix>" + "\n</Series>" + "\n\t<Title>" + "\n\t\t<Prefix>" + p[17][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[17][1] + "</Postfix>" + "\n</Title>" + "\n\t<Type>" + "\n\t\t<Prefix>" + p[18][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[18][1] + "</Postfix>" + "\n</Type>" + "\n\t<URL>" + "\n\t\t<Prefix>" + p[19][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[19][1] + "</Postfix>" + "\n</URL>" + "\n\t<Volume>" + "\n\t\t<Prefix>" + p[20][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[20][1] + "</Postfix>" + "\n</Volume>" + "\n\t<Year>" + "\n\t\t<Prefix>" + p[21][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[21][1] + "</Postfix>" + "\n</Year>" + "\n\t<Index>" + "\n\t\t<Prefix>" + p[22][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[22][1] + "</Postfix>" + "\n</Index>" + "\n\t<Location>" + "\n\t\t<Prefix>" + p[23][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[23][1] + "</Postfix>" + "\n</Location>" + "\n\t<DOI>" + "\n\t\t<Prefix>" + p[24][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[24][1] + "</Postfix>" + "\n</DOI>" + "\n\t<ISBN>" + "\n\t\t<Prefix>" + p[25][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[25][1] + "</Postfix>" + "\n</ISBN>" + "\n\t<ENUM>" + "\n\t\t<Prefix>" + p[26][0] + "</Prefix>" + "\n\t\t<Postfix>" + p[26][1] + "</Postfix>" + "\n</ENUM>"

		+ "\n</PrePost>";

		ex = ex.replaceAll("\n", System.getProperty("line.separator"));
		final JFileChooser fc = new JFileChooser();

		final File fil = new File(names[0] + ".bcf");
		fc.setSelectedFile(fil);

		final int state = fc.showSaveDialog(CustomizeGui.contentPane);

		if (state == JFileChooser.APPROVE_OPTION) {
			try {
				final BufferedWriter out = new BufferedWriter(new FileWriter(fc.getSelectedFile().getPath()));
				out.write(ex);

				out.close();
			} catch (final IOException e) {
				System.out.println("Exception ");
			}
		}

	}

	private static String addAttribute(String attribute, String value) {
		return "<" + attribute + ">\n" + value + "\n</" + attribute + ">\n";
	}

	public Export() {
	}
}
