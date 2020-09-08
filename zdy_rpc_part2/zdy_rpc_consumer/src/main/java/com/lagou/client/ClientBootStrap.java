package com.lagou.client;

import com.lagou.service.UserService;
import com.lagou.zookeeper.CreateNote;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ClientBootStrap {

   // public static  final String providerName="UserService#sayHello#";

    public static void main(String[] args) throws Exception {

        RpcConsumer rpcConsumer = new RpcConsumer();
        CreateNote cn = new CreateNote();
        cn.getConnection( "127.0.0.1:2181");
        while (true) {

              List<String> children = cn.getChildren("/servers");
              List<String> times = new ArrayList<>();
              ZooKeeper zk = cn.getZk();
              Stat s = zk.exists("/times", false);
              if (s != null) {
                  times = cn.getChildren("/times");
              }
              Integer port = null;
              if (times.size() > 0 ) {
                   long time = 0;
                   for (String element : times) {
                        String val[] = element.split("_");
                        long curT = Long.valueOf(val[1]);
                        SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                        Date currentT = new Date();
                        Date dateT = timeStamp.parse(val[2]);
                        long difference = currentT.getTime() - dateT.getTime();
                        if (difference >= 5000) {
                            zk.delete("/times/" + element, -1);
                            continue;
                        }
                        if (time == 0 || curT < time) {
                            port = Integer.valueOf(val[0]);
                            time = curT;
                        }
                   }
                  UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class, "127.0.0.1", port);
                  Thread.sleep(2000);
                  String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                  proxy.sayHello("are you ok?_" + timeStamp);
                  System.out.println("已响应");
              }
              if (port == null) {
                  for (String element : children) {
                      String hostName = element.split(":")[0];
                      port = Integer.valueOf(element.split(":")[1].substring(0, 4));
                      UserService proxy = (UserService) rpcConsumer.createProxy(UserService.class, hostName, port);
                      Thread.sleep(2000);
                      String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                      proxy.sayHello("are you ok?_" + timeStamp);
                      System.out.println("已响应");
                  }
              }

        }


    }




}
