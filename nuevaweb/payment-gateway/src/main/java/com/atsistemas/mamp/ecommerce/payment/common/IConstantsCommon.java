/**
 * 
 */
package com.atsistemas.mamp.ecommerce.payment.common;


/**
 * 
 *
 */
public interface IConstantsCommon {
	
	String MODULE_NAME = "payment-gateway";
	String MAGNOLIA_VERSION = "Magnolia 5.5";
	String INSTALL_BOOTSTRAP_ROOT_FOLDER = "mgnl-bootstrap";
	String INSTALL_BOOTSTRAP_FINAL_FOLDER = "install";
	String DOT_EXTENSION_BOOTSTRAP_FILE = ".xml";
	
	String SEED_FILE = "C:\\ECM\\Ficheros seguridad\\seed.properties";
	String PROPERTIES_FILE = "C:\\ECM\\Ficheros seguridad\\data.properties";
	
	String PUBLIC_KEY_PROPERTY = "publicKey";
	String URL_SUBSCRIBER_PROPERTY = "URL";
	String ACTIVE_SUBSCRIBER_PROPERTY = "active";
	String IS_FIRST_INSTALL_PROPERTY = "isFirstInstall";
	String IS_STATIC_PROPERTY = "isStatic";
	
	String DEMO_WEBSITE_NAME_PREFIX = "demo";
	String DEMO_SITE_DEFINITION_NAME_PREFIX = "demo";
	String STATIC_DOMAIN_DATA_PROPERTY_PREFIX = "STATIC_DOMAIN";
	String DOMAIN_DATA_PROPERTY_PREFIX = "DOMAIN";
	
	String GET_REQUEST_METHOD = "GET";
	
	String UTF8 = "UTF-8";
	
	String SERVER_SUBSCRIBERS_PATH = "/server/activation/subscribers";
	String CONFIG_SITES_PATH = "/modules/multisite/config/sites";
	
	String SITE_DEF_DOMAINS_NODE_NAME = "domains";
	
	String MAGNOLIA_WORKSPACE_CONFIG = "config";
	String MAGNOLIA_PATH_TO_INGENICO_CONFIG_NODE = "/modules/payment-gateway/ingenico";
	String SDK_CONFIG_NODE_COMMON_NAME = "common";
	
	String MAGNOLIA_PATH_TO_PAYPAL_CONFIG_NODE = "/modules/payment-gateway/paypal";
	
	String[] NODE_TYPE_PATH_TO_EXCLUDE_CONFIG_PAYMENTS = {"mgnl:","jcr:"};
	
}
