package nickmiste;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener 
{
	@Override
	public void onEnable() 
	{
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() 
	{
		
	}

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event)
	{
		FloatItemTask task = new FloatItemTask(event.getEntity());
		int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, 5, 0);
		task.setTaskId(taskId);
	}
}