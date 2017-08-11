import javax.swing.ImageIcon;

public abstract class Pieces 
{
	public String Alliance; //By default constructor a piece is of the black alliance.
	private int id;
	private int Position;
	private ImageIcon image;
	public boolean answer;
	private int choice;
	private boolean Promotion;
	private boolean Captured;
	private String type;
	//public String type = "pawn";
	
	
	public Pieces()
	{
		Alliance = "Black"; //By default constructor a piece is of the black alliance.
		Position = 0;
		answer = false;
		choice = 0;
		Promotion = false;
		Captured = false;
		type = "Black Pawn";
		id = 0;
	}
	
	public void setAlliance(String choice)
	{
		if(choice == "Black")
			Alliance = "Black";
		else
			Alliance = "White";
	}
	
	public String getAlliance()
	{
		return Alliance;
	}

	public void setId(int ID)
	{
		this.id = ID;
	}
	
	public int getId()
	{
		return id;
	}	
	
	public void setPosition(int Pos)
	{
		this.Position = Pos;
	}
	
	public int getPosition()
	{
		return Position;
	}
	
	
	public void setImage(ImageIcon Image)
	{
		this.image = Image;
	}
	public ImageIcon getImage()
	{
		return image;
	}
	
	public void setChoice(int number)
	{
		this.choice = number;
	}

	public int getChoice()
	{
		return choice;
	}
	public void setPromotion(boolean setting)
	{
		this.Promotion = setting;
	}
	public boolean getPromotion()
	{
		return Promotion;
	}
	public void setCaptured(boolean setting)
	{
		this.Captured = setting;
	}
	public boolean getCaptured()
	{
		return Captured;
	}
	public void setType(String chosenType)	//	add super.setType("type") to the constructor of each child class of Piece;
	{
		this.type = chosenType;
	}	
	public String getType()
	{
		return type;
	}
	
	public boolean isValidMove(int Position,int destination,String [] pieceOnTileAlliance, boolean promotion, String turn)
	{
		if(turn == "Black")
		{
			if(promotion == false)
			{
				if (destination == (Position-10))
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
				if (destination == (Position+10))
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


