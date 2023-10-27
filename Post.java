package socialmedia;

import java.util.ArrayList;


public class Post extends PostsParent{
	protected String postType;
	private final String message;

	/**
	 * Constructor for post
	 * @param accountHandle the account's handle that the post belongs to
	 * @param message the post's message
	 */
	public Post (String accountHandle, String message) {
		super(accountHandle);
		postType = "originalPost";
		this.message = message;
	}

	/**
	 * Return the message of the post
	 * @return message
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the type of post: original, comment, or endorsement
	 * @return string of the post type
	 */
	@Override
	public String getType() {
		return postType;
	}

}