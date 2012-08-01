package fr.soat.spring.cache.coherence;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.tangosol.net.NamedCache;

public class CoherenceCacheWrapper implements Cache {

	private NamedCache namedCache;
	private ConfigurableBeanFactory beanFactory;

	public CoherenceCacheWrapper(NamedCache namedCache, ConfigurableBeanFactory beanFactory) {
		this.namedCache = namedCache;
		this.beanFactory = beanFactory;
	}

	public String getName() {
		return namedCache.getCacheName();
	}

	public Object getNativeCache() {
		return namedCache;
	}

	public ValueWrapper get(Object key) {
		Object value = namedCache.get(key);
		return (value != null ? new SimpleValueWrapper(value) : null);
	}

	public void put(Object key, Object value) {
		if (value != null) {
			Long ttl = getTTLinMs(CoherenceCacheManager.CONFIG.get());
			CoherenceCacheManager.CONFIG.set(null);
			if (ttl != null) {
				namedCache.put(key, value, ttl);
			} else {
				namedCache.put(key, value);
			}
		}
	}

	private Long getTTLinMs(CacheTTL cacheTTL) {
		if (cacheTTL == null) {
			return null;
		}
		Long time = Long.valueOf(beanFactory.resolveEmbeddedValue(cacheTTL.value()));
		if (cacheTTL.timeUnit() == TimeUnit.NANOSECONDS) {
			time = time / (1000 * 1000);
		} else if (cacheTTL.timeUnit() == TimeUnit.MICROSECONDS) {
			time = time / 1000;
		} else if (cacheTTL.timeUnit() == TimeUnit.SECONDS) {
			time = time * 1000;
		}
		return time;
	}

	public void evict(Object key) {
		namedCache.remove(key);
	}

	public void clear() {
		namedCache.clear();
	}
}
