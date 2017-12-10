import com.hazelcast.cache.ICache;
import com.hazelcast.config.CacheConfig;
import com.hazelcast.config.CacheSimpleConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICacheManager;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.*;
import javax.cache.event.CacheEntryListener;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.security.Key;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final int BUFFER_LENGTH = 100;

    public static void main(String[] args) {

        CachedCardProvider cachedCardProvider = new CachedCardProvider(BUFFER_LENGTH, new CounterSolution(0, BUFFER_LENGTH));


        for (int j = 0; j < 100000; j++) {
            System.out.println(cachedCardProvider.getNext());
        }


    }


}

