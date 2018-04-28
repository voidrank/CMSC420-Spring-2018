package projects.spatial.knnutils;

/**
 * <p>A <tt>PriorityQueueNode</tt> is a {@link Comparable} type which is used to wrap around
 * the (data, priority) pairs in a priority queue. Its overriding of the
 * {@link Comparable#compareTo(Object)} method
 * allows the <tt>PriorityQueue</tt> to disambiguate between
 * the same priority elements, thus establishing a strict ordering in the data structure.</p>
 *  
 * @author Jason Filippou (jasonfil@cs.umd.edu)
 *
 * @param <T> The Type of element contained in the <tt>PriorityQueueNode</tt>.
 */
public class PriorityQueueNode<T> implements Comparable<PriorityQueueNode<T>>{

	private T data;
	private double priority;
	private int orderInserted;
	private static int orderCounter = 0;
	
	public PriorityQueueNode(T data, double priority, int insertionOrder){
		this.data = data;
		this.priority = priority;
		orderInserted = insertionOrder;
	}

	public PriorityQueueNode(T data){
		this(data, -1, 0);
	}

	public PriorityQueueNode(){
		this(null);
	}

	@Override
	public int compareTo(PriorityQueueNode<T> o) {
		// Remember that a numerically smaller priority
		// is actually considered larger in priority
		// queue terms. Also recall that we are using a 
		// MinHeap, so the smallest elements ascend to the top,
		// not the largest.
		if(priority < o.priority)
			return -1;
		else if(priority > o.priority)
			return 1;
		else {
			if(orderInserted < o.orderInserted)
				return -1;
			else
				return 1;
		}
	}
	
	public T getData(){
		return data;
	}
	
	public double getPriority(){
		return priority;
	}

}