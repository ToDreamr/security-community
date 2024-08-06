package com.pray.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Order(Ordered.LOWEST_PRECEDENCE)
public class CustomEnvironmentPostConfig implements EnvironmentPostProcessor {
    public static final String TABLE_NAME = "tb_sys";
    public static final String SQL = "select * from `backend-base`.tb_sys";

    public static final String sqlPath = "";
    private static final String DATABASE = "backend-base";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            Map<String, Object> map = new HashMap<>();

            String username = environment.getProperty("spring.datasource.username");
            String password = environment.getProperty("spring.datasource.password");
            String url = environment.getProperty("spring.datasource.url");
            System.out.println(url);
            String driver = environment.getProperty("spring.datasource.driver-class-name");
            Class.forName(driver);
            assert url != null;
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                //初始化数据库
                initDb(connection);
                //加载配置文件
                try (Statement statement = connection.createStatement()) {
                    try (ResultSet resultSet = statement.executeQuery(SQL)) {
                        while (resultSet.next()) {
                            map.put(resultSet.getString("config_key"), resultSet.getString("config_value"));
                        }
                    }
                }
            }

            MutablePropertySources propertySources = environment.getPropertySources();
            PropertySource<?> source = new MapPropertySource(TABLE_NAME, map);
            propertySources.addFirst(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDb(Connection connection) throws SQLException,IOException{
        try (Statement statement = connection.createStatement()){
            try (ResultSet set = statement.executeQuery("Show databases like'"+DATABASE+"'")){
                if (!set.next()){
                    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
                    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                    populator.addScripts(resolver.getResources(sqlPath));
                    populator.populate(connection);
                }
            }
        }
    }
}
