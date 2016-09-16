package algos;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by viveksrivastava on 27/10/15.
 */
public class EncryptionManager {
	public static void main(String[] args) throws Exception {
		EncryptionManager encryptionManager = new EncryptionManager();
		Key impsgatewayKey = encryptionManager.getPrivateKey("/Users/viveksrivastava/Documents/workspace/ImpsGateway/keystore.jks", "profit123".toCharArray(), "impsgateway");
		Key publicKey = encryptionManager.getPublicKey("/Users/viveksrivastava/Documents/workspace/ImpsGateway/client.cer");

		byte[] encrypted = encryptionManager.encrypt(impsgatewayKey, "Testing encryption");
		byte[] decrypt = encryptionManager.decrypt(publicKey, encrypted);

		System.out.println("new String(decrypt) = " + new String(decrypt));
	}

	private static String hex(String binStr) {

		String newStr = "";

		try {
			String hexStr = "0123456789ABCDEF";
			byte[] p = binStr.getBytes();
			for (int k = 0; k < p.length; k++) {
				int j = (p[k] >> 4) & 0xF;
				newStr = newStr + hexStr.charAt(j);
				j = p[k] & 0xF;
				newStr = newStr + hexStr.charAt(j) + " ";
			}
		} catch (Exception e) {
			System.out.println("Failed to convert into hex values: " + e);
		}
		return newStr;
	}

	public byte[] encrypt(Key key, String plaintext)
			throws Exception {

		// Create a cipher using that key to initialize it
		Cipher cipher = Cipher.getInstance("algos.RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] plaintextBytes = plaintext.getBytes();

		// Perform the actual encryption

		return cipher.doFinal(plaintextBytes);
	}

	public byte[] decrypt(Key key, byte[] plaintext)
			throws Exception {

		// Create a cipher using that key to initialize it
		Cipher cipher = Cipher.getInstance("algos.RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);

		// Perform the actual decryption

		return cipher.doFinal(plaintext);
	}

	public Key getPrivateKey(String keyStoreFile, char[] password, String alias) throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException, UnrecoverableKeyException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		java.io.FileInputStream fis = new java.io.FileInputStream(keyStoreFile);
		ks.load(fis, password);
		fis.close();
		return ks.getKey(alias, password);
	}

	public Key getPublicKey(String certificateFile) throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException, UnrecoverableKeyException, NoSuchPaddingException, InvalidKeyException {
		InputStream inStream = new FileInputStream(certificateFile);
		CertificateFactory cf = CertificateFactory.getInstance("algos.X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
		inStream.close();

		// Read the public key from certificate file
		RSAPublicKey pubkey = (RSAPublicKey) cert.getPublicKey();
		byte[] tempPub = null;
		String sPub = null;
		tempPub = pubkey.getEncoded();
		sPub = new String(tempPub);
		System.out.println("Public key from certificate file:\n" + hex(sPub) + "\n");
		System.out.println("Public Key Algorithm = " + cert.getPublicKey().getAlgorithm() + "\n");

		return pubkey;
	}
}
