package com.atguigu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class redisTest {

        public static void main(String[] args) {
                System.out.println(getJedis().ping());
                testString();
        }

        public static Jedis getJedis() {
                return new Jedis("hadoop102", 6379);
        }

        private static JedisPool jedisPool = null;

        public static Jedis getJedisPool() {

                if (jedisPool == null) {
                        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                        jedisPoolConfig.setMaxTotal(10);
                        jedisPoolConfig.setMaxIdle(5);
                        jedisPoolConfig.setMinIdle(5);
                        jedisPoolConfig.setBlockWhenExhausted(true);
                        jedisPoolConfig.setMaxWaitMillis(2000);
                        jedisPoolConfig.setTestOnBorrow(true);
                        jedisPool = new JedisPool("hadoop102", 6379);
                }
                return jedisPool.getResource();
        }

        public static void testString() {
                Jedis jedis = getJedisPool();
                jedis.set("zhangsan", "20");
                System.out.println("jedis.get(\"zhangsan\") = " + jedis.get("zhangsan"));

                System.out.println("jedis.strlen(\"zhangsan\") = " + jedis.strlen("zhangsan"));
                
                jedis.close();
        }

}
