package nickmiste;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParachuteSelector 
{
	public static final String SELECTOR_NAME = ChatColor.BLUE + "Select a parachute:";
	public static final ArrayList<String> CONTRABAND = new ArrayList<String>();
	
	public static HashMap<UUID, String> parachutes = new HashMap<UUID, String>();
	
	public static final String RAINBOW_PARACHUTE = ChatColor.DARK_RED + "R" + ChatColor.RED + "a" + ChatColor.GOLD + "i" +
			ChatColor.YELLOW + "n" + ChatColor.DARK_GREEN + "b" + ChatColor.GREEN + "o" + ChatColor.AQUA + "w" +
			ChatColor.DARK_AQUA + " P" + ChatColor.BLUE + "a" + ChatColor.DARK_BLUE+ "r" + ChatColor.LIGHT_PURPLE + "a" +
			ChatColor.DARK_PURPLE + "c" + ChatColor.DARK_RED + "h" + ChatColor.RED + "u" + ChatColor.GOLD + "t" +
			ChatColor.YELLOW + "e";
	public static final String DEFAULT_PARACHUTE = ChatColor.GRAY + "Default Parachute";
	public static final String STEAMPUNK_PARACHUTE = ChatColor.DARK_GRAY + "Steam" + ChatColor.GOLD + "punk" + ChatColor.DARK_GRAY + " Parachute";
	public static final String IRONIC_PARACHUTE = ChatColor.DARK_GRAY + "Iron" + ChatColor.GRAY + "ic Parachute";
	public static final String SKYDOG_PARACHUTE = ChatColor.DARK_RED + "Sky" + ChatColor.GRAY + "dog";
	public static final String METEOR_PARACHUTE = ChatColor.RED + "" + ChatColor.BOLD + "Meteor";
	public static final String SLIME_PARACHUTE = ChatColor.GREEN + "" + ChatColor.BOLD + "Slime Parachute";
	public static final String JETPACK_PARACHUTE = ChatColor.RED + "" + ChatColor.BOLD + "Jetpack";
//	public static final String WINGS = ChatColor.GOLD + "Wings";
	
	private static final ArrayList<ItemStack> ITEMS = new ArrayList<ItemStack>();
	
	static
	{
		CONTRABAND.add(ChatColor.RED + "" + ChatColor.MAGIC + "Contraband");
		addItem(Material.FEATHER, 0, DEFAULT_PARACHUTE);
		addItem(Material.GOLD_INGOT, 0, RAINBOW_PARACHUTE);
		addItem(Material.NETHERRACK, 0, METEOR_PARACHUTE);
		addItem(Material.WOOD_BUTTON, 0, STEAMPUNK_PARACHUTE);
		addItem(Material.ANVIL, 0, IRONIC_PARACHUTE);
		addItem(Material.BONE, 0, SKYDOG_PARACHUTE);
		addItem(Material.SLIME_BALL, 0,  SLIME_PARACHUTE);
		addItem(Material.BLAZE_POWDER, 0, JETPACK_PARACHUTE);
//		addItem(Material.SKULL_ITEM, 1, WINGS);
	}
	
	public static Inventory getSelector(Player player)
	{
		Inventory selector = Bukkit.createInventory(player, 9, SELECTOR_NAME);
		
		for (int i = 0; i < 8; i++)
		{
			if (player.hasPermission("sqparachute." + getStringWithoutFormatting(ITEMS.get(i).getItemMeta().getDisplayName())) || ITEMS.get(i).getItemMeta().getDisplayName().equals(DEFAULT_PARACHUTE))
				selector.setItem(selector.firstEmpty(), ITEMS.get(i));
		}
		
		ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(" ");
		stack.setItemMeta(meta);
		
		while(selector.firstEmpty() != -1)
			selector.setItem(selector.firstEmpty(), stack);
		
		return selector;
	}
	
	private static void addItem(Material material, int damage, String name)
	{	
		ItemStack stack = new ItemStack(material, 1, (short) damage);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(CONTRABAND);
		stack.setItemMeta(meta);
		
		ITEMS.add(stack);
	}
	
	public static void setParachuteWithGUI(Player player, String str)
	{
		if (parachutes.containsKey(player.getUniqueId()))
			parachutes.remove(player.getUniqueId());
		if (!str.equals(DEFAULT_PARACHUTE))
			parachutes.put(player.getUniqueId(), str);
		player.sendMessage(str + ChatColor.RESET + " has been selected!");
		
		player.closeInventory();
		
		Bukkit.dispatchCommand(player.getServer().getConsoleSender(), "ee parachute " + player.getUniqueId() + " " + str.replaceAll(" ", "%"));
	}
	
	public static void setParachuteWithCommand(Player player, String str)
	{
		if (parachutes.containsKey(player.getUniqueId()))
			parachutes.remove(player.getUniqueId());
		
		if (str.replaceAll("%", " ").equals(getStringWithoutFormatting(DEFAULT_PARACHUTE)))
			parachutes.remove(player.getUniqueId());
		else
			parachutes.put(player.getUniqueId(), str.replaceAll("%", " "));
	}
	
	private static String getStringWithoutFormatting(String parachute)
	{
		String str = "";
		for (int i = 1; i < parachute.length(); i++)
			if (parachute.charAt(i) != ChatColor.COLOR_CHAR && parachute.charAt(i - 1) != ChatColor.COLOR_CHAR && parachute.charAt(i) != ' ')
				str += parachute.charAt(i);
		return str.toLowerCase();
	}
}
