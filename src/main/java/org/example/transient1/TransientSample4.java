package org.example.transient1;


import javax.json.bind.annotation.JsonbTransient;

public class TransientSample4 {

    public transient String transientField = "Serialized?";

    private String field = "Serialized!";

    public String getField() {
        return field;
    }

    @JsonbTransient
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
