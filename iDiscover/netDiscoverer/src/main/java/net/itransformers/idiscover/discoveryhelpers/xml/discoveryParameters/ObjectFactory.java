/*
 * ObjectFactory.java
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

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.06.21 at 01:58:29 AM EEST 
//


package net.itransformers.idiscover.discoveryhelpers.xml.discoveryParameters;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.itransformers.idiscover.discoveryhelpers.xml.discoveryParameters package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DiscoveryHelper_QNAME = new QName("", "discovery-helper");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.itransformers.idiscover.discoveryhelpers.xml.discoveryParameters
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DiscoveryHelperType }
     * 
     */
    public DiscoveryHelperType createDiscoveryHelperType() {
        return new DiscoveryHelperType();
    }

    /**
     * Create an instance of {@link MatchNotType }
     * 
     */
    public MatchNotType createMatchNotType() {
        return new MatchNotType();
    }

    /**
     * Create an instance of {@link DiscoveryMethodType }
     * 
     */
    public DiscoveryMethodType createDiscoveryMethodType() {
        return new DiscoveryMethodType();
    }

    /**
     * Create an instance of {@link DeviceType }
     * 
     */
    public DeviceType createDeviceType() {
        return new DeviceType();
    }

    /**
     * Create an instance of {@link MatchType }
     * 
     */
    public MatchType createMatchType() {
        return new MatchType();
    }

    /**
     * Create an instance of {@link StopCriteriaType }
     * 
     */
    public StopCriteriaType createStopCriteriaType() {
        return new StopCriteriaType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscoveryHelperType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "discovery-helper")
    public JAXBElement<DiscoveryHelperType> createDiscoveryHelper(DiscoveryHelperType value) {
        return new JAXBElement<DiscoveryHelperType>(_DiscoveryHelper_QNAME, DiscoveryHelperType.class, null, value);
    }

}
