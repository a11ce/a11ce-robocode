package a11ce;
import robocode.*;
import robocode.BattleRules.*;
import robocode.util.Utils;

import java.awt.Point;
import java.util.Random;

import java.awt.Color;


public class Windsor_1224 extends AdvancedRobot
{

    private Point nextMove;
    private int GRID_RANGE = 200;
    private int SAFE_EDGE = 100;
    private double enemyRange = 10000;
    private int turnsSinceSeenTarget = 11;
    
    Random satan = new Random();

	public void run() {

		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		nextMove = getLoc();

		setColors(new Color(255,255, 255),
			  new Color(42, 128, 154),
			  new Color(42, 128, 154)); // body,gun,radar

		
		while(true) {
		    if(turnsSinceSeenTarget>10)
		    {
		        setTurnRadarRight(Double.POSITIVE_INFINITY);
		    }
		    System.out.println(nextMove);
		    turnsSinceSeenTarget++;
            	    scan();
		    doMove();
		    doFire();
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
				nextMove = new Point((int) getX() + (satan.nextInt(GRID_RANGE*2) - GRID_RANGE),
						     (int) getY() + (satan.nextInt(GRID_RANGE*2) - GRID_RANGE));
			} while(nextMove.getX() < SAFE_EDGE || nextMove.getX() > (getBattleFieldWidth () - SAFE_EDGE)
				||  nextMove.getY() < SAFE_EDGE || nextMove.getY() > (getBattleFieldHeight() - SAFE_EDGE));
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

	public void onScannedRobot(ScannedRobotEvent e)
	{
	    turnsSinceSeenTarget = 0;
	    enemyRange = e.getDistance();
	    double radarTurn = getHeading() + e.getBearing() - getRadarHeading();
	    double gunTurn = getHeading() + e.getBearing() - getGunHeading();
	    setTurnGunRight(normalizeBearing(gunTurn));
	    setTurnRadarRight(1.9 * normalizeBearing(radarTurn));
	}

	public void doFire()
	{
	    double firePower = Math.min(500 / enemyRange, 3);
	    if(turnsSinceSeenTarget<10)
	    {
	    	setFire(firePower);
	    }
	}

}
