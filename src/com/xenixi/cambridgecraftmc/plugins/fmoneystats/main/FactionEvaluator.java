package com.xenixi.cambridgecraftmc.plugins.fmoneystats.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.money.Money;

public class FactionEvaluator {

	protected double getTotalValue(Faction f) {
		double value = 0;

		List<MPlayer> members = f.getMPlayers();

		for (MPlayer mp : members) {
		//	Bukkit.getServer().getLogger().info("UUID of player is: " + mp.getUuid());
			
			value += MoneyStats.e.getBalance(Bukkit.getServer().getOfflinePlayer(mp.getUuid()));
			
		}
		value += Money.get(f);

		return value;
	}

	protected List<Faction> getTop(Collection<Faction> factions) {

		Map<Faction, Double> data = new HashMap<>();
		// test

		//
		for (Faction f : factions) {

			
				data.put(f, getTotalValue(f));
		}

		List<Double> values = new ArrayList<>();

		for (Entry<Faction, Double> e : data.entrySet()) {
			values.add(e.getValue());
		}

		Collections.sort(values);
		Collections.reverse(values);

		List<Faction> finals = new ArrayList<>();

		for (Double value : values) {

			for (Entry<Faction, Double> e : data.entrySet()) {

				if (e.getValue() == value.doubleValue()) {

					if (!finals.contains(e.getKey())) {
						finals.add(e.getKey());
					}

				}

			}

		}
		return finals;
	}
}
