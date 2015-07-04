package nickmiste;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Reward
{
	SPONGE(new ItemStack [] {new ItemStack(Material.SPONGE, 1), new ItemStack(Material.SPONGE, 2)}, new int[] {1, 1}, 1, 1),
	STEAK(new ItemStack[] {new ItemStack(Material.COOKED_BEEF, 64)}, new int[] {1}, 1, 8),
	IRON_TOOL(new ItemStack[] {new ItemStack(Material.IRON_AXE), new ItemStack(Material.IRON_HOE), new ItemStack(Material.IRON_PICKAXE), new ItemStack(Material.IRON_SPADE), new ItemStack(Material.IRON_SWORD)},
			new int[] {1, 1, 1, 1, 1}, 1, 4),
	DIAMOND_TOOL(new ItemStack[] {new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.DIAMOND_HOE), new ItemStack(Material.DIAMOND_PICKAXE), new ItemStack(Material.DIAMOND_SPADE), new ItemStack(Material.DIAMOND_SWORD)},
			new int[] {1, 1, 1, 1, 1}, 1, 6),
	MUSIC_DISK(new ItemStack[] {new ItemStack(Material.GOLD_RECORD), new ItemStack(Material.GREEN_RECORD), new ItemStack(Material.RECORD_3), new ItemStack(Material.RECORD_4), new ItemStack(Material.RECORD_5),
			new ItemStack(Material.RECORD_6), new ItemStack(Material.RECORD_7), new ItemStack(Material.RECORD_8), new ItemStack(Material.RECORD_9), new ItemStack(Material.RECORD_10), new ItemStack(Material.RECORD_11),
			new ItemStack(Material.RECORD_12)}, new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 1, 6),
	ORE(new ItemStack[] {new ItemStack(Material.GOLD_ORE), new ItemStack(Material.IRON_ORE), new ItemStack(Material.COAL_ORE), new ItemStack(Material.LAPIS_ORE), new ItemStack(Material.DIAMOND_ORE),
			new ItemStack(Material.REDSTONE_ORE), new ItemStack(Material.EMERALD_ORE), new ItemStack(Material.QUARTZ_ORE)}, new int[] {5, 2, 1, 1, 7, 4, 5, 1}, 25, 3),
//	SHIP_KIT(new ItemStack[] {new ItemStack(Material.DISPENSER), new ItemStack(Material.DROPPER), new ItemStack(Material.WORKBENCH), new ItemStack(Material.FURNACE),
//			new ItemStack(Material.STEP, 8, (short) 5), new ItemStack(Material.STEP, 8, (short) 7), new ItemStack(Material.GLASS, 8), new ItemStack(Material.SIGN), 
//			new ItemStack(Material.QUARTZ_STAIRS, 8), new ItemStack(Material.SMOOTH_STAIRS, 8)}, new int[] {1, 1, 1, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1},
//			64, 24),
//	MEGA_SHIP_KIT(new ItemStack[] {new ItemStack(Material.DISPENSER), new ItemStack(Material.DROPPER), new ItemStack(Material.WORKBENCH), new ItemStack(Material.FURNACE), 
//			new ItemStack(Material.STEP, 8, (short) 5), new ItemStack(Material.STEP, 8, (short) 7), new ItemStack(Material.GLASS, 8), new ItemStack(Material.SIGN), 
//			new ItemStack(Material.QUARTZ_STAIRS, 8), new ItemStack(Material.SMOOTH_STAIRS, 8)}, new int[] {1, 1, 1, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1},
//			128, 32),
	DYE(new ItemStack[] {new ItemStack(Material.INK_SACK, 1, (short) 0), new ItemStack(Material.INK_SACK, 1, (short) 1), new ItemStack(Material.INK_SACK, 1, (short) 2), new ItemStack(Material.INK_SACK, 1, (short) 3),
			new ItemStack(Material.INK_SACK, 1, (short) 4), new ItemStack(Material.INK_SACK, 1, (short) 5), new ItemStack(Material.INK_SACK, 1, (short) 6), new ItemStack(Material.INK_SACK, 1, (short) 7), 
			new ItemStack(Material.INK_SACK, 1, (short) 8), new ItemStack(Material.INK_SACK, 1, (short) 9), new ItemStack(Material.INK_SACK, 1, (short) 10), new ItemStack(Material.INK_SACK, 1, (short) 11), 
			new ItemStack(Material.INK_SACK, 1, (short) 12), new ItemStack(Material.INK_SACK, 1, (short) 13), new ItemStack(Material.INK_SACK, 1, (short) 14)}, new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 64, 6),
	ENCH(new ItemStack[] {new ItemStack(Material.EXP_BOTTLE), new ItemStack(Material.BOOK), new ItemStack(Material.INK_SACK, 1, (short) 4)}, new int[] {3, 2, 1}, 48, 16);
	
	public int totalValue;
	//Both arrays must have the same length
	public ItemStack[] items;
	public int[] values;
	
	double price;
	
	Reward(ItemStack[] items, int[] values, int totalValue, double price)
	{
		this.items = items;
		this.values = values;
		this.totalValue = totalValue;
		this.price = price;
	}
	
	public ArrayList<ItemStack> getItems()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		
		int usedValue = 0;
		do
		{
			int index = (int) (Math.random() * (items.length));
			ItemStack stack = items[index];
			usedValue += values[index];
			stacks.add(stack);
		}
		while (usedValue < totalValue);
		
		return stacks;
	}
}
