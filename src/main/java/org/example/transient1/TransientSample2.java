package org.example.transient1;


import javax.json.bind.annotation.JsonbTransient;

public class TransientSample2 {

    public transient String transientField = "Serialized?";

    @JsonbTransient
    private String field = "Serialized!";

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
