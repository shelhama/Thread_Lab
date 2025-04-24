import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
   This component draws two car shapes.
*/
public class CarPanel extends JComponent
{  
	private Car car1;
	private int x,y, delay;
	private CarQueue carQueue;
	private int direction;
	
	CarPanel(int x1, int y1, int d, CarQueue queue)
	{
		delay = d;
        x=x1;
        y=y1;
        car1 = new Car(x, y, this);
        carQueue = queue;
	}
	public void startAnimation()
	{
	    class AnimationRunnable implements Runnable
	    {
	        public void run()
	        {
	            int currentDirection = direction; // Track current direction from queue

	            try
	            {
	                for (int i = 0; i < 50; i++) // Run more times for animation
	                {
	                    Integer newDirection = carQueue.deleteQueue();

	                    if (newDirection != null) {
	                        currentDirection = newDirection;
	                    }

	                    // Move according to current direction
	                    switch (currentDirection)
	                    {
	                        case 0: y -= 10; break; // up
	                        case 1: y += 10; break; // down
	                        case 2: x += 10; break; // right
	                        case 3: x -= 10; break; // left
	                    }

	                    // Reverse direction if boundary is hit
	                    if (x < 0) {
	                        x = 0;
	                        currentDirection = 2; // change to right
	                    }
	                    if (x > getParent().getWidth() - 60) {
	                        x = getParent().getWidth() - 60;
	                        currentDirection = 3; // change to left
	                    }
	                    if (y < 0) {
	                        y = 0;
	                        currentDirection = 1; // change to down
	                    }
	                    if (y > getParent().getHeight() - 30) {
	                        y = getParent().getHeight() - 30;
	                        currentDirection = 0; // change to up
	                    }

	                    repaint();
	                    Thread.sleep(delay * 1000);
	                }
	            }
	            catch (InterruptedException exception)
	            {
	                exception.printStackTrace();
	            }
	        }
	    }

	    Runnable r = new AnimationRunnable();
	    Thread t = new Thread(r);
	    t.start();
	}

	
   public void paintComponent(Graphics g)
   {  
      Graphics2D g2 = (Graphics2D) g;

      car1.draw(g2,x,y);    
   }
}