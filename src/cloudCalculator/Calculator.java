package cloudCalculator;

public class Calculator { 
	
	public static boolean canCalculate(int n1, int n2, int mode){
		if((mode==3 && n2==0)||mode<0)return false;
		return true;
	}
	public static double calculate(int n1, int n2, int mode) {
		if(mode==0) {
			return n1+n2;
		}
		else if(mode == 1) {
			return n1-n2;
		}
		else if(mode == 2) {
			return n1*n2;
		}
		else if(mode == 3) {
			return n1/n2;
		}
		return mode;
	}
	
}
