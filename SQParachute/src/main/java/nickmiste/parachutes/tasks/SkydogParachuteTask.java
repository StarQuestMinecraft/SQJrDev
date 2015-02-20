package nickmiste.parachutes.tasks;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Wolf;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class SkydogParachuteTask extends ParachuteTask
{
	private Parachute parachute;
	
	private ArmorStand armorStand1;
	private ArmorStand armorStand2;
	private Wolf skydog;
	private Slime[] slimes;
	
	public SkydogParachuteTask(Parachute parachute, ArmorStand armorStand1, ArmorStand armorStand2, Wolf skydog, Slime[] slimes)
	{
		this.parachute = parachute;
		
		this.armorStand1 = armorStand1;
		this.armorStand2 = armorStand2;
		this.skydog = skydog;
		this.slimes = slimes;
	}
	
	@Override
	public void run()
	{
		skydog.setVelocity(new Vector(0, -0.3, 0));
		skydog.setPassenger(parachute.player);
		
		if (skydog.isOnGround() || !this.parachute.player.isOnline())
		{
			Parachute.parachuting.remove(parachute.player);
			Parachute.parachutingArmorStands.remove(armorStand2);
			Parachute.parachutingArmorStands.remove(armorStand2);
			armorStand1.remove();
			armorStand2.remove();
			skydog.remove();
			for (Slime slime : slimes)
				slime.remove();
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}