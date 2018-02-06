import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import net.itransformers.graphmlloader.GraphmlLoader;
import net.itransformers.utils.MyGraphMLWriter;
import org.apache.commons.collections15.Transformer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by niau on 1/26/18.
 */
public class GraphWritter {

  void  write(Graph<String, String> currentGraph,Map graphMetadatas, Map vertexMetadatas, Map edgeMetadatas, File outputGraphml){
        Writer fileWriter;
        try {
            fileWriter = new FileWriter(outputGraphml);
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        MyGraphMLWriter writer = new MyGraphMLWriter();
      writer.setGraphData(graphMetadatas);
      writer.setVertexData(vertexMetadatas);
      writer.setEdgeData(edgeMetadatas);
        writer.setEdgeIDs(new Transformer<String, String>() {

            @Override
            public String transform(String s) {
                Pair<String> endpoints = currentGraph.getEndpoints(s);
                String[] endpointsArr =  new String[] {endpoints.getFirst(), endpoints.getSecond()};
                Arrays.sort(endpointsArr);
                return endpointsArr[0] + "_" + endpointsArr[1];
            }
        });
        boolean flag;
        try {
            writer.save(currentGraph, fileWriter);

        } catch (IOException e1) {
            e1.printStackTrace();

        }
    }
}
