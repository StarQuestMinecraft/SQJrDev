package nickmiste;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Checkout 
{
	public static HashMap<Player, ArrayList<Reward>> cart = new HashMap<Player, ArrayList<Reward>>();
	
	public static void checkout(Player player)
	{
		int amount = 0;
		for (Reward reward : cart.get(player))
			amount += reward.price;
		
		System.out.println(player);
		System.out.println(SQVote.cc3);
		
		if (cart.containsKey(player))
		{
			if (SQVote.cc3.getAccountManager().getAccount(player.getName()).hasEnough(amount, player.getWorld().getName(), "VT"))
			{
				SQVote.cc3.getAccountManager().getAccount(player.getName()).withdraw(amount, player.getWorld().getName(), "VT");
		
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				for (Reward reward : cart.get(player))
					for (ItemStack stack : reward.getItems())
						items.add(stack);
				
				Inventory testHasRoom = Bukkit.createInventory(null, 27);
				
				if (hasRoom(testHasRoom, items.toArray(new ItemStack[1])))
				{
					new Parachest(player, items);
					player.sendMessage(ChatColor.AQUA + "Checkout Successful!");
				}
				else
					player.sendMessage(ChatColor.DARK_RED + "One parachest can't fit all of that! Please make multiple orders to receive your items.");
				cart.remove(player);
			}
			else
				player.sendMessage(ChatColor.DARK_RED + "You do not have enough VT for that! Earn VT by voting for the server!");
		}
		else
			player.sendMessage(ChatColor.DARK_RED + "Add something to your cart before pressing Checkout!");
	}
	
	private static boolean hasRoom(Inventory inv, ItemStack[] items)
	{
		int numItems = 0;
		for (ItemStack stack : items)
			numItems += stack.getAmount();
		
		for (ItemStack stack : inv)
		{
			if (stack == null)
				numItems -= items[0].getMaxStackSize();
			else if (stack.getType().equals(items[0].getType()) && stack.getDurability() == items[0].getDurability())
				numItems -= stack.getMaxStackSize() - stack.getAmount();
		}
		
		return numItems <= 0;
	}
}