package com.yj.cache.test;

import org.ehcache.spi.loaderwriter.BulkCacheLoadingException;
import org.ehcache.spi.loaderwriter.BulkCacheWritingException;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;

import java.util.Map;

public class ProductCacheLoaderWriter implements CacheLoaderWriter {

    @Override
    public Object load(Object o) throws Exception {
        return null;
    }

    @Override
    public Map loadAll(Iterable iterable) throws BulkCacheLoadingException, Exception {
        return null;
    }

    @Override
    public void write(Object o, Object o2) throws Exception {

    }

    @Override
    public void writeAll(Iterable iterable) throws BulkCacheWritingException, Exception {

    }

    @Override
    public void delete(Object o) throws Exception {

    }

    @Override
    public void deleteAll(Iterable iterable) throws BulkCacheWritingException, Exception {

    }
}
