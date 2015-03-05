package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.SlimeParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SlimeParachute extends Parachute
{
	public Slime slime;
	
	public SlimeParachute(Player player) 
	{
		super(player);
		
		slime = player.getWorld().spawn(player.getLocation(), Slime.class);
		slime.setSize(5);
		slime.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4, false, false));
		slime.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 4, false, false));
		slime.setPassenger(player);
		
		SlimeParachuteTask task = new SlimeParachuteTask(this, slime);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}	
}
