//**********************************************************

import java.awt.*;
import java.util.*; //serve a Random

public class Ball extends Rectangle {

	Random random = new Random();
		
	int dx;
	int dy;
	
	int ballSpeed = 2;
	
	Ball(int x, int y, int width, int height) {
	
		super(x,y,width,height); //costruttore di Rectangle
		
		Random rand = new Random();
 
		//double vectorX = rand.nextDouble(); 
		int vectorX=rand.nextInt(2); 
		if (vectorX==0)
			vectorX=-1;
		setDX(vectorX);
		
		//double vectorY = rand.nextDouble();
		int vectorY=rand.nextInt(2);
		if (vectorY==0)
			vectorY=-1;
		setDY(vectorY);
			
	} // end costruttore ----------------------------------
	
	public void setDX(double vectorX) {
		dx = (int)(vectorX*ballSpeed);
	}
	
	public void setDY(double vectorY) {
		dy = (int)(vectorY*ballSpeed);
	}
	
	public void reset(int n) {
		
		Random rand = new Random();
		
		int vectorX = 0,vectorY = 0;
		
		if(n == 1) 
			vectorX = -1;
		if(n == 2)
			 vectorX = 1;
		
		setDX(vectorX);
		
		
		//double vectorY = rand.nextDouble();
		vectorY=rand.nextInt(2);
		if (vectorY==0)
			vectorY=-1;
		setDY(vectorY);
	}
	
	
	public void move() {	
	
		 x = x + dx;
		 y = y + dy;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, height, width); 
		//g.fillOval(x, y, height, width); 
	}
	
}