package cs.technion.ac.il.sd.library;


import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by antonb on 4/8/16.
 */
public class GraphUtils {


    /**
     * Performs topological sort on DirectedGraph
     *
     * @param graph - graph on which topological sort will be applied
     * @return List of vertices representing the topological sort
     * or empty Optional if graph is not acyclic or topological sort could not be performed
     */
    public static <V,E> Optional<List<V>> toposortGraph(DirectedGraph<V,E> graph)  {

        CycleDetector<V,E> cycleDetector = new CycleDetector<>(graph);
        if (graph == null || cycleDetector.detectCycles()) {
            return Optional.empty();
        }
        List<V> toposort = new LinkedList<>();
        TopologicalOrderIterator<V,E> it = new TopologicalOrderIterator<>(graph);
        it.forEachRemaining(toposort::add);
        return Optional.of(toposort);

    }

    public static void main(String[] args) {
        DirectedGraph<Integer, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        graph.addVertex(5);
        graph.addVertex(7);
        graph.addVertex(3);
        graph.addVertex(11);
        graph.addVertex(8);
        graph.addVertex(2);
        graph.addVertex(9);
        graph.addVertex(10);
        graph.addEdge(5,11);
        graph.addEdge(11,2);
        graph.addEdge(11,9);
        graph.addEdge(11,10);
        graph.addEdge(7,11);
        graph.addEdge(7,8);
        graph.addEdge(8,9);
        graph.addEdge(3,8);
        graph.addEdge(3,10);

        //adds cycle
//        graph.addEdge(3,2);
//        graph.addEdge(2,5);
//        graph.addEdge(5,3);
        Optional<List<Integer>> t = GraphUtils.toposortGraph(graph);
        t.ifPresent(l -> l.stream().forEach(System.out::println));

    }
}
