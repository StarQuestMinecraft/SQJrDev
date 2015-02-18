package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.IronicParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class IronicParachute extends Parachute
{
	public IronicParachute(Player player) 
	{
		super(player);
		
		ArmorStand anvil = player.getWorld().spawn(loc, ArmorStand.class);
		anvil.getEquipment().setHelmet(new ItemStack(Material.ANVIL, 0));
		anvil.setVisible(false);
		
		Slime slime1 = player.getWorld().spawn(player.getLocation(), Slime.class);
		Slime slime2 = player.getWorld().spawn(player.getLocation(), Slime.class);
	
		slime1.setSize(-1);
		slime2.setSize(-1);
		
		slime1.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		slime2.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		
		player.setPassenger(slime1);
		slime1.setPassenger(slime2);
		slime2.setPassenger(anvil);
		
		IronicParachuteTask task = new IronicParachuteTask(this, anvil, slime1, slime2);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}
