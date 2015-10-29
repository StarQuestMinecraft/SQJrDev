//package nickmiste;
//
//import org.bukkit.Bukkit;
//import org.bukkit.entity.Item;
//
//public class ItemUpdateTask implements Runnable
//{
//	private int taskId;
//	private Item item;
//	
//	public ItemUpdateTask(Item item)
//	{
//		this.item = item;
//	}
//	
//	@Override
//	public void run() 
//	{
//		if (item.isDead())
//		{
//			SQSpaceItems.setFloating(item, false);
//			Bukkit.getScheduler().cancelTask(this.taskId);
//			return;
//		}
//		
//		SQSpaceItems.setFloating(item, SQSpaceItems.isInSpace(item) && item.getLocation().getY() < 250);
//	}
//	
//	public void setId(int taskId)
//	{
//		this.taskId = taskId;
//	}
//}