package cache;

import java.util.Optional;

public interface SelfManagedCache<T> {
    Optional<T> getNext();
}
