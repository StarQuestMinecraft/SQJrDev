package nickmiste;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.WitherSkull;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FloatItemTask extends BukkitRunnable
{
	Item item;
	double vecX;
	double vecY;
	double vecZ;
	
	public FloatItemTask(Item item)
	{
		this.item = item;
		vecX = item.getVelocity().getX();
		vecY = item.getVelocity().getY();
		vecZ = item.getVelocity().getZ();
	}
	
	public void run()
	{
		if (!item.isValid())
			this.cancel();
		if (true)//(SQSpace.isInSpace(item))
		{
			if (item.getVehicle() == null)
			{
				Location loc = item.getLocation();
				WitherSkull skull = item.getWorld().spawn(loc, WitherSkull.class);
				skull.setDirection(new Vector(0, 0, 0));
				skull.setPassenger(item);
		  	}
		}
		else if (item.getVehicle() != null)
			item.getVehicle().remove();
		
	}
}
