/*
 * DiscoverySource.java
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

package net.itransformers.idiscover.v2.core.model;

import net.itransformers.connectiondetails.connectiondetailsapi.ConnectionDetails;

import java.util.Collections;
import java.util.List;

public class DiscoverySource {
    String id;
    List<ConnectionDetails> connectionDetailsList;

    public DiscoverySource(String id, List<ConnectionDetails> connectionDetailsList) {
        this.id = id;
        this.connectionDetailsList = connectionDetailsList;
    }

    public String getId() {
        return id;
    }

    public List<ConnectionDetails> getConnectionDetailsList() {
        return Collections.unmodifiableList(connectionDetailsList);
    }


    @Override
    public String toString() {
        StringBuilder neighboursStr = new StringBuilder();

        return "Node{" +
                "id='" + id + '\'' +
                ", connectionDetailsList=" + connectionDetailsList +
                String.format(", neighbours=[%s]",neighboursStr.toString())+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscoverySource node = (DiscoverySource) o;

        if (id != null ? !id.equals(node.id) : node.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
