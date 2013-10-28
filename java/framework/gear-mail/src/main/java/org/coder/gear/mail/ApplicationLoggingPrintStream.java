package org.coder.gear.mail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * ApplicationLoggingPrintStream.
 * 
 * @author yoshida-n
 *
 */
public class ApplicationLoggingPrintStream extends PrintStream {

    /**
     * Constructor .
     */
    public ApplicationLoggingPrintStream() {
        super(new ByteArrayOutputStream());
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return out.toString();
    }

}
