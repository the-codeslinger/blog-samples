package com.thecodeslinger.externalconfig.app;

import com.thecodeslinger.externalconfig.config.ExternalConfigProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.cli.*;
import org.springframework.boot.CommandLineRunner;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final ExternalConfigProperties externalConfigProperties;


    private final Option input = Option.builder()
            .option("i").longOpt("input").hasArg().required()
            .desc("Source folder to read from.")
            .build();
    private final Option output = Option.builder()
            .option("o").longOpt("output").hasArg().required()
            .desc("Destination folder to write to.")
            .build();
    private final Option file = Option.builder()
            .option("f").longOpt("file").hasArg()
            .desc("(Optional) Filename in source folder.")
            .build();
    private final Option help = Option.builder()
            .option("h").longOpt("help")
            .desc("Print this message.")
            .build();

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-> AppRunner.run() Command Line Arguments");

        for (final var arg : args) {
            System.out.println("Argument: " + arg);
        }

        System.out.println("-> ExternalConfigProperties");

        System.out.println("Input path: " + externalConfigProperties.getInput().getPath());
        System.out.println("Output path: " + externalConfigProperties.getOutput().getPath());

        System.out.println("-> Parsing Help With Apache Commons CLI");
        final var parser = new DefaultParser();
        final var applicationOptions = example_2_Options();

        try {
            final var options = example_2_Help();
            final var cli = parser.parse(options, args, true);

            if (cli.hasOption(help)) {
                // Append the actual options for printing to the command-line.
                applicationOptions.getOptions().forEach(options::addOption);
                new HelpFormatter().printHelp("external-config-commons-cli", options);
                return;
            }
        } catch (MissingOptionException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("-> Parsing Arguments With Apache Commons CLI");
        
        try {
            final var cli = parser.parse(applicationOptions, args, true);

            applicationOptions.getOptions().forEach(opt -> {
                if (cli.hasOption(opt)) {
                    System.out.printf("Found option %s with value %s%n",
                            opt.getOpt(), cli.getOptionValue(opt));
                }
            });
        } catch (MissingOptionException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Most straight-forward setup. Apache Commons CLI does not have a concept of "help".
     * This config will fail when running the application with "--help" because the required
     * arguments "-i" and "-o" are not present.
     */
    private void example_1() {
        System.out.println("-> Parsing Arguments With Apache Commons CLI");

        final var options = new Options();
        options.addOption(input);
        options.addOption(output);
        options.addOption(file);
        options.addOption(help);

        try {
            final var args = new String[]{};
            final var parser = new DefaultParser();
            final CommandLine cli = parser.parse(options, args);

            Stream.of(cli.getOptions()).map(Option::toString).forEach(System.out::println);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }

    private Options example_2_Options() {
        final var options = new Options();
        options.addOption(input);
        options.addOption(output);
        options.addOption(file);

        return options;
    }

    private Options example_2_Help() {
        final var options = new Options();
        options.addOption(help);

        return options;
    }
}
