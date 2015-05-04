package nickmiste;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
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
		
		HashMap<String, Object> configParachutes = new HashMap<String, Object>(this.getConfig().getConfigurationSection("parachutes").getValues(false));
		HashMap<UUID, String> parachutes = new HashMap<UUID, String>();
		
		for (String str : configParachutes.keySet())
			parachutes.put(UUID.fromString(str), (String) configParachutes.get(str));
		ParachuteSelector.parachutes = parachutes;
	}
	
	@Override
	public void onDisable()
	{
		HashMap<String, String> modifiedParachutes = new HashMap<String, String>();
		
		for (UUID uuid : ParachuteSelector.parachutes.keySet())
			modifiedParachutes.put(uuid.toString(), ParachuteSelector.parachutes.get(uuid));
		
		this.getConfig().createSection("parachutes", modifiedParachutes);
		this.saveConfig();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getPlayer().hasPermission("sqparachute.use"))
		{
			if (event.getAction().equals(Action.RIGHT_CLICK_AIR) && event.getPlayer().getItemInHand().getType().equals(Material.FEATHER) && 
				!event.getPlayer().isOnGround() && !Parachute.parachuting.contains(event.getPlayer()))
			{
				Parachute.startParachuting(event.getPlayer(), false);
				
				if (event.getPlayer().getItemInHand().getAmount() > 1)
					event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
				else
					event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
			}
		}
		
		if (event.getPlayer().hasPermission("sqparachute.useglider"))
		{
			if (event.getAction().equals(Action.RIGHT_CLICK_AIR) && event.getPlayer().getItemInHand().getType().equals(Material.LEATHER) && 
					!event.getPlayer().isOnGround() && !Parachute.parachuting.contains(event.getPlayer()))
			{
				Parachute.startParachuting(event.getPlayer(), true);
				
				if (event.getPlayer().getItemInHand().getAmount() > 1)
					event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount() - 1);
				else
					event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event)
	{
		if (event.getRightClicked() instanceof ArmorStand)
			if (Parachute.parachutingArmorStands.contains((ArmorStand) event.getRightClicked()))
				event.setCancelled(true);
	}
	
	@EventHandler
	public void onDamageTaken(EntityDamageEvent event)
	{
		if (event.getEntity() instanceof Player)
		{
			if (event.getCause().equals(DamageCause.FALL) && Parachute.parachuting.contains((Player) event.getEntity()))
				event.setCancelled(true);
			else if (event.getCause().equals(DamageCause.PROJECTILE) && Parachute.glidingPlayers.contains((Player) event.getEntity()))
				Parachute.parachuting.remove((Player) event.getEntity());
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (event.getInventory().getName().equals(ParachuteSelector.SELECTOR_NAME))
		{
			if (event.getCurrentItem() != null)
			{
				if (event.getCurrentItem().getItemMeta() != null) 
				{
					if (event.getCurrentItem().getItemMeta().getDisplayName() != " ")
						ParachuteSelector.setParachuteWithGUI((Player) event.getWhoClicked(), event.getCurrentItem().getItemMeta().getDisplayName());
					event.setCancelled(true);
				}
			}
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
			{
				ParachuteSelector.setParachuteWithCommand(Bukkit.getPlayer(args[0]), args[1]);
				return true;
			}
			else
			{
				((Player) sender).openInventory(ParachuteSelector.getSelector((Player) sender));
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