package projects.pqueue.heaps; // ******* <---  DO NOT ERASE THIS LINE!!!! *******

/* *****************************************************************************************
 * THE FOLLOWING IMPORT IS NECESSARY FOR THE ITERATOR() METHOD'S SIGNATURE. FOR THIS
 * REASON, YOU SHOULD NOT ERASE IT! YOUR CODE WILL BE UNCOMPILABLE IF YOU DO!
 * ********************************************************************************** */

import java.util.Iterator;
/**
 * <p>A <tt>LinkedMinHeap</tt> is a tree (specifically, a <b>complete</b> binary tree) where every nodes is
 * smaller than or equal to its descendants (as defined by the <tt>compareTo() </tt>overridings of the type T).
 * Percolation is employed when the root is deleted, and insertions guarantee are performed in a way that guarantees
 * that the heap property is maintained. </p>
 *
 * <p>You <b>must</b> edit this class! To receive <b>any</b> credit for the unit tests related to this class,
 * your implementation <b>must</b> be a <i>"linked"</i>, <b>non-contiguous storage</b> (or, at least, not <i>necessarily</i>
 * contiguous storage) implementation based on a binary tree of nodes and references! </p>
 *
 * <p>Your background from CMSC132 as well as the implementation and testing framework of {@link projects.pqueue.trees.LinkedBinarySearchTree}
 * could be a help here. </p>
 * 
 * @author --- YOUR NAME HERE ---
 *
 * @param <T> The {@link Comparable} type of object held by the <tt>LinkedMinHeap</tt>.
 *
 * @see projects.pqueue.trees.LinkedBinarySearchTree
 * @see MinHeap
 * @see ArrayMinHeap
 */
public class LinkedMinHeap<T extends Comparable<T>> implements MinHeap<T> { // *** <-- DO NOT CHANGE THIS LINE!!! ***

	private static final RuntimeException UNIMPL_METHOD = new RuntimeException("Implement this method!");

	/* *********************************************
	 * PLACE YOUR PRIVATE AND PROTECTED FIELDS HERE!
	 * YOU MIGHT ALSO WANT TO PUT PRIVATE METHODS AND/OR CLASSES HERE!
	 * THE DESIGN CHOICE IS YOURS ENTIRELY.
	 * ******************************************** */



	/* ***********************************************************************************
	 * YOU SHOULD IMPLEMENT THE FOLLOWING METHODS. BESIDES THE INTERFACE METHODS,
	 * THOSE INCLUDE CONSTRUCTORS (DEFAULT, NON-DEFAULT, COPY) AS WELL AS EQUALS().
	 * PLEASE MAKE SURE YOU RECALL HOW ONE SHOULD MAKE A CLASS-SAFE EQUALS() FROM EARLIER
	 * JAVA COURSES!
	 *
	 * YOU SHOULD NOT CHANGE *ANY* METHOD SIGNATURES! IF YOU DO, YOUR CODE WILL NOT RUN
	 * AGAINST OUR TESTS!
	 * ********************************************************************************** */


	/**
	 *  Default constructor.
	 */
	public LinkedMinHeap(){
		/* FILL THIS IN WITH YOUR IMPLEMENTATION OF A DEFAULT CONSTRUCTOR, IF ANY. */
	}

	/**
	 *  Second, non-default constructor.
	 *  @param rootElement the element to create the root with.
	 */
	public LinkedMinHeap(T rootElement){
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	/**
	 * Copy constructor initializes the current MinHeap as a carbon
	 * copy of the parameter.
	 *
	 * @param other The MinHeap to copy the elements from.
	 */
	public LinkedMinHeap(MinHeap<T> other){
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	/**
	 * Standard equals() method.
	 *
	 * @return true If the parameter Object and the current MinHeap
	 * are identical Objects.
	 */
	@Override
	public boolean equals(Object other){
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public boolean isEmpty() {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public int size() {

		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public void clear() {

		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public void insert(T element) {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	} 

	@Override
	public T getMin() throws EmptyHeapException {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

	@Override
	public T deleteMin() throws EmptyHeapException {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}	
	


	@Override
	public Iterator<T> iterator() {
		throw UNIMPL_METHOD; /* ERASE THIS LINE AFTER IMPLEMENTING THE METHOD. */
	}

}



