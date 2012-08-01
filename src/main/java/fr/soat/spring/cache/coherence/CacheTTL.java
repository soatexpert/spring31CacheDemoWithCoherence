package fr.soat.spring.cache.coherence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheTTL {

	String value();

	TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
