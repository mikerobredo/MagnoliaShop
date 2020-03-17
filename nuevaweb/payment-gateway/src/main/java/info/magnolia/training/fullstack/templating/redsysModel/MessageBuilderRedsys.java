package info.magnolia.training.fullstack.templating.redsysModel;

import es.redsys.insite.api.constants.InsiteConstants.TransactionType;
import es.redsys.insite.api.model.message.InsiteOperationMessage;

public class MessageBuilderRedsys implements IMessageBuider {

	@Override
	public InsiteOperationMessage buildMessage(String merchant,String terminal,String amount, String currency,String id,String order) {
		InsiteOperationMessage msg= new InsiteOperationMessage();
		msg.setMerchant(merchant);
		msg.setTerminal(terminal);
		msg.setAmount(amount);
		msg.setCurrency(currency);
		msg.setOperID(id);
		msg.setOrder(order);
		msg.setTransactionType(TransactionType.AUTHORIZATION);
		msg.useDirectPayment();			
		return msg;		
	}


}
