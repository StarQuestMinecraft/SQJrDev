package nickmiste;

import java.util.ArrayList;

import nickmiste.parachutes.DefaultParachute;
import nickmiste.parachutes.IronicParachute;
import nickmiste.parachutes.MeteorParachute;
import nickmiste.parachutes.RainbowParachute;
import nickmiste.parachutes.SkydogParachute;
import nickmiste.parachutes.SlimeParachute;
import nickmiste.parachutes.SteampunkParachute;

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
	
	public static Parachute startParachuting(Player player)
	{
		if (ParachuteSelector.parachutes.containsKey(player))
			if (ParachuteSelector.parachutes.get(player).equals(ParachuteSelector.RAINBOW_PARACHUTE))
				return new RainbowParachute(player);
			else if (ParachuteSelector.parachutes.get(player).equals(ParachuteSelector.STEAMPUNK_PARACHUTE))
				return new SteampunkParachute(player);
			else if (ParachuteSelector.parachutes.get(player).equals(ParachuteSelector.IRONIC_PARACHUTE))
				return new IronicParachute(player);
			else if (ParachuteSelector.parachutes.get(player).equals(ParachuteSelector.SKYDOG_PARACHUTE))
				return new SkydogParachute(player);
			else if (ParachuteSelector.parachutes.get(player).equals(ParachuteSelector.METEOR_PARACHUTE))
				return new MeteorParachute(player);
			else if (ParachuteSelector.parachutes.get(player).equals(ParachuteSelector.SLIME_PARACHUTE))
				return new SlimeParachute(player);
		return new DefaultParachute(player);
	}
	
	public static void startGliding(Player player)
	{
		new HangGlider(startParachuting(player));
	}
}