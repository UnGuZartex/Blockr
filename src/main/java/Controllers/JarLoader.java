package Controllers;

import GameWorldAPI.GameWorldType.GameWorldType;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

public class JarLoader {

    public GameWorldType load() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, MalformedURLException {
        Class c = Class.forName(System.getProperty("GameWorldRootClass"));
        URL location = c.getResource('/' + c.getName().replace('.', '/') + ".class");
        String[] url = location.toString().split("/");
        String jar = url[url.length-3].split("-")[0];
        System.setProperty("GameWorldJar", jar);
        return (GameWorldType) c.getConstructor().newInstance();
    }
}
