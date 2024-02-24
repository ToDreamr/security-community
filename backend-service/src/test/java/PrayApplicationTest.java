import com.pray.SecurityApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.format.DateTimeFormatter;

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
    public void classLoader(){
//        long now=1705743526917L;
//        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String param=dateFormat.format(now);
//        Duration between = Duration.between(LocalDateTime.now(), LocalDateTime.parse(param,timeFormatter));
//        System.out.println(between.getSeconds());
//        System.out.println(TimeUnit.DAYS.convert(between));//转换为天数
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }
}
