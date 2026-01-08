package edu.ncsu.csc216.pack_scheduler.user;

/**
 * A class representing someone who will use this scheduler program.
 * Users include a Student, Registrar, or Faculty. Each user can perform different functions.
 */
public abstract class User {

	/** Variable representing the first name of a student */
	private String firstName;
	/** Variable representing the last name of a student */
	private String lastName;
	/** Variable representing the unique id of a student */
	private String id;
	/** Variable representing the email of a student */
	private String email;
	/** Variable representing the password of a student */
	private String password;
	
	/**
	 * Constructor for the user class.
	 * @param firstName the first name of the user
	 * @param lastName the last name of the user
	 * @param id the unique id of the user
	 * @param email the email of the user
	 * @param password the password of the user
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
		
	}



	/**
	 * Gets the email of a student
	 * @return email of a student
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the password of a student
	 * @return password of a student
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the first name of a student
	 * @return first name of a student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the last name of a student
	 * @return last name of a student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the id of a student
	 * @return id of a student
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the first name of a student
	 * @param firstName first name of a student
	 * @throws IllegalArgumentException if first name is invalid
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the last name of a student
	 * @param lastName last name of a student
	 * @throws IllegalArgumentException if last name is invalid
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Sets the id of a student
	 * @param id id of a student
	 * @throws IllegalArgumentException if id is invalid
	 */
	public void setId(String id) {
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Sets the email of a student
	 * @param email email of a student
	 * @throws IllegalArgumentException if email is invalid
	 */
	public void setEmail(String email) {
		if (email == null || email.length() == 0 || !email.contains("@") || !email.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		}
		int at = email.indexOf("@");
		int dot = email.lastIndexOf(".");
		if (dot < at) {
			throw new IllegalArgumentException("Invalid email");
		}
		this.email = email;
	}

	/**
	 * Sets the password of a student
	 * @param password password of a student
	 * @throws IllegalArgumentException if password is invalid
	 */
	public void setPassword(String password) {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}
	
	/**
	 * Returns a formatted String with the correct informations
	 * @return formatted String
	 */
	 @Override
	 public String toString() {
		 return firstName + "," + lastName + "," + id + "," + email + "," + password;
	 }
	
	/**
	 * Generates a unique hash code for this object
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}
	
	/**
	 * Checks if another user is equal to the user
	 * @param obj object being compared to
	 * @return true if it is equal and false if it is not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}