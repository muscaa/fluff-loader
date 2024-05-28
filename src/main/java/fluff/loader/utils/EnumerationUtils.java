package fluff.loader.utils;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Utility methods for working with enumerations.
 */
public class EnumerationUtils {
    
    /**
     * Converts an iterator into an enumeration.
     *
     * @param iterator the iterator to convert
     * @param <V> the type of elements returned by the iterator
     * @return an enumeration backed by the specified iterator
     */
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
    
    /**
     * Returns an empty enumeration.
     *
     * @param <V> the type of elements in the enumeration (irrelevant as it's empty)
     * @return an empty enumeration
     * @throws NoSuchElementException if the enumeration has no more elements
     */
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
