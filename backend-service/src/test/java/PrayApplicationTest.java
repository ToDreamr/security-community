import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.pray.SecurityApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLClassLoader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;

/**
 * PrayApplicationTest
 *
 * @author 春江花朝秋月夜
 * @since 2023/11/4 16:44
 */
@SpringBootTest(classes = SecurityApplication.class)
@ComponentScan(basePackages = "com.pray.*")
public class PrayApplicationTest {
    private static final DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void classLoader() {
        Cache<Object, Object> cache = Caffeine.newBuilder().build();
        cache.put("2024-7-12","14:21 afternoon");

        System.out.println(cache.getIfPresent("2024-7-12"));
        //// 优先根据key查询JVM缓存，如果未命中，则执行参数二的Lambda表达式
        Object cacheValue = cache.get("2024", key -> {
            //查询JVM缓存失败，执行DB策略
            return "Trying to find the cache of key but failed,now turning to find of which in DB";
        });
        System.out.println(cacheValue);
        //默认情况下，当一个缓存元素过期的时候，Caffeine不会自动立即将其清理和驱逐。
        // 而是在一次读或写操作后，或者在空闲时间完成对失效数据的驱逐。
    }
    @Test
    public void resource() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(URLClassLoader.
                getSystemResource("custom-mail.html").getFile()));
        while (reader.readLine()!=null){
            System.out.println(reader.readLine());
        }
    }
    @Test
    public void array_DeQueue_Test(){
        ArrayDeque<String> deque = new ArrayDeque<>(),min;
        deque.addLast("尾部元素");
        deque.addFirst("头部元素");

        min = new ArrayDeque<>(Arrays.asList("尾部元素", "哈哈"));

        while (!deque.isEmpty()&&!min.isEmpty()){
            System.out.println(deque.pollFirst());
            System.out.println(min.pollFirst());
        }
    }
}
