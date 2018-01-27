/*
 * GraphViewerPanelManager.java
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
import net.itransformers.graphmlloader.FileSystemGraphmlLoader;
import net.itransformers.graphmlloader.GraphmlLoader;
import net.itransformers.topologyviewer.config.TopologyViewerConfigManager;
import net.itransformers.topologyviewer.config.models.TopologyViewerConfType;
import org.apache.commons.collections15.Factory;

import javax.swing.*;
import java.io.File;
import java.util.Map;

public class GraphViewerPanelManager<G extends Graph<String, String>> {
    private G entireGraph;
    private GraphmlLoader<G> graphmlLoader;
    private String initialNode;
    private File projectPath;
    private TopologyViewerConfigManager viewerConfigManager;
    private GraphType graphType;
    private IconMapLoader iconMapLoader;
    private EdgeStrokeMapLoader edgeStrokeMapLoader;
    private EdgeColorMapLoader edgeColorMapLoader;
    private File graphmlFileName;
    private Factory<G> factory;
    private JTabbedPane tabbedPane;
    private TopologyViewerConfType viewerConfig;
    private TopologyManagerFrame frame;
    private final File versionDir;
    private String layout;
    private final File deviceXmlPath;
    protected  GraphViewerPanelFactory graphViewerPanelFactory;

    public GraphViewerPanelManager(TopologyManagerFrame frame, String projectType, File projectPath, TopologyViewerConfigManager viewerConfigPath,
                                   File graphmlFile, Factory<G> factory, JTabbedPane tabbedPane,
                                   GraphType graphType, GraphViewerPanelFactory graphViewerPanelFactory) throws Exception {
        this.frame = frame;
        this.projectPath = projectPath;
        this.graphType = graphType;
        this.viewerConfigManager = viewerConfigPath;
        versionDir = new File(new File(graphmlFile.getParent()).getParent());
       // TODO remove this Hardcode
        this.deviceXmlPath = versionDir;
        this.graphmlFileName = graphmlFile;
        this.factory = factory;
        this.tabbedPane = tabbedPane;
        entireGraph = factory.create();
        viewerConfig = viewerConfigManager.getTopologyViewerConfType();
        this.layout="FRLayout";
        this.graphViewerPanelFactory = graphViewerPanelFactory;
    }

    public Map<String, GraphMLMetadata<String>> getVertexMetadatas() {
        return graphmlLoader.getVertexMetadatas();
    }

    public void setInitialNode(String initialNode) {
        this.initialNode = initialNode;
    }

    private GraphViewerPanel createViewerPanel() {
        return graphViewerPanelFactory.createGraphViewerPanel(this.frame,viewerConfig,graphmlLoader,iconMapLoader,edgeStrokeMapLoader,
                edgeColorMapLoader,entireGraph,projectPath,deviceXmlPath,versionDir, graphmlFileName, initialNode, layout);
    }

    public void init() throws Exception {
        iconMapLoader = new IconMapLoader(viewerConfig);
        System.out.println("iconSystem" + System.currentTimeMillis());
        edgeStrokeMapLoader = new EdgeStrokeMapLoader(viewerConfig);
        System.out.println("edgeStrokeMapLoader" + System.currentTimeMillis());

        edgeColorMapLoader = new EdgeColorMapLoader(viewerConfig);
        System.out.println("edgeColorMapLoader" + System.currentTimeMillis());

        // TODO make factory
        graphmlLoader = new FileSystemGraphmlLoader<G>(entireGraph, factory, graphmlFileName);
        System.out.println("FileSystemGraphmlLoader" + System.currentTimeMillis());


        graphmlLoader.addGraphmlLoaderListener(iconMapLoader);
        System.out.println("addGraphmlLoaderListener(iconMapLoader)" + System.currentTimeMillis());


        graphmlLoader.addGraphmlLoaderListener(edgeStrokeMapLoader);
        System.out.println("addGraphmlLoaderListener(edgeStrokeMapLoader);" + System.currentTimeMillis());

        graphmlLoader.addGraphmlLoaderListener(edgeColorMapLoader);
        System.out.println("addGraphmlLoaderListener(edgeColorMapLoader);" + System.currentTimeMillis());

        graphmlLoader.loadGraphml();
        System.out.println("graphmlLoader.loadGraphml();" + System.currentTimeMillis());

    }

    public void createAndAddViewerPanel() {
        GraphViewerPanel panel = createViewerPanel();
        String tabName = this.versionDir.getName();
        tabbedPane.addTab(tabName, null, panel, graphmlFileName.getAbsolutePath());
    }
    public File getVersionDir(){
        return versionDir;
    }
    public GraphType getGraphType(){
        return graphType;
    }
}
