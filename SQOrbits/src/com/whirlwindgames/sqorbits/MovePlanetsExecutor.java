package com.whirlwindgames.sqorbits;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MovePlanetsExecutor implements CommandExecutor {
	private SQOrbits main;
	
	protected MovePlanetsExecutor(SQOrbits main){
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("SQOrbits.moveplanets")){
			sender.sendMessage("You need more blue to be able to move worlds!");
			return true;
		}
		main.message(sender, "Attempting to move all the planets."
				+  "Note: The movecraft planet location may not be updated until the server restarts.");
		main.updateConfigStuff();
		main.tryMovePlanets();
		main.savePlanetRotations();
		return true;
	}
}
