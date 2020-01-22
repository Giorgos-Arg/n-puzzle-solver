import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * The fringe is a priority queue that contains the puzzle states sorted in
 * descending order according to the state's priority function value.
 * 
 * @author Giorgos Argyrides
 */
public class Fringe {
	private int statesCount = 1;
	private PriorityQueue<State> fringe = new PriorityQueue<State>();

	public int getStatesCount() {
		return this.statesCount;
	}

	public void increaseStatesCount() {
		this.statesCount++;
	}

	public void add(State s) {
		fringe.add(s);
	}

	public State remove() {
		return fringe.remove();
	}

	Iterator<State> iterator() {
		return fringe.iterator();
	}
}