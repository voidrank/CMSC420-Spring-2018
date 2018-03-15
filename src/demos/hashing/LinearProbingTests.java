package demos.hashing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by Jason on 7/2/2017.
 */
public class LinearProbingTests {

    private LinearProbingHashTable ht;
    private String[] desserts = new String[]{
            "waffles", "pancakes", "grapes", "yoghurt", "ice cream", "limoncello", "cookies", "lemon cake",
            "quiche lorraine", "strawberries", "red velvet cake", "tiramissu", "milfeuille"
    };

    private static final int MAX_INSERTS = 200;

    /* Setup and teardown methods */
    @Before
    public void setUp(){
        ht = new LinearProbingHashTable();
    }

    @After
    public void tearDown(){
        ht = null;
        System.gc();
    }

    /* Testing the API */
    @Test
    public void testEmptyAndCount(){
        assertTrue("Hash Table should be empty.", ht.isEmpty());
        assertEquals("Count of keys in hash table should be 0.", 0, ht.getCount());
        ht.insert("aba");
        assertFalse("Hash Table should no longer be empty.", ht.isEmpty());
        assertEquals("Count of keys in hash table should be 1.", 1, ht.getCount());
    }

    @Test
    public void testDuplicateInsertions(){
        ht.insert("aba");
        assertNotEquals("aba should have been found in the hash table.", null, ht.search("aba"));
        ht.insert("aba");
        assertNotEquals("aba should still have been found in the hash table.", null, ht.search("aba"));
        assertFalse("Hash Table should no longer be empty.", ht.isEmpty());
        assertEquals("Element count of hash table should be 1.", 1, ht.getCount());
    }

    @Test
    public void testInsertAndSearch(){
        for(String s : desserts)
            ht.insert(s);
        assertEquals("Count of hash table and number of input strings should be identical.", desserts.length, ht.getCount());
        assertFalse("Hash Table should not be considered empty.", ht.isEmpty());

        for(String s: desserts)
            assertNotEquals("String " + s + " was not found in the table.", null, ht.search(s));
    }

    @Test
    public void stressTestInsert(){
        IntStream.range(0, 100).forEach(x->ht.insert(String.valueOf(x)));
        try {
            IntStream.range(100, 200).forEach(x->ht.insert(String.valueOf(x)));
        } catch(MaxEnlargementsReachedException ignored){
            // Good, we expected this.
        } catch(Throwable t){
            fail("Caught a " + t.getClass().getSimpleName() + " with message " + t.getMessage() + ".");
        }
    }

    // No deletion tests yet...

    @Test
    public void testDeleteInTable(){
        // Test for deletion of keys in table.
        IntStream.range(0, 100).forEach(x->ht.insert(String.valueOf(x)));
        assertEquals("Count mismatch after inserting 100 keys", 100, ht.getCount());

        assertEquals(ht.search(String.valueOf(1)), String.valueOf(1)); // ??
        ht.delete(String.valueOf(1));
        assertEquals("Count mismatch after deleting 1 key", 99, ht.getCount());

        IntStream.range(10, 20).forEach(x->ht.delete(String.valueOf(x)));
        assertEquals("Count mismatch after deleting 1 key", 89, ht.getCount());

    }

    @Test
    public void testDeleteNotInTable(){
        IntStream.range(0, 100).forEach(x->ht.insert(String.valueOf(x)));
        ht.delete(String.valueOf(1000)); // should not affect count
        assertEquals("Count mismatch after deleting a key that should not be in the hash table", 100, ht.getCount());
    }
}
