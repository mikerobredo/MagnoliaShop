package com.atsistemas.magnolia.objeto.actions;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.dialog.action.SaveDialogAction;
import info.magnolia.ui.form.EditorCallback;
import info.magnolia.ui.form.EditorValidator;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeAdapter;


public class SaveOverrideAction extends SaveDialogAction<SaveOverrideActionDefinition>{

	private JcrNodeAdapter item;
	
	public SaveOverrideAction(SaveOverrideActionDefinition definition, JcrNodeAdapter item, EditorValidator validator,
			EditorCallback callback) {
		super(definition, item, validator, callback);
		this.item = item;
		
	}
	
	@Override
    public void execute() throws ActionExecutionException {
		
		try {
			Node currentNode = item.applyChanges();
			PropertyUtil.getPropertyOrNull(currentNode, "date");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date date = null;
			try {
				date = sdf.parse("29-01-2019");
				PropertyUtil.setProperty(currentNode, "date", date);
				
				currentNode.getSession().save();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
		callback.onSuccess(getDefinition().getName());
		
	}
}