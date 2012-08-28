//$Id: ResultSetMappingSecondPass.java 9019 2006-01-11 18:50:33Z epbernard $
package org.hibernate.cfg;

import java.util.Map;

import org.dom4j.Element;
import org.hibernate.MappingException;
import org.hibernate.engine.ResultSetMappingDefinition;

/**
 * @author Emmanuel Bernard
 */
public class ResultSetMappingSecondPass extends ResultSetMappingBinder implements QuerySecondPass {
	private Element element;
	private String path;
	private Mappings mappings;

	public ResultSetMappingSecondPass(Element element, String path, Mappings mappings) {
		this.element = element;
		this.path = path;
		this.mappings = mappings;
	}

	public void doSecondPass(Map persistentClasses, Map inheritedMetas) throws MappingException {
		ResultSetMappingDefinition definition = buildResultSetMappingDefinition( element, path, mappings);
		mappings.addResultSetMapping( definition );
	}
}
