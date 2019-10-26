/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xpressplayer.xpressplayer.sys;

import java.util.HashMap;

/**
 *
 * @author martin
 */
public class FormObjectManager {
    private final HashMap<String, Object> mapObjects;

    private static final FormObjectManager singletonObject;
    
    static {
        singletonObject = new FormObjectManager();
    }
    
    public static FormObjectManager getInstance() {
        return singletonObject;
    }
    
    private FormObjectManager() {
        this.mapObjects = new HashMap<>();
    }
    
    public boolean exists(String key) {
        return mapObjects.containsKey(key);
        
    }
    
    public void add(String key, Object object) {
        mapObjects.put(key, object);
    }
    
    public <T> T get(String key) {
        return (T) mapObjects.get(key);
    }
    
    public void set(String key, Object object) {
        mapObjects.put(key, object);
    }
    
    public void delete(String key) {
        mapObjects.remove(key);
    }
    
}
