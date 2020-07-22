package org.example;


/*
* Simple JAX-RS application demonstrating integrating with JSON-P
* */

import javax.json.JsonPointer;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/")
public  class App extends Application {

    public Set<Class<?>> getClasses(){
        Set<Class<?>> set = new HashSet<>();
        set.add(JsonPatchResource.class);
        set.add(JsonPointerResource.class);
        return set;
    }

    @Override
    public Map<String, Object> getProperties(){
        return  new HashMap<String, Object>(){{
            put(JsonGenerator.PRETTY_PRINTING, true);
        }
        };
    }

}


