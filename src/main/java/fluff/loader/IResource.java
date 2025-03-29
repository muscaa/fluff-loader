package fluff.loader;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Represents a generic resource that can be loaded and accessed.
 */
public interface IResource {
    
    /**
     * Initializes the resource.
     *
     * @return true if the resource was successfully initialized, false otherwise
     */
    boolean init();
    
    /**
     * Retrieves the URLs of the resources with the specified name.
     *
     * @param list the list where to store the resource urls
     * @param name the name of the resources
     */
    void getURLs(List<URL> list, String name);
    
    /**
     * Retrieves the URL of the resource with the specified name.
     *
     * @param name the name of the resource
     * @return the URL of the resource, or null if the resource is not found
     */
    URL getURL(String name);
    
    /**
     * Opens an InputStream for the resource with the specified name.
     *
     * @param name the name of the resource
     * @return an InputStream for the resource, or null if the resource was not found
     */
    InputStream openInputStream(String name);
}
