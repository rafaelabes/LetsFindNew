package br.edu.uneb.letsfind;

import android.util.Log;

public class Geometry {

	public static boolean isPointInCircle(double cx, double cy, double r, double px, double py){
		
		Double distance = Math.sqrt(Math.pow(cx - px, 2) + Math.pow(cy - py, 2));
		
		Log.wtf("dist", distance.toString());
		
		return distance <= r;
		
	}
	
	
}
