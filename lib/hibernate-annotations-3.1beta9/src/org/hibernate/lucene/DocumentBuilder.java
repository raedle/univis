//$Id: DocumentBuilder.java 8722 2005-11-30 20:00:55Z epbernard $
package org.hibernate.lucene;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.AccessibleObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.hibernate.AssertionFailure;
import org.hibernate.HibernateException;

public class DocumentBuilder<T> {

	private final List<Member> keywordGetters = new ArrayList<Member>();
	private final List<String> keywordNames = new ArrayList<String>();
	private final List<Member> unstoredGetters = new ArrayList<Member>();
	private final List<String> unstoredNames = new ArrayList<String>();
	private final List<Member> textGetters = new ArrayList<Member>();
	private final List<String> textNames = new ArrayList<String>();

	//private final Class<T> beanClass;
	private final File file;
	private String idKeywordName;
	private final Analyzer analyzer;

	public DocumentBuilder(Class clazz, Analyzer analyzer, File indexDir) {
		//this.beanClass = clazz;
		this.analyzer = analyzer;
		String fileName = getTypeName( clazz, ( (Indexed) clazz.getAnnotation(Indexed.class) ).index() );
		file = new File(indexDir, fileName);

		for ( Class currClass = clazz; currClass != null ; currClass = currClass.getSuperclass() ) {
			Method[] methods = currClass.getDeclaredMethods();
			for ( int i = 0; i < methods.length ; i++ ) {
				Method method = methods[i];
				Keyword keywordAnn = method.getAnnotation(Keyword.class);
				if (keywordAnn!=null) {
					String name = getAttributeName( method, keywordAnn.name() );
					if ( keywordAnn.id() ) {
						idKeywordName = name;
					}
					else {
						setAccessible(method);
						keywordGetters.add(method);
						keywordNames.add(name);
					}
				}
				Unstored unstoredAnn = method.getAnnotation(Unstored.class);
				if (unstoredAnn!=null) {
					setAccessible(method);
					unstoredGetters.add(method);
					unstoredNames.add( getAttributeName( method, unstoredAnn.name() ) );
				}
				Text textAnn = method.getAnnotation(Text.class);
				if (textAnn!=null) {
					textGetters.add(method);
					textNames.add( getAttributeName( method, textAnn.name() ) );
				}
			}
		}

		if (idKeywordName==null) {
			throw new HibernateException( "No id Keyword for: " + clazz.getName() );
		}
	}

	private Object getValue(Member member, T bean) {
		try {
			if (member instanceof java.lang.reflect.Field) {
				return ( (java.lang.reflect.Field) member).get( bean );
			}
			else if (member instanceof Method) {
				return ( (Method) member).invoke( bean );
			}
			else {
				throw new AssertionFailure("Unexpected member: " + member.getClass().getName() );
			}
		}
		catch (Exception e) {
			throw new IllegalStateException( "Could not get property value", e );
		}
	}

	public Document getDocument(T instance, Serializable id) {
		Document doc = new Document();
		doc.add( Field.Keyword( idKeywordName, id.toString() ) );
		for ( int i=0; i<keywordNames.size(); i++ ) {
			Member member = keywordGetters.get(i);
			Object value = getValue( member, instance );
			if (value!=null) {
				doc.add( Field.Keyword( keywordNames.get(i), toString( value ) ) );
			}
		}
		for ( int i=0; i<textNames.size(); i++ ) {
			Object value = getValue( textGetters.get(i), instance );
			if (value!=null) {
				doc.add( Field.Text( textNames.get(i), toString( value ) ) );
			}
		}
		for ( int i=0; i<unstoredNames.size(); i++ ) {
			Object value = getValue( unstoredGetters.get(i), instance );
			if (value!=null) {
				doc.add( Field.UnStored( unstoredNames.get(i), toString( value ) ) );
			}
		}
		return doc;
	}

	private static String toString(Object value) {
		return value.toString();
	}

	public Term getTerm(Serializable id) {
		return new Term( idKeywordName, id.toString() );
	}

	private static String getAttributeName(Method method, String name) {
		//FIXME: yuk! what about "is"
		return "".equals(name) ? method.getName().substring(3) : name;
	}

	private static String getTypeName(Class clazz, String name) {
		return "".equals(name) ? clazz.getName() : name;
	}

	public File getFile() {
		return file;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	private static void setAccessible(Member member) {
		if ( !Modifier.isPublic( member.getModifiers() ) ) {
			( (AccessibleObject) member ).setAccessible( true );
		}
	}
}
