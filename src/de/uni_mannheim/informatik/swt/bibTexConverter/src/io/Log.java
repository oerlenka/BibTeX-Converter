package de.uni_mannheim.informatik.swt.bibTexConverter.src.io;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JOptionPane;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.Converter;

/**
 * 
 */

/**
 * @author Oliver Erlenkämper
 */
public final class Log {

	public static Boolean debug = false;

	private static String errors = "";

	private static int logNo = 1;
	private static String logs = "";
	private static String warnings = "";

	/**
	 * write message to error log
	 * 
	 * @param message
	 */
	public static void error(String message) {

		System.err.println(message);
		errors += message;
	}

	/**
	 * returns the occured errors
	 * 
	 * @return
	 */
	public static String getErrors() {
		if (!errors.isEmpty()) {
			Converter.setStatus("Finished with errors.");
			JOptionPane.showMessageDialog(null, "Finished with errors:\n" + errors, "Error!", JOptionPane.WARNING_MESSAGE);
			return (errors);
		}

		else
			return "No errors recorded.";
	}

	/**
	 * returns the log
	 * 
	 * @return
	 */
	public static String getLog() {
		if (logs.isEmpty()) return "No logs recorded.";
		else
			return logs;
	}

	/**
	 * returns the occured warnings
	 * 
	 * @return
	 */
	public static String getWarnings() {

		if (!warnings.isEmpty()) {
			Converter.setStatus("Finished with warnings.");
			JOptionPane.showMessageDialog(null, "Finished with warnings:\n" + warnings, "Warning!", JOptionPane.WARNING_MESSAGE);

			return (warnings);
		}

		else
			return "No warnings recorded.";
	}

	/**
	 * write message to log
	 * 
	 * @param message
	 */
	public static void log(String message) {

		final Calendar cal = Calendar.getInstance();
		cal.getTime();
		final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		if (debug) {
			System.out.println("\n" + logNo + "(" + sdf.format(cal.getTime()) + ") : " + message);
		}
		logNo++;
		logs += message;
	}

	/**
	 * reset the log
	 */
	public static void reset() {
		logNo = 1;
		errors = "";
		warnings = "";
		logs = "";
		Log.log("Log resetted.\n");
	}

	/**
	 * write warning to log
	 * 
	 * @param message
	 */
	public static void warning(String message) {

		if (debug) {
			System.out.println(message);
		}
		warnings += message + "\n";
	}

}
