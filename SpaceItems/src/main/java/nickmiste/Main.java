package nickmiste;

import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener
{
	@Override
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event)
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new FloatItemTask(event.getEntity()), 5, 0);
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event)
	{
		if (event.getItem().getVehicle() != null)
			event.getItem().getVehicle().remove();
	}
	
	@EventHandler
	public void onItemDespawn(ItemDespawnEvent event)
	{
		if (event.getEntity().getVehicle() != null)
			event.getEntity().getVehicle().remove();
	}
	
	//Debug Method
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		for (int i = 0; i < event.getPlayer().getWorld().getEntities().size(); i++)
			if (event.getPlayer().getWorld().getEntities().get(i) instanceof WitherSkull)
				event.getPlayer().getWorld().spawn(event.getPlayer().getWorld().getEntities().get(i).getLocation(), Horse.class);
	}
	
	//Unused
	
	/*@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event)
	{
		final List<Entity> entities = event.getPlayer().getWorld().getEntities();
		final Location playerLoc = event.getPlayer().getLocation();
		for (int i = 0; i < entities.size(); i++)
		{
			if (entities.get(i) instanceof WitherSkull && entities.get(i).getLocation().distance(playerLoc) <= 5)
			{
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() 
				{
					  public void run() 
					  {
						  for (int i = 0; i < entities.size(); i++)
							  if (entities.get(i) instanceof WitherSkull && entities.get(i).getLocation().distance(playerLoc) <= 5)
								  entities.get(i).remove();
					  }
				}, 1200);
			}
		}
	}
	
	@EventHandler
	public void onItemDespawn(ItemDespawnEvent event)
	{
		final List<Entity> entities = event.getEntity().getWorld().getEntities();
		final Location playerLoc = event.getEntity().getLocation();
		for (int i = 0; i < entities.size(); i++)
		{
			if (entities.get(i) instanceof WitherSkull && entities.get(i).getLocation().distance(playerLoc) <= 5)
			{
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() 
				{
					  public void run() 
					  {
						  for (int i = 0; i < entities.size(); i++)
							  if (entities.get(i) instanceof WitherSkull && entities.get(i).getLocation().distance(playerLoc) <= 5)
								  entities.get(i).remove();
					  }
				}, 1200);
			}
		}
	}
	*/
	
}