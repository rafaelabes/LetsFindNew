package br.edu.uneb.webclient;

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


public class WebClient extends Thread{

	private String hostAddress = null;
	private String path = null;
	private String fileName = null;
	
	CookieDataSource cookies = null;
	
	public static final int GET = 0;
	public static final int POST = 1;
	
	private int method = GET;
	
	org.apache.http.Header[] cookie = null;
	
	public void setMethod(int method){
		this.method = method;
	}
	
	private List<NameValuePair> parametros = null;
	
	public void setParametros(List<NameValuePair> parametros) {
		this.parametros = parametros;
	}
	
	public WebClient(Context context, String host, String path, String fileName){
		cookies = new CookieDataSource(context);
		this.hostAddress = host;
		this.path = path;
		this.fileName = fileName;
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
		
		cookies.open();
		
		String content;
		
		if(this.method == GET){
			
			content = executeGet();	
			
		}else{
						
			content = executePost();
		}
		
		Log.v("content", content);
		

		/*
		
		try {

			JSONObject jsonObject = new JSONObject(content);

			Log.v("WebClient","executando a segunda thread");

			JSONArray values = (JSONArray) jsonObject.get("values");

			for (int i = 0; i < values.length(); i++) {
				JSONObject innerObj = (JSONObject) values.get(i);

				String iorder = (String) innerObj.get("order");
				String iname = (String) innerObj.get("name");
				String iponts = (String) innerObj.get("ponts");

				Log.v("app",iorder + " / " + iname + " / " + iponts);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		*/
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
		HttpPost httpPost = new HttpPost("http://"+ hostAddress + path+fileName);

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


}
