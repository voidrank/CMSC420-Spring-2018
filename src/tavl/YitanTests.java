package tavl;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/** A class containing jUnit tests to test the students' code with.
 *
 * @author <a href="https://github.com/jasonfil" alt="Jason Filippou's GitHub page">Jason Filippou</a>
 */
public class YitanTests {



    @Test
    public void easyTest(){
        ThreadedAVLTree<Integer> intTree = new ThreadedAVLTree<>();
        assertTrue("The tree should be empty!", intTree.isEmpty());
        Assert.assertEquals("The tree should have a height of -1!", -1 , intTree.height());
        intTree.insert(8);
        assertFalse("The tree should be empty!", intTree.isEmpty());
        Assert.assertEquals("The tree should have a height of 0!", 0, intTree.height());
        intTree.delete(8);
        assertTrue("The tree should be empty!", intTree.isEmpty());
        Assert.assertEquals("The tree should have a height of -1!", -1 , intTree.height());
        try {
            intTree.delete(-1); // This shouldn't do anything, but it's interesting to investigate if it throws an exception
        } catch(Throwable t) {
            fail("Caught a " + t.getClass().getSimpleName() + " in easyTest(). The message was: " + t.getMessage() + ".");
        }
    }

    @Test
    public void tougherTest(){
        ThreadedAVLTree<Integer> intTree = new ThreadedAVLTree<>();
        Integer[] arr = {50, 10, 56, 43, 25, 94, 20};
        for(Integer i:arr){
            try {
                intTree.insert(i);
            }catch(Throwable t) {
                fail("Caught a " + t.getClass().getSimpleName() + " in tougherTest() while inserting integer: " + i +
                        ". The message was: " + t.getMessage() + ".");
            }
        }
        Assert.assertEquals("The height of the tree was wrong.", 3, intTree.height());
        for(Integer i:arr){
            try {
                intTree.delete(i);
            }catch(Throwable t) {
                fail("Caught a " + t.getClass().getSimpleName() + " in tougherTest() while deleting integer: " + i +
                        ". The message was: " + t.getMessage() + ".");
            }
        }
        Assert.assertEquals("The height of the tree was wrong.", -1, intTree.height());
        assertTrue("The tree should be empty!", intTree.isEmpty());
    }
}