package info.magnolia.training.fullstack.templating.redsysModel;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.atsistemas.mamp.ecommerce.payment.PaymentGateway;

import info.magnolia.context.MgnlContext;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;
//como instalar el modulo de maven install-file -Dfile=C:\Users\mnunez.robredo\Desktop\insite-api.jar -DgroupId=es.redsys.insite.api -DartifactId=insite-api -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar
import info.magnolia.templating.functions.TemplatingFunctions;
import info.magnolia.training.fullstack.templating.redsysModel.RedsysModelUtils;

public class RedsysModel<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private String id;
	private String check;
	private String merchant;
	private String terminal;
	private String amount;
	private String currency;
	private String order;
	private String merchantSignature;
	private String ordenInternaMod;
	private String restUrl;
	
	@Inject
	private TemplatingFunctions templatingFunctions;
	
	@Inject
	private PaymentGateway paymentGateway;

	@Inject
	private RedsysModelUtils redsysModelUtils;
	
	
	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrl) {
		this.restUrl = restUrl;
	}

	@Inject
	public RedsysModel(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);
	}
	
	@Override
	public String execute() {		
		try {
			setDataInContexFromComponent();
		} catch (RepositoryException e) {
			
			e.printStackTrace();
		}
		this.restUrl = this.redsysModelUtils.buildUrlRest(MgnlContext.getAggregationState().getOriginalURL(), this.paymentGateway.getRedsysConfig().getRestUrlIdRedsys());	
		return super.execute();		
	}
	public void setDataInContexFromComponent() throws RepositoryException {
		
		if (checkPaymentData()){
			getPaymentData();
			setPaymentDataInContext();
		}		
		this.amount = "999";
		this.id = MgnlContext.getAttribute("idr");		
		if (MgnlContext.getAttribute("ordenInterna") != null) {
			this.order = MgnlContext.getAttribute("ordenInterna").toString();
		}
	}
	
	private boolean checkPaymentData() throws RepositoryException {
		boolean c1 = false;
		
			if(isMerchantDataAvailable())
			{c1= true;}
			else 
			{c1 = false;}
		
		return c1;
	}
	
	private boolean isMerchantDataAvailable() throws RepositoryException
	{
		boolean a=false;
		if(		content.hasProperty("currency") 
				&& content.hasProperty("merchant")
				&& content.hasProperty("merchantSignature") 
				&& content.hasProperty("terminal"))
		{a =true;}
		return a;
		
	}

	
	private void getPaymentData() {
		try {
		this.currency = content.getProperty("currency").getString();
		this.merchant = content.getProperty("merchant").getString(); 
		this.merchantSignature = content.getProperty("merchantSignature").getString();
		this.terminal = content.getProperty("terminal").getString();
		} catch (RepositoryException e) {e.printStackTrace();}
	}
	
	private void setPaymentDataInContext() {
		MgnlContext.setAttribute("currency", currency, 2);
		MgnlContext.setAttribute("merchant", merchant, 2);
		MgnlContext.setAttribute("merchantSignature", merchantSignature, 2);
		MgnlContext.setAttribute("terminal", terminal, 2);		
	}
	
	public String getOrdenInternaMod() {
		return ordenInternaMod;
	}

	public void setOrdenInternaMod(String ordenInternaMod) {
		this.ordenInternaMod = ordenInternaMod;
	}


	// getter y setters
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