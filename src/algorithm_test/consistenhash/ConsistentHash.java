package algorithm_test.consistenhash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * һ����hash�㷨
 * 
 * @author jianzijian
 *
 */
class ConsistentHash {

	// ����ڵ㼯�ϣ�ʹ��treemap��֤��ֵ���򣬷������
	private final TreeMap<Long, Machine> nodes = new TreeMap<>();
	// ��ʵ�ڵ㣬���ڽ�������ڵ㣬�Լ����ݲ��Ҵ洢
	private final List<Machine> machines = new ArrayList<>();
	// ÿ̨��ʵ����������Ľڵ���
	private final int VIRTUAL_NODE_COUNT = 4;
	// ��д�������ڻ���ɾ����������ɵĲ���
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * ����2^32�ѽڵ�ֲ���Բ�����档
	 * 
	 * @param digest
	 * @param nTime
	 * @return
	 */
	private long hash(byte[] digest, int nTime) {
		long rv = ((long) (digest[3 + nTime * 4] & 0xFF) << 24) | ((long) (digest[2 + nTime * 4] & 0xFF) << 16)
				| ((long) (digest[1 + nTime * 4] & 0xFF) << 8) | (digest[0 + nTime * 4] & 0xFF);

		return rv & 0xffffffffL; /* Truncate to 32-bits */
	}

	/**
	 * Get the md5 of the given key. ����MD5ֵ
	 */
	private byte[] computeMd5(String k) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 not supported", e);
		}
		md5.reset();
		byte[] keyBytes = null;
		try {
			keyBytes = k.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unknown string :" + k, e);
		}

		md5.update(keyBytes);
		return md5.digest();
	}

	private void init() {
		machines.add(new Machine("127.0.0.1", "machine number 1"));
		machines.add(new Machine("127.0.0.2", "machine number 2"));
		machines.add(new Machine("127.0.0.3", "machine number 3"));
		machines.add(new Machine("127.0.0.4", "machine number 4"));
		machines.forEach(machine -> {
			for (int virtualIndex = 0; virtualIndex < VIRTUAL_NODE_COUNT; virtualIndex++) {
				// ����
				nodes.put(this.hash(this.computeMd5(machine.name + "#" + virtualIndex), 0), machine);
			}
		});
	}

	private Machine lookupNode(long hash) {
		lock.readLock().lock();
		try {
			// �ҵ���ֵ��ָ��ֵ��Ľڵ�
			SortedMap<Long, Machine> sortedMap = nodes.tailMap(hash);
			if (sortedMap.isEmpty()) {
				return nodes.get(nodes.firstKey());
			} else {
				return nodes.get(sortedMap.firstKey());
			}
		} finally {
			lock.readLock().unlock();
		}
	}

	private void remove(Machine machine) {
		lock.writeLock().lock();
		try {
			for (int virtualIndex = 0; virtualIndex < VIRTUAL_NODE_COUNT; virtualIndex++) {
				nodes.remove(this.hash(this.computeMd5(machine.name + "#" + virtualIndex),
						0));
			}
			machines.remove(machine);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	private void add(Machine machine){
		lock.writeLock().lock();
		try {
			for (int virtualIndex = 0; virtualIndex < VIRTUAL_NODE_COUNT; virtualIndex++) {
				nodes.put(this.hash(this.computeMd5(machine.name + "#" + virtualIndex),
						virtualIndex),machine);
			}
			machines.add(machine);
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	private ConsistentHash(){
		
	}

	public static void main(String[] args) {
		ConsistentHash consistentHash=new ConsistentHash();
		consistentHash.init();
		new Thread(){
			public void run() {
				for (int value = 0; value < 40; value++) {
					// �˴��о���Ӧ��ʹ�������������û�а취��֤ͬһ����������hash��ֵ��һ�µģ�ȡ������ô����
					Machine machine = consistentHash
							.lookupNode(consistentHash.hash(consistentHash.computeMd5(String.valueOf(value)), 0));
					System.out.println(machine.des);
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		new Thread(){
			public void run() {
				consistentHash.remove(consistentHash.machines.get(1));
			};
		}.start();
		try {
			Thread.sleep(300);
		} catch (Exception e) {
			// TODO: handle exception
		}
		new Thread(){
			public void run() {
				consistentHash.add(new Machine("127.0.0.5", "machine number 5"));
			};
		}.start();
	}

	private static class Machine {
		private final String name;
		private final String des;

		private Machine(String name, String des) {
			super();
			this.name = name;
			this.des = des;
		}
	}

}
