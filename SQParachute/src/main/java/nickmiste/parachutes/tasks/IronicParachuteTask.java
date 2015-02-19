package nickmiste.parachutes.tasks;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Slime;
import org.bukkit.util.Vector;

public class IronicParachuteTask extends ParachuteTask
{
	private Parachute parachute;
	private ArmorStand anvil;
	private Slime slime1;
	private Slime slime2;
	
	public IronicParachuteTask(Parachute parachute, ArmorStand anvil, Slime slime1, Slime slime2)
	{
		this.parachute = parachute;
		this.anvil = anvil;
		this.slime1 = slime1;
		this.slime2 = slime2;
	}
	
	@Override
	public void run() 
	{
		parachute.player.setVelocity(new Vector(parachute.player.getVelocity().getX(), -0.3, parachute.player.getVelocity().getZ()));
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline())
		{
			Parachute.parachuting.remove(parachute.player);
			anvil.remove();
			slime1.remove();
			slime2.remove();
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}	
}
