package core;

public final class LogUtil {
	
	public static void log(String msg) {
		System.out.println(new java.text.SimpleDateFormat("HH:mm:ss:SSS").format(new java.util.Date()) + " " + msg);
	}

}
