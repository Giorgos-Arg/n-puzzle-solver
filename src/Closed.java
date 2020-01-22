import java.util.ArrayList;

/**
 * Closed is a list that contains the states that were expanded so far.
 * 
 * @author Giorgos Argyrides
 */
public class Closed {
	private ArrayList<State> closed = new ArrayList<State>();

	public State get(int index) {
		return closed.get(index);
	}

	public void add(State s) {
		closed.add(s);
	}

	public int size() {
		return closed.size();
	}
}