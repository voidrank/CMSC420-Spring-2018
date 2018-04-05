package projects.bpt;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class BinaryPatriciaTrieTest {

    private BinaryPatriciaTrie bpt;

    @Before
    public void setUp() throws Exception {
        bpt = new BinaryPatriciaTrie();
    }

    @Test
    public void testSearch() throws Exception {
        String[] array1 = {"0000", "0001", "000", "0", "01010", "011001", "000111", "000101",
                "10010", "110111", "110100", "111", "111101", "10100", "11111"};
        String[] array2 = {"001", "01", "0010", "00", "01011", "1", "11", "110", "1111", "1010",
                "1011", "1000", "11110", "111000", "1100110", "10101"};
        for(String s : array1)
            bpt.insert(s);
        for(String s : array1)
            assertEquals(true, bpt.search(s));
        for(String s : array2)
            assertEquals(false, bpt.search(s));
    }

    @Test
    public void testInsert() throws Exception {
        String[] strArray = {"0000", "0001", "000", "0", "000111", "000101", "10010", "110111", "1000", "11111"};
        String[] result = {"0000", "000", "000101", "0001", "000111", "0", "1000", "10010", "110111", "11111"};
        for(String s : strArray)
            bpt.insert(s);

        Iterator<String> iterator = bpt.inorderTraversal();
        List<String> list = new ArrayList<>();
        while(iterator.hasNext())
            list.add(iterator.next());
        assertArrayEquals(list.toArray(), result);
    }

    @Test
    public void testDelete() throws Exception {
        String[] strArray = {"0000", "0001", "000", "0", "000111", "000101", "10010", "110111",
                "110100", "111", "111101", "10100", "11111"};
        Set<String> set = new HashSet<>(Arrays.asList(strArray));
        for(String s : strArray)
            bpt.insert(s);

        for(String s : strArray) {
            bpt.delete(s);
            set.remove(s);
            Iterator<String> iterator = bpt.inorderTraversal();
            while (iterator.hasNext())
                assertEquals(set.contains(iterator.next()), true);
        }
    }

    @Test
    public void testGetLongest() throws Exception {
        String[] array = {"0000", "0001", "000", "0", "01010", "011001", "000111", "000101", "10010",
                "110111", "110100", "111", "111101", "10100", "11111", "001", "01", "0010",
                "00", "01011", "1", "11", "110", "1111", "1010", "1011", "1000", "11110", "111000",
                "110011", "10101"};
        for(String s : array)
            bpt.insert(s);
        String longest = bpt.getLongest();
        assertEquals(true, longest.equals("111101"));
    }
}