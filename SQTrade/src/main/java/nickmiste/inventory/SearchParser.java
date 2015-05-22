package nickmiste.inventory;

import java.util.ArrayList;

import nickmiste.orders.Order;
import nickmiste.orders.OrderTracker;

import org.bukkit.Material;

public class SearchParser
{
	public static ArrayList<Order> parseSearch(String search)
	{
		ArrayList<Order> validOrders = (ArrayList<Order>) OrderTracker.getOrders().clone();
		String[] args = search.split(",");
		for (String str : args)
		{
			String[] param = str.split("=");
			if (param.length != 2)
				return null;
			
			if (param[0].equalsIgnoreCase("item"))
			{
				if (Material.getMaterial(param[1].toUpperCase()) == null)
					return new ArrayList<Order>();
				for (int i = 0; i < validOrders.size(); i++)
					if (!validOrders.get(i).item.equals(Material.getMaterial(param[1].toUpperCase())))
						validOrders.set(i, null);
			}
			else if (param[0].equalsIgnoreCase("data"))
			{
				try
				{
					short data = Short.parseShort(param[1]);
					for (int i = 0; i < validOrders.size(); i++)
						if (validOrders.get(i).dmg != data)
							validOrders.set(i, null);
				}
				catch (NumberFormatException e)
				{
					return null;
				}
			}
			else if (param[0].equalsIgnoreCase("price"))
			{
				String[] highLow = param[1].split("-");
				if (highLow.length != 2)
					return null;
				try
				{
					int low = Integer.parseInt(highLow[0]);
					int high = Integer.parseInt(highLow[1]);
					
					for (int i = 0; i < validOrders.size(); i++)
						if (!(validOrders.get(i).price >= low && validOrders.get(i).price <= high))
							validOrders.set(i, null);
				}
				catch (NumberFormatException e)
				{
					return null;
				}
			}
			else if (param[0].equalsIgnoreCase("amount"))
			{
				String[] highLow = param[1].split("-");
				if (highLow.length != 2)
					return null;
				try
				{
					int low = Integer.parseInt(highLow[0]);
					int high = Integer.parseInt(highLow[1]);
					
					for (int i = 0; i < validOrders.size(); i++)
						if (!(validOrders.get(i).quantity >= low && validOrders.get(i).quantity <= high))
							validOrders.set(i, null);
				}
				catch (NumberFormatException e)
				{
					return null;
				}
			}
			else if (param[0].equalsIgnoreCase("type"))
			{
				for (int i = 0; i < validOrders.size(); i++)
				{
					if (param[1].equalsIgnoreCase("sell"))
					{
						if (!validOrders.get(i).isSellOrder)
						{
							validOrders.set(i, null);
						}
					}
					else if (param[1].equalsIgnoreCase("buy"))
					{
						if (validOrders.get(i).isSellOrder)
						{
							validOrders.set(i, null);
						}
					}
					else
						return null;
				}
			}
			else if (param[0].equalsIgnoreCase("planet"))
			{
				for (int i = 0; i < validOrders.size(); i++)
				{
					if (!param[1].equalsIgnoreCase(validOrders.get(i).loc.getWorld().getName()))
					{
						validOrders.set(i, null);
					}
				}
			}
			else
				return null;
			while (validOrders.remove(null));
		}
		
		return validOrders;
	}
}