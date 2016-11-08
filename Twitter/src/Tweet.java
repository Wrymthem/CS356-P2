/*
 * Small tweet class, just for convenience
 */
public class Tweet {
	User user;
	String tweet;
	
	Tweet(String tweet, User user) {
		this.user = user;
		this.tweet = tweet;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public String getTweet() {
		return this.tweet;
	}
}
