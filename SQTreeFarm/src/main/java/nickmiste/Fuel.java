package nickmiste;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Fuel implements ConfigurationSerializable
{
	private String material;
	private int damage;
	private int efficiency;
	
	public Fuel(String material, int damage, int efficiency)
	{
		this.material = material;
		this.damage = damage;
		this.efficiency = efficiency;
	}
	
	public Fuel(Map<String, Object> map)
	{
		this.material = (String) map.get("material");
		this.damage = (int) map.get("damage");
		this.efficiency = (int) map.get("efficiency");
	}
	
	@Override
	public Map<String, Object> serialize() 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("material", this.material);
		map.put("damage", this.damage);
		map.put("efficiency", this.efficiency);
		return map;
	}
	
	public Material getMaterial()
	{
		return Material.getMaterial(this.material);
	}
	
	public int getDamage()
	{
		return this.damage;
	}
	
	public int getEfficiency()
	{
		return this.efficiency;
	}
}
