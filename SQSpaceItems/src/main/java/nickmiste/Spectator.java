package nickmiste;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

public class Spectator 
{
	private static int id = 1000;
	
	public static void spawnSpectator(Item item)
	{
		PacketContainer container = new PacketContainer(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
		container.getModifier().writeDefaults();
		container.getIntegers().write(0, id++);
		container.getIntegers().write(1, item.getLocation().getBlockX() * 32);
		container.getIntegers().write(2, item.getLocation().getBlockY() * 32);
		container.getIntegers().write(3, item.getLocation().getBlockZ() * 32);
		container.getGameProfiles().write(0, WrappedGameProfile.fromPlayer(Bukkit.getPlayer("Nickmiste")));
		
		try
		{
			ProtocolLibrary.getProtocolManager().sendServerPacket(Bukkit.getPlayer("Nickmiste"), container);
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
}