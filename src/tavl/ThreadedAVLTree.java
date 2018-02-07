package tavl; /**<p> <tt>tavl.ThreadedAVLTree</tt> implements threaded Adelson-Velsky-Landis (AVL) trees.
 * Those trees allow efficient search, insertion and deletion in <em>O(logn)</em> time, by virtue
 * of being AVL trees, as well as amortized constant time of finding the inorder successor of any given
 * node, by virtue of being threaded trees. They thus combine two powerful ideas in a common
 * framework.</p>
 *
 * @author Jason Filippou (jasonfil@cs.umd.edu, https://github.com/jasonfil)
 * @param <T> The {@link java.lang.Comparable} type held by the data structure.
 * @since February 2015
 */
import java.util.Iterator;
import java.util.ArrayList; // Just for its iterator.

public class ThreadedAVLTree<T extends Comparable<T>> {

    /* Private node class */
    private class Node {

        /* Data members */
        private int height;
        private T key;
        private Node left, right, parent; // Parent pointer probably needed for deletions.
        private byte leftTag, rightTag; // 14 bits per node wasted, but what can you do?

        /* Private methods. */
        private Node getInorderSuccessor(){
            // The caller of this method has ensured that
            // "right" is neither null nor a thread.
            return right.leftmostLeaf();
        }

        private Node leftmostLeaf(){
            Node curr = this;
            while(!curr.isLeftSet())
                curr = curr.left;
            return curr;
        }

        private Node rightMostLeaf(){
            Node curr = this;
            while(!curr.isRightSet())
                curr = curr.right;
            return curr;
        }

        private boolean isRightSet(){
            return rightTag == (byte)1;
        }

        private boolean isLeftSet(){
            return leftTag == (byte)1;
        }

        private void setRight(){
            rightTag = (byte)1;
        }

        private void setLeft(){
            leftTag = (byte)1;
        }

        private void clearRight(){
            rightTag = (byte)0;
        }

        private void clearLeft(){
            leftTag = (byte)0;
        }

		/* Methods accessible from the container class. */

        /* Constructors */
        Node(T key){ // This is really only useful for the root.
            this.key = key;
            leftTag = rightTag = (byte)1;
            left = right = parent = null;
        }

        Node(T key, Node parent, Node predecessor, Node successor){
            this.key = key;
            this.parent= parent;
            left = predecessor;
            right = successor;
            leftTag = rightTag = (byte)1; // setting the tags
        }

    } // End of inner node class definition

    /* Other data members */
    private Node root;

	/* Private methods */

    /* Height of a node. */
    private int height(Node n){
        return n == null ? -1 : n.height; // Interesting how I can access this without Eclipse complaining.
    }

    /* Return the balance of the current node, defined as the difference
     * between the height of the right subtree and the left subtree.
     */
    private int balance(Node n){
        int rightHeight, leftHeight;
		/* The interesting thing to realize here is that if your
		 * right link is a thread, this means that, in AVL terms,
		 * your right subtree has a height of -1! Same for the
		 * left link, naturally.
		 */
        if(n.isRightSet())
            rightHeight = -1;
        else
            rightHeight = height(n.right);
        if(n.isLeftSet())
            leftHeight = -1;
        else
            leftHeight = height(n.left);
        return rightHeight - leftHeight; // This is purely a convention, could be left - right.
    }

    /* Rotation methods. Very important that those methods
     * update the tree's threads correctly. Another important factor is that
     * rotations should update node heights differently depending on whether
     * they are called in the context of insertions (insertMode == true)
     * or deletions (insertMode == false). In insertions, the node that's just
     * been demoted needs its height to be reduced by just 1, because we haven't had a chance
     * to update it with its proper height yet when we detect the imbalance.
     * In deletions, on the other hand, we need to reduce the node's height by 2.
     */
    private Node rotateRight(Node n, boolean insertMode){
        Node x = n.left;
        if(x.right == n){ // Via a thread...
            // assert  x.isRightSet();
            n.left = x;
            n.setLeft();
        }else { // x was not right-pointing to n via a thread...
            n.left = x.right;
        }
        x.right = n;
        x.parent = n.parent;
        x.clearRight(); // Will clear x's right thread bit if it was set.
        n.parent = x;
        x.height = n.height;
        if(insertMode)
            n.height--;
        else
            n.height -= 2;
        return x;
    }

    private Node rotateLeft(Node n, boolean insertMode){
        Node x = n.right;
        if(x.left == n){ // Via a thread...
            // assert  x.isLeftSet();
            n.right = x;
            n.setRight();
        } else {
            n.right = x.left; // x was not left-pointing to n via a thread...
        }
        x.left = n;
        x.parent = n.parent;
        x.clearLeft(); // Will clear x's left thread bit if it was set.
        n.parent = x;
        x.height = n.height;
        if(insertMode)
            n.height--;
        else
            n.height -= 2;
        return x;
    }

    /* Recursive insertion method. */
    private Node insert(Node n, Node parent, Node predecessor, Node successor, T key){
        if(n == null)
            return new Node(key, parent, predecessor, successor);
        if(key.compareTo(n.key) < 0){
            if(n.isLeftSet()){
                n.left = new Node(key, n, predecessor, n);
                n.clearLeft();
            }else{
                n.left = insert(n.left, n, predecessor, n, key);
            }
            // Did our insertion cause an imbalance?
            if(balance(n) == -2){
                // What was the source of the imbalance? To find it,
                // we need to understand exactly in which subtree
                // we inserted the key; left-left or left-right?
                if(key.compareTo(n.left.key) < 0) // Right Rotation
                    n = rotateRight(n, true);
                else{
                    n.left = rotateLeft(n.left, true); // LR Rotation
                    n = rotateRight(n, true);
                }
            }
        } else{ // Symmetric cases
            if(n.isRightSet()){
                n.right = new Node(key, n, n, successor);
                n.clearRight();
            }else{
                n.right = insert(n.right, n, n, successor, key);
            }
            // Any imbalances caused?
            if(balance(n) == 2){
                if(key.compareTo(n.right.key) > 0) // Left Rotation
                    n = rotateLeft(n, true);
                else{
                    n.right = rotateRight(n.right, true); // RL Rotation
                    n = rotateLeft(n, true);
                }
            }
        }
        // Update the current node's height. Once again, we need to pay
        // attention to the fact that our node might have threads pointing
        // to our left or right.
        int rightHeight = n.isRightSet() ? -1 : height(n.right);
        int leftHeight = n.isLeftSet() ? -1 : height(n.left);
        int maxHeight = rightHeight > leftHeight ? rightHeight : leftHeight;
        n.height = maxHeight + 1;
        return n;
    }

	/* Recursive deletion method. Needs to cater for both the AVL and
	 * threaded structure of the tree. Splits cases across leaf deletions
	 * as well as inner node deletions. Most complex method of data structure.
	 */

    private Node delete(Node n, Node parent, Node predecessor, Node successor, T key){
        if(n.key.compareTo(key) == 0){
            // Case #1: Pure leaf node. Both left and right pointers are threads.
            if(n.isLeftSet() && n.isRightSet()){
                if(parent != null){ // If we're deleting the root, parent is null!
                    if(parent.left == n){ // We are our parent's left child.
                        parent.setLeft();
                        return n.left;
                    } else if(parent.right == n){ // We are our parent's right child.
                        parent.setRight();
                        return n.right;
                    }
                } else { // Stub tree of which we are deleting the single node.
                    return null;
                }

				/* Case #2: Right pointer only is a thread. This means that we have a left
				 * subtree. In this case, we need to update the right thread emanating
				 * from the rightmost leaf of this left subtree to point to our current node's
				 * right-pointed node (which could be null), and return the node's left child
				 * to the caller. For the latter step, we need to check if we arrived at the current
				 * node by traversing a parent's left link or right link.
				 */
            } else if(n.isRightSet()){

                // assert  !n.isLeftSet();
                // assert  n.left.rightMostLeaf().right == n;

                n.left.rightMostLeaf().right = n.right;

                return n.left;

				/* Case #3: Left pointer only is a thread. This is the symmetric case
				 * of case #2.
				 */
            } else if(n.isLeftSet()){

                // assert  !n.isRightSet();
                // assert  n.right.leftmostLeaf().left == n;

                n.right.leftmostLeaf().left = n.left;

                return n.right;

                // Case #4: We are deleting an inner node. We need to promote
                // its inorder successor, and recursively delete it from the
                // right subtree.
            } else {

                // assert  !(n.isRightSet() || n.isLeftSet());

                Node inSucc = n.getInorderSuccessor();
                n.key = inSucc.key;
                n.right = delete(n.right, n, n, successor, n.key);

                // Maybe the deletion of the node on the right caused an imbalance.
                // If that's the case, we will need to take corrective action, via rotations!
                if(balance(n) == -2){

					/* Since it is a deletion from the *right* subtree
					 * that caused the imbalance, it is n's *left* subtree
					 * that is causing us grief. Trivially, this means that
					 * n's left link cannot be a thread. It points to a node which
					 * has at least one child.
					 */

                    // assert  !n.isLeftSet();

					/* To figure out whether we need to do a right rotation about n
					 * or an LR rotation about n, we need to query its left subtree
					 * about whether it is left heavy or right heavy. Since we have an
					 * imbalance of -2 detected at n, n's left subtree *has* to be
					 * heavy on either one of its two sides.
					 *
					 * At this point, it is spectacularly important that we
					 * pay attention to potential threads that can mess up
					 * the behavior of height() (TODO).
					 */

                    int leftBalance = balance(n.left);
                    // assert  (leftBalance == 1 || leftBalance == -1);
                    if(leftBalance == -1){ // Left-leaning left subtree. Right rotation about n.
                        n = rotateRight(n, false);
                    } else{ // Right-leaning left subtree. LR rotation about n.
                        n.left = rotateLeft(n.left, false);
                        n = rotateRight(n, false);
                    }
                }

            }
        } else if(key.compareTo(n.key) < 0){
            if(n.isLeftSet()){
                return n; // Return the node itself, since there is no way the key you want to delete is in the tree.
            } else {
                n.left = delete(n.left, n, predecessor, n, key);

                // Do we need to re-balance? Check the right subtree!

                if(balance(n) == 2){
                    // assert  !n.isRightSet();
                    int rightBalance = balance(n.right);
                    // assert  (rightBalance == 1 || rightBalance == -1);
                    if(rightBalance == 1){ // Right-leaning right subtree. Left rotation about n.
                        n = rotateLeft(n, false);
                    } else{ // Left-leaning right subtree. RL rotation about n.
                        n.right = rotateRight(n.right, false);
                        n = rotateLeft(n, false);
                    }
                }
            }
        } else {
            if(n.isRightSet()){
                return n; // Same point applies.
            } else {
                // The rest of this code is the same as the inorder successor deletion case.
                n.right = delete(n.right, n, n, successor, key);
                if(balance(n) == -2){
                    // assert  !n.isLeftSet();
                    int leftBalance = balance(n.left);
                    // assert  (leftBalance == 1 || leftBalance == -1);
                    if(leftBalance == -1){ // Left-leaning left subtree. Right rotation about n.
                        n = rotateRight(n, false);
                    } else{ // Right-leaning left subtree. LR rotation about n.
                        n.left = rotateLeft(n.left, false);
                        n = rotateRight(n, false);
                    }
                }
            }
        }

        // Before we return the node, we need to adjust its height appropriately,
        // taking into consideration the heights of its children subtrees.
        // Once again, we pay attention to threads.

        int rightHeight = n.isRightSet() ? -1 : height(n.right);
        int leftHeight = n.isLeftSet() ? -1 : height(n.left);
        int maxHeight = rightHeight > leftHeight ? rightHeight : leftHeight;
        n.height = maxHeight + 1;
        return n;
    }

    /* Recursive search method. Standard BST-like implementation,
     * enhanced to terminate search if a thread is reached. */
    private T search(Node n, T key){
        if(key.compareTo(n.key) < 0)
            if(n.isLeftSet())
                return null;
            else
                return search(n.left, key);
        else if(key.compareTo(n.key) > 0)
            if(n.isRightSet())
                return null;
            else
                return search(n.right, key);
        else
            return n.key;

    }

    /* Populate the argument with the keys in symmetric order. */
    private void inorderTraversal(ArrayList<T> collector){
        Node curr = root;
        while(true) {
            // First, find leftmost leaf and add it to the collector.
            curr = curr.leftmostLeaf();
            collector.add(curr.key);

            // Second, check right. If it's a non-null thread,
            // you need to traverse to it, add the key, and then
            // check again.
            while(curr.isRightSet() && curr.right != null){
                curr = curr.right;
                collector.add(curr.key);
            }
            if(curr.right == null)
                break; // We're done
            else
                curr = curr.right; // Move down a level in the tree and repeat the process.
        }
    }

	/* Public (interface) methods */

    /**
     * Insert <tt>key</tt> in the tree.
     * @param key The key to insert in the tree.
     */
    public void insert(T key){
        if(isEmpty())
            root = new Node(key);
        else
            root = insert(root, null, null, null, key);
    }

    /**
     * Delete the key from the data structure and return it to the caller.
     * @param key The key to delete from the structure.
     * @return The key that was removed, or <tt>null</tt> if the key was not found.
     */
    public T delete(T key){
		/* While it is surely not the most efficient thing to do, we will first
		 * search for the key in the tree and only delete it if it's found in the tree.
		 * This makes successful deletions slower by a logarithmic parameter, yet
		 * it also makes for cleaner deletion code. It also speeds up unsuccessful
		 * deletions, i.e deletions of keys that are not in the tree (but any reasonable
		 * application would search keys first anyway).
		 */
        T retVal = search(key);
        if(retVal != null)
            root = delete(root, null, null, null, key);
        return retVal; // null or otherwise.
    }

    /**
     * Search for <tt>key</tt> in the tree. Return a reference to it if it's in there,
     * or <tt>null</tt> otherwise.
     * @return <tt>key</tt> if <tt>key</tt> is in the tree, or <tt>null</tt> otherwise.
     */
    public T search(T key){
        if(isEmpty())
            return null;
        else
            return search(root, key);
    }


    /**
     * Return the height of the tree. The height of the tree is defined as the length of the
     * longest path between the root and the leaf level. By definition of path length, a
     * stub tree has a height of 0, and we define an empty tree to have a height of -1.
     * @return The height of the tree.
     */
    public int height(){
        return height(root);
    }

    /**
     * Query the tree for emptiness. A tree is empty iff it has zero keys stored.
     * @return <tt>true</tt> if the tree is empty, <tt>false</tt> otherwise.
     */
    public boolean isEmpty(){
        return root == null;
    }

    /**
     * Return the key at the tree's root node.
     * @return The key at the tree's root node.
     */
    public T getRoot(){
        return root == null ? null : root.key;
    }

    /**
     * Generate an inorder (symmetric) traversal over the tree's stored keys. This should be done
     * by using the tree's threads, to be able to find every inorder successor in amortized constant
     * time.
     * @return A {@link java.util.Iterator} over <tt>T</tt>s, which exposes the elements in
     * symmetric order.
     */
    public Iterator<T> inorderTraversal(){
        ArrayList<T> collector = new ArrayList<T>();
        if(isEmpty())
            return collector.iterator();
        inorderTraversal(collector); // Populates "collector" with the elements in symmetric order.
        return collector.iterator();
    }


}
