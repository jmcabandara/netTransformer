import edu.uci.ics.jung.graph.Graph;
import net.itransformers.graphmlloader.GraphmlLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by niau on 1/25/18.
 */
public class BirthAnalyze {

    static GraphmlLoader graphmlLoader;

    private static Set<String> exitingNodes = new HashSet<>();

    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Start processing..");

        if (args.length !=1) {
            System.out.println("Missing input dir");
            System.exit(1);
        }
        File f = new File(args[0]);
        String[] files = f.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("bz2.graphml");
            }
        });
        Arrays.sort(files);
        for (String file : files) {
            System.out.println("Processing file: "+file);
            File inputGraphmlFile = new File(f,file);
            count(inputGraphmlFile);
        }
    }

    static void count(File inputGraphmlFile) {

        GraphLoader graphLoader = new GraphLoader(inputGraphmlFile);
        graphmlLoader = graphLoader.getGraphmlLoader();
        Graph entireGraph = graphLoader.getEntireGraph();


        Collection<String> vertices = entireGraph.getVertices();
        for (String vertex : vertices) {
            boolean isAdded = exitingNodes.add(vertex);
            if (isAdded) {
                Collection<String> neighbours = entireGraph.getNeighbors(vertex);
                for (String neighbour : neighbours) {
                    int neighbourCount = entireGraph.getNeighborCount(neighbour);
                    System.out.println(neighbourCount);
                }
            }
        }
    }
}
