package com.thecodeslinger;

import com.beust.jcommander.Parameter;

import java.util.List;

public class Parameters
{
    @Parameter(names = {"--name", "-n"})
    private String name;

    @Parameter(names = {"--features", "-f"})
    private List<String> features;

    @Override
    public String toString() {
        return "Name: " + name + " - Features: " + features;
    }
}
