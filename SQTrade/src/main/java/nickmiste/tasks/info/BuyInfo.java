package nickmiste.tasks.info;

import org.bukkit.Material;

public class BuyInfo
{
	public int price;
	public Material material;
	public int quantity;
	public short dmg;
	
	public BuyInfo(int price, Material material, int quantity, short dmg)
	{
		this.price = price;
		this.material = material;
		this.quantity = quantity;
		this.dmg = dmg;
	}
}
