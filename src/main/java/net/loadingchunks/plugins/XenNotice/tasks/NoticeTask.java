package net.loadingchunks.plugins.XenNotice.tasks;

import java.util.ArrayList;

import net.loadingchunks.plugins.XenNotice.XenNotice;
import net.loadingchunks.plugins.XenNotice.util.Formatter;

import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class NoticeTask extends BukkitRunnable {

	private final XenNotice plugin;
	private int lastIndex = 0;
	
	public NoticeTask(XenNotice plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if(plugin.noticeList.size() == 0)
			return;

		if(lastIndex >= plugin.noticeList.size()) {
			lastIndex = 0;
		}
		
		for(Player p : plugin.getServer().getOnlinePlayers()) {
			p.sendMessage(Formatter.formatNotice(plugin.noticeList.get(lastIndex)));
		}
		
		lastIndex++;
	}
	
}
