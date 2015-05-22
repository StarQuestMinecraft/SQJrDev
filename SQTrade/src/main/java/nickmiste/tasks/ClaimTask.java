package nickmiste.tasks;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class ClaimTask implements Runnable
{
	public static ArrayList<Player> pendingClaims = new ArrayList<Player>();
	
	private Player player;
	
	public ClaimTask(Player player)
	{
		this.player = player;
		
		SellInfoTask.prices.remove(player);
		BuyInfoTask.info.remove(player);
		DeletionConfirmationTask.pendingDeletions.remove(player);
		CompletionTask.pendingCompletions.remove(player);
		CompletionConfirmationTask.pendingCompletions.remove(player);
		pendingClaims.remove(player);
		pendingClaims.add(player);
	}

	@Override
	public void run() 
	{
		pendingClaims.remove(player);
	}
}
