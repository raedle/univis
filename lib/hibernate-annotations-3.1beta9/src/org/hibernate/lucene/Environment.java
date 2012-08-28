//$Id: Environment.java 8721 2005-11-30 19:24:48Z epbernard $
package org.hibernate.lucene;

/**
 * @author Emmanuel Bernard
 */
public final class Environment {
	/**
	 * Indexes base directory
	 */
	public static final String INDEX_BASE_DIR = "hibernate.lucene.index_dir";

	/**
	 * Lucene analyser
	 */
	public static final String ANALYZER_CLASS = "hibernate.lucene.analyzer";
}
