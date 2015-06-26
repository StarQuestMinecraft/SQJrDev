package nickmiste.orders;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import net.countercraft.movecraft.bedspawns.Bedspawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class OrderTracker 
{
	//public static ArrayList<Order> orders = new ArrayList<Order>();
	//public static ArrayList<Order> completedOrders = new ArrayList<Order>();
	
	public static ArrayList<Order> getOrders()
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		
		try
		{
			Bedspawn.getContext();
			Statement s = Bedspawn.cntx.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM minecraft.trade_orders");			
			
			while (rs.next())
			{
				Order order = new Order(UUID.fromString(rs.getString(2)),
						rs.getBoolean(3), Material.getMaterial(rs.getInt(4)), rs.getShort(5), rs.getInt(6), rs.getInt(7), 
						new Location(null, rs.getInt(8), rs.getInt(9), rs.getInt(10)), rs.getString(11));
				order.orderId = UUID.fromString(rs.getString(1));
				
				orders.add(order);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return orders;
	}
	
	public static ArrayList<Order> getCompletedOrders()
	{
		ArrayList<Order> orders = new ArrayList<Order>();
		
		try
		{
			Bedspawn.getContext();
			Statement s = Bedspawn.cntx.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM minecraft.trade_completedorders");			
			
			while (rs.next())
			{
				Order order = new Order(UUID.fromString(rs.getString(2)),
						rs.getBoolean(3), Material.getMaterial(rs.getInt(4)), rs.getShort(5), rs.getInt(6), rs.getInt(7), 
						new Location(null, rs.getInt(8), rs.getInt(9), rs.getInt(10)), rs.getString(11));
				order.orderId = UUID.fromString(rs.getString(1));
				
				orders.add(order);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(); 
		}
		
		return orders;
	}
	
	public static void addOrder(Order order)
	{
		try
		{
			Bedspawn.getContext();
			Statement s = Bedspawn.cntx.createStatement();
			s.executeUpdate("INSERT INTO minecraft.trade_orders VALUES ("
					+"'"+ order.orderId
					+"','"+ order.player
					+"',"+ order.isSellOrder 
					+","+ order.item.getId() 
					+","+ order.dmg 
					+","+ order.quantity 
					+","+ order.price 
					+","+ order.loc.getBlockX() 
					+","+ order.loc.getBlockY()
					+","+ order.loc.getBlockZ()
					+",'"+ order.world
					+"');");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void addCompletedOrder(Order order)
	{
		try
		{
			Bedspawn.getContext();
			Statement s = Bedspawn.cntx.createStatement();
			s.executeUpdate("INSERT INTO minecraft.trade_completedorders VALUES ("
					+"'"+ order.orderId
					+"','"+ order.player
					+"',"+ order.isSellOrder 
					+","+ order.item.getId() 
					+","+ order.dmg 
					+","+ order.quantity 
					+","+ order.price 
					+","+ order.loc.getBlockX() 
					+","+ order.loc.getBlockY()
					+","+ order.loc.getBlockZ()
					+",'"+ order.world
					+"');");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void removeOrder(int index)
	{
		try
		{
			Bedspawn.getContext();
			Statement s = Bedspawn.cntx.createStatement();
			s.executeUpdate("DELETE FROM minecraft.trade_orders WHERE orderId='" + getOrders().get(index).orderId.toString() + "';");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void removeCompletedOrder(int index)
	{
		try
		{
			Bedspawn.getContext();
			Statement s = Bedspawn.cntx.createStatement();
			s.executeUpdate("DELETE FROM minecraft.trade_completedorders WHERE orderId='" + getCompletedOrders().get(index).orderId.toString() + "';");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//================================================
	// End of SQL 
	//================================================
	
	public static ArrayList<Order> getOrdersFromPlayer(Player player)
	{
		ArrayList<Order> ordersFromPlayer = new ArrayList<Order>();
		for (Order order : getOrders())
			if (order.player.equals(player.getUniqueId()))
				ordersFromPlayer.add(order);
		return ordersFromPlayer;
	}
	
	/* Returns orders that meet the following conditions:
	 * 
	 * a) from specified location
	 * b) from specified player
	 * c) not available for completion
	 */
	public static ArrayList<Order> getCompletedOrdersFromLocation(Location post, UUID player)
	{
		ArrayList<Order> completedOrdersFromPost = new ArrayList<Order>();
		for (Order order : getCompletedOrders())
			if (order.loc.equals(post) && order.player.equals(player))
				completedOrdersFromPost.add(order);
		return completedOrdersFromPost;
	}
	
	/* Returns orders that meet the following conditions:
	 * 
	 * a) from specified location
	 * b) not from specified player
	 * c) available for completion
	 */
	public static ArrayList<Order> getOtherOrdersFromLocation(Location post, UUID player)
	{
		ArrayList<Order> ordersFromPost = new ArrayList<Order>();
		for (Order order : getOrders())
			if (order.loc.equals(post) && !order.player.equals(player))
				ordersFromPost.add(order);
		return ordersFromPost;
	}
	
	public static boolean canPlaceOrder(Location post, UUID player)
	{
		int numPostTrades = 0;
		int numPlayerTrades = 0;
		
		for (Order order : getOrders())
		{
			if (order.loc.equals(post))
				numPostTrades++;
			if (order.player.equals(player))
				numPlayerTrades++;
		}
		
		if (numPlayerTrades >= 54)
			Bukkit.getPlayer(player).sendMessage(ChatColor.DARK_RED + "You can only have up to 54 trades at a time!");
		if (numPostTrades >= 54)
			Bukkit.getPlayer(player).sendMessage(ChatColor.DARK_RED + "Each post can only have up to 54 trades at a time!");
		
		return numPlayerTrades < 54 && numPostTrades < 54;
	}
}