package nickmiste.parachutes;

import nickmiste.Parachute;
import nickmiste.SQParachute;
import nickmiste.parachutes.tasks.DefaultParachuteTask;
import nickmiste.parachutes.tasks.SlimeParachuteTask;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SlimeParachute extends Parachute
{
	private Slime slime;
	
	public SlimeParachute(Player player, int size)
	{
		super(player);
		
		this.slime = player.getWorld().spawn(loc, Slime.class);
		this.slime.setSize(size);
		this.slime.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 4));
		this.slime.setNoDamageTicks(Integer.MAX_VALUE);
		
		SlimeParachuteTask task = new SlimeParachuteTask(this, this.slime);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}