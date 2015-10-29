//package nickmiste;
//
//import java.util.concurrent.CopyOnWriteArrayList;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.World;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.ArmorStand;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.Item;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.entity.ItemSpawnEvent;
//import org.bukkit.plugin.java.JavaPlugin;
//
//public final class SQSpaceItemsOld extends JavaPlugin implements Listener 
//{
//	public static CopyOnWriteArrayList<Player> spectators = new CopyOnWriteArrayList<Player>();
//	
//	private static SQSpaceItemsOld instance;
//	
//	@Override
//	public void onEnable() 
//	{
////		if (!Bukkit.getServerName().startsWith("Trinitos_"))
////		{
////			this.setEnabled(false);
////			return;
////		}
//		instance = this;
//		Bukkit.getPluginManager().registerEvents(this, this);
//		
//		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
//		{
//			public void run()
//			{
//				for (Player spectator : spectators)
//				{
//					spectators.remove(spectator);
//					spectator.remove();
//				}
//			}
//		}, 6000, 6000);
//	}
//	
//	@Override
//	public void onDisable()
//	{
//		for (Player spectator : spectators)
//			spectator.remove();
//	}
//	
//	@EventHandler
//	public void onItemSpawn(ItemSpawnEvent event)
//	{
//		if (isInSpace(event.getEntity()))
//			setFloating(event.getEntity(), true);
//		
//		ItemUpdateTask task = new ItemUpdateTask(event.getEntity());
//		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), task, 1, 1);
//		task.setId(id);
//	}
//	
//	public static void setFloating(Item item, boolean floating)
//	{
//		if (floating && item.getVehicle() == null)
//		{
//			Spectator.spawnSpectator(item);
//			//Player mount = item.getWorld().spawn(item.getLocation().clone().subtract(0, 1.5, 0), Player.class);
//			//mount.setGameMode(GameMode.SPECTATOR);
//			//mount.setPassenger(item);
//			//spectators.add(mount);
//		}
//		else if (!floating && item.getVehicle() != null)
//		{
//			spectators.remove((ArmorStand) item.getVehicle());
//			item.getVehicle().remove();
//		}
//	}
//	
//	@Override
//	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
//	{
//		if (cmd.getName().equalsIgnoreCase("spaceitemsclear"))
//		{
//			if (sender instanceof Player)
//			{
//				if (((Player) sender).hasPermission("sqspaceitems.clear"))
//				{
//					int counter = 0;
//					for (Player spectator : spectators)
//					{
//						spectators.remove(spectator);
//						spectator.remove();
//						counter++;
//					}
//					sender.sendMessage("Removed " + counter + " space items.");
//					return true;
//				}
//			}
//			else
//			{
//				int counter = 0;
//				for (Player spectator : spectators)
//				{
//					spectators.remove(spectator);
//					spectator.remove();
//					counter++;
//				}
//				sender.sendMessage("Removed " + counter + " space items.");
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	public static boolean isInSpace(Entity e)
//	{
//		final Location l = e.getLocation();
//		boolean air1 = true;
//		boolean air2 = true;
//		final int x = l.getBlockX();
//		final int y = l.getBlockY();
//		final int z = l.getBlockZ();
//		final World w = l.getWorld();
//		int height = 40;
// 
//		for (int i = 0; i < height; i++) 
//		{
//			final int id1 = w.getBlockTypeIdAt(x, y + i + 1, z);
//			final int id2 = w.getBlockTypeIdAt(x, y - i, z);
//			if (id1 != 0) 
//				air1 = false;
//			
//			if (id2 != 0)
//				air2 = false;
//		}
//		if ((!air1) && (!air2))
//			return false;
//		return true;
//	}
//	
//	public static SQSpaceItemsOld getInstance()
//	{
//		return instance;
//	}
//}