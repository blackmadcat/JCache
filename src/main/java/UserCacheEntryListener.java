import javax.cache.event.*;
import java.util.Iterator;

public class UserCacheEntryListener
        implements CacheEntryCreatedListener<Integer, String>,
        CacheEntryUpdatedListener<Integer, String>,
        CacheEntryRemovedListener<Integer, String>,
        CacheEntryExpiredListener<Integer, String> {

    private CachedCardProvider cachedCardProvider;

    public UserCacheEntryListener(CachedCardProvider cachedCardProvider) {
        this.cachedCardProvider = cachedCardProvider;
    }

    private void printEvents(Iterable<CacheEntryEvent<? extends Integer, ? extends String>> iterable) {
        Iterator<CacheEntryEvent<? extends Integer, ? extends String>> iterator = iterable.iterator();
        while ( iterator.hasNext() ) {
            CacheEntryEvent<? extends Integer, ? extends String> event = iterator.next();
            System.out.println("EVENT: "+ event.getEventType() );
        }
    }

    public void onCreated(Iterable<CacheEntryEvent<? extends Integer, ? extends String>> iterable) throws CacheEntryListenerException {
        printEvents( iterable );
    }



    public void onExpired(Iterable<CacheEntryEvent<? extends Integer, ? extends String>> iterable) throws CacheEntryListenerException {
        printEvents( iterable );
    }

    public void onRemoved(Iterable<CacheEntryEvent<? extends Integer, ? extends String>> iterable) throws CacheEntryListenerException {
        printEvents(iterable);

        Iterator<CacheEntryEvent<? extends Integer, ? extends String>> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            CacheEntryEvent<? extends Integer, ? extends String> event = iterator.next();
            if (event.getEventType().equals(EventType.REMOVED)) {
                System.out.println("INFORM REMOVED... "+event.getKey());
                cachedCardProvider.removed(event.getKey());
            }
        }
    }


    public void onUpdated(Iterable<CacheEntryEvent<? extends Integer, ? extends String>> iterable) throws CacheEntryListenerException {
        printEvents( iterable );
    }
}


