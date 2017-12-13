package cache;

import org.junit.Before;
import org.junit.Test;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SelfManagedCacheTest {

    SelfManagedCache<String> selfManagedCache;
    CacheBuffer<String> cacheBuffer;
    private SelectionStrategy selectionStrategy;

    @Before
    public void init() {
        cacheBuffer = mock(CacheBuffer.class);
        selectionStrategy = mock(SelectionStrategy.class);
        selfManagedCache = new SelfManagedCacheDefault<>(cacheBuffer, selectionStrategy);
    }

    @Test
    public void shouldReturnElementFromBuffer() {

        when(cacheBuffer.getAndRemove(anyInt())).thenReturn(Optional.of("test"));

        //when
        Optional<String> actual = selfManagedCache.getNext();

        assertThat(actual).isInstanceOf(Optional.class)
                .contains("test");
    }

    @Test
    public void shouldUseStrategyToChoseElement() {

        when(cacheBuffer.getAndRemove(anyInt())).thenReturn(Optional.of("test"));

        //when
        selfManagedCache.getNext();

        verify(selectionStrategy).getNextElementKey();
    }



}
