package cache;


import java.util.Optional;

public class SelfManagedCacheDefault<V> implements SelfManagedCache<V> {
    private CacheBuffer<V> cacheBuffer;
    private SelectionStrategy selectionStrategy;

    public SelfManagedCacheDefault(CacheBuffer<V> cacheBuffer, SelectionStrategy selectionStrategy) {
        this.cacheBuffer = cacheBuffer;
        this.selectionStrategy = selectionStrategy;
    }

    public Optional<V> getNext() {
        Integer nextElementKey = selectionStrategy.getNextElementKey();
        return cacheBuffer.getAndRemove(nextElementKey);
    }
}
