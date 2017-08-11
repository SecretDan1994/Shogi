public class King extends Pieces
{

	public King()
	{
		super();
		super.setType("Black King");
	}
	
	public boolean isValidMove(int Position,int destination,String [] pieceOnTileAlliance,boolean promotion, String turn)
	{
		if(promotion == false)
		{
			if (destination == (Position-1) || destination == (Position+1) || destination == (Position-9) || destination == (Position-10) || destination == (Position-11) || destination == (Position+9) || destination == (Position+10) || destination == (Position+11))
				answer = true;
			else
				answer = false;
		}
		return answer;
	} 
}
