
public class Rook extends Pieces
{
	public Rook()
	{
		super();
		super.setType("Black Rook");
	}
	
	public int [] enemymarkers = new int[8];
	
	public boolean result;
	
	public boolean isValidMove(int Position,int destination,String [] pieceOnTileAlliance, boolean promotion, String turn)
	{

		int gap = Math.abs(destination - Position);
		//Time to experiment by using switch statement on switch(gap)
		if(promotion == false)
		{
			if(Position>destination && gap >= 9)
				answer = MoveUp(Position,destination,pieceOnTileAlliance,turn);
			else if(Position<destination && gap >= 9)
				answer = MoveDown(Position,destination,pieceOnTileAlliance,turn);
			else if(Position>destination && (gap > 0 && gap < 9))
				answer = MoveLeft(Position,destination,pieceOnTileAlliance,turn);
			else if(Position<destination && (gap > 0 && gap < 9))
				answer = MoveRight(Position,destination,pieceOnTileAlliance,turn);	
		}
		
		else if(promotion == true)
		{
			if (destination == (Position-1) || destination == (Position+1) || destination == (Position-9) || destination == (Position-10) || destination == (Position-11) || destination == (Position+9) || destination == (Position+10) || destination == (Position+11))			
				answer = true;
			else if(Position>destination && gap > 9)
				answer = MoveUp(Position,destination,pieceOnTileAlliance,turn);
			else if(Position<destination && gap > 9)
				answer = MoveDown(Position,destination,pieceOnTileAlliance,turn);
			else if(Position>destination && gap > 0 && gap < 9)
				answer = MoveLeft(Position,destination,pieceOnTileAlliance,turn);
			else if(Position<destination && gap > 0 && gap < 9)
				answer = MoveRight(Position,destination,pieceOnTileAlliance,turn);	
			else
				answer = false;	
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
		
		for(int i = Position-10;i>destination;i-=10)
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
	public boolean MoveLeft(int Position, int destination, String [] pieceOnTileAlliance,String turn)
	{
		System.out.println("OMG IT'S LEFT");
		int marker = 0,k=0;
		double firstdigitpos=0,firstdigitdest=0;

		firstdigitpos=Math.floor(Position/10);
		firstdigitdest=Math.floor(destination/10);
		
		if(firstdigitpos!=firstdigitdest)
			marker=1;
		
		for(int i=0;i<8;i++)
			enemymarkers[i]=0;
		
		for(int i = destination; i<Position;i++) 
			if((turn == "Black" && pieceOnTileAlliance[i] == "Black") || (turn == "White" && pieceOnTileAlliance[i] == "White"))
				marker=1;
		
		for(int i = Position;i>destination;i--)
		{
			if((turn == "Black" && pieceOnTileAlliance[i] == "White") || (turn == "White" && pieceOnTileAlliance[i] == "Black"))
			{
				enemymarkers[k] = i;
				k++;
			}
		}
		
		if(destination < enemymarkers[0])
			marker=1;
		
		if(marker == 0)
			result = true;
		else if(marker==1)
			result = false;
		return result;
	}
	public boolean MoveRight(int Position, int destination, String [] pieceOnTileAlliance,String turn)
	{
		System.out.println("OMG IT'S RIGHT");
		int marker=0,k=0;
		for(int i=0;i<8;i++)
			enemymarkers[i]=0;
		for(int i = Position+1; i<destination+1; i++) 
			if((turn == "Black" && pieceOnTileAlliance[i] == "Black") || (turn == "White" && pieceOnTileAlliance[i] == "White"))
				marker=1;
		for(int i = Position;i<Position+8;i++)
		{
			if((turn == "Black" && pieceOnTileAlliance[i] == "White") || (turn == "White" && pieceOnTileAlliance[i] == "Black"))
			{
				enemymarkers[k] = i;
				k++;
			}
		}			
		if(destination > enemymarkers[0] && enemymarkers[0] != 0)
			marker=1;

		if(marker == 0)
			result = true;
		else if(marker==1)
			result = false;
		return result;
	}
}
