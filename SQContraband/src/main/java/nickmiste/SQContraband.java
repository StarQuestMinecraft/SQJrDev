package nickmiste;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class SQContraband extends JavaPlugin implements Listener
{
	@Override
	public void onEnable()
	{	
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				for (World world: Bukkit.getWorlds())
					for (Player player : world.getPlayers())
						for (ItemStack stack : player.getInventory())
							if (stack != null)
								if (stack.hasItemMeta())
									if (stack.getItemMeta().hasLore())
										if (stack.getItemMeta().getLore().contains(ChatColor.RED + "Contraband"))
											player.getInventory().remove(stack);
			}
		}, 1, 1);
	}
}