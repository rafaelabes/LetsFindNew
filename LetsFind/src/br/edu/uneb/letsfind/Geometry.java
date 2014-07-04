package br.edu.uneb.letsfind;

public class Geometry {

	public boolean isPointInCircle(double cx, double cy, double r, double px, double py){
		
		double distance = Math.sqrt(Math.pow(cx - px, 2) + Math.pow(cy - py, 2));
		return distance <= r;
		
	}
	
	
}
