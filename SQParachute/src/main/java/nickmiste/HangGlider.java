package nickmiste;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HangGlider
{
	public static ArrayList<Player> gliding = new ArrayList<Player>();
	
	public HangGlider(Parachute parachute)
	{	
		HangGliderTask task = new HangGliderTask(parachute);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
		
		gliding.add(parachute.player);
	}
}