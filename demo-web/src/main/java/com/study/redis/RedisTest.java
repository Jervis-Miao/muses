/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.study.redis;

import com.study.queue.dto.QueueParam;
import com.utils.SerializeUtilTest;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RDeque;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author miaoqiang
 * @date 2018/8/22.
 */
public class RedisTest {
	public static void main(String[] args) {
		RedisConnect connect = new RedisConnect();
		RedissonClient redisson = connect.getRedisson();
		// 首先获取redis中的key-value对象，key不存在没关系
		// RBucket<String> keyObject = redisson.getBucket("key1");
		// RList<String> rList = redisson.getList("list");
		// RDeque<Object> lList = redisson.getDeque("list");
		// 如果key存在，就设置key的值为新值value
		// 如果key不存在，就设置key的值为value
		// keyObject.set("value");
		// rList.add("last");
		// lList.add("1");
		// lList.addFirst("0");
		RList<Object> list = redisson.getList("GLOBAL_PROP_4");
		list.readAll();

		RList<QueueParam> dtos = redisson.getList("test");
		QueueParam param = new QueueParam();
		param.setTaskKey("123123123");
		param.setVipFlag(true);
		dtos.delete();
		dtos.add(param);
		QueueParam test = dtos.get(0);
		System.out.println("---" + test);
		// 最后关闭RedissonClient
		connect.shutdown();

	}
}
