package br.edu.uneb.webclient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
/*
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
 */
import org.json.JSONArray;
import org.json.JSONObject;

import br.edu.uneb.webclient.db.CookieDataSource;
import android.content.Context;
import android.preference.PreferenceActivity.Header;
import android.util.Log;
import android.view.View;


public class WebClient extends Thread{

	private String hostAddress = null;
	private String path = null;
	private String fileName = null;
	
	private CookieDataSource cookies = null;
	private Context context = null;
	private ResponseHandler responseHandler = null; 
	
	//Pesquisar depois sobre esta classe
	//CookieStore store;
	
	public static final int GET = 0;
	public static final int POST = 1;
	public static final int BIN_GET = 2;
	public static final int BIN_POST = 3;
	
	private int method = GET;
	
	org.apache.http.Header[] cookie = null;
	
	public void setMethod(int method){
		this.method = method;
	}
	
	private List<NameValuePair> parametros = null;
	
	public void setParametros(List<NameValuePair> parametros) {
		this.parametros = parametros;
	}
	
	public WebClient(Context context, String host, String path, String fileName, ResponseHandler responseHandler){
		
		this.hostAddress = host;
		this.path = path;
		this.fileName = fileName;
		this.responseHandler = responseHandler;
		this.context = context;
		
		cookies = new CookieDataSource(context);
		cookies.setHostAddress(host);
		cookies.setPath(path);
		
	}
	
	private String getStrParametros(InputStream is){
		
		if(parametros == null) return "";
		
		try {
			
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			StringBuilder total = new StringBuilder();
			String line;
		
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
			
			return total.toString();	
			
		} catch (Exception e) {
			Log.wtf("WebClient", "getStrParametros:\r\n" + e.toString());
		}
		
		return "";
		
	}

	public void run(){
		
		String content;
		byte[] data;
		
		switch(this.method){
		
			case GET:
				content = executeGet();
				if(responseHandler != null)
					responseHandler.execute(this.context, content);
			break;
			case POST:
				content = executePost();
				if(responseHandler != null)
					responseHandler.execute(this.context, content);
			break;
			case BIN_GET:
				data = executeBinaryGet();
				if(responseHandler != null)
					responseHandler.execute(this.context, data);
			break;
			case BIN_POST:
				data = executeBinaryPost();
				if(responseHandler != null)
					responseHandler.execute(this.context, data);
			break;
		
		}
		
	}



	public String executeGet() {
		
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		UrlEncodedFormEntity par = null;
		HttpGet httpGet;
		
		try {
			
			if(parametros != null)
			par = new UrlEncodedFormEntity(parametros);
			String url;
			
			
			if(parametros != null){
				url = "http://"+hostAddress+path+fileName+"?"+getStrParametros(par.getContent());
			}
			else{
				url = "http://"+hostAddress+path+fileName;
			}
			
			Log.v("GET URL", url);
			
			httpGet = new HttpGet(url);
			
			
			String scookies = cookies.toString();
			if(!scookies.equals(""))
				httpGet.setHeader("Cookie", cookies.toString());
			
			
			HttpResponse response = client.execute(httpGet);
			
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();

				/***************************************************************/
				/* Tentando imprimir os cookies */

				cookie = (org.apache.http.Header[]) response.getHeaders("Set-Cookie");
				for(int h=0; h<cookie.length; h++){
					String cook = cookie[h].toString();
					cook = cook.substring(cook.indexOf(':')+1, cook.length()).trim();
					Log.v("Set-Cookie", cook);
					cookies.setCookie(cook);
				}

				/***************************************************************/

				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e("WebClient", "excuteGet: Failed to download file");
			}
		}
		catch (Exception e) {
			Log.e("WebClient.Get", Log.getStackTraceString(e));
		}
		
		return builder.toString();
	}


	public String executePost() {
		
		// Create a new HttpClient and Post Header
		StringBuilder builder = new StringBuilder();
		HttpClient httpclient = new DefaultHttpClient();
		String url = "http://"+ hostAddress + path+fileName;
		HttpPost httpPost = new HttpPost(url);

		Log.v("POST URL", url);
		
		try {
			// Add your data
			if(parametros != null)
			httpPost.setEntity(new UrlEncodedFormEntity(parametros));
			
			
			String scookies = cookies.toString();
			if(!scookies.equals(""))
				httpPost.setHeader("Cookie", cookies.toString());
			
			
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httpPost);
			
			/***************************************************************/
			/* Tentando imprimir os cookies */

			cookie = (org.apache.http.Header[]) response.getHeaders("Set-Cookie");
			for(int h=0; h<cookie.length; h++){
				String cook = cookie[h].toString();
				cook = cook.substring(cook.indexOf(':')+1, cook.length()).trim();
				Log.v("Set-Cookie", cook);
				cookies.setCookie(cook);
			}

			/***************************************************************/
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			
		} catch (Exception e) {
			Log.e("WebClient.Post", Log.getStackTraceString(e));
		}
		
		return builder.toString();
	}
	
	
	public byte[] executeBinaryGet() {
		
		HttpClient client = new DefaultHttpClient();
		UrlEncodedFormEntity par = null;
		HttpGet httpGet;
		byte[] data = null;
		
		try {
			
			if(parametros != null)
			par = new UrlEncodedFormEntity(parametros);
			String url;
			
			
			if(parametros != null){
				url = "http://"+hostAddress+path+fileName+"?"+getStrParametros(par.getContent());
			}
			else{
				url = "http://"+hostAddress+path+fileName;
			}
			
			Log.v("GET URL", url);
			
			httpGet = new HttpGet(url);
			
			String scookies = cookies.toString();
			if(!scookies.equals(""))
				httpGet.setHeader("Cookie", cookies.toString());
			
			HttpResponse response = client.execute(httpGet);
			InputStream is = response.getEntity().getContent();
	        int contentSize = (int) response.getEntity().getContentLength();
	        BufferedInputStream bis = new BufferedInputStream(is, 512);
	         
	        data = new byte[contentSize];
	        int bytesRead = 0;
	        int offset = 0;
	         
	        while (bytesRead != -1 && offset < contentSize) {
	            bytesRead = bis.read(data, offset, contentSize - offset);
	            offset += bytesRead;
	        }
		}
		catch (Exception e) {
			Log.e("WebClient.BinaryGet", Log.getStackTraceString(e));
		}
		
		return data;
	}
	
	
	public byte[] executeBinaryPost() {
		
		HttpClient client = new DefaultHttpClient();
		UrlEncodedFormEntity par = null;
		HttpPost httpPost;
		byte[] data = null;
		
		try {
			
			if(parametros != null)
			par = new UrlEncodedFormEntity(parametros);
			String url;
			
			
			if(parametros != null){
				url = "http://"+hostAddress+path+fileName+"?"+getStrParametros(par.getContent());
			}
			else{
				url = "http://"+hostAddress+path+fileName;
			}
			
			Log.v("GET URL", url);
			
			httpPost = new HttpPost(url);
			
			String scookies = cookies.toString();
			if(!scookies.equals(""))
				httpPost.setHeader("Cookie", cookies.toString());
			
			HttpResponse response = client.execute(httpPost);
			InputStream is = response.getEntity().getContent();
	        int contentSize = (int) response.getEntity().getContentLength();
	        BufferedInputStream bis = new BufferedInputStream(is, 512);
	         
	        data = new byte[contentSize];
	        int bytesRead = 0;
	        int offset = 0;
	         
	        while (bytesRead != -1 && offset < contentSize) {
	            bytesRead = bis.read(data, offset, contentSize - offset);
	            offset += bytesRead;
	        }
		}
		catch (Exception e) {
			Log.e("WebClient.BinaryPost", Log.getStackTraceString(e));
		}
		
		return data;
	}


}
