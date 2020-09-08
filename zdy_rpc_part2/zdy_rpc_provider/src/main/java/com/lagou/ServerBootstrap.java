package com.lagou;

import com.lagou.service.UserServiceImpl;
import com.lagou.zookeeper.CreateNote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerBootstrap {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(ServerBootstrap.class, args);

        UserServiceImpl.startServer("127.0.0.1",8990);

        CreateNote cn = new CreateNote();
        cn.getConnection("127.0.0.1:2181");
        cn.registerServer("/servers/" + "127.0.0.1:8990");
    }



}
