package nickmiste;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public abstract class Parachute 
{
	public Player player;
	
	protected Location loc;
	
	public static ArrayList<Player> parachuting = new ArrayList<Player>();
	public static ArrayList<ArmorStand> parachutingArmorStands = new ArrayList<ArmorStand>();
	
	public Parachute(Player player)
	{
		this.player = player;
		this.loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
		
		parachuting.add(player);
	}
}