package nickmiste;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SQTreeFarm extends JavaPlugin implements Listener
{	
	@Override
	public void onEnable()
	{
		loadConfig();
		TreeTask.validFuels = (List<Fuel>) getConfig().getList("fuels");
		TreeTask.allowHopperFuelInput = getConfig().getBoolean("allowHopperFuelInput");
		TreeTask.rangeIncrements = getConfig().getInt("rangeIncrements");
		Structure.allowHopperFuelInput = getConfig().getBoolean("allowHopperFuelInput");
		
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if (event.getClickedBlock().getState().getType().equals(Material.WALL_SIGN))
			{
				Sign sign = (Sign) (event.getClickedBlock().getState());
				if ((sign.getLine(0).equals("[treefarm]") && Structure.isValid(event.getClickedBlock().getLocation())))
				{
					sign.setLine(0, "");
					sign.setLine(1, ChatColor.GREEN + "Tree" + ChatColor.DARK_GREEN + "farm");
					sign.setLine(2, ChatColor.BLUE + "[ACTIVE]");
					sign.setLine(3, "");
					sign.update();
					
					TreeTask task = new TreeTask(sign.getLocation(), event.getPlayer());
					int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, 5, 5);
					task.setId(id);
				}
				else if (sign.getLine(1).equals(ChatColor.GREEN + "Tree" + ChatColor.DARK_GREEN + "farm") &&
						 sign.getLine(2).equals(ChatColor.BLUE + "[ACTIVE]"))
				{
					sign.setLine(2, ChatColor.DARK_RED + "[INACTIVE]");
					sign.update();
				}
				else if (sign.getLine(1).equals(ChatColor.GREEN + "Tree" + ChatColor.DARK_GREEN + "farm") &&
						 sign.getLine(2).equals(ChatColor.DARK_RED + "[INACTIVE]") &&
						 Structure.isValid(event.getClickedBlock().getLocation()))
				{
					sign.setLine(2, ChatColor.BLUE + "[ACTIVE]");
					sign.update();
					
					TreeTask task = new TreeTask(sign.getLocation(), event.getPlayer());
					int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, this.getConfig().getInt("update"), this.getConfig().getInt("update"));
					task.setId(id);
				}
			}
		}
	}
	
	private void loadConfig()
	{
		List<Fuel> fuels = Arrays.asList(new Fuel("COAL", 0, 12),
										 new Fuel("COAL", 1, 8),
										 new Fuel("BLAZE_ROD", 0, 18));
		getConfig().addDefault("fuels", fuels);
		getConfig().addDefault("allowHopperFuelInput", false);
		getConfig().addDefault("rangeIncrements", 3);
		getConfig().addDefault("update", 5);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}