package com.pray.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * KaptChaConfig
 *
 * @author 春江花朝秋月夜
 * @since 2024/7/13 0:25
 */
@Configuration
public class KaptChaConfig {
    @Bean
    public Producer producer(){
        //配置图形验证码的Bean
        Properties properties=new Properties();
        properties.setProperty("kaptcha.image.width","150");
        properties.setProperty("kaptcha.image.height","50");
        properties.setProperty("kaptcha.textproducer.char.string","012345678");
        properties.setProperty("kaptcha.textproducer.char.length","4");

        Config config=new Config(properties);
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
