import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import net.itransformers.graphmlloader.FileSystemGraphmlLoader;
import net.itransformers.graphmlloader.GraphmlLoader;
import org.apache.commons.collections15.Factory;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by niau on 1/25/18.
 */
public class GraphLoader<G extends Graph<String, String>> {

    File inputGraphml;
    private GraphmlLoader<G> graphmlLoader;
    static Logger logger = Logger.getLogger(GraphLoader.class);
    G entireGraph;


    public GraphLoader(File inputGraphml) {
        this.inputGraphml = inputGraphml;
        load();
    }

    public G getEntireGraph() {
        return entireGraph;
    }

    public GraphmlLoader<G> getGraphmlLoader() {
        return graphmlLoader;
    }

    public void setGraphmlLoader(GraphmlLoader<G> graphmlLoader) {
        this.graphmlLoader = graphmlLoader;
    }

    void load(){
        Factory<G> factory = (Factory<G>) UndirectedSparseMultigraph.<String, String>getFactory();
        entireGraph  = factory.create();

        graphmlLoader = new FileSystemGraphmlLoader<G>(entireGraph, factory, inputGraphml);

        try {
            graphmlLoader.loadGraphml();
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
