package nickmiste;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class SQParachute extends JavaPlugin implements Listener
{
	private static SQParachute instance;
	
	@Override
	public void onEnable()
	{
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getPlayer().hasPermission("sqparachute.use"))
		{
			if (event.getAction().equals(Action.RIGHT_CLICK_AIR) && event.getPlayer().getItemInHand().getType().equals(Material.FEATHER) && 
				!event.getPlayer().isOnGround() && !Parachute.parachuting.contains(event.getPlayer()))
			{
				ParachuteSelector.startParachuting(event.getPlayer());
				
				if (event.getPlayer().getItemInHand().getAmount() > 1)
					event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
				else
					event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
			}
			else if (event.getAction().equals(Action.RIGHT_CLICK_AIR) && event.getPlayer().getItemInHand().getType().equals(Material.LEATHER) && 
				!event.getPlayer().isOnGround() && !Parachute.parachuting.contains(event.getPlayer()))
			{
				ParachuteSelector.startGliding(event.getPlayer());
				
				if (event.getPlayer().getItemInHand().getAmount() > 1)
					event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
				else
					event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
			}
		}
	}
	
	@EventHandler
	public void onDamageTaken(EntityDamageEvent event)
	{
		if (event.getEntity() instanceof Player)
			if (event.getCause().equals(DamageCause.FALL) && Parachute.parachuting.contains((Player) event.getEntity()))
				event.setCancelled(true);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (event.getInventory().getName().equals(ParachuteSelector.selector.getName()))
		{
			ParachuteSelector.setParachute((Player) event.getWhoClicked(), event.getCurrentItem().getItemMeta().getDisplayName());
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		ParachuteSelector.parachutes.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerKicked(PlayerKickEvent event)
	{
		ParachuteSelector.parachutes.remove(event.getPlayer());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("parachute"))
		{
			if (!(sender instanceof Player))
				sender.sendMessage("You are not a player!");
			else
			{
				((Player) sender).openInventory(ParachuteSelector.selector);
				return true;
			}
		}
		return false;
	}
	
	public static SQParachute getInstance()
	{
		return instance;
	}
}