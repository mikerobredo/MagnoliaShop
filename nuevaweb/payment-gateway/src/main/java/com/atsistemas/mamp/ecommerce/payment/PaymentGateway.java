package com.atsistemas.mamp.ecommerce.payment;

import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsCommon.MAGNOLIA_PATH_TO_INGENICO_CONFIG_NODE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsCommon.MAGNOLIA_PATH_TO_PAYPAL_CONFIG_NODE;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsCommon.MAGNOLIA_WORKSPACE_CONFIG;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_ACCEPT_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_API_PASSPHRASE_SHAIN;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_API_PASSPHRASE_SHAOUT;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_API_USER_ID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_API_USER_PSWD;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_DECLINE_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_ENDPOINT_ALIAS_GATEWAY;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_ENDPOINT_DIRECT_LINK;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_EXCEPTION_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_KO_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_OK_URL;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico.SDK_CONFIG_PROP_PSPID;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsPaypal.SDK_CONFIG_NODE_COMMON_NAME;
import static com.atsistemas.mamp.ecommerce.payment.common.IConstantsPaypal.SDK_CONFIG_PROP_ENDPOINT_PAYPAL;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atsistemas.mamp.ecommerce.payment.beans.ingenico.SdkConfigIngenico;
import com.atsistemas.mamp.ecommerce.payment.beans.paypal.SdkConfigPaypal;
import com.atsistemas.mamp.ecommerce.payment.cipher.interfaces.ICipherUtils;
import com.atsistemas.mamp.ecommerce.payment.common.ConstantsIngenico;
import com.atsistemas.mamp.ecommerce.payment.common.ConstantsPaypal;
import com.atsistemas.mamp.ecommerce.payment.common.IConstantsCommon;
import com.atsistemas.mamp.ecommerce.payment.common.IConstantsIngenico;
import com.atsistemas.mamp.ecommerce.payment.common.IConstantsPaypal;

import info.magnolia.context.MgnlContext;
import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;
import info.magnolia.training.fullstack.templating.redsysModel.RedsysConfig;

/**
 * This class is optional and represents the configuration for the module.
 * By exposing simple getter/setter/adder methods, this bean can be configured via content2bean
 * using the properties and node from <tt>config:/modules/my-module-name</tt>.
 * If you don't need this, simply remove the reference to this class in the module descriptor xml.
 */
public class PaymentGateway implements ModuleLifecycle {
	
    /* you can optionally implement info.magnolia.module.ModuleLifecycle */

	private static Logger log = LoggerFactory.getLogger(PaymentGateway.class);

	private RedsysConfig redsysConfig;
	
	@Inject
	private ICipherUtils cipherUtils;

	@Override
	public void start(ModuleLifecycleContext moduleLifecycleContext) {
		
		try {

			log.info("Load SDK Paypal configuration..");
			loadSdkPaypalConfig();

			log.info("Load SDK Ingenico configuration..");
			loadSdkIngenicoConfig();
			
		} catch (LoginException e) {

			log.error("Login exception: " + e.getMessage());

		} catch (RepositoryException e) {

			log.error("Repository exception: " + e.getMessage());
			
		} catch (IOException e) {
			
			log.error("I/O exception: " + e.getMessage());
		}
		
	}

	@Override
	public void stop(ModuleLifecycleContext moduleLifecycleContext) {
		
	}
	
	private void loadSdkPaypalConfig() throws LoginException, RepositoryException, IOException {
		
		Session session = MgnlContext.getJCRSession(MAGNOLIA_WORKSPACE_CONFIG);

		Node serverNode = session.getNode(MAGNOLIA_PATH_TO_PAYPAL_CONFIG_NODE);
		if (serverNode != null) {
			
			InputStream inputStream = new FileInputStream(IConstantsCommon.SEED_FILE);  
			Properties keyProp = new Properties();
			keyProp.load(inputStream);
			String secretKey = keyProp.getProperty("key").trim();
			
			NodeIterator nodeIterator = serverNode.getNodes();
			if (nodeIterator.hasNext()) {
			
				Node currentNode = null;
				while (nodeIterator.hasNext()) {
					
					currentNode = nodeIterator.nextNode();
					
					if (SDK_CONFIG_NODE_COMMON_NAME.equalsIgnoreCase(currentNode.getName())) {
						this.loadSdkPaypalCommonConfig(currentNode);
					}else {
						this.loadSdkPaypalSiteConfig(currentNode, secretKey);
					}
				}
				
			}else {
				log.error("Missing subnodes in Paypal config. node!!");
			}
			
			// Reset de memoria de info. sensible
			keyProp = null;
			secretKey = null;
			
		}else {
			log.error("Paypal config. node not found!!");
		}
	}
	
	private void loadSdkPaypalCommonConfig(Node currentNode) throws RepositoryException {
		
		Map<String, String> paypalSdkConfigClear = new HashMap<String, String>();
		
		Property currentProperty = null;
		for (PropertyIterator propertyIterator = currentNode.getProperties(); propertyIterator.hasNext();) {
			
			currentProperty = propertyIterator.nextProperty();
			
			if (! this.isMagnoliaPropertyType(currentProperty.getName())) {
				
				if (SDK_CONFIG_PROP_ENDPOINT_PAYPAL.equalsIgnoreCase(currentProperty.getName())) {
					
					ConstantsPaypal.setENDPOINT(currentProperty.getValue().getString().trim());
					
				}else {
					paypalSdkConfigClear.put(currentProperty.getName(), currentProperty.getValue().getString().trim());
				}
			}
		}
		
		if (! paypalSdkConfigClear.isEmpty()) {
			
			ConstantsPaypal.setSDK_CONFIG_CLEAR(paypalSdkConfigClear);
			
		}else {
			
			log.error("Missing properties in Paypal " + currentNode.getName() + " config. node!!");
		}
	}
	
	private void loadSdkPaypalSiteConfig(Node currentNode, String secretKey) throws RepositoryException {

		Map<String, String> paypalSdkConfigClear = new HashMap<String, String>();
		Map<String, byte[]> paypalSdkConfigEncrypted = new HashMap<String, byte[]>();
		
		Property currentProperty = null;
		for (PropertyIterator iterator = currentNode.getProperties(); iterator.hasNext();) {
			
			currentProperty = iterator.nextProperty();
			
			// There is only one encrypted property by now
			if (IConstantsPaypal.SDK_CONFIG_PROP_ACCT1_PWD.equalsIgnoreCase(currentProperty.getName())) {
				
				paypalSdkConfigEncrypted.put(currentProperty.getName(), this.cipherUtils.decrypt(currentProperty.getValue().getString().trim(), secretKey));
				
			}else {
				if (! this.isMagnoliaPropertyType(currentProperty.getName())) {
					paypalSdkConfigClear.put(currentProperty.getName(), currentProperty.getValue().getString().trim());
				}
			}
		}
		
		if (! paypalSdkConfigClear.isEmpty() && ! paypalSdkConfigEncrypted.isEmpty()) {
			
			SdkConfigPaypal sdkConfigPaypal = new SdkConfigPaypal();
			sdkConfigPaypal.setSDK_CONFIG_CLEAR(paypalSdkConfigClear);
			sdkConfigPaypal.setSDK_CONFIG_ENCRYPTED(paypalSdkConfigEncrypted);
			
			// Initialize map if necessary
			if (ConstantsPaypal.getMapSdkConfigSites() == null) {
				ConstantsPaypal.setMapSdkConfigSites(new HashMap<String, SdkConfigPaypal>());
			}
			
			// Add new site config.
			ConstantsPaypal.getMapSdkConfigSites().put(currentNode.getName(), sdkConfigPaypal);
			
		}else {
			log.error("Missing properties in Paypal " + currentNode.getName() + " config. node!!");
		}
	}
	
	private void loadSdkIngenicoConfig() throws LoginException, RepositoryException, IOException {
		
		Session session = MgnlContext.getJCRSession(MAGNOLIA_WORKSPACE_CONFIG);

		Node serverNode = session.getNode(MAGNOLIA_PATH_TO_INGENICO_CONFIG_NODE);
		if (serverNode != null) {
			
			InputStream inputStream = new FileInputStream(IConstantsCommon.SEED_FILE);  
			Properties keyProp = new Properties();
			keyProp.load(inputStream);
			String secretKey = keyProp.getProperty("key").trim();
			
			NodeIterator nodeIterator = serverNode.getNodes();
			if (nodeIterator.hasNext()) {
			
				Node currentNode = null;
				while (nodeIterator.hasNext()) {
					
					currentNode = nodeIterator.nextNode();
					
					if (IConstantsIngenico.SDK_CONFIG_NODE_COMMON_NAME.equalsIgnoreCase(currentNode.getName())) {
						this.loadSdkIngenicoCommonConfig(currentNode);
					}else {
						this.loadSdkIngenicoSiteConfig(currentNode, secretKey);
					}
				}
				
			}else {
				log.error("Missing subnodes in Ingenico config. node!!");
			}
			
			// Reset de memoria de info. sensible
			keyProp = null;
			secretKey = null;
			
		}else {
			log.error("Ingenico config. node not found!!");
		}
	}
	
	private void loadSdkIngenicoSiteConfig(Node currentNode, String secretKey) throws RepositoryException {
		
		PropertyIterator propertyIterator = currentNode.getProperties();
		if (propertyIterator.hasNext()) {
			
			SdkConfigIngenico currentSdkConfigIngenico = new SdkConfigIngenico();
			Property currentProperty = null;
			while (propertyIterator.hasNext()) {
				
				currentProperty = propertyIterator.nextProperty();
				
				if (SDK_CONFIG_PROP_API_USER_PSWD.equalsIgnoreCase(currentProperty.getName())) {
					
					currentSdkConfigIngenico.setAPI_USER_PSWD(this.cipherUtils.decrypt(currentProperty.getValue().getString().trim(), secretKey));
					
				}else if (SDK_CONFIG_PROP_API_PASSPHRASE_SHAIN.equalsIgnoreCase(currentProperty.getName())) {
					
					currentSdkConfigIngenico.setAPI_PASSPHRASE_SHAIN(this.cipherUtils.decrypt(currentProperty.getValue().getString().trim(), secretKey));
					
				}else if (SDK_CONFIG_PROP_API_PASSPHRASE_SHAOUT.equalsIgnoreCase(currentProperty.getName())) {
					
					currentSdkConfigIngenico.setAPI_PASSPHRASE_SHAOUT(this.cipherUtils.decrypt(currentProperty.getValue().getString().trim(), secretKey));
					
				}else if (SDK_CONFIG_PROP_API_USER_ID.equalsIgnoreCase(currentProperty.getName())) {
					
					currentSdkConfigIngenico.setAPI_USERID(currentProperty.getValue().getString().trim());
					
				}else if (SDK_CONFIG_PROP_PSPID.equalsIgnoreCase(currentProperty.getName())) {
					
					currentSdkConfigIngenico.setPSPID(currentProperty.getValue().getString().trim());
					
				}else if (SDK_CONFIG_PROP_ACCEPT_URL.equalsIgnoreCase(currentProperty.getName())) {
					
					currentSdkConfigIngenico.setACCEPT_URL(currentProperty.getValue().getString().trim());
					
				}else if (SDK_CONFIG_PROP_DECLINE_URL.equalsIgnoreCase(currentProperty.getName())) {
					
					currentSdkConfigIngenico.setDECLINE_URL(currentProperty.getValue().getString().trim());
					
				}else if (SDK_CONFIG_PROP_EXCEPTION_URL.equalsIgnoreCase(currentProperty.getName())) {
					
					currentSdkConfigIngenico.setEXCEPTION_URL(currentProperty.getValue().getString().trim());
					
				}else if (SDK_CONFIG_PROP_KO_URL.equalsIgnoreCase(currentProperty.getName())) {
					
					currentSdkConfigIngenico.setKO_URL(currentProperty.getValue().getString().trim());
					
				}else if (SDK_CONFIG_PROP_OK_URL.equalsIgnoreCase(currentProperty.getName())) {
					
					currentSdkConfigIngenico.setOK_URL(currentProperty.getValue().getString().trim());
					
				}
			}
			
			// Initialize map if necessary
			if (ConstantsIngenico.getMapSdkConfigSites() == null) {
				ConstantsIngenico.setMapSdkConfigSites(new HashMap<String, SdkConfigIngenico>());
			}
			
			// Add new site config.
			ConstantsIngenico.getMapSdkConfigSites().put(currentNode.getName(), currentSdkConfigIngenico);
			
		}else {
			log.error("Missing properties in Ingenico " + currentNode.getName() + " config. node!!");
		}
	}

	private void loadSdkIngenicoCommonConfig(Node currentNode) throws RepositoryException {
		
		PropertyIterator propertyIterator = currentNode.getProperties();
		if (propertyIterator.hasNext()) {
			
			Property currentProperty = null;
			while (propertyIterator.hasNext()) {
				
				currentProperty = propertyIterator.nextProperty();
				
				if (SDK_CONFIG_PROP_ENDPOINT_ALIAS_GATEWAY.equalsIgnoreCase(currentProperty.getName())) {
					
					ConstantsIngenico.setENDPOINT_ALIAS_GATEWAY(currentProperty.getValue().getString().trim());
					
				}else if (SDK_CONFIG_PROP_ENDPOINT_DIRECT_LINK.equalsIgnoreCase(currentProperty.getName())) {
					
					ConstantsIngenico.setENDPOINT_DIRECT_LINK(currentProperty.getValue().getString().trim());
					
				}else if (SDK_CONFIG_PROP_ENDPOINT_PAYPAL.equalsIgnoreCase(currentProperty.getName())) {
					
					ConstantsIngenico.setSERVICE_ENDPOINT_PAYPAL(currentProperty.getValue().getString().trim());
					
				}
			}
			
		}else {
			log.error("Missing properties in Ingenico " + currentNode.getName() + " config. node!!");
		}
	}
	
	private boolean isMagnoliaPropertyType(String propertyName) {
		
		boolean ret = false;
		
		forMagPropType:for (String currentMagnoliaPropType : IConstantsCommon.NODE_TYPE_PATH_TO_EXCLUDE_CONFIG_PAYMENTS) {
			
			if (propertyName.contains(currentMagnoliaPropType)) {
			
				ret = true;
				break forMagPropType;
			}
		}
		
		return ret;
	}

	public RedsysConfig getRedsysConfig() {
		return redsysConfig;
	}

	public void setRedsysConfig(RedsysConfig redsysConfig) {
		this.redsysConfig = redsysConfig;
	}
	
}
