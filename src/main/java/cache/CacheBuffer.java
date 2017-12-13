package cache;

import java.util.Optional;

public interface CacheBuffer<V> {
    Optional<V> getAndRemove(Integer key);
    void insertIfEmpty(Integer key, V value);
    Integer getElementsCount();
    void attach(CacheBufferListener listener);
    void detach(CacheBufferListener listener);
    void notifyElementRemoved();
}
