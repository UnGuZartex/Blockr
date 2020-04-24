package Controllers;

import GameWorldAPI.GameWorldType.GameWorldType;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class JarLoader {

    public GameWorldType load() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, MalformedURLException {
        URL[] urls = {new URL("jar:file:" + System.getenv("GameWorldFileLocation") + "!/")};
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        Class c = Class.forName(System.getenv("GameWorldRootClass"), true, cl);

        return (GameWorldType) c.getConstructor().newInstance();
    }
}
