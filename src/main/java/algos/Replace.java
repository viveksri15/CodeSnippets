package algos;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Created by viveksrivastava on 30/07/15.
 */
public class Replace {
	public static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";


	public static void main(String[] args) {
		System.out.println("encrypt(\"\") = " + encrypt("d8F(]Qd-s]%`s.Q2bT8J"));
	}

	public static String encrypt(String cleanText) {

		String secretKeyStr = "mynameislakhan";

		SecretKey secretKey = null;
		try {
			secretKey = generateSecretKey(secretKeyStr.toCharArray());
			System.out.println("secretKey = " + secretKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}


		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] stringBytes = cleanText.getBytes("UTF8");
			// encrypt using the cypher
			byte[] raw = cipher.doFinal(stringBytes);
			return new String(Hex.encodeHex(raw));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static SecretKey generateSecretKey(char[] passphraseOrPin)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		final byte[] salt = "12345".getBytes();
		final int iterations = 1000;
		// Generate a 256-bit key
		final int outputKeyLength = 256;
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec keySpec = new PBEKeySpec(passphraseOrPin, salt, iterations, outputKeyLength);
		return secretKeyFactory.generateSecret(keySpec);
	}
}
