
package us.higashiyama.george.Redeem;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin implements Listener {

	//@formatting:off
	public static Permission permission = null;
	public static Economy economy = null;
	

	public static playerItem[] bronzePrizes1 = {
        new playerItem(1, 16, -1, 0, 0), 
        new playerItem(4, 16, -1, 0, 0), 
        new playerItem(15, 2, -1, 0, 0), 
        new playerItem(17, 16, -1, 0, 0), 
        new playerItem(6, 8, -1, 0, 0), 
	}; 
	

	

						 
							 
	
	public static playerItem[] bronzePrizes2 = {
		new playerItem(35, 8, -1, 0, 0), 
		new playerItem(35, 8, -1, 0, 1), 
		new playerItem(35, 8, -1, 0, 2), 
		new playerItem(35, 8, -1, 0, 3), 
		new playerItem(35, 8, -1, 0, 4), 
		new playerItem(35, 8, -1, 0, 5), 
		new playerItem(35, 8, -1, 0, 6), 
		new playerItem(35, 8, -1, 0, 7), 
		new playerItem(35, 8, -1, 0, 8), 
		new playerItem(35, 8, -1, 0, 9), 
		new playerItem(35, 8, -1, 0, 10), 
		new playerItem(35, 8, -1, 0, 11), 
		new playerItem(35, 8, -1, 0, 12), 
		new playerItem(35, 8, -1, 0, 13), 
		new playerItem(35, 8, -1, 0, 14), 
    	new playerItem(35, 8, -1, 0, 15) 
		};
	
	public static playerItem[] bronzePrizes3 = {
        new playerItem(23, 1, -1, 0, 0), 
        new playerItem(29, 1, -1, 0, 0), 
        new playerItem(33, 1, -1, 0, 0), 
        new playerItem(20, 8, -1, 0, 0), 
        new playerItem(19, 1, -1, 0, 0), 
	}; 
	
	public static playerItem[] silverPrizes1 = {
        new playerItem(2, 6, -1, 0, 0), 
        new playerItem(47, 8, -1, 0, 0), 
        new playerItem(174, 8, -1, 0, 0), 
        new playerItem(79, 12, -1, 0, 0), 
        new playerItem(116, 1, -1, 0, 0), 
	}; 
	
	public static playerItem[] silverPrizes2 = {
        new playerItem(322, 1, -1, 0, 0), 
        new playerItem(322, 1, -1, 0, 0), 
        new playerItem(365, 64, -1, 0, 0), 
        new playerItem(354, 16, -1, 0, 0), 
        new playerItem(400, 24, -1, 0, 0), 
	}; 
	
	public static playerItem[] silverPrizes3 = {
		new playerItem(35, 48, -1, 0, 0), 
		new playerItem(35, 48, -1, 0, 1), 
		new playerItem(35, 48, -1, 0, 2), 
		new playerItem(35, 48, -1, 0, 3), 
		new playerItem(35, 48, -1, 0, 4), 
		new playerItem(35, 48, -1, 0, 5), 
		new playerItem(35, 48, -1, 0, 6), 
		new playerItem(35, 48, -1, 0, 7), 
		new playerItem(35, 48, -1, 0, 8), 
		new playerItem(35, 48, -1, 0, 9), 
		new playerItem(35, 48, -1, 0, 10), 
		new playerItem(35, 48, -1, 0, 11), 
		new playerItem(35, 48, -1, 0, 12), 
		new playerItem(35, 48, -1, 0, 13), 
		new playerItem(35, 48, -1, 0, 14), 
    	new playerItem(35, 48, -1, 0, 15) 
		};
	
	public static playerItem[] goldPrizes1 = {
		new playerItem(35, 128, -1, 0, 0), 
		new playerItem(35, 128, -1, 0, 1), 
		new playerItem(35, 128, -1, 0, 2), 
		new playerItem(35, 128, -1, 0, 3), 
		new playerItem(35, 128, -1, 0, 4), 
		new playerItem(35, 128, -1, 0, 5), 
		new playerItem(35, 128, -1, 0, 6), 
		new playerItem(35, 128, -1, 0, 7), 
		new playerItem(35, 128, -1, 0, 8), 
		new playerItem(35, 128, -1, 0, 9), 
		new playerItem(35, 128, -1, 0, 10), 
		new playerItem(35, 128, -1, 0, 11), 
		new playerItem(35, 128, -1, 0, 12), 
		new playerItem(35, 128, -1, 0, 13), 
		new playerItem(35, 128, -1, 0, 14), 
    	new playerItem(35, 128, -1, 0, 15) 
		};
	
	public static playerItem[] goldPrizes2 = {
		new playerItem(403, 1, 0, 4, 0), 
		new playerItem(403, 1, 34, 3, 0), 
		new playerItem(403, 1, 16, 5, 0), 
		new playerItem(403, 1, 19, 2, 0), 
		new playerItem(403, 1, 32, 5, 0), 
		new playerItem(403, 1, 35, 3, 0), 
		new playerItem(403, 1, 33, 1, 0), 
		new playerItem(403, 1, 48, 5, 0), 
		new playerItem(403, 1, 51, 1, 0)
		};
	
	public static playerItem[] goldPrizes3 = {
		
		new playerItem(384, 1024, -1, 0, 0), 
		};
	
//@formatting:on	

	public void onEnable() {

		setupPermissions();
		setupEconomy();
		getServer().getPluginManager().registerEvents(this, this);
		Database.setUp();
	}

	public static void onClick(InventoryClickEvent e) {

		if (e.getCursor().getType() == Material.ENCHANTED_BOOK) {
			ItemStack i = e.getCursor();
			if (i == null)
				return;
			if (i.getAmount() > 1) {
				i.setType(Material.AIR);
				i.setAmount(0);
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if ((cmd.getName().equalsIgnoreCase("redeem")) && (sender instanceof Player)) {
			Player p = (Player) sender;
			if (args.length > 0) {
				if ((args[0].equalsIgnoreCase("help"))) {
					showHelpDialogue(p);
					return true;
				}
				if (args[0].equalsIgnoreCase("menu")) {

					openGui(p);
					return true;
				}
			} else {
				p.sendMessage(ChatColor.GOLD + "[Redeem] Please use /redeem help");
				return true;
			}
		} else if (args[0].equalsIgnoreCase("add")) {
			if (Database.hasKey(args[1])) {
				Database.updatePlayerEntry(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			} else {
				// player, tokens, money, sponges
				Database.newPlayerEntry(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			}

		}
		return true;
	}

	//@formatting:off
	private void openGui(final Player p) {
			
		final int[] prizes = Database.getPrizes(p.getName());
		
		final RandomVoteGUI menu;
		 menu = new RandomVoteGUI("RandomVote Menu", 27, new RandomVoteGUI.OptionClickEventHandler() {
	

			public void onOptionClick(RandomVoteGUI.OptionClickEvent event) {
				event.setWillDestroy(true);
				final int[] prizes = Database.getPrizes(p.getName());
				switch(event.getPosition()) {
					case 9:
						
						if(prizes[2] == 0) break;
						givePlayerItem(p,  new playerItem(19, prizes[2], -1, 0, 0));
						Database.updatePlayerEntry(p.getName(), 0, 0, -prizes[2]);
						break;
					case 18:
						if(prizes[1] == 0) break;
						economy.depositPlayer(p.getName(), prizes[1]);
						Database.updatePlayerEntry(p.getName(), 0, -prizes[1], 0);
						break;
					case 6:
						if(prizes[0] < 1) {
							p.sendMessage(ChatColor.GOLD + "Not enough coins");
							return;
						}
						givePlayerItem(p, getRandom(bronzePrizes1));
						Database.updatePlayerEntry(p.getName(), -1, 0, 0);
						break;
					case 15:
						if(prizes[0] < 2) {
							p.sendMessage(ChatColor.GOLD + "Not enough coins");
							return;
						}
					
						givePlayerItem(p, getRandom(bronzePrizes3));
						Database.updatePlayerEntry(p.getName(), -2, 0, 0);
						break;
					case 24:
						if(prizes[0] < 3) {
							p.sendMessage(ChatColor.GOLD + "Not enough coins");
							return;
						}
						givePlayerItem(p, getRandom(bronzePrizes2));
						Database.updatePlayerEntry(p.getName(), -3, 0, 0);
						break;
						
					case 7:
						if(prizes[0] < 13) {
							p.sendMessage(ChatColor.GOLD + "Not enough coins");
							return;
						}
						givePlayerItem(p, getRandom(silverPrizes1));
						Database.updatePlayerEntry(p.getName(), -13, 0, 0);
						break;
					case 16:
						if(prizes[0] < 14) {
							p.sendMessage(ChatColor.GOLD + "Not enough coins");
							return;
						}
						givePlayerItem(p, getRandom(silverPrizes2));
						Database.updatePlayerEntry(p.getName(), -14, 0, 0);
						break;
					case 25:
						if(prizes[0] < 15) {
							p.sendMessage(ChatColor.GOLD + "Not enough coins");
							return;
						}
						givePlayerItem(p, getRandom(silverPrizes3));
						Database.updatePlayerEntry(p.getName(), -15, 0, 0);
						break;
					case 8:
						if(prizes[0] < 32) {
							p.sendMessage(ChatColor.GOLD + "Not enough coins");
							return;
						}
						givePlayerItem(p, getRandom(goldPrizes1));
						Database.updatePlayerEntry(p.getName(), -32, 0, 0);
						break;
					case 17:
						if(prizes[0] < 48) {
							p.sendMessage(ChatColor.GOLD + "Not enough coins");
							return;
						}
						givePlayerEnchantedBook(p, getRandom(goldPrizes2));
						Database.updatePlayerEntry(p.getName(), -48, 0, 0);
						break;
					case 26:
						if(prizes[0] < 64) {
							p.sendMessage(ChatColor.GOLD + "Not enough coins");
							return;
						}
						givePlayerItem(p, getRandom(goldPrizes3));
						Database.updatePlayerEntry(p.getName(), -90, 0, 0);
						
						break;
					
					
				
				}
			
					
				
				
			}
		}, this);

		menu.setOption(0, new ItemStack(Material.EMERALD, prizes[0]), ChatColor.GOLD + "" + prizes[0]+ ": RandomVote Tokens");
		menu.setOption(9, new ItemStack(Material.SPONGE, prizes[2]), ChatColor.GOLD + "" + prizes[2] + ": Sponges",new String[] { ChatColor.RED + "Click to redeem!" });
		menu.setOption(18, new ItemStack(Material.GOLD_RECORD, prizes[1]), ChatColor.GOLD + "" + prizes[1]+ ": Credits", new String[] { ChatColor.RED + "Click to redeem!" });
		//Bronze token items
		menu.setOption(6, new ItemStack(Material.BRICK, 1), ChatColor.GOLD + "Random Natural Block", new String[] { ChatColor.RED + "Price: 1 Token" });
		menu.setOption(15, new ItemStack(Material.BRICK, 2), ChatColor.GOLD + "Random Ship Mechanic", new String[] { ChatColor.RED + "Price: 2 Tokens" });
		menu.setOption(24, new ItemStack(Material.BRICK, 3), ChatColor.GOLD + "Random Polymer x8", new String[] { ChatColor.RED + "Price: 3 Tokens" });
		//Silver token items
		menu.setOption(7, new ItemStack(Material.IRON_INGOT, 13), ChatColor.GOLD + "Random Rare Block", new String[] { ChatColor.RED + "Price: 13 Tokens" });
		menu.setOption(16, new ItemStack(Material.IRON_INGOT, 14), ChatColor.GOLD + "Random Food", new String[] { ChatColor.RED + "Price: 14 Tokens" });
		menu.setOption(25, new ItemStack(Material.IRON_INGOT, 15), ChatColor.GOLD + "Random Polymer x48", new String[] { ChatColor.RED + "Price: 15 Tokens" });
		//Diamond token items
		menu.setOption(8, new ItemStack(Material.DIAMOND, 32), ChatColor.GOLD + "Random Polymer x128", new String[] { ChatColor.RED + "Price: 32 Tokens" });
		menu.setOption(17, new ItemStack(Material.DIAMOND, 48), ChatColor.GOLD + "Max Level Enchanted Books", new String[] { ChatColor.RED + "Price: 48 Tokens" });
		menu.setOption(26, new ItemStack(Material.DIAMOND, 64), ChatColor.GOLD + "x16 Stacks of Enchanting Bottles", new String[] { ChatColor.RED + "Price: 90 Tokens" });
		
		
		
		menu.open(p);
	

	}
	
	
	
	//@formatting:on

	public static playerItem getRandom(playerItem[] piArray) {

		return piArray[(int) (Math.random() * piArray.length)];
	}

	// Help the nubs
	public void showHelpDialogue(Player p) {

		p.sendMessage(ChatColor.GOLD + "[Redeem] " + "Redeem prizes from voting, donating, and more!");
		p.sendMessage(ChatColor.GOLD + "[RV] " + "/redeem menu | Opens the redeeming menu");
	}

	public void givePlayerItem(Player p, playerItem pi) {

		int enchantment = pi.enchantment;
		int ID = pi.itemID;
		int quantity = pi.quantity;
		short data = (short) pi.data;
		int enchantmentLevel = pi.enchantmentLevel;
		if (quantity == 0)
			return;
		if (enchantment == -1) {
			short itemdata = data;
			ItemStack i = new ItemStack(ID, quantity, itemdata);
			p.getInventory().addItem(new ItemStack[] { i });
			p.sendMessage(ChatColor.GOLD + "[RV] " + "You recieved your reward! Thanks for voting!");
		} else {
			ItemStack i = new ItemStack(ID, quantity);
			Enchantment e = new EnchantmentWrapper(enchantment);
			i.addEnchantment(e, enchantmentLevel);
			p.getInventory().addItem(new ItemStack[] { i });
			p.sendMessage(ChatColor.GOLD + "[RV] " + "You recieved your reward! Thanks for voting!");
		}
	}

	public void givePlayerEnchantedBook(Player p, playerItem pi) {

		int enchantment = pi.enchantment;
		int ID = pi.itemID;
		int quantity = pi.quantity;
		short data = (short) pi.data;
		int enchantmentLevel = pi.enchantmentLevel;
		if (quantity == 0)
			return;
		ItemStack i = new ItemStack(ID, quantity);
		EnchantmentStorageMeta enchantments = (EnchantmentStorageMeta) i.getItemMeta();
		enchantments.setDisplayName("Random Vote Enchantment Book");
		enchantments.addStoredEnchant((Enchantment) new EnchantmentWrapper(enchantment), enchantmentLevel, true);
		i.setItemMeta(enchantments);
		p.getInventory().addItem(new ItemStack(i));
		p.sendMessage(ChatColor.GOLD + "[RV] " + "You recieved your reward! Thanks for voting!");
	}

	// Setting up permissions for Vault use
	private boolean setupPermissions() {

		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		return permission != null;
	}

	// Setting up the economy for Vault use
	private boolean setupEconomy() {

		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}
		return economy != null;
	}
}
