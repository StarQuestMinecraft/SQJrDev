package nickmiste;

import org.bukkit.Location;
import org.bukkit.Material;

public class Structure 
{
	public static boolean isValid(Location loc)
	{
		Location block = getBlockBehindSign(loc);
		
		if (block == null)
			return false;
		
		return (getBlockTypeRelativeToLoc(loc, 0, -1, 0).equals(Material.CHEST) &&
				getBlockTypeRelativeToLoc(block, 0, -1, 0).equals(Material.EMERALD_BLOCK) &&
				getBlockTypeRelativeToLoc(block, 0, 1, 0).equals(Material.LAPIS_BLOCK));
	}
	
	private static Material getBlockTypeRelativeToLoc(Location loc, int x, int y, int z)
	{
		return loc.getWorld().getBlockAt(new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z)).getType();
	}
	
	private static Location getBlockBehindSign(Location loc)
	{
		Location block = null;
		
		if (getBlockTypeRelativeToLoc(loc, 1, 0, 0).equals(Material.EMERALD_BLOCK))
			block = new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ());
		else if (getBlockTypeRelativeToLoc(loc, -1, 0, 0).equals(Material.EMERALD_BLOCK))
			block = new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ());
		else if (getBlockTypeRelativeToLoc(loc, 0, 0, 1).equals(Material.EMERALD_BLOCK))
			block = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 1);
		else if (getBlockTypeRelativeToLoc(loc, 0, 0, -1).equals(Material.EMERALD_BLOCK))
			block = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 1);
		
		return block;
	}
}
