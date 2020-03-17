package info.magnolia.training.fullstack.templating.redsysModel;

import java.net.MalformedURLException;
import java.net.URL;

import info.magnolia.context.MgnlContext;

public class RedsysModelUtils {
	
	public String buildUrlRest(String StrUrl1, String restEndpoint ) {
		//String StrUrl1= (MgnlContext.getAggregationState().getOriginalURL());
		String urlRest= "0";
		try {
			URL url1= new URL (StrUrl1);
			urlRest= String.format("%s://%s:%s%s%s", url1.getProtocol(), url1.getHost(), url1.getPort(),MgnlContext.getContextPath(),restEndpoint);			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}	
		return urlRest;
	}
}
