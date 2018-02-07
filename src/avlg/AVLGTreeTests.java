package avlg;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Collection;
import static org.junit.Assert.*;

/** A class containing jUnit tests to test the students' code with.
 *
 * @author <a href="https://github.com/jasonfil" alt="Jason Filippou's GitHub page">Jason Filippou</a>
 */
public class AVLGTreeTests {

    private Collection<AVLGTree> trees;
    private static final int MAX_IMBALANCE=10;
    private static final Integer ZERO = 0;
    private static final int NUMS = 150;

    /**
     * Set-up the trees that we will use for our tests.
     */
    @Before
    public void setUp() {
       IntStream.rangeClosed(1, MAX_IMBALANCE).forEach(imb->trees.add(new AVLGTree<Integer>(imb)));
    }

    @Test
    public void easyTest(){
        assertTrue("The tree should be empty!", AVLTrees[0].isEmpty());
        assertEquals("The tree should have a height of -1!", -1 , AVLTrees[0].height());
        AVLTrees[0].insert(8);
        assertFalse("The tree should be empty!", AVLTrees[0].isEmpty());
        assertEquals("The tree should have a height of 0!", 0, AVLTrees[0].height());
        AVLTrees[0].delete(8);
        assertTrue("The tree should be empty!", AVLTrees[0].isEmpty());
        assertEquals("The tree should have a height of -1!", -1 , AVLTrees[0].height());
        try {
            AVLTrees[0].delete(-1); // This shouldn't do anything, but it's interesting to investigate if it throws an exception
        } catch(Throwable t) {
            fail("Caught a " + t.getClass().getSimpleName() + " in easyTest(). The message was: " + t.getMessage() + ".");
        }
    }

    /**
     * Preliminary tests for empty and stub trees. Essentially fiddle with at most
     * one node.
     */
    @Test
    public void testEmptyAndStubTree(){
        // Make sure that an empty tree is indeed empty:
        assertTrue("Tree should be empty.", AVLTrees[0].isEmpty() && stringAVL1Tree.isEmpty());
        assertTrue("Empty tree should have a height of -1.",
                AVLTrees[0].height() == -1 && stringAVL1Tree.height() == -1);

        // Make a single insertion and check the tree's status.
        AVLTrees[0].insert(ZERO);
        assertFalse("After a single insertion, the tree should no longer be empty.",
                AVLTrees[0].isEmpty());
        assertEquals("After a single insertion, tree's height should be 0.",
                0, AVLTrees[0].height());
        assertEquals("Incorrect root key.", ZERO, AVLTrees[0].getRoot());

        // Look up the key through the search() method.
        assertEquals("Looking up the single inserted key in the tree failed.",
                ZERO, AVLTrees[0].search(ZERO));

        // Delete the tree and make it back into a stub.
        assertEquals("Deleting the single key should return the key itself.",
                ZERO, AVLTrees[0].delete(ZERO));
        assertTrue("Tree should now be empty again.", AVLTrees[0].isEmpty());
        assertEquals("Tree should now have a height of -1 once more.", -1, AVLTrees[0].height());
    }

    /** Test the "AVL" part of the structure: Does the tree re-balance itself
     * after insertions (we can figure this out by querying about the height)?
     *  Is the root the expected root?
     */
    @Test
    public void testBalancedInsertions(){

        /* First tree will be very simple, yet effective at uncovering
         * basic errors.
         */

        AVLTrees[0].insert(-1);
        AVLTrees[0].insert(-2);

        /* Tree is now like this (we're not drawing the threads):
         *
         * 				-1
         * 				/
         * 			   /
         * 			 -2
         *
         * Let us first make sure that it is this way, and then insert a new element.
         */

        assertEquals("Incorrect tree height. Everything ok with your insertions?",
                1, AVLTrees[0].height());
        assertEquals("Incorrect key detected at root: Are you performing " +
                "unnecessary rotations?", new Integer(-1), AVLTrees[0].getRoot());
        assertEquals("Looking up the key we just inserted should return a reference " +
                "to the key itself.", new Integer(-2), AVLTrees[0].search(-2));
        assertFalse("The code tells us that a tree where we inserted two keys is empty.",
                AVLTrees[0].isEmpty());

        /* TODO: Should you expand those tests by making sure the inorder traversal
         * works as it should?... */

        AVLTrees[0].insert(-5);

        /* The tree should now be like this after a right rotation about the root:
         *
         *  			-2
         *  			/ \
         *  		   /   \
         *  		 -5	   -1
         *
         *  So it should have a height of 1 and the root should be -2!
         */
        assertEquals("Tree not re-balanced properly.", 1, AVLTrees[0].height());
        assertEquals("Incorrect key detected at root: are you rotating *right* properly?", new Integer(-2), AVLTrees[0].getRoot());
        assertEquals("Looking up the key we just inserted should return a reference " +
                "to the key itself.",	new Integer(-5), AVLTrees[0].search(-5));
        assertFalse("The code tells us that a tree where we inserted three keys is empty.",
                AVLTrees[0].isEmpty());

        /* We will also make similar tests, for a right-heavy tree this time. */

        stringAVL1Tree.insert("fad");
        stringAVL1Tree.insert("hom");

        /* This tree should now look like:
         *
         * 			fad
         *			   \
         *				\
         *  			hom
         *
         *  Let's make sure that it does indeed look that way:
         */

        assertEquals("Incorrect tree height.", 1, stringAVL1Tree.height());
        assertEquals("Incorrect key detected at root: Are you performing " +
                "unnecessary rotations?", "fad", stringAVL1Tree.getRoot());
        assertEquals("Looking up the key we just inserted should return a reference " +
                "to the key itself.", "hom", stringAVL1Tree.search("hom"));
        assertFalse("The code tells us that a tree where we inserted two keys is empty.",
                stringAVL1Tree.isEmpty());
        stringAVL1Tree.insert("qer");

        /* After the insertion of "qer", we would need the tree to re-balance itself
         * via a left rotation about the root, like so:
         *
         * 				hom
         * 			   /   \
         * 			  /     \
         * 			fad		qer
         *
         * So let's make sure that it does!
         */

        assertEquals("Tree not re-balanced properly.", 1, stringAVL1Tree.height());
        assertEquals("Incorrect key detected at root: are you rotating *left* properly?",
                "hom", stringAVL1Tree.getRoot());
        assertEquals("Looking up the key we just inserted should return a reference " +
                "to the key itself.","qer", stringAVL1Tree.search("qer"));
        assertFalse("The code tells us tells us that a tree where we inserted " +
                "three keys is empty.", stringAVL1Tree.isEmpty());

        /* Now we will sequentially insert couple nodes which should not cause
         * rotations. That is, after any insertion, the tree should be of the same height.
         *
         * Recall that the integer tree should look like this now:
         *
         * 				-2
         * 			   /  \
         * 			  -5  -1
         *
         * Inserting another subtree on the left should not cause any rotations.
         */

        AVLTrees[0].insert(-10);
        assertEquals("Incorrect tree height after another insertion.", 2, AVLTrees[0].height());
        assertEquals("Key at root changed unnecessarily", new Integer(-2), AVLTrees[0].getRoot());
        // Will no longer test lookups and empties, that's
        // just unnecessary at this point.

        AVLTrees[0].insert(-4);
        assertEquals("Incorrect tree height after another insertion.", 2, AVLTrees[0].height());
        assertEquals("Key at root changed unnecessarily.", new Integer(-2), AVLTrees[0].getRoot());

        /* If every test passed, the tree should now look like this:
         *
         * 				-2
         * 			   /  \
         * 			  /    \
         * 			-5		-1
         * 		   /  \
         * 		  /	   \
         * 		-10     -4
         *
         * Inserting -20 should cause an imbalance detected at the root, and corrective action via
         * a right rotation about the root will need to be taken. Specifically, the tree
         * should look like this:
         *
         * 					-5
         * 				   /  \
         * 				  /    \
         * 				-10    -2
         * 				/  \   / \
         * 			  -20	x -4  -1
         */

        AVLTrees[0].insert(-20);
        assertEquals("Incorrect root detected: Are you rotating *right* properly?",
                new Integer(-5), AVLTrees[0].getRoot());
        assertEquals("Incorrect tree height after another insertion: " +
                "are you balancing properly?", 2, AVLTrees[0].height());


        /* A similar situation, only this time on the right side, should happen with the string tree.
         * Recall the current form of the string tree:
         *
         *
         * 				hom
         * 			   /   \
         * 			  /     \
         * 			fad		qer
         *
         *
         *  Inserting "job" and "rad" should not affect the tree's height in any fashion, leading
         *  us to the tree:
         *
         *  			hom
         *  		   /  \
         *  		  /    \
         *  		fad    qer
         *  		      /  \
         *  		     job rad
         */

        stringAVL1Tree.insert("job");
        assertEquals("Incorrect tree height after another insertion.", 2, stringAVL1Tree.height());
        assertEquals("Key at root changed unnecessarily.", "hom", stringAVL1Tree.getRoot());
        stringAVL1Tree.insert("rad");
        assertEquals("Incorrect tree height after another insertion.", 2, stringAVL1Tree.height());
        assertEquals("Key at root changed unnecessarily.", "hom", stringAVL1Tree.getRoot());

        /* If the above tests passed, our tree should look like this:
         *
         * 			    hom
         * 			   /   \
         * 			  /     \
         * 			fad		qer
         * 					/ \
         * 				   /   \
         * 				 job   rad
         *
         * Inserting "red" should cause an imbalance detected at the root, solvable via a left
         * rotation about the root. The tree should look like this:
         *
         *	  			 qer
         *	  		   /	 \
         *	  		  /		  \
         *	        hom	  	  rad
         *	       /	\    /   \
         *	      fad	job x	  red
         */

        stringAVL1Tree.insert("red");
        assertEquals("Incorrect root detected after another insertion: Are you rotating *left* properly?",
                "qer", stringAVL1Tree.getRoot());
        assertEquals("Incorrect tree height after another insertion: "+
                "are you balancing properly?", 2, stringAVL1Tree.height());

        /* Finally, we need to check for some cases of LR and RL rotations.
         * We will check LR on the integer tree and RL on the string tree.
         *
         * Recall the current form of the integer tree:
         *
         *
         *  				-5
         * 				   /  \
         * 				  /    \
         * 				-10    -2
         * 				/  \   / \
         * 			  -20	x -4  -1
         *
         *
         *	Inserting -15 should cause an imbalance detected at node -10, addressible via a
         *  LR rotation about it which should modify the tree as follows:
         *
         *					-5
         *				   /   \
         *				  /     \
         *				-15		 -2
         *				/  \	 / \
         * 			  -20  -10  -4  -1
         */

        AVLTrees[0].insert(-15);
        assertEquals("Incorrect root detected after another insertion: are you performing unnecessary rotations?",
                new Integer(-5), AVLTrees[0].getRoot());
        assertEquals("Incorrect tree height after another insertion: are you balancing properly?",
                2, AVLTrees[0].height());

        /*	Now we will work on the string tree to see whether RL rotations work. As a reminder,
         *  this is what our tree should look like right now:
         *
         *
         * 				qer
         *	  		   /   \
         *	  		  /		\
         *	        hom	  	rad
         *	       /	\   /  \
         *	      fad  job x   red
         *
         *	Inserting "rbd" should cause an imbalance detected at "rad", addressible via a
         *	RL rotation which should modify the tree as follows:
         *
         *
         *				 qer
         *	  		   /	 \
         *	  		  /		  \
         *	        hom	  	  rbd
         *	       /	\    /   \
         *	      fad	job rad	  red
         */

        stringAVL1Tree.insert("rbd");
        assertEquals("Incorrect root detected after another insertion: are you performing unnecessary rotations?",
                "qer", stringAVL1Tree.getRoot());
        assertEquals("Incorrect tree height after another insertion: are you balancing properly?",
                2, stringAVL1Tree.height());

        /* For our final tests on insertion, we will see whether the students handle RL and LR rotations
         * *about the root* properly. We will first begin with a RL rotation:
         */

        AVLTrees[0] = new AVLGTree<Integer>();
        Integer[] keys1 = {ZERO, 5, 3};
        for(Integer k: keys1)
            AVLTrees[0].insert(k);

        /* Tree should be as follows after an RL rotation about the root triggered
         * at the insertion of '3':
         *
         * 				3
         * 			   / \
         * 			  /   \
         * 			 0	   5
         */

        assertEquals("Incorrect root detected: check your RL rotations!",
                new Integer(3), AVLTrees[0].getRoot());
        assertEquals("Incorrect tree height detected: check your RL rotations!",
                1, AVLTrees[0].height());

        /* And then also check LR rotations: */
        stringAVL1Tree = new AVLGTree<String>();
        String[] keys2 = {"gea", "beq", "car"};
        for(String k: keys2)
            stringAVL1Tree.insert(k);

        /* The tree should look this way:
         *
         * 			car
         * 		   /  \
         * 		  /	   \
         * 		beq     gea
         */

        assertEquals("Incorrect root detected: check your LR rotations!",
                "car", stringAVL1Tree.getRoot());
        assertEquals("Incorrect tree height detected: check your LR rotations!",
                1, stringAVL1Tree.height());
    }

    /** Deletion tests. */
    @Test
    public void testBalancedDeletions(){

        /* Our goal will be to check whether students deal correctly with deletions
         * that should cause rotations as well as deletions that should not cause rotations.
         * After every deletion, we will also perform a search() operation to make sure the
         * element cannot be found in the data structure. We are also interested in deleting
         * both inner nodes as well as leaf nodes (directly).
         *
         *  For our first test, let's try a deletion at the leaf level which shouldn't cause any
         *  rotations to occur.
         */

        Integer[] keys = {ZERO, 5, 3};
        for(Integer k : keys)
            AVLTrees[0].insert(k);

        /* Reminder: After all those insertions, the tree should look
         * like this:
         *
         * 				3
         * 			   / \
         * 			  /   \
         * 			0	   5
         *
         * Removing 0 should not affect the tree's height:
         */

        assertEquals("Removing a leaf node should return itself to the caller.", ZERO, AVLTrees[0].delete(ZERO));
        assertTrue("Once we remove a key, we should no longer be able to find it " + "in the tree.", AVLTrees[0].search(ZERO) == null);
        assertEquals("After this particular deletion, the tree's height should remain " +
                "the same.", 1, AVLTrees[0].height());

        /* However, deleting 5 should make it into a stub tree. */

        assertEquals("Removing a leaf node should return itself to the caller.",
                new Integer(5), AVLTrees[0].delete(5));
        assertTrue("Once we remove a key, we should no longer be able to find it " +
                "in the tree.", AVLTrees[0].search(5) == null);
        assertEquals("Incorrect tree height detected after deletions.", 0, AVLTrees[0].height());

        /* After all these deletions, the root should be 3. We will make sure of this. */

        assertEquals("Incorrect root detected after deletions.",
                new Integer(3), AVLTrees[0].getRoot());

        /* Now that we have a stub tree consisting of just the number 3,
         * we will need to expand the tree to perform a deletion that will
         * leave us with an unbalanced tree. We will create the following tree:
         *
         *  			3
         *  		  /   \
         *  		 /	   \
         *  		1       5
         * 					 \
         *  			  	  6
         *
         */

        AVLTrees[0].insert(1);
        AVLTrees[0].insert(5);
        AVLTrees[0].insert(6);

        /* Now, deleting 1 should cause an imbalance detected at the root.
         * This should be solved via a left rotation of the root, resulting in
         * the following tree:
         *
         * 					5
         * 				  /  \
         *			     /	  \
         *				3	   6
         */

        AVLTrees[0].delete(1); // No more need to check return values here.
        assertTrue("Once we remove a key, we should no longer be able to find it " +
                "in the tree.", AVLTrees[0].search(1) == null);
        assertEquals("Incorrect tree root. Are you rotating *left* correctly " +
                "after deletions?", new Integer(5), AVLTrees[0].getRoot());
        assertEquals("Incorrect tree height. Are you " +
                "re-balancing correctly after deletions?", 1, AVLTrees[0].height());

        /* Let's try the same scenario, only this time with a deletion that should trigger
         * a right root rotation. We will use the string tree to do this.
         */

        stringAVL1Tree.insert("Jason");
        stringAVL1Tree.insert("Matt");
        stringAVL1Tree.insert("George");
        stringAVL1Tree.insert("Gene");

        /* This is what the tree should look like right now:
         *
         * 					Jason
         * 					/	\
         * 				   /	 \
         * 				George	 Matt
         * 				 /
         * 			   Gene
         *
         * Removing Matt should trigger a right rotation about Jason, leaving us with the tree:
         *
         * 					George
         * 					/  \
         * 				   /    \
         * 				 Gene  Jason
         */

        stringAVL1Tree.delete("Matt");
        assertTrue("Once we remove a key, we should no longer be able to find it " +
                "in the tree.", stringAVL1Tree.search("Matt") == null);
        assertEquals("Incorrect tree root. Are you rotating *right* correctly after deletions?",
                "George", stringAVL1Tree.getRoot());
        assertEquals("Incorrect tree height. Are you re-balancing correctly after deletions?",
                1, stringAVL1Tree.height());

        /* The previous cases we examined had one thing in common: After deletions, a simple
         * left or right rotation of the root took care of the imbalance. We also need to check
         * whether students correctly take care of LR and RL rotations.
         *
         *  Let's check LR first, with the integer tree. We will create the following tree:
         *
         *  			10
         * 			   /  \
         * 			  /	   \
         * 			 5	    15
         * 			  \
         * 			   7
         *
         *   Which, after the deletion of 15, should be re-balanced via an LR rotation about
         *   the root, yielding the following result:
         *
         *   			7
         *   		   / \
         *   		  /   \
         *   		 5	   10
         */

        AVLTrees[0] = new AVLGTree<Integer>();
        keys = new Integer[]{10, 5, 15, 7};
        for(Integer k: keys)
            AVLTrees[0].insert(k);
        AVLTrees[0].delete(15);
        assertTrue("Once we remove a key, we should no longer be able to find it " +
                "in the tree.", AVLTrees[0].search(15) == null);
        assertEquals("Incorrect tree root. Are you rotating *LR* correctly after deletions?",
                new Integer(7), AVLTrees[0].getRoot());
        assertEquals("Incorrect tree height. Are you re-balancing correctly after deletions?",
                1, AVLTrees[0].height());

        /* We will now use the string tree to test whether students rotate *RL*
         * correctly after deletions. We will create the following tree:
         *
         *  				Karen
         *  				/	\
         *  			   /	 \
         *  			Jake	  Linda
         * 		`				  /
         * 						 Lauren
         *
         *  Then, removing Jake should trigger an RL rotation about the root, yielding
         *  the following result:
         *
         *
         * 				Lauren
         * 			    /	 \
         * 			   / 	  \
         * 			Karen     Linda
         */

        stringAVL1Tree = new AVLGTree<String>();
        String[] keys2 = new String[]{"Karen", "Jake", "Linda", "Lauren"};
        for(String k: keys2)
            stringAVL1Tree.insert(k);
        stringAVL1Tree.delete("Jake");
        assertTrue("Once we remove a key, we should no longer be able to find it " +
                "in the tree.", stringAVL1Tree.search("Jake") == null);
        assertEquals("Incorrect tree root. Are you rotating *RL* correctly after deletions?",
                "Lauren", stringAVL1Tree.getRoot());
        assertEquals("Incorrect tree height. Are you re-balancing correctly after deletions?",
                1, stringAVL1Tree.height());

        /* We will author two more tests with deletions which impose an RL or LR rotation.
         * In both of those cases, we have a LR or RL rotation which does *not* propagate to
         * the root, but rather to one of the root's children. Here's the tree for our first test:
         *
         *  				45
         *  			   /  \
         *  			  /    \
         *  			 20	   60
         *  			/  \   / \
         *  		   10  30 50  65
         *  			   /
         *  			  25
         *
         *   Deleting 10 should pose an imbalance at the root's left child (20), triggering
         *   a RL rotation about it and resulting in the tree:
         *
         *    				45
         *    			   /  \
         *    			  /    \
         *    			 25    60
         *    			/  \   / \
         *    		   20  30 50  65
         *
         *    Let's check whether it does:
         */

        AVLTrees[0] = new AVLGTree<Integer>();
        keys = new Integer[]{45, 20, 60, 10, 30, 50, 65, 25}; // Inserting them in this order guarantees the tree above.
        for(Integer k : keys)
            AVLTrees[0].insert(k);
        AVLTrees[0].delete(10);
        assertTrue("Once we remove a key, we should no longer be able to find it " +
                "in the tree.", AVLTrees[0].search(10) == null);
        assertEquals("Tree root should not have changed after the last deletion. " +
                        "Are you perhaps rotating too aggressively after deletions?" ,
                new Integer(45), AVLTrees[0].getRoot());
        assertEquals("Incorrect tree height. Are you re-balancing correctly after deletions?",
                2, AVLTrees[0].height());

        /* We will use the string tree to impose the symmetric test of the above
         * and conclude the deletion tests. We will create the following tree:
         *
         *   					kal
         *   					/ \
         *   				   /   \
         *   				 bet	rod
         *   				 / \	/  \
         *   				ar dog mad zed
         *   						\
         *   						nac
         *
         *   which, after removing "zed", should look like this:
         *
         *   				kal
         *   			   /    \
         *   			  /		 \
         *   			bet	     nac
         *   			/ \		/	\
         *   		  ar  dog  mad	rod
         */

        stringAVL1Tree = new AVLGTree<String>();
        keys2 = new String[]{"kal", "bet", "rod", "ar", "dog", "mad", "zed", "nac"};
        for(String k: keys2)
            stringAVL1Tree.insert(k);
        stringAVL1Tree.delete("zed");
        assertTrue("Once we remove a key, we should no longer be able to find it " +
                "in the tree.", stringAVL1Tree.search("zed") == null);
        assertEquals("Tree root should not have changed after this deletion. " +
                        "Are you perhaps rotating too aggressively after deletions?" ,
                "kal", stringAVL1Tree.getRoot());
        assertEquals("Incorrect tree height. Are you re-balancing correctly after deletions?",
                2, AVLTrees[0].height());

        /* Up until now, we have always deleted keys from the leaf level. We also
         * need to make sure that students correctly delete keys from inner nodes.
         *
         * For that purpose, we will re-create an integer tree from earlier:
         *
         * 				    45
         *  			   /  \
         *  			  /    \
         *  			 20	   60
         *  			/  \   / \
         *  		   10  30 50  65
         *  			   /
         *  			  25
         */

        AVLTrees[0] = new AVLGTree<Integer>();
        keys = new Integer[]{45, 20, 60, 10, 30, 50, 65, 25};
        for(Integer k : keys)
            AVLTrees[0].insert(k);

        /* Now, deleting 20 should promote 25 in its place, yet trigger no rotations:
         *
         *     				45
         *  			   /  \
         *  			  /    \
         *  			 25	   60
         *  			/  \   / \
         *  		   10  30 50  65
         */

        AVLTrees[0].delete(20);
        assertEquals("After a deletion of an inner node, the height of the tree was not " +
                "properly updated.", 2, AVLTrees[0].height());
        assertEquals("After a deletion of an inner node, the root was found to be needlessly changed.",
                new Integer(45), AVLTrees[0].getRoot());

        /* Now we will delete the root. This should result in the following tree:
         *
         * 					50
         *  			   /  \
         *  			  /    \
         *  			 25	   60
         *  			/  \     \
         *  		   10  30     65
         *
         *  Again, no rotations occur.
         */

        AVLTrees[0].delete(45);
        assertEquals("After root deletion, the height of the tree needlessly changed.",
                2, AVLTrees[0].height());
        assertEquals("After root deletion, the new root of the tree was incorrect.",
                new Integer(50), AVLTrees[0].getRoot());

        /* We will perform one last test on deletions, which will be our most complex one.
         * In this scenario, we delete the tree's root. This results in recursively deleting
         * its inorder successor from the leaf level. However, that very recursive deletion
         * triggers an RL rotation of its parent. Here's the tree that we will use (a slight
         * modification of an earlier integer tree):
         *
         * 					45
         *  			   /  \
         *  			  /    \
         *  			 25	   60
         *  			/  \   / \
         *  		   10  30 50  65
         *  					  /
         *  					 62
         */

        AVLTrees[0] = new AVLGTree<Integer>();
        keys = new Integer[]{45, 25, 60, 10, 30, 50, 65, 62};
        for(Integer k: keys)
            AVLTrees[0].insert(k);


        /* And here's what it should look like after we delete 45, the root:
         *
         * 					50
         *  			   /  \
         *  			  /    \
         *  			 25	   62
         *  			/  \   / \
         *  		   10  30 60  65
         */

        // Since this deletion is likely to uncover corner case issues with the code,
        // we will wrap it into a try-catch block and spice up the output to point the
        // students to the right direction.

        try {
            AVLTrees[0].delete(45);
        }catch(Throwable t){
            fail("In a deletion of the root which should also involve a local rotation, " +
                    "the code threw a " + t.getClass() + " with message:" + t.getMessage() + ".");
        }
        assertEquals("After deletion of the root, the new root promoted was incorrect.",
                new Integer(50), AVLTrees[0].getRoot());
        assertEquals("After deletion of the root, the height of the new tree was incorrect.",
                2, AVLTrees[0].height());
    }



    /** A stress test meant to catch any and all errors generated by the
     * students' code.
     */
    @Test
    public void testManyInsertions(){
        LinkedList<Integer> collector = new LinkedList<Integer>();
        for(int i = 0; i < NUMS; i++)
            collector.add(i);
        Collections.shuffle(collector);

        /* It's not particularly efficient to initiate a try / catch block
         * within a loop, but it will be helpful for the students so that they know
         * exactly which iteration failed, and what the key that caused the failure was.
         */
        for(int i = 0; i < collector.size(); i++){
            try {
                AVLTrees[0].insert(collector.get(i));
            }catch(Throwable t){
                fail("Caught a " + t.getClass() + " with message: " + t.getMessage() +
                        " in iteration " + i + ", when inserting key " + collector.get(i) + ".");
            }
        }
    }


    /** What if I insert a certain collection of numbers and then delete all of them?
     * Everything ok while doing that?
     */
    @Test
    public void testManyDeletions(){
        LinkedList<Integer> collector = new LinkedList<Integer>();
        for(int i = 0; i < NUMS; i++)
            collector.add(i);
        Collections.shuffle(collector);

        /* It's not particularly efficient to initiate a try / catch block
         * within a loop, but it will be helpful for the students so that they know
         * exactly which iteration failed, and what the key that caused the failure was.
         */
        for(int i = 0; i < collector.size(); i++){
            try {
                AVLTrees[0].insert(collector.get(i));
            }catch(Throwable t){
                fail("Caught a " + t.getClass() + " with message: " + t.getMessage() +
                        " in iteration " + i + ", when inserting key " + collector.get(i) + ".");
            }
        }

        // Now, delete all of the keys.
        for(int i = 0; i < collector.size(); i++){
            try {
                Integer retVal = AVLTrees[0].delete(collector.get(i));
                assertEquals("In iteration " + i +", when attempting to delete key " + collector.get(i) +
                                ", the return value of delete() did not match the key. Instead, it was: " + retVal + ".",
                        collector.get(i), retVal);
                assertTrue("In iteration " + i +", after deleting key " + retVal +
                                ", search() determined the key to still exist inside the tree.",
                        AVLTrees[0].search(retVal) == null);
            }catch(Throwable t){
                fail("Caught a " + t.getClass() + " with message: " + t.getMessage() +
                        " in iteration " + i + ", with the key " + collector.get(i) + ".");
            }
        }
        // After deletion, the tree had better be empty!
        assertTrue("We deleted all items that should be in the tree, yet the code " +
                "believes the tree is not empty.", AVLTrees[0].isEmpty());
    }

    /** Destroy the trees after every test. We could sequentially delete all nodes, but we will be
     * testing deletion separately anyway.
     */
    @After
    public void tearDown() {
        // Allow garbage collection to occur....
        AVLTrees[0] = null;
        stringAVL1Tree = null;
    }


}
