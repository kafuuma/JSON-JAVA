package org.example.transient1;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class JsonbCreatorSample {

    private String field1;
    private String field2;

    public String publicField = "";

    @JsonbCreator
    public JsonbCreatorSample(@JsonbProperty("field1") String field1,
                              @JsonbProperty("field2") String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    @Override
    public String toString() {
        return "JsonbCreatorSample{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", publicField='" + publicField + '\'' +
                '}';
    }
}
