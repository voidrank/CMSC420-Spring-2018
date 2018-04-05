package projects.bpt;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

//@SuppressWarnings("unused")

public class PhongTests {
	@Test public void test1() {
	  BinaryPatriciaTrie tree = new BinaryPatriciaTrie();
	  
	  assertTrue(tree.isEmpty());
	  assertTrue(tree.getSize() == 0);
	  
	  assertFalse(tree.search("0101"));
	  assertTrue(tree.insert("0101"));
	  assertFalse(tree.insert("0101"));
	  
	}
	
	@Test public void test2() {
	  BinaryPatriciaTrie tree = new BinaryPatriciaTrie();
	  
	  assertTrue(tree.insert("00000"));
	  assertTrue(tree.insert("00011"));
	  assertFalse(tree.search("000"));
	  assertTrue(tree.insert("000"));
	  assertTrue(tree.search("000"));
	  assertTrue(tree.insert("11011"));
	  assertTrue(tree.insert("1"));
	  
	  Iterator<String> it = tree.inorderTraversal();
	  
	  while (it.hasNext()) {
		System.out.println(it.next());  
	  }
	  
	  String s = tree.getLongest();
	  System.out.println("LONGEST STRING = " + s);
	}
	
	@Test public void test3() {
	  BinaryPatriciaTrie tree = new BinaryPatriciaTrie();
	  
	  assertTrue(tree.insert("00000"));
	  assertTrue(tree.delete("00000"));
	  
	  assertEquals(tree.getSize(), 0);
	}
	
	@Test public void test4() {
	  System.out.println("--------------TEST4--------------");
	  BinaryPatriciaTrie t = new BinaryPatriciaTrie();
	  
	  assertTrue(t.insert("100"));
	  assertTrue(t.insert("101"));
	  assertTrue(t.insert("0"));
	  assertTrue(t.insert("10111"));
	  assertTrue(t.insert("1011"));
	  assertTrue(t.insert("1010"));
	  assertTrue(t.insert("1"));
	  assertFalse(t.insert("1"));
	  assertTrue(t.insert("111"));
	  assertTrue(t.insert("11"));
	  assertTrue(t.insert("110"));
	  assertTrue(t.insert("10110"));
	  
	  Iterator<String> it = t.inorderTraversal();
	  
	  while (it.hasNext()) {
		System.out.println(it.next());
	  }
	}
	
	@Test public void test5() {
	  // System.out.println("----------------------------");
	  BinaryPatriciaTrie t = new BinaryPatriciaTrie();
		  
	  assertTrue(t.insert("100"));
	  assertTrue(t.insert("101"));
	  // assertTrue(t.insert("0"));
	  // assertTrue(t.insert("10111"));
	  // assertTrue(t.insert("1011"));
	  // assertTrue(t.insert("1010"));
	  // assertTrue(t.insert("1"));	

	  // delete process
	  assertTrue(t.delete("100"));
	  assertFalse(t.search("100"));
	  assertTrue(t.delete("101"));
	  assertFalse(t.search("101"));
	  assertEquals(t.getSize(), 0);
	  
	  Iterator<String> it = t.inorderTraversal();
		  
	  while (it.hasNext()) {
		System.out.println(it.next());
	  }
	}
	
	@Test public void test6() {
	  System.out.println("----------------------------");
	  BinaryPatriciaTrie t = new BinaryPatriciaTrie();
	  
	  assertTrue(t.insert("00001"));
	  // assertTrue(t.insert("010"));
	  assertTrue(t.insert("010010"));
	  assertTrue(t.insert("010111"));
	  assertTrue(t.insert("011011"));
	  assertTrue(t.insert("011101"));
	  assertTrue(t.insert("01111"));
	  assertTrue(t.insert("10010"));
	  assertTrue(t.insert("10110"));
	  assertTrue(t.insert("11001"));

	  Iterator<String> it = t.inorderTraversal();
	  
	  while (it.hasNext()) {
		System.out.println(it.next());
	  }
	}
	
    @Test public void test7() {
	  System.out.println("--------------TEST7--------------");
	  BinaryPatriciaTrie t = new BinaryPatriciaTrie();
			  
	  assertTrue(t.insert("100"));
	  assertTrue(t.insert("101"));
	  assertTrue(t.insert("0"));
	  assertTrue(t.insert("10111"));
	  assertTrue(t.insert("1011"));
	  assertTrue(t.insert("1010"));
	  assertTrue(t.insert("1"));
	  assertTrue(t.insert("111"));
	  assertTrue(t.insert("11"));
	  assertTrue(t.insert("110"));
	  assertTrue(t.insert("10110"));

	  Iterator<String> it = t.inorderTraversal();
			  
	  while (it.hasNext()) {
	    System.out.println(it.next());
	  }
	  
	  System.out.println("-----------------");

	  assertTrue(t.delete("101"));
	  assertFalse(t.search("101"));
	  assertFalse(t.insert("100"));
	  assertFalse(t.search("10"));
	  
	  it = t.inorderTraversal();
	  
	  while (it.hasNext()) {
		System.out.println(it.next());
	  }
	}
}
