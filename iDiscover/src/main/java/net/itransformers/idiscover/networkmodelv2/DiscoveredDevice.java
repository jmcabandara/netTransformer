package net.itransformers.idiscover.networkmodelv2;

import net.itransformers.idiscover.core.Subnet;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by niau on 8/25/16.
 */
public class DiscoveredDevice {

    private String name;
    private String id;
    private HashMap <String,String> params;
    private List<DiscoveredInterface> interfaceList;
    private LogicalDeviceData logicalDeviceData;

    public DiscoveredDevice(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<DeviceNeighbour> getDeviceNeighbours(){

        List<DeviceNeighbour> deviceNeighbours = new ArrayList<>();
        for (DiscoveredInterface discoveredInterface: interfaceList){
            deviceNeighbours.addAll(discoveredInterface.getNeighbours());
        }
        deviceNeighbours.addAll(logicalDeviceData.getDeviceNeighbourList());
        return deviceNeighbours;
    }
    public List<Subnet> getDeviceSubnets(){

        List<Subnet> subnets = new ArrayList<>();
        for(DiscoveredInterface discoveredInterface: interfaceList) {
            if (discoveredInterface.getNeighbours() != null){
                List<DiscoveredIPv4Address> iPv4Addresses = discoveredInterface.getiPv4AddressList();

                for (DiscoveredIPv4Address iPv4Address : iPv4Addresses) {
                    String ipv4Subnet = iPv4Address.getParams().get("ipv4Subnet");
                    String ipSubnetMask = iPv4Address.getParams().get("ipSubnetMask");
                    String ipv4SubnetPrefix = iPv4Address.getParams().get("ipv4SubnetPrefix");
                    String subnetName = ipv4Subnet + "/" + ipv4SubnetPrefix;
                    Subnet subnet = new Subnet(subnetName);
                    subnet.setSubnetMask(ipv4SubnetPrefix);
                    subnet.setIpAddress(ipv4Subnet);
                    subnet.setLocalInterface(discoveredInterface.getName());
                    subnet.setSubnetProtocolType("IPv4");
                    try {
                        subnet.setSubnetDiscoveryMethods(discoveredInterface.getDiscoveryMethodsPerSubnet(subnetName, "IPv4"));
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    subnets.add(subnet);

                }
            }
        }
        return subnets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public List<DiscoveredInterface> getInterfaceList() {
        return interfaceList;
    }

    public void setInterfaceList(List<DiscoveredInterface> interfaceList) {
        this.interfaceList = interfaceList;
    }

    public LogicalDeviceData getLogicalDeviceData() {
        return logicalDeviceData;
    }

    public void setLogicalDeviceData(LogicalDeviceData logicalDeviceData) {
        this.logicalDeviceData = logicalDeviceData;
    }
}
