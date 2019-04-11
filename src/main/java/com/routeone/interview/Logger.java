package com.routeone.interview;

public class Logger {

    public static void error(String msg) {
	System.err.println("ERROR: " + msg);
    }

    public static void warning(String msg) {
	System.err.println("WARNING: " + msg);
    }

    public static void info(String msg) {
	System.out.println("INFO: " + msg);
    }

    public static void out(String msg) {
	System.out.println(msg);
    }
    
    public static void banner(String msg) {
	System.out.println("---------------------- " + msg + " ----------------------");
    }
    
    public static void totalsBanner() {
	System.out.println("============================================");
    }
    
}
