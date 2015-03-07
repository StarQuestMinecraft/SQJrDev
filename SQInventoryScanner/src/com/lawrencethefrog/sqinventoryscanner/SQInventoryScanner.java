package com.lawrencethefrog.sqinventoryscanner;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Furnace;
import org.bukkit.configuration.file.FileConfiguration;
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

public class SQInventoryScanner extends JavaPlugin implements Listener{
	static long ticksOnFor = 20L;
	
	@SuppressWarnings("serial")
	static ArrayList<Material> preciseStructure = new ArrayList<Material>(){{
		add(Material.STONE_PLATE);
		add(Material.GOLD_BLOCK);
		add(Material.REDSTONE_BLOCK);
		add(Material.EMERALD_BLOCK);
		add(Material.FURNACE);
	}};
	@SuppressWarnings("serial")
	static ArrayList<Material> impreciseStructure = new ArrayList<Material>(){{
		add(Material.STONE_PLATE);
		add(Material.IRON_BLOCK);
		add(Material.REDSTONE_BLOCK);
		add(Material.EMERALD_BLOCK);
		add(Material.FURNACE);
	}};	
		
	static int bottomBlockDepth = preciseStructure.size() -1;
	
	@Override
	public void onEnable(){				
		getServer().getLogger().info("Successfully started SQInventoryScanner");
		getServer().getPluginManager().registerEvents(this, this);
		setupConfig();
	}
	
	private void setupConfig(){
		//if there is no config, creates one from the saved on in the jar
		saveDefaultConfig();
		
		FileConfiguration config = getConfig();
		
		//attempts to change structure lists if there is a valid setting in the config
		//if there is not valid setting in the config, does nothing
		tryUpdateStructure(config, "PreciseMaterial", preciseStructure, 1);
		tryUpdateStructure(config, "ImpreciseMaterial", impreciseStructure, 1);
		tryUpdateStructure(config, "MovingBlockMaterials.Upper", preciseStructure, 2);
		tryUpdateStructure(config, "MovingBlockMaterials.Upper", impreciseStructure, 2);
		tryUpdateStructure(config, "MovingBlockMaterials.Lower", preciseStructure, 3);
		tryUpdateStructure(config, "MovingBlockMaterials.Lower", impreciseStructure, 3);
		
		//does the same with the ticksOnFor variable
		Long ticksOnForScanned = config.getLong("TicksOnFor");
		if(ticksOnForScanned != null){
			ticksOnFor = ticksOnForScanned;
		}
	}
	
	
	private void tryUpdateStructure(FileConfiguration config, String configPath, ArrayList<Material> structure, int index){
		Material material = Material.getMaterial(config.getString(configPath));
		if (material != null){
			structure.set(index, material);
			structure.set(index, material);
		}
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
		
		//tests for structure and using list
		boolean isPrecise = true;		
		for (int blocksDown = 0; blocksDown <= bottomBlockDepth; blocksDown++ ){
			Material blockMaterial = plate.getRelative(BlockFace.DOWN, blocksDown).getType();
			if(!(blockMaterial == preciseStructure.get(blocksDown))){		//if blocks found do not match precise structure
				if(blockMaterial == impreciseStructure.get(blocksDown)){	//checks if they match imprecise structure
					isPrecise = false;
				} else return;												//if not, terminates
			}
		}
		
		List<Material> currentStructure = isPrecise ? preciseStructure : impreciseStructure;
		
		//if detection tests pass, scans inventory
				
		if(checkInventory(isPrecise, plate.getRelative(BlockFace.DOWN, currentStructure.indexOf(Material.FURNACE)), player)){
			//if inventory scan finds item match, output signal
			outputRedstone(
					plate.getRelative(BlockFace.DOWN, 2), 
					plate.getRelative(BlockFace.DOWN, 3)
					);
		}
	}
	
	private boolean checkInventory(boolean isPrecise, Block block, Player player){
		FurnaceInventory furnaceInv = ((Furnace)block.getState()).getInventory();
		Inventory playerInv = player.getInventory();
		
		if(furnaceInv.getSmelting() != null){			//if furnace contains something
			Material furnaceItemType = furnaceInv.getSmelting().getType();
			for (ItemStack iS : playerInv){
				if (iS != null){
					Material iSMaterial = iS.getType();
					if(iSMaterial == furnaceItemType){	//if item types match
						if(isPrecise){					//if precise, checks item name
							String furnaceItemName = furnaceInv.getSmelting().getItemMeta().getDisplayName();
							String playerItemName = iS.getItemMeta().getDisplayName();
							//checks if either item is unrenamed
							if(furnaceItemName == null || playerItemName == null){
								//checks if both items are unrenamed
								if (furnaceItemName == null && playerItemName == null){
									return true;
								}
								else return false;
							} else if (furnaceItemName.equals(playerItemName)){
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
		
		final Material upperBlockMaterial = upperMovingBlock.getType();
		final Material lowerBlockMaterial = lowerMovingBlock.getType();
		
		lowerMovingBlock.setType(upperBlockMaterial);
		upperMovingBlock.setType(lowerBlockMaterial);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this,
				new Runnable(){
					Block savedUpperMovingBlock = upperMovingBlock;
					Block savedLowerMovingBlock = lowerMovingBlock;
					
					//saves moving block types (not strictly necessary but makes method more robust in case other things change)
					Material savedUpperBlockMaterial = upperBlockMaterial;
					Material savedLowerBlockMaterial = lowerBlockMaterial;
					@Override
					public void run(){
						//checks if the blocks are still there before changing them (to prevent resource spawning)
						if(savedUpperMovingBlock.getType() == savedLowerBlockMaterial && savedLowerMovingBlock.getType() == savedUpperBlockMaterial){
							savedUpperMovingBlock.setType(savedUpperBlockMaterial);
							savedLowerMovingBlock.setType(savedLowerBlockMaterial);
						}
					}
				}, ticksOnFor);
	}
}
