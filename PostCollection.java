package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;


public class PostCollection implements Serializable {
	// post list
	ArrayList<PostsParent> posts = new ArrayList<>();

	/**
	 * Adds the newly created post to the list of existing posts
	 * @param post Post to be added
	 * @return the post's ID
	 */
	public int addPost(PostsParent post) {
		posts.add(post);
		return post.getID();
	}

	/**
	 * Is the message valid
	 * @param message Message post contains
	 * @return return boolean for validity
	 */
	public boolean checkValidMessage(String message) {
		if (message.length() == 0 || message.length() > 100) {
			return false;
		}
		return true;
	}

	/**
	 * Remove given post from the list of all posts
	 * @param post post to be deleted
	 */
	public void removePost(PostsParent post) {
		posts.remove(post);
	}

	/**
	 * Get total number of original posts, comments or endorsements
	 * @param type type of post
	 * @return total number of the given post type
	 */
	public int getTotalNumber(String type) {

		int count = 0;

		// Iterate through each post to check for the correct type, and increment if correct type found
		for(PostsParent post: posts) {
			if (post.getType().equals(type)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Get the number of comments or endorsements that point to the given post id
	 * @param id post id
	 * @param type type of post: comment or endorsement
	 * @return number of that type that link to the given post
	 */
	public int getNumberOfTypeAssociatedToPost(int id, String type) {

		int count = 0;

		for(PostsParent post: posts) {
			// Check if it is the corresponding post id and correct type of post (endorsement or comment)
			if (post.getType().equals(type)) {
				if (post.getPostID() == id) {
					count++;
				}
			}
		}
		return count;
	}

}