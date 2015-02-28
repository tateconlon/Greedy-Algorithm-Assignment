//Tate Conlon 10067255 12tc3
public class Agency
{
	public int value;
	public int deadline;
	public int num;
	public boolean chosen;

	public Agency(int deadline, int value, int num)
	{
		this.value = value * 1000;
		this.deadline = deadline;
		chosen = false;
		this.num = num;
	}
}