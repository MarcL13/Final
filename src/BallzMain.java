import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


public class BallzMain extends JFrame implements ActionListener, Updatable
{
	private int counter = 0;
	private ArrayList<Ball> balls;
	private ArrayList<Brick> bricks;
	private ArrayList<AddBalls> addBalls;
	private Timer t1;
	private int level = 1;
	private int dx;
	private int dy;
	private Ball newBall;
	
	
	public BallzMain()
	{
		setBounds(400,200,800,500);
		setLayout(null);
		
		t1 = new Timer(1000/60,this);
		
		
		balls = new ArrayList<Ball>();
		bricks = new ArrayList<Brick>();
		addBalls = new ArrayList<AddBalls>();
		
		//start with one ball
		Ball firstBall = new Ball((getWidth()/2), 350);
		add(firstBall);
		balls.add(firstBall);
		
		
		JButton fire = new JButton("Press to fire");
		fire.setBounds(50,415,150,25);
		add(fire);
		fire.setVisible(true);
		
		JLabel launch = new JLabel("Enter Launch angle.  Between 30 and 150");
		launch.setBounds(50,370,400,25);
		add(launch);
		launch.setVisible(true);
		
		JTextField answer = new JTextField();
		answer.setBounds(50,390,200,25);
		add(answer);
		answer.setVisible(true);
		
		//creating bricks and addBalls
		double random = 0;
		for(int i=1;i<8;i++)
		{
			random = Math.random();
			if(random<.5)
			{
//				JButton test = new JButton("");
//				test.setBounds((i*70), 20, 65, 40);
//				test.setEnabled(false);
//				add(test);
				
				Brick brick = new Brick(level);
				brick.setBounds((i*70), 20, 65, 40);
				brick.setText("" + brick.getHP());
				add(brick);
				bricks.add(brick);
				
			}
			
			
		}
		
		
		fire.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent e)
					{
						String response = answer.getText();
						Integer ans = Integer.parseInt(response);
						
						fire.setEnabled(false);
						answer.setText("");
						answer.setEnabled(false);
						
						t1.start();
						
						//click button set a field and first ball = to these values 
						if(ans < 90)
						{
							dx = (int)(5*(-Math.cos(ans*((Math.PI)/180))));
							dy = (int)(5*(-(Math.sin(ans*((Math.PI)/180)))));
							
						}//repeat
						else if(ans == 90)
						{
							dx = (0);
							dy = (-5);
							
						}
						else if(ans > 90)
						{
							dx = ((int)(5*(-Math.cos(ans*((Math.PI)/180)))));
							dy = ((int)(5*(-(Math.sin(ans*((Math.PI)/180))))));
							
						}
					
					balls.get(0).setDx(dx);
					balls.get(0).setDy(dy);
					fire.setEnabled(true);
					answer.setEnabled(true);
						
				}
		
			});
		
		
		
		setVisible(true);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent arg0)
	{
		counter++;
		balls.get(0).setStart(true);
		balls.get(0).update();

		if(counter/30 < balls.size() && !balls.get(counter/30).getStart())
		{
			balls.get(counter/30).setDx(dx);
			balls.get(counter/30).setDy(dy);
			balls.get(counter/30).setStart(true);
			balls.get(counter/30).update();
		}
		
		for(Ball b : balls)
		{
			if(b.getY() >= 350)
			{
				b.setDx(0);
				b.setDy(0);
				b.setStart(false);
			}
		}
		
		
		

		
		
		//collision with walls
		for(int i = 0; i < balls.size(); i++)
		{
		
			if((balls.get(i)).getX() >= 770 || (balls.get(i)).getX() <= 0)
			{
				(balls.get(i)).setDx(-((balls.get(i)).getDx()));
				
			}
			
			if(((balls.get(balls.size() - 1)).getY() >= 350))
			{
				
				t1.stop();
				counter = 0;
				level++;
				
				
				for(Brick br : bricks)
				{
					br.update();
				}
				
				
				
			}
			
			if(((balls.get(i)).getY() >= 350))
			{
				double random = 0;
				for(int r = 1; r < 8; r++)
				{
					random = Math.random();
					if(random < .5)
					{
						Brick brick = new Brick(level);
						brick.setBounds((r*70), 20, 65, 40);
						brick.setText("" + brick.getHP());
						add(brick);
						bricks.add(brick);
					}
					
				}

			}
			
			if(balls.get(i).getY() <= 0)
			{
				balls.get(i).setDy(-((balls.get(i)).getDy()));
			}
		}
		
		
		
		
		
		
		
		//checking for collision with bricks
		for(int i = bricks.size() - 1; i >= 0; i--)
		{
			for(int j = 0; j < balls.size(); j++)
			{

				if(i<bricks.size() && balls.get(j).getX() >= bricks.get(i).getX() && balls.get(j).getY() >= bricks.get(i).getY() && balls.get(j).getX() <= bricks.get(i).getX() + 67 && balls.get(j).getY() <= bricks.get(i).getY() + 42)
				{
//					balls.get(j).setDx(-(balls.get(j).getDx()));
//					balls.get(j).setDy(-(balls.get(j).getDy()));
					bricks.get(i).updateHP();
					bricks.get(i).setText("" + bricks.get(i).getHP());
					if(bricks.get(i).getHP() == 0)
					{
						remove(bricks.get(i));
						bricks.remove(bricks.get(i));
						repaint();
						
					}
					if(counter%2 == 0)
					{
						balls.get(j).setDy(-(balls.get(j).getDy()));
					}
					else
					{
						balls.get(j).setDx(-(balls.get(j).getDx()));
					}
				}
			}
		}
		
	
		
	}
	
	public static void main(String[] args) 
	{
		new BallzMain();
	}

}








//import java.awt.CardLayout;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.util.ArrayList;
//import java.awt.event.KeyListener;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.Timer;
//
//
//public class BallzMain extends JFrame implements ActionListener, Updatable
//{
//	private int counter = 0;
//	private ArrayList<Ball> balls;
//	private ArrayList<Brick> bricks;
//	private ArrayList<AddBalls> addBalls;
//	private ArrayList<Ball> tempBall;
//	private Timer t1;
//	private int level = 1;
//	private int dx;
//	private int dy;
//	private Ball newBall;
//	
//	
//	public BallzMain()
//	{
//		setBounds(400,200,800,500);
//		setLayout(null);
//		
//		t1 = new Timer(1000/60,this);
//		
//		
//		balls = new ArrayList<Ball>();
//		bricks = new ArrayList<Brick>();
//		addBalls = new ArrayList<AddBalls>();
//		
//		//start with one ball
//		Ball firstBall = new Ball((getWidth()/2), 350);
//		add(firstBall);
//		balls.add(firstBall);
//		
//		
//		JButton fire = new JButton("Press to fire");
//		fire.setBounds(50,415,150,25);
//		add(fire);
//		fire.setVisible(true);
//		
//		JLabel launch = new JLabel("Enter Launch angle.  Between 30 and 150");
//		launch.setBounds(50,370,400,25);
//		add(launch);
//		launch.setVisible(true);
//		
//		JTextField answer = new JTextField();
//		answer.setBounds(50,390,200,25);
//		add(answer);
//		answer.setVisible(true);
//		
//		//creating bricks and addBalls
//		double random = 0;
//		for(int i=1;i<8;i++)
//		{
//			random = Math.random();
//			if(random<.5)
//			{
////				JButton test = new JButton("");
////				test.setBounds((i*70), 20, 65, 40);
////				test.setEnabled(false);
////				add(test);
//				
//				Brick brick = new Brick(level);
//				brick.setBounds((i*70), 20, 65, 40);
//				brick.setText("" + brick.getHP());
//				add(brick);
//				bricks.add(brick);
//				
//			}
//			
////			else if(.5<=random && random<=.7)
////			{
////				AddBalls addB = new AddBalls((i)*70,25);
////				addBalls.add(addB);
////				add(addB);
////			}
//		}
//		
//		
//		fire.addActionListener(new ActionListener()
//				{
//
//					public void actionPerformed(ActionEvent e)
//					{
//						String response = answer.getText();
//						Integer ans = Integer.parseInt(response);
//						
//						fire.setEnabled(false);
//						answer.setText("");
//						answer.setEnabled(false);
//						
//						t1.start();
//						
//						//click button set a field and first ball = to these values 
//						if(ans < 90)
//						{
//							dx = (int)(5*(-Math.cos(ans*((Math.PI)/180))));
//							dy = (int)(5*(-(Math.sin(ans*((Math.PI)/180)))));
//							
//						}//repeat
//						else if(ans == 90)
//						{
//							dx = (0);
//							dy = (-5);
//							
//						}
//						else if(ans > 90)
//						{
//							dx = ((int)(5*(-Math.cos(ans*((Math.PI)/180)))));
//							dy = ((int)(5*(-(Math.sin(ans*((Math.PI)/180))))));
//							
//						}
//					
//					balls.get(0).setDx(dx);
//					balls.get(0).setDy(dy);
//					
//					
//					
//					
//					
//					
//					fire.setEnabled(true);
//					answer.setEnabled(true);
//						
//				}
//		
//			});
//		
//		
//		
//		setVisible(true);
//		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
//	}
//
//
//	@Override
//	public void update() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void actionPerformed(ActionEvent e)
//	{
//		counter++;
//		
//		//to add balls
//		tempBall = new ArrayList<Ball>();
//		tempBall = balls;
//		if(!tempBall.get(tempBall.size() -1).getStart())
//			{
//				newBall = new Ball(balls.get(0).getX(), balls.get(0).getY());
//				add(newBall);
//				balls.add(newBall);			
//			}
//		
//		
//		if(balls.size() ==1)
//		{
//			balls.get(0).setStart(true);
//			balls.get(0).update();
//		}
//		else
//		{
//			
//			if(counter/90 < balls.size() && !balls.get(counter/90).getStart())
//			{
//				balls.get(counter/90).setDx(dx);
//				balls.get(counter/90).setDy(dy);
//				balls.get(counter/90).setStart(true);
//				balls.get(counter/90).update();	
//			}
//		
//		}
//		
//		if(balls.size() > 1)
//		{
//			for(int i = 0; i<balls.size() - 1; i++)
//			{
//				if(balls.get(i).getY() >= 350 && balls.get(i).getX() != balls.get(0).getX())
//				{
//					remove(balls.get(i));
//				}
//			}
//		}
//		
//		
//		for(Ball b : balls)
//		{
//			if(b.getY() == 350)
//			{
//				b.setDx(0);
//				b.setDy(0);
//				b.setStart(false);
//			}
//			
//			
//		}
//		
//		
//		
//		
//		//collision with walls
//		for(int i = 0; i < balls.size(); i++)
//		{
//		
//			if((balls.get(i)).getX() >= 770 || (balls.get(i)).getX() <= 0)
//			{
//				(balls.get(i)).setDx(-((balls.get(i)).getDx()));
//				
//			}
//			
//			if(((balls.get(balls.size() - 1)).getY() >= 350))
//			{
//				
//				t1.stop();
//				
//				//counter = 0;
//				level++;
//				
//				
//				
//				
////				for(int a = 0; a < addBalls.size(); a++)
////				{
////					addBalls.get(a).update();
////					repaint();
////					addBalls.get(a).setLocation(addBalls.get(a).getX(), addBalls.get(a).getY()+45);
////					addBalls.get(a).repaint();
////				}
//				
//			}
//			
//			if(((balls.get(i)).getY() >= 350))
//			{
//				for(Brick br : bricks)
//				{
//					br.update();
//				}
//				
//				
//				double random = 0;
//				for(int r = 1; r < 8; r++)
//				{
//					random = Math.random();
//					if(random < .5)
//					{
//						Brick brick = new Brick(level);
//						brick.setBounds((r*70), 20, 65, 40);
//						brick.setText("" + brick.getHP());
//						add(brick);
//						bricks.add(brick);
//					}
////					else if(random>=.5 && random<=.7)
////					{
////						AddBalls addB = new AddBalls((r)*70,25);
////						addBalls.add(addB);
////						add(addB);
////					}
//				}
//
//			}
//			
//			if(balls.get(i).getY() <= 0)
//			{
//				balls.get(i).setDy(-((balls.get(i)).getDy()));
//			}
//		}
//		
//		
//		
//		
//		
//		
//		
//		//checking for collision with bricks
//		for(int i = bricks.size() - 1; i >= 0; i--)
//		{
//			for(int j = 0; j < balls.size(); j++)
//			{
//
//				if(i<bricks.size() && balls.get(j).getX() >= bricks.get(i).getX() && balls.get(j).getY() >= bricks.get(i).getY() && balls.get(j).getX() <= bricks.get(i).getX() + 67 && balls.get(j).getY() <= bricks.get(i).getY() + 42)
//				{
////					balls.get(j).setDx(-(balls.get(j).getDx()));
////					balls.get(j).setDy(-(balls.get(j).getDy()));
//					bricks.get(i).updateHP();
//					bricks.get(i).setText("" + bricks.get(i).getHP());
//					if(bricks.get(i).getHP() == 0)
//					{
//						remove(bricks.get(i));
//						bricks.remove(bricks.get(i));
//						repaint();
//						
//					}
//					if(counter%2 == 0)
//					{
//						balls.get(j).setDy(-(balls.get(j).getDy()));
//					}
//					else
//					{
//						balls.get(j).setDx(-(balls.get(j).getDx()));
//					}
//				}
//			}
//		}
//		
//	
//		
//	}
//	
//	public static void main(String[] args) 
//	{
//		new BallzMain();
//	}
//
//}