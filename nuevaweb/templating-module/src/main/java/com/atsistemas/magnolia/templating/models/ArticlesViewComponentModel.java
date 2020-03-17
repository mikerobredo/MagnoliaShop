	package com.atsistemas.magnolia.templating.models;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import info.magnolia.templating.functions.TemplatingFunctions;


public class ArticlesViewComponentModel <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<ConfiguredTemplateDefinition> {
 
	private String id;
	private String query;
	private Node node;
    private String titleEvent1;
	/** The Constant log. */
	

    /** The events. */
    private ArrayList<Node> events;
    
    /** The events search. */
    private ArrayList<Node> eventsSearch;
    	
    /** The query param anteriores. */	
    private String queryParamAnteriores;
    
    /** The query param proximos. */
    private String queryParamProximos;
    
    /** The title event. */
    private String titleEvent;
    
    /** The query param. */
    private String queryParam;
    
    /** The items. */
    private List<Node> items;
	/** The common util. */
    private ArrayList<String> arIds ;
    
    private String[] cosas;
    
    private long tamQuery;

	
    
    /** The templating functions. */
    private TemplatingFunctions templatingFunctions;
	
    public ArticlesViewComponentModel(Node content, ConfiguredTemplateDefinition definition, RenderingModel<?> parent) throws PathNotFoundException, RepositoryException {
        super(content, definition, parent);
    }
    
    @Override
    public String execute() {
    	id = MgnlContext.getParameter("id");// a partir de aqui cojo la variable id en el ftl
    	
        	query = "select * from [mgnl:objeto]";
        	ArrayList<Node> items = new ArrayList<Node>();    //aqui tengo los nodos
        	ArrayList<String> items2 = new ArrayList<String>();   //aquii se me queda la uuid de cada nodo cone l metodo getidentifier
        	QueryManager queryManager;
    		try {
    			queryManager = MgnlContext.getJCRSession("caja").getWorkspace().getQueryManager();
    			Query q = queryManager.createQuery(query, Query.JCR_SQL2);
    			
    			/*
    			if(limit != null && limit.length > 0){
    				q.setLimit(limit[0]);
    			}*/
    			QueryResult qr = q.execute();
    	    	NodeIterator nodes = qr.getNodes();
    			Node n = null;
    			
    			System.out.println("tamaño del nodo");
    			tamQuery = nodes.getSize();
    			cosas = new String[(int) nodes.getSize()];
    			 int i=0;
    	    	if(nodes != null){
    	    		while(nodes.hasNext()){
    	    			//System.out.println("uno ");
    	    			n = nodes.nextNode();
    	    			
						n.setProperty("price", 300);
    					items.add(n);
    					items2.add((n.getIdentifier()));
    					cosas[i]= n.getIdentifier();
    					i++;
    					System.out.println(n.getCorrespondingNodePath("caja"));
    					
    					//System.out.println(items.toString());
    				}
    	    		System.out.println("cosas ----");
    	    		System.out.println(cosas[0]);}			
    		} catch (LoginException e) {
    			
    		} catch (RepositoryException e) {
    			
    		}
    		events = items;
    		arIds = items2;
    		System.out.println(arIds);
    		
    		
    		
    		
    	        	//        	
        	//Search for related articles
        	try {
        		node = content.getNode("relatedArticles/singleton");
        	} catch (RepositoryException e) {
    			
    		}
        	int limit = 5;
			if(node != null){
				limit = Integer.valueOf(PropertyUtil.getString(node, "numElements", "5"));
			}
			//Property selectTags = PropertyUtil.getPropertyOrNull(this.templatingFunctions.page(content), "selectTags");
			//this.items = this.commonUtil.getRelatedArticles(limit, selectTags, content.getIdentifier());
    	
    	///
    	return super.execute();
    }
    
    public String getId() {
    	return id;
    }
    
    public ArrayList<Node> getEvents(){
    	return events;
    }
 
    public Node getNode() {
    	return node;
    }
    public String getQuery() {
    	return query;
    }
    public ArrayList<String> getarIds(){
    	return arIds;
    }
    public String[] getCosas() {
    	return cosas;
    }
    public long getTamQuery() {
    	return tamQuery;
    }
    
}