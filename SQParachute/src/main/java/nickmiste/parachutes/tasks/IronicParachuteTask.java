package nickmiste.parachutes.tasks;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;

public class IronicParachuteTask extends ParachuteTask
{
	private Parachute parachute;
	private ArmorStand anvil;
	
	public IronicParachuteTask(Parachute parachute, ArmorStand anvil)
	{
		this.parachute = parachute;
		this.anvil = anvil;
	}
	
	@Override
	public void run() 
	{
		Vector direction = parachute.player.getLocation().getDirection();
		
		if (!anvil.isDead())
		{
			anvil.setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
			anvil.setPassenger(parachute.player);
			
			if (anvil.isOnGround() || !this.parachute.player.isOnline() || this.parachute.player.getLocation().getBlock().isLiquid())
			{
				parachute.player.getWorld().playSound(parachute.player.getLocation(), Sound.ANVIL_LAND, 20, 0);
				Parachute.parachutingArmorStands.remove(anvil);
				anvil.remove();
			}
		}
		else
			parachute.player.setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline() || this.parachute.player.getLocation().getBlock().isLiquid())
		{
			Parachute.parachuting.remove(parachute.player);
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}	
}