/*
 * netTransformer is an open source tool able to discover IP networks
 * and to perform dynamic data data population into a xml based inventory system.
 * Copyright (C) 2010  http://itransformers.net
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.itransformers.topologyviewer.rightclick.impl.discoveryinvoker;

import net.itransformers.topologyviewer.rightclick.RightClickHandler;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.Map;

public class DiscoveryInvokerHandler implements RightClickHandler{
    @Override
    public <G> void handleRightClick(JFrame parent, String v, Map<String, String> graphMLParams,
                                     Map<String, String> rightClickParams,
                                     File projectPath,
                                     File s) throws Exception{
        System.out.println("DiscoveryInvokerHandler: v=" +v);
        System.out.println("DiscoveryInvokerHandler: graphMLParams=" +graphMLParams);
        System.out.println("DiscoveryInvokerHandler: rightClickParams=" +rightClickParams);
        System.out.println("DiscoveryInvokerHandler: s=" +s);
    }
}