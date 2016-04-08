package cs.technion.ac.il.sd.library;


import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.traverse.TopologicalOrderIterator;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * GraphUtils library
 */
public class GraphUtils {

    /**
     * Performs topological sort on DirectedGraph
     *
     * @param graph - graph on which topological sort will be applied
     * @return List of vertices representing the topological sort
     * or empty Optional if graph is not acyclic or topological sort could not be performed
     */
    public static <V,E> Optional<List<V>> toposort(DirectedGraph<V,E> graph)  {

        CycleDetector<V,E> cycleDetector = new CycleDetector<>(graph);

        if (graph == null || cycleDetector.detectCycles()) {
            return Optional.empty();
        }

        TopologicalOrderIterator<V,E> it = new TopologicalOrderIterator<>(graph);
        List<V> toposort = new LinkedList<>();
        it.forEachRemaining(toposort::add);
        return Optional.of(toposort);
    }

}
