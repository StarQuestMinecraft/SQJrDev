package nickmiste.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfirmGUI 
{
	public static final String INVENTORY_NAME_DELETE = ChatColor.RED + "Click to confirm deletion";
	public static final String INVENTORY_NAME_COMPLETE = ChatColor.RED + "Click to confirm completion";
	
	public static ItemStack confirm;
	public static ItemStack cancel;
	
	static
	{
		confirm = new ItemStack(Material.WOOL, 1, (short) 5);
		ItemMeta confirmMeta = confirm.getItemMeta();
		confirmMeta.setDisplayName(ChatColor.GREEN + "CONFIRM");
		confirm.setItemMeta(confirmMeta);
		
		cancel = new ItemStack(Material.WOOL, 1, (short) 14);
		ItemMeta cancelMeta = cancel.getItemMeta();
		cancelMeta.setDisplayName(ChatColor.RED + "CANCEL");
		cancel.setItemMeta(cancelMeta);
	}
	
	public static Inventory getDeletionGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(player, 9, INVENTORY_NAME_DELETE);
		inv.setItem(2, confirm);
		inv.setItem(6, cancel);
		return inv;
	}
	
	public static Inventory getCompletionGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(player, 9, INVENTORY_NAME_COMPLETE);
		inv.setItem(2, confirm);
		inv.setItem(6, cancel);
		return inv;
	}
}