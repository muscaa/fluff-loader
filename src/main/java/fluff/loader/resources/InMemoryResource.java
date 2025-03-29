package fluff.loader.resources;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

import fluff.loader.AbstractNamedResource;

/**
 * Represents an in memory resource.
 */
public class InMemoryResource extends AbstractNamedResource {
	
	protected final byte[] data;
	
    /**
     * Constructs an InMemoryResource with the specified name and data.
     *
     * @param name the name of the resource
     * @param data the data of the resource
     */
	public InMemoryResource(String name, byte[] data) {
		super(name);
		
		this.data = data;
	}
	
    @Override
    public boolean init() {
        return true;
    }
    
    @Override
    public URL getURL() {
    	return null;
    }
    
    @Override
    public InputStream openInputStream() {
    	return new ByteArrayInputStream(data);
    }
    
    /**
     * Returns the data of the resource.
     * 
     * @return the data of the resource
     */
    public byte[] getData() {
		return data;
	}
}
