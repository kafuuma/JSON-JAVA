package org.example.serialise_deserialize;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

public class AnimalSerializer implements JsonbSerializer<Animal> {

    @Override
    public void serialize(Animal obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartObject();
        ctx.serialize(obj.getClass().getName(), obj, generator);
        generator.writeEnd();
    }
}
