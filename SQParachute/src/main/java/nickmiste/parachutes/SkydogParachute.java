package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.SkydogParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;

public class SkydogParachute extends Parachute
{
	public Wolf skydog;
	
	public SkydogParachute(Player player)
	{
		super(player);
		
		ArmorStand armorStand1 = player.getWorld().spawn(loc, ArmorStand.class);
		ArmorStand armorStand2 = player.getWorld().spawn(loc, ArmorStand.class);
		
		ItemStack stack1 = new ItemStack(Material.BANNER, 1, (short) 15);
		BannerMeta meta1 = (BannerMeta) stack1.getItemMeta();
		meta1.addPattern(new Pattern(DyeColor.ORANGE, PatternType.STRIPE_DOWNRIGHT));
		stack1.setItemMeta(meta1);
		
		ItemStack stack2 = new ItemStack(Material.BANNER, 1, (short) 15);
		BannerMeta meta2 = (BannerMeta) stack2.getItemMeta();
		meta2.addPattern(new Pattern(DyeColor.ORANGE, PatternType.STRIPE_DOWNLEFT));
		stack2.setItemMeta(meta2);
		
		armorStand1.setHeadPose(new EulerAngle(0, Math.PI / 2, Math.PI / 2));
		armorStand1.getEquipment().setHelmet(stack1);
		armorStand1.setGravity(false);
		armorStand1.setVisible(false);
		
		armorStand2.setHeadPose(new EulerAngle(0, 3 * Math.PI / 2, 3 * Math.PI / 2));
		armorStand2.getEquipment().setHelmet(stack2);
		armorStand2.setGravity(false);
		armorStand2.setVisible(false);
		
		Parachute.parachutingArmorStands.add(armorStand1);
		Parachute.parachutingArmorStands.add(armorStand2);
		
		skydog = player.getWorld().spawn(loc, Wolf.class);
		skydog.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		skydog.setBreed(false);
		
		Slime slime1 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime2 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime3 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime4 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime5 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime6 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime7 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime8 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime9 = player.getWorld().spawn(player.getLocation(), Slime.class);
		
		
		slime1.setSize(-1);
		slime2.setSize(-1);
		slime3.setSize(-1);
		slime4.setSize(-1);
		slime5.setSize(-1);
		slime6.setSize(-1);
		slime7.setSize(-1);
		slime8.setSize(-1);
		slime9.setSize(-1);
		
		slime1.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime2.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime3.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime4.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime5.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime6.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime7.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime8.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime9.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		
		slime1.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		slime2.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		slime3.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		slime4.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		slime5.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		slime6.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		slime7.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		slime8.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		
		skydog.setPassenger(player);
		player.setPassenger(slime1);
		slime1.setPassenger(slime2);
		slime2.setPassenger(slime3);
		slime3.setPassenger(slime4);
		slime4.setPassenger(slime5);
		slime5.setPassenger(armorStand1);
		armorStand1.setPassenger(slime6);
		slime6.setPassenger(slime7);
		slime7.setPassenger(slime8);
		slime8.setPassenger(slime9);
		slime9.setPassenger(armorStand2);
		
		SkydogParachuteTask task = new SkydogParachuteTask(this, armorStand1, armorStand2, skydog, new Slime[] 
				{slime1, slime2, slime3, slime4, slime5, slime6, slime7, slime8, slime9});
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}