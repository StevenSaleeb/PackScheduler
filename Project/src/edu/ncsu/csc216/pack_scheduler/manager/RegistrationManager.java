package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Class used for Registrar users allowing them access to a full course catalog and a student directory.
 * Registrars can create, load, save, add, and delete in the Course Catalog and Student Directory.
 * 
 * @author Liam Yeager
 * @author Steven Saleeb
 */
public class RegistrationManager {
	
	/** the directory of faculty */
	private FacultyDirectory facultyDirectory;
	
	/** A single instance of this class, used in the singleton design pattern to ensure only one object exists*/
	private static RegistrationManager instance;
	/** A catalog of all of the courses */
	private CourseCatalog courseCatalog;
	/** A list of all of the students */
	private StudentDirectory studentDirectory;
	/** The registrar that is described in the properties file */
	private User registrar;
	/** The current user that is using the manager */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** The file name containing the registrar information */
	private static final String PROP_FILE = "registrar.properties";
	

	/**
	 * Constructor for RegistrationManager
	 */ 
	private RegistrationManager() {
		createRegistrar();
		courseCatalog = new CourseCatalog();
		studentDirectory = new StudentDirectory();
		facultyDirectory = new FacultyDirectory();
	}
	
	/**
	 * Creates a registrar by reading the registrar properties file.
	 * Constructs a registrar class using the fields from the file.
	 */
	private void createRegistrar() {
		Properties prop = new Properties();
		
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			String hashPW = hashPW(prop.getProperty("pw"));
			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"), prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}
	
	/**
	 * Hashes a password. Returns a unique hash for each password using a
	 * hashing algorithm.
	 * @param pw the password being hashed
	 * @return the hash string representing the password
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Returns the instance of this class, or creates one if one has not been made yet. 
	 * Ensures only one object of this class exists at once.
	 * @return the single instance of this class
	 */
	public static synchronized RegistrationManager getInstance() {
		  if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}
	
	/**
	 * Returns the Course Catalog, a list of all of the courses.
	 * @return the Course Catalog, a list of all of the courses.
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}
	
	/**
	 * Returns the Student Directory, a list of all of the students.
	 * @return the Student Directory, a list of all of the students.
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	
	/**
	 * Checks a password against an id's password, and checks if the user is a registrar.
	 * Returns true if the password is equal, and returns true if the user is a registrar.
	 * @param id the id of the user logging in
	 * @param password the password input by the user logging in
	 * @return true if the password is equal, and returns true if the user is a registrar.
	 * @throws IllegalArgumentException if the user id does not correspond to a user.
	 */
	public boolean login(String id, String password) {
		String localHashPW = hashPW(password);
		// Check if a user is already logged in
		if (currentUser != null) {
			return false;
		}
		// Check if id is for registrar
		if (registrar.getId().equals(id)) {
			if(registrar.getPassword().equals(localHashPW)) {	
				currentUser = registrar;
				return true;
			}
		}
		else { // id not for registrar
			Student s = studentDirectory.getStudentById(id);
			if (s == null) {
				Faculty f = facultyDirectory.getFacultyById(id);
				if (f == null) {
					currentUser = null;
					throw new IllegalArgumentException("User doesn't exist.");
				}
				if (f.getPassword().equals(localHashPW)) {
					currentUser = f;
					return true;
				}
				return false;
			}
			if (s.getPassword().equals(localHashPW)) {
				currentUser = s;
				return true;
			}
		}
		return false;
	}

	/**
	 * Overrides the current user with the registrar user.
	 */
	public void logout() {
		currentUser = null; 
	}
	
	/**
	 * Returns the current user of the program
	 * @return the current user of the program
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Clears the data in the course catalog and the student directory
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}
	
	/**
	 * An inner class in the RegistrationManager class representing a Registrar. There should 
	 * only be one registrar for this program.
	 */
	private static class Registrar extends User {
	    /**
	     * Create a registrar user
	     * @param firstName the first name of the registrar
	     * @param lastName the last name of the registrar
	     * @param id the unique id of the registrar
	     * @param email the email of the registrar
	     * @param hashPW the hashed password of the registrar
	     */
	    public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
	        super(firstName, lastName, id, email, hashPW);
	    }
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}
	
	/**
	 * Returns the faculty directory that stores and manages all faculty records
	 * @return the faculty directory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}
	
	/**
	 * Adds the given course from the given faculty's schedule. Also adds
	 * the instructor id from the course.
	 * @param c the course
	 * @param f the faculty member
	 * @return true if the faculty can be added to the course
	 * @throws IllegalArgumentException if the course cannot be added
	 */
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if (getCurrentUser() != null && getCurrentUser().equals(registrar)) {
			return f.getSchedule().addCourseToSchedule(c);
		}
		else {
			throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Removes the given course from the given faculty's schedule. Also removes
	 * the instructor id from the course.
	 * @param c the course
	 * @param f the faculty member
	 * @return true if the faculty can be removed from the course
	 * @throws IllegalArgumentException if the user is not the registrar
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if (getCurrentUser() != null && getCurrentUser().equals(registrar)) {
			return f.getSchedule().removeCourseFromSchedule(c);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Resets a faculty memeber's schedule
	 * @param f the faculty member to have their schedule reset
	 * @throws IllegalArgumentException if the user is not the registrar
	 */
	public void resetFacultySchedule(Faculty f) {
		if (getCurrentUser() != null && getCurrentUser().equals(registrar)) {
			f.getSchedule().resetSchedule();
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
}