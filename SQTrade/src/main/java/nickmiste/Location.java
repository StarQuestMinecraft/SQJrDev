package nickmiste;

public class Location
{
	public String world;
	public int x;
	public int y;
	public int z;
	
	public Location(String world, int x, int y, int z)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double distance(org.bukkit.Location loc)
	{
		return loc.distance(new org.bukkit.Location(loc.getWorld(), this.x, this.y, this.z));
	}
}
