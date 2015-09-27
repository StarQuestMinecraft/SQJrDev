package nickmiste;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

import com.comphenix.packetwrapper.WrapperPlayServerNamedEntitySpawn;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

public class Spectator 
{
	private static int id = 1000;
	
	public static void spawnSpectator(double x, double y, double z)
	{
		WrapperPlayServerNamedEntitySpawn spectator = new WrapperPlayServerNamedEntitySpawn();
		spectator.setEntityID(id++);
		spectator.setPosition(new Vector(x, y, z));
		spectator.set
		
		WrappedDataWatcher watcher = new WrappedDataWatcher();
		spectator.setMetadata(watcher);
		
		try
		{
			ProtocolLibrary.getProtocolManager().sendServerPacket(Bukkit.getPlayer("Nickmiste"), spectator.getHandle());
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
}