package io.newblock.pluginex.util;

import io.newblock.pluginex.PluginEX;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util {

    static Map<String,String> lang = new HashMap<>();

    public static InputStream getResource(String path) {
        String pathx;
        if (path.startsWith("/")) pathx = path;
        else pathx = "/" + path;
        return Util.class.getResourceAsStream(pathx);
    }

    public static void createLangFile(File file,PluginEX instance){
        if(!file.exists()){
            InputStream in = Util.getResource(file.getName());
            try(BufferedOutputStream target = new BufferedOutputStream(new FileOutputStream(file))){
                file.createNewFile();
                byte[] buf = new byte[8192];
                int length;
                while ((length = in.read(buf)) > 0) {
                    target.write(buf, 0, length);
                }
            }catch (IOException io){
                instance.getLogger().warning("Cannot create language file.");
            }
        }
    }

    public static void loadLangInfo(File file, PluginEX instance){
        try(BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))){
            Properties properties = new Properties();
            properties.load(in);

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                lang.put((String) entry.getKey(), (String) entry.getValue());
            }
        }catch (IOException e){
            instance.getLogger().warning("Cannot load language file.");
        }
    }

    public static String format(String format){
        String msg = lang.get(format);
        return msg == null ? format : msg;
    }

    public static Field getClassField(Object object,String name){
        try {
            Field field = object.getClass().getField(name);
            field.setAccessible(true);
            return field;
        }catch (NoSuchFieldException e){
            return null;
        }
    }
}
