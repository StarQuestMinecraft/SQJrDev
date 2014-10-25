package nickmiste;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.WitherSkull;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FloatItemTask extends BukkitRunnable
{
	Item item;
	
	public FloatItemTask(Item item)
	{
		this.item = item;
	}
	
	public void run()
	{
		if (!item.isValid())
		{	
			if (item.getVehicle() != null)
				item.getVehicle().remove();
			this.cancel();
			return;
		}
		if (isInSpace(item))
		{
			if (item.getVehicle() == null)
			{
				WitherSkull skull = item.getWorld().spawn(item.getLocation().add(0, -0.25, 0), WitherSkull.class);
				skull.setDirection(new Vector(0, 0, 0));
				skull.setPassenger(item);
		  	}
		}
		else if (item.getVehicle() != null)
			item.getVehicle().remove();
		
	}
	
	private static boolean isInSpace(Entity e)
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
}
