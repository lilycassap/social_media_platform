package socialmedia;


import java.io.Serializable;

public class Account implements Serializable {
	// account ID based off previous account ID + 1 - must be unique
	private final int myID;
	
	private static int nextID = 1;
		
	// account's description
	private String description;
	
	// string handle, aka username, for account - must be unique
	private String stringHandle;



	/**
	 * Constructor for account
	 * @param handle account's handle
	 */
	public Account(String handle) {
		myID = nextID;
		nextID += 1;
		this.stringHandle = handle;
	}

	/**
	 * Constructor for account
	 * @param handle account's handle
	 * @param description account's description
	 */
	public Account(String handle, String description) {
		myID = nextID;
		nextID += 1;
		this.stringHandle = handle;
		this.description = description;		
	}

	/**
	 * Get nextID for the platform saving
	 * @return nextID
	 */
	public static int getNextID() {
		return nextID;
	}

	/**
	 * Change nextID when loading platform
	 * @param value nextID value from file being loaded
	 */
	public static void setNextID(int value) {
		nextID = value;
	}

	/**
	 * Returns the ID of account
	 * @return ID of account
	 */
	public int getID() {
		return myID;
	}

	/**
	 * Returns the account's handle
	 * @return handle
	 */
	public String getStringHandle() {
		return stringHandle;
	}

	/**
	 * Returns the account's description
	 * @return description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * Change the account's handle
	 * @param handle new account handle
	 */
	public void setAccountHandle(String handle) {
		this.stringHandle = handle;
	}

	/**
	 * Change the account's description
	 * @param description new account description
	 */
	public void setAccountDescription (String description) {
		this.description = description;	
	}

	/**
	 * Set nextID back to 1 for when platform is erased
	 */
	public static void resetNextID() {
		nextID = 1;
	}


	
}