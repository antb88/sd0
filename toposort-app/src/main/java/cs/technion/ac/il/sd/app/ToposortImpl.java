package cs.technion.ac.il.sd.app;

import com.google.inject.Inject;
import cs.technion.ac.il.sd.External;
import cs.technion.ac.il.sd.library.GraphUtils;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Nati on 06/04/2016.
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

        DirectedGraph<Integer, DefaultEdge> graph = buildGraph(file);
        if(graph == null)
        {
            //TODO - how to handle parsing error?
        }

        Optional<List<Integer>> toposort = GraphUtils.toposort(graph);
        if(!toposort.isPresent())
        {
           external.fail();
        }
        else
        {
            for (Integer vertex : toposort.get()) {
                external.process(vertex);
            }
        }

    }

    private DirectedGraph<Integer, DefaultEdge> buildGraph(File file) {
        if(file == null)
        {
            return null;
        }
        DirectedGraph<Integer, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();

            while (line != null) {
                processLine(graph, line);
                line = br.readLine();
            }

        } catch (Exception e) {
           graph = null;
        }
        return graph;
    }

    private void processLine(DirectedGraph<Integer, DefaultEdge> graph, String line) {
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

//    public static void main(String[] args)
//    {
//        DefaultDirectedGraph graph = new DefaultDirectedGraph<>(DefaultEdge.class);
//        graph.addVertex(1);
//        System.out.println(graph.containsVertex(1));
//        System.out.println(graph.vertexSet().size());
//        graph.addVertex(1);
//        System.out.println(graph.vertexSet().size());
//
//    }



}
