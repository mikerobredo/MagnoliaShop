package com.atsistemas.mamp.ecommerce.payment.beans.ingenico;

/**
 * 
 *
 */
public class SdkConfigIngenico {

	private String PSPID;
	
	private String ACCEPT_URL;
	
	private String DECLINE_URL;
	
	private String EXCEPTION_URL;
	
	private String OK_URL;
	
	private String KO_URL;
	
	private String API_USERID;
	
	private byte[] API_USER_PSWD;
	
	private byte[] API_PASSPHRASE_SHAIN;
	
	private byte[] API_PASSPHRASE_SHAOUT;

	public String getPSPID() {
		return PSPID;
	}

	public void setPSPID(String pSPID) {
		PSPID = pSPID;
	}

	public String getACCEPT_URL() {
		return ACCEPT_URL;
	}

	public void setACCEPT_URL(String aCCEPT_URL) {
		ACCEPT_URL = aCCEPT_URL;
	}

	public String getDECLINE_URL() {
		return DECLINE_URL;
	}

	public void setDECLINE_URL(String dECLINE_URL) {
		DECLINE_URL = dECLINE_URL;
	}

	public String getEXCEPTION_URL() {
		return EXCEPTION_URL;
	}

	public void setEXCEPTION_URL(String eXCEPTION_URL) {
		EXCEPTION_URL = eXCEPTION_URL;
	}

	public String getOK_URL() {
		return OK_URL;
	}

	public void setOK_URL(String oK_URL) {
		OK_URL = oK_URL;
	}

	public String getKO_URL() {
		return KO_URL;
	}

	public void setKO_URL(String kO_URL) {
		KO_URL = kO_URL;
	}

	public String getAPI_USERID() {
		return API_USERID;
	}

	public void setAPI_USERID(String aPI_USERID) {
		API_USERID = aPI_USERID;
	}

	public String getStringAPI_USER_PSWD() {
		return new String(API_USER_PSWD);
	}

	public void setAPI_USER_PSWD(byte[] aPI_USER_PSWD) {
		API_USER_PSWD = aPI_USER_PSWD;
	}

	public String getStringAPI_PASSPHRASE_SHAIN() {
		return new String(API_PASSPHRASE_SHAIN);
	}

	public byte[] getAPI_PASSPHRASE_SHAIN() {
		return API_PASSPHRASE_SHAIN;
	}

	public void setAPI_PASSPHRASE_SHAIN(byte[] aPI_PASSPHRASE_SHAIN) {
		API_PASSPHRASE_SHAIN = aPI_PASSPHRASE_SHAIN;
	}

	public String getStringAPI_PASSPHRASE_SHAOUT() {
		return new String(API_PASSPHRASE_SHAOUT);
	}

	public byte[] getAPI_PASSPHRASE_SHAOUT() {
		return API_PASSPHRASE_SHAOUT;
	}

	public void setAPI_PASSPHRASE_SHAOUT(byte[] aPI_PASSPHRASE_SHAOUT) {
		API_PASSPHRASE_SHAOUT = aPI_PASSPHRASE_SHAOUT;
	}

	public byte[] getAPI_USER_PSWD() {
		return API_USER_PSWD;
	}
	
}
