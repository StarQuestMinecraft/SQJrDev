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
		parachute.player.setVelocity(new Vector(parachute.player.getVelocity().getX(), -0.3, parachute.player.getVelocity().getZ()));
		
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute " + parachute.player.getName() + " ~ ~ ~ particle cloud ~ ~-3 ~ 1 .5 1 0 25");
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline())
		{
			Parachute.parachuting.remove(parachute.player);
			Bukkit.getScheduler().cancelTask(this.id);
		}
		else if (!Parachute.parachuting.contains(parachute.player))
		{
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}