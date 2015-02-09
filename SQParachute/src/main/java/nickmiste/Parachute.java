package nickmiste;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class Parachute 
{
	public Player player;
	private short color;
	
	public static ArrayList<Player> parachuting = new ArrayList<Player>();
	
	public Parachute(Player player, short color)
	{
		this.player = player;
		this.color = color;
		
		parachuting.add(player);
		
		Location loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
		
		ArmorStand armorStand1 = player.getWorld().spawn(loc.clone().add(0, 1, 2.3), ArmorStand.class);
		ArmorStand armorStand2 = player.getWorld().spawn(loc.clone().add(0, 2, 1), ArmorStand.class);
		ArmorStand armorStand3 = player.getWorld().spawn(loc.clone().add(0, 1, -3), ArmorStand.class);
		
		armorStand1.setHeadPose(new EulerAngle(5 * Math.PI / 3, 0, 0));
		armorStand1.getEquipment().setHelmet(new ItemStack(Material.BANNER, 1, this.color));
		armorStand1.setGravity(false);
		armorStand1.setVisible(false);
		
		armorStand2.setHeadPose(new EulerAngle(3 * Math.PI / 2, 0, 0));
		armorStand2.getEquipment().setHelmet(new ItemStack(Material.BANNER, 1, this.color));
		armorStand2.setGravity(false);
		armorStand2.setVisible(false);
		
		armorStand3.setHeadPose(new EulerAngle(5 * Math.PI / 3, Math.PI, 0));
		armorStand3.getEquipment().setHelmet(new ItemStack(Material.BANNER, 1, this.color));
		armorStand3.setGravity(false);
		armorStand3.setVisible(false);

		ParachuteTask task = new ParachuteTask(this, armorStand1, armorStand2, armorStand3);
		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
		task.setId(id);
	}
}