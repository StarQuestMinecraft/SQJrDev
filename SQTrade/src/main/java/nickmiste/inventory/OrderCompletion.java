package nickmiste.inventory;

import nickmiste.orders.OrderTracker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OrderCompletion
{
	public static final String INVENTORY_NAME = ChatColor.RED + "Click to complete:";
	
	public static Inventory getInventory(Player player, Location loc)
	{
		Inventory inv = Bukkit.createInventory(player, 54, INVENTORY_NAME);
		
		for (int i = 0; i < OrderTracker.getOtherOrdersFromLocation(loc, player.getUniqueId()).size(); i++)	
			inv.setItem(i, InvUtils.getDisplayStack(OrderTracker.getOtherOrdersFromLocation(loc, player.getUniqueId()).get(i)));
		return inv;
	}
}