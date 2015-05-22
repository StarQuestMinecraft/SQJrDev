package nickmiste.tasks;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class CompletionConfirmationTask implements Runnable
{
	public static HashMap<Player, UUID> pendingCompletions = new HashMap<Player, UUID>();
	
	private Player player;
	
	public CompletionConfirmationTask(Player player, UUID order)
	{
		this.player = player;
		
		ClaimTask.pendingClaims.remove(player);
		BuyInfoTask.info.remove(player);
		CompletionTask.pendingCompletions.remove(player);
		DeletionConfirmationTask.pendingDeletions.remove(player);
		SellInfoTask.prices.remove(player);
		pendingCompletions.remove(player);
		pendingCompletions.put(player, order);
	}
	
	@Override
	public void run()
	{
		pendingCompletions.remove(player);
	}
}
