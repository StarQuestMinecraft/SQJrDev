package nickmiste;

import org.bukkit.Bukkit;

public class HangGlider
{
	public HangGlider(Parachute parachute)
	{	
		HangGliderTask task = new HangGliderTask(parachute);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}