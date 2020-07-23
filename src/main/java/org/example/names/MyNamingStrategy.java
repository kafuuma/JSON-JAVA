package org.example.names;


import javax.json.bind.config.PropertyNamingStrategy;

public class MyNamingStrategy implements PropertyNamingStrategy {
    @Override
    public String translateName(String propertyName) {
        return "_" + propertyName;
    }
}