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
		config.setMaxActive(500); // 500活跃对象
		config.setMaxIdle(10 * 1000); // 对象最大空闲时间10s
		config.setMaxWait(10 * 1000); // 获取对象最大10s等待
		config.setTestOnBorrow(true); // 租赁对象前先测试对象活性
		config.setTestOnReturn(true); // 返还对象前先测试对象活性

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
