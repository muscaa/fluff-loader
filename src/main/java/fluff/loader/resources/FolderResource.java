package fluff.loader.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import fluff.loader.IResource;

public class FolderResource implements IResource {
	
	private final Map<String, byte[]> contents = new HashMap<>();
	
	private final File folder;
	private final URL url;
	
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
	
	private void load(String path, File file) throws IOException {
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				load(path + f.getName() + "/", f);
				continue;
			}
			
			FileInputStream fis = new FileInputStream(f);
			contents.put(path + f.getName(), fis.readAllBytes());
			fis.close();
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
		return contents.containsKey(name) ? contents.get(name) : null;
	}
}
