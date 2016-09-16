package algos;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;

/**
 * Created by viveksrivastava on 18/06/15.
 */
public class HTTPGet {

	protected HttpClient client;
	private static HTTPGet instance;

	public static HTTPGet getInstance() {
		if (instance == null) {
			instance = new HTTPGet();

			MultiThreadedHttpConnectionManager connManag = new MultiThreadedHttpConnectionManager();
			HttpConnectionManagerParams managParams = connManag.getParams();

			managParams.setConnectionTimeout(10000); // 1
			managParams.setSoTimeout(10000); //2

			instance.client = new HttpClient(connManag);
		}
		return instance;
	}

	public byte[] connectSyncApache_get(String url) {
		GetMethod method = null;
		try {
			HttpClientParams params = client.getParams();
			params.setSoTimeout(0);
			method = new GetMethod(url);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, new Integer(0));
			method.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS algos.X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
			int statusCode = client.executeMethod(method);

			System.out.println("URL = " + url + " " + statusCode);

			switch (statusCode) {
				case 200:
				case 201:
					return IOUtils.toByteArray(method.getResponseBodyAsStream());
			}

			return null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (method != null)
				method.releaseConnection();
		}
		return null;
	}

	public static void main(String[] args) {
		HTTPGet httpGet = getInstance();
//		httpGet.connectSyncApache_get("http://www.binlist.net/json/454198");
		httpGet.connectSyncApache_get("http://www.binlist.net/xml/431940");
	}
}
