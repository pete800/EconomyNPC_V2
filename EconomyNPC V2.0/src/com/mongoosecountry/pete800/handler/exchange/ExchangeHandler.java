package com.mongoosecountry.pete800.handler.exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExchangeHandler
{
	List<UUID> players = new ArrayList<UUID>();
	
	public void newTransaction(UUID player)
	{
		players.add(player);
	}
	
	public void endTransaction(UUID player)
	{
		players.remove(player);
	}
	
	public boolean isTransacting(UUID player)
	{
		return players.contains(player);
	}
}
