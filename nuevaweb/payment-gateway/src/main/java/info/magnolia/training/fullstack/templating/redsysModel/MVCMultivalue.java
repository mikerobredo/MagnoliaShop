package info.magnolia.training.fullstack.templating.redsysModel;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

public class MVCMultivalue<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	@Inject
	public MVCMultivalue(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);

	}

	public String iterateMultivalueNodes(Node Nc) {
		String m1 = StringUtils.EMPTY;
		try {
			NodeIterator child = Nc.getNodes();
			while (child.hasNext()) {
				Node aux = child.nextNode();
				if (aux.hasProperty("shoppingListChildrenNode")) {
					m1 = m1 + aux.getProperty("shoppingListChildrenNode").getString() + "<br>";
				}
			}

		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m1;
	}

	public String iterateFlights(Node Nc) {
		String m1 = StringUtils.EMPTY;
		try {
			NodeIterator child = Nc.getNodes();
			//Nc.getNodes("flights*");
			while (child.hasNext()) {
				Node aux = child.nextNode();
				if (aux.hasProperty("from")) {
					m1 = m1 + " desde: " + aux.getProperty("from").getString()  ;
					if (aux.hasProperty("to")) {
						m1 = m1 + " hasta: " + aux.getProperty("to").getString() ;
					}
					
					NodeIterator subchild = aux.getNodes();
					while(subchild.hasNext()) {
						Node subAux = subchild.nextNode();
						if(subAux.hasProperty("seat")) {
							m1 = m1 + " asiento: " + subAux.getProperty("seat").getString() ;
						}
					}					
				}
				m1 = m1 + "<br>" ;			
			}

		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(m1);
		return m1;
	}

}