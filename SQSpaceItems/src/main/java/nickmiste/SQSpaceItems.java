package nickmiste;

import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SQSpaceItems extends JavaPlugin implements Listener 
{
	public static CopyOnWriteArrayList<Player> spectators = new CopyOnWriteArrayList<Player>();
	
	private static SQSpaceItems instance;
	
	@Override
	public void onEnable() 
	{
//		if (!Bukkit.getServerName().startsWith("Trinitos_"))
//		{
//			this.setEnabled(false);
//			return;
//		}
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event)
	{
		Spectator.spawnSpectator(event.getEntity());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
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