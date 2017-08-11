
public class SilverGeneral extends Pieces
{
	public SilverGeneral()
	{
		super();
		super.setType("Black Silver General");
	}
	public boolean isValidMove(int Position,int destination,String [] pieceOnTileAlliance,boolean promotion, String turn)
	{
		if(turn == "Black")
		{
			if(promotion == false)
			{
				if (destination == (Position-9) || destination == (Position-10) || destination == (Position-11) || destination == (Position+9) || destination == (Position+11))
					answer = true;
				else
					answer = false;
			}
			else if(promotion == true)
			{
				if (destination == (Position-1) || destination == (Position-9) || destination == (Position-10) || destination == (Position-11) || destination == (Position+1) || destination == (Position+10))
					answer = true;
				else
					answer = false;
			}
		}
		else if(turn == "White")
		{
			if(promotion == false)
			{
				if (destination == (Position+9) || destination == (Position+10) || destination == (Position+11) || destination == (Position-9) || destination == (Position-11))
					answer = true;
				else
					answer = false;
			}
			else if(promotion == true)
			{
				if (destination == (Position+1) || destination == (Position+9) || destination == (Position+10) || destination == (Position+11) || destination == (Position-1) || destination == (Position-10))
					answer = true;
				else
					answer = false;
			}
		}
		return answer;
	} 
}
