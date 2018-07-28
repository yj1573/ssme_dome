package com.yj.cache.test;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;

import org.ehcache.config.builders.CacheConfigurationBuilder;

import org.ehcache.config.builders.CacheManagerBuilder;

import org.ehcache.config.builders.ResourcePoolsBuilder;

import org.ehcache.config.units.EntryUnit;

import org.ehcache.config.units.MemoryUnit;

import org.ehcache.expiry.Duration;

import org.ehcache.expiry.Expirations;


import java.io.FileNotFoundException;

import java.util.concurrent.TimeUnit;


//@Configuration
//@EnableCaching
public class EhcacheUtil {

    private static CacheManager cacheManager;

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        // 初始化Ehcache对象

        new EhcacheUtil();

        // 获取初始化的缓存对象

        Cache<String, String> mineCache = cacheManager.getCache("defaultCache", String.class, String.class);

        // 创建测试内容

        StringBuilder strTemp = new StringBuilder("测试");

        while(strTemp.toString().getBytes().length <= 1024*1024){

            strTemp.append("测试一二三四五六七八九十");

        }

        System.out.println("大小为：" + strTemp.toString().getBytes().length + " Byte,1MB大小为:" + 1024*1024 + " Byte");

        // 存入第1条数据

        mineCache.put("key", strTemp.toString());

        // 取出并输出

        System.out.println("key:" + mineCache.get("key"));

        strTemp = new StringBuilder("测试2");

        // 存入第2条数据

        mineCache.put("key2", strTemp.toString());

        // 取出并输出

        System.out.println("key2:" + mineCache.get("key2"));

        // 取出并输出第一条数据,由于offheap的个存在所以当存入第2条数据时,第一条会被存储到offheap中,但是第一条数据的大小大于offheap所以会被淘汰

        System.out.println("key:" + mineCache.get("key"));

        // 关闭ehcache

        cacheManager.close();

    }







    /**
     * 初始化Ehcache缓存对象
     */

    public EhcacheUtil() {

        System.out.println("[Ehcache配置初始化<开始>]");


        // 配置默认缓存属性

        CacheConfiguration<String, String> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(

                // 缓存数据K和V的数值类型

                // 在ehcache3.3中必须指定缓存键值类型,如果使用中类型与配置的不同,会报类转换异常

                String.class, String.class,

                ResourcePoolsBuilder

                        .newResourcePoolsBuilder()

                        //设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到offheap中

                        .heap(1L, EntryUnit.ENTRIES)

                        //设置堆外储存大小(内存存储) 超出offheap的大小会淘汰规则被淘汰

                        .offheap(1L, MemoryUnit.MB)

                        // 配置磁盘持久化储存(硬盘存储)用来持久化到磁盘,这里设置为false不启用

//                        .disk(500L, MemoryUnit.MB, false)

        ).withExpiry(Expirations.timeToLiveExpiration(

                //设置缓存过期时间

                Duration.of(30L, TimeUnit.SECONDS))

        ).withExpiry(Expirations.timeToIdleExpiration(

                //设置被访问后过期时间(同时设置和TTL和TTI之后会被覆盖,这里TTI生效,之前版本xml配置后是两个配置了都会生效)

                Duration.of(60L, TimeUnit.SECONDS))

        ).build();

        // 缓存淘汰策略 默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。

        // 这块还没看

        /*.withEvictionAdvisor(
                new OddKeysEvictionAdvisor<Long, String>())

        )*/

        // CacheManager管理缓存

        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()

                // 硬盘持久化地址

                .with(CacheManagerBuilder.persistence("D:/ehcacheData"))

                // 设置一个默认缓存配置

                .withCache("defaultCache", cacheConfiguration)

                //创建之后立即初始化

                .build(true);


        System.out.println("[Ehcache配置初始化<完成>]");

    }
}

