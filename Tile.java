public class Tile {

	private int neighbor;
	private boolean bomb;
	private boolean flagged;
	private boolean cleared;
	
	public Tile()
	{
		neighbor = 0;
		bomb = false;
		flagged = false;
		cleared = false;
	}
	
	public void setCleared()
	{
		cleared = true;
	}
	
	public boolean getCleared()
	{
		return cleared;
	}
	
	public void flag()
	{
		flagged = true;
	}
	
	public boolean getFlag()
	{
		return flagged;
	}
	
	public void setBomb()
	{
		bomb = true;
	}
	
	public boolean getBomb()
	{
		return bomb;
	}
	
	public int getNeighbor()
	{
		return neighbor;
	}
	
	public void setNeighbor(int x)
	{
		neighbor = x;
	}
}
