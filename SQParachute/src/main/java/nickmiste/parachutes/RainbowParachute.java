package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.RainbowParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class RainbowParachute extends Parachute
{
	public RainbowParachute(Player player) 
	{
		super(player);

		ItemStack stack = new ItemStack(Material.BANNER, 1, (short) 1);
		
		ArmorStand armorStand1 = player.getWorld().spawn(loc.clone().add(0, 1, 2.3), ArmorStand.class);
		ArmorStand armorStand2 = player.getWorld().spawn(loc.clone().add(0, 2, 1), ArmorStand.class);
		ArmorStand armorStand3 = player.getWorld().spawn(loc.clone().add(0, 1, -3), ArmorStand.class);
		
		armorStand1.setHeadPose(new EulerAngle(5 * Math.PI / 3, 0, 0));
		armorStand1.getEquipment().setHelmet(stack);
		armorStand1.setGravity(false);
		armorStand1.setVisible(false);
		
		armorStand2.setHeadPose(new EulerAngle(3 * Math.PI / 2, 0, 0));
		armorStand2.getEquipment().setHelmet(stack);
		armorStand2.setGravity(false);
		armorStand2.setVisible(false);
		
		armorStand3.setHeadPose(new EulerAngle(5 * Math.PI / 3, Math.PI, 0));
		armorStand3.getEquipment().setHelmet(stack);
		armorStand3.setGravity(false);
		armorStand3.setVisible(false);
		
		RainbowParachuteTask task = new RainbowParachuteTask(this, armorStand1, armorStand2, armorStand3);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}
