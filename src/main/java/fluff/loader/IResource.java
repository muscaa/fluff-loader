package fluff.loader;

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
     * Retrieves the content of the resource as a byte array with the specified name.
     *
     * @param name the name of the resource
     * @return the content of the resource as a byte array, or null if the resource is not found
     */
    byte[] getBytes(String name);
}
