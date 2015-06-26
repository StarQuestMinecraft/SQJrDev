package nickmiste.orders;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

public class Order
{	
	public UUID orderId;
	public UUID player;
	public boolean isSellOrder;
	public Material item;
	public short dmg;
	public int quantity;
	public int price;
	public Location loc;
	public String world;
	
	public Order(UUID player, boolean isSellOrder, Material item, short dmg, int quantity, int price, Location loc, String world)
	{
		this.orderId = UUID.randomUUID();
		this.player = player;
		this.isSellOrder = isSellOrder;
		this.item = item;
		this.dmg = dmg;
		this.quantity = quantity;
		this.price = price;
		this.loc = loc;
		this.world = world;
	}
	
	@Override
	public String toString()
	{
		return "[" + this.isSellOrder + ", " + this.item + ", " + this.dmg + ", " + this.quantity + ", " + this.price + ", " + this.loc + "]";
	}
}