package de.uni_mannheim.informatik.swt.bibTexConverter.src.citations;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.Citation;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.settings.Settings;

/**
 * @author Oliver Erlenkämper
 */
public class Inbook extends Citation {

	public Inbook(int id, String[] valuePairArray) {
		super(id, valuePairArray);
		citationType = "InBook";

	}

	@Override
	protected Boolean checkRequired() {
		if (title.isEmpty()) {
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <title>");
			return false;
		}

		if (chapter.isEmpty() & pages.isEmpty()) {
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <chapter> or <pages>");
			return false;
		}
		if (publisher.isEmpty()) {
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <publisher>");
			return false;
		}
		if (year.isEmpty()) {
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <year>");
			return false;
		}

		if (authors.isEmpty()) {
			authors.add(new String[] { "", "" });

			if (editors.isEmpty()) {
				Log.warning("Required fields missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <author> or <editor>");
				return false;
			} else
				return true;
		}

		return true;
	}

	@Override
	protected String getFormattedString() {
		prePost = Settings.prePost;
		format = Settings.InbookFormat;
		settings = Settings.settings;
		final String s = super.getFormattedString();

		return s;
	}
}
