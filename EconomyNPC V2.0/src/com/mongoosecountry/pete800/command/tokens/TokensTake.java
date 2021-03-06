package com.mongoosecountry.pete800.command.tokens;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import com.mongoosecountry.pete800.EconomyNPC;
import com.mongoosecountry.pete800.command.AbstractCommand;
import com.mongoosecountry.pete800.util.UUIDFetcher;

public class TokensTake extends AbstractCommand
{
	public TokensTake(EconomyNPC plugin)
	{
		super(plugin, false, "take", "Take tokens from a player's account.", "npc.tokens.take", Arrays.asList("/tokens", "take", "<player> <amount>"), null);
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args)
	{
		if (!canSenderUseCommand(sender))
			return false;
		
		if (args.length > 1)
		{
			OfflinePlayer player = null;
			try
			{
				player = Bukkit.getOfflinePlayer(UUIDFetcher.getUUIDOf(args[0]));
			}
			catch (Exception e)
			{
				sender.sendMessage(ChatColor.DARK_RED + "An unexpected error occurred while pinging Mojang's API.");
				return false;
			}
			
			if (player == null)
			{
				sender.sendMessage(ChatColor.DARK_RED + "Invalid player name.");
				return false;
			}
			
			if (!plugin.isNumber(args[1]))
			{
				sender.sendMessage(ChatColor.DARK_RED + "Expected number, instead received " + args[1]);
				return false;
			}
			
			plugin.tokens.removeTokens(player.getUniqueId(), Integer.parseInt(args[1]));
			sender.sendMessage(ChatColor.AQUA+ "Tokens removed.");
			return true;
		}
		
		sender.sendMessage(ChatColor.DARK_RED + "Not enough arguments: " + getUsage());
		return false;
	}	
}
