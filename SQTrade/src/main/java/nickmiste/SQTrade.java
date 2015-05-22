package nickmiste;

import java.util.ArrayList;

import nickmiste.inventory.ConfirmGUI;
import nickmiste.inventory.InvUtils;
import nickmiste.inventory.OrderBrowser;
import nickmiste.inventory.OrderClaimer;
import nickmiste.inventory.OrderCompletion;
import nickmiste.inventory.OrderManager;
import nickmiste.inventory.SearchParser;
import nickmiste.orders.Order;
import nickmiste.orders.OrderTracker;
import nickmiste.tasks.BuyInfoTask;
import nickmiste.tasks.ClaimTask;
import nickmiste.tasks.CompletionConfirmationTask;
import nickmiste.tasks.CompletionTask;
import nickmiste.tasks.DeletionConfirmationTask;
import nickmiste.tasks.SellInfoTask;
import nickmiste.tasks.info.BuyInfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.greatmancode.craftconomy3.Cause;
import com.greatmancode.craftconomy3.Common;
import com.greatmancode.craftconomy3.tools.interfaces.Loader;

import dibujaron.Compression;

public final class SQTrade extends JavaPlugin implements Listener
{
	public static Common cc3;
	
	@Override
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(this, this);
		
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Craftconomy3");
		if (plugin != null)
		{
			cc3 = (Common) ((Loader) plugin).getCommon();
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if (event.getClickedBlock().getState().getType().equals(Material.WALL_SIGN))
			{
				Sign sign = (Sign) (event.getClickedBlock().getState());
				if (sign.getLine(0).equals("[tradepost]") && Structure.isValid(event.getClickedBlock().getLocation()))
				{
					sign.setLine(0, "");
					sign.setLine(1, ChatColor.DARK_PURPLE + "Trade Post");
					sign.setLine(2, "");
					sign.setLine(3, "");
					sign.update();
				}
				else if (sign.getLine(1).equals(ChatColor.DARK_PURPLE + "Trade Post") && Structure.isValid(event.getClickedBlock().getLocation()))
				{	
					if (SellInfoTask.prices.containsKey(event.getPlayer()) && OrderTracker.canPlaceOrder(sign.getLocation(), event.getPlayer().getUniqueId()))
					{
						Chest chest = (Chest) sign.getWorld().getBlockAt(sign.getLocation().subtract(0, 1, 0)).getState();
					
						//return if chest is empty
						for (int i = 0; i < chest.getInventory().getSize(); i++)
							if (chest.getInventory().getItem(i) != null)
								break;
							else if (i == chest.getInventory().getSize() - 1)
							{
								event.getPlayer().sendMessage(ChatColor.DARK_RED + "The chest is empty!");
								return;
							}
						
						ItemStack firstItem = null;
						for (ItemStack stack : chest.getInventory().getContents())
						{
							if (stack == null)
								continue;
							else
							{
								firstItem = stack;
								break;
							}
						}
						
						Material itemToSell = firstItem.getType();
						short itemToSellDmg = firstItem.getDurability();
						int quantity = 0;
						
						for (ItemStack stack : chest.getInventory().getContents())
						{
							if (stack == null)
								continue;
							
							if (stack.getType().equals(itemToSell) && stack.getDurability() == itemToSellDmg)
								quantity += stack.getAmount();
							else
							{
								event.getPlayer().sendMessage(ChatColor.DARK_RED + "You can only sell one type of item at a time!");
								return;
							}
						}
						OrderTracker.addOrder(new Order(event.getPlayer().getUniqueId(), true, itemToSell, itemToSellDmg,
								quantity, SellInfoTask.prices.get(event.getPlayer()), sign.getLocation()));
						chest.getInventory().clear();
						SellInfoTask.prices.remove(event.getPlayer());
						event.getPlayer().sendMessage(ChatColor.AQUA + "Your order has been processed. You will recieve your credits as soon as somebody completes your order.");
					}
					else if (BuyInfoTask.info.containsKey(event.getPlayer()) && OrderTracker.canPlaceOrder(sign.getLocation(), event.getPlayer().getUniqueId()))
					{
						if (cc3.getAccountManager().getAccount(event.getPlayer().getName(), false).hasEnough(BuyInfoTask.info.get(event.getPlayer()).price, event.getPlayer().getWorld().getName(), "credit"))
						{
							BuyInfo info = BuyInfoTask.info.get(event.getPlayer());

							cc3.getAccountManager().getAccount(event.getPlayer().getName(), false).withdraw(info.price, event.getPlayer().getWorld().getName(), "credit");
							
							OrderTracker.addOrder(new Order(event.getPlayer().getUniqueId(), false, info.material, info.dmg, 
									info.quantity, info.price, sign.getLocation()));
							BuyInfoTask.info.remove(event.getPlayer());
							event.getPlayer().sendMessage(ChatColor.AQUA + "Your order has been processed. You will recieve your items as soon as somebody completes your order.");
						}
						else
						{
							event.getPlayer().sendMessage(ChatColor.DARK_RED + "You do not have enough money to buy that!");
						}
					}
					else if (CompletionTask.pendingCompletions.contains(event.getPlayer()))
					{
						event.getPlayer().openInventory(OrderCompletion.getInventory(event.getPlayer(), sign.getLocation()));
					}
					else if (ClaimTask.pendingClaims.contains(event.getPlayer()))
					{
						event.getPlayer().openInventory(OrderClaimer.getClaimer(event.getPlayer(), sign.getLocation()));
					}
					else
						event.getPlayer().sendMessage(ChatColor.AQUA + "Use \"/tradepost\" before you click the sign!");
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (event.getInventory().getName().equals(OrderManager.INVENTORY_NAME))
		{
			if (event.getCurrentItem() != null)
			{
				if (!InvUtils.clickedOnOrder(event))
				{
					event.setCancelled(true);
					return;
				}
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new DeletionConfirmationTask((Player) event.getWhoClicked(), 
						Compression.str15ToUuid(event.getCurrentItem().getItemMeta().getLore().get(0).substring(2))), 1200);
				
				event.getWhoClicked().closeInventory();
				((Player) event.getWhoClicked()).openInventory(ConfirmGUI.getDeletionGUI((Player) event.getWhoClicked()));
			}
			event.setCancelled(true);
		}
		else if (event.getInventory().getName().startsWith(OrderBrowser.INVENTORY_NAME))
		{
			if (event.getCurrentItem() != null)
			{
				if (!InvUtils.clickedOnOrder(event))
				{
					event.setCancelled(true);
					return;
				}
				
				if (event.getCurrentItem().getItemMeta().getDisplayName().equals(OrderBrowser.NEXT_NAME))
				{
					if (OrderBrowser.getCurrentPage((Player) event.getWhoClicked()) < OrderTracker.getOrders().size() / OrderBrowser.MAIN_SLOTS.length)
						event.getWhoClicked().openInventory(OrderBrowser.getBrowser((Player) event.getWhoClicked(), OrderBrowser.getCurrentPage((Player) event.getWhoClicked()) + 1, null));
				}
				else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(OrderBrowser.PREVIOUS_NAME))
				{
					if (OrderBrowser.getCurrentPage((Player) event.getWhoClicked()) > 0)
						event.getWhoClicked().openInventory(OrderBrowser.getBrowser((Player) event.getWhoClicked(), OrderBrowser.getCurrentPage((Player) event.getWhoClicked()) - 1, null));
				}
			}
			event.setCancelled(true);
		}
		else if (event.getInventory().getName().equals(OrderCompletion.INVENTORY_NAME))
		{
			if (event.getCurrentItem() != null)
			{
				if (!InvUtils.clickedOnOrder(event))
				{
					event.setCancelled(true);
					return;
				}
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new CompletionConfirmationTask((Player) event.getWhoClicked(), 
						Compression.str15ToUuid(event.getCurrentItem().getItemMeta().getLore().get(0).substring(2))), 1200);
				
				((Player) event.getWhoClicked()).closeInventory();
				((Player) event.getWhoClicked()).openInventory(ConfirmGUI.getCompletionGUI((Player) event.getWhoClicked()));
			}
			event.setCancelled(true);
		}
		else if (event.getInventory().getName().equals(OrderClaimer.INVENTORY_NAME))
		{
			for (int i = 0; i < OrderTracker.getCompletedOrders().size(); i++)
			{
				int numFullStacks = OrderTracker.getCompletedOrders().get(i).quantity / OrderTracker.getCompletedOrders().get(i).item.getMaxStackSize();
				int leftover = OrderTracker.getCompletedOrders().get(i).quantity % OrderTracker.getCompletedOrders().get(i).item.getMaxStackSize();
				ItemStack[] items = new ItemStack[leftover == 0 ? numFullStacks : numFullStacks + 1];
				
				for (int j = 0; j < numFullStacks; j++)
					items[j] = new ItemStack(OrderTracker.getCompletedOrders().get(i).item, OrderTracker.getCompletedOrders().get(i).item.getMaxStackSize(), OrderTracker.getCompletedOrders().get(i).dmg);
				if (leftover != 0)
					items[items.length - 1] = new ItemStack(OrderTracker.getCompletedOrders().get(i).item, leftover, OrderTracker.getCompletedOrders().get(i).dmg);
				
				if (InvUtils.hasRoom((Player) event.getWhoClicked(), items))
				{
					((Player) event.getWhoClicked()).getInventory().addItem(items);
					OrderTracker.removeCompletedOrder(i);
					((Player) event.getWhoClicked()).closeInventory();
					((Player) event.getWhoClicked()).sendMessage(ChatColor.AQUA + "Your items have been claimed!");
				}
				else
				{
					((Player) event.getWhoClicked()).sendMessage(ChatColor.DARK_RED + "Not enough inventory space!");
					event.setCancelled(true);
					return;
				}
			}
			event.setCancelled(true);
		}
		else if (event.getInventory().getName().equals(ConfirmGUI.INVENTORY_NAME_COMPLETE))
		{
			event.setCancelled(true);
			if (event.getCurrentItem() != null)
			{	
				if (event.getCurrentItem().hasItemMeta())
				{
					if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "CONFIRM"))
					{
						if (CompletionConfirmationTask.pendingCompletions.containsKey((Player) event.getWhoClicked()))
						{
							for (int i = 0; i < OrderTracker.getOrders().size(); i++)
							{
								if (OrderTracker.getOrders().get(i).orderId.equals(CompletionConfirmationTask.pendingCompletions.get((Player) event.getWhoClicked())))
								{
									if (((Player) event.getWhoClicked()).getLocation().distance(OrderTracker.getOrders().get(i).loc) <= 10)
									{
										if (OrderTracker.getOrders().get(i).isSellOrder)
										{
											if (cc3.getAccountManager().getAccount(((Player) event.getWhoClicked()).getName(), false).hasEnough(OrderTracker.getOrders().get(i).price,
													((Player) event.getWhoClicked()).getWorld().getName(), "credit"))
											{
												int numFullStacks = OrderTracker.getOrders().get(i).quantity / OrderTracker.getOrders().get(i).item.getMaxStackSize();
												int leftover = OrderTracker.getOrders().get(i).quantity % OrderTracker.getOrders().get(i).item.getMaxStackSize();
												ItemStack[] items = new ItemStack[leftover == 0 ? numFullStacks : numFullStacks + 1];
												
												for (int j = 0; j < numFullStacks; j++)
													items[j] = new ItemStack(OrderTracker.getOrders().get(i).item, OrderTracker.getOrders().get(i).item.getMaxStackSize(), OrderTracker.getOrders().get(i).dmg);
												if (leftover != 0)
													items[items.length - 1] = new ItemStack(OrderTracker.getOrders().get(i).item, leftover, OrderTracker.getOrders().get(i).dmg);
												
												if (InvUtils.hasRoom((Player) event.getWhoClicked(), items))
												{
													cc3.getAccountManager().getAccount(((Player) event.getWhoClicked()).getName(), false).withdraw(OrderTracker.getOrders().get(i).price,
															((Player) event.getWhoClicked()).getWorld().getName(), "credit");
													cc3.getAccountManager().getAccount(Bukkit.getPlayer(OrderTracker.getOrders().get(i).player).getName(), false).deposit(OrderTracker.getOrders().get(i).price,
															((Player) event.getWhoClicked()).getWorld().getName(), "credit");
													
													((Player) event.getWhoClicked()).getInventory().addItem(items);
													OrderTracker.removeOrder(i);
												}
												else
												{
													event.getWhoClicked().closeInventory();
													((Player) event.getWhoClicked()).sendMessage(ChatColor.DARK_RED + "Not enough inventory space!");
													return;
												}
											}
											else
											{
												((Player) event.getWhoClicked()).sendMessage(ChatColor.DARK_RED + "You do not have enough money to buy that!");
											}
										}
										else
										{
											int numFullStacks = OrderTracker.getOrders().get(i).quantity / OrderTracker.getOrders().get(i).item.getMaxStackSize();
											int leftover = OrderTracker.getOrders().get(i).quantity % OrderTracker.getOrders().get(i).item.getMaxStackSize();
											ItemStack[] items = new ItemStack[leftover == 0 ? numFullStacks : numFullStacks + 1];
											
											for (int j = 0; j < numFullStacks; j++)
												items[j] = new ItemStack(OrderTracker.getOrders().get(i).item, OrderTracker.getOrders().get(i).item.getMaxStackSize(), OrderTracker.getOrders().get(i).dmg);
											if (leftover != 0)
												items[items.length - 1] = new ItemStack(OrderTracker.getOrders().get(i).item, leftover, OrderTracker.getOrders().get(i).dmg);
											
											if (InvUtils.hasEnough((Player) event.getWhoClicked(), items))
											{
												((Player) event.getWhoClicked()).getInventory().removeItem(items);
												cc3.getAccountManager().getAccount(((Player) event.getWhoClicked()).getName(), false).deposit(OrderTracker.getOrders().get(i).price, ((Player) event.getWhoClicked()).getWorld().getName(),
														"credit");
												
												OrderTracker.addCompletedOrder(OrderTracker.getOrders().get(i));
												OrderTracker.removeOrder(i);
											}
											else
											{
												event.getWhoClicked().closeInventory();
												((Player) event.getWhoClicked()).sendMessage(ChatColor.DARK_RED + "You do not have the required amount of items!");
												return;
											}
										}
										
										event.getWhoClicked().closeInventory();
										((Player) event.getWhoClicked()).sendMessage(ChatColor.AQUA + "Transaction Successful!");
										return;
									}
									else
									{
										event.getWhoClicked().closeInventory();
										((Player) event.getWhoClicked()).sendMessage(ChatColor.DARK_RED + "Too far away!");
										return;
									}
								}
							}
						}
						else
						{
							((Player) event.getWhoClicked()).sendMessage(ChatColor.DARK_RED + "No order selected to complete!");
						}	
					}
				}
			}
			event.getWhoClicked().closeInventory();
		}
		else if (event.getInventory().getName().equals(ConfirmGUI.INVENTORY_NAME_DELETE))
		{
			event.setCancelled(true);
			if (event.getCurrentItem() != null)
			{
				if (event.getCurrentItem().hasItemMeta())
				{
					if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "CONFIRM"))
					{
						for (int i = 0; i < OrderTracker.getOrders().size(); i++)
						{
							if (OrderTracker.getOrders().get(i).orderId.equals(DeletionConfirmationTask.pendingDeletions.get((Player) event.getWhoClicked())))
							{
								if (((Player) event.getWhoClicked()).getLocation().distance(OrderTracker.getOrders().get(i).loc) <= 10)
								{
									if (OrderTracker.getOrders().get(i).isSellOrder)
									{
										int numFullStacks = OrderTracker.getOrders().get(i).quantity / OrderTracker.getOrders().get(i).item.getMaxStackSize();
										int leftover = OrderTracker.getOrders().get(i).quantity % OrderTracker.getOrders().get(i).item.getMaxStackSize();
										ItemStack[] items = new ItemStack[leftover == 0 ? numFullStacks : numFullStacks + 1];
										
										for (int j = 0; j < numFullStacks; j++)
											items[j] = new ItemStack(OrderTracker.getOrders().get(i).item, OrderTracker.getOrders().get(i).item.getMaxStackSize(), OrderTracker.getOrders().get(i).dmg);
										if (leftover != 0)
											items[items.length - 1] = new ItemStack(OrderTracker.getOrders().get(i).item, leftover, OrderTracker.getOrders().get(i).dmg);
										
										if (InvUtils.hasRoom((Player) event.getWhoClicked(), items))
											((Player) event.getWhoClicked()).getInventory().addItem(items);
										else
										{
											event.getWhoClicked().closeInventory();
											((Player) event.getWhoClicked()).sendMessage(ChatColor.DARK_RED + "Not enough inventory space!");
											return;
										}
									}
									else
									{
										cc3.getAccountManager().getAccount(((Player) event.getWhoClicked()).getName(), false).deposit(OrderTracker.getOrders().get(i).price, ((Player) event.getWhoClicked()).getName(),
												"credit");
									}
									
									event.getWhoClicked().closeInventory();
									OrderTracker.removeOrder(i);
									((Player) event.getWhoClicked()).sendMessage(ChatColor.AQUA + "Your order has been deleted!");
									return;
								}
								else
								{
									event.getWhoClicked().closeInventory();
									((Player) event.getWhoClicked()).sendMessage(ChatColor.DARK_RED + "Too far away!");
									return;
								}
							}
						}
						((Player) event.getWhoClicked()).sendMessage(ChatColor.DARK_RED + "No pending deletions!");
					}
					else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "CANCEL"))
						event.getWhoClicked().closeInventory();
				}
			}
			event.getWhoClicked().closeInventory();
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("tradepost"))
		{
			if (sender instanceof Player)
			{
				if (args.length > 0)
				{
					if (args[0].equalsIgnoreCase("sell"))
					{
						if (args.length > 1)
						{
							try
							{
								Bukkit.getScheduler().scheduleSyncDelayedTask(this, new SellInfoTask((Player) sender, Integer.parseInt(args[1])), 1200);
								((Player) sender).sendMessage(ChatColor.AQUA + "Right click the tradepost sign that you would like to use for this transaction!");
								return true;
							}
							catch (NumberFormatException e)
							{
								((Player) sender).sendMessage(ChatColor.RED + "/tradepost sell <price>");
								return false;
							}
						}
						else
						{
							((Player) sender).sendMessage(ChatColor.RED + "/tradepost sell <price>");
							return false;
						}
					}
					else if (args[0].equalsIgnoreCase("buy"))
					{
						if (args.length >= 4)
						{
							if (Material.getMaterial(args[2].toUpperCase()) == null)
							{
								((Player) sender).sendMessage(ChatColor.RED + "Please enter a valid item name!");
								return false;
							}
							try
							{
								Bukkit.getScheduler().scheduleSyncDelayedTask(this, new BuyInfoTask((Player) sender, Integer.parseInt(args[1]), 
										Material.getMaterial(args[2].toUpperCase()), Integer.parseInt(args[3]), args.length == 5 ? Short.parseShort(args[4]) : 0), 1200);
								((Player) sender).sendMessage(ChatColor.AQUA + "Right click the tradepost sign that you would like to use for this transaction!");
								return true;
							}
							catch (NumberFormatException e)
							{
								((Player) sender).sendMessage(ChatColor.RED + "/tradepost buy <price> <item> <amount> [data]");
								return false;
							}
						}
						else
						{
							((Player) sender).sendMessage(ChatColor.RED + "/tradepost buy <price> <item> <amount> [data]");
							return false;
						}
					}
					else if (args[0].equalsIgnoreCase("manage"))
					{
						((Player) sender).openInventory(OrderManager.getManager((Player) sender));
						return true;
					}
					else if (args[0].equalsIgnoreCase("browse"))
					{
						if (args.length == 1)
							((Player) sender).openInventory(OrderBrowser.getBrowser((Player) sender, 0, null));
						else
						{
							ArrayList<Order> customSearch = SearchParser.parseSearch(args[1]);
							if (customSearch == null)
							{
								((Player) sender).sendMessage(ChatColor.DARK_RED + "Invalid syntax!");
								return false;
							}
							if (customSearch.isEmpty())
							{
								((Player) sender).sendMessage(ChatColor.DARK_RED + "No orders matching your search!");
								return false;
							}
							
							((Player) sender).openInventory(OrderBrowser.getBrowser((Player) sender, 0, customSearch));
							return true;
						}
					}
					else if (args[0].equalsIgnoreCase("complete"))
					{
						((Player) sender).sendMessage(ChatColor.AQUA + "Click on the trade post you would like to buy from/sell to.");
						Bukkit.getScheduler().scheduleSyncDelayedTask(this, new CompletionTask((Player) sender), 1200);
						return true;
					}
					else if (args[0].equalsIgnoreCase("claim"))
					{
						((Player) sender).sendMessage(ChatColor.AQUA + "Click on the tradepost you would like to claim items from");
						Bukkit.getScheduler().scheduleSyncDelayedTask(this, new ClaimTask((Player) sender), 1200);
						return true;
					}
					else if (args[0].equalsIgnoreCase("admin") && ((Player) sender).hasPermission("sqtrade.admin"))
					{
						//Admin commands - No need to check arg lengths
					}
				}
				else
				{
					((Player) sender).sendMessage(ChatColor.RED + "/tradepost <buy | sell | manage | browse | complete | claim>");
					return false;
				}
			}
		}
		return false;
	}
}