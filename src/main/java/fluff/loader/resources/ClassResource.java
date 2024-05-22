package fluff.loader.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import fluff.loader.IResource;

public class ClassResource implements IResource {
	
	private final String name;
	private final URL url;
	
	private byte[] content;
	
	public ClassResource(String name, URL url) {
		this.name = name;
		this.url = url;
	}
	
	@Override
	public boolean load() {
		try (InputStream is = url.openStream()) {
			content = is.readAllBytes();
			return true;
		} catch (IOException e) {}
		
		return false;
	}
	
	@Override
	public URL getURL(String name) {
		return this.name.equals(name) ? url : null;
	}
	
	@Override
	public byte[] getBytes(String name) {
		return this.name.equals(name) ? content : null;
	}
}
