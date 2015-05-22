package nickmiste.inventory;

import nickmiste.orders.Order;
import nickmiste.orders.OrderTracker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OrderManager 
{	
	public static final String INVENTORY_NAME = ChatColor.RED + "Click to delete:";
	
	public static Inventory getManager(Player player)
	{
		Inventory inv = Bukkit.createInventory(player, 9, INVENTORY_NAME);
		for (Order order : OrderTracker.getOrdersFromPlayer(player))
		{
			inv.setItem(inv.firstEmpty(), InvUtils.getDisplayStack(order));
		}
		
		return inv;
	}
}