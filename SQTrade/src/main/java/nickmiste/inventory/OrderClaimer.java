package nickmiste.inventory;

import net.md_5.bungee.api.ChatColor;
import nickmiste.orders.Order;
import nickmiste.orders.OrderTracker;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OrderClaimer 
{
	public static final String INVENTORY_NAME = ChatColor.RED + "Click to claim:";
	
	public static Inventory getClaimer(Player player, Location post)
	{
		Inventory inv = Bukkit.createInventory(player, 54, INVENTORY_NAME);
		
		for (Order order : OrderTracker.getCompletedOrdersFromLocation(post, player.getUniqueId()))	
			inv.setItem(inv.firstEmpty(), InvUtils.getDisplayStack(order));
		
		return inv;
	}
}