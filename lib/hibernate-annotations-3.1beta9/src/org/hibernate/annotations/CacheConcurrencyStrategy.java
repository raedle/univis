//$Id: CacheConcurrencyStrategy.java 6275 2005-04-01 00:32:27Z epbernard $
package org.hibernate.annotations;

/**
 * Cache concurrency strategy
 * @author Emmanuel Bernard
 */
public enum CacheConcurrencyStrategy {
	NONE,
	READ_ONLY,
	NONSTRICT_READ_WRITE,
	READ_WRITE,
	TRANSACTIONAL
}
