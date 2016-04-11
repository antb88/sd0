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
     * Performs topological sort on DirectedAcyclicGraph
     *
     * @param graph - graph on which topological sort will be applied
     * @return Iterator whose order of iteration is graph's topological sort
     */

    public static <V,E> Iterator<V> toposort(DirectedAcyclicGraph<V, E> graph)  {
        Objects.requireNonNull(graph);
        return new TopologicalOrderIterator<>(graph);
    }

}
