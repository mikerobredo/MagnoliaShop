package info.magnolia.training.fullstack.templating.redsysModel;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.jcr.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atsistemas.mamp.ecommerce.payment.PaymentGateway;

import es.redsys.insite.api.constants.InsiteConstants.Environment;
import es.redsys.insite.api.constants.InsiteConstants.TransactionType;
import es.redsys.insite.api.model.message.InsiteOperationMessage;
import es.redsys.insite.api.model.message.InsiteResponseMessage;
import es.redsys.insite.api.service.InsiteService;
import es.redsys.insite.api.service.impl.InsiteOperationService;
import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
import com.atsistemas.mamp.ecommerce.payment.PaymentGateway;

//como instalar el modulo de maven install-file -Dfile=C:\Users\mnunez.robredo\Desktop\insite-api.jar -DgroupId=es.redsys.insite.api -DartifactId=insite-api -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar

public class RedsysModelPayment<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private String id;
	private String check;
	private String merchant;
	private String terminal;
	private String amount;
	private String currency;
	private String order;
	private String merchantSignature;
	private static Logger log = LoggerFactory.getLogger(RedsysModelPayment.class);
	private String ordenInternaMod;
	private InsiteOperationMessage insiteOperation;
	private InsiteService insiteService;
	private String proceedUrlRest;
	
	@Inject
	private PaymentGateway paymentGateway;

	@Inject
	private RedsysModelUtils redsysModelUtils;
	@Inject
	public RedsysModelPayment(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);
	}

	@Override
	public String execute() {
		
		this.proceedUrlRest = this.redsysModelUtils.buildUrlRest(MgnlContext.getAggregationState().getOriginalURL(), this.paymentGateway.getRedsysConfig().getRestUrlProceed());	
		retrieveData();
		
		if (MgnlContext.getAttribute("proceed") != null) {
			pay();
		}
		
		return super.execute();
	}
	


	private void retrieveData() {

		this.currency = MgnlContext.getAttribute("currency").toString();
		this.merchant = MgnlContext.getAttribute("merchant").toString();
		this.merchantSignature = MgnlContext.getAttribute("merchantSignature").toString();
		this.terminal = MgnlContext.getAttribute("terminal").toString();
		this.amount = "999";

		if (MgnlContext.getAttribute("ordenInterna") != null) {
			this.order = MgnlContext.getAttribute("ordenInterna").toString();
		}

		id = MgnlContext.getAttribute("idr");
	}

	public void pay() {	
		
		this.insiteOperation = MessageBuilderFactory.buildMessage(merchant, terminal, amount, currency, id, order, "REDSYS");

		this.insiteService = new InsiteOperationService(merchantSignature, Environment.SANDBOX);	
		try {
			log.info(String.valueOf(insiteOperation));
			InsiteResponseMessage insiteResponse = (InsiteResponseMessage) insiteService.sendOperation(insiteOperation);
			log.info(String.valueOf(insiteResponse));
			switch (insiteResponse.getResult()) {
			case OK:
				log.info("Operation was OK");
				check = "Operacion aceptada";
				break;
			case AUT:
				log.info("Operation requires authentication");
				check = "Esta operacion necesitoa autentificacion";
				break;
			default:
				log.info("Operation was not OK");
				check = "Operacion incorrecta";
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void cleanContext() {

		MgnlContext.removeAttribute("idr", 2);
		MgnlContext.removeAttribute("ordenInterna", 2);
		MgnlContext.removeAttribute("idi", 2);
		MgnlContext.removeAttribute("proceed", 2);
		MgnlContext.removeAttribute("currency", 2);
		MgnlContext.removeAttribute("merchant", 2);
		MgnlContext.removeAttribute("merchantSignature", 2);
		MgnlContext.removeAttribute("terminal", 2);

	}
	
	public String getProceedUrlRest() {
		return proceedUrlRest;
	}

	public void setProceedUrlRest(String proceedUrlRest) {
		this.proceedUrlRest = proceedUrlRest;
	}

	public String getOrdenInternaMod() {
		return ordenInternaMod;
	}

	// getter y setters
	public void setOrdenInternaMod(String ordenInternaMod) {
		this.ordenInternaMod = ordenInternaMod;
	}

	public String getId() {
		return id;
	}

	public String getCheck() {
		return check;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getMerchantSignature() {
		return merchantSignature;
	}

	public void setMerchantSignature(String merchantSignature) {
		this.merchantSignature = merchantSignature;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCheck(String check) {
		this.check = check;
	}

}