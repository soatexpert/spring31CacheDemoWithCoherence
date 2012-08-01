package fr.soat.spring.cache.coherence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheService;
import com.tangosol.net.Cluster;

public class CoherenceCacheManager implements CacheManager {

	public static ThreadLocal<CacheTTL> CONFIG = new ThreadLocal<CacheTTL>();

	@Autowired
	private ConfigurableBeanFactory beanFactory;

	public Cache getCache(String name) {
		return new CoherenceCacheWrapper(CacheFactory.getCache(name), beanFactory);
	}

	public Collection<String> getCacheNames() {
		List<String> listCaches = new ArrayList<String>();
		Cluster cluster = CacheFactory.getCluster();
		@SuppressWarnings("unchecked")
		Enumeration<String> serviceNames = cluster.getServiceNames();

		while (serviceNames.hasMoreElements()) {
			String serviceName = serviceNames.nextElement();
			if (cluster.getService(serviceName) instanceof CacheService) {
				CacheService serviceCache = (CacheService) cluster.getService(serviceName);
				@SuppressWarnings("unchecked")
				Enumeration<String> cacheNames = serviceCache.getCacheNames();
				while (cacheNames.hasMoreElements()) {
					String cacheName = cacheNames.nextElement();
					listCaches.add(cacheName);
				}
			}
		}
		return listCaches;
	}
}
