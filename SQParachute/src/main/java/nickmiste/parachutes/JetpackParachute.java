package nickmiste.parachutes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.JetpackParachuteTask;

public class JetpackParachute extends Parachute
{
	public JetpackParachute(Player player, boolean gliding)
	{
		super(player, gliding);
		
		Slime[] slimes = new Slime[3];
		for (int i = 0; i < slimes.length; i++)
		{
			slimes[i] = (Slime) player.getWorld().spawnEntity(loc, EntityType.SLIME);
			slimes[i].setSize(-1);
			slimes[i].addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
			slimes[i].addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		}
		
		
		ArmorStand body1 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand body2 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand body3 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand body4 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand connector = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand torch1 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand torch2 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand hopper1 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand hopper2 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand hopper3 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand hopper4 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand strap1 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		ArmorStand strap2 = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		
		ArmorStand[] bodies = {body1, body2, body3, body4};
		ArmorStand[] connectors = {connector};
		ArmorStand[] torches = {torch1, torch2};
		ArmorStand[] hoppers = {hopper1, hopper2, hopper3, hopper4};
		ArmorStand[] straps = {strap1, strap2};
		
		ArmorStand[][] armorStands = {bodies, connectors, torches, hoppers, straps};
		
		for (ArmorStand[] armorStandArray : armorStands)
		{
			for (ArmorStand armorStand : armorStandArray)
			{
				armorStand.setGravity(false);
				armorStand.setVisible(false);
				armorStand.setSmall(true);
				Parachute.parachutingArmorStands.add(armorStand);
			}
		}
		
		for (ArmorStand armorStand : bodies)
			armorStand.getEquipment().setHelmet(new ItemStack(Material.STAINED_CLAY, 1, (short) 9));
		connector.getEquipment().setHelmet(new ItemStack(Material.REDSTONE_BLOCK));
		torch1.getEquipment().setHelmet(new ItemStack(Material.REDSTONE_TORCH_ON));
		torch2.getEquipment().setHelmet(new ItemStack(Material.REDSTONE_TORCH_OFF));
		for (ArmorStand armorStand : hoppers)
		{
			armorStand.getEquipment().setHelmet(new ItemStack(Material.HOPPER));
			armorStand.setSmall(false);
		}
		for(ArmorStand armorStand : straps)
		{
			armorStand.getEquipment().setHelmet(new ItemStack(Material.ANVIL));
			armorStand.setHeadPose(new EulerAngle(90, 0, 0));
		}
		
		body1.setPassenger(slimes[1]);
		slimes[1].setPassenger(slimes[2]);
		slimes[2].setPassenger(body2);
		
		body3.setPassenger(slimes[3]);
		slimes[3].setPassenger(slimes[4]);
		slimes[4].setPassenger(body4);
		
		JetpackParachuteTask task = new JetpackParachuteTask(this, armorStands, slimes);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}