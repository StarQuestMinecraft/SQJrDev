package nickmiste;

import org.bukkit.Location;
import org.bukkit.Material;

public class Structure 
{
	public static boolean isValid(Location loc)
	{
		Location gold = getGoldRelativeToSign(loc);

		if (gold == null)
			return false;
		
		return (getBlockTypeRelativeToLoc(loc, 0, 0, 0).equals(Material.WALL_SIGN) &&
				getBlockTypeRelativeToLoc(gold, 1, 0, 0).equals(Material.STAINED_GLASS) &&
				getBlockTypeRelativeToLoc(gold, -1, 0, 0).equals(Material.STAINED_GLASS) &&
				getBlockTypeRelativeToLoc(gold, 0, 1, 0).equals(Material.STAINED_GLASS) &&
				getBlockTypeRelativeToLoc(gold, 0, -1, 0).equals(Material.STAINED_GLASS) &&
				getBlockTypeRelativeToLoc(gold, 0, 0, 1).equals(Material.STAINED_GLASS) &&
				getBlockTypeRelativeToLoc(gold, 0, 0, -1).equals(Material.STAINED_GLASS) &&
				getBlockTypeRelativeToLoc(loc, 0, -1, 0).equals(Material.CHEST) &&
				getBlockTypeRelativeToLoc(loc, 0, 1, 0).equals(Material.FURNACE));
	}
	
	private static Material getBlockTypeRelativeToLoc(Location loc, int x, int y, int z)
	{
		return loc.getWorld().getBlockAt(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z)).getType();
	}
	
	public static Location getGoldRelativeToSign(Location loc)
	{
		Material[] validCores = new Material[] {Material.COAL_BLOCK, Material.IRON_BLOCK, Material.GOLD_BLOCK, Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK};
		Location gold = null;
		
		for (Material m : validCores)
		{
			if (getBlockTypeRelativeToLoc(loc, 2, 0, 0).equals(m))
			{
				gold = new Location(loc.getWorld(), loc.getX() + 2, loc.getY(), loc.getZ());
				break;
			}
			else if (getBlockTypeRelativeToLoc(loc, -2, 0, 0).equals(m))
			{
				gold = new Location(loc.getWorld(), loc.getX() - 2, loc.getY(), loc.getZ());
				break;
			}
			else if (getBlockTypeRelativeToLoc(loc, 0, 0, 2).equals(m))
			{
				gold = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 2);
				break;
			}
			else if (getBlockTypeRelativeToLoc(loc, 0, 0, -2).equals(m))
			{
				gold = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 2);
				break;
			}
		}
		return gold;
	}
}