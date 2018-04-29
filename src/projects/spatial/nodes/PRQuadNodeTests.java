package projects.spatial.nodes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import projects.spatial.knnutils.BoundedPriorityQueue;

import java.util.Random;

/**
 * <p>Some tests for {@link BoundedPriorityQueue}s.</p>
 * @author <a href = "mailto:jasonfil@cs.umd.edu">Jason Filippou</a>
 */
public class PRQuadNodeTests {

	private Random r;
	private static final long DEFAULT_SEED = 47;
	private static final int DEFAULT_BUCKETING = 1;
	private static final int SCALE = 100;

	@Before
	public void setUp(){
		r = new Random(DEFAULT_SEED);
	}

	@After
	public void tearDown()  {
		r = null;
		System.gc();
	}

	@Test
	public void testEmptyAndStubPRQuadNode(){

	}

	@Test
	public void testOneInsertion(){

	}

	@Test
	public void testOneDeletion(){

	}

	@Test
	public void testFewInsertions(){

	}

	@Test
	public void testFewDeletions(){

	}

	@Test
	public void testManyInsertions(){

	}

	@Test
	public void testManyDeletions(){

	}

}
