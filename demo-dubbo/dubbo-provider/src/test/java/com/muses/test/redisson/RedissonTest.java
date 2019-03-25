/*
 * Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.muses.test.redisson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RSet;
import org.redisson.api.RTopic;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.FstCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import com.muses.api.dto.StudentDTO;
import com.muses.common.orm.mybatis.easylist.list.utils.ObjectUtils;
import com.muses.provider.StudentCache;

/**
 * @author Jervis
 * @date 2018/9/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/redisson-spring.xml", "classpath:/spring/provider-test.xml" })
public class RedissonTest {

	private static final LocalCachedMapOptions	options	= LocalCachedMapOptions.defaults();

	@Autowired
	private RedissonClient						redissonClient;
	@Autowired
	private JsonJacksonCodec					jsonJacksonCodec;
	@Autowired
	private SerializationCodec					jdkCodec;
	@Autowired
	private StringCodec							stringCodec;

	private static final ThreadPoolExecutor		codecNatureTestPool;

	// @Autowired
	// private StudentProvider studentProvider;

	static {
		options
		// 用于淘汰清除本地缓存内的元素
		// 共有以下几种选择:
		// LFU - 统计元素的使用频率，淘汰用得最少（最不常用）的。
		// LRU - 按元素使用时间排序比较，淘汰最早（最久远）的。
		// SOFT - 元素用Java的WeakReference来保存，缓存元素通过GC过程清除。
		// WEAK - 元素用Java的SoftReference来保存, 缓存元素通过GC过程清除。
		// NONE - 永不淘汰清除缓存元素。
		.evictionPolicy(LocalCachedMapOptions.EvictionPolicy.LRU)
		// 如果缓存容量值为0表示不限制本地缓存容量大小
				.cacheSize(1000)
				// 以下选项适用于断线原因造成了未收到本地缓存更新消息的情况。
				// 断线重连的策略有以下几种：
				// CLEAR - 如果断线一段时间以后则在重新建立连接以后清空本地缓存
				// LOAD - 在服务端保存一份10分钟的作废日志
				// 如果10分钟内重新建立连接，则按照作废日志内的记录清空本地缓存的元素
				// 如果断线时间超过了这个时间，则将清空本地缓存中所有的内容
				// NONE - 默认值。断线重连时不做处理。
				.reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE)
				// 以下选项适用于不同本地缓存之间相互保持同步的情况
				// 缓存同步策略有以下几种：
				// INVALIDATE - 默认值。当本地缓存映射的某条元素发生变动时，同时驱逐所有相同本地缓存映射内的该元素
				// UPDATE - 当本地缓存映射的某条元素发生变动时，同时更新所有相同本地缓存映射内的该元素
				// NONE - 不做任何同步处理
				.syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
				// 每个Map本地缓存里元素的有效时间，默认毫秒为单位
				.timeToLive(1, TimeUnit.DAYS)
				// 每个Map本地缓存里元素的最长闲置时间，默认毫秒为单位
				.maxIdle(1, TimeUnit.SECONDS);
	}

	static {
		codecNatureTestPool = new ThreadPoolExecutor(50, 100, 10L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
				new NamedThreadFactory("CodecNatureTestPool"));
		codecNatureTestPool.allowCoreThreadTimeOut(Boolean.TRUE);
	}

	@Test
	public void codecNatureTest() {
		// jdkCodec
		// jsonJacksonCodec
		int size = 100;
		// setTest(jdkCodec, size);
		getTest(jdkCodec);
	}

	private void getTest(Codec codec) {
		List<Future<StudentDTO>> getFutures = new ArrayList<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 200; i++) {
			getFutures.add(codecNatureTestPool.submit(new GetFromRedisTask(String.valueOf(i), codec)));
		}
		for (Future<StudentDTO> f : getFutures) {
			try {
				f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println("all spended: " + (System.currentTimeMillis() - start));
	}

	private void setTest(Codec codec, int size) {
		byte[] b = new byte[1024 * size - 699];
		Arrays.fill(b, (byte) 0x32);
		String name = new String(b);
		List<Future<Long>> setFutures = new ArrayList<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 200; i++) {
			setFutures
					.add(codecNatureTestPool.submit(new SetToRedisTask(new StudentDTO(Long.valueOf(i), name), codec)));
		}
		for (Future<Long> f : setFutures) {
			try {
				f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println("all spended: " + (System.currentTimeMillis() - start));
	}

	private class SetToRedisTask implements Callable<Long> {
		private StudentDTO	student;
		private Codec		codec;

		SetToRedisTask(StudentDTO student, Codec codec) {
			this.student = student;
			this.codec = codec;
		}

		@Override
		public Long call() throws Exception {
			long start = System.currentTimeMillis();
			RBucket<StudentDTO> bucket = redissonClient.getBucket("" + this.student.getStudentId(), this.codec);
			bucket.set(this.student);
			System.out.println("set: " + this.student.getStudentId() + ", has spended: "
					+ (System.currentTimeMillis() - start));
			return this.student.getStudentId();
		}
	}

	private class GetFromRedisTask implements Callable<StudentDTO> {
		private String	studentId;
		private Codec	codec;

		GetFromRedisTask(String studentId, Codec codec) {
			this.studentId = studentId;
			this.codec = codec;
		}

		@Override
		public StudentDTO call() throws Exception {
			long start = System.currentTimeMillis();
			RBucket<StudentDTO> bucket = redissonClient.getBucket(studentId, this.codec);
			StudentDTO student = bucket.get();
			System.out.println("get: " + studentId + ", has spended: " + (System.currentTimeMillis() - start));
			return student;
		}
	}

	@Test
	public void testSet() {
		RScoredSortedSet<Object> anySet = redissonClient.getScoredSortedSet("anySet1", new JsonJacksonCodec());
		anySet.pollFirst();
		StudentDTO strudent1 = new StudentDTO();
		strudent1.setName("1");
		StudentDTO strudent2 = new StudentDTO();
		strudent2.setMobile("1");
		anySet.add(.1, strudent1);
		anySet.add(.2, strudent2);
	}

	public static class MyComparator implements Comparator<Number> {
		@Override
		public int compare(Number d1, Number d2) {
			return ((Long) d1.longValue()).compareTo(d2.longValue());
		}
	}

	@Test
	public void test() {
		String a = "miaoqiang";
		RBucket<String> bucket = redissonClient.getBucket(a, stringCodec);
		System.out.println(bucket.get());
	}

	@Test
	public void testBlockingDeque() {
		try {
			RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque("blockingDeque");
			blockingDeque.put(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void testTopic() {
		RTopic topic = redissonClient.getTopic("");
		topic.publish(null);
	}

	@Test
	public void codecSetTest() {
		StudentDTO student = new StudentDTO();
		byte[] b = new byte[1024 * 20];
		Arrays.fill(b, (byte) 0x32);
		String s = new String(b);
		student.setName(s);
		student.setStudentId(123L);
		student.setMobile("123456789001");
		RBucket<StudentDTO> bucket = redissonClient.getBucket("student1", jsonJacksonCodec);
		bucket.set(student);
	}

	@Test
	public void expireTest() {
		RBucket<StudentDTO> bucket = redissonClient.getBucket("1", jsonJacksonCodec);
		bucket.set(new StudentDTO());
		bucket.expire(10, TimeUnit.MINUTES);
	}

	@Test
	public void codecGetTest() throws InterruptedException {
		AtomicLong aLong = new AtomicLong(0L);
		final CountDownLatch latch = new CountDownLatch(100);
		ExecutorService executorService = Executors.newFixedThreadPool(50);
		for (int i = 0; i < 100; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					long start = System.currentTimeMillis();
					RBucket<StudentDTO> bucket = redissonClient.getBucket("student1", jsonJacksonCodec);
					StudentDTO studentDTO = bucket.get();
					System.out.println(aLong + " 耗时：" + (System.currentTimeMillis() - start));
					aLong.set(aLong.get() + 1);
					latch.countDown();
				}
			});
		}
		latch.await();
	}

	@Test
	public void BucketSet() {
		StudentCache sc = new StudentCache();
		StudentDTO studentDTO = sc.getFromRedisByBucket(redissonClient, "property:base:option:1");
		System.out.println(studentDTO);
	}

	@Test
	public void delTest() {
		RKeys keys = redissonClient.getKeys();
		keys.flushdb();
	}

	@Test
	public void redissonTest() {
		// RListMultimap<Object, Object> multimap = redissonClient.getListMultimap("tree:*:2",new StringCodec());
		// List<String> a = new ArrayList<>();
		// a.add("11");
		// a.add("12");
		// multimap.putAll("1",a);

		RSet<Object> rSet = redissonClient.getSet("set1", new StringCodec());
		Set<Object> set = rSet.readIntersection("set2");
		// RList<String> rList = redissonClient.getList("a:test1");
		// System.out.println("---" + rList);
		// rList.add("last");
		// rList.add("1");
		// RList<String> rList2 = redissonClient.getList("test");
		// System.out.println("---" + rList2);

	}

	@Test
	public void rateLimiterTest() {
		RMap<String, Long> rMap = redissonClient.getMap("myRateLimiter", new LongCodec());
		rMap.readAllMap();
		RRateLimiter rateLimiter = redissonClient.getRateLimiter("myRateLimiter");
		// 初始化
		// 最大流速 = 每1秒钟产生10个令牌
		rateLimiter.trySetRate(RateType.OVERALL, 4, 100, RateIntervalUnit.SECONDS);

		// 获取4个令牌
		rateLimiter.tryAcquire(2);

	}

	@Test
	public void localCacheTest() throws InterruptedException {
		byte[] b = new byte[1024 * 1024];
		Arrays.fill(b, (byte) 0x32);
		String s = new String(b);
		StudentDTO student1 = new StudentDTO();
		student1.setName(s);
		student1.setStudentId(1L);
		String cachekey1 = "localCache:students:1";
		RLocalCachedMap<Long, StudentDTO> localCachedMap1 = redissonClient.getLocalCachedMap(cachekey1, new FstCodec(),
				options);
		localCachedMap1.put(student1.getStudentId(), student1);
		String cachekey2 = "localCache:students:2";
		RLocalCachedMap<Long, StudentDTO> localCachedMap2 = redissonClient.getLocalCachedMap(cachekey2, new FstCodec(),
				options);
		StudentDTO student2 = new StudentDTO();
		student2.setName("13");
		student2.setStudentId(2L);
		localCachedMap2.put(student2.getStudentId(), student2);
		for (int j = 0; j < 5; j++) {
			System.out.println("-------- " + 1 + " 开始 ---------");
			long timeMillis = System.currentTimeMillis();
			StudentDTO studentDTO1 = localCachedMap1.get(1L);
			System.out.println(System.currentTimeMillis() - timeMillis);
			System.out.println("-------- " + 1 + " 结束 ---------");
			System.out.println("-------- " + 2 + " 开始 ---------");
			timeMillis = System.currentTimeMillis();
			StudentDTO studentDTO2 = localCachedMap1.get(2L);
			System.out.println(System.currentTimeMillis() - timeMillis);
			System.out.println("-------- " + 2 + " 结束 ---------");
		}

		// RKeys rKeys = redissonClient.getKeys();
		// Iterator<String> iterator = rKeys.getKeys().iterator();
		// while (iterator.hasNext()) {
		// String next = iterator.next();
		// RLocalCachedMap<String, PropDTO> cachedMap = redissonClient
		// .getLocalCachedMap(next, new FstCodec(), options);
		// PropDTO o = cachedMap.get(valKey);
		// localCachedMap.put(valKey, o);
		// cachedMap.delete();
		// }

		// for (int i = 0; i < 10; i++) {
		// Long start = System.currentTimeMillis();
		// PropDTO propDTO = localCachedMap.get(valKey);
		// System.out.print("耗时：" + (System.currentTimeMillis() - start));
		// // 内存守护对象，防止外部串改
		// PropDTO clonePropDTO = (PropDTO) SerializationUtils.clone((Serializable) propDTO);
		// // 守护测试
		// System.out.print(",修改前" + i);
		// System.out.print(": propDTO.getName = " + propDTO.getName());
		// System.out.print(",clonePropDTO.getName = " + clonePropDTO.getName());
		// if (i == 1) {
		// clonePropDTO.setName("test");
		// }
		// if (i > 0) {
		// System.out.print(",修改后" + i);
		// System.out.print(": propDTO.getName = " + propDTO.getName());
		// System.out.print(",clonePropDTO.getName = " + clonePropDTO.getName());
		// }
		// System.out.println("");
		// // 内存有效时间测试
		// if (i > 0 && i % 3 == 0) {
		// Thread.sleep(7000);
		// System.out.println("time = " + (i / 3));
		// }
		// }
	}

	@Test
	public void threadTest() throws InterruptedException {
		// RBucket<PackProdBaseDTO> rBucket = redissonClient.getBucket("test");
		// PackProdBaseDTO baseDTO = new PackProdBaseDTO();
		// baseDTO.setBriefName("miaoqiangtest");
		// rBucket.set(baseDTO);
		// RBatch rBatch = redissonClient.createBatch();
		// String key = "GLOBAL_PROP_1";
		// rBatch.getBucket("test").getAsync();
		// List<?> results = rBatch.execute();
		// List<PackProdBaseDTO> packProdBaseDTOs = new ArrayList<>();
		// if (CollectionUtils.isNotEmpty(results)) {
		// packProdBaseDTOs = results.stream().map((result) -> {
		// if (result instanceof PackProdBaseDTO) {
		// // return (PackProdBaseDTO) redisPackTemplate.getValueSerializer().deserialize((byte[]) result);
		//
		// return (PackProdBaseDTO) result;
		// }
		// return null;
		// }).collect(Collectors.toList());
		// }

		// ExecutorService executor = Executors.newFixedThreadPool(5);
		LockTest lock1 = new LockTest();
		LockTest lock2 = new LockTest();
		// executor.submit(lock1);
		// executor.submit(lock2);
		lock1.start();
		lock2.start();
		// 防止线程提交后，框架注销bean
		Thread.sleep(20000);
	}

	protected class LockTest extends Thread {
		@Override
		public void run() {
			RLock rLock = redissonClient.getLock("lockTest12");
			lock(rLock, 1);
			lock(rLock, 2);
			tryLock(rLock, 3);
		}

		private void lock(RLock rLock, Integer time) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				System.out.println("----------------- Thread : " + Thread.currentThread().getId() + ", time : " + time
						+ ", start : " + format.format(new Date()) + " , " + rLock.isLocked() + " -----------------");
				rLock.lock(10L, TimeUnit.SECONDS);
				// 重复锁，需要强制解锁
				// rLock.tryLock(10L, TimeUnit.SECONDS);
				// rLock.lock(10L, TimeUnit.SECONDS);
				// rLock.forceUnlock();
				RBucket<String> miaoqiangTest = redissonClient.getBucket("miaoqiangTest");
				miaoqiangTest.set("test" + Thread.currentThread().getName());
				System.out.println("----------------- Thread : " + Thread.currentThread().getId() + ", time : " + time
						+ ", lock : " + format.format(new Date()) + " , " + rLock.isLocked() + " -----------------");
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (ObjectUtils.isNotEmpty(rLock)) {
					rLock.unlock();
				}
				System.out.println("----------------- Thread : " + Thread.currentThread().getId() + ", time : " + time
						+ ", unlock : " + format.format(new Date()) + " , " + rLock.isLocked() + " -----------------");
			}
		}

		private void tryLock(RLock rLock, Integer time) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				System.out.println("----------------- Thread : " + Thread.currentThread().getId() + ", time : " + time
						+ ", start : " + format.format(new Date()) + " , " + rLock.isLocked() + " -----------------");
				rLock.tryLock(10L, TimeUnit.SECONDS);
				// 重复锁，需要强制解锁
				// rLock.lock(10L, TimeUnit.SECONDS);
				// rLock.tryLock(10L, TimeUnit.SECONDS);
				// rLock.forceUnlock();
				RBucket<String> miaoqiangTest = redissonClient.getBucket("miaoqiangTest");
				miaoqiangTest.set("test" + Thread.currentThread().getName());
				System.out.println("----------------- Thread : " + Thread.currentThread().getId() + ", time : " + time
						+ ", lock : " + format.format(new Date()) + " , " + rLock.isLocked() + " -----------------");
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (ObjectUtils.isNotEmpty(rLock)) {
					rLock.unlock();
				}
				System.out.println("----------------- Thread : " + Thread.currentThread().getId() + ", time : " + time
						+ ", unlock : " + format.format(new Date()) + " , " + rLock.isLocked() + " -----------------");
			}
		}
	}

}
