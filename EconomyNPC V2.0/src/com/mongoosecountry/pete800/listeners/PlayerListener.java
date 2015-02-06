package com.mongoosecountry.pete800.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.mongoosecountry.pete800.EconomyNPC;
import com.mongoosecountry.pete800.npc.PlayerNPC;
import com.mongoosecountry.pete800.npc.PlayerNPC.NPCType;

public class PlayerListener implements Listener
{
	EconomyNPC plugin;
	
	public PlayerListener(EconomyNPC plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if (!plugin.tokens.containsPlayer(player.getUniqueId()))
			plugin.tokens.addTokens(player.getUniqueId(), 0);
		
		if (Bukkit.getOnlinePlayers().size() == 1)
		{
			//Needed delay so the NPCs actually spawn when the server has the chunks loaded for the player
			new BukkitRunnable()
			{
				@Override
				public void run()
				{
					for (PlayerNPC npc : plugin.storage.getNPCs())
						npc.respawnNPC();
				}
			}.runTaskLater(plugin, 1L);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if (Bukkit.getOnlinePlayers().size() == 1)
			for (PlayerNPC npc : plugin.storage.getNPCs())
				npc.despawnNPC();
	}
	
	@EventHandler
	public void onEntityInteraction(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		Villager villager = (Villager) event.getRightClicked();
		for (PlayerNPC npc : plugin.storage.getNPCs())
		{
			if (npc.getVillager().getUniqueId() == villager.getUniqueId())
			{
				if(npc.getType() == NPCType.SHOP || npc.getType() == NPCType.SELL)
					player.openInventory(npc.getInventory(player));
				else
					npc.handleNonInventoryNPC(player, plugin.econ);
				
				event.setCancelled(true);
				return;
			}
		}
	}
}
