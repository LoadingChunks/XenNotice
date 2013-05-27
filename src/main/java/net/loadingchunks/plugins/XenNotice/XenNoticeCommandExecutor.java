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

import net.loadingchunks.plugins.XenNotice.util.Formatter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XenNoticeCommandExecutor implements CommandExecutor {

    private XenNotice plugin;

    public XenNoticeCommandExecutor(XenNotice plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("xn")) {
        	if(args.length < 1)
        		return false;
        	if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("xen.reload")) {
        		plugin.reloadConfig();
        	} else if(args[0].equalsIgnoreCase("list") && sender.hasPermission("xen.list")) {
        		for(String s : plugin.noticeList) {
        			sender.sendMessage("- " + Formatter.formatNotice(s));
        		}
        	} else if(args[0].equalsIgnoreCase("rawlist") && sender.hasPermission("xen.list")) {
        		for(String s : plugin.noticeList) {
        			sender.sendMessage("- " + s);
        		}
        	}
        }
        return false;
    }
}
