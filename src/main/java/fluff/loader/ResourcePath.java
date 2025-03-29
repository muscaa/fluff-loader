package fluff.loader;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of resources and provides methods to add resources and retrieve data from them.
 */
public class ResourcePath {
    
    protected final List<IResource> resources = new ArrayList<>();
    
    /**
     * Retrieves the URLs of a resources by name.
     *
     * @param list the list where to store the resources
     * @param name the name of the resources
     */
    public void getURLs(List<URL> list, String name) {
    	for (IResource r : resources) {
    		r.getURLs(list, name);
    	}
    }
    
    /**
     * Retrieves the URL of a resource by name.
     *
     * @param name the name of the resource
     * @return the URL of the resource, or null if not found
     */
    public URL getURL(String name) {
        for (IResource r : resources) {
            URL url = r.getURL(name);
            
            if (url != null) return url;
        }
        return null;
    }
    
    /**
     * Opens an InputStream for a resource by name.
     *
     * @param name the name of the resource
     * @return an InputStream for the resource, or null if not found
     */
    public InputStream openInputStream(String name) {
        for (IResource r : resources) {
            InputStream is = r.openInputStream(name);
            
            if (is != null) return is;
        }
        return null;
    }
    
    /**
     * Adds a resource to the resource path.
     *
     * @param resource the resource to add
     * @return true if the resource was added successfully, false otherwise
     */
    public boolean add(IResource resource) {
        if (resource.init()) {
            resources.add(resource);
            return true;
        }
        return false;
    }
    
    /**
     * Retrieves the list of resources.
     * 
     * @return the list of resources
     */
    public List<IResource> getResources() {
		return resources;
	}
}
