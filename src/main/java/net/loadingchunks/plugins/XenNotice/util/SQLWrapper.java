package net.loadingchunks.plugins.XenNotice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.loadingchunks.plugins.XenNotice.XenNotice;

public class SQLWrapper {
	static private XenNotice plugin;
	static private Connection con;
	static private boolean success;
	
	static private String user;
	static private String password;
	static private String host;
	static private String db;
	
	public static void setPlugin(XenNotice plugin) {
		SQLWrapper.plugin = plugin;
	}
	
	public static void setConfig(String user, String password, String host, String db) {
		SQLWrapper.user = user;
		SQLWrapper.password = password;
		SQLWrapper.host = host;
		SQLWrapper.db = db;
	}
	
	public static XenNotice getPlugin() {
		return plugin;
	}
	
	public static boolean connect() {
		success = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			SQLWrapper.con = DriverManager.getConnection("jdbc:mysql://" + SQLWrapper.host + ":3306/" + SQLWrapper.db, SQLWrapper.user, SQLWrapper.password);
		} catch(SQLException e) {
			e.printStackTrace();
			SQLWrapper.success = false;
		} catch (ClassNotFoundException e) { e.printStackTrace(); SQLWrapper.success = false; }
		
		return success;
	}
	
	public static void disconnect() {
		try {
			if(!con.isClosed())
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void populateNotices(ArrayList<String> noticeList) {
		try {
			PreparedStatement stat = con.prepareStatement("SELECT title FROM xf_thread WHERE node_id = ? AND prefix_id != ? ORDER BY post_date ASC");
			stat.setInt(1, plugin.getConfig().getInt("xen.node"));
			stat.setInt(2, plugin.getConfig().getInt("xen.ignore_prefix"));
			stat.execute();
			
			ResultSet result = stat.getResultSet();
			
			noticeList.clear();
			
			while(result.next()) {
				noticeList.add(result.getString("title"));
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
}
