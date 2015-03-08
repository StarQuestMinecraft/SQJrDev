package nickmiste.parachutes.tasks;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;


public class DefaultParachuteTask extends ParachuteTask
{
	protected Parachute parachute;
	
	protected ArmorStand armorStand1;
	protected ArmorStand armorStand2;
	protected ArmorStand armorStand3;
	
	private double lastX;
	private double lastY;
	private double lastZ;
	
	public DefaultParachuteTask(Parachute parachute, ArmorStand armorStand1, ArmorStand armorStand2, ArmorStand armorStand3)
	{
		this.parachute = parachute;
		this.armorStand1 = armorStand1;
		this.armorStand2 = armorStand2;
		this.armorStand3 = armorStand3;
		
		this.lastX = parachute.player.getLocation().getX();
		this.lastY = parachute.player.getLocation().getY();
		this.lastZ = parachute.player.getLocation().getZ();
	}
	
	@Override
	public void run()
	{
		Vector direction = parachute.player.getLocation().getDirection();
		parachute.player.setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
		
		armorStand1.teleport(new Location(this.armorStand1.getWorld(),
										  this.armorStand1.getLocation().getX() - (lastX - parachute.player.getLocation().getX()),
										  this.armorStand1.getLocation().getY() - (lastY - parachute.player.getLocation().getY()),
										  this.armorStand1.getLocation().getZ() - (lastZ - parachute.player.getLocation().getZ())));
		
		armorStand2.teleport(new Location(this.armorStand2.getWorld(),
										  this.armorStand2.getLocation().getX() - (lastX - parachute.player.getLocation().getX()),
										  this.armorStand2.getLocation().getY() - (lastY - parachute.player.getLocation().getY()),
										  this.armorStand2.getLocation().getZ() - (lastZ - parachute.player.getLocation().getZ())));

		armorStand3.teleport(new Location(this.armorStand3.getWorld(),
										  this.armorStand3.getLocation().getX() - (lastX - parachute.player.getLocation().getX()),
										  this.armorStand3.getLocation().getY() - (lastY - parachute.player.getLocation().getY()),
										  this.armorStand3.getLocation().getZ() - (lastZ - parachute.player.getLocation().getZ())));
		
		this.lastX = parachute.player.getLocation().getX();
		this.lastY = parachute.player.getLocation().getY();
		this.lastZ = parachute.player.getLocation().getZ();
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline() || !Parachute.parachuting.contains(parachute.player))
		{
			Parachute.parachuting.remove(parachute.player);
			Parachute.glidingPlayers.remove(parachute.player);
			Parachute.parachutingArmorStands.remove(armorStand1);
			Parachute.parachutingArmorStands.remove(armorStand2);
			Parachute.parachutingArmorStands.remove(armorStand3);
			armorStand1.remove();
			armorStand2.remove();
			armorStand3.remove();
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}