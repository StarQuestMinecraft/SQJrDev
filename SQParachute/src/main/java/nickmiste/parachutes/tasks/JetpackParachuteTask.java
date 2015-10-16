package nickmiste.parachutes.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;

public class JetpackParachuteTask extends ParachuteTask
{
	private Parachute parachute;
	
	private ArmorStand[][] armorStands;
	private Slime[] slimes;
	
	private int iteration;
	
	public JetpackParachuteTask(Parachute parachute, ArmorStand[][] armorStands, Slime[] slimes)
	{
		this.parachute = parachute;
		this.armorStands = armorStands;
		this.slimes = slimes;
		this.iteration = 0;
	}

	@Override
	public void run() 
	{
		Vector direction = parachute.player.getLocation().getDirection();
		parachute.player.setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
		armorStands[0][0].setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
		armorStands[0][2].setVelocity(new Vector(parachute.gliding ? direction.getX() : 0, -0.3, parachute.gliding ? direction.getZ() : 0));
		
		if (iteration % 20 == 0)
		{
			if (armorStands[2][0].getEquipment().getHelmet().getType().equals(Material.REDSTONE_TORCH_ON))
			{
				armorStands[2][0].getEquipment().setHelmet(new ItemStack(Material.REDSTONE_TORCH_OFF));
				armorStands[2][1].getEquipment().setHelmet(new ItemStack(Material.REDSTONE_TORCH_ON));
			}
			else
			{
				armorStands[2][0].getEquipment().setHelmet(new ItemStack(Material.REDSTONE_TORCH_ON));
				armorStands[2][1].getEquipment().setHelmet(new ItemStack(Material.REDSTONE_TORCH_OFF));
			}
		}
		
		if (this.parachute.player.isOnGround() || !this.parachute.player.isOnline() || this.parachute.player.getLocation().getBlock().isLiquid() || this.parachute.player.isDead())
		{
			Parachute.parachuting.remove(parachute.player);
			Parachute.glidingPlayers.remove(parachute.player);
			Parachute.glidingPlayers.remove(parachute.player);
			
			for (ArmorStand[] armorStandArray : armorStands)
			{
				for (ArmorStand armorStand : armorStandArray)
				{
					Parachute.parachutingArmorStands.remove(armorStand);
					armorStand.remove();
				}
			}
			for (Slime slime : slimes)
			{
				slime.remove();
			}
			Bukkit.getScheduler().cancelTask(this.id);
		}
	}
}