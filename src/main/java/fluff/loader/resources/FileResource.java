package fluff.loader.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import fluff.loader.AbstractNamedResource;

/**
 * Represents a file resource.
 */
public class FileResource extends AbstractNamedResource {
    
	protected final File file;
	
    /**
     * Constructs a FileResource with the specified name and file.
     * 
     * @param name the name of the resource
     * @param file the file resource
     */
    public FileResource(String name, File file) {
        super(name);
        
        this.file = file;
    }
    
	@Override
	public boolean init() {
		return true;
	}
	
	@Override
	public URL getURL() {
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {}
		
		return null;
	}
	
	@Override
	public InputStream openInputStream() {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {}
		
		return null;
	}
	
	/**
	 * Returns the file resource.
	 * 
	 * @return the file resource
	 */
	public File getFile() {
		return file;
	}
}
