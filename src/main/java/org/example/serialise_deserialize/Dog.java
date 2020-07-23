package org.example.serialise_deserialize;

public class Dog extends Animal {
    private boolean barking;

    public Dog() {
    }

    public Dog(String name, boolean barking) {
        super(name);
        this.barking = barking;
    }

    public boolean isBarking() {
        return barking;
    }

    public void setBarking(boolean barking) {
        this.barking = barking;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name=" + getName() +
                ", barking=" + barking +
                '}';
    }
}

