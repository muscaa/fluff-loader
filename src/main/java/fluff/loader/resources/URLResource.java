package fluff.loader.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import fluff.loader.AbstractNamedResource;

/**
 * Represents an url resource.
 */
public class URLResource extends AbstractNamedResource {
	
	protected final URL url;
	
    /**
     * Constructs an URLResource with the specified name and URL.
     *
     * @param name the name of the resource
     * @param url the URL of the resource
     */
	public URLResource(String name, URL url) {
		super(name);
		
		this.url = url;
	}
	
    @Override
    public boolean init() {
        return true;
    }
    
    @Override
    public URL getURL() {
    	return url;
    }
    
    @Override
    public InputStream openInputStream() {
    	try {
			return url.openStream();
		} catch (IOException e) {}
    	
    	return null;
    }
}
