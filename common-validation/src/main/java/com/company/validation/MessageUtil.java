package com.company.validation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * <p>Title: MessageUtil<／p>
 * <p>Description: ${DESC}<／p>
 *
 * @author wangzhj
 * @time 2016-11-04 16:47
 */
public abstract class MessageUtil {

    private static final String MESSAGE_FILE = "com/company/validation/message.properties";

    private static final Properties prop = new Properties();

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream(MESSAGE_FILE);
        try {
            prop.load(new InputStreamReader(is, "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     **/
    public static String format(String key, Object... args) {
        String pattern = prop.getProperty(key);
        return MessageFormat.format(pattern, args);
    }

    public static void main(String[] args) {
        System.out.println(MessageUtil.format("param.error.minLength", "userName", 100));
    }
}
