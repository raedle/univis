//$Id: Cache.java 7783 2005-08-07 23:08:28Z epbernard $
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.TYPE;

import static java.lang.annotation.ElementType.METHOD;

import static java.lang.annotation.ElementType.FIELD;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

/**
 * Add caching strategy to a root entity or a collection
 * @author Emmanuel Bernard
 */
@Target({TYPE, METHOD, FIELD}) @Retention(RUNTIME)
public @interface Cache {
	/** concurrency strategy chosen */
	CacheConcurrencyStrategy usage();
	/** cache region name */
	String region() default "";
	/**
	 * whether or not lazy-properties are included in the second level cache
	 * default all, other value: non-lazy
	 */
	String include() default "all";
}
