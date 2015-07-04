package nickmiste;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;


public class ParachestTask implements Runnable
{
	protected int id;
	
	protected Parachest parachute;
	
	protected ArmorStand armorStand1;
	protected ArmorStand armorStand2;
	protected ArmorStand armorStand3;
	
	private double lastX;
	private double lastY;
	private double lastZ;
	
	public ParachestTask(Parachest parachute, ArmorStand armorStand1, ArmorStand armorStand2, ArmorStand armorStand3)
	{
		this.parachute = parachute;
		this.armorStand1 = armorStand1;
		this.armorStand2 = armorStand2;
		this.armorStand3 = armorStand3;
		
		this.lastX = parachute.chest.getLocation().getX();
		this.lastY = parachute.chest.getLocation().getY();
		this.lastZ = parachute.chest.getLocation().getZ();
	}
	
	@Override
	public void run()
	{
		parachute.chest.setVelocity(new Vector(0, -0.3, 0));
		
		armorStand1.teleport(new Location(this.armorStand1.getWorld(),
										  this.armorStand1.getLocation().getX() - (lastX - parachute.chest.getLocation().getX()),
										  this.armorStand1.getLocation().getY() - (lastY - parachute.chest.getLocation().getY()),
										  this.armorStand1.getLocation().getZ() - (lastZ - parachute.chest.getLocation().getZ())));
		
		armorStand2.teleport(new Location(this.armorStand2.getWorld(),
										  this.armorStand2.getLocation().getX() - (lastX - parachute.chest.getLocation().getX()),
										  this.armorStand2.getLocation().getY() - (lastY - parachute.chest.getLocation().getY()),
										  this.armorStand2.getLocation().getZ() - (lastZ - parachute.chest.getLocation().getZ())));

		armorStand3.teleport(new Location(this.armorStand3.getWorld(),
										  this.armorStand3.getLocation().getX() - (lastX - parachute.chest.getLocation().getX()),
										  this.armorStand3.getLocation().getY() - (lastY - parachute.chest.getLocation().getY()),
										  this.armorStand3.getLocation().getZ() - (lastZ - parachute.chest.getLocation().getZ())));
		
		this.lastX = parachute.chest.getLocation().getX();
		this.lastY = parachute.chest.getLocation().getY();
		this.lastZ = parachute.chest.getLocation().getZ();
		
		if (this.parachute.chest.isOnGround()) //|| this.parachute.chest.getWorld().getBlockAt(this.parachute.chest.getLocation()).isLiquid());
		{
			parachute.chest.getLocation().getBlock().setType(Material.CHEST);
			Chest chest = (Chest) parachute.chest.getLocation().getBlock().getState();
			
			for (ItemStack reward : parachute.rewards)
				chest.getInventory().addItem(reward);
			
			SQVote.parachutingArmorStands.remove(armorStand1);
			SQVote.parachutingArmorStands.remove(armorStand2);
			SQVote.parachutingArmorStands.remove(armorStand3);
			SQVote.parachutingArmorStands.remove(parachute.chest);
			armorStand1.remove();
			armorStand2.remove();
			armorStand3.remove();
			parachute.chest.remove();
			
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
}