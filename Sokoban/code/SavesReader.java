package java2020.finalProject;

import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SavesReader {
	private Scanner input;
	private String fileName; // target file name

	public SavesReader(String fileName) {
		this.fileName = fileName;
	}

	public int readSaves() {
		openFile();
		int temp = readRecords();

		closeFile();

		return temp;
	}

	public void openFile() {
		try {
			input = new Scanner(Paths.get(fileName));
		} catch (IOException ioException) {

			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
	}

	// Read all records and return an ArrayList of Player Objects
	public int readRecords() {
		int temp = 0;
		try {
			while (input.hasNext()) // while there is more to read
			{
				temp = input.nextInt();
			}
		} catch (NoSuchElementException elementException) {
			System.err.println("File improperly formed. Terminating.");
		} catch (IllegalStateException stateException) {
			System.err.println("Error reading from file. Terminating.");
		}

		return temp;
	} // end method readRecords

	// close file and terminate application
	public void closeFile() {
		if (input != null)
			input.close();
	}

}
