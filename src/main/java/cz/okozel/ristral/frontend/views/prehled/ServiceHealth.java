package cz.okozel.ristral.frontend.views.prehled;

import com.vaadin.fusion.Nonnull;

/**
 * Simple DTO class for the inbox list to demonstrate complex object data
 */
public class ServiceHealth {

    @Nonnull
    private Status status;

    @Nonnull
    private String city;

    @Nonnull
    private int input;

    @Nonnull
    private int output;

    @Nonnull
    private String theme;

    enum Status {
        EXCELLENT, OK, FAILING
    }

    public ServiceHealth() {

    }

    public ServiceHealth(Status status, String city, int input, int output) {
        this.status = status;
        this.city = city;
        this.input = input;
        this.output = output;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

}
