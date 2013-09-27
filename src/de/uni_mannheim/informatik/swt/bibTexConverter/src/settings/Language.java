package de.uni_mannheim.informatik.swt.bibTexConverter.src.settings;

/**
 * @author Oliver Erlenkämper
 */
public final class Language {
	/**
	 * translation method due to multi-language support
	 * 
	 * @param text
	 *            to be translated
	 * @return translated text
	 */
	public static String getTranslatedString(String text) {
		if (Settings.language.equals("English")) return text;
		else if (Settings.language.equals("Deutsch")) {
			if (text.equals("Select file")) return "Datei auswählen";
			else if (text.equals("Cancel")) return "Abbrechen";
			else if (text.equals("author")) return "Autor";
			else if (text.equals("article")) return "Artikel";
			else if (text.equals("book")) return "Buch";
			else if (text.equals("booklet")) return "Broschüre";
			else if (text.equals("conference")) return "Konferenz";
			else if (text.equals("incollection")) return "Sammlung";
			else if (text.equals("inproceedings")) return "Veranstaltungsbericht";
			else if (text.equals("inbook")) return "Buchauszug";
			else if (text.equals("manual")) return "Anleitung";
			else if (text.equals("masterthesis")) return "Masterarbeit";
			else if (text.equals("misc")) return "Sonstiges";
			else if (text.equals("phdthesis")) return "Doktorarbeit";
			else if (text.equals("techreport")) return "Fachbericht";
			else if (text.equals("unpublished")) return "Unveröffentlicht";
			else if (text.equals("title")) return "Titel";
			else if (text.equals("year")) return "Jahr";
			else if (text.equals("booktitle")) return "Buchtitel";
			else if (text.equals("publisher")) return "Verlag";
			else if (text.equals("editor")) return "Herausgeber";
			else if (text.equals("school")) return "Schule";
			else if (text.equals("chapter")) return "Kapitel";
			else if (text.equals("institution")) return "Einrichtung";
			else if (text.equals("journal")) return "Zeitschrift";
			else if (text.equals("note")) return "Anmerkung";
			else if (text.equals("pages")) return "Seiten";
			else if (text.equals("address")) return "Adresse";
			else if (text.equals("edition")) return "Ausgabe";
			else if (text.equals("howpublished")) return "Veröffentlichungsweg";
			else if (text.equals("month")) return "Monat";
			else if (text.equals("number")) return "Nummer";
			else if (text.equals("organization")) return "Organisation";
			else if (text.equals("series")) return "Reihe";
			else if (text.equals("citationType")) return "Typ";
			else if (text.equals("volume")) return "Band";
			else if (text.equals("Convert")) return "Konvertieren";
			else if (text.equals("Language")) return "Sprache";
			else if (text.equals("Please paste BibTex here...")) return "Bitte BibTex hier einfügen...";
			else if (text.equals("Copy to Clipboard")) return "In Zwischenablage...";
			else if (text.equals("File")) return "Datei";
			else if (text.equals("Import from file")) return "Aus Datei importieren";

			else if (text.equals("Clear input after conversion")) return "Eingabefeld nach Konvertierung leeren";
			else if (text.equals("sort")) return "sort.";
			else if (text.equals("Add")) return "Hinzufügen";
			else if (text.equals("Edit")) return "Bearbeiten";

			else if (text.equals("Remove")) return "Entfernen";
			else if (text.equals("About")) return "Über";
			else if (text.equals("done.")) return "fertig.";
			else if (text.equals("Export selected entry...")) return "Gewählten Eintrag exportieren...";
			else if (text.equals("Edit output format")) return "Ausgabeformat bearbeiten";
			else if (text.equals("File")) return "Datei";
			else if (text.equals("Import from file")) return "Aus Datei importieren";
			else if (text.equals("Settings")) return "Einstellungen";
			else if (text.equals("Automatic Conversion")) return "Automatische Konvertierung";
			else if (text.equals("Automatically start conversion when input changes?")) return "Konvertierung unmittelbar nach Eingabe starten?";
			else if (text.equals("Clear input after conversion?")) return "Eingabefeld nach konvertierung leeren?";
			else if (text.equals("Language")) return "Sprache";
			else if (text.equals("Automatic Modes")) return "Automatische Modi";
			else if (text.equals("Automatic Collection Mode")) return "Automatischer Sammel-Modus";
			else if (text.equals("Automatic Conversion Mode")) return "Automatischer Kovertierungs-Modus";
			else if (text
					.equals("This sets the Application to Auto Conversion Mode." + "\n" + "\nWhile active, you can copy and paste citations directly from" + "\nany textual source (e.g. documents, websites) into another " + "\ndocument (e.g. Word document) in the format set in the" + "\nmain application window." + "\n" + "\n" + "To change the desired output format, set the new format in the" + "\n" + "system tray icon context menu." + "\n" + "\n" + "To restore the application window, please use the context menu " + "\n" + "of the system tray icon.")) return "" + "Das Programm schaltet jetzt in den Automatischen Konvertierungs-Modus\n\n" + "Solange dieser Modus aktiv ist, können Sie Referenzen direkt\n" + "aus jeder Textquelle kopieren (z.B. Dokumente, Webseiten) und\n" + "direkt in andere Dokumente (z.B. ein Word Dokument) im eingestellten Format\n" + "einfügen.\n\n" + "Das gewünschte Ausgabeformat können Sie jederzeit über das Kontextmenü\n" + "des Systemmenü-Icons ändern.\n\n" + "Zum Wiederherstellen des Anwendungsfensters, nutzen Sie bitte das Systemmenü.";

			else if (text.equals("Clean up file")) return "Datei bereinigen";
			else if (text.equals("eliminates duplicates in a specific file")) return "eliminiert Duplikate in einer spezifischen Datei";

			else if (text.equals("Convert")) return "Konvertieren";
			else if (text.equals("start the conversion...")) return "Konvertierung starten...";
			else if (text.equals("Add BibTeX citations to collection")) return "BibTeX Refernzen zur Sammlung hinzufügen";
			else if (text.equals("Clear")) return "Leeren";
			else if (text.equals("Output format")) return "Ausgabeformat";

			else if (text.equals("Reference format")) return "Referenzen-Format";
			else if (text.equals("Please choose output format")) return "Bitte wählen Sie das Ausgabeformat";
			else if (text.equals("New")) return "Neu";
			else if (text.equals("Create new format")) return "Neues Format erstellen";
			else if (text.equals("Copy selected format into new reference entry")) return "Ausgewähltes Format als Vorlage verwenden";
			else if (text.equals("Copy")) return "Kopieren";
			else if (text.equals("Edit...")) return "Bearbeiten...";
			else if (text.equals("Delete")) return "Löschen";
			else if (text.equals("Delete selected entry")) return "Gewählten Eintrag löschen";
			else if (text.equals("Are you sure to delete ")) return "Wollen Sie diesen Eintrag wirklich löschen: ";
			else if (text.equals("sort the citations")) return "Referenzen sortieren";
			else if (text.equals("Index format:")) return "Indexierung:";
			else if (text.equals("Copy to Clipboard")) return "In Zwischenablage kopieren";
			else if (text.equals("Copy the output to the clipboard (for pasting into other applications, e.g. MS Word)")) return "Kopiert das Ergebnis in die Zwischenablage (zum Einfügen in andere Programme, z.B. MS Word";
			else if (text.equals("Copied to clipboard")) return "In die Zwischenablage kopiert.";
			else if (text.equals("copy the output automatically to the clipboard (after the conversion has finished)")) return "Ergebnis automatisch in die Zwischenablage kopieren (nach Beendigung der Konvertierung)";
			else if (text.equals("edit selected entry")) return "gewählten Eintrag bearbeiten";
			else if (text.equals("remove selected entry")) return "gewählten Eintrag entfernen";
			else if (text.equals("Resets the converter")) return "Reset";
			else if (text.equals("Citation added")) return "Referenz hinzugefügt";
			else if (text.equals("Collection now contains ")) return "Die Sammlung umfasst nun ";
			else if (text.equals(" citations.")) return " Referenzen.";
			else if (text.equals("Double klick to restore BibTextConverter.\nRight click for options.")) return "Doppelklicken zur Wiederherstellung des BibTeX Konverters\nRechtsklick für Optionen.";

			else if (text.equals("Restore")) return "Wiederherstellen";
			else if (text.equals("Change format")) return "Format wechseln";
			else if (text.equals("Format changed")) return "Format geändert";
			else if (text.equals("New Format: ")) return "Neues Format: ";

			else if (text.equals(Settings.WELCOME_MESSAGE)) return "Wilkommen im BibTexConverter!\n\nBitte fügen Sie Ihre BibTex Referenzen in das linke Textfeld ein oder laden Sie Referenzen aus einer vorhandenen .bib-Datei.\n\nBitte geben Sie unten Ihre Verarbeitungs-Präferenzen an und starten Sie die Umwandlung mit der 'Konvertieren'-Schaltfläche.";
			else if (text.equals("abbreviate first names")) return "Vornamen kürzen";
			else if (text.equals("sort")) return "sortieren";
			else if (text.equals("index")) return "indizieren";
			else if (text.equals("automatically")) return "automatisch";

			else if (text.equals("use 'et al.'")) return "mit 'et al.' abkürzen";
		}

		return text;
	}
}
