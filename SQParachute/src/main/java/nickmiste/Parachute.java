package nickmiste;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Parachute 
{
	public Player player;
	protected short color;
	
	protected Location loc;
	
	public static ArrayList<Player> parachuting = new ArrayList<Player>();
	
	public Parachute(Player player, short color)
	{
		this.player = player;
		this.color = color;
		this.loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
		
		parachuting.add(player);
	}
}