package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.IronicParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IronicParachute extends Parachute
{
	public ArmorStand anvil;
	
	public IronicParachute(Player player, boolean gliding) 
	{
		super(player, gliding);
		
		anvil = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		anvil.getEquipment().setHelmet(new ItemStack(Material.ANVIL, 0));
		anvil.setVisible(false);
		
		anvil.setPassenger(player);
		
		player.getWorld().playSound(player.getLocation(), Sound.ANVIL_USE, 20, 1);
		
		IronicParachuteTask task = new IronicParachuteTask(this, anvil);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}
