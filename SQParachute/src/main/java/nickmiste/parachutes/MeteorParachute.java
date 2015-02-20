package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.MeteorParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MeteorParachute extends Parachute
{
	public MeteorParachute(Player player)
	{
		super(player);
		
		MeteorParachuteTask task = new MeteorParachuteTask(this);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}

}
