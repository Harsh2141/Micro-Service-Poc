package com.tender247.poc.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EncryptDecryptUtil {

	private final String SECRET_KEY = "MicroservicesPOC";
	private final String ALGORITHM = "AES";
	private final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

	private Cipher getCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(mode, secretKeySpec);
		return cipher;
	}

	public String encrypt(String data) {
		try {
			return Base64.getEncoder()
					.encodeToString(getCipher(Cipher.ENCRYPT_MODE).doFinal(data.getBytes(StandardCharsets.UTF_8)));

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String decrypt(String data) {
		try {
			return new String(getCipher(Cipher.DECRYPT_MODE).doFinal(Base64.getDecoder().decode(data)));

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

}
