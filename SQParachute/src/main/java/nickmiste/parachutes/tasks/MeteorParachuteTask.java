package nickmiste.parachutes.tasks;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

public class MeteorParachuteTask extends ParachuteTask
{
	private Parachute parachute;
	
	public MeteorParachuteTask(Parachute parachute)
	{
		this.parachute = parachute;
	}

	@Override
	public void run() 
	{
		parachute.player.setVelocity(new Vector(0, -0.3, 0));
		
		for (double i = -2; i <= 2; i += 0.1)
			for (double j = -2; j <= 2; j += 0.1)
				parachute.player.getWorld().playEffect(parachute.player.getLocation().clone().add(i, -2, j), Effect.LAVA_POP, 0, 2);
		parachute.player.getWorld().playSound(parachute.player.getLocation(), Sound.FIRE, 10, -10);
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline())
		{	
			for (int i = 0; i < 10; i++)
				parachute.player.getWorld().playEffect(parachute.player.getLocation(), Effect.EXPLOSION_HUGE, 0, 2);
			parachute.player.getWorld().playSound(parachute.player.getLocation(), Sound.EXPLODE, 20, 0);
			Parachute.parachuting.remove(parachute.player);
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}