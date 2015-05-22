package nickmiste.tasks;

import java.util.HashMap;

import nickmiste.tasks.info.BuyInfo;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BuyInfoTask implements Runnable
{
	public static HashMap<Player, BuyInfo> info = new HashMap<Player, BuyInfo>();

	private Player player;
	
	public BuyInfoTask(Player player, int price, Material material, int quantity, short dmg)
	{
		this.player = player;
		
		ClaimTask.pendingClaims.remove(player);
		SellInfoTask.prices.remove(player);
		DeletionConfirmationTask.pendingDeletions.remove(player);
		CompletionTask.pendingCompletions.remove(player);
		CompletionConfirmationTask.pendingCompletions.remove(player);
		info.remove(player);
		info.put(player, new BuyInfo(price, material, quantity, dmg));
	}
	
	@Override
	public void run() 
	{
		info.remove(player);
	}
}