package com.thecodeslinger;

import com.beust.jcommander.JCommander;

public class JPackageDemo
{
    public static void main(String[] args) throws Exception
    {
        var params = new Parameters();
        JCommander.newBuilder().addObject(params).build().parse(args);

        System.out.println(params);
    }
}
