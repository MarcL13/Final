import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JComponent;

public class Ball extends JComponent implements Updatable
{
	private Ellipse2D.Double ball;
	private int dx = 0, dy = -5;
	private ArrayList<Ball> balls = new ArrayList<Ball>();
	private ArrayList<Brick> bricks = new ArrayList<Brick>();
	private boolean start = false;
	
	//for the first ball
	public Ball(int x, int y)
	{
		setBounds(x,y,9,9);
		ball = new Ellipse2D.Double(0,0,10,10);
		this.setSize(11,11);
	}
	

	public void updateBrick(ArrayList<Brick> bricks)
	{
		for(int i = 0; i < bricks.size(); i++)
		{
			for(int j = 0; j < balls.size(); j++)
			{
				if(balls.get(j).getX() >= bricks.get(i).getX() && balls.get(j).getY() >= bricks.get(i).getY() && balls.get(j).getX() <= bricks.get(i).getX() + 65 && balls.get(j).getY() <= bricks.get(i).getY() + 40)
				{
					
					bricks.get(i).updateHP();
					remove(bricks.get(i));
				}
			}
		}
	}

	
	public void setStart(boolean start)
	{
		this.start = start;
	}
	
	public boolean getStart()
	{
		return start;
	}
	
	public void update()
	{
		setLocation(getX() + dx, getY() + dy);
	}
	
	public void setDx(int x)
	{
		dx = x;
	}
	
	public void setDy(int y)
	{
		dy = y;
	}
	
	public int getDx()
	{
		return dx;
	}
	
	public int getDy()
	{
		return dy;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.fill(ball);
		
	}
		
}
	
