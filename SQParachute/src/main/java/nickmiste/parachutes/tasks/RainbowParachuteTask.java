package nickmiste.parachutes.tasks;

import java.util.LinkedList;
import java.util.Queue;

import nickmiste.Parachute;

import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

public class RainbowParachuteTask extends DefaultParachuteTask
{
	private Queue<Short> colors = new LinkedList<Short>();
	
	private int iteration;
	
	public RainbowParachuteTask(Parachute parachute, ArmorStand armorStand1, ArmorStand armorStand2, ArmorStand armorStand3) 
	{
		super(parachute, armorStand1, armorStand2, armorStand3);
		
		colors.add((short) 1);
		colors.add((short) 14);
		colors.add((short) 11);
		colors.add((short) 10);
		colors.add((short) 2);
		colors.add((short) 6);
		colors.add((short) 4);
		colors.add((short) 12);
		colors.add((short) 13);
		colors.add((short) 5);
		
		this.iteration = 0;
	}
	
	@Override
	public void run()
	{
		super.run();
		if (iteration % 2 == 0)
		{
			ItemStack helmet1 = this.armorStand1.getHelmet();
			ItemStack helmet2 = this.armorStand2.getHelmet();
			ItemStack helmet3 = this.armorStand3.getHelmet();
			
			short color = colors.poll();
			colors.add(color);
			
			helmet1.setDurability(color);
			helmet2.setDurability(color);
			helmet3.setDurability(color);
			
			this.armorStand1.setHelmet(helmet1);
			this.armorStand2.setHelmet(helmet2);
			this.armorStand3.setHelmet(helmet3);
		}
		iteration++;
	}
}
