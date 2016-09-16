package algos;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * Created by viveksrivastava on 11/09/15.
 */
public class HTTPSApache {
	public static void main(String[] args) throws IOException {
		fireHTTPHit(args[0]);
	}

	public static void fireHTTPHit(String arg) throws IOException {
		
		HttpClient httpclient = new HttpClient();
		GetMethod httpget = new GetMethod(arg);
		try {
			httpclient.executeMethod(httpget);
			System.out.println(httpget.getStatusLine());
			System.out.println("httpget = " + new String(httpget.getResponseBody()));
		} finally {
			httpget.releaseConnection();
		}
	}

}