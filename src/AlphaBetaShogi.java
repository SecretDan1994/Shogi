import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JFrame; //Everything inside the window.
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * The following is a project for the board game Shogi, commonly known as Japanese chess.
 * It's turn-based and uses the pieces: Pawn,Rook,Bishop,Lance,Knight,SilverGeneral,GoldGeneral,and King.
 * All pieces have an alliance to either the Black Team or the White Team   
 * Traditionally the Black Team faces upwards and the White Team faces downwards. The Black Team also goes first.
 * @author Daniel Llerena
 * @version 0.993
 */

public class AlphaBetaShogi implements ActionListener{

	private final JPanel gui = new JPanel(new BorderLayout(3,3));
    private static JFrame f = new JFrame("ShogiMaster");
	private JButton[] buttons = new JButton[90], capBbuttons = new JButton[8], capWbuttons= new JButton[8];
	private JPanel shogiBoard,CapturedBlackPieces,CapturedWhitePieces;
	private int[] ShogiBoard = new int[91]; 
	public String[] pieceOnTileAlliance = new String[177];
	private int i,j=0,position, buttonX, buttonY, destination, newposition, clicked = 0,bob = 0,tileClicked = 0,turncounter = 1,inputValue;
	
	//Counters for how many captured of each Black piece there are.
	private int CapBPawnsCounter,CapBBishopsCounter,CapBRooksCounter,CapBLancesCounter,CapBKnightsCounter,CapBSGsCounter,CapBGGsCounter,CapBKingsCounter;
	
	//Counters for how many captured of each White piece there are.
	private int CapWPawnsCounter,CapWBishopsCounter,CapWRooksCounter,CapWLancesCounter,CapWKnightsCounter,CapWSGsCounter,CapWGGsCounter,CapWKingsCounter;
	
	public ArrayList<Pieces> pieces = new ArrayList<Pieces>();
	public ArrayList<Pieces> capturedpieces = new ArrayList<Pieces>();
	public String turn;
	public ImageIcon storedImage,secondStoredImage;
	public boolean enemyPiece = false, promotion = false, captured = false, isValidPiece = true, isValidBlackPieceDrop = false, isValidWhitePieceDrop = false, isValidDestination = false;
	public boolean CapBPawnsPressed = false, CapBBishopsPressed = false, CapBRooksPressed = false, CapBLancesPressed = false,CapBKnightsPressed = false, CapBSGsPressed = false, CapBGGsPressed = false, CapBKingsPressed = false;
	public boolean CapWPawnsPressed = false, CapWBishopsPressed = false, CapWRooksPressed = false, CapWLancesPressed = false,CapWKnightsPressed = false, CapWSGsPressed = false, CapWGGsPressed = false, CapWKingsPressed = false;
	public boolean dropflag = false; // This tells us if it's a drop.
	public boolean isBlackTurn()
	{
		if(turncounter % 2 != 0)
			return true;
		else
			return false;
	}
	public String Turn()
	{
		String response = "null";
		if(isBlackTurn())
			response = "Black";
		else if(!isBlackTurn())
			response = "White";
		return response;
	}
	
	public Pawn p1 = new Pawn();
	public Pawn p2 = new Pawn();
	public Pawn p3 = new Pawn();
	public Pawn p4 = new Pawn();
	public Pawn p5 = new Pawn();
	public Pawn p6 = new Pawn();
	public Pawn p7 = new Pawn();
	public Pawn p8 = new Pawn();
	public Pawn p9 = new Pawn();
	public Pawn p10 = new Pawn();
	public Pawn p11 = new Pawn();
	public Pawn p12 = new Pawn();
	public Pawn p13 = new Pawn();
	public Pawn p14 = new Pawn();
	public Pawn p15 = new Pawn();
	public Pawn p16 = new Pawn();
	public Pawn p17 = new Pawn();
	public Pawn p18 = new Pawn();
	public Rook r1 = new Rook();
	public Rook r2 = new Rook();
	public Bishop b1 = new Bishop();
	public Bishop b2 = new Bishop();
	public Lance l1 = new Lance();
	public Lance l2 = new Lance();
	public Lance l3 = new Lance();
	public Lance l4 = new Lance();
	public Knight n1 = new Knight();
	public Knight n2 = new Knight();
	public Knight n3 = new Knight();
	public Knight n4 = new Knight();
	public SilverGeneral sg1 = new SilverGeneral();
	public SilverGeneral sg2 = new SilverGeneral();
	public SilverGeneral sg3 = new SilverGeneral();
	public SilverGeneral sg4 = new SilverGeneral();
	public GoldGeneral g1 = new GoldGeneral();
	public GoldGeneral g2 = new GoldGeneral();
	public GoldGeneral g3 = new GoldGeneral();
	public GoldGeneral g4 = new GoldGeneral();
	public King k1 = new King();
	public King k2 = new King();
	
	public AlphaBetaShogi()
	{
		initializeGui();	
	}
	 
	/* Now let's discuss how pieces move

	Programming ideas: 1. Use position swapper to move pieces
	2. Draw the path for each possible move the piece can go to.
	3. Add a timer for both teams.
	4. Add a ranking system based on the official shogi ranking system.
	5. Show the coordinates on movement of a piece. Ex: [16:00:00]Pawn C9-D9
														Use of Timestamp from time class.
														[16:02:00]Pawn*C9 Dropping a pawn token.
	Pawn - Can only move one space upwards
	**Please note that the opponent version of the piece, considered the white team, is upside down.
	Lance - can only move spaces above */
    
	public final void updateGui()
	{
        //For the Captured For Black Pieces area
        CapturedBlackPieces.add(new JLabel("For    Black"),SwingConstants.CENTER); 	// Top title
        CapturedBlackPieces.add(new JLabel("    Captured"),SwingConstants.CENTER); 	// Top title
        for(i=0;i<16;i++)
        {
        	switch(i)
        	{
		        case 2:
		            CapturedBlackPieces.add(new JLabel("" + CapBPawnsCounter, SwingConstants.CENTER));
		            break;
		        case 3:
		            CapturedBlackPieces.add(new JLabel("" + CapBBishopsCounter, SwingConstants.CENTER));
		            break;
		        case 6:
		            CapturedBlackPieces.add(new JLabel("" + CapBRooksCounter, SwingConstants.CENTER));
		            break;
		        case 7:
		            CapturedBlackPieces.add(new JLabel("" + CapBLancesCounter, SwingConstants.CENTER));
		            break;
		        case 10:
		            CapturedBlackPieces.add(new JLabel("" + CapBKnightsCounter, SwingConstants.CENTER));
		            break;
		        case 11:
		            CapturedBlackPieces.add(new JLabel("" + CapBSGsCounter, SwingConstants.CENTER));
		            break;
		        case 14:
		            CapturedBlackPieces.add(new JLabel("" + CapBGGsCounter, SwingConstants.CENTER));
		            break;
		        case 15:
		            CapturedBlackPieces.add(new JLabel("" + CapBKingsCounter, SwingConstants.CENTER));
		            break;
		        default: CapturedBlackPieces.add(capBbuttons[j]);
		        		 j++;
		                 break;
        	}
        }
        j=0;
        
        //For the Captured For White Pieces Area
        CapturedWhitePieces.add(new JLabel("For    White"),SwingConstants.CENTER); 	// Top title
        CapturedWhitePieces.add(new JLabel("    Captured"),SwingConstants.CENTER); 	// Top title
        for(i=0;i<16;i++)
        {
        	switch(i)
        	{
		        case 2:
		            CapturedWhitePieces.add(new JLabel("" + CapWPawnsCounter, SwingConstants.CENTER));
		            break;
		        case 3:
		            CapturedWhitePieces.add(new JLabel("" + CapWBishopsCounter, SwingConstants.CENTER));
		            break;
		        case 6:
		            CapturedWhitePieces.add(new JLabel("" + CapWRooksCounter, SwingConstants.CENTER));
		            break;
		        case 7:
		            CapturedWhitePieces.add(new JLabel("" + CapWLancesCounter, SwingConstants.CENTER));
		            break;
		        case 10:
		            CapturedWhitePieces.add(new JLabel("" + CapWKnightsCounter, SwingConstants.CENTER));
		            break;
		        case 11:
		            CapturedWhitePieces.add(new JLabel("" + CapWSGsCounter, SwingConstants.CENTER));
		            break;
		        case 14:
		            CapturedWhitePieces.add(new JLabel("" + CapWGGsCounter, SwingConstants.CENTER));
		            break;
		        case 15:
		            CapturedWhitePieces.add(new JLabel("" + CapWKingsCounter, SwingConstants.CENTER));
		            break;
		        default: CapturedWhitePieces.add(capWbuttons[j]);
		        		 j++;
		                 break;
        	}
        }
        j=0;
	}
	
    public final void initializeGui()
    {
    	gui.setBorder(new EmptyBorder(5,5,5,5));
    	CapturedBlackPieces = new JPanel(new GridLayout(0,2));
    	CapturedWhitePieces = new JPanel(new GridLayout(0,2));
    	shogiBoard = new JPanel(new GridLayout(0,10));
    	shogiBoard.setBorder(new LineBorder(Color.BLACK));
    	gui.add(shogiBoard);
    	gui.add(CapturedBlackPieces,BorderLayout.LINE_START);
    	gui.add(CapturedWhitePieces,BorderLayout.LINE_END);

    	//Hi there, I'm putting all the objects in an array list here:		
    	pieces.add(p1);
    	pieces.add(p2);
    	pieces.add(p3);
    	pieces.add(p4);
    	pieces.add(p5);
    	pieces.add(p6);
    	pieces.add(p7);
    	pieces.add(p8);
    	pieces.add(p9);
    	pieces.add(p10);
    	pieces.add(p11);
    	pieces.add(p12);
    	pieces.add(p13);
    	pieces.add(p14);
    	pieces.add(p15);
    	pieces.add(p16);
    	pieces.add(p17);
    	pieces.add(p18);
    	pieces.add(r1);
    	pieces.add(r2);
    	pieces.add(b1);
    	pieces.add(b2);
    	pieces.add(l1);
    	pieces.add(l2);
    	pieces.add(l3);
    	pieces.add(l4);
    	pieces.add(n1);
    	pieces.add(n2);
    	pieces.add(n3);
    	pieces.add(n4);
    	pieces.add(sg1);
    	pieces.add(sg2);
    	pieces.add(sg3);
    	pieces.add(sg4);
    	pieces.add(g1);
    	pieces.add(g2);
    	pieces.add(g3);
    	pieces.add(g4);
    	pieces.add(k1);
    	pieces.add(k2);
    	
    	//Set IDs to each piece inside of the piece arraylist.
    	for(int g=0; g<pieces.size();g++)
    		pieces.get(g).setId(g+1);
		
		//Here we are storing the setImage( of each piece with its respective ImageIcon object.
    	for(i=0;i<9;i++)
    		pieces.get(i).setImage(new ImageIcon("src/images/-8.png"));
    	for(i=9;i<18;i++)
    		pieces.get(i).setImage(new ImageIcon("src/images/8.png"));
		r1.setImage(new ImageIcon("src/images/-2.png"));
		r2.setImage(new ImageIcon("src/images/2.png"));
		b1.setImage(new ImageIcon("src/images/-3.png"));
		b2.setImage(new ImageIcon("src/images/3.png"));
		l1.setImage(new ImageIcon("src/images/-7.png"));
		l2.setImage(new ImageIcon("src/images/-7.png"));
		l3.setImage(new ImageIcon("src/images/7.png"));
		l4.setImage(new ImageIcon("src/images/7.png"));
		n1.setImage(new ImageIcon("src/images/-6.png"));
		n2.setImage(new ImageIcon("src/images/-6.png"));
		n3.setImage(new ImageIcon("src/images/6.png"));
		n4.setImage(new ImageIcon("src/images/6.png"));
		sg1.setImage(new ImageIcon("src/images/-5.png"));
		sg2.setImage(new ImageIcon("src/images/-5.png"));
		sg3.setImage(new ImageIcon("src/images/5.png"));
		sg4.setImage(new ImageIcon("src/images/5.png"));
		g1.setImage(new ImageIcon("src/images/-4.png"));
		g2.setImage(new ImageIcon("src/images/-4.png"));
		g3.setImage(new ImageIcon("src/images/4.png"));
		g4.setImage(new ImageIcon("src/images/4.png"));
		k1.setImage(new ImageIcon("src/images/-1.png"));
		k2.setImage(new ImageIcon("src/images/1.png"));
	
		for(i=0;i<91;i++)
			ShogiBoard[i] = 0; //Start with an empty board.
	
		//Occupy Starting Positions.
		for(i = 0; i < 9; i++)
			ShogiBoard[i] = 1;
	
		ShogiBoard[11] = 1;
		ShogiBoard[17] = 1;
	
		for(i = 20; i < 29; i++)
			ShogiBoard[i] = 1;
	
		for(i = 60; i < 69; i++)
			ShogiBoard[i] = 1;
	
		ShogiBoard[71] = 1;
		ShogiBoard[77] = 1;
	
		for(i=80;i<89;i++)
			ShogiBoard[i] = 1;
		
		ShogiBoard[89] = 2; //Special spot for captured pieces.
		ShogiBoard[9] = 2;
		
		// Assign Alliances.
		for(i=9;i<18;i++)
			pieces.get(i).setAlliance("White");
		r2.setAlliance("White");
		b2.setAlliance("White");
		l3.setAlliance("White");
		l4.setAlliance("White");
		n3.setAlliance("White");
		n4.setAlliance("White");
		sg3.setAlliance("White");
		sg4.setAlliance("White");
		g3.setAlliance("White");
		g4.setAlliance("White");
		k2.setAlliance("White");
		
		// Assign Types.
		for(i=9;i<18;i++)
			pieces.get(i).setType("White Pawn");
		r2.setType("White Rook");
		b2.setType("White Bishop");
		l3.setType("White Lance");
		l4.setType("White Lance");
		n3.setType("White Knight");
		n4.setType("White Knight");
		sg3.setType("White Silver General");
		sg4.setType("White Silver General");
		g3.setType("White Gold General");
		g4.setType("White Gold General");
		k2.setType("White King");		
		
		//Initalize string array pieceOnTileType
		
		for(i = 0;i<90;i++)
			pieceOnTileAlliance[i] = "null";

		for(i = 0; i < 9; i++)
			pieceOnTileAlliance[i] = "White";
	
		pieceOnTileAlliance[11] = "White";
		pieceOnTileAlliance[17] = "White";
	
		for(i = 20; i < 29; i++)
			pieceOnTileAlliance[i] = "White";
	
		for(i = 60; i < 69; i++)
			pieceOnTileAlliance[i] = "Black";
	
		pieceOnTileAlliance[71] = "Black";
		pieceOnTileAlliance[77] = "Black";
	
		for(i=80;i<89;i++)
			pieceOnTileAlliance[i] = "Black";
		
		for(i=89;i<177;i++)
			pieceOnTileAlliance[i] = "null";
		
		// Assign StartingPositions
	
		p1.setPosition(60);
		p2.setPosition(61);
		p3.setPosition(62);
		p4.setPosition(63);
		p5.setPosition(64);
		p6.setPosition(65);
		p7.setPosition(66); 
		p8.setPosition(67);
		p9.setPosition(68);
		p10.setPosition(20);
		p11.setPosition(21); 
		p12.setPosition(22);
		p13.setPosition(23);
		p14.setPosition(24);
		p15.setPosition(25);
		p16.setPosition(26);
		p17.setPosition(27);
		p18.setPosition(28);
		r1.setPosition(77);
		r2.setPosition(11);
		b1.setPosition(71);
		b2.setPosition(17);
		l1.setPosition(80);
		l2.setPosition(88);
		l3.setPosition(0);
		l4.setPosition(8);
		n1.setPosition(81);
		n2.setPosition(87);
		n3.setPosition(1);
		n4.setPosition(7);
		sg1.setPosition(82); 
		sg2.setPosition(86);
		sg3.setPosition(2);
		sg4.setPosition(6);
		g1.setPosition(83);
		g2.setPosition(85);
		g3.setPosition(3);
		g4.setPosition(5);
		k1.setPosition(84);
		k2.setPosition(4);
    	
    	Insets buttonMargin = new Insets(0,0,0,0);

		for(i = 0;i<90;i++) // need 11 spaces after each row
		{		
			JButton b =new JButton();
            b.setMargin(buttonMargin);
			b.setBackground(Color.orange);
			b.setPreferredSize(new Dimension(75, 75));
			switch(i)
			{
				case 0: b.setIcon(l3.getImage());
						break;
				case 1: b.setIcon(n3.getImage());
						break;
				case 2: b.setIcon(sg3.getImage());
						break;
				case 3: b.setIcon(g3.getImage());
						break;
				case 4: b.setIcon(k2.getImage());
						break;
				case 5: b.setIcon(g4.getImage());
						break;
				case 6: b.setIcon(sg4.getImage());
						break;
				case 7: b.setIcon(n4.getImage());
						break;
				case 8: b.setIcon(l4.getImage());
						break;
				case 11: b.setIcon(r2.getImage());
						break;
				case 17: b.setIcon(b2.getImage());
						break;
				case 20: b.setIcon(p10.getImage());
						break;
				case 21: b.setIcon(p11.getImage());
						break;
				case 22: b.setIcon(p12.getImage());
						break;
				case 23: b.setIcon(p13.getImage());
						break;
				case 24: b.setIcon(p14.getImage());
						break;
				case 25: b.setIcon(p15.getImage());
						break;
				case 26: b.setIcon(p16.getImage());
						break;
				case 27: b.setIcon(p17.getImage());
						break;
				case 28: b.setIcon(p18.getImage());
						break;
				case 60: b.setIcon(p1.getImage());
						break;
				case 61: b.setIcon(p2.getImage());
						break;
				case 62: b.setIcon(p3.getImage());
						break;
				case 63: b.setIcon(p4.getImage());
						break;
				case 64: b.setIcon(p5.getImage());
						break;
				case 65: b.setIcon(p6.getImage());
						break;
				case 66: b.setIcon(p7.getImage());
						break;
				case 67: b.setIcon(p8.getImage());
						break;
				case 68: b.setIcon(p9.getImage());
						break;
				case 71: b.setIcon(b1.getImage());
						break;
				case 77: b.setIcon(r1.getImage());
						break;
				case 80: b.setIcon(l1.getImage());
						break;
				case 81: b.setIcon(n1.getImage());
						break;
				case 82: b.setIcon(sg1.getImage());
						break;
				case 83: b.setIcon(g1.getImage());
						break;
				case 84: b.setIcon(k1.getImage());
						break;
				case 85: b.setIcon(g2.getImage());
						break;
				case 86: b.setIcon(sg2.getImage());
						break;
				case 87: b.setIcon(n2.getImage());
						break;
				case 88: b.setIcon(l2.getImage());
						break;
				default: b.setIcon(null);
						break;
			}
			
			buttons[i] = b;
		}
		
		for(i = 0;i<90;i++)
		    buttons[i].addActionListener(this);
		
		
		//This is for establishing the buttons for our new captured black pieces pile.
		for(i=0;i<8;i++)	
		{
			JButton b =new JButton();
	        b.setMargin(buttonMargin);
			b.setBackground(Color.black);
			b.setPreferredSize(new Dimension(75, 75));
		
			switch(i)
			{
				case 0: b.setIcon(p1.getImage());
						break;
				case 1: b.setIcon(b1.getImage());
						break;
				case 2: b.setIcon(r1.getImage());
						break;
				case 3: b.setIcon(l1.getImage());
						break;
				case 4: b.setIcon(n1.getImage());
						break;
				case 5: b.setIcon(sg1.getImage());
						break;
				case 6: b.setIcon(g1.getImage());
						break;
				case 7: b.setIcon(k1.getImage());
						break;
				default:b.setIcon(null);
						break;
			}
			capBbuttons[i]=b;
		}
			
		//This is for establishing the buttons for our new captured white pieces pile.
		for(i=0;i<8;i++)	
		{
			JButton b =new JButton();
			//b.setOpaque(true);				// Removes background of buttons.
			//b.setContentAreaFilled(true);
			//b.setBorderPainted(true);
	        b.setMargin(buttonMargin);
			b.setBackground(Color.white);
			b.setPreferredSize(new Dimension(75, 75));
		
			switch(i)
			{
				case 0: b.setIcon(p10.getImage());
						break;
				case 1: b.setIcon(b2.getImage());
						break;
				case 2: b.setIcon(r2.getImage());
						break;
				case 3: b.setIcon(l3.getImage());
						break;
				case 4: b.setIcon(n3.getImage());
						break;
				case 5: b.setIcon(sg3.getImage());
						break;
				case 6: b.setIcon(g3.getImage());
						break;
				case 7: b.setIcon(k2.getImage());
						break;
				default:b.setIcon(null);
						break;
			}
			capWbuttons[i]=b;
		}
			
		for(i=0;i<8;i++)
			capBbuttons[i].addActionListener(this);		
		
		for(i=0;i<8;i++)
			capWbuttons[i].addActionListener(this);
		
		
		//fill the Shogi Board.
		
        for (i = 0; i < 9; i++) 
            shogiBoard.add(new JLabel("" + (9-i), SwingConstants.CENTER));
        
		shogiBoard.add(new JLabel(""));
        // fill the black non-pawn piece row
        for (i = 0; i < 90; i++) {
            switch(i) {
                case 9:
                shogiBoard.add(new JLabel("A", SwingConstants.CENTER));
                	break;           	
                case 19:
                    shogiBoard.add(new JLabel("B", SwingConstants.CENTER));
                    break;
                case 29:
                    shogiBoard.add(new JLabel("C", SwingConstants.CENTER));
                    break;
                case 39:
                    shogiBoard.add(new JLabel("D", SwingConstants.CENTER));
                    break;
                case 49:
                    shogiBoard.add(new JLabel("E", SwingConstants.CENTER));
                    break;
                case 59:
                    shogiBoard.add(new JLabel("F", SwingConstants.CENTER));
                    break;
                case 69:
                    shogiBoard.add(new JLabel("G", SwingConstants.CENTER));
                    break;
                case 79:
                    shogiBoard.add(new JLabel("H", SwingConstants.CENTER));
                    break;
                case 89:
                    shogiBoard.add(new JLabel("I", SwingConstants.CENTER));
                    break;
                default:
                    shogiBoard.add(buttons[i]);
                    break;
                }
        }
    	   
        //For the Captured For Black Pieces area
        
        CapturedBlackPieces.add(new JLabel("For    Black"),SwingConstants.CENTER); 	// Top title
        CapturedBlackPieces.add(new JLabel("    Captured"),SwingConstants.CENTER); 	// Top title
        for(i=0;i<16;i++)
        {
        	switch(i)
        	{
		        case 2:
		            CapturedBlackPieces.add(new JLabel("" + CapBPawnsCounter, SwingConstants.CENTER));
		            break;
		        case 3:
		            CapturedBlackPieces.add(new JLabel("" + CapBBishopsCounter, SwingConstants.CENTER));
		            break;
		        case 6:
		            CapturedBlackPieces.add(new JLabel("" + CapBRooksCounter, SwingConstants.CENTER));
		            break;
		        case 7:
		            CapturedBlackPieces.add(new JLabel("" + CapBLancesCounter, SwingConstants.CENTER));
		            break;
		        case 10:
		            CapturedBlackPieces.add(new JLabel("" + CapBKnightsCounter, SwingConstants.CENTER));
		            break;
		        case 11:
		            CapturedBlackPieces.add(new JLabel("" + CapBSGsCounter, SwingConstants.CENTER));
		            break;
		        case 14:
		            CapturedBlackPieces.add(new JLabel("" + CapBGGsCounter, SwingConstants.CENTER));
		            break;
		        case 15:
		            CapturedBlackPieces.add(new JLabel("" + CapBKingsCounter, SwingConstants.CENTER));
		            break;
		        default: CapturedBlackPieces.add(capBbuttons[j]);
		        		 j++;
		                 break;
        	}
        }
        j=0;
        
        //For the Captured For White Pieces area
        CapturedWhitePieces.add(new JLabel("For    White"),SwingConstants.CENTER); 	// Top title
        CapturedWhitePieces.add(new JLabel("    Captured"),SwingConstants.CENTER); 	// Top title
        for(i=0;i<16;i++)
        {
        	switch(i)
        	{
		        case 2:
		            CapturedWhitePieces.add(new JLabel("" + CapWPawnsCounter, SwingConstants.CENTER));
		            break;
		        case 3:
		            CapturedWhitePieces.add(new JLabel("" + CapWBishopsCounter, SwingConstants.CENTER));
		            break;
		        case 6:
		            CapturedWhitePieces.add(new JLabel("" + CapWRooksCounter, SwingConstants.CENTER));
		            break;
		        case 7:
		            CapturedWhitePieces.add(new JLabel("" + CapWLancesCounter, SwingConstants.CENTER));
		            break;
		        case 10:
		            CapturedWhitePieces.add(new JLabel("" + CapWKnightsCounter, SwingConstants.CENTER));
		            break;
		        case 11:
		            CapturedWhitePieces.add(new JLabel("" + CapWSGsCounter, SwingConstants.CENTER));
		            break;
		        case 14:
		            CapturedWhitePieces.add(new JLabel("" + CapWGGsCounter, SwingConstants.CENTER));
		            break;
		        case 15:
		            CapturedWhitePieces.add(new JLabel("" + CapWKingsCounter, SwingConstants.CENTER));
		            break;
		        default: CapturedWhitePieces.add(capWbuttons[j]);
		        		 j++;
		                 break;
        	}
        }
        j=0;

    }
    
    //public final JComponent getShogiBoard() {
    //    return shogiBoard;
    //}

    public final JComponent getGui() {
        return gui;
    }    

	public static void main(String[] args){


		//new AlphaBetaShogi();

        Runnable r = new Runnable() {

           @Override
           public void run() {
                AlphaBetaShogi sb = new AlphaBetaShogi();


                f.add(sb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                //f.setContentPane(new JLabel(new ImageIcon("C:/Users/Armando9167/Desktop/images/board.png")));
                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
       };	

	SwingUtilities.invokeLater(r);
	}
	
	public final int getNewPosition(int position, int destination)
	{
		int Position = 0;
		Position = position-(position-destination);
		return Position;
	}

	public void updateCounter(String type)
	{
		if(type == "White Pawn")
			CapBPawnsCounter++;
		else if(type == "White Rook")
			CapBRooksCounter++;
		else if(type == "White Bishop")
			CapBBishopsCounter++;
		else if(type == "White Lance")
			CapBLancesCounter++;
		else if(type == "White Knight")
			CapBKnightsCounter++;
		else if(type == "White Silver General")
			CapBSGsCounter++;
		else if(type == "White Gold General")
			CapBGGsCounter++;
		else if(type == "White King")
			CapBKingsCounter++;
		else if(type == "Black Pawn")
			CapWPawnsCounter++;
		else if(type == "Black Rook")
			CapWRooksCounter++;
		else if(type == "Black Bishop")
			CapWBishopsCounter++;
		else if(type == "Black Lance")
			CapWLancesCounter++;
		else if(type == "Black Knight")
			CapWKnightsCounter++;
		else if(type == "Black Silver General")
			CapWSGsCounter++;
		else if(type == "Black Gold General")
			CapWGGsCounter++;
		else if(type == "Black King")
			CapWKingsCounter++;
	}
	
	
	public void move()
	{
		if(turn == "Black" && isValidDestination)
		{
			newposition = getNewPosition(position,destination);
			ShogiBoard[position] = 0;
			ShogiBoard[newposition] = 1;
			pieceOnTileAlliance[position] = "null";
			pieceOnTileAlliance[destination] = "Black";
			buttons[position].setIcon(null);	//set setImage( for button on old position to blank.
			buttons[newposition].setIcon(storedImage);	//set setImage( for button on new position to setImage of the object.
			
			for(int k=0;k<pieces.size();k++)
			{
				if(pieces.get(k).getPosition() == destination && pieces.get(k).getAlliance()=="White")
				{
					updateCounter(pieces.get(k).getType());
					pieces.get(k).setPosition(9);			//Fixes alliance issue after eating a piece.
					pieces.get(k).setCaptured(true);
					pieces.get(k).setAlliance("Black");
					
					// Change Type to Opposing Type.
					if(pieces.get(k).getType()=="White Pawn")
						pieces.get(k).setType("Black Pawn");
					else if(pieces.get(k).getType()=="White Rook")
						pieces.get(k).setType("Black Rook");
					else if(pieces.get(k).getType()=="White Bishop")
						pieces.get(k).setType("Black Bishop");
					else if(pieces.get(k).getType()=="White Lance")
						pieces.get(k).setType("Black Lance");
					else if(pieces.get(k).getType()=="White Knight")
						pieces.get(k).setType("Black Knight");
					else if(pieces.get(k).getType()=="White Silver General")
						pieces.get(k).setType("Black Silver General");
					else if(pieces.get(k).getType()=="White Gold General")
						pieces.get(k).setType("Black Gold General");
					else if(pieces.get(k).getType()=="White King")
						pieces.get(k).setType("Black King");					
					
					capturedpieces.add(pieces.get(k));
				}
			}
			pieces.get(i).setPosition(newposition);
			clicked = 0;
			turncounter=2;
		}
		else if (turn == "White" && isValidDestination)
		{
			newposition = getNewPosition(position,destination);
			ShogiBoard[position] = 0;
			ShogiBoard[newposition] = 1;
			pieceOnTileAlliance[position] = "null";
			pieceOnTileAlliance[destination] = "White";
			buttons[position].setIcon(null);	//set setImage( for button on old position to blank.
			buttons[newposition].setIcon(storedImage);	//set setImage( for button on new position to setImage of the object.
			for(int k=0;k<pieces.size();k++)
			{
				if(pieces.get(k).getPosition() == destination && pieces.get(k).getAlliance()=="Black")
				{
					updateCounter(pieces.get(k).getType());
					pieces.get(k).setPosition(89);			//Fixes alliance issue after eating a piece.
					pieces.get(k).setCaptured(true);
					pieces.get(k).setAlliance("White");
					
					// Change Type to Opposing Type.
					if(pieces.get(k).getType()=="Black Pawn")
						pieces.get(k).setType("White Pawn");
					else if(pieces.get(k).getType()=="Black Rook")
						pieces.get(k).setType("White Rook");
					else if(pieces.get(k).getType()=="Black Bishop")
						pieces.get(k).setType("White Bishop");
					else if(pieces.get(k).getType()=="Black Lance")
						pieces.get(k).setType("White Lance");
					else if(pieces.get(k).getType()=="Black Knight")
						pieces.get(k).setType("White Knight");
					else if(pieces.get(k).getType()=="Black Silver General")
						pieces.get(k).setType("White Silver General");
					else if(pieces.get(k).getType()=="Black Gold General")
						pieces.get(k).setType("White Gold General");
					else if(pieces.get(k).getType()=="Black King")
						pieces.get(k).setType("White King");
					
					capturedpieces.add(pieces.get(k));
				}
			}
			pieces.get(i).setPosition(newposition);
			clicked = 0;
			turncounter=1;
		}
		
		//Update the GUI
		CapturedBlackPieces.removeAll();
		CapturedWhitePieces.removeAll();
		updateGui();
		f.repaint();
	}
	
	public void promotePiecePrompt()
	{
		if(pieces.get(i).getChoice() == 0)
		{
			inputValue = JOptionPane.showConfirmDialog(null,"Promote Piece?", "Promote Piece?", JOptionPane.YES_NO_OPTION);
			if (inputValue == JOptionPane.YES_OPTION)
			{
				if(pieces.get(i).getType() == "Black Pawn")
					pieces.get(i).setImage(new ImageIcon("src/images/-14.png"));
				else if(pieces.get(i).getType() == "Black Rook")
					pieces.get(i).setImage(new ImageIcon("src/images/-9.png"));
				else if(pieces.get(i).getType() == "Black Bishop")
					pieces.get(i).setImage(new ImageIcon("src/images/-10.png"));
				else if(pieces.get(i).getType() == "Black Lance")
					pieces.get(i).setImage(new ImageIcon("src/images/-13.png"));
				else if(pieces.get(i).getType() == "Black Knight")
					pieces.get(i).setImage(new ImageIcon("src/images/-12.png"));
				else if(pieces.get(i).getType() == "Black Silver General")
					pieces.get(i).setImage(new ImageIcon("src/images/-11.png"));
				else if(pieces.get(i).getType() == "White Pawn")
					pieces.get(i).setImage(new ImageIcon("src/images/14.png"));
				else if(pieces.get(i).getType() == "White Rook")
					pieces.get(i).setImage(new ImageIcon("src/images/9.png"));
				else if(pieces.get(i).getType() == "White Bishop")
					pieces.get(i).setImage(new ImageIcon("src/images/10.png"));
				else if(pieces.get(i).getType() == "White Lance")
					pieces.get(i).setImage(new ImageIcon("src/images/13.png"));
				else if(pieces.get(i).getType() == "White Knight")
					pieces.get(i).setImage(new ImageIcon("src/images/12.png"));
				else if(pieces.get(i).getType() == "White Silver General")
					pieces.get(i).setImage(new ImageIcon("src/images/11.png"));
				
				buttons[destination].setIcon(pieces.get(i).getImage());
				pieces.get(i).setPromotion(true);
				System.out.println("Promotion for: " + pieces.get(i).getType() + " is " + pieces.get(i).getPromotion());
				pieces.get(i).setChoice(2);
		    }
		}	
	}

	public void promote()
	{
		if(pieces.get(i).getType() == "Black Pawn")
			pieces.get(i).setImage(new ImageIcon("src/images/-14.png"));
		else if(pieces.get(i).getType() == "Black Lance")
			pieces.get(i).setImage(new ImageIcon("src/images/-13.png"));
		else if(pieces.get(i).getType() == "Black Knight")
			pieces.get(i).setImage(new ImageIcon("src/images/-12.png"));
		else if(pieces.get(i).getType() == "White Pawn")
			pieces.get(i).setImage(new ImageIcon("src/images/14.png"));
		else if(pieces.get(i).getType() == "White Lance")
			pieces.get(i).setImage(new ImageIcon("src/images/13.png"));
		else if(pieces.get(i).getType() == "White Knight")
			pieces.get(i).setImage(new ImageIcon("src/images/12.png"));
		buttons[destination].setIcon(pieces.get(i).getImage());
		pieces.get(i).setPromotion(true);
		pieces.get(i).setChoice(2);
	}

	public void drop(int g)
	{ 
		
		for(int l=0;l<pieces.size();l++)
		{
			if(pieces.get(l).getId() == capturedpieces.get(g).getId())
			{
				
				if(pieces.get(l).getType() == "Black Pawn")
				{
					System.out.println("The amount of captured Black Pawns is:" + CapBPawnsCounter);
					CapBPawnsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/-8.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidBlackPieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "Black Bishop"){
					CapBBishopsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/-3.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidBlackPieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "Black Rook"){
					CapBRooksCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/-2.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidBlackPieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "Black Lance"){
					CapBLancesCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/-7.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidBlackPieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "Black Knight"){
					CapBKnightsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/-6.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidBlackPieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "Black Silver General"){
					CapBSGsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/-5.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidBlackPieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "Black Gold General"){
					CapBGGsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/-4.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidBlackPieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "Black King"){
					CapBKingsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/-1.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidBlackPieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "White Pawn"){
					CapWPawnsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/8.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidWhitePieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "White Bishop"){
					CapWBishopsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/3.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidWhitePieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "White Rook"){
					CapWRooksCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/2.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidWhitePieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "White Lance"){
					CapWLancesCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/7.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidWhitePieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "White Knight"){
					CapWKnightsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/6.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidWhitePieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "White Silver General"){
					CapWSGsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/5.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidWhitePieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "White Gold General"){
					CapWGGsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/4.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidWhitePieceDrop = false;
					break;
				}
				else if(pieces.get(l).getType() == "White King"){
					CapWKingsCounter--;
					pieces.get(i).setImage(new ImageIcon("src/images/1.png"));
					pieceOnTileAlliance[destination] = pieces.get(l).getAlliance();
					buttons[destination].setIcon(pieces.get(l).getImage());
					pieces.get(l).setPosition(destination);
					pieces.get(l).setCaptured(false);
					pieces.get(l).setPromotion(false);
					capturedpieces.remove(pieces.get(l));
					isValidWhitePieceDrop = false;
					break;
				}
			}			
		}
		ShogiBoard[destination] = 1;
		
		if(isBlackTurn()){
			clicked = 0;
			turncounter=2;	
		}
		else{
			clicked = 0;
			turncounter=1;				
		}
		dropflag = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton button = (JButton)e.getSource();
		JFrame frame = new JFrame();
	    buttonX = button.getX();
		buttonY = button.getY();
		turn = Turn(); 
				
		System.out.println("Logs: \n");
		System.out.println("clicked: " + clicked);
		System.out.println("turncounter: " + turncounter);	
		System.out.println("drop?: " + dropflag);	
		
		
		if(clicked == 0)
		{

			for(i = 0;i<90;i++)
				if(buttons[i].getX() == buttonX && buttons[i].getY() == buttonY)
					tileClicked = i;
			
			//Checking if a captured piece button was pressed.
			if(isBlackTurn())
			{
				for(i = 0;i<8;i++)
				{
					if(capBbuttons[i].getX() == buttonX && capBbuttons[i].getY() == buttonY)
					{
						switch(i)
						{
							case 1:if(CapBBishopsCounter > 0 )
								   		isValidBlackPieceDrop = true;
									break;
							case 2:if(CapBRooksCounter > 0 )
								   		isValidBlackPieceDrop = true;
									break;
							case 3:if(CapBLancesCounter > 0 )
										isValidBlackPieceDrop = true;
									break;
							case 4:if(CapBKnightsCounter > 0 )
										isValidBlackPieceDrop = true;
									break;
							case 5:if(CapBSGsCounter > 0 )
										isValidBlackPieceDrop = true;
									break;
							case 6:if(CapBGGsCounter > 0 )
										isValidBlackPieceDrop = true;
									break;
							case 7:	if(CapBKingsCounter > 0 )
										isValidBlackPieceDrop = true;
									break;
							default:if(CapBPawnsCounter > 0 )
										isValidBlackPieceDrop = true;
									break;
						}
						tileClicked = 89;
					}
				}
			}
			else if(!isBlackTurn())
			{
				for(i=0;i<8;i++)
				{
					if(capWbuttons[i].getX() == buttonX && capWbuttons[i].getY() == buttonY)
					{
						switch(i)
						{
							case 1: if(CapWBishopsCounter > 0 )
										isValidWhitePieceDrop = true;
									break;
							case 2: if(CapWRooksCounter > 0 )
								   		isValidWhitePieceDrop = true;
									break;
							case 3: if(CapWLancesCounter > 0 )
										isValidWhitePieceDrop = true;
									break;
							case 4: if(CapWKnightsCounter > 0 )
										isValidWhitePieceDrop = true;
									break;
							case 5: if(CapWSGsCounter > 0 )
										isValidWhitePieceDrop = true;
									break;
							case 6: if(CapWGGsCounter > 0 )
										isValidWhitePieceDrop = true;
									break;
							case 7:	if(CapWKingsCounter > 0 )
										isValidWhitePieceDrop = true;
									break;
							default: if(CapWPawnsCounter > 0 )
										isValidWhitePieceDrop = true;
									break;
						}
						tileClicked = 9;
					}
				}
			}
			
			for(i = 0;i<pieces.size();i++)
			{
				
				if(pieces.get(i).getPosition() == tileClicked && isBlackTurn())
				{
					if(pieces.get(i).getAlliance() == "Black") // if Piece is Black
						isValidPiece = true;
					else
						isValidPiece = false;
				}
				else if(pieces.get(i).getPosition() == tileClicked && !isBlackTurn())
				{
					if(pieces.get(i).getAlliance() == "White") // if Piece is White
						isValidPiece = true;
					else
						isValidPiece = false;
				}
			}
			
			if(tileClicked == 89)
				isValidPiece = true;
			else if(tileClicked == 9)
				isValidPiece = true;
			
			if(ShogiBoard[tileClicked] == 1 && isValidPiece == true)
			{
				for(i = 0;i<pieces.size();i++)
				{
					bob = pieces.get(i).getPosition();
			 	 
					if(buttonX == buttons[bob].getX() && buttonY == buttons[bob].getY())
					{
						position = pieces.get(i).getPosition();
						storedImage = pieces.get(i).getImage();
					}
				}
				clicked=1;
			}
			
			else if(isValidBlackPieceDrop && isValidPiece){
				clicked=1;
				dropflag = true;
			}
			
			else if(isValidWhitePieceDrop && isValidPiece){
				clicked=1;
				dropflag = true;
			}
			
			else if(ShogiBoard[tileClicked] == 1 && isBlackTurn() && isValidPiece == false)
				JOptionPane.showMessageDialog(frame, "Not your piece.");
			
			else if(ShogiBoard[tileClicked] == 1 && !isBlackTurn() && isValidPiece == false)
				JOptionPane.showMessageDialog(frame, "Not your piece.");
			
			else if(ShogiBoard[tileClicked] == 2 && isBlackTurn() && isValidPiece)
			{

				if(button.getBackground() == Color.black && isValidBlackPieceDrop == false)
					JOptionPane.showMessageDialog(frame, "You haven't captured any pieces of that type, try again.");

				else if(button.getBackground() == Color.white)
					JOptionPane.showMessageDialog(frame, "Not your Captured Piece Panel.");
				
			}
			else if(ShogiBoard[tileClicked] == 2 && !isBlackTurn() && isValidPiece)
			{

				if(button.getBackground() == Color.white && isValidWhitePieceDrop == false)
					JOptionPane.showMessageDialog(frame, "You haven't captured any pieces of that type, try again.");

				else if(button.getBackground() == Color.black)
					JOptionPane.showMessageDialog(frame, "Not your Captured Piece Panel.");
			
			}
			
			if(isBlackTurn())
				System.out.println("Black Turn");
			else
				System.out.println("White Turn");
		}
		else if(clicked == 1 && !dropflag)
		{
			
			for(i = 0;i<90;i++)
				if(buttons[i].getX() == buttonX && buttons[i].getY() == buttonY)
					destination = i;
			
			for(i=0;i<pieces.size();i++)
			{
				if(pieces.get(i).getPosition() == destination && turn == "Black" && pieces.get(i).getAlliance() == "White")
				{
					isValidDestination = true;
				    enemyPiece = true;
					System.out.println("Destination is White");
				}
				else if(pieces.get(i).getPosition() == destination && turn == "Black" && pieces.get(i).getAlliance() == "Black")
				{
					isValidDestination = false;
					System.out.println("Destination is Black");
					position = tileClicked;
					destination = tileClicked;
					JOptionPane.showMessageDialog(frame, "Try again.");
					clicked = 0;
					turncounter=1;
					enemyPiece = false;
				}
				else if(pieces.get(i).getPosition() == destination && turn == "White" && pieces.get(i).getAlliance() == "Black")
				{
					isValidDestination = true;
				    enemyPiece = true;
					System.out.println("Destination is Black");
				}
				else if(pieces.get(i).getPosition() == destination && turn == "White" && pieces.get(i).getAlliance() == "White")
				{
					isValidDestination = false;
					System.out.println("Destination is White");
					position = tileClicked;
					destination = tileClicked;
					JOptionPane.showMessageDialog(frame, "Try again.");
					clicked = 0;
					turncounter=2;
					enemyPiece = false;
				}
			}
			
			if(pieceOnTileAlliance[destination] == "null")
			{
				isValidDestination = true;
				System.out.println("Destination is null");
			}
			
			for(i=0;i<pieces.size();i++)
			{
				if(pieces.get(i).getPosition() == position)
				{
					promotion = pieces.get(i).getPromotion();
					captured = pieces.get(i).getCaptured();
					System.out.println("Captured?: " + captured);
					System.out.println("piece ID: " + pieces.get(i).getId());				

					if(isValidDestination && pieces.get(i).isValidMove(position,destination,pieceOnTileAlliance,promotion,turn)){
							System.out.println("Piece: " + pieces.get(i).getType());
							System.out.println("Choice: " + pieces.get(i).getChoice());
							System.out.println("Promotion(T/F?): " + promotion);
							
							//This is a small index swap fix to an indexing problem that happens in between the move function.
							int index = i;
							move();
							i = index;
	
							//If it's a black/white pawn or lance check to see which promotion row it's in.
							if(pieces.get(i).getType()== "Black Pawn" || pieces.get(i).getType()== "White Pawn" || pieces.get(i).getType()== "Black Lance" || pieces.get(i).getType()== "White Lance"){ 
								if((destination >= 0 && destination <= 8) && turn == "Black" && pieces.get(i).getChoice()==0) // If it's the last row.
									promote();
								else if((destination >= 10 && destination <= 28) && turn == "Black" && pieces.get(i).getChoice()==0){ //If it's not last row.
									promotePiecePrompt();
								}
								else if((destination >= 80 && destination <= 88) && turn == "White" && pieces.get(i).getChoice()==0) // If it's last row.
									promote();
								else if((destination >= 60 && destination <= 78) && turn == "White" && pieces.get(i).getChoice()==0) // If it's not last row.
									promotePiecePrompt();
							}
							else if(pieces.get(i).getType()=="Black Knight" || pieces.get(i).getType()=="White Knight")
							{
								if((destination >= 0 && destination <= 18) && turn == "Black" && pieces.get(i).getChoice()==0) // If it's the last row.
									promote();
								else if((destination >= 20 && destination <= 28) && turn == "Black" && pieces.get(i).getChoice()==0){ //If it's not last row.
									promotePiecePrompt();
								}
								else if((destination >= 70 && destination <= 88) && turn == "White" && pieces.get(i).getChoice()==0) // If it's last row.
									promote();
								else if((destination >= 60 && destination <= 68) && turn == "White" && pieces.get(i).getChoice()==0) // If it's not last row.
									promotePiecePrompt();							
							}
							else{
								if((destination >= 0 && destination <= 28) && turn == "Black" && pieces.get(i).getChoice()==0)
									promotePiecePrompt();
								else if((destination >= 60 && destination <= 88) && turn == "White" && pieces.get(i).getChoice()==0)
									promotePiecePrompt();
							}
						}
					}
				}
			}
		
			else if(clicked == 1 && dropflag){
				for(i = 0;i<90;i++)
					if(buttons[i].getX() == buttonX && buttons[i].getY() == buttonY)
						destination = i;
				for(i=0;i<pieces.size();i++)
				{
					if(pieces.get(i).getAlliance() == "Black" && isValidBlackPieceDrop){
						for(int g=0;g<capturedpieces.size();g++){
							System.out.println("capturedpiecelist piece ID: " + capturedpieces.get(g).getId());
							if(capturedpieces.get(g).getId() == pieces.get(i).getId())
							{
								System.out.println("capturedpiece ID: " + capturedpieces.get(g).getId());
								drop(g);
								break;
							}
						}
					}
					else if(pieces.get(i).getAlliance() == "White" && isValidWhitePieceDrop){
						for(int g=0;g<capturedpieces.size();g++){
							if(capturedpieces.get(g).getId() == pieces.get(i).getId())
							{
								drop(g);
								break;
							}
						}							
					}
				}
			}	
		}
	}

	



