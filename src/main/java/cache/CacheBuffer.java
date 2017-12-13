package cache;

import java.util.Observable;
import java.util.Optional;

public interface CacheBuffer<V> {
    Optional<V> getAndRemove(Integer key);

    void insertIfEmpty(Integer key, V value);

    Integer getElementsCount();
}
