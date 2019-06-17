package org.whale.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class SelfWatcher implements Watcher {

	ZooKeeper zk = null;

	@Override
	public void process(WatchedEvent event) {
		System.out.println(event.toString());
	}

	SelfWatcher(String address) {
		try {
			zk = new ZooKeeper(address, 3000, this); // 在创建ZooKeeper时第三个参数负责设置该类的默认构造函数
			zk.create("/whale-ha", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		} catch (IOException e) {
			e.printStackTrace();
			zk = null;
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	void setWatcher() {
		try {
			Stat s = zk.exists("/whale-ha", true);
			if (s != null) {
				zk.getData("/whale-ha", false, s);
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	void trigeWatcher() {
		try {
			Stat s = zk.exists("/whale-ha", false); // 此处不设置watcher
			zk.setData("/whale-ha", "a".getBytes(), s.getVersion());// 修改数据时需要提供version
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void disconnect() {
		if (zk != null)
			try {
				zk.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {
		SelfWatcher inst = new SelfWatcher("node1:2181");
		inst.setWatcher();
		inst.trigeWatcher();
		inst.disconnect();
	}

}