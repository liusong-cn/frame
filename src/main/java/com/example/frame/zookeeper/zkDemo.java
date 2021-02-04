package com.example.frame.zookeeper;

import org.apache.zookeeper.KeeperException;

/**
 * @author: ls
 * @date: 2021/2/4 16:37
 **/
public class zkDemo {

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZkClientDemo demo = new ZkClientDemo();
        demo.run("/zkPro/zkSub1");
    }
}
