package org.rococoa.okeydoke.formatters;

import org.junit.Assert;
import org.rococoa.okeydoke.Formatter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * The standard Formatter, formats Objects to Strings.
 */
public class StringFormatter implements Formatter<Object, String> {

    private static final int BUFFER_SIZE = 4 * 1024;

    private final Charset charset;

    public StringFormatter(Charset charset) {
        this.charset = charset;
    }

    @Override
    public String readFrom(InputStream is) throws IOException {
        return readFully(new InputStreamReader(is, charset));
    }

    @Override
    public Object emptyThing() {
        return "";
    }

    @Override
    public void assertEquals(String expected, String actual) {
        Assert.assertEquals(expected, actual);
    }

    @Override
    public String formatted(Object actual) {
        if (actual.getClass().isArray())
            return stringFor((Object[]) actual);
        if (actual instanceof Iterable)
            return stringFor((Iterable) actual);
        return String.valueOf(actual);
    }

    @Override
    public void writeTo(String s, OutputStream os) throws IOException {
        os.write(s.getBytes(charset));
    }

    public Charset getCharset() {
        return charset;
    }

    private String stringFor(Iterable iterable) {
        StringBuilder result = new StringBuilder("[");
        for (Object o : iterable) {
            result.append("\"").append(formatted(o)).append("\",");
        }
        result.deleteCharAt(result.length() - 1).append("]");
        return result.toString();
    }

    private String stringFor(Object[] iterable) {
        return stringFor(Arrays.asList(iterable));
    }

    private static String readFully(Reader input) throws IOException {
        StringBuilder result = new StringBuilder();
        char[] buffer = new char[BUFFER_SIZE];
        int charsRead;
        while ((charsRead = input.read(buffer)) != -1) {
            result.append(buffer, 0, charsRead);
        }
        return result.toString();
    }
}
