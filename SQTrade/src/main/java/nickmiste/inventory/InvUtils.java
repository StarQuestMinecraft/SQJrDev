package nickmiste.inventory;

import java.util.ArrayList;

import nickmiste.orders.Order;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvUtils 
{
	public static boolean hasRoom(Player player, ItemStack[] items)
	{
		int numItems = 0;
		for (ItemStack stack : items)
			numItems += stack.getAmount();
		
		ItemStack[] inv = player.getInventory().getContents();
		
		for (ItemStack stack : inv)
		{
			if (stack == null)
				numItems -= items[0].getMaxStackSize();
			else if (stack.getType().equals(items[0].getType()) && stack.getDurability() == items[0].getDurability())
				numItems -= stack.getMaxStackSize() - stack.getAmount();
		}
		
		return numItems <= 0;
	}
	
	public static boolean hasEnough(Player player, ItemStack[] items)
	{
		int numItems = 0;
		for (ItemStack stack : items)
			numItems += stack.getAmount();
		
		ItemStack[] inv = player.getInventory().getContents();
		
		for (ItemStack stack : inv)
		{
			if (stack != null)
				if (stack.getType().equals(items[0].getType()) && stack.getDurability() == items[0].getDurability())
					numItems -= stack.getAmount();
		}
		
		return numItems <= 0;
	}
	
	public static ItemStack getDisplayStack (Order order)
	{
		ItemStack stack = new ItemStack(order.item, order.quantity < order.item.getMaxStackSize() ? order.quantity : order.item.getMaxStackSize(), order.dmg);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "" + order.quantity + "x " + stack.getType());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(order.isSellOrder ? ChatColor.AQUA + "Sell Order" : ChatColor.AQUA + "Buy Order");
		lore.add(ChatColor.AQUA + "Price: " + order.price);
		lore.add(ChatColor.AQUA + "Located on " + order.world + " [" + order.loc.getX() + ", " + order.loc.getY() + ", " + order.loc.getZ() + "]");
		lore.add(ChatColor.AQUA + "Ordered by: " + Bukkit.getOfflinePlayer(order.player).getName());
		lore.add(ChatColor.AQUA + "" + ChatColor.MAGIC + order.orderId);
		lore.add(ChatColor.RED + "Contraband");
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	public static boolean clickedOnOrder(InventoryClickEvent event)
	{
		if (event.getCurrentItem().hasItemMeta())
		{
			if (event.getCurrentItem().getItemMeta().hasDisplayName())
			{
				if (!event.getCurrentItem().getItemMeta().getDisplayName().startsWith(ChatColor.RED + ""))
					return false;
			}
			else
				return false;
		}	
		else
			return false;
		return true;
	}
}