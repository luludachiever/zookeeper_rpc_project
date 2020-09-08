package com.example.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Node implements Watcher {

        private static ZooKeeper zooKeeper;

        public void createConnection() throws IOException, KeeperException, InterruptedException {
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 60000, this);
            writeData();
        }
        void writeData() throws KeeperException, InterruptedException {
            if (zooKeeper != null) {
                Stat stat = zooKeeper.exists("/url", false);
                if (stat == null) {
                    zooKeeper.create("/url", "jdbc:mysql://localhost:3306/user_db".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
                stat = zooKeeper.exists("/username", false);
                if (stat == null) {
                    zooKeeper.create("/username", "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
                stat = zooKeeper.exists("/password", false);
                if (stat == null)
                    zooKeeper.create("/password", "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }
        public String getUrl() throws KeeperException, InterruptedException {
                byte[] url = zooKeeper.getData("/url", false, null);
                return new String(url);
        }
        public String getUser() throws KeeperException, InterruptedException {
            byte[] name = zooKeeper.getData("/username", false, null);
            return new String(name);
        }
        public String getPassWord() throws KeeperException, InterruptedException {
            byte[] password = zooKeeper.getData("/password", false, null);
            return new String(password);
        }
    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected){

            //解除主程序在CountDownLatch上的等待阻塞
            System.out.println("process方法执行了...");
            // 创建节点

        }
    }
}
