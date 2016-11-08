import java.util.*;

public class User extends GroupComponent implements Subject, Observer {
	
	/*
	 * This is my User class, some pretty neat stuff here
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	/*
	 * This variable right here is where all the magic happens. This
	 * variable allows us to not only have a single instance of any
	 * user's panel, but it also allows us to update our news feed
	 * from here, when we call notifyAllObservers()
	 * 
	 * How cool is that? >:U
	 */
	private UserPanel panel;
	
	private List <Observer> followers;
	private List <Subject> following;
	
	private List <Tweet> tweets;
	private List <Tweet> newsFeed;
	
	/*
	 * Very basic constructor
	 */
	User(String userName) {
		this.userName = userName;
		this.followers = new ArrayList <Observer> ();
		this.following = new ArrayList <Subject> ();
		this.tweets = new ArrayList <Tweet> ();
		this.newsFeed = new ArrayList <Tweet> ();
	}
	
	public List <Observer> getFollowers() {
		return this.followers;
	}
	
	public List <Subject> getFollowing() {
		return this.following;
	}
	
	public List <Tweet> getNewsFeed() {
		return this.newsFeed;
	}
	
	public List <Tweet> getTweets() {
		return this.tweets;
	}
	
	public UserPanel getPanel() {
		return panel;
	}
	
	public void setPanel(UserPanel panel) {
		this.panel = panel;
	}
	
	public String toString() {
		return this.userName;
	}

	/*
	 * You follow them, they add you to their
	 * followers list
	 */
	public void follow(User u) {
		this.following.add(u);
		u.subscribe(this);
	}
	
	public void subscribe(Observer o) {
		this.followers.add(o);
	}

	/*
	 * When you post a new tweet, first add that to
	 * your tweet history, then notify your followers
	 */
	public void newTweet(String newTweet) {
		this.tweets.add(new Tweet(newTweet, this));
		notifyFollowers();
	}
	
	/*
	 * This iterates through your followers and gives each of 
	 * them an update with your latest tweet
	 */
	public void notifyFollowers() {
		for (Observer o : followers)
			o.update(this.tweets.get(this.tweets.size() - 1));
	}

	/*
	 * This right here took me ages to figure out. Specifically
	 * the "this.panel.updateNewsFeed();" My god, I was stuck on
	 * this for HOURS. Like at least a day. I couldn't figure out
	 * where or how I could update the NewsFeed, which is in a
	 * separate class!
	 * 
	 * One of my friends had the brilliant idea of putting an instance
	 * of the UserPanel into the User class, this made it so that I could
	 * call the updateNewFeed function from in here when the tweet is published
	 * AND having that variable up there ^^^^^ made it so that I could
	 * only have one UserPanel open per user!
	 */
	public void update(Tweet tweet) {
		this.newsFeed.add(tweet);
		this.panel.updateNewsFeed();
	}
	
	public boolean isFollowing(String userID) {
		for (Subject s : this.following)
			if (s.toString().equals(userID))
				return true;
		return false;
	}
}
