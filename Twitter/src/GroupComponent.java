import javax.swing.tree.DefaultMutableTreeNode;

/*
 * Nothing really interesting going on in here, but extending the
 * DefaultMutableTreeNode definitely made it easier for me to manage
 * the JTree in the admin panel. I didn't need to make a seperate list,
 * just an ArrayList of GroupComponents (Which Users and UserGroups are
 * a part of, oooooo).
 */
public abstract class GroupComponent extends DefaultMutableTreeNode {
	
	private static final long serialVersionUID = 1L;

	public void addGroupComponent(GroupComponent newUserGroup) {}
	
	public void getGroupComponent(GroupComponent userGroup) {}
	
	/*
	 * I don't have anything here, cause I don't actually use anything from
	 * GroupComponent. I'm not sure if I should take it out or if it's okay
	 * to leave it here. But I'm gonna leave it just in case...
	 */
	public String toString() {}
}