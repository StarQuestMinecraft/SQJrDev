package nickmiste;

import nickmiste.parachutes.BalloonParachute;
import nickmiste.parachutes.IronicParachute;
import nickmiste.parachutes.MeteorParachute;
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
		else if (parachute instanceof IronicParachute)
			((IronicParachute) parachute).anvil.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
		else if (parachute instanceof MeteorParachute)
			((MeteorParachute) parachute).meteor.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
		else
			parachute.player.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline())
		{
			Bukkit.getScheduler().cancelTask(this.id);
		}
		else if (parachute instanceof SkydogParachute)
		{
			if (((SkydogParachute) parachute).skydog.isOnGround())
				Bukkit.getScheduler().cancelTask(this.id);
		}
		else if (parachute instanceof IronicParachute)
		{
			if (((IronicParachute) parachute).anvil.isOnGround())
				Bukkit.getScheduler().cancelTask(this.id);
		}
		else if (parachute instanceof MeteorParachute)
		{
			if (((MeteorParachute) parachute).meteor.isOnGround())
				Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}