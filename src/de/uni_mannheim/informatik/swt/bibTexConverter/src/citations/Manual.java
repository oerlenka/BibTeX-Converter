package de.uni_mannheim.informatik.swt.bibTexConverter.src.citations;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.Citation;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.settings.Settings;

/**
 * @author Oliver Erlenkämper
 */
public class Manual extends Citation {

	public Manual(int id, String[] valuePairArray) {
		super(id, valuePairArray);
		citationType = "Manual";
	}

	@Override
	protected Boolean checkRequired() {
		if (title.isEmpty()) {
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <title>");
			return false;
		}

		if (authors.isEmpty()) {
			authors.add(new String[] { "", "" });
			// Log.warning("Required field missing in "+this.getClass().getSimpleName()+" (ID="+id+") : <author>");
			return true;
		}

		return true;
	}

	@Override
	protected String getFormattedString() {
		prePost = Settings.prePost;
		format = Settings.ManualFormat;
		settings = Settings.settings;
		final String s = super.getFormattedString();

		return s;
	}

}
