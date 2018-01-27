import net.itransformers.graphmlloader.GraphmlLoader;
import net.itransformers.topologyviewer.config.models.FilterType;
import net.itransformers.topologyviewer.config.models.ForType;
import net.itransformers.topologyviewer.config.models.IncludeType;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by niau on 1/25/18.
 */
public class CLIGraphFilter {
    static Logger logger = Logger.getLogger(CLIGraphFilter.class);

    static GraphmlLoader graphmlLoader;

    public static void main(String[] args) throws MalformedURLException {


        Options options = new Options();
        Option inputGraphl = new Option("ig", "input graphml", true, "Path to input graphml file");
        inputGraphl.setRequired(true);
        options.addOption(inputGraphl);

        Option outputGraphmlOption = new Option("og", "output graphml ", true, "Path to output graphml file");
        outputGraphmlOption.setRequired(false);
        options.addOption(outputGraphmlOption);

        Option filter = new Option("f", "filter", true, "Filter name");
        filter.setRequired(true);
        options.addOption(filter);


        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error(e);
            System.exit(1);
        }

        String inputGraphml = cmd.getOptionValue("ig");
        File inputGraphmlFile = new File(inputGraphml);

        String outputGraphml = cmd.getOptionValue("og");
        File outputGraphmlFile = new File(outputGraphml);


        filter(inputGraphmlFile, outputGraphmlFile);


    }

    static void filter(File inputGraphmlFile,File outputGraphmlFile){

      GraphLoader graphLoader = new GraphLoader(inputGraphmlFile);
      graphmlLoader = graphLoader.getGraphmlLoader();
      GraphTransformer graphTransformer = new GraphTransformer(graphmlLoader);

      FilterType filterType = new FilterType();
      filterType.setName("BG peering filter");
      filterType.setType("or");
      IncludeType edgeIncludeType = new IncludeType();
      edgeIncludeType.setFor(ForType.EDGE);
      filterType.getInclude().add(edgeIncludeType);
      IncludeType nodeIncludeType = new IncludeType();
      nodeIncludeType.setFor(ForType.NODE);

      nodeIncludeType.setClassType("net.itransformers.customgraphfilters.BGIPv4IntPeeringIncluder");
      filterType.getInclude().add(nodeIncludeType);
      GraphWritter graphWritter = new GraphWritter();
      graphWritter.write(graphTransformer.transformCurrentGraph(graphLoader.getEntireGraph(),filterType,null),graphmlLoader,outputGraphmlFile);


    }

}
