package fluff.loader.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import fluff.loader.IResource;

/**
 * Represents a class resource that can be loaded from a URL.
 */
public class ClassResource implements IResource {
    
    private final String name;
    private final URL url;
    private byte[] content;
    
    /**
     * Constructs a new ClassResource with the specified name, URL, and content.
     *
     * @param name the name of the resource
     * @param url the URL of the resource
     * @param content the content of the resource as a byte array
     */
    public ClassResource(String name, URL url, byte[] content) {
        this.name = name;
        this.url = url;
        this.content = content;
    }
    
    /**
     * Constructs a new ClassResource with the specified name and URL.
     * The content is read from the URL.
     *
     * @param name the name of the resource
     * @param url the URL of the resource
     * @throws IOException if an I/O error occurs while reading from the URL
     */
    public ClassResource(String name, URL url) throws IOException {
        this.name = name;
        this.url = url;
        
        try (InputStream is = url.openStream()) {
            this.content = is.readAllBytes();
        }
    }
    
    @Override
    public boolean load() {
        return true;
    }
    
    @Override
    public URL getURL(String name) {
        return this.name.equals(name) ? url : null;
    }
    
    @Override
    public InputStream getInputStream(String name) {
        return this.name.equals(name) ? new ByteArrayInputStream(content) : null;
    }
}
