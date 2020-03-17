package info.magnolia.training.fullstack.templating.redsysModel;

import es.redsys.insite.api.model.message.InsiteOperationMessage;

public class MessageBuilderFactory {
	
	   
	   public static InsiteOperationMessage buildMessage(String merchant, String terminal, String amount, String currency, String id,
				String order, String MessageType){
	      if(MessageType == null){
	         return null;
	      }		
	      if(MessageType.equalsIgnoreCase("REDSYS")){
	         return new MessageBuilderRedsys().buildMessage(merchant, terminal, amount, currency, id, order);	      
	      }
	      return null;
	   }

}
