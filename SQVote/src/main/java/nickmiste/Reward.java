package nickmiste;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Reward
{
	SPONGES(new ItemStack[] {new ItemStack(Material.SPONGE, 1), new ItemStack(Material.SPONGE, 2), new ItemStack(Material.SPONGE, 3)}, new int[] {5, 3, 2}, 1, 1),
	DYE(new ItemStack[] {new ItemStack(Material.INK_SACK, 1, (short) 0), new ItemStack(Material.INK_SACK, 1, (short) 1),
			new ItemStack(Material.INK_SACK, 1, (short) 2), new ItemStack(Material.INK_SACK, 1, (short) 3), new ItemStack(Material.INK_SACK, 1, (short) 4),
			new ItemStack(Material.INK_SACK, 1, (short) 5), new ItemStack(Material.INK_SACK, 1, (short) 6), new ItemStack(Material.INK_SACK, 1, (short) 7),
			new ItemStack(Material.INK_SACK, 1, (short) 8), new ItemStack(Material.INK_SACK, 1, (short) 9), new ItemStack(Material.INK_SACK, 1, (short) 10),
			new ItemStack(Material.INK_SACK, 1, (short) 11), new ItemStack(Material.INK_SACK, 1, (short) 12), new ItemStack(Material.INK_SACK, 1, (short) 13),
			new ItemStack(Material.INK_SACK, 1, (short) 14)}, new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, 64, 6),
	ORES(new ItemStack[] {new ItemStack(Material.GOLD_ORE), new ItemStack(Material.IRON_ORE), new ItemStack(Material.COAL_ORE), new ItemStack(Material.LAPIS_ORE), new ItemStack(Material.DIAMOND_ORE),
			new ItemStack(Material.REDSTONE_ORE), new ItemStack(Material.EMERALD_ORE), new ItemStack(Material.QUARTZ_ORE)}, new int[] {4, 6, 7, 7, 1, 4, 4, 7}, 15, 3),
	ENCHANT(new ItemStack[] {new ItemStack(Material.EXP_BOTTLE), new ItemStack(Material.INK_SACK, 1, (short) 4), new ItemStack(Material.BOOK)}, new int[] {5, 1, 1}, 32, 3),
	BREWING(new ItemStack[] {new ItemStack(Material.GHAST_TEAR), new ItemStack(Material.FERMENTED_SPIDER_EYE), new ItemStack(Material.BLAZE_POWDER), new ItemStack(Material.MAGMA_CREAM),
			new ItemStack(Material.SPECKLED_MELON), new ItemStack(Material.GOLDEN_CARROT), new ItemStack(Material.RABBIT_FOOT), new ItemStack(Material.SUGAR),
			new ItemStack(Material.SPIDER_EYE), new ItemStack(Material.RAW_FISH, 3), new ItemStack(Material.GLOWSTONE_DUST), new ItemStack(Material.REDSTONE)}, 
			new int[] {1, 50, 25, 25, 25, 25, 25, 25, 25, 25, 50, 50}, 24, 6),
	CROP_FARM(new ItemStack[] {new ItemStack(Material.SEEDS), new ItemStack(Material.MELON_SEEDS), new ItemStack(Material.PUMPKIN_SEEDS),
			new ItemStack(Material.DIRT), new ItemStack(Material.CARROT_ITEM), new ItemStack(Material.POTATO_ITEM), new ItemStack(Material.SUGAR_CANE),
			new ItemStack(Material.COCOA), new ItemStack(Material.CACTUS), new ItemStack(Material.SAND), new ItemStack(Material.INK_SACK, 1, (short) 15)},
			new int[] {10, 10, 10, 50, 10, 10, 10, 10, 10, 10, 50}, 128, 12),
	FISH(new ItemStack[] {new ItemStack(Material.RAW_FISH), new ItemStack(Material.RAW_FISH, 1, (short) 1), new ItemStack(Material.RAW_FISH, 1, (short) 2), new ItemStack(Material.RAW_FISH, 1, (short) 3),
			new ItemStack(Material.BOW), new ItemStack(Material.BOOK), new ItemStack(Material.FISHING_ROD), new ItemStack(Material.NAME_TAG), new ItemStack(Material.SADDLE),
			new ItemStack(Material.WATER_LILY), new ItemStack(Material.BOWL), new ItemStack(Material.FISHING_ROD), new ItemStack(Material.LEATHER), new ItemStack(Material.LEATHER_BOOTS),
			new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.STICK), new ItemStack(Material.STRING), new ItemStack(Material.POTION), new ItemStack(Material.BONE),
			new ItemStack(Material.INK_SACK, 10), new ItemStack(Material.TRIPWIRE_HOOK)}, new int[] {5100, 2125, 170, 1105, 84, 84, 84, 84, 84, 84, 120, 24, 120, 120, 120, 60, 60, 120, 120, 12, 120}, 128, 6);
	
	
	public int totalValue;
	//Both arrays must have the same length
	public ItemStack[] items;
	public int[] weights;
	double price;
	
	Reward(ItemStack[] items, int[] weights, int totalValue, double price)
	{
		this.items = items;
		this.weights = weights;
		this.totalValue = totalValue;
		this.price = price;
	}
	
	public ArrayList<ItemStack> getItems()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		double[] normalizedWeights = normalizeWeights(weights);
		
		for (int i = 0; i < totalValue; i++)
		{
			double randNum = Math.random() * 101;
			for (int j = 0; j < items.length; j++)
			{
				if (randNum <= normalizedWeights[j])
				{
					stacks.add(items[j]);
					break;
				}
			}
		}
		return stacks;
	}
	
	/* Distributes the weights such that they all add to 100,
	 * then makes the weights cumulative
	 */
	private static double[] normalizeWeights(int[] weights)
	{
		double[] newWeights = new double[weights.length];
		int sum = 0;
		for (int i : weights)
			sum += i;
		double multiplier = 100.0/sum;
		for (int i = 0; i < weights.length; i++)
			newWeights[i] = weights[i] * multiplier;
		
		for (int i = 1; i < newWeights.length; i++)
			newWeights[i] += newWeights[i-1];
		
		return newWeights;
	}
}