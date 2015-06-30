package nickmiste.parachutes.tasks;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;

public class WingParachuteTask extends ParachuteTask
{
	private Parachute parachute;
	private ArmorStand[] wing1;
	private ArmorStand[] wing2;
	
	public WingParachuteTask(Parachute parachute, ArmorStand[] wing1, ArmorStand[] wing2)
	{
		this.parachute = parachute;
		this.wing1 = wing1;
		this.wing2 = wing2;
	}
	
	@Override
	public void run()
	{
		Vector direction = parachute.player.getLocation().getDirection();
		parachute.player.setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
		
		wing1[0].getLocation().add(-0.1875, -0.3125, -0.28125).setDirection(new Vector(90, 0, 0));
		wing2[0].getLocation().add(-0.1875, -0.3125, 0.28125).setDirection(new Vector(90, 0, 0));
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline() || this.parachute.player.getLocation().getBlock().isLiquid() || this.parachute.player.isDead())
		{
			for (ArmorStand as : wing1)
				removeArmorStand(as);
			for (ArmorStand as : wing2)
				removeArmorStand(as);
				
			Parachute.parachuting.remove(parachute.player);
			Parachute.glidingPlayers.remove(parachute.player);
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
	
	private static void removeArmorStand(ArmorStand as)
	{
		Parachute.parachutingArmorStands.remove(as);
		as.remove();
	}
}