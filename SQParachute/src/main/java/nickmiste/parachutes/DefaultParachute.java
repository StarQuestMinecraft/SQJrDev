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
	public DefaultParachute(Player player)
	{
		super(player);
		
		short color = (short) (Math.random() * 15);
		
		ArmorStand armorStand1 = player.getWorld().spawn(loc.clone().add(0, 1, 2.3), ArmorStand.class);
		ArmorStand armorStand2 = player.getWorld().spawn(loc.clone().add(0, 2, 1), ArmorStand.class);
		ArmorStand armorStand3 = player.getWorld().spawn(loc.clone().add(0, 1, -3), ArmorStand.class);
		
		armorStand1.setHeadPose(new EulerAngle(5 * Math.PI / 3, 0, 0));
		armorStand1.getEquipment().setHelmet(new ItemStack(Material.BANNER, 1, color));
		armorStand1.setGravity(false);
		armorStand1.setVisible(false);
		
		armorStand2.setHeadPose(new EulerAngle(3 * Math.PI / 2, 0, 0));
		armorStand2.getEquipment().setHelmet(new ItemStack(Material.BANNER, 1, color));
		armorStand2.setGravity(false);
		armorStand2.setVisible(false);
		
		armorStand3.setHeadPose(new EulerAngle(5 * Math.PI / 3, Math.PI, 0));
		armorStand3.getEquipment().setHelmet(new ItemStack(Material.BANNER, 1, color));
		armorStand3.setGravity(false);
		armorStand3.setVisible(false);
		
		Parachute.parachutingArmorStands.add(armorStand1);
		Parachute.parachutingArmorStands.add(armorStand2);
		Parachute.parachutingArmorStands.add(armorStand3);
		
		DefaultParachuteTask task = new DefaultParachuteTask(this, armorStand1, armorStand2, armorStand3);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}