package nickmiste.parachutes.tasks;

import java.lang.reflect.Field;

import net.minecraft.server.PacketPlayOutWorldParticles;
import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

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
		
		for (double i = -2; i <= 2; i += 0.1)
			for (double j = -2; j <= 2; j += 0.1)
			{
				PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles();
				try 
				{
					Field name = packet.getClass().getDeclaredField("a");
					name.setAccessible(true);
					name.set(packet, "cloud");
					
					Field speed = packet.getClass().getDeclaredField("h");
					speed.setAccessible(true);
					speed.set(packet, 0);
				} 
				catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) 
				{
					e.printStackTrace();
				}
			}
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline())
		{
			Parachute.parachuting.remove(parachute.player);
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}