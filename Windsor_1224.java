package a11ce;
import robocode.*;
import robocode.BattleRules.*;

import java.awt.Point;
import java.util.Random;

import java.awt.Color;


public class Windsor_1224 extends AdvancedRobot
{

    private Point nextMove;
	private int GRID_RANGE = 200;	

	Random satan = new Random();

	public void run() {
		
		nextMove = getLoc();

		setColors(
					new Color(255,255, 255),
					new Color(42, 128, 154),
					new Color(42, 128, 154)
				); // body,gun,radar

		
		while(true) {
		
			System.out.println(nextMove);
			setTurnRight(100);
			doMove();
			execute();
		}
	}

	private Point getLoc()
	{
		return new Point((int)getX(),(int)getY());
	}

    private void doMove()
    {
        if(nextMove.distance(getLoc()) < GRID_RANGE);
		{
			do {
			nextMove = new Point(   (int) getX() + (satan.nextInt(GRID_RANGE*2) - GRID_RANGE),
									(int) getY() + (satan.nextInt(GRID_RANGE*2) - GRID_RANGE)
								);
			} while(nextMove.getX() < 0 || nextMove.getX() > getBattlefieldWidth ()
				||  nextMove.getY() < 0 || nextMove.getY() > getBattlefieldHeight()
				);
		}
        double absDeg = absoluteBearing(getLoc(), nextMove);
		setTurnRight(1* normalizeBearing(absDeg - getHeading()));
		setAhead(nextMove.distance(getLoc()) / 10);
  	}
	
	private double normalizeBearing(double angle)
	{
		while(angle> 180){angle -= 360;}
		while(angle<-180){angle += 360;}
		return angle;
		
	}
	
	private double absoluteBearing(Point p1, Point p2)
	{
		double xo = p2.getX() - p1.getX();
		double yo = p2.getY() - p1.getY();
		double hyp = p1.distance(p2);
		double arcSin = Math.toDegrees(Math.asin(xo/hyp));
		double bearing = 0;
		
		     if(xo > 0 && yo > 0) { bearing = arcSin; }
		else if(xo < 0 && yo > 0) { bearing = 360 + arcSin; }
		else if(xo > 0 && yo < 0) { bearing = 180 - arcSin; }
		else if(xo < 0 && yo < 0) { bearing = 180 - arcSin; }
		
		return bearing;
	}

}
