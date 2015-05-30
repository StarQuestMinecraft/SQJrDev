package nickmiste;

import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SQSpaceItems extends JavaPlugin implements Listener 
{
	public static CopyOnWriteArrayList<ArmorStand> armorStands = new CopyOnWriteArrayList<ArmorStand>();
	
	private static SQSpaceItems instance;
	
	@Override
	public void onEnable() 
	{
		if (!Bukkit.getServerName().equals("Regalis") && !Bukkit.getServerName().equals("Digitalia") && !Bukkit.getServerName().equals("Defalos"))
		{
			this.setEnabled(false);
			return;
		}
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				for (ArmorStand armorStand : armorStands)
				{
					armorStands.remove(armorStand);
					armorStand.remove();
				}
			}
		}, 6000, 6000);
	}
	
	@Override
	public void onDisable()
	{
		for (ArmorStand armorStand : armorStands)
			armorStand.remove();
	}
	
	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event)
	{
		if (isInSpace(event.getEntity()))
			setFloating(event.getEntity(), true);
		
		ItemUpdateTask task = new ItemUpdateTask(event.getEntity());
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), task, 1, 1);
		task.setId(id);
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event)
	{
		if (event.getRightClicked() instanceof ArmorStand)
			if (armorStands.contains((ArmorStand) event.getRightClicked()))
				event.setCancelled(true);
	}
	
	public static void setFloating(Item item, boolean floating)
	{
		if (floating && item.getVehicle() == null)
		{
			ArmorStand mount = item.getWorld().spawn(item.getLocation().clone().subtract(0, 1.5, 0), ArmorStand.class);
			mount.setVisible(false);
			mount.setGravity(false);
			mount.setPassenger(item);
			armorStands.add(mount);
		}
		else if (!floating && item.getVehicle() != null)
		{
			armorStands.remove((ArmorStand) item.getVehicle());
			item.getVehicle().remove();
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("spaceitemsclear"))
		{
			if (sender instanceof Player)
			{
				if (((Player) sender).hasPermission("sqspaceitems.clear"))
				{
					for (ArmorStand armorStand : armorStands)
					{
						armorStands.remove(armorStand);
						armorStand.remove();
					}
					return true;
				}
			}
			else
			{
				for (ArmorStand armorStand : armorStands)
				{
					armorStands.remove(armorStand);
					armorStand.remove();
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInSpace(Entity e)
	{
		final Location l = e.getLocation();
		boolean air1 = true;
		boolean air2 = true;
		final int x = l.getBlockX();
		final int y = l.getBlockY();
		final int z = l.getBlockZ();
		final World w = l.getWorld();
		int height = 40;
 
		for (int i = 0; i < height; i++) 
		{
			final int id1 = w.getBlockTypeIdAt(x, y + i + 1, z);
			final int id2 = w.getBlockTypeIdAt(x, y - i, z);
			if (id1 != 0) 
				air1 = false;
			
			if (id2 != 0)
				air2 = false;
		}
		if ((!air1) && (!air2))
			return false;
		return true;
	}
	
	public static SQSpaceItems getInstance()
	{
		return instance;
	}
}