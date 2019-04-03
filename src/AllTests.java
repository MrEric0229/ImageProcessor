
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class AllTests {

    // general functionality
    static final int totalPoints = 500; //not including efficiency, which will be added later

    static int p = 9001;
    static int partial = 0;
    static int totalEarned = 0;
    static int totalLost = 0;
    static StringBuilder builder = new StringBuilder();

    // timing data
    static final int SEED = 23;
    static final int TEST_NUM = 5;
    static final int TEST_SIZE = 500_000;
    static final String OUTPUTFILE = "temp.junk";

    @Rule
    public Timeout timeout = Timeout.seconds(500);

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {

            if (p > totalPoints) {
                System.out.println("ERROR: POINT VALUES NOT RESET!"); // this ensures that we don't miss resetting point values
            }

            totalEarned += p;
            p = 9000;
        }

        @Override
        protected void failed(Throwable e, Description description) {

            if (p > totalPoints) {
                System.out.println("ERROR: POINT VALUES NOT RESET!"); // this ensures that we don't miss resetting point values
            }

            if (partial > 0) {
                builder.append("test " + description + " partially failed with exception " + e + " (-" + (p - partial) + ");\n");

                totalLost += p - partial;
                totalEarned += partial;
                partial = 0;
            } else {
                builder.append("test " + description + " failed with exception " + e + " (-" + p + "); \n");
                totalLost += p;
            }

            p = 9000;
        }
    };

    @AfterClass
    public static void printResults() {
        if (totalEarned + totalLost != totalPoints) {
            System.out.println("\n\nERROR!  Earned " + totalEarned + " but lost " + totalLost + "; should add to " + totalPoints);
            System.out.println("missing " + (totalPoints - totalEarned - totalLost));
        }


        System.out.println("\n================================================================");
        System.out.println(" comments below ");
        System.out.println("================================================================");
        System.out.println("[total points: " + Math.min(totalEarned, totalPoints) + "/" + totalPoints + "]");
        System.out.println("");
        System.out.println("[deductions:");
        System.out.println(builder.toString());
        System.out.println("]");
        System.out.println("================================================================");

    }

    // ================================================================
    // SETUP
    // ================================================================
    @BeforeClass
    public static void setUp() throws Exception {

    }

    public static boolean strListEquals(List<String> a, List<String> b) {
        if (a.size() != b.size()) return false;
        for (int i = 0; i < a.size(); i++) {
            if (!(a.get(i).equals(b.get(i)))) return false;
        }
        return true;
    }

    boolean setEquality(ArrayList<String> a, ArrayList<String> b) {
        return a.containsAll(b) && b.containsAll(a);
    }

    // ================================================================
    // Part 1 tests.
    // ================================================================

    @Test
    public void v2vSmallTest1() throws Exception {
        p = 20;

        String input = "resources/graph-small.txt";
        int[] answerarray =
                {
                        1,1,
                        1,2,
                        1,3,
                        1,4,
                        1,5,
                        1,6,
                        1,7,
                        1,8,
                        1,9,
                        1,0
                };

        WGraph g = new WGraph(input);
        ArrayList<Integer> result = g.V2V(1, 1, 1, 0);
        ArrayList<Integer> answer = intArrToIntList(answerarray);

        Assert.assertTrue("Incorrect result for V2V on small graph.", areListsEqual(result, answer));
    }

    @Test
    public void v2vSmallTest2() throws Exception {
        p = 20;

        String input = "resources/graph-small.txt";
        int[] answerarray =
                {
                };

        WGraph g = new WGraph(input);
        ArrayList<Integer> result = g.V2V(0, 0, 9, 2);
        ArrayList<Integer> answer = intArrToIntList(answerarray);

        Assert.assertTrue("Incorrect result for V2V on small graph with no path.", areListsEqual(result, answer));
    }

    @Test
    public void v2sSmallTest1() throws Exception {
        p = 20;

        String input = "resources/graph-small.txt";
        int[] answerarray =
                {
                        8,8,
                        9,9,
                        0,0,
                        0,1
                };
        int[] set =
                {
                        8,9,
                        0,1,
                        5,7
                };

        WGraph g = new WGraph(input);
        g.V2V(1, 1, 1, 0);
        ArrayList<Integer> result = g.V2S(8, 8, intArrToIntList(set));
        ArrayList<Integer> answer = intArrToIntList(answerarray);

        Assert.assertTrue("Incorrect result for V2S on small graph.", areListsEqual(result, answer));
    }

    @Test
    public void v2sSmallTest2() throws Exception {
        p = 20;

        String input = "resources/graph-small.txt";
        int[] answerarray =
                {
                };
        int[] set =
                {
                        8,9,
                        0,1,
                        5,7
                };

        WGraph g = new WGraph(input);
        g.V2V(1, 1, 1, 0);
        ArrayList<Integer> result = g.V2S(9,0, intArrToIntList(set));
        ArrayList<Integer> answer = intArrToIntList(answerarray);

        Assert.assertTrue("Incorrect result for V2S on small graph with no path.", areListsEqual(result, answer));
    }

    @Test
    public void v2sSmallTest3() throws Exception {

        p = 10;

        String input = "resources/graph-small.txt";
        int[] answerarray =
                {
                        8,8
                };
        int[] set =
                {
                        8,9,
                        0,1,
                        5,7,
                        8,8
                };

        WGraph g = new WGraph(input);
        g.V2V(1, 1, 1, 0);
        ArrayList<Integer> result = g.V2S(8, 8, intArrToIntList(set));
        ArrayList<Integer> answer = intArrToIntList(answerarray);

        Assert.assertTrue("Incorrect result for V2S on small graph when source is in target set.", areListsEqual(result, answer));
    }

    @Test
    public void s2sSmallTest1() throws Exception {
        p = 20;

        String input = "resources/graph-small.txt";
        int[] answerarray =
                {
                        0,0,
                        0,1,
                        0,2,
                        0,3,
                        0,4,
                        0,5,
                        0,6,
                        0,7,
                        0,8,
                        0,9
                };
        int[] set1 =
                {
                        9,0,
                        9,2,
                        0,0,
                        5,0
                };
        int[] set2 =
                {
                        0,9,
                        5,9,
                        9,9
                };

        WGraph g = new WGraph(input);
        g.V2V(1, 1, 1, 0);
        g.V2S(8, 8, intArrToIntList(new int[] { 8,9, 0,1, 5,7 }));
        ArrayList<Integer> result = g.S2S(intArrToIntList(set1), intArrToIntList(set2));
        ArrayList<Integer> answer = intArrToIntList(answerarray);

        Assert.assertTrue("Incorrect result for S2S on small graph.", areListsEqual(result, answer));
    }

    @Test
    public void s2sSmallTest2() throws Exception {
        p = 20;

        String input = "resources/graph-small.txt";
        int[] answerarray =
                {
                };
        int[] set1 =
                {
                        9,0,
                        9,2
                };
        int[] set2 =
                {
                        0,9,
                        5,9,
                        9,9
                };

        WGraph g = new WGraph(input);
        g.V2V(1, 1, 1, 0);
        g.V2S(8, 8, intArrToIntList(new int[] { 8,9, 0,1, 5,7 }));
        ArrayList<Integer> result = g.S2S(intArrToIntList(set1), intArrToIntList(set2));
        ArrayList<Integer> answer = intArrToIntList(answerarray);

        Assert.assertTrue("Incorrect result for S2S on small graph with no path.", areListsEqual(result, answer));
    }

    @Test
    public void s2sSmallTest3() throws Exception {
        p = 10;

        String input = "resources/graph-small.txt";
        int[] answerarray =
                {
                        5,5
                };
        int[] set1 =
                {
                        9,0,
                        9,2,
                        0,0,
                        5,0,
                        5,5
                };
        int[] set2 =
                {
                        0,9,
                        5,9,
                        9,9,
                        5,5
                };

        WGraph g = new WGraph(input);
        g.V2V(1, 1, 1, 0);
        g.V2S(8, 8, intArrToIntList(new int[] { 8,9, 0,1, 5,7 }));
        ArrayList<Integer> result = g.S2S(intArrToIntList(set1), intArrToIntList(set2));
        ArrayList<Integer> answer = intArrToIntList(answerarray);

        Assert.assertTrue("Incorrect result for V2S on small graph when the same node is in both sets.", areListsEqual(result, answer));
    }

    @Test
    public void s2sLargeTest() throws Exception {
        p = 100;

        String input = "resources/graph-mid.txt";

        int[] answerarray =
                {
                        1,1,
                        5,5,
                        200,200,
                        900,900,
                        1234,1234,
                        5555,5555,
                        7,7,
                        5001,5001,
                };

        int[] set1 =
                {
                        1,1,
                        2,2,
                        3,3,
                        4,4,
                        //5,5,
                        6,6,
                        //7,7,
                        8,8,
                        9,9,
                        10,10,
                        11,11,
                        12,12,
                        13,13,
                        14,14,
                        15,15,
                        16,16,
                        17,17,
                        18,18,
                        19,19,
                        20,20
                };
        int[] set2 =
                {
                        5000,5000,
                        5001,5001,
                        5002,5002,
                        5003,5003,
                        5004,5004,
                        5005,5005,
                        5006,5006,
                        5007,5007,
                        5008,5008,
                        5009,5009,
                        5010,5010,
                        5011,5011,
                        5012,5012,
                        5013,5013,
                        5014,5014,
                        5015,5015,
                        5016,5016,
                        5017,5017,
                        5018,5018,
                        5019,5019,
                        5020,5020
                };

        WGraph g = new WGraph(input);
        ArrayList<Integer> result = g.S2S(intArrToIntList(set1), intArrToIntList(set2));
        ArrayList<Integer> answer = intArrToIntList(answerarray);

        for (int i=0; i<result.size(); i++) {
        	System.out.print(result.get(i) + " ");
        }
        
        Assert.assertTrue("Incorrect result for S2S on larger graph.", areListsEqual(result, answer));
    }

    // ================================================================
    // Part 2 tests.
    // ================================================================

    @Test
    public void smallImageTest1() throws Exception {
        p = 30;

        String input = "resources/image1.txt";

        ImageProcessor ip = new ImageProcessor(input);
        ArrayList<ArrayList<Integer>> result = ip.getImportance();

        ArrayList<ArrayList<Integer>> answer = readImportanceFromFile("resources/image1importance.txt");

        Assert.assertTrue("Incorrect importance for small image.", areMatricesEqual(answer, result));
    }

    @Test
    public void smallImageTest2() throws Exception {
        p = 30;

        String input = "resources/image1.txt";

        ImageProcessor ip = new ImageProcessor(input);
        ip.writeReduced(1, OUTPUTFILE);

        ArrayList<ArrayList<Pixel>> answer = readImageFromFile("resources/image1reduced1.txt");
        ArrayList<ArrayList<Pixel>> result = readImageFromFile(OUTPUTFILE);

        Assert.assertTrue("Incorrect result reducing small image by 1..", areMatricesEqual(answer, result));
    }

    @Test
    public void smallImageTest3() throws Exception {

        p = 30;

        String input = "resources/image1.txt";

        ImageProcessor ip = new ImageProcessor(input);
        ArrayList<ArrayList<Integer>> result1 = ip.getImportance();
        ip.writeReduced(1, OUTPUTFILE);
        ArrayList<ArrayList<Integer>> result2 = ip.getImportance();

        Assert.assertTrue("Inconsistent result for importance by 1 of small image.", areMatricesEqual(result1, result2));
    }

    @Test
    public void smallImageTest4() throws Exception {
        p = 30;

        String input = "resources/image1.txt";

        ImageProcessor ip = new ImageProcessor(input);
        ip.writeReduced(1, OUTPUTFILE);
        ArrayList<ArrayList<Pixel>> result1 = readImageFromFile(OUTPUTFILE);

        deleteTempFile();

        ip.writeReduced(1, OUTPUTFILE);
        ArrayList<ArrayList<Pixel>> result2 = readImageFromFile(OUTPUTFILE);

        Assert.assertTrue("Inconsistent result for reduction by 1 of small image.", areMatricesEqual(result1, result2));
    }

    @Test
    public void smallImageTest5() throws Exception {
        p = 30;

        String input = "resources/image1.txt";

        ImageProcessor ip = new ImageProcessor(input);
        ip.writeReduced(5, OUTPUTFILE);

        ArrayList<ArrayList<Pixel>> answer = readImageFromFile("resources/image1reduced5.txt");
        ArrayList<ArrayList<Pixel>> result = readImageFromFile(OUTPUTFILE);
        
        Assert.assertTrue("Incorrect result for reduction by 5 of small image.", areMatricesEqual(answer, result));
    }

    @Test
    public void largeImageTest() throws Exception {
        p = 110;

        String input = "resources/image4.txt";

        ImageProcessor ip = new ImageProcessor(input);
        ip.writeReduced(17, OUTPUTFILE);

        ArrayList<ArrayList<Pixel>> answer = readImageFromFile("resources/image4reduced17.txt");
        ArrayList<ArrayList<Pixel>> result = readImageFromFile(OUTPUTFILE);

        Assert.assertTrue("Incorrect result for reduction by 17 of large image.", areMatricesEqual(answer, result));
    }

    ///////////////////////
    ///////////////////////
    //                   //
    ///////////////////////
    ///////////////////////

    //////////////
    // Helpers. //
    //////////////

    @Before
    @After
    public void deleteTempFile() {
        try {
            java.nio.file.Files.deleteIfExists(Paths.get(OUTPUTFILE));
        } catch (Exception ex) {}
    }

    private static ArrayList<Integer> intArrToIntList(int[] arr) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int x :
             arr) {
            list.add(x);
        }
        return list;
    }

    private static <T> boolean areListsEqual(ArrayList<T> l1, ArrayList<T> l2) {
        if (l1 == null || l2 == null) return false;
        if (l1.size() != l2.size()) return false;
        for (int i = l1.size(); i<0; i++)
            if (!l1.get(i).equals(l2.get(i))) return false;
        return true;
    }

    private static <T> boolean areMatricesEqual(ArrayList<ArrayList<T>> m1, ArrayList<ArrayList<T>> m2) {
        if (m1 == null || m2 == null || m1.size() != m2.size())
            return false;
        for (int i = 0; i < m1.size(); i++) {
            ArrayList<T> l1 = m1.get(i);
            ArrayList<T> l2 = m2.get(i);
            if (l1.size() != l2.size())
                return false;
            for (int j = 0; j < l1.size(); j++) {
                if (!l1.get(j).equals(l2.get(j)))
                    return false;
            }
        }
        return true;
    }

    private static ArrayList<ArrayList<Integer>> readImportanceFromFile(String file) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(file));
            for (int i = (lines.size() - 1); i >= 0; i--)
                if (lines.get(i).trim().length() <= 0)
                    lines.remove(i--);
            List<String[]> strmat = new ArrayList<>();
            for (String line : lines)
                strmat.add(line.split("\\s+"));
            ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
            for (String[] row : strmat) {
                ArrayList<Integer> irow = new ArrayList<Integer>();
                for (String num : row)
                    irow.add(Integer.parseInt(num));
                matrix.add(irow);
            }
            return matrix;
        } catch (IOException e) {
            System.out.println("An exception occurred reading a test file. This should be investigated by the person running this program!");
        }
        return null;
    }

    private static ArrayList<ArrayList<Pixel>> readImageFromFile(String file) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(file));
            for (int i = (lines.size() - 1); i >= 0; i--)
                if (lines.get(i).trim().length() <= 0)
                    lines.remove(i--);
            List<String[]> strmat = new ArrayList<>();
            int height = Integer.parseInt(lines.get(0));
            int width = Integer.parseInt(lines.get(1));
            lines.remove(1);
            lines.remove(0);
            lines = lines.subList(0, height);
            for (String line : lines)
                strmat.add(line.split("\\s+"));
            ArrayList<ArrayList<Pixel>> matrix = new ArrayList<>();
            for (String[] row : strmat) {
                ArrayList<Pixel> prow = new ArrayList<Pixel>();
                for(int i = 0; i < row.length; i+= 3)
                    prow.add(new Pixel(row[i], row[i+1], row[i+2]));
                matrix.add(prow);
                Assert.assertTrue(prow.size() == width);
            }
            return matrix;
        } catch (IOException e) {
            System.out.println("An exception occurred reading an image from file");
        }
        return null;
    }

    private static void writeImportanceToFile(String file, ArrayList<ArrayList<Integer>> imp) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        for (ArrayList<Integer> r : imp) {
            String line = "";
            for (int i : r) {
                line += i + " ";
            }
            line = line.substring(0, line.length() - 1);
            lines.add(line);
        }
        Files.write(Paths.get(file), lines);
    }

    private static Map<Pair<Pair<String, String>, Pair<String, String>>, Integer> getWeightMap(String path) throws Exception {
        List<String> lines = java.nio.file.Files.readAllLines(Paths.get(path));
        Map<Pair<Pair<String, String>, Pair<String, String>>, Integer> map = new HashMap<Pair<Pair<String, String>, Pair<String, String>>, Integer>();
        for (int i = 2; i < lines.size(); i++) {
            String[] line = lines.get(i).split("\\s+");
            map.put(new Pair(new Pair(line[0], line[1]), new Pair(line[2], line[3])), Integer.parseInt(line[4]));
        }
        return map;
    }

    private static double jaccardSimilarity(Collection x, Collection y) {
        Set a = new HashSet(x);
        Set b = new HashSet(y);
        Set c = new HashSet();
        c.addAll(a);
        c.addAll(b);
        double unionSize = c.size();
        c.retainAll(a);
        c.retainAll(b);
        double intersectionSize = c.size();
        return intersectionSize / unionSize;
    }

    private static class Pair<A, B> {
        private A a;
        private B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(a, pair.a) &&
                    Objects.equals(b, pair.b);
        }

        @Override
        public int hashCode() {

            return Objects.hash(a, b);
        }
    }

    public static class Pixel {
        public int r, g, b;

        public Pixel(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public Pixel(String r, String g, String b) {
            this.r = Integer.parseInt(r);
            this.g = Integer.parseInt(g);
            this.b = Integer.parseInt(b);
        }

        @Override
        public String toString() {
            return Integer.toString(r) + " " + Integer.toString(g) + " " + Integer.toString(b);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof  Pixel)) return false;

            Pixel other = (Pixel) obj;
            return this.r == other.r && this.g == other.g && this.b == other.b;
        }
    }

}









