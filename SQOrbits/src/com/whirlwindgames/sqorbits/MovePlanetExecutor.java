package com.whirlwindgames.sqorbits;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MovePlanetExecutor implements CommandExecutor {
	private SQOrbits main;
	
	protected MovePlanetExecutor(SQOrbits main){
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("SQOrbits.moveplanet")){
			sender.sendMessage("You're not strong enough to move the world! Try finding a lever and somewhere to stand.");
			return true;
		}
		if(args.length==0) return false;	//else
		main.message(sender, "Attempting to move planet '"+args[0]+"'. "
				+ "Note: The movecraft planet location may not be updated until the server restarts.");
		main.updateConfigStuff();
		main.tryMovePlanet(args[0]);
		main.saveConfig();
		return true;
	}
}
