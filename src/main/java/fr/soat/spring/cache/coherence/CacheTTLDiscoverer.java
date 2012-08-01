package fr.soat.spring.cache.coherence;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CacheTTLDiscoverer {

	@Pointcut(value = "execution(public * *(..))")
	public void anyPublicMethod() {
	}

	@Around("anyPublicMethod() && @annotation(cacheTTL) && @annotation(cachePut)")
	public Object doCacheTTLConfig(final ProceedingJoinPoint pjp, CacheTTL cacheTTL, CachePut cachePut) throws Throwable {
		return doCacheConfig(pjp, cacheTTL, cachePut.value());
	}

	@Around("anyPublicMethod() && @annotation(cacheTTL) && @annotation(cacheable)")
	public Object doCacheTTLConfig(final ProceedingJoinPoint pjp, CacheTTL cacheTTL, Cacheable cacheable) throws Throwable {
		return doCacheConfig(pjp, cacheTTL, cacheable.value());
	}

	private Object doCacheConfig(ProceedingJoinPoint pjp, CacheTTL cacheTTL, String[] cacheNames) throws Throwable {
		System.out.println("Interception : " + cacheTTL + " | " + cacheNames);
		CoherenceCacheManager.CONFIG.set(cacheTTL);
		return pjp.proceed();
	}
}
