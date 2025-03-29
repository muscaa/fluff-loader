package fluff.loader;

import java.io.File;
import java.net.URL;

import fluff.loader.loaders.ResourceLoader;
import fluff.loader.resources.ClassResource;
import fluff.loader.resources.FileResource;
import fluff.loader.resources.FolderResource;
import fluff.loader.resources.JarResource;
import fluff.loader.resources.URLResource;

/**
 * A class loader that allows loading classes, jars, files, and folders at runtime.
 * Extends {@link AbstractClassLoader} and provides methods to add different types of resources.
 */
public class RuntimeClassLoader extends AbstractClassLoader {
    
    private final ResourceLoader resourceLoader = new ResourceLoader(getCurrentLoader().getPriority() + 10);
    
    /**
     * Constructs a new RuntimeClassLoader with the specified parent class loader.
     *
     * @param parent the parent class loader
     */
    public RuntimeClassLoader(ClassLoader parent) {
        super(parent);
        
        addLoader(resourceLoader);
    }
    
    /**
     * Constructs a new RuntimeClassLoader with the system class loader as its parent.
     */
    public RuntimeClassLoader() {
        this(getSystemClassLoader());
    }
    
	/**
	 * Adds a resource to the resource loader.
	 *
	 * @param resource the resource to add
	 * @return true if the resource was added successfully, false otherwise
	 */
    public boolean addResource(IResource resource) {
    	return resourceLoader.getResourcePath().add(resource);
    }
    
    /**
     * Adds a jar file URL to the resource loader.
     *
     * @param jarUrl the URL of the jar file to add
     * @return true if the jar file was added successfully, false otherwise
     */
    public boolean addJar(URL jarUrl) {
        return addResource(new JarResource(false, jarUrl));
    }
    
    /**
     * Adds a jar file to the resource loader.
     *
     * @param jarFile the jar file to add
     * @return true if the jar file was added successfully, false otherwise
     */
    public boolean addJar(File jarFile) {
        return addResource(new JarResource(false, jarFile));
    }
    
    /**
     * Adds a class URL to the resource loader.
     *
     * @param name the name of the class
     * @param classUrl the URL of the class file
     * @return true if the class file was added successfully, false otherwise
     */
    public boolean addClass(String name, URL classUrl) {
    	return addResource(new ClassResource(name, classUrl));
    }
    
    /**
     * Adds a class file to the resource loader.
     *
     * @param name the name of the class
     * @param classFile the class file to add
     * @return true if the class file was added successfully, false otherwise
     */
    public boolean addClass(String name, File classFile) {
        return addResource(new ClassResource(name, classFile));
    }
    
    /**
     * Adds a file URL to the resource loader.
     *
     * @param name the name of the file
     * @param fileUrl the URL of the file
     * @return true if the file was added successfully, false otherwise
     */
    public boolean addFile(String name, URL fileUrl) {
        return addResource(new URLResource(name, fileUrl));
    }
    
    /**
     * Adds a file with a specified name to the resource loader.
     *
     * @param name the name of the file
     * @param file the file to add
     * @return true if the file was added successfully, false otherwise
     */
    public boolean addFile(String name, File file) {
        return addResource(new FileResource(name, file));
    }
    
    /**
     * Adds a folder to the resource loader.
     *
     * @param folder the folder to add
     * @return true if the folder was added successfully, false otherwise
     */
    public boolean addFolder(File folder) {
        return addResource(new FolderResource(false, folder));
    }
    
    /**
     * Returns the resource loader used by this class loader.
     *
     * @return the resource loader
     */
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
