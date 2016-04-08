import cs.technion.ac.il.sd.library.GraphUtils;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.*;

/**
 * Test file for {@link GraphUtils#toposort(DirectedGraph)}
 */
public class GraphToposortTest {

    private static final boolean PRINT_ON_FAIL = true;

    private static final DirectedGraph<Integer, DefaultEdge> smallGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    private static final DirectedGraph<Integer, DefaultEdge> cyclicGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    private static final DirectedGraph<Integer, DefaultEdge> emptyGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    private static final DirectedGraph<Integer, DefaultEdge> complexGraph = new DefaultDirectedGraph<>(DefaultEdge.class);

    private <V, E> Optional<List<V>> toposort(DirectedGraph<V, E> graph) {
        return GraphUtils.toposort(graph);
    }

    @BeforeClass
    public static  void setupSmall() {
        smallGraph.addVertex(1);
        smallGraph.addVertex(2);
        smallGraph.addVertex(3);
        smallGraph.addVertex(4);
        smallGraph.addEdge(1, 2);
        smallGraph.addEdge(1, 3);
        smallGraph.addEdge(3, 4);
        System.out.println("@setupSmall");
    }

    @BeforeClass
    public static  void setupCyclic() {
        cyclicGraph.addVertex(1);
        cyclicGraph.addVertex(2);
        cyclicGraph.addVertex(3);
        cyclicGraph.addVertex(4);
        cyclicGraph.addEdge(1, 2);
        cyclicGraph.addEdge(1, 3);
        cyclicGraph.addEdge(3, 4);
        cyclicGraph.addEdge(4, 1);
        System.out.println("@setupCyclic");
    }

    @BeforeClass
    public static void setupComplex() {
        complexGraph.addVertex(5);
        complexGraph.addVertex(7);
        complexGraph.addVertex(3);
        complexGraph.addVertex(11);
        complexGraph.addVertex(8);
        complexGraph.addVertex(2);
        complexGraph.addVertex(9);
        complexGraph.addVertex(10);
        complexGraph.addEdge(5, 11);
        complexGraph.addEdge(11, 2);
        complexGraph.addEdge(11, 9);
        complexGraph.addEdge(11, 10);
        complexGraph.addEdge(7, 11);
        complexGraph.addEdge(7, 8);
        complexGraph.addEdge(8, 9);
        complexGraph.addEdge(3, 8);
        complexGraph.addEdge(3, 10);
        System.out.println("@setupComplex");
    }

    public static <V, E> boolean toposortInvariant(DirectedGraph<V, E> graph, List<V> toposort) {

        for (E edge : graph.edgeSet()) {

            V source = graph.getEdgeSource(edge);
            V target = graph.getEdgeTarget(edge);

            if (toposort.indexOf(source) > toposort.indexOf(target)) {
                if (PRINT_ON_FAIL) {
                    System.out.println("toposort indexOf " + source + " = " + toposort.indexOf(source));
                    System.out.println("toposort indexOf " + target + " = " + toposort.indexOf(target));
                    System.out.println("graph = " + graph);
                    System.out.println("toposort = " + toposort);
                }
                return false;
            }
        }
        return true;
    }

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);


    @Test
    public void smallGraphReturnsValidSort() {
        List<Integer> topoSort = toposort(smallGraph).get();

        boolean sortValid = topoSort.equals(new ArrayList<>(Arrays.asList(1, 2, 3, 4)))
                || topoSort.equals(new ArrayList<>(Arrays.asList(1, 3, 2, 4)));

        Assert.assertTrue(toposortInvariant(smallGraph, topoSort));
        Assert.assertTrue(sortValid);
    }

    @Test
    public void emptyGraphReturnsEmptySort() {
        List<Integer> topoSort = toposort(emptyGraph).get();
        Assert.assertEquals(new LinkedList<Integer>(), topoSort);
    }

    @Test
    public void cyclicGraphReturnsNoToposort() {
        Assert.assertEquals(Optional.empty(), toposort(cyclicGraph));
    }

    @Test
    public void complexGraphPreservesInvariant() {
        List<Integer> topoSort = toposort(complexGraph).get();
        Assert.assertTrue(toposortInvariant(complexGraph, topoSort));
    }

}
