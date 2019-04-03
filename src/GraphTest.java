import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class GraphTest {
	static long vtovaverage;
	static long stovaverage;
	static long stosaverage;

	@Test
	void testV2V1() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		solution.add(1);
		solution.add(2);
		solution.add(7);
		solution.add(8);
		solution.add(2);
		solution.add(2);
		solution.add(3);
		solution.add(4);
		solution.add(5);
		solution.add(6);
		assertEquals(solution, wg.V2V(1, 2, 5, 6));
		long finalTime = System.nanoTime() - start;
		vtovaverage += finalTime;
	}
	
	@Test
	void testV2V2() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		solution.add(1);
		solution.add(2);
		solution.add(7);
		solution.add(8);
		solution.add(2);
		solution.add(2);
		solution.add(3);
		solution.add(4);
		assertEquals(solution, wg.V2V(1, 2, 3, 4));
		long finalTime = System.nanoTime() - start;
		vtovaverage += finalTime;
	}
	
	@Test
	void testV2V3() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		solution.add(1);
		solution.add(2);
		solution.add(7);
		solution.add(8);
		solution.add(2);
		solution.add(2);
		solution.add(3);
		solution.add(4);
		solution.add(5);
		solution.add(6);
		solution.add(4);
		solution.add(4);
		assertEquals(solution, wg.V2V(1, 2, 4, 4));
		long finalTime = System.nanoTime() - start;
		vtovaverage += finalTime;
	}
	
	/**
	 * Invalid vertex
	 * @throws IOException
	 */
	@Test
	void testV2V4() throws IOException {	
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		assertEquals(solution, wg.V2V(9, 2, 3, 4));
		long finalTime = System.nanoTime() - start;
		vtovaverage += finalTime;
	}
	
	@Test
	void testV2V5() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		solution.add(2);
		solution.add(2);
		solution.add(3);
		solution.add(4);
		assertEquals(solution, wg.V2V(2, 2, 3, 4));
		long finalTime = System.nanoTime() - start;
		vtovaverage += finalTime;
	}
	
	/**
	 * 1,2 back to itself
	 * @throws IOException
	 */
	@Test
	void testV2V6() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		solution.add(1);
		solution.add(2);
		assertEquals(solution, wg.V2V(1, 2, 1, 2));
		long finalTime = System.nanoTime() - start;
		vtovaverage += finalTime;
	}
	
	
	

	@Test
	void testS2V1() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s = new ArrayList<Integer>();
		s.add(4);
		s.add(4);
		s.add(5);
		s.add(6);
		s.add(3);
		s.add(4);
		solution.add(1);
		solution.add(2);
		solution.add(7);
		solution.add(8);
		solution.add(2);
		solution.add(2);
		solution.add(3);
		solution.add(4);
		assertEquals(solution, wg.V2S(1, 2, s));
		long finalTime = System.nanoTime() - start;
		stovaverage += finalTime;
	}
	
	@Test
	void testS2V2() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s = new ArrayList<Integer>();
		s.add(4);
		s.add(4);
		s.add(5);
		s.add(6);
		s.add(3);
		s.add(4);
		assertEquals(solution, wg.V2S(1, 5, s));
		long finalTime = System.nanoTime() - start;
		stovaverage += finalTime;
	}
	
	@Test
	void testS2V3() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s = new ArrayList<Integer>();
		s.add(1);
		s.add(2);
		solution.add(1);
		solution.add(2);
		assertEquals(solution, wg.V2S(1, 2, s));
		long finalTime = System.nanoTime() - start;
		stovaverage += finalTime;
	}
	
	@Test
	void testS2V4() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s = new ArrayList<Integer>();
		s.add(4);
		s.add(4);
		solution.add(1);
		solution.add(2);
		solution.add(7);
		solution.add(8);
		solution.add(2);
		solution.add(2);
		solution.add(3);
		solution.add(4);
		solution.add(5);
		solution.add(6);
		solution.add(4);
		solution.add(4);
		assertEquals(solution, wg.V2S(1, 2, s));
		long finalTime = System.nanoTime() - start;
		stovaverage += finalTime;
	}
	
	@Test
	void testS2V5() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s = new ArrayList<Integer>();
		s.add(1);
		s.add(2);
		s.add(5);
		s.add(6);
		s.add(4);
		s.add(4);
		solution.add(7);
		solution.add(8);
		solution.add(2);
		solution.add(2);
		solution.add(3);
		solution.add(4);
		solution.add(5);
		solution.add(6);
		assertEquals(solution, wg.V2S(7, 8, s));
		long finalTime = System.nanoTime() - start;
		stovaverage += finalTime;
	}
	
	@Test
	void testS2S1() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s1 = new ArrayList<Integer>();
		s1.add(1);
		s1.add(2);
		s1.add(2);
		s1.add(2);
		ArrayList<Integer> s2 = new ArrayList<Integer>();
		s2.add(5);
		s2.add(6);
		s2.add(4);
		s2.add(4);
		solution.add(2);
		solution.add(2);
		solution.add(3);
		solution.add(4);
		solution.add(5);
		solution.add(6);
		assertEquals(solution, wg.S2S(s1, s2));
		long finalTime = System.nanoTime() - start;
		stosaverage += finalTime;
	}
	
	@Test
	void testS2S2() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s1 = new ArrayList<Integer>();
		s1.add(4);
		s1.add(4);
		ArrayList<Integer> s2 = new ArrayList<Integer>();
		s2.add(5);
		s2.add(6);
		s2.add(3);
		s2.add(4);
		s2.add(4);
		s2.add(4);
		solution.add(4);
		solution.add(4);
		assertEquals(solution, wg.S2S(s1, s2));
		long finalTime = System.nanoTime() - start;
		stosaverage += finalTime;
	}
	
	@Test
	void testS2S3() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s1 = new ArrayList<Integer>();
		s1.add(3);
		s1.add(4);
		ArrayList<Integer> s2 = new ArrayList<Integer>();
		s2.add(5);
		s2.add(6);
		solution.add(3);
		solution.add(4);
		solution.add(5);
		solution.add(6);
		assertEquals(solution, wg.S2S(s1, s2));
		long finalTime = System.nanoTime() - start;
		stosaverage += finalTime;
	}
	
	@Test
	void testS2S4() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s1 = new ArrayList<Integer>();
		s1.add(3);
		s1.add(4);
		s1.add(1);
		s1.add(2);
		s1.add(7);
		s1.add(8);
		ArrayList<Integer> s2 = new ArrayList<Integer>();
		s2.add(5);
		s2.add(6);
		s2.add(4);
		s2.add(4);
		s2.add(2);
		s2.add(2);
		solution.add(7);
		solution.add(8);
		solution.add(2);
		solution.add(2);
		assertEquals(solution, wg.S2S(s1, s2));
		long finalTime = System.nanoTime() - start;
		stosaverage += finalTime;
	}

	@Test
	void testS2S5() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s1 = new ArrayList<Integer>();
		s1.add(4);
		s1.add(4);
		ArrayList<Integer> s2 = new ArrayList<Integer>();
		s2.add(4);
		s2.add(4);
		solution.add(4);
		solution.add(4);
		assertEquals(solution, wg.S2S(s1, s2));
		long finalTime = System.nanoTime() - start;
		stosaverage += finalTime;
	}
	
	@Test
	void testS2S6() throws IOException {
		long start = System.nanoTime();
		WGraph wg = new WGraph("example.txt");
		ArrayList<Integer> solution = new ArrayList<Integer>();
		ArrayList<Integer> s1 = new ArrayList<Integer>();
		s1.add(4);
		s1.add(4);
		ArrayList<Integer> s2 = new ArrayList<Integer>();
		s2.add(5);
		s2.add(6);
		s2.add(3);
		s2.add(4);
		assertEquals(solution, wg.S2S(s1, s2));
		long finalTime = System.nanoTime() - start;
		stosaverage += finalTime;
	}
	
	@AfterAll
	static void getAverage() {
		System.out.println("V2V average runtime was: " + vtovaverage/6 + " ns");
		System.out.println("S2V average runtime was: " + stovaverage/5 + " ns");
		System.out.println("S2S average runtime was: " + stosaverage/6 + " ns");
	}
}
