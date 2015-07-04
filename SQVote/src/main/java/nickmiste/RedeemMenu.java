package nickmiste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RedeemMenu 
{
	public static final String INVENTORY_NAME = ChatColor.GOLD + "Add to Cart";
	private static Inventory menu;
	
	public static HashMap<ItemStack, Reward> rewards = new HashMap<ItemStack, Reward>();
	
	private static List<String> lore = new ArrayList<String>();
	private static List<String> checkoutLore = new ArrayList<String>();
	
	static
	{
		lore.add(ChatColor.GRAY + "Click to add one to your cart!");
		lore.add(ChatColor.RED + "Contraband");
		checkoutLore.add(ChatColor.DARK_RED + "!!!IMPORTANT!!!");
		checkoutLore.add(ChatColor.DARK_RED + "Make sure you only click");
		checkoutLore.add(ChatColor.DARK_RED + "this button if you are");
		checkoutLore.add(ChatColor.DARK_RED + "standing in a location");
		checkoutLore.add(ChatColor.DARK_RED + "where you will be able");
		checkoutLore.add(ChatColor.DARK_RED + "to access your supply");
		checkoutLore.add(ChatColor.DARK_RED + "drop chest!");
		checkoutLore.add(ChatColor.RED + "Contraband");
	}
	
	public static void setupMenu()
	{
		Inventory inv = Bukkit.createInventory(null, 27, INVENTORY_NAME);
		
		//rewards.put(addItem(inv, slot, material, damage, name, lore), reward);
		rewards.put(addItem(inv, 0, Material.SPONGE, 0, ChatColor.GREEN + "1-2 Mechanical Blocks (1 VT)", lore), Reward.SPONGE);
		rewards.put(addItem(inv, 1, Material.COOKED_BEEF, 0, ChatColor.GREEN + "64  Steak (8 VT)", lore), Reward.STEAK);
		rewards.put(addItem(inv, 2, Material.IRON_PICKAXE, 0, ChatColor.GREEN + "1 Iron Tool (4 VT)", lore), Reward.IRON_TOOL);
		rewards.put(addItem(inv, 3, Material.DIAMOND_PICKAXE, 0, ChatColor.GREEN + "1 Diamond Tool (6 VT)", lore), Reward.DIAMOND_TOOL);
		rewards.put(addItem(inv, 4, Material.GOLD_RECORD, 0, ChatColor.GREEN + "1 Music Disk (6 VT)", lore), Reward.MUSIC_DISK);
		rewards.put(addItem(inv, 5, Material.GOLD_ORE, 0, ChatColor.GREEN + "Bundle of Ores (3 VT)", lore), Reward.ORE);
//		rewards.put(addItem(inv, 6, Material.WOOL, 0, ChatColor.GREEN + "Ship Builder's Kit (16 VT)", lore), Reward.SHIP_KIT);
//		rewards.put(addItem(inv, 7, Material.WOOL, 0, ChatColor.GREEN + "Mega Ship Builder's Kit (24 VT)", lore), Reward.MEGA_SHIP_KIT);
		rewards.put(addItem(inv, 8, Material.INK_SACK, 1, ChatColor.GREEN + "Dye Package (6 VT)", lore), Reward.DYE);
		rewards.put(addItem(inv, 9, Material.EXP_BOTTLE, 0, ChatColor.GREEN + "Enchanter's Toolkit (16 VT)", lore), Reward.ENCH);
		
		addItem(inv, 26, Material.EMERALD, 0, ChatColor.GREEN + "Checkout", checkoutLore);
		
		menu = inv;
	}
	
	private static ItemStack addItem(Inventory inv, int slot, Material material, int damage, String name, List<String> lore)
	{
		ItemStack stack = new ItemStack(material, 1, (short) damage);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		stack.setItemMeta(meta);
		
		inv.setItem(slot, stack);
		return stack;
	}
	
	public static Inventory getMenu()
	{
		return menu;
	}
}