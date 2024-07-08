package fluff.loader.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fluff.loader.IResource;

/**
 * Represents a folder resource that can be loaded from a directory.
 */
public class FolderResource implements IResource {
    
    private final Map<String, IResource> contents = new HashMap<>();
    
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
    	ExecutorService executor = Executors.newCachedThreadPool();
    	List<Future<Map.Entry<String, IResource>>> results = new LinkedList<>();
    	
        load(executor, results, "", folder);
        
        for (Future<Map.Entry<String, IResource>> result : results) {
        	try {
        		Map.Entry<String, IResource> e = result.get();
        		if (e == null) return false;
        		
        		contents.put(e.getKey(), e.getValue());
			} catch (InterruptedException | ExecutionException e) {
				return false;
			}
        }
        
        executor.shutdown();
        
        return true;
    }
    
    /**
     * Recursively loads the contents of the specified folder.
     *
     * @param executor the executor service to use
     * @param results the list where to store the results
     * @param path the path within the folder
     * @param file the file to be loaded
     */
    protected void load(ExecutorService executor, List<Future<Map.Entry<String, IResource>>> results, String path, File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                load(executor, results, path + f.getName() + "/", f);
                continue;
            }
            
            Future<Map.Entry<String, IResource>> result = executor.submit(() -> {
                try (FileInputStream fis = new FileInputStream(f)) {
                	String name = path + f.getName();
                	URL url = new URL(this.url, name);
                	
                	IResource r = name.endsWith(".class") ?
                			new ClassResource(name, url, fis.readAllBytes())
                			: new FileResource(name, url);
                    
                    return new SimpleEntry<>(name, r);
				} catch (IOException e) {}
                return null;
            });
            
            results.add(result);
        }
    }
    
	@Override
	public URL getURL(String name) {
		return contents.containsKey(name) ? contents.get(name).getURL(name) : null;
	}
	
	@Override
	public InputStream getInputStream(String name) {
		return contents.containsKey(name) ? contents.get(name).getInputStream(name) : null;
	}
	
	@Override
	public void getURLs(List<URL> list, String name) {
    	for (Map.Entry<String, IResource> e : contents.entrySet()) {
    		e.getValue().getURLs(list, name);
    	}
	}
}
