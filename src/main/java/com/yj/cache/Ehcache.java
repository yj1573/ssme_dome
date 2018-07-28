package com.yj.cache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames="productCache")
public class Ehcache {
    final Logger log = LoggerFactory.getLogger(Ehcache.class);

    @CachePut(key="#id")
    public void put(String id){

    }

    @Cacheable(key = "#id",unless="#result == null")
    public Object get(String id){
        log.info("初始获取数据{}",id);
        return id+"初始化";
    }

    @CacheEvict(key="#id")
    public void delete(String id) {
        //cityMapper.delete(id);
        log.info("删除id为{}的缓存",id);
    }
    /**
     * allEntries移除所有
     */
    @CacheEvict(allEntries = true)
    public void deleteAll() {
        log.info("删除所有的缓存");
    }

}
