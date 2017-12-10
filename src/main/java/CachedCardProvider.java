
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.concurrent.TimeUnit;

public class CachedCardProvider {

    private Integer valueCounter = 0;

    private CounterSolution counter;

    private final int bufferLength;
    private Cache<Integer, String> entries;

    public CachedCardProvider(int bufferLength, CounterSolution counter) {
        this.bufferLength = bufferLength;
        this.counter = counter;
        initCache();
    }

    public String getNext() {
        Integer no = counter.getAndUpdateCounter();
        return entries.getAndRemove(no);
    }


    private void initCache() {
        entries = configureCache();
        loadFromDatasource();

    }

    private Cache<Integer, String> configureCache() {
        // explicitly retrieve the Hazelcast backed javax.cache.spi.CachingProvider
        CachingProvider cachingProvider = Caching.getCachingProvider(
                "com.hazelcast.cache.HazelcastCachingProvider"
        );

        // retrieve the javax.cache.CacheManager
        CacheManager cacheManager = cachingProvider.getCacheManager();

        // create javax.cache.configuration.CompleteConfiguration subclass
        CompleteConfiguration<Integer, String> config =
                new MutableConfiguration<Integer, String>()
                        // configure the cache to be typesafe
                        .setTypes(Integer.class, String.class)
                        // configure to expire entries 30 secs after creation in the cache
                        .setExpiryPolicyFactory(FactoryBuilder.factoryOf(
                                new AccessedExpiryPolicy(new Duration(TimeUnit.SECONDS, 30))
                        ))
                        // configure read-through of the underlying store
                        .setReadThrough(false)
                        // configure write-through to the underlying store
                        .setWriteThrough(false)
                        // configure the javax.cache.integration.CacheLoader
/*                        .setCacheLoaderFactory(FactoryBuilder.factoryOf(
                                new UserCacheLoader(userDao)
                        ))
                        // configure the javax.cache.integration.CacheWriter
                        .setCacheWriterFactory(FactoryBuilder.factoryOf(
                                new UserCacheWriter(userDao)
                        ))*/
                        // configure the javax.cache.event.CacheEntryListener with no javax.cache.event.CacheEntryEventFilter,
                        // to include old value and to be executed synchronously
                        .addCacheEntryListenerConfiguration(
                                new MutableCacheEntryListenerConfiguration<Integer, String>(
                                        new UserCacheEntryListenerFactory(this),
                                        null, false, false
                                )
                        );

        // create the cache called "users" and using the previous configuration
        return cacheManager.createCache("test", config);
    }

    public void loadFromDatasource() {
        System.out.printf("LOADING...");

        //entries.deregisterCacheEntryListener(UserCacheEntryListener);

        for (int i=0; i < this.bufferLength; i++) {
            valueCounter++;
            entries.put(i, valueCounter.toString());
        }
    }

    public void removed(Integer key) {
        if (key == bufferLength-1) {
            this.loadFromDatasource();
        }
    }
}
