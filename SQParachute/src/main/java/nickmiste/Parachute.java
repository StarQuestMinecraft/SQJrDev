package nickmiste;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

public abstract class Parachute 
{
	public Player player;
	
	protected Location loc;
	
	public static ArrayList<Player> parachuting = new ArrayList<Player>();
	public static ArrayList<Slime> parachutingSlimes = new ArrayList<Slime>();
	
	public Parachute(Player player)
	{
		this.player = player;
		this.loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
		
		parachuting.add(player);
	}
}