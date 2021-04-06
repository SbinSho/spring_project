package com.goCamping.service;

import java.security.PrivateKey;
import java.util.Map;

public interface EncryptService {
	
	public Map<String, Object> createKey();

	public Boolean decryptRsa(PrivateKey privateKey, String[] encrypt_arry) throws Exception;
	
	public byte[] hexToByteArray(String hex);
}
