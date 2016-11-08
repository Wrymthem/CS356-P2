import java.util.ArrayList;

public class NewsFeed {
	
	private ArrayList <Tweet> tweets;
	
	NewsFeed () {
		tweets = new ArrayList <Tweet> ();
	}
	
	public void addTweet(Tweet tweet) {
		this.tweets.add(tweet);
	}
	
	public ArrayList <Tweet> getTweets() {
		return this.tweets;
	}
}
