package nickmiste.parachutes.tasks;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Slime;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class SteampunkParachuteTask extends ParachuteTask
{	
	private Parachute parachute;
	
	private ArmorStand armorStand1;
	private ArmorStand armorStand2;
	private Slime[] slimes;
	
	private double iteration;
	
	
	public SteampunkParachuteTask(Parachute parachute, ArmorStand armorStand1, ArmorStand armorStand2, Slime[] slimes)
	{
		this.parachute = parachute;
		this.armorStand1 = armorStand1;
		this.armorStand2 = armorStand2;
		this.slimes = slimes;
		
		this.iteration = 0;
	}
	
	@Override
	public void run()
	{
		Vector direction = parachute.player.getLocation().getDirection();
		parachute.player.setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
		
		armorStand1.setHeadPose(new EulerAngle(Math.sin(iteration) * (Math.PI / 6) - Math.PI / 2, parachute.player.getLocation().getYaw() * (Math.PI / 180) + Math.PI / 2, 0));
		
		armorStand2.setHeadPose(new EulerAngle(Math.sin(iteration) * (Math.PI / 6) + Math.PI / 2, -(parachute.player.getLocation().getYaw() * (Math.PI / 180) + Math.PI / 2), Math.PI));
		
		iteration += 0.1;
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline() || !Parachute.parachuting.contains(parachute.player))
		{
			Parachute.parachuting.remove(parachute.player);
			Parachute.glidingPlayers.remove(parachute.player);
			Parachute.glidingPlayers.remove(parachute.player);
			Parachute.parachutingArmorStands.remove(armorStand1);
			Parachute.parachutingArmorStands.remove(armorStand2);
			armorStand1.remove();
			armorStand2.remove();
			for (Slime slime: slimes)
				slime.remove();
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}
