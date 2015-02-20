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
	
	public IronicParachuteTask(Parachute parachute, ArmorStand anvil)
	{
		this.parachute = parachute;
		this.anvil = anvil;
	}
	
	@Override
	public void run() 
	{
		anvil.setVelocity(new Vector(0, -0.3, 0));
		anvil.setPassenger(parachute.player);
		
		if (anvil.isOnGround() || !this.parachute.player.isOnline())
		{
			Parachute.parachuting.remove(parachute.player);
			Parachute.parachutingArmorStands.remove(parachute.player);
			anvil.remove();
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}	
}