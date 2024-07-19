package fluff.loader.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    	CompletionService<Map.Entry<String, IResource>> completion = new ExecutorCompletionService<>(executor);
    	
        int files = load(completion, "", folder);
        
        for (int i = 0; i < files; i++) {
        	try {
        		Map.Entry<String, IResource> e = completion.take().get();
        		if (e == null) throw new InterruptedException("Something went wrong!");
        		
        		contents.put(e.getKey(), e.getValue());
			} catch (InterruptedException | ExecutionException e) {
				executor.shutdownNow();
				return false;
			}
        }
        
        executor.shutdown();
        
        return true;
    }
    
    /**
     * Recursively loads the contents of the specified folder.
     *
     * @param completion the completion service to use
     * @param path the path within the folder
     * @param file the file to be loaded
     * @return the number of files
     */
    protected int load(CompletionService<Map.Entry<String, IResource>> completion, String path, File file) {
    	int files = 0;
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                files += load(completion, path + f.getName() + "/", f);
                continue;
            }
            
            files++;
            completion.submit(() -> {
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
        }
        return files;
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
