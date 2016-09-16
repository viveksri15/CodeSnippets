package algos;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by viveksrivastava on 29/08/15.
 */
public class YesBank {

	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	public static void main(String[] args) throws Exception {
		send(args[0]);
	}

	private static void send(String urlS) throws Exception {

		String url = urlS;
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		con.setHostnameVerifier(DO_NOT_VERIFY);
		SSLContext context = SSLContext.getInstance("TLS");
		TrustManager[] tmlist = {new MyTrustManager()};
		context.init(null, tmlist, null);
		con.setSSLSocketFactory(context.getSocketFactory());

		//add reuqest header
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		char[] raw_resp = new char[1024];
		int raw_resp_len = in.read(raw_resp);

		StringBuffer s = new StringBuffer();
		s.append(raw_resp, 0, raw_resp_len);
		String resp = s.toString();
		System.out.println("resp = " + resp);
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
