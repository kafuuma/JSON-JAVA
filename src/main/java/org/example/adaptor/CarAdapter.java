package org.example.adaptor;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class CarAdapter implements JsonbAdapter<Car, JsonObject> {
    @Override
    public JsonObject adaptToJson(Car car) throws Exception {
        return Json.createObjectBuilder()
                .add("distance", car.getDistance() * 1.6)
                .build();
    }

    @Override
    public Car adaptFromJson(JsonObject obj) throws Exception {
        Car car = new Car();
        car.setDistance(obj.getJsonNumber("distance").doubleValue() / 1.6);
        return car;
    }
}
