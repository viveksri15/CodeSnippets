package algos;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by viveksrivastava on 31/01/16.
 */
public class HttpConnection {
	private static Logger logger = Logger.getLogger(HttpConnection.class.getName());

	private static HttpConnection instance;
	protected HttpClient client;

	public static HttpConnection getInstance() {
		if (instance == null) {
			instance = new HttpConnection();

			MultiThreadedHttpConnectionManager connManag = new MultiThreadedHttpConnectionManager();
			HttpConnectionManagerParams managParams = connManag.getParams();

			managParams.setConnectionTimeout(60000); // 1
			managParams.setSoTimeout(60000); //2

			instance.client = new HttpClient(connManag);
		}
		return instance;
	}

	public String connectSyncApache_put(String url, String input) {


		long t0 = System.currentTimeMillis();


		PutMethod method = null;
		String value = null;
		logger.info("REQUEST=" + url + "," + input);
		try {

			method = new PutMethod(url);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);

			StringRequestEntity requestEntity;
			requestEntity = new StringRequestEntity(input,
					"application/x-www-form-urlencoded", "UTF-8");

			method.setRequestEntity(requestEntity);

			int statusCode = client.executeMethod(method);

			switch (statusCode) {
				case 200:
				case 201:
					BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line).append("\n");
					}
					br.close();
					value = sb.toString();
					return value;
			}
			return null;

		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
		} finally {
			logger.info("TIMETAKE=" + (System.currentTimeMillis() - t0));
			logger.info("RESPONSE=" + value);

			if (method != null)
				method.releaseConnection();
		}
		return null;
	}

	public byte[] connectSyncApache_get(String url) {
		return connectSyncApache_get(url, 60000);
	}

	public byte[] connectSyncApache_get(String url, int timeout) {


		long t0 = System.currentTimeMillis();


		GetMethod method = null;
		logger.info("REQUEST=" + url);
		byte[] bytes = null;
		try {
			method = new GetMethod(url);
//			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
//					new DefaultHttpMethodRetryHandler(1, false));
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
			method.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS algos.X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
			int statusCode = client.executeMethod(method);

			logger.info("URL = " + url + " " + statusCode);

			switch (statusCode) {
				case 200:
				case 201:
					BufferedInputStream br = new BufferedInputStream(method.getResponseBodyAsStream());
					bytes = IOUtils.toByteArray(br);
					return bytes;
			}

			return null;

		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
		} finally {
			if (bytes != null)
				logger.info("REQUEST=" + new String(bytes));
			else
				logger.info("REQUEST=NULL");
			if (method != null)
				method.releaseConnection();
			logger.info("TIMETAKE=" + (System.currentTimeMillis() - t0));
		}
		return null;
	}

	public byte[] connectSyncApache_post(String url, byte[] requestStream) {


		long t0 = System.currentTimeMillis();


		PostMethod method = null;
		logger.info("REQUEST=" + url);
		logger.info("REQUEST_BODY=" + new String(requestStream));
		byte[] bytes = null;
		try {
			method = new PostMethod(url);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);

			method.setRequestHeader("Content-Type", "application/octet-stream");
			method.setRequestHeader("Content-Length", "" + requestStream.length);

			method.setRequestEntity(new ByteArrayRequestEntity(requestStream));

			int statusCode = client.executeMethod(method);

			switch (statusCode) {
				case 200:
				case 201:
					BufferedInputStream br = new BufferedInputStream(method.getResponseBodyAsStream());
					bytes = IOUtils.toByteArray(br);
					return bytes;
			}

			return null;

		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
		} finally {
			if (bytes != null)
				logger.info("REQUEST=" + new String(bytes));
			else
				logger.info("REQUEST=NULL");
			if (method != null)
				method.releaseConnection();
			logger.info("TIMETAKE=" + (System.currentTimeMillis() - t0));
		}
		return null;
	}

	public byte[] connectSyncApache_post_proto(String url, byte[] requestStream) {
		long t0 = System.currentTimeMillis();
		logger.info("REQUEST=" + url);
		logger.info("REQUEST_BODY=" + new String(requestStream));
		byte[] bytes = null;
		PostMethod method = null;
		try {
			method = new PostMethod(url);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);

			method.setRequestHeader("Content-Type", "application/x-protobuf");
			method.setRequestHeader("Content-Length", "" + requestStream.length);

			method.setRequestEntity(new ByteArrayRequestEntity(requestStream));

			int statusCode = client.executeMethod(method);

			System.out.println("url=" + url + " statusCode = " + statusCode);

			switch (statusCode) {
				case 200:
				case 201:
					BufferedInputStream br = new BufferedInputStream(method.getResponseBodyAsStream());
					bytes = IOUtils.toByteArray(br);
					return bytes;
			}

			return null;

		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
		} finally {
			if (bytes != null)
				logger.info("REQUEST=" + new String(bytes));
			else
				logger.info("REQUEST=NULL");
			if (method != null)
				method.releaseConnection();
			logger.info("TIMETAKE=" + (System.currentTimeMillis() - t0));
		}
		return null;
	}


	public String connectSyncApache_post_json(String url, String jsonRequest, HashMap<String, String> headers) {
		long t0 = System.currentTimeMillis();
		PostMethod method = null;
		logger.info("REQUEST=" + url);
		logger.info("REQUEST_BODY=" + jsonRequest);
		String bytes = null;
		try {
			method = new PostMethod(url);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);

			method.setRequestHeader("Content-Type", "application/json");
			if (headers != null) {
				Iterator it = headers.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, String> pair = (Map.Entry) it.next();
					logger.info("putting to header, key = " + pair.getKey() + ", value = " + pair.getValue());
					method.setRequestHeader(pair.getKey(), pair.getValue());
				}
			}
			method.setRequestEntity(new StringRequestEntity(jsonRequest, "application/json", "UTF-8"));

			int statusCode = client.executeMethod(method);

			logger.info("url=" + url + " statusCode = " + statusCode);

			switch (statusCode) {
				case 200:
				case 201:
					BufferedInputStream br = new BufferedInputStream(method.getResponseBodyAsStream());
					bytes = IOUtils.toString(br);
					return bytes;
			}

			return null;

		} catch (Exception e) {
			logger.info(ExceptionUtils.getStackTrace(e));
		} finally {
			logger.info("REQUEST=" + bytes);
			if (method != null)
				method.releaseConnection();
			logger.info("TIMETAKE=" + (System.currentTimeMillis() - t0));
		}
		return null;
	}
}
