package java2020.finalProject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.SecurityException;
import java.util.Formatter;
import java.util.FormatterClosedException;

public class SavesWriter {
	private static Formatter output; // outputs text to a file
	private String fileName; // target file name

	public SavesWriter(String fileName) {
		this.fileName = fileName;
	}

	public void upDate(int temp) {
		openFile();
		upDateRecord(temp);
		closeFile();
	}

	public void openFile() {
		try {
			FileWriter fw = new FileWriter(fileName, false);//overwrite previous data
			output = new Formatter(fw);			
		} catch (SecurityException securityException) {
			System.err.println("Write permission denied. Terminating.");
			System.exit(1); // terminate the program
		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Error opening file. Terminating.");
			System.exit(1); // terminate the program
		} catch (IOException e) {
			System.err.println("I/O error. Terminating.");
			System.exit(1); // terminate the program
		}
	}

	// add records to file
	public void upDateRecord(int temp) {
		try {
			output.format("%d",temp);
		} catch (FormatterClosedException formatterClosedException) {
			System.err.println("Error writing to file. Terminating.");
		}
	}

	// close file
	public static void closeFile() {
		if (output != null)
			output.close();
	}
}