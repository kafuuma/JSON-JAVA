package org.example.serialise_deserialize;

import javax.json.bind.JsonbException;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;

public class AnimalDeserializer implements JsonbDeserializer<Animal> {

    @Override
    public Animal deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
        parser.next();

        String className = parser.getString();
        parser.next();

        try {
            return ctx.deserialize(Class.forName(className).asSubclass(Animal.class), parser);
        } catch (ClassNotFoundException e) {
            throw new JsonbException("Cannot deserialize a class!");
        }
    }
}
