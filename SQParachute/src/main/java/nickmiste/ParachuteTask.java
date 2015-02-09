package nickmiste;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;


public class ParachuteTask implements Runnable
{
	private int id;
	private Parachute parachute;
	
	private ArmorStand armorStand1;
	private ArmorStand armorStand2;
	private ArmorStand armorStand3;
	
	private double lastX;
	private double lastY;
	private double lastZ;
	
	public ParachuteTask(Parachute parachute, ArmorStand armorStand1, ArmorStand armorStand2, ArmorStand armorStand3)
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
		parachute.player.setVelocity(new Vector(parachute.player.getVelocity().getX(), -0.3, parachute.player.getVelocity().getZ()));
		
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
		
		if (this.parachute.player.isOnGround())
		{
			Parachute.parachuting.remove(parachute.player);
			armorStand1.remove();
			armorStand2.remove();
			armorStand3.remove();
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
}