package nickmiste;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.tools.interfaces.Loader;
import com.vexsoftware.votifier.model.VotifierEvent;

public final class SQVote extends JavaPlugin implements Listener
{
	private static SQVote instance;
	public static Common cc3;
	
	public static ArrayList<ArmorStand> parachutingArmorStands = new ArrayList<ArmorStand>();
	
	@Override
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(this, this);
		instance = this;
		
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Craftconomy3");
		if (plugin != null)
		{
			cc3 = (Common) ((Loader) plugin).getCommon();
		}
		
		RedeemMenu.setupMenu();
	}
	
	@EventHandler
	public void onVote(VotifierEvent event)
	{
		cc3.getAccountManager().getAccount(event.getVote().getUsername()).deposit(1, Bukkit.getServer().getWorlds().get(0).getName(), "VT");
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (event.getInventory().getName().equals(RedeemMenu.INVENTORY_NAME))
		{
			event.setCancelled(true);
			if (RedeemMenu.rewards.containsKey(event.getCurrentItem()))
			{
				if (!Checkout.cart.containsKey((Player) event.getWhoClicked()))
				{
					Checkout.cart.put((Player) event.getWhoClicked(), new ArrayList<Reward>());
					Checkout.cart.get((Player) event.getWhoClicked()).add(RedeemMenu.rewards.get(event.getCurrentItem()));
				}
				else
				{
					Checkout.cart.get((Player) event.getWhoClicked()).add(RedeemMenu.rewards.get(event.getCurrentItem()));
				}
			}
			else if (event.getCurrentItem() != null)
			{
				if (event.getCurrentItem().hasItemMeta())
					if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Checkout"))
					{
						if (Checkout.cart.get((Player) event.getWhoClicked()) == null)
							event.getWhoClicked().sendMessage(ChatColor.RED + "Your cart is empty!");
						else
							Checkout.checkout((Player) event.getWhoClicked());
						event.getWhoClicked().closeInventory();
					}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event)
	{
		if (event.getInventory().getName().equals(RedeemMenu.INVENTORY_NAME) && Checkout.cart.containsKey((Player) event.getPlayer()))
		{
			Checkout.cart.remove((Player) event.getPlayer());
			event.getPlayer().sendMessage(ChatColor.AQUA + "Your cart has been emptied.");
		}
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event)
	{
		if (event.getRightClicked() instanceof ArmorStand)
			if (parachutingArmorStands.contains((ArmorStand) event.getRightClicked()))
				event.setCancelled(true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			if (cmd.getName().equalsIgnoreCase("redeemvote"))
			{
				((Player) sender).openInventory(RedeemMenu.getMenu());
				return true;
			}
		}
		return false;
	}
	
	public static SQVote getInstance()
	{
		return instance;
	}
}
