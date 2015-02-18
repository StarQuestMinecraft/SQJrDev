package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.ParachuteTask;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.DefaultParachuteTask;
import nickmiste.parachutes.tasks.RainbowParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class DefaultParachute extends Parachute
{
	private ArmorStand armorStand1;
	private ArmorStand armorStand2;
	private ArmorStand armorStand3;
	
	public DefaultParachute(Player player)
	{
		super(player);
		
		short color = (short) (Math.random() * 15);
		
		this.armorStand1 = player.getWorld().spawn(loc.clone().add(0, 1, 2.3), ArmorStand.class);
		this.armorStand2 = player.getWorld().spawn(loc.clone().add(0, 2, 1), ArmorStand.class);
		this.armorStand3 = player.getWorld().spawn(loc.clone().add(0, 1, -3), ArmorStand.class);
		
		this.armorStand1.setHeadPose(new EulerAngle(5 * Math.PI / 3, 0, 0));
		this.armorStand1.getEquipment().setHelmet(new ItemStack(Material.BANNER, 1, color));
		this.armorStand1.setGravity(false);
		this.armorStand1.setVisible(false);
		
		this.armorStand2.setHeadPose(new EulerAngle(3 * Math.PI / 2, 0, 0));
		this.armorStand2.getEquipment().setHelmet(new ItemStack(Material.BANNER, 1, color));
		this.armorStand2.setGravity(false);
		this.armorStand2.setVisible(false);
		
		this.armorStand3.setHeadPose(new EulerAngle(5 * Math.PI / 3, Math.PI, 0));
		this.armorStand3.getEquipment().setHelmet(new ItemStack(Material.BANNER, 1, color));
		this.armorStand3.setGravity(false);
		this.armorStand3.setVisible(false);
		
		DefaultParachuteTask task = new DefaultParachuteTask(this, this.armorStand1, this.armorStand2, this.armorStand3);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}