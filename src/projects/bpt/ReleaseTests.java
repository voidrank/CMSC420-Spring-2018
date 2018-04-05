package projects.bpt;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

//@SuppressWarnings("unused")

public class ReleaseTests {


    /************************************************* General test ****************************************/

    @Test public void testSimple1() {
        BinaryPatriciaTrie tree = new BinaryPatriciaTrie();

        assertTrue("Trie should be empty",tree.isEmpty());
        assertTrue("Trie size should be 0",tree.getSize() == 0);

        assertFalse("No string inserted so search should fail", tree.search("0101"));
        assertTrue("String should be inserted successfully",tree.insert("0101"));
        assertFalse("String should not be inserted as it already exists" , tree.insert("0101"));

    }

    @Test public void testSimple2() {
        BinaryPatriciaTrie tree = new BinaryPatriciaTrie();

        assertTrue("String should be inserted successfully",tree.insert("00000"));
        assertTrue("String should be inserted successfully",tree.insert("00011"));
        assertFalse("Search should fail as string does not exist",tree.search("000"));
        assertTrue("String should be inserted successfully",tree.insert("000"));
        assertTrue("String exists and should be found", tree.search("000"));
        assertTrue("String should be inserted successfully",tree.insert("11011"));
        assertTrue("String should be inserted successfully",tree.insert("1"));
        String s = tree.getLongest();
        assertEquals("longest string is 11011", s ,"11011");
    }



    @Test public void testSimple3() {
        BinaryPatriciaTrie tree = new BinaryPatriciaTrie();

        assertTrue("String should be inserted successfully" , tree.insert("00000"));
        assertTrue("String should be deleted successfully" ,tree.delete("00000"));

        assertEquals("Trie size should be 0", tree.getSize(), 0);
    }




    @Test public void testSimple4() {

        BinaryPatriciaTrie t = new BinaryPatriciaTrie();

        assertTrue("String should be inserted successfully" , t.insert("100"));
        assertTrue("String should be inserted successfully" , t.insert("101"));


        // delete process
        assertTrue("String should be deleted successfully",t.delete("100"));
        assertFalse("String 100 should not be found in the trie",t.search("100"));
        assertTrue("String should be deleted successfully",t.delete("101"));
        assertFalse("String 100 should not be found in the trie",t.search("101"));
        assertEquals("Trie size should be 0" , t.getSize(), 0);


    }



    @Test public void testSimple5() {

        BinaryPatriciaTrie t = new BinaryPatriciaTrie();

        assertTrue("String should be inserted successfully" , t.insert("100"));
        assertTrue("String should be inserted successfully" , t.insert("101"));
        assertTrue("String should be inserted successfully" , t.insert("0"));
        assertTrue("String should be inserted successfully" , t.insert("10111"));
        assertTrue("String should be inserted successfully" , t.insert("1011"));
        assertTrue("String should be inserted successfully" , t.insert("1010"));
        assertTrue("String should be inserted successfully" , t.insert("1"));
        assertTrue("String should be inserted successfully" , t.insert("111"));
        assertTrue("String should be inserted successfully" , t.insert("11"));
        assertTrue("String should be inserted successfully" , t.insert("110"));
        assertTrue("String should be inserted successfully" , t.insert("10110"));

        assertTrue("String should be deleted successfully",t.delete("101"));
        assertFalse("String 101 should not be found in the trie",t.search("101"));
        assertFalse("String should not be inserted as it already exists", t.insert("100"));
        assertFalse("String 10 should not be found in the trie",t.search("10"));


    }


    /************************************** Testing each function ****************************/



    //Testing search function
    @Test public void testSearch() {
        BinaryPatriciaTrie tree = new BinaryPatriciaTrie();
        //Case 1: Searching in an empty tree
        assertFalse("No strings have been inserted yet search should return false",tree.search("0101"));

        //Case 2: Searching for an existing string
        tree.insert("0101");
        assertTrue("String has been insert so it should be found",tree.search("0101"));

        //Case 3: Searching for an non-existing string
        assertFalse("String has not been insert so it should not be found",tree.search("0111"));
    }

    //testing isEmpty function
    @Test public void testisEmpty() {
        BinaryPatriciaTrie tree = new BinaryPatriciaTrie();
        //Case 1: isEmpty for an empty trie
        assertTrue("Trie should be empty",tree.isEmpty());

        //Case 2: isEmpty for a non-empty trie
        tree.insert("0101");
        assertFalse("Trie should be not be empty as it has one string",tree.isEmpty());

        //Case 3: isEmpty after inserting and deleteing a string
        tree.delete("0101");
        assertTrue("Trie should be empty",tree.isEmpty());
    }

    //testing getSize function
    @Test public void testgetSize() {
        BinaryPatriciaTrie tree = new BinaryPatriciaTrie();
        //Case 1: getSize for an empty trie
        assertEquals("Trie is empty so size should be zero" , tree.getSize() , 0);

        //Case 2: getSize  after adding strings
        tree.insert("0101");
        assertEquals("Trie should size should be 1",tree.getSize(), 1);

        tree.insert("0100");
        assertEquals("Trie should size should be 2",tree.getSize(),2);

        tree.insert("1100");
        assertEquals("Trie should size should be 3",tree.getSize(),3);


        //Case 3: getSize after deleting strings
        tree.delete("0101");
        assertEquals("Trie should size should be 2",tree.getSize(), 2);

        tree.delete("0100");
        assertEquals("Trie should size should be 1",tree.getSize() ,1);

        tree.delete("1100");
        assertEquals("Trie should size should be 0",tree.getSize(),0);


    }


    //testing getLongest function
    @Test public void testgetLongest() {
        BinaryPatriciaTrie tree = new BinaryPatriciaTrie();
        //Case 1: getLongest for an empty trie
        assertEquals("Trie is empty so no strings" , tree.getLongest() , "");

        //Case 2: getLongest  after adding strings

        //A) Adding a string with length 2
        tree.insert("11");
        assertEquals("Longest string is 11",tree.getLongest() , "11");

        //B) Adding a string with length 3
        tree.insert("0101");
        assertEquals("Longest string is 0101",tree.getLongest()  , "0101");

        //C)Adding a string with length 3 but smaller then the previous one
        tree.insert("0100");
        assertEquals("Longest string is 0101",tree.getLongest() , "0101");

        //D)Adding a string with length 3 larger then the prevous one
        tree.insert("1100");
        assertEquals("Longest string is 1100",tree.getLongest() , "1100");


        //Case 3: getLongest after deleting strings

        tree.delete("1100");
        assertEquals("Longest string is 0101",tree.getLongest() ,  "0101");

        tree.delete("0101");
        assertEquals("Longest string is 0100",tree.getLongest() ,  "0100");

    }



    /**
     * Make sure the trie generate the proper inorder traversal of strings.
     */


    /**************** SIMPLE InorderTraversal ***********************/


    @Test
    public void testSimpleInorderTraversal(){
        BinaryPatriciaTrie t = new BinaryPatriciaTrie();

        //Simple case when all strings have same length
        t.insert("000001"); //1
        t.insert("010010");//2
        t.insert("010111");//3
        t.insert("011011");//4
        t.insert("011101");//5
        t.insert("011111");//6
        t.insert("100100");//7
        t.insert("110001");//8
        t.insert("110110");//9



        Iterator<String> inorder = t.inorderTraversal();

        String[] orderedstrings = {"000001", "010010", "010111", "011011","011101", "011111","100100", "110001","110110"};
        int NUMS = t.getSize();
        for(int i = 0; i <NUMS ; i++){
            assertTrue("Generated inorder traversal unexpectedly ran out of keys  "
                    + "after key #" +   i + ".",inorder.hasNext() );
            String next = inorder.next();


            assertEquals("Mismatch between expected value and generated value at string " + next
                    + ". ", new String(orderedstrings[i]), next);

        }
        String[] NewOrderedstrings =  { "010010", "010111", "011011", "011111","100100","110110"};

        // delete process
        t.delete("000001");
        t.delete("011101");
        t.delete("110001");

        NUMS = t.getSize();
        assertEquals("Size of trie should be 6" , NUMS, 6);
        inorder = t.inorderTraversal();
        for(int i = 0; i <NUMS ; i++){

            assertTrue("Generated inorder traversal unexpectedly ran out of keys "
                    + "after key #" +   i + ".",inorder.hasNext() );


            String next = inorder.next();
            assertEquals("Mismatch between expected value and generated value at string " + next
                    + ". ", NewOrderedstrings[i], next);

        }



    }


    /**************** complex InorderTraversal ***********************/


    @Test
    public void testComplexInorderTraversal(){
        BinaryPatriciaTrie t = new BinaryPatriciaTrie();


        //Case 1: Add strings with different size and check if in order traversal works properly


        t.insert("100");//4
        t.insert("101");//5
        t.insert("0");//1
        t.insert("10111");//11
        t.insert("1011");//9
        t.insert("1010");//8
        t.insert("1");//2
        t.insert("111");//7
        t.insert("11");//3
        t.insert("110");//6
        t.insert("10110");//10


        String[] orderedstrings = {"0", "100", "1010", "101", "10110", "1011", "10111", "1", "110", "11", "111"};


        Iterator<String> inorder = t.inorderTraversal();
        int NUMS = t.getSize();
        assertEquals("Size of trie should be 11" , NUMS, 11);
        for(int i = 0; i <NUMS ; i++){

            assertTrue("Generated inorder traversal unexpectedly ran out of keys "
                    + "after key #" +   i + ".",inorder.hasNext() );

            String next = inorder.next();

            assertEquals("Mismatch between expected value and generated value at string " + next
                    + ". ",new String( orderedstrings[i]), next);

        }


        //Case 2: Inorder After deleting strings

        String[] NewOrderedstrings = {"0", "100", "1010", "101", "10110", "1011",  "11", "111"};



        // delete process
        t.delete("1");
        t.delete("110");
        t.delete("10111");

        NUMS = t.getSize();
        inorder = t.inorderTraversal();
        assertEquals("Size of trie should be 8" , NUMS, 8);
        for(int i = 0; i <NUMS ; i++){

            assertTrue("Generated inorder traversal unexpectedly ran out of keys "
                    + "after key #" +   i + ".",inorder.hasNext() );


            String next = inorder.next();
            assertEquals("Mismatch between expected value and generated value at string " + next
                    + ". ", NewOrderedstrings[i], next);

        }


    }


    //testing insert function
    @Test public void testinsert() {
        BinaryPatriciaTrie tree = new BinaryPatriciaTrie();
        //case 1: Added a new string
        assertTrue("String should be inserted successfully",tree.insert("0101"));

        //case 2: Adding a string that is already in the trie
        assertFalse("String already exists in the trie",tree.insert("0101"));

        //case 3: Inserting a string with equal length but different keys
        assertTrue("String should be inserted successfully",tree.insert("1101"));
        //Checking that they are inserted correctly

        Iterator<String> inorder = tree.inorderTraversal();
        String[] orderedstringsCase3 = {"0101" , "1101"};

        int NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
            String next = inorder.next();

            assertEquals("Mismatch between expected value and output value value at string " + next
                    + ". ", new String(orderedstringsCase3[i]), next);

        }

        //case4: Inserting a key with a smaller length but is a prefix of an existing string
        assertTrue("String should be inserted successfully",tree.insert("11"));
        assertTrue("String should be inserted successfully",tree.insert("0"));
        inorder = tree.inorderTraversal();
        String[] orderedstringsCase4 = {"0" , "0101" , "1101" , "11"};

        NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
            String next = inorder.next();

            assertEquals("Mismatch between expected value and output value value at string " + next
                    + ". ", new String(orderedstringsCase4[i]), next);

        }



        //case5: Insert a string which has a prefix in the trie
        assertTrue("String should be inserted successfully",tree.insert("111"));
        assertTrue("String should be inserted successfully",tree.insert("00"));

        inorder = tree.inorderTraversal();
        String[] orderedstringsCase5 = {"00" , "0" , "0101" , "1101","11" , "111"};

        NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
            String next = inorder.next();
            assertEquals("Mismatch between expected value and output value value at string " + next
                    + ". ", new String(orderedstringsCase5[i]), next);

        }


        //case6: Insert a longer string with prefix not in Trie
        tree.delete("11");
        assertTrue("String should be inserted successfully",tree.insert("1000000000"));
        inorder = tree.inorderTraversal();
        String[] orderedstringsCase6 = {"00" , "0" , "0101" ,"1000000000",  "1101" , "111"};

        NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
            String next = inorder.next();
            assertEquals("Mismatch between expected value and output value value at string " + next
                    + ". ", new String(orderedstringsCase6[i]), next);

        }


    }



    @Test public void testdelete() {
        BinaryPatriciaTrie tree = new BinaryPatriciaTrie();
        //Delete node from an empty tree
        assertFalse("Trie is empty can not should be able to delete any string" ,tree.delete("0"));

        /*********************BPTNode of interest is the root***************/

        Iterator<String> it = tree.inorderTraversal();
        //Case1: Has a left and a right child
        tree.insert("0");
        tree.insert("00");
        tree.insert("01");

        assertTrue("String should be deleted successfully" ,tree.delete("0"));
        String[] rootCase1 = {"00" , "01"};
        it = tree.inorderTraversal();
        int NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
            String next = it.next();
            assertEquals("Mismatch between expected value and output value value at string " + next
                    + ". ", new String(rootCase1[i]), next);

        }


        //Case2: Has a left child only
        tree.insert("0");
        tree.delete("01");

        assertTrue("String should be deleted successfully" ,tree.delete("0"));
        String[] rootCase2 = {"00"};
        it = tree.inorderTraversal();
        NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
            String next = it.next();
            assertEquals("Mismatch between expected value and output value value at string " + next
                    + ". ", new String(rootCase2[i]), next);

        }


        //Case3: Has right child only
        tree.insert("0");
        tree.insert("01");
        tree.delete("00");
        assertTrue("String should be deleted successfully" ,tree.delete("0"));
        String[] rootCase3 = {"01"};
        it = tree.inorderTraversal();
        NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
            String next = it.next();
            assertEquals("Mismatch between expected value and output value value at string " + next
                    + ". ", new String(rootCase3[i]), next);

        }
        tree.delete("01");
        NUMS = tree.getSize();

        assertEquals("Size of trie should be 0" , NUMS, 0);

        /*********************BPTNode of interest is the left child***************/


        //Case1: Has a left and a right child
        tree.insert("10");
        tree.insert("100");
        tree.insert("101");
        tree.insert("1000");
        tree.insert("10000");
        tree.insert("10001");
        tree.insert("10010");
        tree.insert("10011");
        tree.insert("1001");

        assertTrue("String should be deleted successfully" ,tree.delete("100"));
        String[] leftCase1 = {"10000" , "1000", "10001" , "10010","1001","10011","10","101"};
        it = tree.inorderTraversal();
         NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
            String next = it.next();
            assertEquals("Mismatch between expected value and output value value at string " + next
                    + ". ", new String(leftCase1[i]), next);

        }
        assertFalse("String can't be deleted as it no longer exists in trie" ,tree.delete("100"));

        //Case2: Has a left child only
        tree.insert("100");
        tree.delete("1001");

        assertTrue("String should be deleted successfully" ,tree.delete("100"));
        String[] leftCase2 = {"10000" , "1000", "10001" , "10010","10011","10","101"};
        it = tree.inorderTraversal();
        NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
            String next = it.next();
            assertEquals("Mismatch between expected value and output value value at string " + next
                    + ". ", new String(leftCase2[i]), next);

        }
        assertFalse("String can't be deleted as it no longer exists in trie" ,tree.delete("100"));


        //Case3: Has right child only
        tree.insert("100");
        tree.insert("1001");
        tree.delete("1000");
        assertTrue("String should be deleted successfully" ,tree.delete("100"));
        String[] leftCase3 ={"10000" , "10001" , "10010","1001","10011","10","101"};
        it = tree.inorderTraversal();
        NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
            String next = it.next();
            assertEquals("Mismatch between expected value and output value value at string " + next
                    + ". ", new String(leftCase3[i]), next);

        }

        assertFalse("String can't be deleted as it no longer exists in trie" ,tree.delete("100"));


    /*********************BPTNode of interest is the right child***************/


    //Case1: Has a left and a right child
        tree.insert("100");
        tree.insert("1010");
        tree.insert("1011");
        tree.insert("10100");
        tree.insert("10101");
        tree.insert("10110");
        tree.insert("10111");

    assertTrue("String should be deleted successfully" ,tree.delete("101"));
        String[] rightCase1 = {"10000" ,  "10001" ,"100", "10010","1001","10011","10","10100","1010","10101","10110","1011","10111"};

    it = tree.inorderTraversal();
    NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
        String next = it.next();
        assertEquals("Mismatch between expected value and output value value at string " + next
                + ". ", new String(rightCase1[i]), next);

    }
    assertFalse("String can't be deleted as it no longer exists in trie" ,tree.delete("101"));

    //Case2: Has a left child only
        tree.insert("101");
        tree.delete("1010");

    assertTrue("String should be deleted successfully" ,tree.delete("101"));
    String[] rightCase2 =
        {"10000" ,  "10001" , "100","10010","1001","10011","10","10100","10101","10110","1011","10111"};
    it = tree.inorderTraversal();
    NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
        String next = it.next();
        assertEquals("Mismatch between expected value and output value value at string " + next
                + ". ", new String(rightCase2[i]), next);

    }
    assertFalse("String can't be deleted as it no longer exists in trie" ,tree.delete("101"));


    //Case3: Has right child only
        tree.insert("101");
        tree.insert("1010");
        tree.delete("1011");
    assertTrue("String should be deleted successfully" ,tree.delete("101"));
    String[] rightCase3 = {"10000" , "10001" , "100","10010","1001","10011","10","10100","1010","10101","10110","10111"};
    it = tree.inorderTraversal();
    NUMS = tree.getSize();
        for(int i = 0; i <NUMS ; i++){
        String next = it.next();
        assertEquals("Mismatch between expected value and output value value at string " + next
                + ". ", new String(rightCase3[i]), next);

    }

    assertFalse("String can't be deleted as it no longer exists in trie" ,tree.delete("101"));
}


}

