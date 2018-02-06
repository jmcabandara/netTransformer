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
public class EdgeLivnessCounter {

    static GraphmlLoader graphmlLoader;

    private static Map<String, AtomicBoolean> edgesExistance = new HashMap<>();
    private static Map<String, AtomicInteger> edgesCount = new HashMap<>();

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
                return name.endsWith("BG.graphml");
            }
        });
        Arrays.sort(files);
        for (String file : files) {
            System.out.println("Processing file: "+file);
            File inputGraphmlFile = new File(f,file);
            count(inputGraphmlFile);
            printHistogram();
        }
    }

    static void count(File inputGraphmlFile) {

        GraphLoader graphLoader = new GraphLoader(inputGraphmlFile);
        graphmlLoader = graphLoader.getGraphmlLoader();
        Graph entireGraph = graphLoader.getEntireGraph();

        // current edges
        Collection<String> edges = entireGraph.getEdges();
        for (String edge : edges) {
            AtomicBoolean exists = edgesExistance.get(edge);
            if (exists == null) { // new edge
                edgesExistance.put(edge,new AtomicBoolean(true));
                edgesCount.put(edge, new AtomicInteger(0));
            } else if (!exists.get()){ // existing edge
                System.out.println("Existing edge appeared again: "+edge);
                exists.set(true);
                AtomicInteger count = edgesCount.get(edge);
                count.set(count.get()+1);
            }
        }

        // removed edges
        Set<String> keys = new HashSet<>(edgesExistance.keySet());
        keys.removeAll(edges);
        for (String edge : keys) {
            AtomicBoolean exists = edgesExistance.get(edge);
            if (exists.get()){
                System.out.println("Existing edge disappeared again: "+edge);
                exists.set(false);
                AtomicInteger count = edgesCount.get(edge);
                count.set(count.get()+1);
            }
        }
    }

    static void printHistogram(){
        int max = 0;
        for (AtomicInteger atomicInteger : edgesCount.values()) {
            if (max < atomicInteger.get()) max = atomicInteger.get();
        }
        int[] hist = new int[max+1];
        for (AtomicInteger atomicInteger : edgesCount.values()) {
            hist[atomicInteger.get()]++;
        }
        for (int i=0; i <= max; i++) {
            System.out.println(i + " -> "+hist[i]);
        }
    }
}
