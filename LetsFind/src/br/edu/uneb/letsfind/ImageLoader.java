package br.edu.uneb.letsfind;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.UnusedStub;
import android.util.Log;

public class ImageLoader {

	public static byte[] getLogoImage(String url){
	     try {
	             URL imageUrl = new URL(url);
	             URLConnection ucon = imageUrl.openConnection();

	             InputStream is = ucon.getInputStream();
	             BufferedInputStream bis = new BufferedInputStream(is);

	             ByteArrayBuffer baf = new ByteArrayBuffer(500);
	             int current = 0;
	             while ((current = bis.read()) != -1) {
	                     baf.append((byte) current);
	             }

	             return baf.toByteArray();
	     } catch (Exception e) {
	             Log.wtf("ImageManager", "Error: " + e.toString());
	             
	            @SuppressWarnings("unused")
				int x = 0;
	     }
	     return null;
	}
}
