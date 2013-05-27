package net.loadingchunks.plugins.XenNotice.util;

import net.loadingchunks.plugins.XenNotice.XenNotice;

public class Formatter {
	private static XenNotice plugin;
	
	public static void setPlugin(XenNotice lplugin) {
		plugin = lplugin;
	}
	
	public static String formatNotice(String str) {
		str = addPrefix(str);
		str = formatColour(str);
		return str;
	}
	
	public static String formatColour(String str) {
		String formatChar = new Character((char)167).toString();
		str = str.replaceAll("&", formatChar);
		return str;		
	}
	
	public static String addPrefix(String str) {
		str = plugin.getConfig().getString("xen.msg_prefix") + str;
		return str;
	}
}
