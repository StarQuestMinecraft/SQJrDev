//package nickmiste.parachutes;
//
//import nickmiste.Parachute;
//import nickmiste.SQParachute;
//import nickmiste.parachutes.tasks.WingParachuteTask;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Material;
//import org.bukkit.entity.ArmorStand;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.util.EulerAngle;
//import org.bukkit.util.Vector;
//
//public class WingParachute extends Parachute
//{
//	public ArmorStand[] wing1 = new ArmorStand[1]; //left wing
//	public ArmorStand[] wing2 = new ArmorStand[1]; //right wing
//	
//	public WingParachute(Player player, boolean gliding)
//	{
//		super(player, gliding);
//		
//		//wing number armorstand number (w#as#)
//		
//		ArmorStand w1as1 = (ArmorStand) player.getWorld().spawnEntity(loc.clone().add(-0.1875, -0.3125, -0.28125).setDirection(new Vector(90, 0, 0)), EntityType.ARMOR_STAND);
//		w1as1.setHeadPose(new EulerAngle(-0.3491, 0, 0.3491));
//		
//		ArmorStand w2as1 = (ArmorStand) player.getWorld().spawnEntity(loc.clone().add(-0.1875, -0.3125, 0.28125).setDirection(new Vector(90, 0, 0)), EntityType.ARMOR_STAND);
//		w2as1.setHeadPose(new EulerAngle(-0.3491, 0, -0.3491));
//		
//		wing1[0] = w1as1;
//		
//		wing2[0] = w2as1;
//		
//		for (ArmorStand as : wing1)
//			setupArmorStand(as);
//		for (ArmorStand as : wing2)
//			setupArmorStand(as);
//		
//		WingParachuteTask task = new WingParachuteTask(this, wing1, wing2);
//		int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(SQParachute.getInstance(), task, 1, 1);
//		task.setId(id);
//	}
//	
//	private static void setupArmorStand(ArmorStand as)
//	{
//		as.getEquipment().setHelmet(new ItemStack(Material.BANNER));
//		as.setVisible(false);
//		as.setGravity(false);
//		Parachute.parachutingArmorStands.add(as);
//	}
//}