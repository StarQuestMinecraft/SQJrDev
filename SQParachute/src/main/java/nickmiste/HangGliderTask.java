package nickmiste;

import nickmiste.parachutes.IronicParachute;
import nickmiste.parachutes.MeteorParachute;
import nickmiste.parachutes.SkydogParachute;
import nickmiste.parachutes.SlimeParachute;

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
		if (!Parachute.parachuting.contains(parachute.player))
		{
			HangGlider.gliding.remove(parachute.player);
			Bukkit.getScheduler().cancelTask(this.id);
		}
		
		Vector direction = parachute.player.getLocation().getDirection();
		
		if (parachute instanceof SkydogParachute)
		{
			if (!((SkydogParachute) parachute).skydog.isDead())
				((SkydogParachute) parachute).skydog.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
			else
				parachute.player.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
		}
		else if (parachute instanceof IronicParachute)
		{
			if (!((IronicParachute) parachute).anvil.isDead())
				((IronicParachute) parachute).anvil.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
			else
				parachute.player.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
		}
		else if (parachute instanceof MeteorParachute)
		{
			if (!((MeteorParachute) parachute).meteor.isDead())
				((MeteorParachute) parachute).meteor.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
			else
				parachute.player.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
		}
		else if (parachute instanceof SlimeParachute)
		{
			if (!((SlimeParachute) parachute).slime.isDead())
				((SlimeParachute) parachute).slime.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
			else
				parachute.player.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
		}
		else
			parachute.player.setVelocity(new Vector(direction.getX(), -0.3, direction.getZ()));
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline())
			Bukkit.getScheduler().cancelTask(this.id);
		else if (parachute instanceof SkydogParachute)
		{
			if (((SkydogParachute) parachute).skydog.isOnGround())
				((SkydogParachute) parachute).skydog.remove();
		}
		else if (parachute instanceof IronicParachute)
		{
			if (((IronicParachute) parachute).anvil.isOnGround())
				((IronicParachute) parachute).anvil.remove();
		}
		else if (parachute instanceof MeteorParachute)
		{
			if (((MeteorParachute) parachute).meteor.isOnGround())
				((MeteorParachute) parachute).meteor.remove();
		}
		else if (parachute instanceof SlimeParachute)
		{
			if (((SlimeParachute) parachute).slime.isOnGround())
				((SlimeParachute) parachute).slime.remove();
		}
	}
}