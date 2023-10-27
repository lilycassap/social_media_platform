package socialmedia;

import java.io.*;
import java.util.ArrayList;

public class SocialMedia implements SocialMediaPlatform{


	
	//all posts and accounts stored in a list
	private AccountCollection allAccounts = new AccountCollection();
	private PostCollection allPosts = new PostCollection();

	/**
	 * Create an account with just a handle
	 * @param handle account's handle.
	 * @return the ID of the created account
	 * @throws IllegalHandleException if the handle already exists in the platform
	 * @throws InvalidHandleException if the new handle is empty, has more than 30 characters, or has white spaces
	 */
	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {

		if(allAccounts.checkHandleExists(handle)) {
			throw new IllegalHandleException("This handle (" + handle + ") already exists");
		}

		if(!allAccounts.checkValidHandle(handle)) {
			throw new InvalidHandleException("This handle (" + handle + ") is not valid");
		}

		int oldID = Account.getNextID();
		Account acc = new Account(handle);
		int id = allAccounts.addAccount(acc);

		//does ID increment as intended
		assert id > oldID;

		return id;
	}

	/**
	 * Create an account with a handle and description
	 * @param handle      account's handle.
	 * @param description account's description.
	 * @return the ID of the created account
	 * @throws IllegalHandleException if the handle already exists in the platform
	 * @throws InvalidHandleException if the new handle is empty, has more than 30 characters, or has white spaces
	 */
	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {

		// Check if the new handle already exists
		if(allAccounts.checkHandleExists(handle)) {
			throw new IllegalHandleException("This handle (" + handle + ") already exists");
		}

		// Check if the new handle meets the criteria for a handle
		if(!allAccounts.checkValidHandle(handle)) {
			throw new InvalidHandleException("This handle (" + handle + ") is not valid");
		}

		int oldID = Account.getNextID();
		Account acc = new Account(handle, description);
		int id = allAccounts.addAccount(acc);

		//does ID increment as intended
		assert id > oldID;

		return id;
	}

	/**
	 * Remove an account based on the id passed through
	 * @param id ID of the account.
	 * @throws AccountIDNotRecognisedException if the ID does not match to any account in the system
	 */
	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		boolean found = false;
		ArrayList<PostsParent> postsToDelete = new ArrayList<>();
		ArrayList<Account> accountsToDelete = new ArrayList<>();
		String handle;


		for(Account acc: allAccounts.accounts) {
			// Compare IDs to check if it is the correct account
			if (acc.getID() == id) {
				handle = acc.getStringHandle();
				// Add this account to the list of accounts to delete
				accountsToDelete.add(acc);
				found = true;

				// Find all posts created by this account and add them to the list of posts to delete
				for(PostsParent post: allPosts.posts) {
					if (post.getHandle().equals(handle)) {
						postsToDelete.add(post);
					}
				}
			}
		}

		if(!found) {
			throw new AccountIDNotRecognisedException("This account ID (" + Integer.toString(id) + ") does not exist");
		}

		// Delete all the account's posts
		for(PostsParent post: postsToDelete) {
			allPosts.removePost(post);
		}

		// Delete the given account
		for(Account account: accountsToDelete) {
			allAccounts.removeAccount(account);
		}

	}

	/**
	 * Remove an account based on the handle passed through
	 * @param handle account's handle.
	 * @throws HandleNotRecognisedException if the handle does not match to any account in the system
	 */
	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		boolean found = false;
		ArrayList<PostsParent> postsToDelete = new ArrayList<>();
		ArrayList<Account> accountsToDelete = new ArrayList<>();

		for(Account acc: allAccounts.accounts) {
			// Compare handles to check if it is the correct account
			if (acc.getStringHandle().equals(handle)) {
				// Add this account to the list of accounts to delete
				accountsToDelete.add(acc);
				found = true;

				// Find all posts created by this account and add them to the list of posts to delete
				for(PostsParent post: allPosts.posts) {
					if (post.getHandle().equals(handle)) {
						postsToDelete.add(post);
					}
				}
			}
		}

		if(!found) {
			throw new HandleNotRecognisedException("This account handle (" + handle + ") does not exist");
		}

		// Delete all the account's posts
		for(PostsParent post: postsToDelete) {
			allPosts.removePost(post);
		}

		// Delete the given account
		for(Account account: accountsToDelete) {
			allAccounts.removeAccount(account);
		}
	}

	/**
	 * Change the account's handle to a new handle
	 * @param oldHandle account's old handle.
	 * @param newHandle account's new handle.
	 * @throws HandleNotRecognisedException if the handle does not match to any account in the system
	 * @throws IllegalHandleException if the handle already exists in the platform
	 * @throws InvalidHandleException if the new handle is empty, has more than 30 characters, or has white spaces
	 */
	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {

		// Check if the new handle already exists
		if(allAccounts.checkHandleExists(newHandle)) {
			throw new IllegalHandleException("This new handle (" + newHandle + ") already exists");
		}

		// Check if the new handle meets the criteria for a handle
		if(!allAccounts.checkValidHandle(newHandle)) {
			throw new InvalidHandleException("This new handle (" + newHandle + ") is not valid");
		}

		boolean found = false;

		// Check that the account can be found in the system
		for(Account acc: allAccounts.accounts) {
			if (acc.getStringHandle().equals(oldHandle)) {
				acc.setAccountHandle(newHandle);
				found = true;
			}
		}

		if(!found) {
			throw new HandleNotRecognisedException("This old handle (" + oldHandle + ") does not exist");
		}

	}

	/**
	 * Change the account's description
	 * @param handle      handle to identify the account.
	 * @param description new text for description.
	 * @throws HandleNotRecognisedException if the handle does not match to any account in the system
	 */
	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {

		boolean found = false;

		for(Account acc: allAccounts.accounts) {
			// Compare the handle to find the correct account in the system
			if (acc.getStringHandle().equals(handle)) {
				// Update the description
				acc.setAccountDescription(description);
				found = true;
			}
		}

		if(!found) {
			throw new HandleNotRecognisedException("This handle (" + handle + ") does not exist");
		}

	}



	/**
	 * Gets account's ID, handle, description, no. Posts, no. endorsements and returns a string
	 * @param handle handle to identify the account.
	 * @return accountInfo - the String that holds the id, handle, description, no. posts & no. endorsements
	 * @throws HandleNotRecognisedException if the handle does not match to any account in the system
	 */
	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		int id;
		String description;
		String accountInfo = "";

		boolean found = false;

		for(Account acc: allAccounts.accounts) {
			// Compare the handle to find the correct account in the system
			if (acc.getStringHandle().equals(handle)) {
				id = acc.getID();
				description = acc.getDescription();

				accountInfo = "ID: " + Integer.toString(id) + "\nHandle: " + handle;
				accountInfo += "\nDescription: " + description;

				int postCount = 0;
				int endorsementCount = 0;

				for(PostsParent post: allPosts.posts) {
					// Ensure that the post is an actionable post (not an endorsement)
					if(!(post instanceof Endorsement)) {
						int numOfEndorsements = allPosts.getNumberOfTypeAssociatedToPost(post.getID(), "endorsement");
						String accountHandle = post.getHandle();
						int accountID = allAccounts.getIDFromHandle(accountHandle);

						// Check it has the correct ID
						if (accountID == id) {
							// Increment endorsements by correct amount for given post
							endorsementCount += numOfEndorsements;
							postCount += 1;
						}
					}
				}

				accountInfo += "\nPost count: " + Integer.toString(postCount);
				accountInfo += "\nEndorse count: " + Integer.toString(endorsementCount);
				found = true;
			}
		}

		if(!found) {
			throw new HandleNotRecognisedException("This handle (" + handle + ") does not exist");
		}

		return accountInfo;
	}


	/**
	 * Create a post with a message from given handle
	 * @param handle  handle to identify the account.
	 * @param message post message.
	 * @return ID of post
	 * @throws HandleNotRecognisedException if the handle does not match to any account in the system
	 * @throws InvalidPostException if the message is empty or has more than 100 characters
	 */
	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// Check if handle already exists in the system
		if(!allAccounts.checkHandleExists(handle)) {
			throw new HandleNotRecognisedException("This handle (" + handle + ") does not exist");
		}

		// Check that the message meets the criteria for a valid message
		if (!allPosts.checkValidMessage(message)) {
			throw new InvalidPostException("This message (" + message + ") is invalid");
		}

		int oldID = PostsParent.getNextID();

		// Create the new post and add to list of posts
		Post post = new Post(handle, message);
		int id = allPosts.addPost(post);

		//does ID increment as intended
		assert id > oldID;

		return id;
	}

	/**
	 * Creates an endorsement post of an existing post, a special type post that contains a
	 * reference to the endorsed post
	 * @param handle of the account endorsing a post.
	 * @param id     of the post being endorsed.
	 * @return id of the endorsement post
	 * @throws HandleNotRecognisedException if the handle does not match to any account in the system
	 * @throws PostIDNotRecognisedException if the ID does not match to any post in the system
	 * @throws NotActionablePostException if the ID refers to an endorsement post
	 */
	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {

		// check account exists
		if(!allAccounts.checkHandleExists(handle)) {
			throw new HandleNotRecognisedException("This handle (" + handle + ") does not exist");
		}

		// check post exists
		boolean found = false;
		for(PostsParent post: allPosts.posts) {
			if (post.getID() == id) {
				found = true;

				// check if endorsement
				if (post instanceof Endorsement) {
					throw new NotActionablePostException("This is an endorsement post");
				}
			}
		}

		if(!found) {
			throw new PostIDNotRecognisedException("This post ID (" + id + ") does not exist");
		}

		int oldID = PostsParent.getNextID();
		// create the new endorsement post
		Endorsement endorsement = new Endorsement(handle, id);
		int endorsement_id = allPosts.addPost(endorsement);

		//does ID increment as intended
		assert endorsement_id > oldID;

		return endorsement_id;
	}

	/**
	 * Creates a comment post referring to an existing post and contains a reference
	 * to the post being commented upon.
	 * @param handle  of the account commenting a post.
	 * @param id      of the post being commented.
	 * @param message the comment post message.
	 * @return id of the comment post
	 * @throws HandleNotRecognisedException if the handle does not match to any account in the system
	 * @throws PostIDNotRecognisedException if the ID does not match to any post in the system
	 * @throws NotActionablePostException if the ID refers to an endorsement post
	 * @throws InvalidPostException if the comment message is empty or has more than 100 characters
	 */
	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {

		// check account exists
		if(!allAccounts.checkHandleExists(handle)) {
			throw new HandleNotRecognisedException("This handle (" + handle + ") does not exist");
		}

		// check valid message
		if (!allPosts.checkValidMessage(message)) {
			throw new InvalidPostException("This message (" + message + ") is invalid");
		}

		// check post exists
		boolean found = false;
		for(PostsParent post: allPosts.posts) {
			if (post.getID() == id) {
				found = true;

				// check if endorsement (not actionable)
				if (post instanceof Endorsement) {
					throw new NotActionablePostException("This is an endorsement post");
				}
			}
		}

		if(!found) {
			throw new PostIDNotRecognisedException("This post ID (" + id + ") does not exist");
		}

		int oldID = PostsParent.getNextID();

		// create new comment post and add to post list
		Comment comment = new Comment(handle, id, message);
		int comment_id = allPosts.addPost(comment);

		//does ID increment as intended
		assert comment_id > oldID;

		return comment_id;
	}

	/**
	 * Removes the given post from the platform, all its endorsements are removed as well and
	 * all replies are updated by replacing the reference to this post by a generic empty post (GhostPost)
	 * @param id ID of post to be removed.
	 * @throws PostIDNotRecognisedException if the ID does not match to any post in the system
	 */
	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {

		//is the id of post to delete a valid ID
		assert id > 0;

		boolean found = false;
		ArrayList<PostsParent> postsToDelete = new ArrayList<>();

		// Look for given post in system and add it to list of posts to delete
		for(PostsParent post: allPosts.posts) {
			if (post.getID() == id) {
				found = true;
				postsToDelete.add(post);
			}
		}

		if(found) {
			for(PostsParent post: allPosts.posts) {
				// Look for any endorsements linked to the post being deleted and add them to
				// list of posts to be deleted as well
				if(post instanceof Endorsement && post.getPostID() == id) {
					postsToDelete.add(post);
				// Look for comments linking to post and make them orphans by removing the link to
				// the post id and setting it to -1 instead
				} else if (post instanceof Comment && post.getPostID() == id) {
					((Comment) post).setPostID(-1);
				}
			}
		} else  {
			throw new PostIDNotRecognisedException("This post ID (" + id + ") does not exist");
			}

		// Remove the given post and endorsements linking to it
		for(PostsParent post: postsToDelete) {
			allPosts.removePost(post);
		}
	}

	/**
	 * Gets post's ID, associated account's handle, no. endorsements post has, no. comments post has, post's message and returns a string
	 * @param id of the post to be shown.
	 * @return postInfo - the String that holds the id, handle, no. endorsements & no. comments and message
	 * @throws PostIDNotRecognisedException if the ID does not match to any post in the system
	 */
	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {

		//is the id of post to display a valid ID
		assert id > 0;

		String postInfo = "";

		String accountHandle;
		String message;
		int numOfEndorsements;
		int numOfComments;

		boolean found = false;

		/* display:

		 * ID: [post ID]
		 * Account: [account handle]
		 * No. endorsements: [number of endorsements received by the post] | No. comments: [number of comments received by the post]
		 * [post message]

		 */

		for(PostsParent post: allPosts.posts) {
			if (post.getID() == id) {

				accountHandle = post.getHandle();
				message = post.getMessage();
				numOfComments = allPosts.getNumberOfTypeAssociatedToPost(id, "comment");
				numOfEndorsements = allPosts.getNumberOfTypeAssociatedToPost(id, "endorsement");
				postInfo = "ID: " + Integer.toString(id) + "\nAccount: " + accountHandle;
				postInfo += "\nNo. endorsements: " + Integer.toString(numOfEndorsements) + " | No. comments: " + Integer.toString(numOfComments);
				postInfo += "\n" + message;
				found = true;
			}
		}

		if(!found) {
			throw new PostIDNotRecognisedException("This post ID (" + id + ") does not exist");
		}

		return postInfo;
	}


	/**
	 * Called recursively to build a string displaying the information of each child post of the original post
	 * @param id id of the post
	 * @param depth the indentation of the post
	 * @param display updated display with the post and all its child posts
	 * @return display updated display with the post and all its child posts
	 * @throws PostIDNotRecognisedException if the ID does not match to any post in the system
	 */
	private String recursion(int id, int depth, String display) throws PostIDNotRecognisedException {

		String postInfo = "";

		// check the post exists, if it does set the string to postinfo
		try {
			postInfo = showIndividualPost(id);
		} catch (PostIDNotRecognisedException e) {
			throw new PostIDNotRecognisedException("This post ID (" + id + ") does not exist");
		}

		//tab in the comment/post by how far down the tree it is (With root being original post)
		String indented = postInfo.indent((depth * 4));

		// indent the display further each time
		display += indented;

		for(PostsParent post: allPosts.posts) {
			// Check if the post is a comment and it links to the given id
			if (post instanceof Comment) {
				if (post.getPostID() == id) {
					String arrow = "|\n| >";
					display += arrow.indent((depth*4));
					// Repeat the process with each child
					display = recursion(post.getID(), depth + 1, display);
				}
			}
		}
		return display;
	}

	/**
	 * Builds a StringBuilder showing the details of the current post and all its children posts
	 * @param id of the post to be shown.
	 * @return a formatted StringBuilder containing the details of the post and its children
	 * @throws PostIDNotRecognisedException if the ID does not match to any post in the system
	 * @throws NotActionablePostException if the ID refers to an endorsement post
	 */
	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {

		//is the id of post to display a valid ID
		assert id > 0;

		String postInfo = "";

		boolean found = false;
		for(PostsParent post: allPosts.posts) {
			// Check if post matches given id
			if (post.getID() == id) {

				// Check if post is an endorsement (not actionable)
				if (post instanceof Endorsement) {
					throw new NotActionablePostException("This is an endorsement");
				}

				// Begin calling recursively
				postInfo = recursion(id, 0, "");

				found = true;
			}
		}

		if (!found) {
			throw new PostIDNotRecognisedException("This post ID (" + id + ") does not exist");
		}

		StringBuilder stb = new StringBuilder(postInfo);

		return stb;
	}

	/**
	 * Number of accounts
	 * @return Length of account list
	 */
	@Override
	public int getNumberOfAccounts() {
		return allAccounts.getNumOfAccounts();
	}

	/**
	 * Get total number of original posts in the system
	 * @return num of original posts
	 */
	@Override
	public int getTotalOriginalPosts() {
		return allPosts.getTotalNumber("originalPost");
	}

	/**
	 * Get total number of endorsements in the system
	 * @return num of endorsements
	 */
	@Override
	public int getTotalEndorsmentPosts() {
		return allPosts.getTotalNumber("endorsement");
	}

	/**
	 * Get total number of comments in the system
	 * @return num of comments
	 */
	@Override
	public int getTotalCommentPosts() {
		return allPosts.getTotalNumber("comment");
	}

	/**
	 * Identifies and returns the post with the highest number of endorsements, a.k.a. the most popular post.
	 * @return id of the most endorsed post
	 */
	@Override
	public int getMostEndorsedPost() {

		int maxVal = -1;
		int numOfEndorsements;
		int mostEndorsedPostID = -1;

		for(PostsParent post: allPosts.posts) {
			// Calculate number of endorsements associated to each post
			numOfEndorsements = allPosts.getNumberOfTypeAssociatedToPost(post.getID(), "endorsement");
			// Check post itself is not an endorsement
			if(!(post instanceof Endorsement) && numOfEndorsements > maxVal) {
				// If the post is more endorsed than the current most endorsed then set it as the new most endorsed
				maxVal = numOfEndorsements;
				mostEndorsedPostID = post.getID();
			}
		}

		return mostEndorsedPostID;
	}

	/**
	 * Identifies and returns the account with the highest number of endorsements, a.k.a. the most popular account
	 * @return id of the most endorsed account
	 */
	@Override
	public int getMostEndorsedAccount() {

		// make a 2d array of account ids and their corresponding number of endorsements
		int listLength = allAccounts.getNumOfAccounts();
		int [][] accountEndorsements = new int[listLength][2];

		for(int i = 0; i < listLength; i++) {
			Account account = allAccounts.accounts.get(i);
			int accountID = account.getID();
			accountEndorsements[i][0] = accountID;
			// Initialise each account's endorsement number to 0
			accountEndorsements[i][1] = 0;
		}

		for(PostsParent post: allPosts.posts) {
			// Check post is not an endorsement as they cannot be endorsed
			if(!(post instanceof Endorsement)) {
				int numOfEndorsements = allPosts.getNumberOfTypeAssociatedToPost(post.getID(), "endorsement");
				String accountHandle = post.getHandle();
				int accountID = allAccounts.getIDFromHandle(accountHandle);

				for(int i = 0; i < listLength; i++) {
					int IDnum = accountEndorsements[i][0];
					// Check for the correct account id in the table
					if (IDnum == accountID){
						// Add the number of endorsements to the correct account in the table
						accountEndorsements[i][1] = accountEndorsements[i][1] + numOfEndorsements;
					}
				}
			}
		}

		// iterate through again and find max amount of endorsements
		int maxVal = -1;
		int mostEndorsedAccountID = -1;

		for(int i = 0; i < listLength; i++) {
			int numOfEndorsements = accountEndorsements[i][1];
			if(numOfEndorsements > maxVal) {
				maxVal = numOfEndorsements;
				// If the account is more endorsed than the current most endorsed then set it as the new most endorsed
				mostEndorsedAccountID = accountEndorsements[i][0];
			}
		}

		return mostEndorsedAccountID;
	}

	/**
	 * Empties this SocialMediaPlatform of its contents and resets all internal counters
	 */
	@Override
	public void erasePlatform() {
		//erase the lists of accounts and posts
		allAccounts.accounts = new ArrayList<>();
		allPosts.posts = new ArrayList<>();

		//reset the nextID counter to 1
		Account.resetNextID();
		PostsParent.resetNextID();
	}

	/**
	 * Saves this SocialMediaPlatform’s contents into a serialised file, with the filename given in the argument
	 * @param filename location of the file to be saved
	 * @throws IOException if there is a problem experienced when trying to save the store contents to the file
	 */
	@Override
	public void savePlatform(String filename) throws IOException {

		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(allAccounts); //saving list of accounts
			out.writeInt(Account.getNextID()); //saving nextID of accounts
			out.writeObject(allPosts); //saving list of posts
			out.writeInt(PostsParent.getNextID()); //saving nextID of posts

			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new IOException("Error when saving Platform");
		}
	}

	/**
	 * Loads and replaces this SocialMediaPlatform’s contents with the serialised contents stored
	 * in the file given in the argument
	 * @param filename location of the file to be loaded
	 * @throws IOException if there is a problem experienced when trying to save the store contents to the file
	 * @throws ClassNotFoundException if required class files cannot be found when loading
	 */
	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			erasePlatform();
			//account list
			Object obj = in.readObject();
			if (obj instanceof AccountCollection) {
				allAccounts = (AccountCollection) obj;
			}

			//resetting account nextValue counter
			int nextValue = in.readInt();

			Account.setNextID(nextValue);


			//post list
			obj = in.readObject();
			if (obj instanceof PostCollection) {

				allPosts = (PostCollection) obj;

			}

			//resetting postsParent nextValue counter
			nextValue = in.readInt();

			PostsParent.setNextID(nextValue);

		} catch (IOException e) {
			throw new IOException("Error when loading Platform");
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("Couldn't find class saved in platform structure");
		}

	}
}