package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.SteampunkParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SteampunkParachute extends Parachute
{
	private ArmorStand armorStand1;
	private ArmorStand armorStand2;
	
	public SteampunkParachute(Player player)
	{
		super(player);
		
		ItemStack stack = new ItemStack(Material.BANNER, 1);
		BannerMeta meta = (BannerMeta) stack.getItemMeta();
		meta.setBaseColor(DyeColor.BROWN);
		meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.BORDER));
		meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CROSS));
		meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.GRADIENT_UP));
		meta.addPattern(new Pattern(DyeColor.ORANGE, PatternType.FLOWER));
		meta.addPattern(new Pattern(DyeColor.BROWN, PatternType.GRADIENT));
		stack.setItemMeta(meta);
		
		this.armorStand1 = player.getWorld().spawn(loc, ArmorStand.class);
		this.armorStand2 = player.getWorld().spawn(loc, ArmorStand.class);
		
		this.armorStand1.getEquipment().setHelmet(stack);
		this.armorStand1.setGravity(false);
		this.armorStand1.setVisible(false);
		
		this.armorStand2.getEquipment().setHelmet(stack);
		this.armorStand2.setGravity(false);
		this.armorStand2.setVisible(false);
		
		Slime slime1 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime2 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime3 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime4 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime5 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime6 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime7 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime8 = player.getWorld().spawn(player.getLocation(), Slime.class);
		
		slime1.setSize(-1);
		slime2.setSize(-1);
		slime3.setSize(-1);
		slime4.setSize(-1);
		slime5.setSize(-1);
		slime6.setSize(-1);
		slime7.setSize(-1);
		slime8.setSize(-1);
		
		slime1.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime2.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime3.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime4.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime5.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime6.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime7.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime8.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		
		player.setPassenger(slime1);
		slime1.setPassenger(slime2);
		slime2.setPassenger(slime3);
		slime3.setPassenger(slime4);
		slime4.setPassenger(armorStand1);
		armorStand1.setPassenger(slime5);
		slime5.setPassenger(slime6);
		slime6.setPassenger(slime7);
		slime7.setPassenger(slime8);
		slime8.setPassenger(armorStand2);
		
		SteampunkParachuteTask task = new SteampunkParachuteTask(this, this.armorStand1, this.armorStand2, new Slime[]
				{slime1, slime2, slime3, slime4, slime5, slime6, slime7, slime8});
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}