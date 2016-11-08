import java.util.*;


public class UserGroup extends GroupComponent {
	/*
	 * This is just the UserGroup class. Nothing really
	 * special going on in here.
	 */
	private static final long serialVersionUID = 1L;
	
	String groupName;
	List <GroupComponent> groupMembers;
	
	public UserGroup(String groupName) {
		this.groupName = groupName;
		this.groupMembers = new ArrayList <GroupComponent> ();
	}
	
	public void add(GroupComponent newGroupComponent) {
		groupMembers.add(newGroupComponent);
	}
	
	public String toString() {
		return this.groupName;
	}
	
	public GroupComponent get(int conponentIndex) {
		return (GroupComponent) groupMembers.get(conponentIndex);
	}
	
	public List <GroupComponent> getAll() {
		return groupMembers;
	}
}
