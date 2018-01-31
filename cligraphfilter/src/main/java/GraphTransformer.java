import edu.uci.ics.jung.algorithms.filters.EdgePredicateFilter;
import edu.uci.ics.jung.algorithms.filters.VertexPredicateFilter;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphMLMetadata;
import net.itransformers.customgraphfilters.EdgeFilterFactory;
import net.itransformers.customgraphfilters.VertexFilterFactory;
import net.itransformers.graphmlloader.GraphmlLoader;
import net.itransformers.topologyviewer.config.models.FilterType;
import net.itransformers.topologyviewer.config.models.datamatcher.DataMatcher;

import java.util.Map;

/**
 * Created by niau on 1/26/18.
 */
public class GraphTransformer<G extends Graph<String, String>> {

    GraphmlLoader graphmlLoader;

    public GraphTransformer(GraphmlLoader graphmlLoader) {
        this.graphmlLoader = graphmlLoader;
    }


    public G transformCurrentGraph(G graph, final FilterType filter, Map<String, DataMatcher> matcherMap){
        final Map<String, GraphMLMetadata<String>> edgeMetadatas1 = graphmlLoader.getEdgeMetadatas();
        EdgePredicateFilter<String, String> edgeFilter = EdgeFilterFactory.createEdgeFilter(filter, matcherMap, edgeMetadatas1);
        final Graph<String, String> graph1 = edgeFilter.transform(graph);
        VertexPredicateFilter<String, String> filterV = VertexFilterFactory.createVertexFilter(filter, matcherMap, graphmlLoader.getVertexMetadatas(), graph1);

        G graph2 = (G) filterV.transform(graph1);
      //  HashSet<String> set = new HashSet<String>(graph2.getVertices());

        return (G) graph2;
    }
}
