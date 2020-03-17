package info.magnolia.training.fullstack.templating.redsysModel;

import es.redsys.insite.api.model.message.InsiteOperationMessage;

public interface IMessageBuider {
	

	public InsiteOperationMessage buildMessage(String merchant, String terminal, String amount, String currency, String id,
			String order);

}
