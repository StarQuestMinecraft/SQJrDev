package nickmiste.tasks;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class CompletionTask implements Runnable
{
	public static ArrayList<Player> pendingCompletions = new ArrayList<Player>();
	
	private Player player;
	
	public CompletionTask(Player player)
	{
		this.player = player;
		
		ClaimTask.pendingClaims.remove(player);
		SellInfoTask.prices.remove(player);
		BuyInfoTask.info.remove(player);
		DeletionConfirmationTask.pendingDeletions.remove(player);
		CompletionConfirmationTask.pendingCompletions.remove(player);
		pendingCompletions.remove(player);
		pendingCompletions.add(player);
	}
	
	@Override
	public void run()
	{
		pendingCompletions.remove(player);
	}
}
