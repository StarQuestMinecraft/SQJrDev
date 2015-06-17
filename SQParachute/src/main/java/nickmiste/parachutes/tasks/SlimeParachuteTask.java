package nickmiste.parachutes.tasks;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Slime;
import org.bukkit.util.Vector;

public class SlimeParachuteTask extends ParachuteTask
{
	private Parachute parachute;
	
	private Slime slime;
	
	public SlimeParachuteTask(Parachute parachute, Slime slime)
	{
		this.parachute = parachute;
		this.slime = slime;
	}
	
	@Override
	public void run() 
	{	
		Vector direction = parachute.player.getLocation().getDirection();
		
		if (!slime.isDead())
		{
			slime.setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
			slime.setPassenger(parachute.player);
			
			if (slime.isOnGround() || !this.parachute.player.isOnline() || this.parachute.player.getLocation().getBlock().isLiquid() || this.parachute.player.isOnGround())
			{
				Parachute.parachutingLargeSlimes.remove(slime);
				slime.remove();
			}
		}
		else
			parachute.player.setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline() || this.parachute.player.getLocation().getBlock().isLiquid() || this.parachute.player.isDead())
		{
			Parachute.parachuting.remove(parachute.player);
			Parachute.glidingPlayers.remove(parachute.player);
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}
