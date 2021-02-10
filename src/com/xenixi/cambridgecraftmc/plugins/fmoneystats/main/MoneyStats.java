package com.xenixi.cambridgecraftmc.plugins.fmoneystats.main;

import java.text.DecimalFormat;
import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MFlag;
import com.massivecraft.factions.entity.MPlayer;

import net.milkbowl.vault.economy.Economy;

public class MoneyStats extends JavaPlugin {
	static Economy e = null;
	static Collection<Faction> factions;
	static FactionEvaluator eval = null;
	
	static DecimalFormat format = new DecimalFormat("###,###,###,###,###.00");

	@Override
	public void onEnable() {

		// Setup economy.
		e = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
		// check if economy exists & disable plugin if economy doesn't function
		if (e == null) {
			getServer().getLogger().info("Failed to locate economy... Disabling fMoneyStats");
			getServer().getPluginManager().disablePlugin(this);
		}
		eval = new FactionEvaluator();
		factions = FactionColl.get().getAll();
		
		

	}

	@Override
	public void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender; 
			MPlayer mp = MPlayer.get(p);
			if (cmd.getName().equalsIgnoreCase("ftotal")) {
				// total faction value
				
				double value = Math.round((eval.getTotalValue(mp.getFaction())*100.0));
				value = value/100.0;
				
				
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Total Faction Value for &a" + mp.getFaction().getName() + "&6:&b $" + format.format(value)));
				
				return true;

			} else if (cmd.getName().equalsIgnoreCase("ftop")) {
				// top ranking factions
				StringBuilder sb = new StringBuilder();
				int i = 1;
				for(Faction f : eval.getTop(factions)) {
					double value = Math.round((eval.getTotalValue(f)*100.0));
					value = value/100.0;
					
					sb.append(ChatColor.translateAlternateColorCodes('&', "&6 " + i + ".) &a" + f.getName() + "&6 - &b$" + format.format(value)));
					sb.append("\n");
					
					i++;
					if(i>10) {
						
						break;
					}
				}
				
				sender.sendMessage(sb.toString());
				return true;

			}
		}
		return false;
	}
}
