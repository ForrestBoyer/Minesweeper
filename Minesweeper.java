import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.JToggleButton;

public class Minesweeper extends JFrame implements ActionListener, KeyListener
{
	private final int size = 16;
	
	private final int bombs = 40;
	
	private int mode = 1;
	
	private Tile[][] board = new Tile[size][size];
	private JButton[][] buttons = new JButton[size][size];
	private JToggleButton toggle = new JToggleButton("Start Flagging");
	private ItemListener listener = new ItemListener(){
		public void itemStateChanged(ItemEvent itemEvent)
		{
			int state = itemEvent.getStateChange(); 
                if (state == ItemEvent.SELECTED) { 
                    mode = 1;
                    toggle.setText("Start Flagging");
                } 
                else { 
  					mode = 2;
  					toggle.setText("Start Mining");
                } 
		}
	};
	
	private Image img;
	private ImageIcon icon1;
	private ImageIcon icon2;
	private ImageIcon icon3;
	private ImageIcon icon4;
	private ImageIcon icon5;
	private ImageIcon icon6;
	private ImageIcon icon7;
	private ImageIcon icon8;
	private ImageIcon iconBomb;
	private ImageIcon iconFlag;
	private ImageIcon iconBlank;
	
	public static void main(String[] args)
  	{
    	Minesweeper window = new Minesweeper();
    	window.setBounds(0, 0, 800, 800);
    	window.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	window.setVisible(true);
  	}

  public Minesweeper()
  {
  	super("Minesweeper");
  	
  	for(int r = 0; r < size; r++)
	{
		for(int c = 0; c < size; c++)
		{
			board[r][c] = new Tile();
		}
	}
  	
  	//Import images
  	img = null;
  	
 	try
 	{
 		
    	img = ImageIO.read(new File("icon1.PNG"));
    	icon1 = new ImageIcon(img);
    	img = ImageIO.read(new File("icon2.PNG"));
    	icon2 = new ImageIcon(img);
    	img = ImageIO.read(new File("icon3.PNG"));
    	icon3 = new ImageIcon(img);
    	img = ImageIO.read(new File("icon4.PNG"));
    	icon4 = new ImageIcon(img);
    	img = ImageIO.read(new File("icon5.PNG"));
    	icon5 = new ImageIcon(img);
    	img = ImageIO.read(new File("icon6.PNG"));
    	icon6 = new ImageIcon(img);
    	img = ImageIO.read(new File("icon7.PNG"));
    	icon7 = new ImageIcon(img);
    	img = ImageIO.read(new File("icon8.PNG"));
    	icon8 = new ImageIcon(img);
    	img = ImageIO.read(new File("iconBomb.PNG"));
    	iconBomb = new ImageIcon(img);
    	img = ImageIO.read(new File("iconFlag.PNG"));
    	iconFlag = new ImageIcon(img);
    	//img = ImageIO.read(new File("iconBlank.PNG"));
    	//iconBlank = new ImageIcon(img);
    	
    }
 	catch(IOException e)
    {
    	e.printStackTrace();
    }
 	
  	for(int i = 0; i < bombs; i++)
  	{
  		board[(int)(Math.random() * size)][(int)(Math.random() * size)].setBomb();
  	}
    
    JPanel buttonPanel = new JPanel();
    	
    buttonPanel.setLayout(new GridLayout(size, size));
    	
	for(int r = 0; r < size; r++)
	{
		for(int c = 0; c < size; c++)
		{
			//Checking number of adjacent tiles
			int neighbor = 0;
			if(!board[r][c].getBomb())
			{
                if( r > 0 && c > 0 && board[r-1][c-1].getBomb()) //top left
                {
                    neighbor++;
                }
                if( c > 0 && board[r][c-1].getBomb()) //left
                {
                    neighbor++;
                }
                if( c < size - 1 && board[r][c+1].getBomb()) //right
                {
                    neighbor++;
                }
                if( r < size - 1 && c > 0 && board[r+1][c-1].getBomb()) //bottom left
                {
                    neighbor++;
                }
                if( r > 0 && board[r-1][c].getBomb()) //up
                {
                    neighbor++;
                }
                if( r < size - 1 && board[r+1][c].getBomb())//down
                {
                    neighbor++;
                }
                if( r > 0 && c < size - 1 &&board[r-1][c+1].getBomb()) //top right
                {
                    neighbor++;
                }
                if( r < size - 1 && c < size - 1 && board[r+1][c+1].getBomb()) //bottom right
                {
                    neighbor++;
                }
			}
			
			buttons[r][c] = new JButton(iconBlank);
			board[r][c].setNeighbor(neighbor);
			
			buttons[r][c].addActionListener(this);
			buttonPanel.add(buttons[r][c]);
		}
	}
	addKeyListener(this);
	
	toggle.addItemListener(listener);
	
    Container c = getContentPane();
    c.add(buttonPanel, BorderLayout.CENTER);
    c.add(toggle, BorderLayout.SOUTH);
  }
  
  public void actionPerformed(ActionEvent e) {
	if(mode == 1) {
    for(int r = 0; r < size; r++)
	{
		for(int c = 0; c < size; c++)
		{
			if(e.getSource() == buttons[r][c])
			{
				int use;
				if(board[r][c].getBomb())
					use = 1;
				else
					use = 0;
				switch(use)
				{
					case 1:
						buttons[r][c].setIcon(iconBomb);
						endGame(0);
						break;
					case 0:
						clear(r,c);
						checkForWin();
						break;
					default:
						
				}
			}
		}
	}
	}
	else if(mode == 2)
	{
		for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				if(e.getSource() == buttons[r][c])
				{
					board[r][c].flag();
					flag(r, c);
					checkForWin();
				}
			}
		}
	}
  }
  
  public void flag(int r, int c)
  {
	  //buttons[r][c].setIcon(iconFlag);
	  buttons[r][c].setBackground(Color.RED);
	  buttons[r][c].setEnabled(false);
  }
  
  public void endGame(int x) {
	  if(x == 1)
	  {
		  JOptionPane.showMessageDialog(null,"You win :)", "End Screen",JOptionPane.PLAIN_MESSAGE);
		  System.exit(0);
	  }
	  else if(x == 0)
	  {
		  JOptionPane.showMessageDialog(null,"You lose :(", "End Screen",JOptionPane.ERROR_MESSAGE);
		  System.exit(0);
	  }
  }
  
  public void checkForWin()
  {
	  int x = 1;
	  for(int r = 0; r < size; r++)
		{
			for(int c = 0; c < size; c++)
			{
				if(buttons[r][c].isEnabled())
					x = 0;
			}
		}
	  if(x == 1)
		  endGame(1);
  }
  
  public void reveal(int r, int c)
  {
	  
	  if(board[r][c].getNeighbor() == 1)
	  {
		  //buttons[r][c].setIcon(icon1);
		  //buttons[r][c].setBackground(Color.BLUE);
	  }
	  if(board[r][c].getNeighbor() == 2)
	  {
		  //buttons[r][c].setIcon(icon2);
		  //buttons[r][c].setBackground(Color.GREEN);
	  }
	  if(board[r][c].getNeighbor() == 3)
	  {
		  //buttons[r][c].setIcon(icon3);
		  //buttons[r][c].setBackground(Color.RED);
	  }
	  if(board[r][c].getNeighbor() == 4)
	  {
		  //buttons[r][c].setIcon(icon4);
		  //buttons[r][c].setBackground(new Color(0, 55, 143));
	  }
	  if(board[r][c].getNeighbor() == 5)
	  {
		  //buttons[r][c].setIcon(icon5);
		  //buttons[r][c].setBackground(new Color(102, 9, 23));
	  }
	  if(board[r][c].getNeighbor() == 6)
	  {
		  //buttons[r][c].setIcon(icon6);
		  //buttons[r][c].setBackground(Color.CYAN);
	  }
	  if(board[r][c].getNeighbor() == 7)
	  {
		  //buttons[r][c].setIcon(icon7);
		  //buttons[r][c].setBackground(Color.BLACK);
	  }
	  if(board[r][c].getNeighbor() == 8)
	  {
		  //buttons[r][c].setIcon(icon8);
		  //buttons[r][c].setBackground(Color.GRAY);
	  }
	  
	  if(board[r][c].getNeighbor() != 0)
		  buttons[r][c].setText(String.valueOf(board[r][c].getNeighbor()));
	  buttons[r][c].setEnabled(false);
  }
  
  public void clear(int r, int c)
  {
	  if(board[r][c].getNeighbor() != 0) {
		  reveal(r, c);
		  board[r][c].setCleared();
	  }
	  else if(!board[r][c].getCleared() && !board[r][c].getBomb())
	  {
		  board[r][c].setCleared();
		  reveal(r, c);
		  if( r > 0 && c > 0) //top left
          {
              clear(r - 1, c - 1);
          }
          if( c > 0) //left
          {
        	  clear(r, c - 1);
          }
          if( c < size - 1) //right
          {
        	  clear(r, c + 1);
          }
          if( r < size - 1 && c > 0) //bottom left
          {
        	  clear(r + 1, c - 1);
          }
          if( r > 0) //up
          {
        	  clear(r - 1, c);
          }
          if( r < size - 1)//down
          {
        	  clear(r + 1, c);
          }
          if( r > 0 && c < size - 1) //top right
          {
        	  clear(r - 1, c + 1);
          }
          if( r < size - 1 && c < size - 1) //bottom right
          {
        	  clear(r + 1, c + 1);
          }
      }
  }
  
  public void keyPressed(KeyEvent e) {
  	System.out.println("Pressy boid");
  	}
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {
  	System.out.println("Pressy boid");}
}