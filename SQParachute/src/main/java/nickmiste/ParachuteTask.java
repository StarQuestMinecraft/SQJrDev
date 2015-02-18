package nickmiste;

public abstract class ParachuteTask implements Runnable
{
	protected int id;
	
	public void setId(int id)
	{
		this.id = id;
	}
}
