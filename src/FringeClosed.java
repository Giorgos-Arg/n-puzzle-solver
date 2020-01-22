
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * The fringe is a priority queue that contains the search nodes sorted in
 * descending order according to the node's priority function value.
 * 
 * Closed is a list that contains the nodes that were expanded so far.
 * 
 * States represents the number of states of the problem that were created so
 * far.
 * 
 * @author Giorgos Argyrides
 *
 */
public class FringeClosed {

	public int states = 1;
	public PriorityQueue<State> fringe = new PriorityQueue<State>();
	public ArrayList<State> closed = new ArrayList<State>();

}
