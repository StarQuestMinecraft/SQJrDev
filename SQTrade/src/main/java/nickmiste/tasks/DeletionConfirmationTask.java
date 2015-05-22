package nickmiste.tasks;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class DeletionConfirmationTask implements Runnable
{
	public static HashMap<Player, UUID> pendingDeletions = new HashMap<Player, UUID>();
	
	private Player player;
	
	public DeletionConfirmationTask(Player player, UUID order)
	{
		this.player = player;
		
		ClaimTask.pendingClaims.remove(player);
		BuyInfoTask.info.remove(player);
		CompletionConfirmationTask.pendingCompletions.remove(player);
		CompletionTask.pendingCompletions.remove(player);
		SellInfoTask.prices.remove(player);
		pendingDeletions.remove(player);
		pendingDeletions.put(player, order);
	}
	
	@Override
	public void run()
	{
		pendingDeletions.remove(player);
	}
}
