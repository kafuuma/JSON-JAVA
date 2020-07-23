package org.example.mapping;

import javax.json.bind.annotation.JsonbProperty;

public class Nillable2 {

    @JsonbProperty(nillable = true)
    public String nullField = null;

    public String anotherNullField = null;

    public String notNullField = "Not null";
}
