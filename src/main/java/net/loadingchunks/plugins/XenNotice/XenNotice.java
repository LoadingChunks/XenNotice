package net.loadingchunks.plugins.XenNotice;

/*
    This file is part of XenNotice

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;

import net.loadingchunks.plugins.XenNotice.tasks.NoticeTask;
import net.loadingchunks.plugins.XenNotice.util.Formatter;
import net.loadingchunks.plugins.XenNotice.util.SQLWrapper;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class XenNotice extends JavaPlugin {

	//ClassListeners
	private final XenNoticeCommandExecutor commandExecutor = new XenNoticeCommandExecutor(this);
	private ArrayList<BukkitTask> schedulerTasks = new ArrayList<BukkitTask>();
	//ClassListeners
	
	public ArrayList<String> noticeList = new ArrayList<String>();

	public void onDisable() {
		for(BukkitTask t : schedulerTasks) {
			t.cancel();
		}
		
		schedulerTasks.clear();
		SQLWrapper.disconnect();
	}

	public void onEnable() { 

		PluginManager pm = this.getServer().getPluginManager();
		
		SQLWrapper.setPlugin(this);

		getCommand("xn").setExecutor(commandExecutor);
		
		getConfig().options().copyDefaults(true);
		getConfig().addDefault("db.host", "localhost");
		getConfig().addDefault("db.password", "password");
		getConfig().addDefault("db.username", "username");
		getConfig().addDefault("db.database", "database");
		getConfig().addDefault("timers.repeat", 60);
		getConfig().addDefault("timers.start", 60);
		getConfig().addDefault("xen.node", 0);
		getConfig().addDefault("xen.ignore_prefix", 0);
		getConfig().addDefault("xen.msg_prefix", "[&6NOTICE&f] ");
		
		Formatter.setPlugin(this);
		
		saveDefaultConfig();
		dbConnect();
		setupTasks();
	}
	
	@Override
	public void reloadConfig() {
		super.reloadConfig();
		dbConnect();
	}
	
	public void dbConnect() {
		SQLWrapper.setConfig(getConfig().getString("db.username"), getConfig().getString("db.password"), getConfig().getString("db.host"),getConfig().getString("db.database"));
		SQLWrapper.connect();
		SQLWrapper.populateNotices(noticeList);
	}
	
	public void setupTasks() {
		schedulerTasks.add(new NoticeTask(this).runTaskTimer(this, getConfig().getLong("timers.repeat") * 20L, getConfig().getLong("timers.start") * 20L));
	}
}
