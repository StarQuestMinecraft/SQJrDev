package nickmiste.parachutes.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

public class CloudParachuteTask extends ParachuteTask
{
	private Parachute parachute;
	
	public CloudParachuteTask(Parachute parachute)
	{
		this.parachute = parachute;
	}

	@Override
	public void run() 
	{
		parachute.player.setVelocity(new Vector(0, -0.3, 0));
		
		//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute " + parachute.player.getName() + " ~ ~ ~ particle cloud ~ ~-3 ~ 1 .5 1 0 25");
		for (double i = -2; i <= 2; i += 0.1)
			for (double j = -2; j <= 2; j += 0.1)
				parachute.player.getWorld().playEffect(parachute.player.getLocation().clone().add(i, -2, j), Effect.CLOUD, 0);
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline())
		{
			Parachute.parachuting.remove(parachute.player);
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}