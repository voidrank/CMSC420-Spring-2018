
package projects.avlg;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import projects.avlg.exceptions.EmptyTreeException;
import projects.avlg.exceptions.InvalidBalanceException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/** A class containing jUnit tests to test the students' code with.
 *
 * @author <a href="https://github.com/jasonfil">Jason Filippou</a>
 */
public class AVLGTreeTests {

    private ArrayList<AVLGTree<Integer>> trees = new ArrayList<>(MAX_IMBALANCE);
    private static final Random RNG = new Random(47);

    private static final int MAX_IMBALANCE=10;
    private static final Integer ZERO = 0;
    private static final int NUMS = 150;

    /**
     * Set-up the trees that we will use for our tests.
     */
    @Before
    public void setUp() {
        IntStream.rangeClosed(1, MAX_IMBALANCE).forEach(imb ->
        {
            try {
                trees.add(new AVLGTree<Integer>(imb));
            } catch (InvalidBalanceException i) {
                throw new RuntimeException(i.getMessage());
            }
        });
    }

    @After
    public void tearDown(){
        trees.forEach(AVLGTree::clear);
    }

    @Test
    public void testInvalidImbalances(){
        IntStream.range(0, NUMS).forEach(imb->
        {
            InvalidBalanceException expected = null;
            try {
                new AVLGTree<Integer>(-imb); // Zero or negative imbalance
            } catch(InvalidBalanceException thrown){
                expected = thrown;
            }
            assertNotNull("", expected);
        });
    }

    @Test
    public void testEmptyTree(){
        trees.forEach(t->
        {
            assertTrue("Upon creation, an AVL-"+t.getMaxImbalance() +
                    " tree should be empty.", t.isEmpty());
            assertEquals("Upon creation, an AVL-"+t.getMaxImbalance() +
                    " tree should have a height of -1.", -1, t.getHeight());
            assertEquals("Upon creation, an AVL-"+t.getMaxImbalance() +
                    " tree should have a count of 0.", 0, t.getCount());
            EmptyTreeException expected = null;
            try {
                t.getRoot();
            } catch(EmptyTreeException thrown){
                expected = thrown;
            }
            assertNotNull("Upon creation, retrieving the root of " +
                    "an AVL-" + t.getMaxImbalance() + " tree should throw an " +
                    "EmptyTreeException.", expected);
        });
    }

    @Test
    public void testTwoInsertionsAndDeletions(){
        trees.forEach(t->
        {
           // Make an insertion and test everything.

            Integer firstKey = RNG.nextInt();
            t.insert(firstKey);
            assertFalse("After inserting a key, the AVL-" +
                    t.getMaxImbalance()+ " tree should no longer be empty.",
                    t.isEmpty());
            assertEquals("After inserting a key, the AVL-" +
                    t.getMaxImbalance()+ " tree's new height should be 0.",
                    0, t.getHeight());
            assertEquals("After inserting a key, the AVL-" +
                            t.getMaxImbalance()+ " tree's new count should be 1.",
                    1, t.getCount());
            try {
                assertEquals("After inserting a key in an AVL-" +
                        t.getMaxImbalance()+ " tree, a search for it should be successful.",
                        firstKey, t.search(firstKey));
            }catch(EmptyTreeException ignored){
                fail("search(Key) should *not* have thrown an EmptyTreeException at this point.");
            }
            try {
                assertEquals("After inserting a key in an AVL-" +
                        t.getMaxImbalance()+ " tree, it should be the tree's root.",
                        firstKey, t.getRoot());
            } catch(EmptyTreeException ignored){
                fail("getRoot() should *not* have thrown an EmptyTreeException at this point.");
            }

            // Insert a second node and test everything

            Integer secondKey = RNG.nextInt();
            t.insert(secondKey); // Will either be left or right of root. No rotations irrespective of parameter G.
            assertFalse("After inserting a second key, the AVL-" +
                            t.getMaxImbalance()+ " tree should still *not* be empty.",
                            t.isEmpty());
            assertEquals("After inserting a second key, the AVL-" +
                            t.getMaxImbalance()+ " tree's new height should be 01",
                    1, t.getHeight());// Irrespective of G, this tree will have a height of 1.
            assertEquals("After inserting a key, the AVL-" +
                            t.getMaxImbalance()+ " tree's new count should be 2.",
                    2, t.getCount());
            try {
                assertEquals("After inserting a second key in an AVL-" +
                                t.getMaxImbalance()+ " tree, a search for the first one should" +
                                "*still* be successful.", firstKey, t.search(firstKey));
                assertEquals("After inserting a second key in an AVL-" +
                        t.getMaxImbalance()+ " tree, a search for the new key should" +
                         "be successful.", secondKey, t.search(secondKey));
            } catch(EmptyTreeException ignored){
                fail("search(Key) on an AVL-" + t.getMaxImbalance() + " tree should *not* have " +
                        "thrown an EmptyTreeException at this point.");
            }
            try {
                assertEquals("After the second key is inserted in an AVL-" + t.getMaxImbalance() +
                        "tree, the first key should still be at the root.", firstKey, t.getRoot()); // Irrespective of G, the root should *not* have changed. This is not a splay tree.
            } catch(EmptyTreeException ignored){
                fail("getRoot() on an AVL-" + t.getMaxImbalance() + " tree should *not* have " +
                        "thrown an EmptyTreeException at this point since we had another " +
                        "insertion and the tree should *still* be non-empty.");
            }

            // Delete the root and see what happens.

            try {
                assertEquals("When deleting the key " + firstKey + " from an AVL-" + t.getMaxImbalance() +
                        " tree, we expected delete() to return the key itself.", firstKey, t.delete(firstKey));
            } catch(EmptyTreeException ignored){
                fail("delete(Key) threw an EmptyTreeException when deleting key " + firstKey +
                        " in an AVL-"+ t.getMaxImbalance() + " tree.");
            }
            assertEquals("", 0, t.getHeight());
            assertEquals("", 1, t.getCount());
            assertFalse("", t.isEmpty());

            try {
                assertEquals("", secondKey, t.getRoot());
            } catch(EmptyTreeException ignored){
                fail("getRoot() on an AVL-" + t.getMaxImbalance() + " tree should *not* have " +
                        "thrown an EmptyTreeException since the deletion of the first key" +
                        " did not leave the tree empty.");
            }

            try {
                assertEquals("", secondKey, t.search(secondKey));
            } catch(EmptyTreeException ignored){
                fail("search() on an AVL-" + t.getMaxImbalance() + " tree threw " +
                        "an EmptyTreeException when searching a tree for its single key.");
            }

            // Delete the second key, making the tree empty, and see what happens.

            try {
                assertEquals("When deleting the key " + secondKey + " from an AVL-" + t.getMaxImbalance() +
                        " tree, we expected delete() to return the key itself.", secondKey, t.delete(secondKey));
            } catch(EmptyTreeException ignored){
                fail("delete(Key) threw an EmptyTreeException when deleting key " + secondKey +
                        " in an AVL-"+ t.getMaxImbalance() + " tree.");
            }
            assertEquals("", -1, t.getHeight());
            assertEquals("", 0, t.getCount());
            assertTrue("", t.isEmpty());

            EmptyTreeException expected = null;
            try {
                t.getRoot();
            } catch(EmptyTreeException thrown){
                expected = thrown;
            }
            assertNotNull("" , expected);

            expected = null;
            try {
                t.search(secondKey);
            } catch(EmptyTreeException thrown){
                expected = thrown;
            }
            assertNotNull("" , expected);
        });
    }

    @Test
    public void testSuccessfulInsertions(){
        List<Integer> keys = IntStream.range(0, NUMS).boxed().collect(Collectors.toList());
        Collections.shuffle(keys, RNG);
        trees.forEach(t->keys.forEach(k-> {
            try {
                t.insert(k);
            } catch(Throwable thr){
                fail("Caught a " + thr.getClass().getSimpleName() + " when inserting the key " + k +
                        " into a tree with maxImbalance parameter " + t.getMaxImbalance() + ".");
            }
        }));
    }

    @Test
    public void testSuccessfulDeletions(){
        List<Integer> keys = IntStream.range(0, NUMS).boxed().collect(Collectors.toList());
        Collections.shuffle(keys, RNG);
        trees.forEach(t->keys.forEach(t::insert)); // Since we've already tested those above...
        trees.forEach(t->keys.forEach(k-> {
            try {
                assertEquals("When deleting the key " + k + " from an AVL-" + t.getMaxImbalance()+
                        " tree, we expected delete() to return the key itself.", k, t.delete(k));
            } catch(Throwable thr){
                fail("Caught a " + thr.getClass().getSimpleName() + " when deleting the key " + k +
                        " from a tree with maxImbalance parameter " + t.getMaxImbalance() + ".");
            }
        }));
    }

    @Test
    public void testSuccessfulSearches(){
        List<Integer> keys = IntStream.range(0, NUMS).boxed().collect(Collectors.toList());
        Collections.shuffle(keys, RNG);
        trees.forEach(t->keys.forEach(t::insert)); // Since we've already tested those above...
        trees.forEach(t->{
            keys.forEach(k->{
                try {
                    assertEquals("Key " + k + " should have been found in the AVL-"
                                    + t.getMaxImbalance() + " tree.",  k, t.search(k));
                } catch(EmptyTreeException ignored){
                    fail("search(Key) threw an EmptyTreeException when searching for key " + k +
                            " in an AVL-" + t.getMaxImbalance() + " tree.");
                }
            });
        });
    }

    @Test
    public void testFailedSearches(){
        List<Integer> keys = IntStream.range(0, NUMS).boxed().collect(Collectors.toList());
        Collections.shuffle(keys, RNG);
        trees.forEach(t->keys.forEach(t::insert)); // Since we've already tested those above...
        trees.forEach(t->{
            keys.stream().filter(x->(x>0)).forEach(k->{
                try {
                    assertEquals("", null, t.search(-k));
                    assertNotEquals("", k, t.search(-k));
                    assertNotEquals("", Integer.valueOf(-k), t.search(k));
                    try {
                        t.delete(k);
                    } catch(EmptyTreeException ignored){
                        fail("delete(Key) threw an EmptyTreeException when deleting key " + k +
                                " in an AVL-"+ t.getMaxImbalance() + " tree.");
                    }
                    assertEquals("", null, t.search(k));
                    assertEquals("", null, t.search(-k));
                } catch(EmptyTreeException ignored){
                    fail("search(Key) threw an EmptyTreeException when searching for key " + k +
                            " in an AVL-"+ t.getMaxImbalance() + " tree.");
                }
            });
        });
    }

    @Test
    public void testCountDeleteAndClear(){
        List<Integer> keys = IntStream.range(0, NUMS).boxed().collect(Collectors.toList());
        Collections.shuffle(keys, RNG);
        trees.forEach(t->{
            keys.forEach(t::insert);
            assertEquals("", keys.size(), t.getCount());
            t.clear();
            assertTrue("", t.isEmpty());
            assertEquals("", 0, t.getCount());

            // Re-insert and delete everything explicitly.
            keys.forEach(t::insert);
            keys.forEach(k->{
                try {
                    t.delete(k);
                } catch(EmptyTreeException ignored){
                    fail("delete(Key) threw an EmptyTreeException when deleting key " + k + ".");
                }
            });
            assertTrue("", t.isEmpty());
            assertEquals("", 0, t.getCount());
        });
    }

    @Test
    public void testBalancedInsertionsAVL1(){

        // (1) A single left rotation

        // (2) A single right rotation

        // (3) A right-left rotation

        // (4) A left-right rotation
    }

    @Test
    public void testBalancedInsertionsAVL2(){

        // (1) A single left rotation

        // (2) A single right rotation

        // (3) A right-left rotation

        // (4) A left-right rotation

    }

    @Test
    public void testBalancedInsertionsAVL3(){

        // (1) A single left rotation

        // (2) A single right rotation

        // (3) A right-left rotation

        // (4) A left-right rotation

    }

    @Test
    public void testBalancedInsertionsAVL4(){

        // (1) A single left rotation

        // (2) A single right rotation

        // (3) A right-left rotation

        // (4) A left-right rotation

    }

    @Test
    public void testBalancedDeletionsAVL1(){

        // (1) A single left rotation

        // (2) A single right rotation

        // (3) A right-left rotation

        // (4) A left-right rotation

    }

    @Test
    public void testBalancedDeletionsAVL2(){

        // (1) A single left rotation

        // (2) A single right rotation

        // (3) A right-left rotation

        // (4) A left-right rotation

    }

    @Test
    public void testBalancedDeletionsAVL3(){

        // (1) A single left rotation

        // (2) A single right rotation

        // (3) A right-left rotation

        // (4) A left-right rotation

    }

    @Test
    public void testBalancedDeletionsAVL4(){

        // (1) A single left rotation

        // (2) A single right rotation

        // (3) A right-left rotation

        // (4) A left-right rotation

    }
}
