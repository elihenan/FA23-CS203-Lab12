package lab12;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class Encrypter {

	private int shift;
	private String encrypted;

	/**
	 * Default Constructor
	 */
	public Encrypter() {
		this.shift = 1;
		this.encrypted = "";
	}

	/**
	 * Non-default Constructor
	 * 
	 * @param s - custom shift amount
	 */
	public Encrypter(int s) {
		this.shift = s;
		this.encrypted = "";
	}

	/**
	 * Encrypts the content of a file and writes the result to another file.
	 *
	 * @param inputFilePath     the path to the file containing the text to be
	 *                          encrypted
	 * @param encryptedFilePath the path to the file where the encrypted text will
	 *                          be written
	 * @throws Exception if an error occurs while reading or writing the files
	 */
	public void encrypt(String inputFilePath, String encryptedFilePath) throws Exception {
		try {
			String message = readFile(inputFilePath);
			String result = "";
			String alphabet = "abcdefghijklmnopqrstuvwxyz";

			// traverses through each char and converts it according to shift
			for (int i = 0; i < message.length(); i++) {
				int position = alphabet.indexOf(String.valueOf(message.charAt(i)).toLowerCase());
				int newPosition = (shift + position) % 26;

				// checks if the original letter was upper or lower case
				if (Character.isUpperCase(message.charAt(i))) {
					String newLetter = String.valueOf(alphabet.charAt(newPosition)).toUpperCase();
					result = result + newLetter;
				} else if (Character.isLowerCase(message.charAt(i))) {
					String newLetter = String.valueOf(alphabet.charAt(newPosition));
					result = result + newLetter;
				} else {
					result = result + message.charAt(i);
				}
			}

			writeFile(result, encryptedFilePath);

		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}

	}

	/**
	 * Decrypts the content of an encrypted file and writes the result to another
	 * file.
	 *
	 * @param messageFilePath   the path to the file containing the encrypted text
	 * @param decryptedFilePath the path to the file where the decrypted text will
	 *                          be written
	 * @throws Exception if an error occurs while reading or writing the files
	 */
	public void decrypt(String messageFilePath, String decryptedFilePath) throws Exception {
		try {
			String message = readFile(messageFilePath);
			String result = "";
			String alphabet = "abcdefghijklmnopqrstuvwxyz";

			// traverses through each char and converts it according to shift
			for (int i = 0; i < message.length(); i++) {
				int position = alphabet.indexOf(String.valueOf(message.charAt(i)).toLowerCase());
				int newPosition = (position - shift) % 26;

				// makes sure there is not a bounds error
				if (newPosition < 0) {
					newPosition = alphabet.length() + newPosition;
				}
				
				// checks if the original letter was upper or lower case
				if (Character.isUpperCase(message.charAt(i))) {
					String newLetter = String.valueOf(alphabet.charAt(newPosition)).toUpperCase();
					result = result + newLetter;
				} else if (Character.isLowerCase(message.charAt(i))) {
					String newLetter = String.valueOf(alphabet.charAt(newPosition));
					result = result + newLetter;
				} else {
					result = result + message.charAt(i);
				}
			}

			writeFile(result, decryptedFilePath);

		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	/**
	 * Reads the content of a file and returns it as a string.
	 *
	 * @param filePath the path to the file to be read
	 * @return the content of the file as a string
	 * @throws Exception if an error occurs while reading the file
	 */
	private static String readFile(String filePath) throws Exception {
		String message = "";
		
		try (Scanner fileScanner = new Scanner(Paths.get(filePath))) {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				message = message + line + "\n";
			}
			fileScanner.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
		return message;
	}

	/**
	 * Writes data to a file.
	 *
	 * @param data     the data to be written to the file
	 * @param filePath the path to the file where the data will be written
	 */
	private static void writeFile(String data, String filePath) throws Exception {
		try (PrintWriter output = new PrintWriter(filePath)) {
			output.print(data);
			output.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	/**
	 * Returns a string representation of the encrypted text.
	 *
	 * @return the encrypted text
	 */
	@Override
	public String toString() {
		return encrypted;
	}
}
