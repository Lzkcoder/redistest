package com.atguigu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ListPosition;

import java.util.HashMap;
import java.util.Map;

public class redisTest {

        public static void main(String[] args) {
                System.out.println(getJedis().ping());
//                testString();
//                testList();
                testHash();
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
                        jedisPool = new JedisPool(jedisPoolConfig,"hadoop102", 6379);
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

        public static void testList() {
                Jedis jedis = getJedisPool();
                jedis.lpush("list1", "1", "2", "3");
                System.out.println("jedis.lpop(\"list1\") = " + jedis.lpop("list1"));
                System.out.println("jedis.lrange(\"list1\",0,2) = " + jedis.lrange("list1", 0, 2));
                System.out.println("jedis.lindex(\"list1\",2) = " + jedis.lindex("list1", 2));
                jedis.linsert("list1", ListPosition.AFTER, "2", "1.1");
                System.out.println("jedis.lpop(\"list1\") = " + jedis.lpop("list1"));

                jedis.close();
        }

        public static void testHash(){
                Jedis jedis = getJedisPool();
                Map<String, String> map = new HashMap<>();
                map.put("age","29");
                map.put("name","zhangsan");

//                jedis.hset("hash","age","20");
                jedis.hset("hash",map);
//                System.out.println("jedis.hget(\"hash\",\"age\") = " + jedis.hget("hash", "age"));
                System.out.println("jedis.hvals(\"hash\") = " + jedis.hvals("hash"));
        }
}
