package org.whale.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class AbortWatcher implements Watcher {

	@Override
	public void process(WatchedEvent event) {

		System.out.println("aaaaaaaaaaaaaaaaaa");

	}

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		ZooKeeper zk = new ZooKeeper("node1:2181", 3000, new AbortWatcher()); // 在创建ZooKeeper时第三个参数负责设置该类的默认构造函数
		zk.create("/whale-ha", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		
		
		
		
		
		
	}
	
}
