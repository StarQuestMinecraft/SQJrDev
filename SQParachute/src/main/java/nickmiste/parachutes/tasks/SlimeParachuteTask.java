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
		if (!slime.isDead())
		{
			slime.setVelocity(new Vector(0, -0.3, 0));
			slime.setPassenger(parachute.player);
			
			if (slime.isOnGround())
				slime.remove();
		}
		else
			parachute.player.setVelocity(new Vector(0, -0.3, 0));
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline() || !Parachute.parachuting.contains(parachute.player))
		{
			Parachute.parachuting.remove(parachute.player);
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}
