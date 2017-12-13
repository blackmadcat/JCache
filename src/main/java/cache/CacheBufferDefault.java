package cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;

public class CacheBufferDefault<V>  extends Observable implements CacheBuffer<V> {
    private Map<Integer, V> buffer = new HashMap<>();

    @Override
    public Optional<V> getAndRemove(Integer key) {
        Optional<V> result = Optional.ofNullable(buffer.get(key));
        result.ifPresent(x->this.notifyObservers());
        return result;
    }

    @Override
    public void insertIfEmpty(Integer key, V value) {

    }

    @Override
    public Integer getElementsCount() {
        return null;
    }
}
