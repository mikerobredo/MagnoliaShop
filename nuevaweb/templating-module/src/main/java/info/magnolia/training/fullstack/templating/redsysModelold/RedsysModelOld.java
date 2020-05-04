package info.magnolia.training.fullstack.templating.redsysModelold;

import javax.inject.Inject;
import javax.jcr.Node;
// como instalar el modulo de maven install-file -Dfile=C:\Users\mnunez.robredo\Desktop\insite-api.jar -DgroupId=es.redsys.insite.api -DartifactId=insite-api -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar
import javax.jcr.RepositoryException;

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



/**
 * Model of the Redirect Template. 2 Redirect mode are available (depending of
 * the 'path' value)
 * <ul>
 * <li>Empty path: This will redirect to the first child page found. This is
 * useful to directly display a page defined in a second or third level of a
 * redirect tree</li>
 * <li>Internal page link: Redirect directly to the selected page</li>
 * </ul>
 * If the redirect is define (not empty path) the rendering is skipped avoiding
 * the writing of the response (not allowed for response.redirect instruction).
 */
public class RedsysModelOld<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<RD> {

	private String id;
	private String check;
	private String merchant;
	private String terminal;
	private String amount;
	private String currency;
	private String order;
	private String merchantSignature;
	
	private Node content;

	@Inject
	public RedsysModelOld(Node content, RD definition, RenderingModel<?> parent) {
		super(content, definition, parent);
		this.content = content;
	}

	@Override
	public String execute() {
        System.out.println("weeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		// datos necesarios apra la conexion los cogemos del contenido del componente
		try {
			if(content.hasProperty("currency") &&
					content.hasProperty("merchant") &&
					content.hasProperty("merchantSignature") &&
					content.hasProperty("terminal") ) {	
				
				this.currency=content.getProperty("currency").getString();
				this.merchant = content.getProperty("merchant").getString();		// fuc
				this.merchantSignature = content.getProperty("merchantSignature").getString();
				this.terminal = content.getProperty("terminal").getString();
				
			}
		} catch (RepositoryException e) {
			
			e.printStackTrace();
		}

		// datos necesarios apra la conexion

		//this.merchant = "999008881"; // fuc
		//this.terminal = "957";
		this.amount = "999";
		//this.currency = "978";
		//this.order = "523358461614"; // id de la comrpra que le damos
		//this.merchantSignature = "holaasdfqwerty123456789000000000";
		// String reference = "referencefromotheroperation";

		// recorrer el nodo
		// /redsys-test/main/00
		if(MgnlContext.getParameter("order") != null) 
		{
		this.order = MgnlContext.getParameter("order");
		//System.out.println(order);
		}
		else {System.out.println("orden es nula");}

		id = MgnlContext.getParameter("id");
		//MgnlContext.setAttribute("order", "valor de order", Context.SESSION_SCOPE);
		//int i = Context.SESSION_SCOPE;
		if (id != null) {
			String operID = id;

			// Operation POJO
			InsiteOperationMessage insiteOperation = new InsiteOperationMessage();
			insiteOperation.setMerchant(merchant);
			insiteOperation.setTerminal(terminal);
			insiteOperation.setAmount(amount);
			insiteOperation.setCurrency(currency);
			insiteOperation.setOperID(operID);
			insiteOperation.setOrder(order);

			insiteOperation.setTransactionType(TransactionType.AUTHORIZATION);
			// Optional parameters: securePayment for authentication pahse, directPayment
			// for bypass security
			// insiteOperation.useSecurePayment();
			insiteOperation.useDirectPayment();
			// insiteOperation.useReference(reference);
			// Service definition
			InsiteService insiteService = new InsiteOperationService(merchantSignature, Environment.SANDBOX);

			try {
				System.out.println(insiteOperation);
				InsiteResponseMessage insiteResponse = (InsiteResponseMessage) insiteService
						.sendOperation(insiteOperation);
				System.out.println(insiteResponse);
				switch (insiteResponse.getResult()) {
				case OK:
					System.out.println("Operation was OK");
					check = "Operacion aceptada";
					break;
				case AUT:
					System.out.println("Operation requires authentication");
					check = "Esta operacion necesitoa autentificacion";
					break;
				default:
					System.out.println("Operation was not OK");
					check = "Operacion incorrecta";
					break;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return super.execute();
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