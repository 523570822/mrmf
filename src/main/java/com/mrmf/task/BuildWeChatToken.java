package com.mrmf.task;

import com.mrmf.service.redis.RedisServiceImpl;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * Created by Tibers on 16/4/21.
 */
public class BuildWeChatToken {
	public void ini(){
		
	}
	/*
    private static RedisServiceImpl redisService = new RedisServiceImpl();
    @Resource
    private JedisPool jedisPool;
	
    public void ini() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Long surplus = 7100L;
                    Jedis jedis = null;
                    try {
                        jedis = jedisPool.getResource();
                        if (jedis.exists(redisService.Redis_Ticket)) {
                            surplus = jedis.ttl(redisService.Redis_Ticket);
                        } else {
                            String token = redisService.getToken("user");
                            String ticket = redisService.getTicket(token);
                            jedis.set(redisService.Redis_Ticket, ticket);
                            jedis.set(redisService.Redis_Token, token);
                            jedis.expire(redisService.Redis_Ticket, 7100);
                            jedis.expire(redisService.Redis_Token, 7100);
                        }
                        if(jedis.exists(redisService.Redis_staff_Ticket)){
                        	surplus = jedis.ttl(redisService.Redis_staff_Ticket);
                        }else{
                        	String token = redisService.getToken("staff");
                            String ticket = redisService.getTicket(token);
                            jedis.set(redisService.Redis_staff_Ticket, ticket);
                            jedis.set(redisService.Redis_staff_Token, token);
                            jedis.expire(redisService.Redis_staff_Ticket, 7100);
                            jedis.expire(redisService.Redis_staff_Token, 7100);
                        }
                        if(jedis.exists(redisService.Redis_organ_Ticket)){
                        	surplus = jedis.ttl(redisService.Redis_organ_Ticket);
                        }else{
                        	String token = redisService.getToken("organ");
                            String ticket = redisService.getTicket(token);
                            jedis.set(redisService.Redis_organ_Ticket, ticket);
                            jedis.set(redisService.Redis_organ_Token, token);
                            jedis.expire(redisService.Redis_organ_Ticket, 7100);
                            jedis.expire(redisService.Redis_organ_Token, 7100);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (jedis != null) {
                            jedisPool.returnResourceObject(jedis);
                        }
                    }
                    try {
                        Thread.sleep(surplus*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
       
    }*/
}
