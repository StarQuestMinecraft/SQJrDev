package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.CloudParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CloudParachute extends Parachute
{
	public CloudParachute(Player player)
	{
		super(player);
		
		CloudParachuteTask task = new CloudParachuteTask(this);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}
