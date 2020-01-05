/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.sys;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class ResourceReader {

    private final InputStream resource;
    private final Map<String, String> mapProperties;
    
    public ResourceReader(String resourcePath) {
        this.resource = getClass().getResourceAsStream(resourcePath);
        mapProperties = new HashMap<>();
        loadData();
    }

    private void loadData() {
        Properties props = new Properties();
        try {
            props.load(resource);
            props.entrySet().forEach((Map.Entry<Object, Object> entry) -> 
                mapProperties.put(entry.getKey().toString(), entry.getValue().toString())
            );
        } catch (IOException ex) {
            Logger.getLogger(ResourceReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getValue(String key) {
        return mapProperties.get(key);
    }
    
}
