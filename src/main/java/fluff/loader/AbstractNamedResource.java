package fluff.loader;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Represents a named resource.
 */
public abstract class AbstractNamedResource implements IResource {
	
	protected final String name;
	
	/**
	 * Constructs an AbstractNamedResource with the specified name.
	 * 
	 * @param name the name of the resource
	 */
	public AbstractNamedResource(String name) {
		this.name = name;
	}
	
    @Override
    public void getURLs(List<URL> list, String name) {
    	URL url = getURL(name);
    	
    	if (url != null) {
    		list.add(url);
    	}
    }
    
    @Override
    public URL getURL(String name) {
        return this.name.equals(name) ? getURL() : null;
    }
    
    @Override
    public InputStream openInputStream(String name) {
    	if (!this.name.equals(name)) return null;
    	
        return openInputStream();
    }
    
    /**
     * Gets the URL of the resource.
     * 
     * @return the URL of the resource
     */
	public abstract URL getURL();
	
	/**
	 * Opens an input stream to the resource.
	 * 
	 * @return an input stream to the resource
	 */
	public abstract InputStream openInputStream();
    
    /**
     * Gets the name of the resource.
     * 
     * @return the name of the resource
     */
    public String getName() {
		return name;
	}
}
