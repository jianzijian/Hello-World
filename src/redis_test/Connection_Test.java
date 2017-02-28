package redis_test;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

class Connection_Test {

	public Connection_Test() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(500); // 500��Ծ����
		config.setMaxIdle(10 * 1000); // ����������ʱ��10s
		config.setMaxWait(10 * 1000); // ��ȡ�������10s�ȴ�
		config.setTestOnBorrow(true); // ���޶���ǰ�Ȳ��Զ������
		config.setTestOnReturn(true); // ��������ǰ�Ȳ��Զ������

		JedisShardInfo info = new JedisShardInfo("10.18.8.45", 16379);
		List<JedisShardInfo> infos = new ArrayList<>();
		infos.add(info);

		ShardedJedisPool pool = new ShardedJedisPool(config, infos);

		ShardedJedis shardedJedis = pool.getResource();
		System.out.println(shardedJedis.hkeys("*").size());
	}

	public static void main(String[] args) {
		new Connection_Test();
	}
}
