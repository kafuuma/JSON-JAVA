package org.example;


import javax.json.stream.JsonGenerator;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class  App extends Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add(JasonResource.class);
        return set;
    }

    @Override
    public Map<String, Object> getProperties() {
        return new HashMap<String, Object>() {{ put(JsonGenerator.PRETTY_PRINTING, true); }};
    }
}