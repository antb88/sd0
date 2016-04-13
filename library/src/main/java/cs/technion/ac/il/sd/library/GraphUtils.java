package cs.technion.ac.il.sd.library;


import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import java.util.Iterator;
import java.util.Objects;

/**
 * GraphUtils library
 */
public class GraphUtils {

    /**
     * Performs topological sort on DirectedGraph
     *
     * @param graph - graph on which topological sort will be applied
     * @return Iterator whose order of iteration is graph's topological sort
     * @throws IllegalArgumentException iff cycle is detected
     */

    public static <V,E> Iterator<V> toposort(DirectedGraph<V, E> graph)  {
        Objects.requireNonNull(graph);
        if (new CycleDetector<>(graph).detectCycles())
            throw new IllegalArgumentException("graph contains cycle");
        return new TopologicalOrderIterator<>(graph);
    }
}
