package nickmiste;

import java.util.HashMap;

import nickmiste.parachutes.CloudParachute;
import nickmiste.parachutes.DefaultParachute;
import nickmiste.parachutes.IronicParachute;
import nickmiste.parachutes.RainbowParachute;
import nickmiste.parachutes.SkydogParachute;
import nickmiste.parachutes.SteampunkParachute;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParachuteSelector 
{
	public static Inventory selector = Bukkit.createInventory(null, 9, "Select a parachute:");
	public static HashMap<Player, String> parachutes = new HashMap<Player, String>();
	
	private static final String RAINBOW_PARACHUTE = ChatColor.DARK_RED + "R" + ChatColor.RED + "a" + ChatColor.GOLD + "i" +
			ChatColor.YELLOW + "n" + ChatColor.DARK_GREEN + "b" + ChatColor.GREEN + "o" + ChatColor.AQUA + "w" +
			ChatColor.DARK_AQUA + " P" + ChatColor.BLUE + "a" + ChatColor.DARK_BLUE+ "r" + ChatColor.LIGHT_PURPLE + "a" +
			ChatColor.DARK_PURPLE + "c" + ChatColor.DARK_RED + "h" + ChatColor.RED + "u" + ChatColor.GOLD + "t" +
			ChatColor.YELLOW + "e";
	private static final String DEFAULT_PARACHUTE = ChatColor.GRAY + "Default Parachute";
	private static final String CLOUD_PARACHUTE = ChatColor.BOLD + "Cloud Parachute";
	private static final String STEAMPUNK_PARACHUTE = ChatColor.DARK_GRAY + "Steam" + ChatColor.GOLD + "punk" + ChatColor.DARK_GRAY + " Parachute";
	private static final String IRONIC_PARACHUTE = ChatColor.DARK_GRAY + "Iron" + ChatColor.GRAY + "ic Parachute";
	private static final String SKYDOG_PARACHUTE = ChatColor.DARK_RED + "Sky" + ChatColor.GRAY + "dog";
	
	static
	{
		addItem(0, Material.FEATHER, 0, DEFAULT_PARACHUTE, false);
		addItem(1, Material.WOOL, 0, CLOUD_PARACHUTE, false);
		addItem(2, Material.GOLD_INGOT, 0, RAINBOW_PARACHUTE, true);
		addItem(3, Material.WOOD_BUTTON, 0, STEAMPUNK_PARACHUTE, false);
		addItem(4, Material.ANVIL, 0, IRONIC_PARACHUTE, false);
		addItem(5, Material.BONE, 0, SKYDOG_PARACHUTE, false);
	}
	
	private static void addItem(int slot, Material material, int damage, String name, boolean enchanted)
	{
		ItemStack stack = new ItemStack(material, 1, (short) damage);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(name);
		if (enchanted)
			stack.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 10);
		stack.setItemMeta(meta);
		selector.addItem(stack);
	}
	
	public static void setParachute(Player player, String parachute)
	{	
		if (parachutes.containsKey(player))
			parachutes.remove(player);
		if (!parachute.equals(DEFAULT_PARACHUTE))
			parachutes.put(player, parachute);
		
		player.closeInventory();
		player.sendMessage(parachute + ChatColor.RESET + " has been selected!");
	}
	
	public static Parachute startParachuting(Player player)
	{
		if (parachutes.containsKey(player))
			if (parachutes.get(player).equals(CLOUD_PARACHUTE))
				return new CloudParachute(player);
			else if (parachutes.get(player).equals(RAINBOW_PARACHUTE))
				return new RainbowParachute(player);
			else if (parachutes.get(player).equals(STEAMPUNK_PARACHUTE))
				return new SteampunkParachute(player);
			else if (parachutes.get(player).equals(IRONIC_PARACHUTE))
				return new IronicParachute(player);
			else if (parachutes.get(player).equals(SKYDOG_PARACHUTE))
				return new SkydogParachute(player);
		
		return new DefaultParachute(player);
	}
	
	public static void startGliding(Player player)
	{
		new HangGlider(startParachuting(player));
	}
}