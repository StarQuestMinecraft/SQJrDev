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
	public ArmorStand anvil;
	
	public IronicParachute(Player player) 
	{
		super(player);
		
		anvil = player.getWorld().spawn(loc, ArmorStand.class);
		anvil.getEquipment().setHelmet(new ItemStack(Material.ANVIL, 0));
		anvil.setVisible(false);
		
		anvil.setPassenger(player);
		
		IronicParachuteTask task = new IronicParachuteTask(this, anvil);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}
