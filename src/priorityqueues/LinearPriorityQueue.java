package priorityqueues;

import fifoqueues.EmptyFIFOQueueException;
import exceptions.InvalidCapacityException;
import fifoqueues.FIFOQueue;
import fifoqueues.LinearArrayFIFOQueue;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * <p><tt>LinearPriorityQueue</tt> is a priority queue implemented as a linear {@link java.util.Collection}
 * of common {@link FIFOQueue}s, where the {@link FIFOQueue}s themselves hold objects
 * with the same priority (in the order they were inserted).</p>
 *
 * <p>You  <b>must</b> implement the methods in this file! To receive <b>any credit</b> for the unit tests related to this class, your implementation <b>must</b>
 * use <b>whichever</b> linear {@link java.util.Collection} you want (e.g {@link ArrayList}, {@link java.util.LinkedList},
 * {@link java.util.Queue}), or even the various {@link lists.List} and {@link FIFOQueue} implementations that we
 * provide for you. It is also possible to use <b>raw</b> arrays.</p>
 *
 * @param <T> The type held by the container.
 * 
 * @author  ---- YOUR NAME HERE ----
 *
 * @see MinHeapPriorityQueue
 *
 */
public class LinearPriorityQueue<T> implements PriorityQueue<T> {

	private ArrayList<PriorityQueueNode<T>> data; // See below for the implementation of the type PriorityQueueNode
	private final int DEFAULT_CAPACITY = 10;
	protected boolean modificationFlag;

	/**
	 * Default constructor initializes the data structure with
	 * a default capacity. This default capacity will be the default capacity of the
	 * underlying data structure that you will choose to use to implement this class.
	 */
	public LinearPriorityQueue(){
		data = new ArrayList<PriorityQueueNode<T>>(DEFAULT_CAPACITY);
		modificationFlag = false;
	}

	/**
	 * Non-default constructor initializes the data structure with 
	 * the provided capacity. This provided capacity will need to be passed to the default capacity
	 * of the underlying data structure that you will choose to use to implement this class.
	 * @see #LinearPriorityQueue()
	 * @param capacity The initial capacity to endow your inner implementation with.
	 * @throws InvalidCapacityException if the capacity provided is negative.
	 */
	public LinearPriorityQueue(int capacity) throws InvalidCapacityException{
		if(capacity < 0)
			throw new InvalidCapacityException("Invalid capacity provided!");
		data = new ArrayList<PriorityQueueNode<T>>(capacity);
		modificationFlag = false;
	}

	@Override
	public void enqueue(T element, int priority) { 
		for(int i = 0; i < data.size(); i++){
			if(data.get(i).getPriority() == priority){ // FIFO case
				data.get(i).enqueue(element); 
				return;
			}
			else if(data.get(i).getPriority() > priority){ // Must make a new queue before the currently scanned one.
				data.add(i, new PriorityQueueNode<T>(element, priority));
				return;
			}
		}
		// The for loop ending would mean that the element inserted has
		// the lowest priority of all elements already in the priority queue,
		// and thus a new FIFO queue needs to be initialized with "element"
		// as its only element, and then added to the back of the ArrayList.
		data.add(new PriorityQueueNode<T>(element, priority));
		modificationFlag = true;
	}

	@Override
	public T dequeue() throws EmptyPriorityQueueException {
		if(isEmpty())
			throw new EmptyPriorityQueueException("dequeue(): queue is empty!");
		// Since PriorityQueueNode is one of my own queues, I need to make
		// this inelegant check for accessing the first FIFO queue in my collection...
		T first = null;
		try {
			first = data.get(0).dequeue();
		} catch(EmptyFIFOQueueException ignored) {
			throw new EmptyPriorityQueueException("dequeue(): queue is empty!");
		}
		if(data.get(0).isEmpty())
			data.remove(0); // Get rid of the FIFO queue itself as well.
		modificationFlag = true;
		return first;
	}

	@Override
	public T getFirst() throws EmptyPriorityQueueException {
		if(isEmpty())
			throw new EmptyPriorityQueueException("dequeue(): queue is empty!");
		// Same point as above...
		T first = null;
		try {
			first = data.get(0).first();
		} catch(EmptyFIFOQueueException ignored){
			throw new EmptyPriorityQueueException("dequeue(): queue is empty!");
		}
		return first; // call to getFirst() instead of dequeue()
	}


	@Override
	public int size() {
		int total = 0;
		for(PriorityQueueNode<T> node : data)
			total += node.size();
		return total;
	}

	@Override
	public boolean isEmpty() {
		return data.size() == 0;
	}

	@Override
	public void clear() {
		data.clear();
		modificationFlag = true;
	}

	@Override
	public Iterator<T> iterator() {
		return new LinearPriorityQueueIterator<T>();
	}

	class LinearPriorityQueueIterator<T2> implements Iterator<T2> {

		private Iterator<PriorityQueueNode<T>> arrayListIt;
		private Iterator<T> queueIt;
		private boolean calledNextOnce;
		
		public LinearPriorityQueueIterator(){
			modificationFlag = false;
			arrayListIt = data.iterator();
			calledNextOnce = false;
			queueIt = (arrayListIt.hasNext()) ? arrayListIt.next().iterator() : null;  
		}
		
		@Override
		public boolean hasNext() {
			if(data.isEmpty() || queueIt == null)
				return false;
			if(queueIt.hasNext())
				return true;
			// If the current queue does not have a subsequent element, 
			// we need to look whether there are other fifoqueues in the ArrayList
			// that might have!
			else { 
				while(arrayListIt.hasNext() ){
					queueIt =arrayListIt.next().iterator();
					if(queueIt.hasNext())
						return true;
				}  
			}
			return false;
		}
		

		@SuppressWarnings("unchecked")
		@Override
		public T2 next() throws ConcurrentModificationException{
			if(modificationFlag)
				throw new ConcurrentModificationException("next(): Attempted to traverse a modified priority queue.");
			if(queueIt.hasNext()){
				calledNextOnce = true;
				return (T2)queueIt.next();
			}
			// If not, similarly to hasNext(), advance the iterator
			// until we find a non-empty queue. If we don't, just return null.
			else{
				while(arrayListIt.hasNext()){
					queueIt = arrayListIt.next().iterator();
					if(queueIt.hasNext()){
						calledNextOnce = true;
						return (T2)queueIt.next();
					}
				}
			}
			return null;
		}
		
		@Override
		public void remove()  throws IllegalStateException{
			if(!calledNextOnce)
				throw new IllegalStateException("Need at least one call to next() prior to removal.");
			queueIt.remove();
		}
		
	}



	/**
	 * A <tt>PriorityQueueNode</tt> is a FIFOQueue which is enhanced with an integer
	 * that dictates its priority. In this case we use a {@link LinearArrayFIFOQueue}
	 * as our preferred queue implementation, but any one of our {@link FIFOQueue} implementations can
	 * be used.
	 * 
	 * @author Jason Filippou (jasonfil@cs.umd.edu)
	 *
	 * @param <T> The type of element held by the PriorityQueueNode.
	 */
	@SuppressWarnings("hiding")
	private class PriorityQueueNode<T> extends LinearArrayFIFOQueue<T> {

		private int priority;

		/**
		 * Constructor that supplies the data element and priority
		 * for the PriorityQueueNode.
		 * @param data the data element of the PriorityQueueNode
		 * @param priority the priority of the PriorityQueueNode in the priority queue
		 */
		public PriorityQueueNode(T data, int priority){
			this.priority = priority;
			if(data != null) // Don't fill up with wasteful references
				enqueue(data);
		}

		/**
		 * Constructor that supplies the data element of the PriorityQueueNode.
		 * Default priority given: -1.
		 * @param dat the data element of the PriorityQueueNode
		 */
		public PriorityQueueNode(T dat){
			this(dat, -1);
		}

		/**
		 * Default constructor provides the node with a null data reference 
		 * and -1 priority.
		 */
		public PriorityQueueNode(){
			this(null);
		}

		/**
		 * Simple getter for priority of node.
		 * @return the originally supplied priority of the node.
		 */
		public int getPriority(){
			return priority;
		}

		/**
		 * Simple setter for node's priority.
		 * @param priority the priority to supply to the node.
		 */
		public void setPriority(int priority){
			this.priority = priority;
		}
	}
}