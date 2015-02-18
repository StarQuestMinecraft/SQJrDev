package nickmiste.parachutes.tasks;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
		parachute.player.setVelocity(new Vector(parachute.player.getVelocity().getX(), -0.3, parachute.player.getVelocity().getZ()));
		slime.setVelocity(new Vector(0, 0, 0));
		
		slime.teleport(this.parachute.player.getLocation());
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline())
		{
			Parachute.parachuting.remove(parachute.player);
			slime.remove();
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}