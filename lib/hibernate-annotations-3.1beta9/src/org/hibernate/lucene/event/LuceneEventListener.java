//$Id: LuceneEventListener.java 8721 2005-11-30 19:24:48Z epbernard $
package org.hibernate.lucene.event;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.Initializable;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.lucene.DocumentBuilder;
import org.hibernate.lucene.Environment;
import org.hibernate.lucene.Indexed;
import org.hibernate.mapping.PersistentClass;

/**
 * This listener supports setting a parent directory for all generated index files.
 * It also supports setting the analyzer class to be used.
 *
 * @author Gavin King
 * @author Emmanuel Bernard
 * @author Mattias Arbin
 */
public class LuceneEventListener implements PostDeleteEventListener, PostInsertEventListener,
		PostUpdateEventListener, Initializable {

	private Map<Class, DocumentBuilder> documentBuilders = new HashMap<Class, DocumentBuilder>();
	private boolean initialized;

	private static final Log log = LogFactory.getLog( LuceneEventListener.class );

	public void initialize(Configuration cfg) {
		if ( initialized ) return;

		Class analyzerClass;
		String analyzerClassName = cfg.getProperty( Environment.ANALYZER_CLASS );
		if ( analyzerClassName != null ) {
			try {
				analyzerClass = Class.forName( analyzerClassName );
			}
			catch (Exception e) {
				throw new HibernateException(
						"Lucene analyzer class '" + analyzerClassName + "' defined in property '" + Environment.ANALYZER_CLASS + "' could not be found.",
						e
				);
			}
		}
		else {
			analyzerClass = StandardAnalyzer.class;
		}
		// Initialize analyzer
		Analyzer analyzer;
		try {
			analyzer = (Analyzer) analyzerClass.newInstance();
		}
		catch (Exception e) {
			throw new HibernateException( "Failed to instantiate lucene analyzer with type " + analyzerClassName );
		}

		// Initialize index parent dir
		String indexDirName = cfg.getProperty( Environment.INDEX_BASE_DIR );
		File indexDir = indexDirName != null ? new File( indexDirName ) : new File( "." );

		if ( !( indexDir.exists() && indexDir.isDirectory() ) ) {
			//TODO create the directory
			throw new HibernateException( "Index directory does not exists: " + Environment.INDEX_BASE_DIR );
		}
		if ( !indexDir.canWrite() ) {
			throw new HibernateException( "Cannot write into index directory: " + Environment.INDEX_BASE_DIR );
		}
		log.info( "Setting index dir to " + indexDir );

		Iterator iter = cfg.getClassMappings();
		while ( iter.hasNext() ) {
			PersistentClass clazz = (PersistentClass) iter.next();
			Class mappedClass = clazz.getMappedClass();
			if ( mappedClass != null ) {
				if ( mappedClass.getAnnotation( Indexed.class ) != null ) {
					final DocumentBuilder documentBuilder = new DocumentBuilder( mappedClass, analyzer, indexDir );
					documentBuilders.put( mappedClass, documentBuilder );
//					try {
//						IndexWriter iw = new IndexWriter( documentBuilder.getFile(), new StopAnalyzer(), true );
//						iw.close();
//					}
//					catch (IOException ioe) {
//						throw new HibernateException(ioe);
//					}
					log.info( "index: " + documentBuilder.getFile().getAbsolutePath() );
				}
			}
		}
		initialized = true;
	}

	public void onPostDelete(PostDeleteEvent event) {
		DocumentBuilder builder = documentBuilders.get( event.getEntity().getClass() );
		if ( builder != null ) {
			remove( builder, event.getId() );
		}
	}

	public void onPostInsert(PostInsertEvent event) {
		final Object entity = event.getEntity();
		DocumentBuilder builder = documentBuilders.get( entity.getClass() );
		if ( builder != null ) {
			add( entity, builder, event.getId() );
		}
	}

	public void onPostUpdate(PostUpdateEvent event) {
		final Object entity = event.getEntity();
		DocumentBuilder builder = documentBuilders.get( entity.getClass() );
		if ( builder != null ) {
			final Serializable id = event.getId();
			remove( builder, id );
			add( entity, builder, id );
		}
	}

	private void remove(DocumentBuilder builder, Serializable id) {
		Term term = builder.getTerm( id );
		log.debug( "removing: " + term );
		try {
			IndexReader reader = IndexReader.open( builder.getFile() );
			reader.delete( term );
			reader.close();
		}
		catch (IOException ioe) {
			throw new HibernateException( ioe );
		}
	}

	private void add(final Object entity, final DocumentBuilder builder, final Serializable id) {
		Document doc = builder.getDocument( entity, id );
		log.debug( "adding: " + doc );
		try {
			File file = builder.getFile();
			IndexWriter writer = new IndexWriter( file, builder.getAnalyzer(), ! file.exists() );
			writer.addDocument( doc );
			writer.close();
		}
		catch (IOException ioe) {
			throw new HibernateException( ioe );
		}
	}

}
