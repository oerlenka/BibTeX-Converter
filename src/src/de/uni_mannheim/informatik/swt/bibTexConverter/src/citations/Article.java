package de.uni_mannheim.informatik.swt.bibTexConverter.src.citations;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.Citation;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.settings.Settings;

/**
 * @author Oliver Erlenkämper
 */
public class Article extends Citation {
	public Article(int id, String[] valuePairArray) {
		super(id, valuePairArray);
		citationType = "Article";

	}

	protected void buildAttributes(String bibtextCitation) {
	}

	@Override
	protected Boolean checkRequired() {
		if (title.isEmpty()) {
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <title>");
			return false;
		}
		if (journal.isEmpty()) {
			Log.warning("Required field missing in " + this.getClass().getSimpleName() + " (ID=" + id + ") : <journal>");
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
		format = Settings.ArticleFormat;
		settings = Settings.settings;

		return super.getFormattedString();
	}

}
