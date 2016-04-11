package cs.technion.ac.il.sd.app;

import com.google.inject.Inject;
import cs.technion.ac.il.sd.External;
import cs.technion.ac.il.sd.library.GraphUtils;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
    public void processFile(File file) {

        try {
            GraphUtils.toposort(buildGraph(file)).forEachRemaining(external::process);
        }
        catch (Exception e) {
            external.fail();
        }
//
// if(graph == null)
//        {
//            //TODO - how to handle parsing error?
//        }
    }

    private DirectedAcyclicGraph<Integer, DefaultEdge> buildGraph(File file) throws Exception {
//        if(file == null)
//        {
//            return null;
//        }
        DirectedAcyclicGraph<Integer, DefaultEdge> graph = new DirectedAcyclicGraph<>(DefaultEdge.class);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();

        while (line != null) {
            processLine(graph, line);
            line = br.readLine();
        }
        return graph;
    }


    private void processLine(DirectedAcyclicGraph<Integer, DefaultEdge> graph, String line) {
        if(line.contains(EDGE_SYMBOLE))
        {
            String[] vertexes = line.split(EDGE_SYMBOLE);
            int source = Integer.parseInt(vertexes[0]);
            int dest = Integer.parseInt(vertexes[1]);
            graph.addVertex(source);
            graph.addVertex(dest);
            graph.addEdge(source,dest);
        }
        else
        {
            graph.addVertex(Integer.parseInt(line));
        }
    }
}
