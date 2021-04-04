package com.goCamping.service;

import java.security.PrivateKey;
import java.util.Map;

import com.goCamping.dto.EncryptDTO;

public interface EncryptService {
	
	public Map<String, Object> createKey();

	public EncryptDTO decryptRsa(PrivateKey privateKey, EncryptDTO encryptDTO) throws Exception;
	
	public byte[] hexToByteArray(String hex);
}
