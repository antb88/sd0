package cs.technion.ac.il.sd.app;

import com.google.inject.Inject;
import cs.technion.ac.il.sd.External;
import cs.technion.ac.il.sd.library.GraphUtils;
import org.jgrapht.DirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

/**
 * Created by Nati on 06/04/2016.
 * Toposort application implementation
 */
public class ToposortImpl implements Toposort
{
    public static final String EDGE_SYMBOLE = "->";
    private final External external;

    @Inject
    public ToposortImpl(External external)
    {
        this.external = external;
    }

    @Override
    public void processFile(File file)
    {
        Optional<Iterator<Integer>> toposort = GraphUtils.toposort(buildGraph(file));
        if(!toposort.isPresent())
        {
            external.fail();
            return;
        }
        toposort.get().forEachRemaining(external::process);
    }

    private DirectedGraph<Integer, DefaultEdge> buildGraph(File file)
    {
        DirectedGraph<Integer, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        try(BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            br.lines().forEach((l) -> processLine(graph, l));
        }
        catch (IOException e)
        {
            throw new AssertionError();
        }
        return graph;
    }


    private void processLine(DirectedGraph<Integer, DefaultEdge> graph, String line) {
        if(line.contains(EDGE_SYMBOLE))
        {
            String[] vertexes = line.split(EDGE_SYMBOLE);
            int source = Integer.parseInt(vertexes[0].trim());
            int dest = Integer.parseInt(vertexes[1].trim());
            graph.addVertex(source);
            graph.addVertex(dest);
            graph.addEdge(source,dest);
        }
        else
        {
            graph.addVertex(Integer.parseInt(line.trim()));
        }
    }
}


