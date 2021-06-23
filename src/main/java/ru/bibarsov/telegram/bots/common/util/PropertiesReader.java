package ru.bibarsov.telegram.bots.common.util;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import java.io.*;
import java.util.Properties;

@Immutable
@ParametersAreNonnullByDefault
public class PropertiesReader {

    public static Properties getPropertiesFromFileOrSystemResource(String fileName) {
        Properties result = new Properties();
        try (InputStream inputStream = getResourceFile(fileName)) {
            result.load(inputStream);
            return result;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public static InputStream getResourceFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (file.exists()) {
            return new FileInputStream(file);
        }
        return ClassLoader.getSystemResourceAsStream(fileName);
    }
}
