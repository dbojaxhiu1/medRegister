package com.example.medregister.models;

import org.jetbrains.annotations.NotNull;

public class Pill {

    private String pill_name;
    private String pill_instruction;
    private String pill_number_in_package;
    private String pill_daily_usage;

    public Pill(String pill_name, String pill_instruction, String pill_number_in_package, String pill_daily_usage) {
        this.pill_name = pill_name;
        this.pill_instruction = pill_instruction;
        this.pill_number_in_package = pill_number_in_package;
        this.pill_daily_usage = pill_daily_usage;
    }

    public Pill() {

    }

    public String getPill_name() {
        return pill_name;
    }

    public void setPill_name(String pill_name) {
        this.pill_name = pill_name;
    }

    public String getPill_instruction() {
        return pill_instruction;
    }

    public void setPill_instruction(String pill_instruction) {
        this.pill_instruction = pill_instruction;
    }

    public String getPill_number_in_package() {
        return pill_number_in_package;
    }

    public void setPill_number_in_package(String pill_number_in_package) {
        this.pill_number_in_package = pill_number_in_package;
    }

    public String getPill_daily_usage() {
        return pill_daily_usage;
    }

    public void setPill_daily_usage(String pill_daily_usage) {
        this.pill_daily_usage = pill_daily_usage;
    }

    @NotNull
    @Override
    public String toString() {
        return "Pill{" +
                "pill_name='" + pill_name + '\'' +
                ", pill_instruction='" + pill_instruction + '\'' +
                ", pill_number_in_package='" + pill_number_in_package + '\'' +
                ", pill_daily_usage='" + pill_daily_usage + '\'' +
                '}';
    }
}
