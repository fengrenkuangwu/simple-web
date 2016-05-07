package com.leaf.base.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtil {
	
	public static String getLocalHostIP() {
		
		String addrIp = "";
		try {
			InetAddress ia = InetAddress.getLocalHost();
			addrIp = ia.getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
		return addrIp;
	}
	
	public static String getLocalHostName() {
		
		String hostName = "";
		try {
			InetAddress ia = InetAddress.getLocalHost();
			hostName = ia.getHostName();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
		return hostName;
	}
	
	public static void main(String[] args) {
		
		System.out.println(getLocalHostIP());
		System.out.println(getLocalHostName());
	}
	
}
