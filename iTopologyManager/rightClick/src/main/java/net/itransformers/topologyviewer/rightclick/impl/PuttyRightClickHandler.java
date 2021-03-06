/*
 * PuttyRightClickHandler.java
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

package net.itransformers.topologyviewer.rightclick.impl;

import net.itransformers.resourcemanager.ResourceManager;
import net.itransformers.resourcemanager.ResourceManagerFactory;
import net.itransformers.topologyviewer.rightclick.RightClickHandler;
import net.itransformers.topologyviewer.rightclick.impl.putty.Putty;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PuttyRightClickHandler implements RightClickHandler {
    protected ResourceManagerFactory resourceManagerFactory;

    public PuttyRightClickHandler(ResourceManagerFactory resourceManagerFactory) {
        this.resourceManagerFactory = resourceManagerFactory;
    }

    public <G> void handleRightClick(JFrame parent, String v,
                                     Map<String, String> graphMLParams,
                                     Map<String, String> rightClickParams,
                                     File projectPath,
                                     java.io.File s){
        Map<String, String> props = new HashMap<>();
        props.put("projectPath",projectPath.getAbsolutePath());
        ResourceManager resourceManager = resourceManagerFactory.createResourceManager("xml",props);
        ResourceResolver resourceResolver = new ResourceResolver(resourceManager);

        Map<String,String> connParams;
        try {
            connParams = resourceResolver.getResource(graphMLParams, "ssh");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Can not find resource : "+e.getMessage());
            return;
        }
        String session = rightClickParams.get("saved_session");
//        connParams.put("discoveredIPv4Address",graphMLParams.get("discoveredIPv4Address"));
        connParams.put("session", session);

        handleConnParams(parent, connParams, rightClickParams);
    }

    protected void handleConnParams(JFrame parent, Map<String, String> connParams, Map<String, String> rightClickParams) {
        Putty putty = new Putty(rightClickParams);
        putty.openSession(connParams);
    }

}
