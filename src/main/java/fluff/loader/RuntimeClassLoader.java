package fluff.loader;

import java.io.File;
import java.net.MalformedURLException;

import fluff.loader.loaders.ResourceLoader;
import fluff.loader.resources.ClassResource;
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
	
	public boolean addJar(File file) {
		try {
			return resourceLoader.getResourcePath().add(new JarResource(file.toURI().toURL()));
		} catch (MalformedURLException e) {}
		return false;
	}
	
	public boolean addClass(String name, File file) {
		try {
			return resourceLoader.getResourcePath().add(new ClassResource(name, file.toURI().toURL()));
		} catch (MalformedURLException e) {}
		return false;
	}
	
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
}
