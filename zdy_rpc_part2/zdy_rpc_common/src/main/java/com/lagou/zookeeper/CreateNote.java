package com.lagou.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;


public class CreateNote {
    private ZooKeeper zk;
    public void getConnection(String url) throws Exception {
        if (zk == null) {
            zk = new ZooKeeper(url, 5000, new Watcher() {
                public void process(WatchedEvent event) {
                    System.out.println("Server Watcher：" + event.getType() + "---" + event.getPath());
                }
            });
        }
    }
    public ZooKeeper getZk() {
        return zk;
    }
    public void registerServer(String server) throws Exception {
        Stat s = zk.exists("/servers", false);
        if (s == null) {
            zk.create("/servers", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        }
            String msg = zk.create(server, server.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("registerServer：" + msg);
    }

    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren(path, true);
        return children;
    }

    public void registerTime(String info) throws Exception {

        Stat s = zk.exists("/times", false);
        if (s == null) {
            zk.create("/times", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        }
        String msg = zk.create(info, info.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("registerTime：" + msg);
    }
}
