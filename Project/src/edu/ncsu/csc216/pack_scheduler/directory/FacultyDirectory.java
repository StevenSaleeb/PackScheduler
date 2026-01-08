package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Maintains a directory of all faculty enrolled at NC State.
 * All faculty must have a unique id. Can load and save faculty to a file, create directories, and remove and add faculty
 * @author Shreyash Jain
 */
public class FacultyDirectory {

	/** List of faculty in the directory */
	private LinkedList<Faculty> facultyDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Creates an empty faculty directory
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}
	
	/**
	 * Creates an empty Faculty directory. 
	 * All Faculty in the previous list are lost unless saved by the user.
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}
	
	/**
	 * Constructs the Faculty directory by reading in Faculty information from the given file. 
	 * @param file file name of the file containing list of Faculty
	 * @throws IllegalArgumentException if file is unable to be read
	 */
	public void loadFacultyFromFile(String file) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(file);
		}
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + file);
		}
	}
	
	/**
	 * Adds a Faculty to the directory.  Returns true if the faculty is added and false if
	 * the faculty is unable to be added because their id matches another faculty's id.
	 * 
	 * This method also hashes the faculty's password for internal storage.
	 * @param firstName first name of the Faculty
	 * @param lastName last name of the Faculty
	 * @param id id of the Faculty
	 * @param email email of the Faculty
	 * @param password password of the Faculty
	 * @param repeatPassword repeated password of the Faculty
	 * @param maxCourses max courses of the Faculty
	 * @return true if added Faculty, false otherwise
	 * @throws IllegalArgumentException if password is invalid or passwords do not match or if information about Faculty is invalid
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password, String repeatPassword, int maxCourses) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}

		hashPW = hashString(password);
		repeatHashPW = hashString(repeatPassword);
		
		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		
		Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
		if (getFacultyById(faculty.getId()) == null) {
			return facultyDirectory.add(faculty);
		}
		else {
			return false;
		}
	}
	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in base64 encoding.
	 * This allows the encoded digest to be safely copied, as it only uses [a-zA-Z0-9+/=].
	 * 
	 * @param toHash the String to hash 
	 * @return the encoded digest of the hash algorithm in base64
	 * @throws IllegalArgumentException if password cannot be hashed
	 */
	private static String hashString(String toHash) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(toHash.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Removes the Faculty with the given id
	 * @param id id
	 * @return true if removed Faculty with given id, false otherwise
	 */
	public boolean removeFaculty(String id) {
		Faculty faculty = getFacultyById(id);
		if (faculty != null) {
			return facultyDirectory.remove(faculty);
		}
		else {
			return false;
		}
	}
	
	/**
	 * Returns a 2D array, where each row is a Faculty and the columns are for the firstName, lastName, and id
	 * @return String 2D array containing all Faculty's first names, last names, and id.
	 */
	public String[][] getFacultyDirectory() {
		String[][] array = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			array[i][0] = facultyDirectory.get(i).getFirstName();
			array[i][1] = facultyDirectory.get(i).getLastName();
			array[i][2] = facultyDirectory.get(i).getId();
		}
		return array;
	}
	
	/**
	 * Saves all faculty in the directory to the given file
	 * @param file file name of the file to save the faculty directory to
	 * @throws IllegalArgumentException if file is unable to be written to
	 */
	public void saveFacultyDirectory(String file) {
		try {
			FacultyRecordIO.writeFacultyRecords(file, facultyDirectory);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + file);
		}
	}
	
	/**
	 * Searches for Faculty by id and returns the Faculty
	 * @param id id
	 * @return Faculty with given id or null if faculty with given id not found
	 */
	public Faculty getFacultyById(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equals(id)) {
				return facultyDirectory.get(i);
			}
		}
		return null;
	}
}
