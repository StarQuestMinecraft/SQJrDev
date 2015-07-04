package nickmiste;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class Parachest
{
	public ArmorStand chest;
	public ArrayList<ItemStack> rewards;
	
	public Parachest(Player player, ArrayList<ItemStack> rewards)
	{
		short color = (short) (Math.random() * 15);
		
		Location loc = player.getLocation().clone().add(0, 100, 0);
		loc.setYaw(0);
		
		ArmorStand chest = player.getWorld().spawn(loc, ArmorStand.class);
		chest.getEquipment().setHelmet(new ItemStack(Material.CHEST, 0, (short) 0));
		chest.setVisible(false);
		
		ArmorStand armorStand1 = player.getWorld().spawn(chest.getLocation().clone().add(0, 1, 2.3), ArmorStand.class);
		ArmorStand armorStand2 = player.getWorld().spawn(chest.getLocation().clone().add(0, 2, 1), ArmorStand.class);
		ArmorStand armorStand3 = player.getWorld().spawn(chest.getLocation().clone().add(0, 1, -3), ArmorStand.class);
		
		armorStand1.setHeadPose(new EulerAngle(5 * Math.PI / 3, 0, 0));
		armorStand1.getEquipment().setHelmet(new ItemStack(Material.BANNER, 0, color));
		armorStand1.setGravity(false);
		armorStand1.setVisible(false);
		
		armorStand2.setHeadPose(new EulerAngle(3 * Math.PI / 2, 0, 0));
		armorStand2.getEquipment().setHelmet(new ItemStack(Material.BANNER, 0, color));
		armorStand2.setGravity(false);
		armorStand2.setVisible(false);
		
		armorStand3.setHeadPose(new EulerAngle(5 * Math.PI / 3, Math.PI, 0));
		armorStand3.getEquipment().setHelmet(new ItemStack(Material.BANNER, 0, color));
		armorStand3.setGravity(false);
		armorStand3.setVisible(false);
		
		SQVote.parachutingArmorStands.add(chest);
		SQVote.parachutingArmorStands.add(armorStand1);
		SQVote.parachutingArmorStands.add(armorStand2);
		SQVote.parachutingArmorStands.add(armorStand3);
		
		this.chest = chest;
		this.rewards = rewards;
		
		ParachestTask task = new ParachestTask(this, armorStand1, armorStand2, armorStand3);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQVote.getInstance(), task, 1, 1);
		task.setId(id);
	}
}