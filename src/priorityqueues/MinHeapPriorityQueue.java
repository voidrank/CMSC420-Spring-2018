package priorityqueues;

import fifoqueues.EmptyFIFOQueueException;
import heaps.EmptyHeapException;
import heaps.LinkedMinHeap;
import heaps.MinHeap;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p><tt>MinHeapPriorityQueue</tt> is a {@link PriorityQueue} implemented using a {@link MinHeap}.</p>
 *
 * <p>You  <b>must</b> implement the methods in this file! To receive <b>any credit</b> for the unit tests related to this class, your implementation <b>must</b>
 * use <b>whichever</b> {@link MinHeap} implementation among the two that you should have implemented you choose!</p>
 *
 * @author  ---- YOUR NAME HERE ----
 *
 * @param <T> The Type held by the container.
 *
 * @see LinearPriorityQueue
 * @see MinHeap
 */
public class MinHeapPriorityQueue<T> implements PriorityQueue<T>{

	private int orderCounter;
	private MinHeap<PriorityQueueNode<T>> data;

	/**
	 * Simple default constructor.
	 */
	public MinHeapPriorityQueue(){
		orderCounter = 0;
		data = new LinkedMinHeap<PriorityQueueNode<T>>(); // Any MinHeap can be used.
	}

	@Override
	public void enqueue(T element, int priority) {
		data.insert(new PriorityQueueNode<T>(element, priority));
	}


	@Override
	public T dequeue() throws EmptyPriorityQueueException {
		try {
			return data.deleteMin().data;
		}catch(EmptyHeapException e){
			throw new EmptyPriorityQueueException("dequeue(): FIFOQueue is empty!");
		}
	}

	@Override
	public T getFirst() throws EmptyPriorityQueueException {
		// TODO Auto-generated method stub
		try {
			return data.getMin().data;
		}catch(EmptyHeapException e){
			throw new EmptyPriorityQueueException("getFirst(): FIFOQueue is empty!");
		}
	}

	@Override
	public Iterator<T> iterator() {
		
		// We will do this in a really dumb way. Like, really dumb.
		// A more efficient iterator would require
		// re-declaring heaps as iterables, and we're not gonna
		// do that, because it introduces significant complications.
		
		// First, make a carbon copy of our contained heap.
		MinHeap<PriorityQueueNode<T>> temp = new LinkedMinHeap<PriorityQueueNode<T>>(data);
		
		// Then, collect every element of the contained heap and put them
		// in an ArrayList.
		ArrayList<T> collection = new ArrayList<T>();
		while(!data.isEmpty()){
			try {
				collection.add(data.deleteMin().data);
			} catch (EmptyHeapException e) {
				// Dummy catchblock to make removeMin() happy
			}
		}
		
		// Finally, restore the original MinHeap and return an 
		// iterator to the ArrayList.
		data = temp; 
		return collection.iterator();
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return data.size() == 0;
	}

	@Override
	public void clear() {
		data.clear();
		orderCounter = 0;
	}
	
	/**
	 * A PriorityQueueNode is a Comparable type which is used to wrap around
	 * the (data, priority) pairs. Its overriding of the compareTo() method
	 * allows the contained MinHeap in the priority queue to disambiguate between
	 * the same priority elements, thus establishing a strict ordering in the heap,
	 * such that the root is always uniquely defined.
	 * 
	 * @author Jason Filippou (jasonfil@cs.umd.edu)
	 *
	 * @param <T> The Type of element contained in the PriorityQueueNode.
	 */
	@SuppressWarnings("hiding")
	private class PriorityQueueNode<T> implements Comparable<PriorityQueueNode<T>>{

		private T data;
		private int priority;
		private int orderInserted;

		public PriorityQueueNode(T data, int priority){
			this.data = data;
			this.priority = priority;
			orderInserted = orderCounter++;
		}

		public PriorityQueueNode(T data){
			this(data, -1);
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

	}
}
