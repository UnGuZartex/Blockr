package Controllers.Utility;

import GameWorldAPI.GameWorldType.GameWorldType;

import java.net.URL;

public class JarLoader {

    /**
     * The current jar present in the project is loaded.
     *
     * @effect The gameWorldJar system property is set accordingly.
     *
     * @return The game world type present in the jar.
     *
     * @throws Exception
     *         When the jar couldn't be loaded due to problems regarding whether the jar was not found, the
     *         root class was not found or the root class failed initialisation.
     */
    public GameWorldType load() throws Exception {
        Class c = Class.forName(System.getProperty("GameWorldRootClass"));
        URL location = c.getResource('/' + c.getName().replace('.', '/') + ".class");
        String[] url = location.toString().split("/");
        String jar = url[url.length-3].split("-")[0].replace(".jar", "").replace("!", "");
        System.setProperty("GameWorldJar", jar);
        return (GameWorldType) c.getConstructor().newInstance();
    }
}
