

public class Lance extends Pieces
{
	public Lance()
	{
		super();
		super.setType("Black Lance");
	}
	
	public int [] enemymarkers = new int[8];
	public boolean result;
	
	public boolean isValidMove(int Position,int destination,String [] pieceOnTileAlliance,boolean promotion,String turn)
	{
		if(turn == "Black")
		{
			if(promotion == false)
				answer = MoveUp(Position,destination,pieceOnTileAlliance,turn);
			
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
				answer = MoveDown(Position,destination,pieceOnTileAlliance,turn);
			
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


	public boolean MoveUp(int Position,int destination,String [] pieceOnTileAlliance,String turn)
	{
		System.out.println("OMG IT'S UP");
		int marker = 0,k=0,max=0,num=0;
		boolean check = false;
		for(int i=0;i<8;i++)
			enemymarkers[i]=0;
	
		for(int i = destination;i<destination+80;i+=10)
			if(i==Position)
				check=true;
		
		for(int i = destination; i<Position;i+=10)	
		{
			if((turn == "Black" && pieceOnTileAlliance[i] == "Black") || (turn == "White" && pieceOnTileAlliance[i] == "White"))
				marker=1;
		}
		
		for(int i = destination;i<destination+80;i+=10)
		{
			if((turn == "Black" && pieceOnTileAlliance[i] == "White") || (turn == "White" && pieceOnTileAlliance[i] == "Black"))
			{
				enemymarkers[k] = i;
				k++;
			}
		}
		for(int i=1;i<8;i++)
		{
			num = enemymarkers[i-1];
			if(num>max)
				max=num;
		}
	
		if(destination < max)
			marker=1;
		if(check==false)
			marker=1;
		
		if(marker == 0)
			result = true;
		else if(marker==1)
			result = false;
		return result;
	}
	
	public boolean MoveDown(int Position, int destination,String [] pieceOnTileAlliance,String turn)
	{
		System.out.println("OMG IT'S DOWN");
		int marker = 0,k=0;
		boolean check = false;
		for(int i=0;i<8;i++)
			enemymarkers[i]=0;
		
		for(int i = Position;i<Position+80;i+=10)
			if(i==destination)
				check=true;
		
		for(int i = Position+10; i<destination;i+=10)	
			if((turn == "Black" && pieceOnTileAlliance[i] == "Black") || (turn == "White" && pieceOnTileAlliance[i] == "White"))
				marker=1;
		for(int i = Position;i<Position+80;i+=10)
		{
			if((turn == "Black" && pieceOnTileAlliance[i] == "White") || (turn == "White" && pieceOnTileAlliance[i] == "Black"))
			{
				enemymarkers[k] = i;
				k++;
			}
		}			

		if(destination > enemymarkers[0] && enemymarkers[0] != 0)
			marker=1;
		if(check == false)
			marker = 1;
		if(marker == 0)
			result = true;
		else if(marker==1)
			result = false;
		return result;
	}
}