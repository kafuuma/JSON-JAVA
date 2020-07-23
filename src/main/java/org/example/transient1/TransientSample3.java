package org.example.transient1;


import javax.json.bind.annotation.JsonbTransient;

public class TransientSample3 {

    public transient String transientField = "Serialized?";

    private String field = "Serialized!";

    @JsonbTransient
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "TransientSample1{" +
                "transientField='" + transientField + '\'' +
                ", field='" + field + '\'' +
                '}';
    }
}

