/*
 * Subject interface
 */
public interface Subject {
	
	public abstract void subscribe(Observer o);
	
	public abstract void notifyFollowers();
}
