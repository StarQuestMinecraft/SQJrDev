package nickmiste.inventory;

import java.util.ArrayList;

import nickmiste.orders.Order;
import nickmiste.orders.OrderTracker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OrderBrowser 
{
	public static final String INVENTORY_NAME = ChatColor.RED + "Order Browser Page ";
	
	public static final String PREVIOUS_NAME = ChatColor.RED + "Previous Page";
	public static final String NEXT_NAME = ChatColor.RED + "Next Page";
	public static final String MY_TRADES_NAME = ChatColor.RED + "My Trades";
	public static final String DISABLED = ChatColor.RED + "This feature is disabled for custom searches";
	
	public static final int[] MAIN_SLOTS = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
	
	public static Inventory getBrowser(Player player, int page, ArrayList<Order> customSearch)
	{
		Inventory inv = Bukkit.createInventory(player, 54, INVENTORY_NAME + (page + 1));
		
		ItemStack previous = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemStack next = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
		ItemStack myTrades = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		
		ItemMeta previousMeta = previous.getItemMeta();
		previousMeta.setDisplayName(customSearch == null ? PREVIOUS_NAME : DISABLED);
		previous.setItemMeta(previousMeta);
		
		ItemMeta nextMeta = next.getItemMeta();
		nextMeta.setDisplayName(customSearch == null ? NEXT_NAME : DISABLED);
		next.setItemMeta(nextMeta);
		
		ItemMeta myTradesMeta = myTrades.getItemMeta();
		myTradesMeta.setDisplayName(MY_TRADES_NAME);
		myTrades.setItemMeta(myTradesMeta);
		
		inv.setItem(0, previous);
		inv.setItem(9, previous);
		inv.setItem(18, previous);
		inv.setItem(27, previous);
		inv.setItem(8, next);
		inv.setItem(17, next);
		inv.setItem(26, next);
		inv.setItem(35, next);
		inv.setItem(36, myTrades);
		inv.setItem(36, myTrades);
		inv.setItem(37, myTrades);
		inv.setItem(38, myTrades);
		inv.setItem(39, myTrades);
		inv.setItem(40, myTrades);
		inv.setItem(41, myTrades);
		inv.setItem(42, myTrades);
		inv.setItem(43, myTrades);
		inv.setItem(44, myTrades);
		

		if (customSearch == null)
			customSearch = OrderTracker.getOrders();
		
		for (int i = page * MAIN_SLOTS.length; i < page * MAIN_SLOTS.length + MAIN_SLOTS.length && i < customSearch.size(); i++)
			if (!customSearch.get(i).player.equals(player.getUniqueId()))
				inv.setItem(MAIN_SLOTS[i % MAIN_SLOTS.length], InvUtils.getDisplayStack(customSearch.get(i)));
		
		for (int i = 0; i < OrderTracker.getOrdersFromPlayer(player).size(); i++)
			inv.setItem(i + 45, InvUtils.getDisplayStack(OrderTracker.getOrdersFromPlayer(player).get(i)));
		
		return inv;
	}
	
	public static int getCurrentPage(Player player)
	{	
		return Integer.parseInt(player.getOpenInventory().getTopInventory().getName().substring(INVENTORY_NAME.length())) - 1;
	}
}