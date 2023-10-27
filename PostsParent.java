package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;


public class PostsParent implements Serializable {
	protected final int myID;
	protected static int nextID = 1;
	protected String handle;

	/**
	 * Constructor for all the different posts
	 * @param accountHandle the account's handle that the post belongs to
	 */
	public PostsParent (String accountHandle){
		myID = nextID;
		nextID += 1;
		handle = accountHandle;
	}

	/**
	 * Return the ID of the post
	 * @return id
	 */
	public int getID() {
		return myID;
	}

	/**
	 * Return ID that this post points to
	 * @return post id
	 */
	public int getPostID() {
		return -1; // to be overridden by child class - negative as it can't exist
	}

	/**
	 * Return the handle of the account that created this post
	 * @return account handle
	 */
	public String getHandle() {
		return handle;
	}

	/**
	 * Return the message in this post
	 * @return null
	 */
	public String getMessage() {
		return null; // to be overridden by child class (only Post and Comment as Endorsements cannot have messages)
	}


	/**
	 * Returns the type of post: original, comment, or endorsement
	 * @return null
	 */
	public String getType() {
		return null; // null as it is to be overridden by the child class
	}

	/**
	 * Set nextID back to 1 for when platform is erased
	 */
	public static void resetNextID() {
		nextID = 1;
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
}
