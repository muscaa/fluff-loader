package fluff.loader.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import fluff.loader.AbstractContentsResource;

/**
 * Represents a folder resource.
 */
public class FolderResource extends AbstractContentsResource {
    
	protected final boolean preload;
	protected final File folder;
	
	/**
	 * Constructs a FolderResource with the specified preload value and folder.
	 * 
	 * @param preload whether to preload the classes in the jar (better
	 * performance at the cost of an overhead and higher memory usage)
	 * @param folder the folder
	 */
	public FolderResource(boolean preload, File folder) {
		this.preload = preload;
		this.folder = folder;
	}
	
	@Override
	public boolean init() {
		Path basePath = folder.toPath();
		
		try (Stream<Path> paths = Files.walk(basePath).parallel()) {
            return paths.allMatch(path -> {
            	File file = path.toFile();
            	if (file.isDirectory()) return true;
            	
                String name = basePath.relativize(path).toString().replace('\\', '/');
                
                return load(name, file);
            });
		} catch (IOException e) {}
		
		return false;
	}
	
	protected boolean load(String name, File file) {
		if (!name.endsWith(".class")) {
			return register(name, new FileResource(name, file));
		}
		
		if (!preload) {
			return register(name, new ClassResource(name, file));
		}
		
		try (InputStream is = new FileInputStream(file)) {
			byte[] data = is.readAllBytes();
			
			return register(name, new ClassResource(name, data));
		} catch (Exception e) {}
		
		return false;
	}
}
