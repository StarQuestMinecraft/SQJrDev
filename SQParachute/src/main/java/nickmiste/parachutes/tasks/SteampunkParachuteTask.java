package nickmiste.parachutes.tasks;

import java.util.ArrayList;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Slime;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class SteampunkParachuteTask extends ParachuteTask
{
	public static ArrayList<Slime> parachutingSlimes = new ArrayList<Slime>();
	
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
		
		for (Slime slime : slimes)
			parachutingSlimes.add(slime);
		
		this.iteration = 0;
	}
	
	@Override
	public void run()
	{
		parachute.player.setVelocity(new Vector(parachute.player.getVelocity().getX(), -0.3, parachute.player.getVelocity().getZ()));
		
//		armorStand1.setHeadPose(new EulerAngle(-Math.PI / 2, Math.PI / 2, -Math.sin(iteration) * (Math.PI / 6)));
//		
//		armorStand2.setHeadPose(new EulerAngle(-Math.PI / 2, -Math.PI / 2, Math.sin(iteration) * (Math.PI / 6)));
		
		armorStand1.setHeadPose(new EulerAngle(Math.sin(iteration) * (Math.PI / 6) - Math.PI / 2, parachute.player.getLocation().getYaw() * (Math.PI / 180) + Math.PI / 2, 0));
		
		armorStand2.setHeadPose(new EulerAngle(Math.sin(iteration) * (Math.PI / 6) + Math.PI / 2, -(parachute.player.getLocation().getYaw() * (Math.PI / 180) + Math.PI / 2), Math.PI));
		
		iteration += 0.1;
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline())
		{
			Parachute.parachuting.remove(parachute.player);
			armorStand1.remove();
			armorStand2.remove();
			for (Slime slime: slimes)
			{
				parachutingSlimes.remove(slime);
				slime.remove();
			}
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}
