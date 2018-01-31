import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.io.GraphMLMetadata;
import net.itransformers.graphmlloader.FileSystemGraphmlLoader;
import net.itransformers.graphmlloader.GraphmlLoader;
import org.apache.commons.collections15.Factory;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    public <G> Map<String, String> getVertexParams(String v) {
        Map<String, GraphMLMetadata<String>> vertexMetadata = graphmlLoader.getVertexMetadatas();
        HashMap<String, String> params = new HashMap<String, String>();
        for (String key : vertexMetadata.keySet()) {
            String value = vertexMetadata.get(key).transformer.transform(v);
            if (value == null) continue;
            if (!params.containsKey(key)) {
                params.put(key, value);
            } else {
                value = value.concat(", ").concat(params.get(key));
                params.put(key, value);
            }
        }
        return params;
    }
    public <G> Map<String, String> getEdgeParams(String v) {
        HashMap<String, String> params = new HashMap<String, String>();
        Map<String, GraphMLMetadata<String>> edgeMetadata = graphmlLoader.getEdgeMetadatas();
        for (String key : edgeMetadata.keySet()) {
            String value = edgeMetadata.get(key).transformer.transform(v);
            if (value == null) continue;
            if (!params.containsKey(key)) {
                params.put(key, value);
            } else {
                value = value.concat(", ").concat(params.get(key));
                params.put(key, value);
            }
        }
        return params;
    }

}
