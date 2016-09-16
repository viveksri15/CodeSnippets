package algos;

import org.apache.commons.io.FileUtils;

import javax.net.ssl.*;
import java.io.DataOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by viveksrivastava on 02/08/15.
 */
public class SendFile {
	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	public static void main(String[] args) throws Exception {
		byte[] bytes = FileUtils.readFileToByteArray(new File("/Users/viveksrivastava/Documents/workspace/Test/debit.tsv"));
		sendPost("http://52.74.110.137:8080/userver_dev/customer/upload_data_file_98ocwi2038h10hxm_1238h_1983hubv1?name=vivek1&username=test&pass=test",
				bytes);
//		sendPost(args[0], bytes);

	}

	private static void sendPost(String urlS, byte[] bytes) throws Exception {

		String url = urlS;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

//		con.setHostnameVerifier(DO_NOT_VERIFY);
		SSLContext context = SSLContext.getInstance("TLS");
		TrustManager[] tmlist = {new MyTrustManager()};
		context.init(null, tmlist, null);
//		con.setSSLSocketFactory(context.getSocketFactory());

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "multipart/form-data");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(bytes);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
	}

	private static class MyTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	}
}
