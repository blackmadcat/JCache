package cache;

import org.junit.Before;
import org.junit.Test;

import java.util.Observable;

import static org.assertj.core.api.Assertions.assertThat;


public class CacheBufferTest  {

    private CacheBuffer<String> stringCacheBuffer;

    @Before
    public void init() {
        stringCacheBuffer = new CacheBufferDefault<>();
    }

    @Test
    public void test() {
        assertThat(stringCacheBuffer).isNotNull();
    }
}