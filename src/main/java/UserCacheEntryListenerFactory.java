import javax.cache.configuration.Factory;
import javax.cache.event.CacheEntryListener;

public class UserCacheEntryListenerFactory
        implements Factory<CacheEntryListener<Integer, String>> {

    private CachedCardProvider cachedCardProvider;

    public UserCacheEntryListenerFactory(CachedCardProvider cachedCardProvider) {
        this.cachedCardProvider = cachedCardProvider;
    }

    public CacheEntryListener<Integer, String> create() {
        return new UserCacheEntryListener(cachedCardProvider);
    }
}