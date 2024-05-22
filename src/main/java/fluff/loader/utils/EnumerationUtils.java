package fluff.loader.utils;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class EnumerationUtils {
	
	public static <V> Enumeration<V> iterator(Iterator<V> iterator) {
		return new Enumeration<V>() {
			
			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}
			
			@Override
			public V nextElement() {
				return iterator.next();
			}
		};
	}
	
	public static <V> Enumeration<V> empty() {
		return new Enumeration<V>() {
			
			@Override
			public boolean hasMoreElements() {
				return false;
			}
			
			@Override
			public V nextElement() {
				throw new NoSuchElementException();
			}
		};
	}
}
