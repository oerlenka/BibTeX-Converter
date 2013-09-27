package de.uni_mannheim.informatik.swt.bibTexConverter.src.citations;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.Citation;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.settings.Settings;

/**
 * @author Oliver Erlenkämper
 */
public class Inproceedings extends Citation {

	public Inproceedings(int id, String[] valuePairArray) {
		super(id, valuePairArray);
		citationType = "InProceedings";
	}

	@Override
	protected Boolean checkRequired() {
		if (title.isEmpty()) {
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <title>");
			return false;
		}
		if (booktitle.isEmpty()) {
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <booktitle>");
			return false;
		}
		if (year.isEmpty()) {
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <year>");
			return false;
		}

		if (authors.isEmpty()) {
			authors.add(new String[] { "", "" });
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <author>");
			return false;
		}

		return true;
	}

	@Override
	protected String getFormattedString() {
		prePost = Settings.prePost;
		format = Settings.InproceedingsFormat;
		settings = Settings.settings;
		final String s = super.getFormattedString();

		return s;
	}
}
