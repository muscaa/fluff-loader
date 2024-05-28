package fluff.loader;

import java.io.File;
import java.net.MalformedURLException;

import fluff.loader.loaders.ResourceLoader;
import fluff.loader.resources.FileResource;
import fluff.loader.resources.FolderResource;
import fluff.loader.resources.JarResource;

/**
 * A class loader that allows loading classes, jars, files, and folders at runtime.
 * Extends {@link AbstractClassLoader} and provides methods to add different types of resources.
 */
public class RuntimeClassLoader extends AbstractClassLoader {
	
    /**
     * The resource loader used for loading resources.
     */
    public final ResourceLoader resourceLoader = new ResourceLoader(getCurrentLoader().getPriority() + 10);
    
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
     * Adds a jar file to the class loader.
     *
     * @param jarFile the jar file to add
     * @return true if the jar file was added successfully, false otherwise
     */
    public boolean addJar(File jarFile) {
        try {
            return resourceLoader.getResourcePath().add(new JarResource(jarFile.toURI().toURL()));
        } catch (MalformedURLException e) {
            return false;
        }
    }
    
    /**
     * Adds a file with a specified name to the class loader.
     *
     * @param name the name of the file
     * @param file the file to add
     * @return true if the file was added successfully, false otherwise
     */
    public boolean addFile(String name, File file) {
        try {
            return resourceLoader.getResourcePath().add(new FileResource(name, file.toURI().toURL()));
        } catch (MalformedURLException e) {
            return false;
        }
    }
    
    /**
     * Adds a folder to the class loader.
     *
     * @param folder the folder to add
     * @return true if the folder was added successfully, false otherwise
     */
    public boolean addFolder(File folder) {
        try {
            return resourceLoader.getResourcePath().add(new FolderResource(folder));
        } catch (MalformedURLException e) {
            return false;
        }
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
