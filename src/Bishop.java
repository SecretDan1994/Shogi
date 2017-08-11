public class Bishop extends Pieces
{

	public Bishop()
	{
		super();
		super.setType("Black Bishop");
	}

	public int [] enemymarkers = new int[8];
	public int difference = 0;
	public boolean result,bugcatcher=false,check1=false,check2=false,check3=false,check4=false;
	
	public boolean isValidMove(int Position,int destination,String [] pieceOnTileAlliance,boolean promotion, String turn)
	{		
		difference = Math.abs(Position-destination);
		bugcatcher=((Position == 71 && destination == 38)||(Position == 71 && destination == 27));
		check1=false;
		check2=false;
		check3=false;
		check4=false;
		
		//upleft
		if(Position>destination && difference%11==0 && bugcatcher == false)
			check1=true;
		//upright
		if(Position>destination && difference%9==0)
			check2=true;
		//downleft
		if(Position<destination && difference%9==0)
			check3=true;
		//downright
		if(Position<destination && difference%11==0)
			check4=true;
		
		if(promotion == false)
		{			
			if(check1 == true)
				answer = MoveUpLeft(Position,destination,pieceOnTileAlliance,turn);
			else if(check2 == true)
				answer = MoveUpRight(Position,destination,pieceOnTileAlliance,turn);	
			else if(check3 == true)
				answer = MoveDownLeft(Position,destination,pieceOnTileAlliance,turn);	
			else if(check4 == true)
				answer = MoveDownRight(Position,destination,pieceOnTileAlliance,turn);	
			else
				answer = false;
				
		}
		else if(promotion == true)
		{
			if (destination == (Position-1) || destination == (Position+1) || destination == (Position-9) || destination == (Position-10) || destination == (Position-11) || destination == (Position+9) || destination == (Position+10) || destination == (Position+11))
				answer = true;
			else if(check1 == true)
				answer = MoveUpLeft(Position,destination,pieceOnTileAlliance,turn);
			else if(check2 == true)
				answer = MoveUpRight(Position,destination,pieceOnTileAlliance,turn);	
			else if(check3 == true)
				answer = MoveDownLeft(Position,destination,pieceOnTileAlliance,turn);	
			else if(check4 == true)
				answer = MoveDownRight(Position,destination,pieceOnTileAlliance,turn);	
			else
				answer = false;
		}
		return answer;
	} 
	
	public boolean MoveUpLeft(int Position,int destination,String [] pieceOnTileAlliance,String turn)
	{
		System.out.println("It's UPLEFT");
		int marker = 0,k=0,max=0,num=0;
		for(int i=0;i<8;i++)
			enemymarkers[i]=0;
			
		for(int i = destination; i<Position-11;i+=11)	
		{
			if((turn == "Black" && pieceOnTileAlliance[i] == "Black") || (turn == "White" && pieceOnTileAlliance[i] == "White"))
				marker=1;
		}
		
		for(int i = Position-11;i>destination;i-=11)
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
		
		if(marker == 0)
			result = true;
		else if(marker==1)
			result = false;
		return result;
	}
	public boolean MoveUpRight(int Position, int destination, String [] pieceOnTileAlliance,String turn)
	{
		
		System.out.println("It's UPRIGHT");
		int marker=0,k=0,max=0,num=0;
		for(int i=0;i<8;i++)
			enemymarkers[i]=0;
		for(int i = destination; i<=Position-9; i+=9) 
			if((turn == "Black" && pieceOnTileAlliance[i] == "Black") || (turn == "White" && pieceOnTileAlliance[i] == "White"))
				marker=1;
		for(int i = destination;i<Position;i+=9)
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

		if(marker == 0)
			result = true;
		else if(marker==1)
			result = false;
		return result;
	}
	public boolean MoveDownRight(int Position, int destination,String [] pieceOnTileAlliance,String turn)
	{
		System.out.println("It's DOWNRIGHT");
		int marker = 0,k=0;

		for(int i=0;i<8;i++)
			enemymarkers[i]=0;
		
		for(int i = Position+11; i<destination; i+=11) 
		{
			if((turn == "Black" && pieceOnTileAlliance[i] == "Black") || (turn == "White" && pieceOnTileAlliance[i] == "White"))
				marker=1;

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
	public boolean MoveDownLeft(int Position, int destination, String [] pieceOnTileAlliance,String turn)
	{
		System.out.println("It's DOWNLEFT");
		int marker=0,k=0;
		for(int i=0;i<8;i++)
			enemymarkers[i]=0;
		for(int i = Position+9; i<destination; i+=9) 
		{
			if((turn == "Black" && pieceOnTileAlliance[i] == "Black") || (turn == "White" && pieceOnTileAlliance[i] == "White"))
				marker=1;

			if((turn == "Black" && pieceOnTileAlliance[i] == "White") || (turn == "White" && pieceOnTileAlliance[i] == "Black"))
			{
				enemymarkers[k] = i;
				k++;
			}
		}			

		if(destination > enemymarkers[0] && enemymarkers[0]!=0)
			marker=1;

		if(marker == 0)
			result = true;
		else if(marker==1)
			result = false;
		return result;
	}
}