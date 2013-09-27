package de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Import;

/**
 * @author Oliver Erlenkämper
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import de.uni_mannheim.informatik.swt.bibTexConverter.src.Converter;
import de.uni_mannheim.informatik.swt.bibTexConverter.src.io.Log;

public class Import extends FileReader {
	String output = "";

	public Import(File file) throws Exception {
		super(file);
		FileInputStream fr;
		Converter.setStatus("Reading file " + file.getName() + " ...");
		try {
			fr = new FileInputStream(file);
			final BufferedReader br = new BufferedReader((new InputStreamReader(fr, getEncoding())));
			String s = null;
			while ((s = br.readLine()) != null) {
				output += s;
			}

			br.close();
			Converter.setStatus("done.\n");

		}

		catch (final Exception e) {
			// e.printStackTrace();
			Log.error("File not found");
			Converter.setStatus("File not found.\n");
		} finally {
			// Log.log(outputPane);
		}
	}

	public Import(FileDescriptor fd) {
		super(fd);
	}

	public Import(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String utf8Output = "";
		try {
			utf8Output = new String(output.getBytes(System.getProperty("file.encoding")));
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return utf8Output;
	}
}
