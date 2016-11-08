import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.*;

import java.util.*;

public class AdminPanel {

	private static AdminPanel instance = new AdminPanel();
	
	private JFrame frame;
	private JPanel contentPane;
	private JTree tree;
	
	private ArrayList <GroupComponent> groupSystem = new ArrayList <GroupComponent> ();
	private GroupComponent root = new UserGroup("root");
	
	private int users = 0;
	private int groups = 0;
	
	private String [] words = {"good", "great", "excellent"};
	
	/*
	 * So I used the Design plugin for this, and copy pasted it over into another file
	 * I probably could have just left it as is, but I wasn't thinking about it. Haaa.
	 */
	private void instance() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 550, 350);
		/*
		 * Resizeable has been set to false cause I didn't use a layout, I just dragged
		 * and dropped things into place and resizing messes up the way it looks
		 */
		frame.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 208, 299);
		contentPane.add(scrollPane);
		
		/*
		 * This is where the User/Group tree is made. I put it in a ScrollPane so that
		 * there wouldn't be any trouble with how it was working.
		 */
		tree = new JTree((DefaultMutableTreeNode) groupSystem.get(0 ));
		scrollPane.setViewportView(tree);
		
		/*
		 *  Add User/Group fields + buttons
		 */
		JTextField txtAddUser = new JTextField();
		txtAddUser.setBounds(228, 11, 146, 23);
		contentPane.add(txtAddUser);
		txtAddUser.setColumns(10);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAUser(txtAddUser.getText());
			}
		});
		btnAddUser.setBounds(388, 11, 146, 23);
		contentPane.add(btnAddUser);
		
		JTextField txtAddGroup = new JTextField();
		txtAddGroup.setColumns(10);
		txtAddGroup.setBounds(228, 45, 146, 23);
		contentPane.add(txtAddGroup);
		
		JButton button = new JButton("Add Group");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAGroup(txtAddGroup.getText());
			}
		});
		button.setBounds(388, 45, 146, 23);
		contentPane.add(button);
		
		/*
		 * Opens the User View, if one is already open, it focuses on that
		 * and doens't allow another one to open
		 */
		JButton btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GroupComponent gc = (GroupComponent) tree.getLastSelectedPathComponent();
				if (gc instanceof User) {
					if (((User) gc).getPanel() == null) {
						((User) gc).setPanel(new UserPanel((User) gc, groupSystem));
					}
				}
				else
					JOptionPane.showMessageDialog(frame, "Not a user");
			}
		});
		btnOpenUserView.setBounds(308, 79, 146, 23);
		contentPane.add(btnOpenUserView);
		
		/*
		 * Displays number of users. This one and showGroupTotal I made functions for
		 * because I made these really early on in the project and wasn't
		 * lazy :p
		 */
		JButton btnShowUserTotal = new JButton("Show User Total");
		btnShowUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, totalUsers());
			}
		});
		btnShowUserTotal.setBounds(228, 253, 146, 23);
		contentPane.add(btnShowUserTotal);
		
		/*
		 * Iterates through the users, then iterates through each users tweets
		 * and counts them
		 */
		JButton btnShowMessageTotal = new JButton("Show Message Total");
		btnShowMessageTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				for (GroupComponent gc : groupSystem)
					if (gc instanceof User)
						for (Tweet t : ((User) gc).getTweets())
							i++;
				JOptionPane.showMessageDialog(frame, "Total Messages: " + i);		
			}
		});
		btnShowMessageTotal.setBounds(228, 287, 146, 23);
		contentPane.add(btnShowMessageTotal);
		
		/*
		 * Displays number of groups
		 */
		JButton btnNewButton = new JButton("Show Group Total");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, totalGroups());
			}
		});
		btnNewButton.setBounds(388, 253, 146, 23);
		contentPane.add(btnNewButton);
		
		/*
		 * Iterates through tweets and finds ones that contain "good" "great" or
		 * "excellent"
		 * 
		 * EDIT:	I just realized that I need to put it as .toLowerCase() to
		 * 			accurately check for words
		 */
		JButton btnShowPositivePercentage = new JButton("Show Positive Percentage");
		btnShowPositivePercentage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double i = 0.0;
				double p = 0.0;
				for (GroupComponent gc : groupSystem)
					if (gc instanceof User)
						for (Tweet t : ((User) gc).getTweets()) {
							String tweet = t.getTweet().toLowerCase();
							if (tweet.contains(words[0]) || tweet.contains(words[1])
									|| tweet.contains(words[2]))
								p++;
							i++;
						}
				if (i > 0) {
					i = (p / i) * 100;
				}
				String s = String.format("Percentage of Positive Tweets: %.2f%%", i);
				JOptionPane.showMessageDialog(frame, s);
			}
		});
		btnShowPositivePercentage.setBounds(388, 287, 146, 23);
		contentPane.add(btnShowPositivePercentage);
		
		frame.setVisible(true);
	}
	
	/*
	 * This function is called when the Add Group button is pressed. It first checks to see if the
	 * current node is a User. If it is, it selects the group under the selected user's parent node
	 * which must be a group. If no node is selected, it automatically selects root.
	 */
	private void addAGroup(String name) {
		DefaultMutableTreeNode current = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (current instanceof User)
			current = (DefaultMutableTreeNode) current.getParent();
		else if (current == null)
			current = (DefaultMutableTreeNode) groupSystem.get(0);
		addAGroup(name, (UserGroup) current);
	}
	
	/*
	 * This is the overload function that actually checks to see if the group is viable to be added
	 * to the list. It checks to see if any other group has the same name. If it is successful, it
	 * adds the group, calls the update function and increments the number of groups by 1
	 */
	private void addAGroup(String name, UserGroup parentGroup) {
		for (GroupComponent u : groupSystem)
			if (u.toString().equals(name)) {
				JOptionPane.showMessageDialog(frame, "Name in use");
				return;
			}
		UserGroup userGroup = new UserGroup(name);
		groupSystem.add(userGroup);
		parentGroup.add(userGroup);
		this.groups++;
		update(userGroup, parentGroup);
	}
	
	/*
	 * This is literally the exact same thing as the AddGroup function. I pretty much just copy pasted
	 * from above
	 */
	private void addAUser(String name) {
		DefaultMutableTreeNode current = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (current instanceof User)
			current = (DefaultMutableTreeNode) current.getParent();
		else if (current == null)
			current = (DefaultMutableTreeNode) groupSystem.get(0);
		addAUser(name, (UserGroup) current);
	}
	
	private void addAUser(String name, UserGroup parentGroup) {
		for (GroupComponent u : groupSystem)
			if (u.toString().equals(name)) {
				JOptionPane.showMessageDialog(frame, "Name in use");
				return;
			}
		User user = new User(name);
		groupSystem.add(user);
		parentGroup.add(user);
		this.users++;
		update(user, parentGroup);
	}
	
	/*
	 * This updates the tree by setting the current tree's model to the updated ArrayList
	 * 
	 * I had a pretty hard time with both this and the AutoUpdate for the UserPanel, but this
	 * one was a lot easier since it was obvious where it would update from
	 */
	private void update(GroupComponent leaf, UserGroup parent) {
		parent.add((DefaultMutableTreeNode) leaf);
		DefaultTreeModel model = new DefaultTreeModel((DefaultMutableTreeNode) groupSystem.get(0));
		tree.setModel(model);
		expandAllNodes(tree, 0, tree.getRowCount());
	}
	
	/*
	 * This just expands the nodes in the tree every time it's updated. Nothing big.
	 */
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}
	
	/*
	 * Getter for number of users
	 */
	private String totalUsers() {
		return  "Total Users: " + this.users;
	}
	
	/*
	 * Getter for number of groups
	 */
	private String totalGroups() {
		return  "Total Groups: " + this.groups;
	}
	
	/*
	 * Only one Admin panel
	 */
	private AdminPanel() {
		groupSystem.add(root);
		instance();
	}
	
	public static AdminPanel getInstance() {
		return instance;
	}
}
