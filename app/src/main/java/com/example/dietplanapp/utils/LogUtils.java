package com.example.dietplanapp.utils;

import android.util.Config;
import android.util.Log;


public class LogUtils {

    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static int line(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null || trace.length == 0)
            return -1; //
        return trace[0].getLineNumber();
    }
    public static String fun(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null)
            return ""; //
        return trace[0].getMethodName();
    }
    public static String filename(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null)
            return ""; //
        return trace[0].getFileName()+"|"+trace[0].getMethodName()+"|"+trace[0].getLineNumber()+"|";
    }
    public static String funAndLine(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null || trace.length == 0)
            return ""; //
        return  filename(e)+"|"+trace[0].getMethodName()+"|"+trace[0].getLineNumber()+"|";
    }
}