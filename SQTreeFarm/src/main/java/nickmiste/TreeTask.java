package nickmiste;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class TreeTask implements Runnable
{
	private int id;
	
	private Location gold;
	private Location sign;
	
	private int radius;
	private int maxHeight;
	
	private Chest chest;
	private Furnace furnace;
	private Sign signBlock;
	
	private Player player;
	
	public static List<Fuel> validFuels;
	public static int rangeIncrements;
	
	public TreeTask(Location sign, Player player)
	{
		this.gold = TreeFarmStructure.getGoldRelativeToSign(sign);
		this.sign = sign;
		
		this.radius = checkRadius();
		this.maxHeight = calculateMaxHeight();
		
		this.chest = (Chest) gold.getWorld().getBlockAt(sign.getBlockX(), sign.getBlockY() - 1, sign.getBlockZ()).getState();
		this.furnace = (Furnace) gold.getWorld().getBlockAt(sign.getBlockX(), sign.getBlockY() + 1, sign.getBlockZ()).getState();
		
		this.player = player;
	}
	
	@Override
	public void run() 
	{
		if (TreeFarmStructure.isValid(sign))
		{
			signBlock = (Sign) gold.getWorld().getBlockAt(sign).getState();
		}
		else
		{
			signBlock.setLine(2, ChatColor.DARK_RED + "[INACTIVE]");
			signBlock.update();
			Bukkit.getScheduler().cancelTask(id);
		}	
		
		if (!player.isOnline())
		{
			signBlock.setLine(2, ChatColor.DARK_RED + "[INACTIVE]");
			signBlock.update();
			Bukkit.getScheduler().cancelTask(id);
		}
		
		if (signBlock.getLine(2).equals(ChatColor.DARK_RED + "[INACTIVE]"))
			Bukkit.getScheduler().cancelTask(id);
	
		maxHeight = calculateMaxHeight();
		radius = checkRadius();
		
		if (furnace.getInventory().getFuel() != null)
		{
			for (Fuel fuel : validFuels)
			{
				if (fuel.getMaterial().equals(furnace.getInventory().getFuel().getType()) && fuel.getDamage() == furnace.getInventory().getFuel().getDurability())
				{
					breakLog:
					for (int y = maxHeight; y >= gold.getBlockY() - 2; y--)
						for (int x = gold.getBlockX() - radius; x < gold.getBlockX() + radius; x++)
							for (int z = gold.getBlockZ() - radius; z < gold.getBlockZ() + radius; z++)
							{
								if (gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LOG) ||
									gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LOG_2))
								{
									BlockBreakEvent event = new BlockBreakEvent(gold.getWorld().getBlockAt(x, y, z), player);
									Bukkit.getServer().getPluginManager().callEvent(event);
									
									if (!event.isCancelled())
									{
										chest.getInventory().addItem(new ItemStack(gold.getWorld().getBlockAt(x, y, z).getType(), 1, (short) (gold.getWorld().getBlockAt(x, y, z).getData() % 4)));
										gold.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
										gold.getWorld().playEffect(new Location(gold.getWorld(), x, y, z), Effect.STEP_SOUND, 17);
										
										if ((int) ((Math.random() * fuel.getEfficiency()) + 1) == 1)
										{
											if (furnace.getInventory().getFuel().getAmount() > 1)
												furnace.getInventory().getFuel().setAmount(furnace.getInventory().getFuel().getAmount() - 1);
											else
												furnace.getInventory().setFuel(null);
										}
									}
									else
									{
										signBlock.setLine(2, ChatColor.DARK_RED + "[INACTIVE]");
										signBlock.update();
										Bukkit.getScheduler().cancelTask(id);
										return;
									}
									break breakLog;
								}
								else if (gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LEAVES) ||
										 gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LEAVES_2))
								{	
									BlockBreakEvent event = new BlockBreakEvent(gold.getWorld().getBlockAt(x, y, z), player);
									Bukkit.getServer().getPluginManager().callEvent(event);
									
									if (!event.isCancelled())
									{
										if (gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LEAVES) &&
											gold.getWorld().getBlockAt(x, y, z).getData() % 4 == 3)
										{
											if ((int) ((Math.random() * 39) + 1) == 1)
											{
												ItemStack stack = new ItemStack(Material.SAPLING, 1, (short) 3);
												
												if (furnace.getInventory().getSmelting() != null)
												{
													if (furnace.getInventory().getSmelting().getAmount() < 64 && furnace.getInventory().getSmelting().isSimilar(stack))
														furnace.getInventory().getSmelting().setAmount(furnace.getInventory().getSmelting().getAmount() + 1);
													else
														chest.getInventory().addItem(stack);
												}
												else
													furnace.getInventory().setSmelting(stack);
											}
										}
										else
										{
											if ((int) ((Math.random() * 19) + 1) == 1)
											{
												Block leaves = gold.getWorld().getBlockAt(x, y, z);
												ItemStack stack = new ItemStack(Material.SAPLING, 1, (short) (leaves.getType().equals(Material.LEAVES) ? leaves.getData() % 4 : (leaves.getData() % 4) + 4));
												if (furnace.getInventory().getSmelting() != null)
												{
													if (furnace.getInventory().getSmelting().getAmount() < 64 && furnace.getInventory().getSmelting().isSimilar(stack))
														furnace.getInventory().getSmelting().setAmount(furnace.getInventory().getSmelting().getAmount() + 1);
													else
														chest.getInventory().addItem(stack);
												}
												else
													furnace.getInventory().setSmelting(stack);
											}
										}
										
										if ((gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LEAVES) && gold.getWorld().getBlockAt(x, y, z).getData() % 4 == 0) ||
											(gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LEAVES_2) && gold.getWorld().getBlockAt(x, y, z).getData() % 4 == 1))
										{
											if ((int) ((Math.random() * 199) + 1) == 1)
											{
												chest.getInventory().addItem(new ItemStack(Material.APPLE, 1));
											}
										}
										
										gold.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
										gold.getWorld().playEffect(new Location(gold.getWorld(), x, y, z), Effect.STEP_SOUND, 18);
									}
									else
									{
										signBlock.setLine(2, ChatColor.DARK_RED + "[INACTIVE]");
										signBlock.update();
										Bukkit.getScheduler().cancelTask(id);
										return;
									}
								}
							}
				}
			}
		}
		
		if (furnace.getInventory().getSmelting() != null)
			if (furnace.getInventory().getSmelting().getType().equals(Material.SAPLING))
			{
				for (int x = gold.getBlockX() - radius; x < gold.getBlockX() + radius; x++)
					for (int z = gold.getBlockZ() - radius; z < gold.getBlockZ() + radius; z++)
						if (gold.getWorld().getBlockAt(x, gold.getBlockY() - 2, z).getType().equals(Material.DIRT))
						{
							if (gold.getWorld().getBlockAt(x, gold.getBlockY() - 1, z).getType().equals(Material.AIR) && furnace.getInventory().getSmelting() != null)
							{
								BlockBreakEvent event = new BlockBreakEvent(gold.getWorld().getBlockAt(x, gold.getBlockY() - 1, z), player);
								Bukkit.getServer().getPluginManager().callEvent(event);
								
								if (!event.isCancelled())
								{
									gold.getWorld().getBlockAt(x, gold.getBlockY() - 1, z).setType(Material.SAPLING);
									gold.getWorld().getBlockAt(x, gold.getBlockY() - 1, z).setData((byte) furnace.getInventory().getSmelting().getDurability());
									
									if (furnace.getInventory().getSmelting().getAmount() > 1)
										furnace.getInventory().getSmelting().setAmount(furnace.getInventory().getSmelting().getAmount() - 1);
									else
										furnace.getInventory().setSmelting(null);
								}
								else
								{
									signBlock.setLine(2, ChatColor.DARK_RED + "[INACTIVE]");
									signBlock.update();
									Bukkit.getScheduler().cancelTask(id);
									return;
								}
							}
						}
		}
	}
	
	private int calculateMaxHeight()
	{
		iterateY:
		for (int y = gold.getBlockY() - 1; y < 255; y++)
			for (int x = gold.getBlockX() - radius; x < gold.getBlockX() + radius; x++)
				for (int z = gold.getBlockZ() - radius; z < gold.getBlockZ() + radius; z++)
				{
					if (gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LOG) ||
						gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LOG_2) ||
						gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LEAVES) ||
						gold.getWorld().getBlockAt(x, y, z).getType().equals(Material.LEAVES_2))
					{
						continue iterateY;
					}
					
					if (x == gold.getBlockX() + radius - 1 && z == gold.getBlockZ() + radius - 1)
						return y;
				}
		return 255;
	}
	
	private int checkRadius()
	{
		if (gold.getWorld().getBlockAt(gold).getType().equals(Material.COAL_BLOCK))
			return rangeIncrements;
		else if (gold.getWorld().getBlockAt(gold).getType().equals(Material.IRON_BLOCK))
			return 2 * rangeIncrements;
		else if (gold.getWorld().getBlockAt(gold).getType().equals(Material.GOLD_BLOCK))
			return 3 * rangeIncrements;
		else if (gold.getWorld().getBlockAt(gold).getType().equals(Material.DIAMOND_BLOCK))
			return 4 * rangeIncrements;
		else if (gold.getWorld().getBlockAt(gold).getType().equals(Material.EMERALD_BLOCK))
			return 5 * rangeIncrements;
		else return 0;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
}