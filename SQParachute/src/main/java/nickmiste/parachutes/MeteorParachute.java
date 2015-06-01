package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.MeteorParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MeteorParachute extends Parachute
{
	public ArmorStand meteor;
	
	public MeteorParachute(Player player, boolean gliding)
	{
		super(player, gliding);
		
		this.meteor = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		meteor.getEquipment().setHelmet(new ItemStack(Material.NETHERRACK, 0));
		meteor.setVisible(false);
		
		Parachute.parachutingArmorStands.add(meteor);
		
		MeteorParachuteTask task = new MeteorParachuteTask(this, meteor);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}

}
