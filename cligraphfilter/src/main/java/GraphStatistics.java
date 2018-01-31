import edu.uci.ics.jung.graph.Graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by niau on 1/28/18.
 */
public class GraphStatistics<G extends Graph<String, String>> {

    GraphLoader loader;

    public GraphStatistics(GraphLoader loader) {
        this.loader = loader;
    }

    String countryGraphStats(String country){
        StringBuffer sb = new StringBuffer();

        Map<String,Integer> countryCounters = new HashMap<String,Integer>();
        Map<String,Long> countryIPv4Counters = new HashMap<String,Long>();
        Map<String,Long> countryIPv6Counters = new HashMap<String,Long>();

        for (Iterator iterator = loader.getEntireGraph().getVertices().iterator(); iterator.hasNext();) {
            String vertex = (String) iterator.next();
            HashMap<String, String> vertexMetadata = (HashMap<String, String>) loader.getVertexParams(vertex);


            if (vertexMetadata != null) {
                String currentCountry = vertexMetadata.get("Country");

                if(currentCountry.equals(country)){
                    if (countryCounters.get(currentCountry)!=null){
                        int counter = countryCounters.get(currentCountry);
                        countryCounters.put(currentCountry,++counter);
                    } else{
                        countryCounters.put(currentCountry,1);
                    }

                    Long IPv4AddressSpace = Long.valueOf(vertexMetadata.get("IPv4AddressSpace"));
                    if (countryIPv4Counters.get(currentCountry)!=null){
                        long counter = countryIPv4Counters.get(country);
                        countryIPv4Counters.put(currentCountry,counter+IPv4AddressSpace);
                    } else{
                        countryIPv4Counters.put(currentCountry, IPv4AddressSpace);
                    }

                    Long IPv6AddressSpace = Long.valueOf(vertexMetadata.get("IPv6AddressSpace"));
                    if (countryIPv6Counters.get(currentCountry)!=null){
                        long counter = countryIPv6Counters.get(currentCountry);
                        countryIPv6Counters.put(country,counter+IPv6AddressSpace);
                    } else{
                        countryIPv6Counters.put(country,IPv6AddressSpace);
                    }
                } else {
                    if (countryCounters.get("InternationalPeering")!=null){
                        int counter = countryCounters.get("InternationalPeering");
                        countryCounters.put("InternationalPeering",++counter);
                    } else{
                        countryCounters.put("InternationalPeering",1);
                    }

                }
            }

        }
        sb.append("\n"+country +"nodes"+country +"edges,IPv4 Address Space,IPv6 Address Space,International peers,International peer links\n");
        for (Map.Entry<String, Integer> stringIntegerEntry : countryCounters.entrySet()) {
            sb.append(stringIntegerEntry.getKey()+ ": "+stringIntegerEntry.getValue()+"\n");
        }
        sb.append("\nBG IPv4 Address Space counters: \n");

        for (Map.Entry<String, Long> stringLongEntry : countryIPv4Counters.entrySet()) {
            sb.append(stringLongEntry.getKey()+ ": "+stringLongEntry.getValue()+"\n");
        }
        sb.append("\nBG IPv6 Address Space counters: \n");

        for (Map.Entry<String, Long> stringLongEntry : countryIPv6Counters.entrySet()) {
            sb.append(stringLongEntry.getKey()+ ": "+stringLongEntry.getValue()+"\n");
        }
        return  sb.toString();
    }

}
