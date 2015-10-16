package nickmiste;

import java.util.ArrayList;

import nickmiste.parachutes.DefaultParachute;
import nickmiste.parachutes.IronicParachute;
import nickmiste.parachutes.JetpackParachute;
import nickmiste.parachutes.MeteorParachute;
import nickmiste.parachutes.RainbowParachute;
import nickmiste.parachutes.SkydogParachute;
import nickmiste.parachutes.SlimeParachute;
import nickmiste.parachutes.SteampunkParachute;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

public abstract class Parachute 
{
	public Player player;
	public boolean gliding;
	
	protected Location loc;
	
	public static ArrayList<Player> parachuting = new ArrayList<Player>();
	public static ArrayList<Player> glidingPlayers = new ArrayList<Player>();
	public static ArrayList<ArmorStand> parachutingArmorStands = new ArrayList<ArmorStand>();
	public static ArrayList<Slime> parachutingLargeSlimes = new ArrayList<Slime>();
	
	public Parachute(Player player, boolean gliding)
	{
		this.player = player;
		this.loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
		this.gliding = gliding;
		
		parachuting.add(player);
		
		if (gliding)
			glidingPlayers.add(player);
	}
	
	public static Parachute startParachuting(Player player, boolean gliding)
	{
		if (ParachuteSelector.parachutes.containsKey(player.getUniqueId()))
			if (ParachuteSelector.parachutes.get(player.getUniqueId()).equals(ParachuteSelector.RAINBOW_PARACHUTE))
				return new RainbowParachute(player, gliding);
			else if (ParachuteSelector.parachutes.get(player.getUniqueId()).equals(ParachuteSelector.STEAMPUNK_PARACHUTE))
				return new SteampunkParachute(player, gliding);
			else if (ParachuteSelector.parachutes.get(player.getUniqueId()).equals(ParachuteSelector.IRONIC_PARACHUTE))
				return new IronicParachute(player, gliding);
			else if (ParachuteSelector.parachutes.get(player.getUniqueId()).equals(ParachuteSelector.SKYDOG_PARACHUTE))
				return new SkydogParachute(player, gliding);
			else if (ParachuteSelector.parachutes.get(player.getUniqueId()).equals(ParachuteSelector.METEOR_PARACHUTE))
				return new MeteorParachute(player, gliding);
			else if (ParachuteSelector.parachutes.get(player.getUniqueId()).equals(ParachuteSelector.SLIME_PARACHUTE))
				return new SlimeParachute(player, gliding);
			else if (ParachuteSelector.parachutes.get(player.getUniqueId()).equals(ParachuteSelector.JETPACK_PARACHUTE))
				return new JetpackParachute(player, gliding);
//			else if (ParachuteSelector.parachutes.get(player.getUniqueId()).equals(ParachuteSelector.WINGS))
//				return new WingParachute(player, gliding);
		return new DefaultParachute(player, gliding);
	}
	
	public static int getDistanceFromGround(Player player)
	{
		int counter = 0;
		
		for (int y = player.getLocation().getBlockY(); y > 0; y--)
		{
			counter++;
			if (!player.getWorld().getBlockAt(player.getLocation().getBlockX(), y, player.getLocation().getBlockZ()).getType().equals(Material.AIR))
				break;
		}
		return counter;
	}
}