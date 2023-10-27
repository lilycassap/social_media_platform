package socialmedia;

/**
 * GhostPost class is for posts that have been deleted from the platform as comments still need a post to point to
 * ID is set to -1 by default, so it cannot be accessed
 * Default message is set
 */
public class GhostPost extends PostCollection {

    private final int id = -1;
    private final String message = "The original content was removed from the system and is no longer available.";


}
