/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package jdk.jshell.tool;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Map;
import java.util.prefs.Preferences;
import jdk.internal.jshell.tool.JShellToolBuilder;

/**
 * Interface to configure and run a Java shell tool instance. An instance of the
 * builder is created with the static {@link #builder} method. This builder can,
 * optionally, be configured with the configuration methods. All configuration
 * methods return the builder instance for use in chained initialization. All
 * configuration methods have sensible defaults which will be used if they are
 * not called.. After zero or more calls to configuration methods, the tool is
 * launched with a call to {@link #run(java.lang.String...) }.
 *
 * @since 9
 */
public interface JavaShellToolBuilder {

    /**
     * Create a builder for launching the JDK jshell tool.
     *
     * @return a builder which can be used to configure and launch the jshell
     * tool
     */
    static JavaShellToolBuilder builder() {
        return new JShellToolBuilder();
    }

    /**
     * Set the input channels.
     *
     * @implSpec If this method is not called, the behavior should be
     * equivalent to calling {@code in(System.in, null)}.
     *
     * @param cmdIn source of command input
     * @param userIn source of input for running user code, or {@code null} to
     * extract user input from cmdIn
     * @return the {@code JavaShellToolBuilder} instance
     */
    JavaShellToolBuilder in(InputStream cmdIn, InputStream userIn);

    /**
     * Set the output channels. Same as {@code out(output, output, output)}.
     *
     * @implSpec If neither {@code out} method is called, the behavior should be
     * equivalent to calling {@code out(System.out)}.
     *
     * @param output destination of command feedback, console interaction, and
     * user code output
     * @return the {@code JavaShellToolBuilder} instance
     */
    JavaShellToolBuilder out(PrintStream output);

    /**
     * Set the output channels.
     *
     * @implSpec If neither {@code out} method is called, the behavior should be
     * equivalent to calling {@code out(System.out, System.out, System.out)}.
     *
     * @param cmdOut destination of command feedback including error messages
     * for users
     * @param console destination of console interaction
     * @param userOut destination of user code output.  For example, user snippet
     * {@code System.out.println("Hello")} when executed {@code Hello} goes to
     * userOut.
     * @return the {@code JavaShellToolBuilder} instance
     */
    JavaShellToolBuilder out(PrintStream cmdOut, PrintStream console, PrintStream userOut);

    /**
     * Set the error channels. Same as {@code err(error, error)}.
     *
     * @implSpec If neither {@code err} method is called, the behavior should be
     * equivalent to calling {@code err(System.err)}.
     *
     * @param error destination of tool errors, and
     * user code errors
     * @return the {@code JavaShellToolBuilder} instance
     */
    JavaShellToolBuilder err(PrintStream error);

    /**
     * Set the error channels.
     *
     * @implSpec If neither {@code err} method is called, the behavior should be
     * equivalent to calling {@code err(System.err, System.err, System.err)}.
     *
     * @param cmdErr destination of tool start-up and fatal errors
     * @param userErr destination of user code error output.
     * For example, user snippet  {@code System.err.println("Oops")}
     * when executed {@code Oops} goes to userErr.
     * @return the {@code JavaShellToolBuilder} instance
     */
    JavaShellToolBuilder err(PrintStream cmdErr, PrintStream userErr);

    /**
     * Set the storage mechanism for persistent information which includes
     * input history and retained settings.
     *
     * @implSpec If neither {@code persistence} method is called, the behavior
     * should be to use the tool's standard persistence mechanism.
     *
     * @param prefs an instance of {@link java.util.prefs.Preferences} that
     * is used to retrieve and store persistent information
     * @return the {@code JavaShellToolBuilder} instance
     */
    JavaShellToolBuilder persistence(Preferences prefs);

    /**
     * Set the storage mechanism for persistent information which includes
     * input history and retained settings.
     *
     * @implSpec If neither {@code persistence} method is called, the behavior
     * should be to use the tool's standard persistence mechanism.
     *
     * @param prefsMap  an instance of {@link java.util.Map} that
     * is used to retrieve and store persistent information
     * @return the {@code JavaShellToolBuilder} instance
     */
    JavaShellToolBuilder persistence(Map<String,String> prefsMap);

    /**
     * Set the source for environment variables.
     *
     * @implSpec If this method is not called, the behavior should be
     * equivalent to calling {@code env(System.getenv())}.
     *
     * @param vars the Map of environment variable names to values
     * @return the {@code JavaShellToolBuilder} instance
     */
    JavaShellToolBuilder env(Map<String,String> vars);

    /**
     * Set the locale.
     *
     * @implSpec If this method is not called, the behavior should be
     * equivalent to calling {@code locale(Locale.getDefault())}.
     *
     * @param locale the locale
     * @return the {@code JavaShellToolBuilder} instance
     */
    JavaShellToolBuilder locale(Locale locale);

    /**
     * Set to enable a command capturing prompt override.
     *
     * @implSpec If this method is not called, the behavior should be
     * equivalent to calling {@code promptCapture(false)}.
     *
     * @param capture if {@code true}, basic prompt is the {@code ENQ}
     * character and continuation prompt is the {@code ACK} character.
     * If false, prompts are as set with set-up or user {@code /set} commands.
     * @return the {@code JavaShellToolBuilder} instance
     */
    JavaShellToolBuilder promptCapture(boolean capture);

    /**
     * Run an instance of the Java shell tool as configured by the other methods
     * in this interface.  This call is not destructive, more than one call of
     * this method may be made from a configured builder.
     *
     * @param arguments the command-line arguments (including options), if any
     * @throws Exception an unexpected fatal exception
     */
    void run(String... arguments) throws Exception;
}
