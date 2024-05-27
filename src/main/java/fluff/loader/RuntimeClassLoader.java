package fluff.loader;

import java.io.File;
import java.net.MalformedURLException;

import fluff.loader.loaders.ResourceLoader;
import fluff.loader.resources.FileResource;
import fluff.loader.resources.FolderResource;
import fluff.loader.resources.JarResource;

public class RuntimeClassLoader extends AbstractClassLoader {
	
	public final ResourceLoader resourceLoader = new ResourceLoader(getCurrentLoader().getPriority() + 10);
	
	public RuntimeClassLoader(ClassLoader parent) {
		super(parent);
		
		addLoader(resourceLoader);
	}
	
	public RuntimeClassLoader() {
		this(getSystemClassLoader());
	}
	
	public boolean addJar(File jarFile) {
		try {
			return resourceLoader.getResourcePath().add(new JarResource(jarFile.toURI().toURL()));
		} catch (MalformedURLException e) {}
		return false;
	}
	
	public boolean addFile(String name, File file) {
		try {
			return resourceLoader.getResourcePath().add(new FileResource(name, file.toURI().toURL()));
		} catch (MalformedURLException e) {}
		return false;
	}
	
	public boolean addFolder(File folder) {
		try {
			return resourceLoader.getResourcePath().add(new FolderResource(folder));
		} catch (MalformedURLException e) {}
		return false;
	}
	
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
}
