package org.example.serialise_deserialize;


public class Cat extends Animal {
    private boolean fluffy;

    public Cat() {
    }

    public Cat(String name, boolean fluffy) {
        super(name);
        this.fluffy = fluffy;
    }

    public boolean isFluffy() {
        return fluffy;
    }

    public void setFluffy(boolean fluffy) {
        this.fluffy = fluffy;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name=" + getName() +
                ", fluffy=" + fluffy +
                '}';
    }
}

