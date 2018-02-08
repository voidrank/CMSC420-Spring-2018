package demos.splaying;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>A <tt>SplayTree</tt> is a Binary Search Tree which strives to maintain
 * amortized logarithmic complexity. That is, the total cost of <em>m</em> insertions, deletions or searches
 * will be <em>O(mlogn)</em>, where <em>n</em> is the maximum number of nodes in the tree at any time. Unlike AVL trees,
 * a splay tree doesn't need to store height information in its nodes, which makes it an attractive alternative because
 * of its space savings.</p>
 * 
 * @author Jason Filippou (jasonfil@cs.umd.edu)
 * 
 * @param <T> The <tt>Comparable</tt> type held by the container.
 */
public class SplayTree<T extends Comparable<T>> {

	/*-******************************
	   * Private fields and methods *
	   ****************************-*/

	private class Node{
		T key;
		Node left, right;

		Node(T key){
			this.key = key;
		}
	}


	private Node root;
	private int count;

	/*
	 * Splaying is the chief operation in splay trees. It searches for the node that contains the key
	 * provided. If it finds it, it ascends it to the top of the tree. If it does not, then the node that
	 * contains either the preceding or following key in the sorted key list ascends to the top of the tree.
	 *
	 * Note that this is a relatively inefficient splay. It's clean and it depends on tested rotation routines.
	 * But it's recursive and it probably doesn't implement the zig-zag and zig-zig routines as efficiently as
	 * they can be implemented. Fork me, solve it, and show me how it's done through a Pull Request!
	 */
	private Node splay(Node root, T key){
		if(key.compareTo(root.key) < 0){
			if(root.left == null)
				return root; // The key is not in the tree; ascend its successor
			else{
				root.left = splay(root.left, key); // The key might be in the tree; keep looking.
				return rotateRight(root); // Rotate the current root to the right to make the key ascend to the tree's root.
			}
		} else if(key.compareTo(root.key) > 0){
			if(root.right == null)
				return root;
			else{
				root.right = splay(root.right, key); // Symmetric case
				return rotateLeft(root);
			}
		} else // Found the key; simply return the current node.
			return root;
	}

	private Node rotateLeft(Node node){
		Node x = node.right;
		node.right = x.left;
		x.left = node;
		return x;
	}

	private Node rotateRight(Node node){
		Node x = node.left;
		node.left = x.right;
		x.right = node;
		return x;
	}

	private Node rotateLeftRight(Node node){
		node.left = rotateLeft(node.left);
		return rotateRight(node);
	}
	private Node rotateRightLeft(Node node){
		node.right = rotateRight(node.right);
		return rotateLeft(node);
	}

	/*-*******************
	   * Public methods *
	   ****************-*/

	/**
	 * Queries the tree for emptiness.
	 * @return true if and only if the tree is empty.
	 */
	public boolean isEmpty(){
		return (count == 0);
	}

	/**
	 * Searches for <tt>key</tt> in the splay tree.
	 * @param key  The {#java.lang.{@link Comparable}} key to insert in the tree.
	 * @return The key, if it is found; <tt>null</tt> otherwise.
	 * @see #delete(Comparable)
	 */
	public T search(T key){
		if(isEmpty())
			return null;
		root = splay(root, key);
		if(root.key.compareTo(key) == 0)
			return root.key;
		else
			return null; // We splayed a neighbor of the key to the root.
	}


	/**
	 * Removes <tt>key</tt> from the tree and returns it.
	 * @param key The {#java.lang.{@link Comparable}} key to deleteRec from the tree.
	 * @return <tt>key</tt>, if it was found in the tree; <tt>null</tt> otherwise.
	 * @see #search(Comparable)
	 */
	public T delete(T key){
		if(isEmpty())
			return null;
		root = splay(root, key);
		if(root.key.compareTo(key) == 0){ // The key ascended is indeed the key to be deleted.
			if(root.left == null) // key was the smallest key in the tree already.
				root = root.right; // Simply make the root point to its right child.
			else{
				root.left = splay(root.left, key); // Will ascend the immediate predecessor to the left child of the root.
				Node prevRight = root.right; // The new root will have that predecessor as its left child and the same right child.
				root = root.left;
				root.right = prevRight;
			}
			count--;
			return key;
		} 
		return null; // If we didn't find the key
	}


	/**
	 * Inserts <tt>key</tt> in the splay tree.
	 * @param key The {#java.lang.{@link Comparable}} key to insert.
	 */
	public void insert(T key){
		if(isEmpty())
			root = new Node(key);
		else{
			root = splay(root, key);
			Node oldRoot = root;
			if(key.compareTo(root.key) < 0){ // The root contains the immediate successor of our key.
				Node oldRootLeft = root.left;
				root = new Node(key);
				root.right = oldRoot;
				root.right.left = null;
				root.left = oldRootLeft;
			} else{ // The root contains either the key itself or an immediate predecessor. The symmetric case occurs.
				Node oldRootRight = root.right;
				root = new Node(key);
				root.left = oldRoot;
				root.left.right = null;
				root.right = oldRootRight;
			}
		}
		count++;
	}

	/**
	 * Returns the number of nodes in the splay tree.
	 * @return the number of nodes in the tree.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Returns the key at the root of the tree.
	 * @return the key at the root of the tree or null if the tree is empty.
	 */
	public T getRoot(){
		return (root != null) ? root.key : null;
	}

	/* Traversal methods ... Mainly put those here because my strong past tests depend on traversals as well.
	*  I'm gonna do them recursively, since I only use them for testing anyway. In fact, to reflect this, I'll make them
	*  package-private. I think one can configure how JavaDocs are printed for those, if one would want to add some JavaDoc in there. */

	/**
	 * Returns a {@link java.util.Iterator<T>}  over the keys of the tree, exposed in the manner of a <b>pre-order</b>
	 * traversal of the tree.
	 * @return A {@link java.util.Iterator<T>} over the keys of the tree, in preorder traversal.
	 * @see #inOrder()
	 * @see #postOrder()
	 */
	Iterator<T> preOrder(){
		List<T> visited = new LinkedList<T>();
		preOrder(root, visited)	;
		return visited.iterator();
	}

	private void preOrder(Node curr, List<T> visited){
		if(curr != null) {
			visited.add(curr.key);
			preOrder(curr.left, visited);
			preOrder(curr.right, visited);
		}
	}

	/**
	 * Returns a {@link java.util.Iterator<T>}  over the keys of the tree, exposed in the manner of an <b>in-order</b>
	 * traversal of the tree.
	 * @return A {@link java.util.Iterator<T>} over the keys of the tree, in inorder traversal.
	 * @see #preOrder()
	 * @see #postOrder()
	 */
	Iterator<T> inOrder(){
		List<T> visited = new LinkedList<T>();
		inOrder(root, visited)	;
		return visited.iterator();
	}

	private void inOrder(Node curr, List<T> visited){
		if(curr != null) {
			preOrder(curr.left, visited);
			visited.add(curr.key);
			preOrder(curr.right, visited);
		}
	}

	/**
	 * Returns a {@link java.util.Iterator<T>}  over the keys of the tree, exposed in the manner of a <b>post-order</b>
	 * traversal of the tree.
	 * @return A {@link java.util.Iterator<T>} over the keys of the tree, in postorder traversal.
	 * @see #preOrder()
	 * @see #inOrder()
	 */
	Iterator<T> postOrder(){
		List<T> visited = new LinkedList<T>();
		postOrder(root, visited)	;
		return visited.iterator();

	}

	private void postOrder(Node curr, List<T> visited){
		if(curr != null) {
			preOrder(curr.left, visited);
			preOrder(curr.right, visited);
			visited.add(curr.key);
		}
	}




}
