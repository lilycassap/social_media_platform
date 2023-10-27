package socialmedia;

//INHERIT PostParent
public class Endorsement extends PostsParent{
	protected String postType;
	protected int postID;

	/**
	 * Constructor for endorsement
	 * @param handle the account's handle that the post belongs to
	 * @param ID the post ID the endorsement is associated to
	 */
	public Endorsement (String handle, int ID) {
		super(handle);
		postID = ID;
		postType = "endorsement";
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
	 * Gets the ID of the post that the post is endorsing
	 * @return post id
	 */
	@Override
	public int getPostID() {
		return postID;
	}
}
