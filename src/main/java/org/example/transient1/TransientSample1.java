
package org.example.transient1;

public class TransientSample1 {

    public transient String transientField = "Serialized?";

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


