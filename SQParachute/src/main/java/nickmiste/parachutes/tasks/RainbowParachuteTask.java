package nickmiste.parachutes.tasks;

import nickmiste.Parachute;

import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

public class RainbowParachuteTask extends DefaultParachuteTask
{
	public RainbowParachuteTask(Parachute parachute, ArmorStand armorStand1, ArmorStand armorStand2, ArmorStand armorStand3) 
	{
		super(parachute, armorStand1, armorStand2, armorStand3);
	}
	
	@Override
	public void run()
	{
		super.run();
		if (this.parachute.player.getWorld().getTime() % 10 == 0)
		{
			ItemStack helmet1 = this.armorStand1.getHelmet();
			ItemStack helmet2 = this.armorStand2.getHelmet();
			ItemStack helmet3 = this.armorStand3.getHelmet();
			
			helmet1.setDurability((short) ((this.armorStand1.getHelmet().getDurability() + 1) % 16));
			helmet2.setDurability((short) ((this.armorStand2.getHelmet().getDurability() + 1) % 16));
			helmet3.setDurability((short) ((this.armorStand3.getHelmet().getDurability() + 1) % 16));
			
			this.armorStand1.setHelmet(helmet1);
			this.armorStand2.setHelmet(helmet2);
			this.armorStand3.setHelmet(helmet3);
		}
	}
}
