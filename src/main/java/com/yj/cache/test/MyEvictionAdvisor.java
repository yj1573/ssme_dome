package com.yj.cache.test;

import org.ehcache.config.EvictionAdvisor;

/**
 * 自定义哪些key被驱逐
 */
public class MyEvictionAdvisor implements EvictionAdvisor {

    @Override
    public boolean adviseAgainstEviction(Object o, Object o2) {
        return true;
    }
}
