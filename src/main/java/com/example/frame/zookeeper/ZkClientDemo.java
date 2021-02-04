package com.example.frame.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: ls
 * @date: 2021/2/4 16:12
 **/
public class ZkClientDemo implements Watcher {

    private static CountDownLatch latch = new CountDownLatch(1);

    private static ZooKeeper zk;

    private static Stat stat = new Stat();

    static {
        try {
            zk = new ZooKeeper("47.108.133.131:2181", 5000, new ZkClientDemo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){//zk成功连接
            if(Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()){
                latch.countDown();
            }else if(watchedEvent.getType() == Event.EventType.NodeDataChanged){//节点内容变化
                try {
                    System.out.println("配置已变化，新值为:" + new String(zk.getData(watchedEvent.getPath(),true, stat)));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run(String path) throws InterruptedException, KeeperException {
        latch.await();
        System.out.println(new String(zk.getData(path, true, stat)));
        Thread.sleep(Integer.MAX_VALUE);
    }
}
