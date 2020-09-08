package com.lagou.client;

import com.lagou.service.UserService;
import com.lagou.zookeeper.CreateNote;

import java.util.List;


public class ClientBootStrap {

   // public static  final String providerName="UserService#sayHello#";

    public static void main(String[] args) throws Exception {

        RpcConsumer rpcConsumer = new RpcConsumer();

        while (true) {
              CreateNote cn = new CreateNote();
              cn.getConnection( "127.0.0.1:2181");
              List<String> children = cn.getChildren("/servers");
              for (String element : children) {
                  String hostName = element.split(":")[0];
                  Integer port = Integer.valueOf(element.split(":")[1].substring(0, 4));
                  UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class, hostName, port);
                  Thread.sleep(2000);
                  proxy.sayHello("are you ok?");
                  System.out.println("已响应");
              }

        }


    }




}
