package org.example.visibility;

public class VisibilityTester {
    public final int publicFinalField = 0;
    private final int privateFinalField = 0;
    public transient int publicTransientField = 0;

    public static int publicStaticField = 0;
    public int publicWithNoGetter = 0;
    public int publicWithPrivateGetter;
    public Integer publicNullField = null;

    private int privateWithNoGetter;
    private int privateWithPublicGetter;

    public int getVirtualField() {
        return 0;
    }

    public void setVirtualField(int value) {

    }

    private int getPublicWithPrivateGetter() {
        return this.publicWithPrivateGetter;
    }

    public int getPrivateWithPublicGetter() {
        return this.privateWithPublicGetter;
    }
}
