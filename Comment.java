package socialmedia;


//INHERIT PostParent
public class Comment extends PostsParent{
	protected String postType;
	protected final String message;
	protected int postID;	//post comment is attached to

	/**
	 * Constructor for comment
	 * @param handle the account's handle that the post belongs to
	 * @param ID the post ID the comment is associated to
	 * @param message the comment's message
	 */
	public Comment (String handle, int ID, String message) {
		super(handle);
		postType = "comment";
		postID = ID;
		this.message = message;
	}

	/**
	 * Returns comment's message
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

	/**
	 * Gets the ID of the post that the comment points to
	 * @return post id
	 */
	@Override
	public int getPostID() {
		return postID;
	}

	/**
	 * Change the post that the comment points to
	 * @param id id of the new post
	 */
	public void setPostID(int id) {
		postID = id;
	}
}