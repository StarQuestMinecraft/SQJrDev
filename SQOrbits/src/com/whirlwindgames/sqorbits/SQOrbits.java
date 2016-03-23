package com.whirlwindgames.sqorbits;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
public class SQOrbits extends JavaPlugin{
	private static final double radiansPerDegree = Math.PI/180;
	private FileConfiguration config;
	private ConfigurationSection configPlanets;
	private Set<String> planetNames;
	private WorldGuardPlugin wg;
	private MarkerAPI markerAPI;
	private boolean dynmapOK;
	private YamlConfiguration planetRotationConfig;
	
	public void onEnable(){		
		updateConfigStuff();				
		Plugin wg = getServer().getPluginManager().getPlugin("WorldGuard");
		if (wg == null || !(wg instanceof WorldGuardPlugin)) {
			print("WorldGuard is not properly installed! This plugin cannot run.");
			setEnabled(false);
	        return;
	    }
		this.wg = (WorldGuardPlugin)wg;		
		
		Plugin dynmapPlugin = getServer().getPluginManager().getPlugin("dynmap");		
		if(dynmapPlugin instanceof DynmapCommonAPI){			
			DynmapCommonAPI dynmapAPI = (DynmapCommonAPI)dynmapPlugin;
			markerAPI = dynmapAPI.getMarkerAPI();
			if(markerAPI != null) dynmapOK = true;
			else {
				print("Missing dynmap marker API!");
				dynmapOK = false;
			}
		} else {
			print("Missing dynmap API!");
			dynmapOK=false;
		}
						
		tryMovePlanets();
		savePlanetRotations();
		
		getCommand("moveplanet").setExecutor(new MovePlanetExecutor(this));
		getCommand("moveplanets").setExecutor(new MovePlanetsExecutor(this));
	}
	
	public void updateConfigStuff(){
		saveDefaultConfig();
		config = getConfig();
		configPlanets = config.getConfigurationSection("Planets");
		planetNames = configPlanets.getKeys(false);
		String path = config.getString("RotationStoreFile");
		if(path==null){
			print("The configuration field '"+path+"' was not a valid YAML configuration component of type 'String'. Expect errors.");
		}
		File planetRotationFile = new File(path);
		if(!planetRotationFile.exists()){
			print("Planet rotations save file does not exist at path '"+path+"'. Creating one...");
			planetRotationConfig = new YamlConfiguration();
			for(String name:planetNames){
				planetRotationConfig.set(name, 0.0);
			}
			savePlanetRotations();
		} else {
			planetRotationConfig = YamlConfiguration.loadConfiguration(planetRotationFile);
		}		
	}
	
	public void tryMovePlanets(){					
		for (String name : planetNames){
			tryMovePlanet(name);
		}
	}	
	
	public void savePlanetRotations(){
		String path = config.getString("RotationStoreFile");
		File planetRotationFile = new File(path);
		try {
			planetRotationConfig.save(planetRotationFile);
		} catch (IOException e) {
			print("An error occurred saving the planet rotation file at path '"+path+"'.");
			e.printStackTrace();
		}
	}
	
	public void tryMovePlanet(String name) {
		if(verifyPlanetConfig(name)){
			ConfigurationSection planetConfig = configPlanets.getConfigurationSection(name);
			String worldName =planetConfig.getString("World");
			World world = getServer().getWorld(worldName);
			if (world==null){
				print("There is no world with the name '"+worldName+"'. The planet '"+name+"' will not be moved.");
				return;
			}
			double oldPlanetAngle = planetRotationConfig.getDouble(name);
			double planetAngle = (oldPlanetAngle+planetConfig.getDouble("Speed"))%360;
			planetRotationConfig.set(name, planetAngle);
			double sin = Math.sin(planetAngle*radiansPerDegree);
			double cos = Math.cos(planetAngle*radiansPerDegree);
			double dist = planetConfig.getDouble("BlocksFromSun");
			double sunX = planetConfig.getDouble("SunCoords.x");
			double sunZ = planetConfig.getDouble("SunCoords.z");
			double centreX = dist*cos+sunX;
			double centreZ = dist*sin+sunZ;
			double centreY = planetConfig.getInt("OrbitCenterY");
			RegionManager rgManager = wg.getRegionManager(world);
			ProtectedRegion oldRegion = rgManager.getRegion(name);	
			if(oldRegion!=null)	{
				BlockVector min = oldRegion.getMinimumPoint();
				BlockVector max = oldRegion.getMaximumPoint();
				//fills with air
				CuboidRegion weRegion = new CuboidRegion(min, max);
				for(BlockVector bv : weRegion)
				{
					world.getBlockAt(bv.getBlockX(), bv.getBlockY(), bv.getBlockZ()).setType(Material.AIR);
				}
				rgManager.removeRegion(name);
			}			
			//max planet size ~= 1 mil blocks
			EditSession es = new EditSession(new BukkitWorld(world), 1000000);
			File schematicFile = new File(planetConfig.getString("SchematicPath"));
			Vector centreVector = new Vector(centreX, centreY, centreZ);
			Vector roundedCentreVector = centreVector.round();
			int blockX = roundedCentreVector.getBlockX();
			int blockY = roundedCentreVector.getBlockY();
			int blockZ= roundedCentreVector.getBlockZ();
			print("New rounded centre coordiantes for planet '"+name+"' are: "+roundedCentreVector.toString()+".");
			CuboidClipboard schematic = null;			
			try {
				schematic = MCEditSchematicFormat.getFormat(schematicFile).load(schematicFile);
				schematic.paste(es, roundedCentreVector, true);
			} catch (MaxChangedBlocksException | IOException | DataException e) {
				print("An error occured pasting the schematic. Stack trace:");
				e.printStackTrace();
			}
			Vector minCornerOffset = new BlockVector(
					planetConfig.getInt("RegionMinCornerOffset.x"),
					planetConfig.getInt("RegionMinCornerOffset.y"),
					planetConfig.getInt("RegionMinCornerOffset.z")
					);
			Vector maxCornerOffset = new BlockVector(
					planetConfig.getInt("RegionMaxCornerOffset.x"),
					planetConfig.getInt("RegionMaxCornerOffset.y"),
					planetConfig.getInt("RegionMaxCornerOffset.z")
					);
			BlockVector minCorner = roundedCentreVector.add(minCornerOffset).toBlockVector();
			BlockVector maxCorner = roundedCentreVector.add(maxCornerOffset).toBlockVector();
			ProtectedCuboidRegion newProtectedRegion = new ProtectedCuboidRegion(name , minCorner, maxCorner);			
			rgManager.addRegion(newProtectedRegion);
			
			@SuppressWarnings("unchecked")
			List<String> configMovecraftPaths = (List<String>) planetConfig.getList("MovecraftConfigPaths");
			
			for(String path : configMovecraftPaths){
				File movecraftConfigFile = new File(path);
				YamlConfiguration movecraftConfig = YamlConfiguration.loadConfiguration(movecraftConfigFile);
				movecraftConfig.set(name+".Xcoord", blockX);
				movecraftConfig.set(name+".Ycoord", blockY);
				movecraftConfig.set(name+".Zcoord", blockZ);
				try {
					movecraftConfig.save(movecraftConfigFile);
				} catch (IOException e) {
					print("Could not save movecraft config at path '"+path+"'. Stack trace: ");
					e.printStackTrace();
				}
			}
			if(!dynmapOK)return;
			//dynmap marker movement
			MarkerSet markerSet = markerAPI.getMarkerSet("markers");
			if(markerSet==null){
				print("The 'markers' set of dynmap markers could not be found!");
				return;
			}
			Marker marker = markerSet.findMarker(name);
			if(marker==null){
				print("The dynmap marker for planet '"+name+"' could not be found!");
				return;
			}
			marker.setLocation(worldName, blockX, blockY, blockZ);
			print("Planet '"+name+"' has successfully been moved.");
		}
		
	}
	
	//verifies all fields are present in config. DOES NOT verify that file paths and world names are valid, only that they are strings
	private boolean verifyPlanetConfig(String name) {
		if (!configPlanets.isConfigurationSection(name)){
			print("Planet '" + name + "' is not a valid configuration section! This planet will not be moved.");
			return false;
		}
		ConfigurationSection planetConfig = configPlanets.getConfigurationSection(name);
		if(!planetConfig.isDouble("Speed")){
			printPlanetConfigError("Speed", name, "Double");
			return false;
		}
		if(!planetConfig.isDouble("BlocksFromSun")){
			printPlanetConfigError("BlocksFromSun", name, "Double");
			return false;
		}
		if(!planetConfig.isString("SchematicPath")){
			printPlanetConfigError("SchematicPath", name, "String");
			return false;
		}
		if(!planetConfig.isList("MovecraftConfigPaths")){
			printPlanetConfigError("MovecraftConfigPaths", name, "List");
			return false;
		}
		if(!planetConfig.isInt("OrbitCenterY")){
			printPlanetConfigError("OrbitCenterY", name, "Integer");
			return false;
		}
		if(!planetConfig.isString("World")){
			printPlanetConfigError("World", name, "String");
			return false;
		}
		if(!planetConfig.isDouble("SunCoords.x")){
			printPlanetConfigError("SunCoords.x", name, "Double");
			return false;
		}
		if(!planetConfig.isDouble("SunCoords.z")){
			printPlanetConfigError("SunCoords.z", name, "Double");
			return false;
		}
		if(!planetConfig.isInt("RegionMinCornerOffset.x")){
			printPlanetConfigError("RegionMinCornerOffset.x", name, "Integer");
			return false;
		}
		if(!planetConfig.isInt("RegionMinCornerOffset.y")){
			printPlanetConfigError("RegionMinCornerOffset.y", name, "Integer");
			return false;
		}
		if(!planetConfig.isInt("RegionMinCornerOffset.z")){
			printPlanetConfigError("RegionMinCornerOffset.z", name, "Integer");
			return false;
		}
		if(!planetConfig.isInt("RegionMaxCornerOffset.x")){
			printPlanetConfigError("RegionMaxCornerOffset.x", name, "Integer");
			return false;
		}
		if(!planetConfig.isInt("RegionMaxCornerOffset.y")){
			printPlanetConfigError("RegionMaxCornerOffset.y", name, "Integer");
			return false;
		}
		if(!planetConfig.isInt("RegionMaxCornerOffset.z")){
			printPlanetConfigError("RegionMaxCornerOffset.z", name, "Integer");
			return false;
		}
		//if all tests pass
		return true;
	}
	private void printPlanetConfigError(String subpath, String planet, String dataType){
		print("The configuration field '"+subpath+"' for the planet '"+planet+"' was not a valid YAML configuration component of type '"+dataType+"'. "
				+ "The planet will not be moved.");
	}
	private void print(String msg){
		getServer().getLogger().info("[SQOrbits] " + msg);
	}
	protected void message(CommandSender sender, String msg){
		sender.sendMessage("[SQOrbits] " + msg);
	}
}
