package org.example.mapping;


import javax.json.bind.annotation.JsonbNillable;

@JsonbNillable
public class Nillable3 {

    public String nullField = null;

    public String anotherNullField = null;

    public String notNullField = "Not null";

}
