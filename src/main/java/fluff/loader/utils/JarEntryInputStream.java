package fluff.loader.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;

/**
 * A simple InputStream that reads from a JarEntry in a JarFile.
 */
public class JarEntryInputStream extends InputStream {
	
	protected volatile JarFile jar;
	protected volatile InputStream in;
	
	/**
	 * Creates a new JarEntryInputStream.
	 * 
	 * @param jar the JarFile to read from
	 * @param entryName the name of the entry to read from
	 * @throws IOException if an I/O error occurs
	 */
	public JarEntryInputStream(JarFile jar, String entryName) throws IOException {
		this.jar = jar;
		this.in = jar.getInputStream(jar.getEntry(entryName));
	}
	
	/**
	 * Creates a new JarEntryInputStream.
	 * 
	 * @param zipFile the File to read from
	 * @param entryName the name of the entry to read from
	 * @throws IOException if an I/O error occurs
	 */
	@SuppressWarnings("resource")
	public JarEntryInputStream(File zipFile, String entryName) throws IOException {
		this(new JarFile(zipFile), entryName);
	}
	
	@Override
    public int read() throws IOException {
        return in.read();
    }
    
	@Override
    public int read(byte b[]) throws IOException {
        return in.read(b);
    }
    
	@Override
    public int read(byte b[], int off, int len) throws IOException {
        return in.read(b, off, len);
    }
    
	@Override
    public long skip(long n) throws IOException {
        return in.skip(n);
    }
    
	@Override
    public int available() throws IOException {
        return in.available();
    }
    
	@Override
    public void close() throws IOException {
        in.close();
        jar.close();
    }
    
	@Override
    public synchronized void mark(int readlimit) {
        in.mark(readlimit);
    }
    
	@Override
    public synchronized void reset() throws IOException {
        in.reset();
    }
    
	@Override
    public boolean markSupported() {
        return in.markSupported();
    }
}
