/*
 * EdgeColorMapLoader.java
 *
 * This work is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This work is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Copyright (c) 2010-2016 iTransformers Labs. All rights reserved.
 */

package net.itransformers.topologyviewer.gui;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphMLMetadata;
import net.itransformers.graphmlloader.GraphmlLoaderListener;
import net.itransformers.topologyviewer.config.models.EdgeColorType;
import net.itransformers.topologyviewer.config.models.TopologyViewerConfType;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Date: 11-11-8
 * Time: 11:27
 * To change this template use File | Settings | File Templates.
 */
public class EdgeColorMapLoader implements GraphmlLoaderListener {
    static Logger logger = Logger.getLogger(EdgeColorMapLoader.class);

    private Map<String,Color> edgesColorMap = new HashMap<String, Color>();
    private TopologyViewerConfType viewerConfig;

    public EdgeColorMapLoader(TopologyViewerConfType viewerConfig) {
        this.viewerConfig = viewerConfig;
    }

    public Map<String, Color> getEdgesColorMap() {
        return edgesColorMap;
    }

    private void updateEdgeColorMap(final Map<String, GraphMLMetadata<String>> edgeMetadata, final Collection<String> graphEdges) {
//        final Collection<String> graphVertices = graph.getVertices();
        String[] vertices = graphEdges.toArray(new String[graphEdges.size()]);
        List<EdgeColorType> edgeColorTypeList = viewerConfig.getEdgeColor();
        List<EdgeColorType.Data> datas;
        for (String edge : vertices) {
            for (EdgeColorType edgeColorType : edgeColorTypeList) {
                boolean match = true;
                datas = edgeColorType.getData();
                boolean isDefaultIcon = datas.isEmpty();
                for (EdgeColorType.Data data : datas) {
                    final GraphMLMetadata<String> stringGraphMLMetadata = edgeMetadata.get(data.getKey());
                    if (stringGraphMLMetadata == null){
                        logger.error(String.format("Can not find edge metadata key '%s'.",data.getKey()));
                        match = false;
                        break;
//                        throw new RuntimeException(String.format("Can not find vertex metadata key '%s' in file '%s'.",data.getKey(), fileName));
                    }
                    final String value = stringGraphMLMetadata.transformer.transform(edge);
                    if (value == null || !value.equals(data.getValue())) {
                        match = false;
                        break;
                    }
                }
                boolean iconExists = edgesColorMap.containsKey(edge);
                if ((!isDefaultIcon && match) || (isDefaultIcon && !iconExists)) {
                    try {
                        String colorStr = edgeColorType.getValue();
                        int colorInt = Integer.parseInt(colorStr,16);
                        final Color edgeColor = new Color(colorInt);
                        edgesColorMap.put(edge, edgeColor);
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                        break;
                    } catch (NullPointerException npe) {
                        npe.printStackTrace();
                        break;
                    }
                    break;
                }
            }
        }
    }

    @Override
    public <G extends Graph<String,String>> void graphmlLoaded(Map<String, GraphMLMetadata<String>> vertexMetadata, Map<String, GraphMLMetadata<String>> edgeMetadata, G graph) {
        updateEdgeColorMap(edgeMetadata, graph.getEdges());
    }
}
