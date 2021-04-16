package com.goCamping.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Encrypt {
	private static final Logger logger = LogManager.getLogger(Encrypt.class);

	public Map<String, Object> createKey() {

		Map<String, Object> map = new HashMap<String, Object>();

		try {

			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);
			KeyPair keyPair = generator.genKeyPair();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();

			RSAPublicKeySpec publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			String publicKeyModulus = publicSpec.getModulus().toString(16);
			String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

			map.put("_RSA_WEB_KEY_", privateKey);
			map.put("RSAModulus", publicKeyModulus);
			map.put("RSAExponent", publicKeyExponent);

			logger.info("_RSA_WEB_KEY_ : " + privateKey);
			logger.info("RSAModulus : " + publicKeyModulus);
			logger.info("RSAExponent : " + publicKeyExponent);

		} catch (Exception e) {

			logger.info("createKey Exception Error");
			e.printStackTrace();
			return null;
		}

		return map;
	}

	public Boolean decryptRsa(PrivateKey privateKey, String[] encrypt_arry) {

		byte[] encryptedBytes;
		byte[] decryptedBytes;

		/*
		 * 암호화 된 값은 byte 배열이다. 이를 문자열 폼으로 전송하기 위해 16진 문자열(hex)로 변경한다. 서버측에서도 값을 받을 때 hex
		 * 문자열을 받아서 이를 다시 byte 배열로 바꾼 뒤에 복호화 과정을 수행한다.
		 */

		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			for (int i = 0; i < encrypt_arry.length; i++) {
				
				encryptedBytes = hexToByteArray(encrypt_arry[i]);
				
				decryptedBytes = cipher.doFinal(encryptedBytes);

				encrypt_arry[i] = new String(decryptedBytes, "utf-8"); // 문자 인코딩 주의.

			}


		} catch (Exception e) {

			logger.info("decryptRsa Exception Error");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public byte[] hexToByteArray(String hex) {

		if (hex == null || hex.length() % 2 != 0) {
			return new byte[] {};
		}

		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			bytes[(int) Math.floor(i / 2)] = value;
		}
		return bytes;

	}
}
