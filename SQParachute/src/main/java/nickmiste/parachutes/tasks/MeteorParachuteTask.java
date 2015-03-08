package nickmiste.parachutes.tasks;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class MeteorParachuteTask extends ParachuteTask
{
	private Parachute parachute;
	private ArmorStand meteor;
	
	private double iteration;
	
	public MeteorParachuteTask(Parachute parachute, ArmorStand meteor)
	{
		this.parachute = parachute;
		this.meteor = meteor;
		
		this.iteration = 0;
	}

	@Override
	public void run() 
	{
		Vector direction = parachute.player.getLocation().getDirection();

		if (!meteor.isDead())
		{
			meteor.setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
			meteor.setPassenger(parachute.player);
			
			meteor.setHeadPose(new EulerAngle(Math.abs(Math.sin(iteration)), iteration * 4, Math.PI / 8));
			iteration += Math.PI / 90;
			
			parachute.player.getWorld().playSound(parachute.player.getLocation(), Sound.FIRE, 10, -10);
			
			if (this.meteor.isOnGround())
			{	
				for (int i = 0; i < 10; i++)
					parachute.player.getWorld().playEffect(parachute.player.getLocation(), Effect.EXPLOSION_HUGE, 0);
				parachute.player.getWorld().playSound(parachute.player.getLocation(), Sound.EXPLODE, 20, 0);
				meteor.remove();
				Parachute.parachutingArmorStands.remove(meteor);
			}
		}
		else
			parachute.player.setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
	
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline() || !Parachute.parachuting.contains(parachute.player))
		{
			Parachute.parachuting.remove(parachute.player);
			Parachute.glidingPlayers.remove(parachute.player);
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}