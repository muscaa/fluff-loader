package fluff.loader.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import fluff.loader.IResource;

/**
 * Represents a folder resource that can be loaded from a directory.
 */
public class FolderResource implements IResource {
    
    private final Map<String, byte[]> contents = new HashMap<>();
    
    private final File folder;
    private final URL url;
    
    /**
     * Constructs a FolderResource with the specified folder.
     *
     * @param folder the folder to be used as a resource
     * @throws MalformedURLException if the folder's URL is malformed
     */
    public FolderResource(File folder) throws MalformedURLException {
        this.folder = folder;
        this.url = folder.toURI().toURL();
    }
    
    @Override
    public boolean load() {
        try {
            load("", folder);
            return true;
        } catch (IOException e) {}
        
        return false;
    }
    
    /**
     * Recursively loads the contents of the specified folder.
     *
     * @param path the path within the folder
     * @param file the file to be loaded
     * @throws IOException if an I/O error occurs
     */
    protected void load(String path, File file) throws IOException {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                load(path + f.getName() + "/", f);
                continue;
            }
            
            try (FileInputStream fis = new FileInputStream(f)) {
                contents.put(path + f.getName(), fis.readAllBytes());
            }
        }
    }
    
    @Override
    public URL getURL(String name) {
        if (!contents.containsKey(name)) return null;
        
        try {
            return new URL(url, name);
        } catch (MalformedURLException e) {}
        
        return null;
    }
    
    @Override
    public byte[] getBytes(String name) {
        return contents.get(name);
    }
}
