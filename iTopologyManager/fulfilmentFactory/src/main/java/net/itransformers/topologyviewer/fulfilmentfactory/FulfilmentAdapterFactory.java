package net.itransformers.topologyviewer.fulfilmentfactory;

import net.itransformers.topologyviewer.fulfilmentfactory.config.FulfilmentFactoriesType;
import net.itransformers.topologyviewer.fulfilmentfactory.config.FulfilmentFactoryType;
import net.itransformers.topologyviewer.fulfilmentfactory.config.ParamType;
import net.itransformers.topologyviewer.fulfilmentfactory.config.TypeType;
import net.itransformers.topologyviewer.fulfilmentfactory.util.JaxbMarshalar;
import net.itransformers.topologyviewer.parameterfactory.ParameterFactory;
import net.itransformers.topologyviewer.parameterfactory.ParameterFactoryBuilder;
import net.itransformers.resourcemanager.config.ResourceType;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 *
 **
 * 
 * Copyright 
 */
public class FulfilmentAdapterFactory {
    private File projectPath;
    private File configFile;
    private ParameterFactoryBuilder parameterFactoryBuilder;
    private Map<String,TypeType> fulfilmentFactoryTypesMap = new LinkedHashMap<String, TypeType>();
    private Map<String,FulfilmentFactoryType> fulfilmentFactoryMap = new LinkedHashMap<String, FulfilmentFactoryType>();

    public FulfilmentAdapterFactory(File projectPath,
                                    File configFile,
                                    ParameterFactoryBuilder parameterFactoryBuilder,
                                    ResourceType resource
    ) throws IOException, JAXBException {
        this.projectPath = projectPath;
        this.configFile = configFile;
        this.parameterFactoryBuilder = parameterFactoryBuilder;
        FileInputStream is = null;
        try {
            is = new FileInputStream(configFile);
            FulfilmentFactoriesType factoriesType = JaxbMarshalar.unmarshal(FulfilmentFactoriesType.class, is);
            for (FulfilmentFactoryType fulfilmentFactoryType : factoriesType.getFulfilmentFactory()){
                fulfilmentFactoryMap.put(fulfilmentFactoryType.getName(),fulfilmentFactoryType);
            }
            for (TypeType typeType : factoriesType.getFulfilmentFactoryTypes().getType()) {
                fulfilmentFactoryTypesMap.put(typeType.getName(), typeType);
            }
        } finally {
            if (is != null) is.close();
        }
        
    }

    public FulfilmentAdapter createFulfilmentAdapter(String factoryName, Map<String, Object> parameterFactoryContext, Logger logger) throws Exception, IllegalAccessException, InstantiationException {
        FulfilmentFactoryType fulfilmentFactory = fulfilmentFactoryMap.get(factoryName);
        if (fulfilmentFactory == null) {
            throw new RuntimeException(String.format("Can not find fulfilment factory '%s' in file : %s",factoryName,configFile));
        }
        String paramFactoryName = fulfilmentFactory.getParameterFactoryName();
        ParameterFactory parameterFactory = parameterFactoryBuilder.buildParameterFactory(paramFactoryName);
        Map<String,String> parameters = parameterFactory.createParameters(parameterFactoryContext);
        String type = fulfilmentFactory.getType();
        TypeType typeType = fulfilmentFactoryTypesMap.get(type);
        Map<String, String> fulfilmentFactoryParams = new HashMap<String, String>();
        if (typeType == null) {
            throw new RuntimeException("Can not find type in fulfilment factotry types: "+type);
        }
        List<ParamType> paramList = typeType.getParam();
        for (ParamType paramType : paramList) {
            if (fulfilmentFactoryParams.containsKey(paramType.getName())){
                throw new RuntimeException("can not define two parameters with the same name");
            }
            fulfilmentFactoryParams.put(paramType.getName(), paramType.getValue());
        }
        String strClass = typeType.getClazz();
        Class<?> clazz = Class.forName(strClass);
        Fulfilment inst = (Fulfilment) clazz.newInstance();
        FulfilmentAdapter adapter = new FulfilmentAdapter(projectPath, inst,parameters, fulfilmentFactoryParams, logger);
        return adapter;
    }

    public String[] getFulfilmentFactoryNamesForResource(String resourceName){
        List<String> result = new ArrayList<String>();
        for (String key : fulfilmentFactoryMap.keySet()) {
            if (fulfilmentFactoryMap.get(key).getResourceName().equals(resourceName)){
                result.add(key);
            }
        }
        return result.toArray(new String[0]);
    }
}