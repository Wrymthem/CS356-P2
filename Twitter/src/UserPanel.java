import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class UserPanel {
	
	private User user;
	private ArrayList <GroupComponent> groupSystem;

	private JFrame frame;
	private JPanel contentPane;
	private JTextField txtUserID;
	private JList <User> following;
	private JTextField txtTweet;
	private JList <String> newsFeed;
	private JScrollPane followingScrollPane;
	private JScrollPane newsScrollPane;
	private DefaultListModel <User> followList;
	private DefaultListModel <String> newsfeedList;
	
	/*
	 * Pretty much the same thing as AdminPanel
	 */
	public UserPanel(User user, ArrayList <GroupComponent> groupSystem) {
		this.user = user;
		this.groupSystem = groupSystem;
		initialize();
	}
	
	private void initialize() {
		
		frame = new JFrame(this.user.toString());
		frame.setBounds(100, 100, 450, 450);
		frame.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*
		 * Follow User Button. It checks to see if you are already following this person, if you're trying
		 * to follow yourself, or if that person doesn't exit. Otherwise, you can follow them just fine.
		 * It also updates the Following box when its done
		 */
		JButton btnFollowUser = new JButton("Follow User");
		btnFollowUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int iD = 0;
				String userID = txtUserID.getText();
				
				if (userID.equals(user.toString())) {
					JOptionPane.showMessageDialog(frame, "Cannot follow yourself");
					return;
				}
				
				if (user.isFollowing(userID)) {
					JOptionPane.showMessageDialog(frame, "Already following");
					return;
				}
				
				for (GroupComponent gc : groupSystem) {
					if (gc instanceof User)
						if (gc.toString().equals(userID)) {
							user.follow((User) groupSystem.get(iD));
							followList.addElement((User) user.getFollowing().get(user.getFollowing().size() - 1));
							following.setModel(followList);
							return;
						}
					iD++;
				}
				JOptionPane.showMessageDialog(frame, "User " + userID + " not found");
			}
		});
		btnFollowUser.setBounds(228, 11, 196, 23);
		contentPane.add(btnFollowUser);
		
		txtUserID = new JTextField();
		txtUserID.setBounds(10, 11, 200, 22);
		contentPane.add(txtUserID);
		txtUserID.setColumns(10);
		
		followingScrollPane = new JScrollPane();
		followingScrollPane.setBounds(10, 39, 414, 120);
		contentPane.add(followingScrollPane);
		
		/*
		 * This is the panel that updates as you follow people. It uses a ListModel that you
		 * add to as you begin to follow more people, then you just set the JList to follow your ListModel
		 * and everything works out just fine
		 */
		followList = new DefaultListModel <User> ();
		for (Subject s : user.getFollowing()) {
			followList.addElement((User) s);
		}
		following = new JList <User> (followList);
		followingScrollPane.setViewportView(following);
		
		/*
		 * Button to send tweets, pretty simple
		 */
		JButton btnTweetMessage = new JButton("Tweet Message");
		btnTweetMessage.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				user.newTweet(txtTweet.getText());
			}
		});
		btnTweetMessage.setBounds(228, 184, 196, 23);
		contentPane.add(btnTweetMessage);
		
		txtTweet = new JTextField();
		txtTweet.setBounds(10, 170, 200, 50);
		contentPane.add(txtTweet);
		
		newsScrollPane = new JScrollPane();
		newsScrollPane.setBounds(10, 226, 414, 174);
		contentPane.add(newsScrollPane);
		
		/*
		 * This is the part that easily gave me the most trouble. I couldn't for the life
		 * of me figure out how to update the NewFeed from somewhere else. I'll probably
		 * explain it somewhere else more relevant in my code
		 */
		newsfeedList = new DefaultListModel <String> ();
		for (Tweet t : user.getNewsFeed()) {
			newsfeedList.addElement(t.getUser() + ": " + t.getTweet());
		}
		newsFeed = new JList <String> (newsfeedList);
		newsScrollPane.setViewportView(newsFeed);
		
		frame.setVisible(true);
	}
	
	public User getUser() {
		return this.user;
	}
	
	public ArrayList <GroupComponent> getGroupSystem() {
		return this.groupSystem;
	}
	
	/*
	 * This is the function that I use to update my NewFeed it just adds an element to
	 * the end of the NewsFeed ListModel thing in the correct format
	 * 
	 * Originally I had it clear the list, then iterate through the tweets, but now
	 * I'm just doing this, and even though (I think) its more efficient, it looks
	 * suuuuper ugly
	 */
	public void updateNewsFeed() {
		newsfeedList.addElement(user.getNewsFeed().get(user.getNewsFeed().size() - 1).getUser().toString()
				+ ": " + user.getNewsFeed().get(user.getNewsFeed().size() - 1).getTweet());
		newsFeed.setModel(newsfeedList);
	}
}
