package nickmiste.tasks;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class SellInfoTask implements Runnable
{
	public static HashMap<Player, Integer> prices = new HashMap<Player, Integer>();
	
	private Player player;
	
	public SellInfoTask(Player player, int price)
	{
		this.player = player;
		
		ClaimTask.pendingClaims.remove(player);
		BuyInfoTask.info.remove(player);
		DeletionConfirmationTask.pendingDeletions.remove(player);
		CompletionTask.pendingCompletions.remove(player);
		CompletionConfirmationTask.pendingCompletions.remove(player);
		prices.remove(player);
		prices.put(player, price);
	}
	
	@Override
	public void run()
	{
		prices.remove(player);
	}
}