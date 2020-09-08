package com.example.dateSourceBuilder;

import com.example.ZooKeeper.Node;
import org.apache.zookeeper.KeeperException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        try {
            Node node = new Node();
            node.createConnection();
            String url = node.getUrl();
            String username = node.getUser();
            String password = node.getPassWord();
            dataSourceBuilder.url(url);
            dataSourceBuilder.username(username);
            dataSourceBuilder.password(password);

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSourceBuilder.build();
    }
}
