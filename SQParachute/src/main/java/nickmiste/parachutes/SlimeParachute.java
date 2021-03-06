package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.SlimeParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SlimeParachute extends Parachute
{
	public Slime slime;
	
	public SlimeParachute(Player player, boolean gliding) 
	{
		super(player, gliding);
		
		slime = (Slime) player.getWorld().spawnEntity(player.getLocation(), EntityType.SLIME);
		slime.setSize(5);
		slime.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		slime.setPassenger(player);
		Parachute.parachutingLargeSlimes.add(slime);
		
		SlimeParachuteTask task = new SlimeParachuteTask(this, slime);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}	
}
