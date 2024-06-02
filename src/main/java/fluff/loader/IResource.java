package fluff.loader;

import java.io.InputStream;
import java.net.URL;

/**
 * Represents a generic resource that can be loaded and accessed.
 */
public interface IResource {
    
    /**
     * Loads the resource.
     *
     * @return true if the resource was successfully loaded, false otherwise
     */
    boolean load();
    
    /**
     * Retrieves the URL of the resource with the specified name.
     *
     * @param name the name of the resource
     * @return the URL of the resource, or null if the resource is not found
     */
    URL getURL(String name);
    
    /**
     * Retrieves an InputStream for the resource with the specified name.
     *
     * @param name the name of the resource
     * @return an InputStream for the resource, or null if the resource is not found
     */
    InputStream getInputStream(String name);
}
