package nickmiste;

import nickmiste.parachutes.SkydogParachute;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

public class HangGliderTask extends ParachuteTask
{
	private Parachute parachute;
	
	public HangGliderTask(Parachute parachute)
	{
		this.parachute = parachute;
	}
	
	@Override
	public void run() 
	{
		Vector direction = parachute.player.getLocation().getDirection();
		
		if (parachute instanceof SkydogParachute)
			((SkydogParachute) parachute).skydog.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
		else
			parachute.player.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline())
		{
			Bukkit.getScheduler().cancelTask(this.id);
		}
		else if (this.parachute instanceof SkydogParachute)
		{
			if (((SkydogParachute) this.parachute).skydog.isOnGround())
				Bukkit.getScheduler().cancelTask(this.id);
		}	
	}
}