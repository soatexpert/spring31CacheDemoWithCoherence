package fr.soat.spring.cache.util;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.DefaultCacheServer;

public class CoherenceCacheServerWrapper {

	private static Thread internalServer = new Thread(new Runnable() {
		public void run() {
			DefaultCacheServer.main(new String[0]);
		}
	});

	private CoherenceCacheServerWrapper() {
	}

	public static void start() {
		internalServer.start();
		while (!CacheFactory.getCluster().isRunning()) {
			// Loop waiting for cluster to start
		}
	}

	public static void stop() {
		CacheFactory.shutdown();
		internalServer.stop();
	}
}
