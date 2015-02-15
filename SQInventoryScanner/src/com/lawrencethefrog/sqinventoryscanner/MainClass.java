package com.lawrencethefrog.sqinventoryscanner;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin implements Listener{
	static final Material impreciseMaterial = Material.IRON_BLOCK;
	static final Material preciseMaterial = Material.GOLD_BLOCK;
	static final long waitTimeTicks = 20L;
	
	
	@Override
	public void onEnable(){
		getServer().getLogger().info("Successfully started SQInventoryScanner");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlateStep(PlayerInteractEvent event){
		//check if the action is a physical type action
		if(event.getAction() == Action.PHYSICAL){
			//check if the event block is a pressure plate
			Material sourceMaterial = event.getClickedBlock().getType();
			if(sourceMaterial == Material.STONE_PLATE){
				runDetectionAndScan(event.getPlayer(), event.getClickedBlock());
			}
		}
	}
	
	
	
	private void runDetectionAndScan(Player player, Block plate) {
		
		boolean isPrecise;
				
		if(isBlockUnderP(plate, 1, preciseMaterial)){								//checks first block under plate (precision block)			
			isPrecise = true;
		} else if (isBlockUnderP(plate, 1, impreciseMaterial)){
			isPrecise = false;
		} else return;
				
		if (!isBlockUnderP(plate, 2, Material.REDSTONE_BLOCK)) return;				//checks first of 2 moving blocks
		
		if (!isBlockUnderP(plate, 3, Material.EMERALD_BLOCK)) return;				//checks second of 2 moving blocks
		
		if (!isBlockUnderP(plate, 4, Material.FURNACE)) return;						//checks furnace
		
		//if all detection tests pass, scans inventory
				
		if(checkInventory(isPrecise, plate.getRelative(BlockFace.DOWN, 4), player)){
			//if inventory scan finds item match, output signal
			outputRedstone(plate.getRelative(BlockFace.DOWN, 2), plate.getRelative(BlockFace.DOWN, 3));
		}
	}
	
	private boolean isBlockUnderP(Block plate, int blocksUnderP, Material type){
		if (plate.getRelative(BlockFace.DOWN, blocksUnderP).getType() == type){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean checkInventory(boolean isPrecise, Block block, Player player){
		Furnace furnace = (Furnace)block.getState();
		FurnaceInventory furnaceInv = furnace.getInventory();
		Inventory playerInv = player.getInventory();
		
		if(furnaceInv.getSmelting() != null){			//if furnace contains something
			Material furnaceItemType = furnaceInv.getSmelting().getType();
			for (ItemStack iS : playerInv){
				if (iS != null){
					Material iSMaterial = iS.getType();
					if(iSMaterial == furnaceItemType){	//if item types match
						if(isPrecise){					//if precise, checks item name
							if (furnaceInv.getSmelting().getItemMeta().getDisplayName() == iS.getItemMeta().getDisplayName()){
								return true;
							}
						} else {						//if imprecise, returns true
							return true;
						}
					}
				}
			}
		}
		//if no item match
		return false;
	}
	
	private void outputRedstone(final Block upperMovingBlock, final Block lowerMovingBlock){
		
		lowerMovingBlock.setType(Material.REDSTONE_BLOCK);
		upperMovingBlock.setType(Material.EMERALD_BLOCK);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this,
				new Runnable(){
					Block savedUpperMovingBlock = upperMovingBlock;
					Block savedLowerMovingBlock = lowerMovingBlock;
					@Override
					public void run(){
						//checks if the blocks are still there before changing them (to prevent resource spawning)
						if(savedUpperMovingBlock.getType() == Material.EMERALD_BLOCK && savedLowerMovingBlock.getType() == Material.REDSTONE_BLOCK){
							savedUpperMovingBlock.setType(Material.REDSTONE_BLOCK);
							savedLowerMovingBlock.setType(Material.EMERALD_BLOCK);
						}
					}
				}, waitTimeTicks);
	}
}
