//**********************************************************

import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle {


	int id;
	int dy;
	int paddleSpeed = 8;
	int point1, point2;

	Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT){
	
		super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT); //costruttore di Rectangle
				
	}

	public void setDeltaY(int yDirection) { 
		dy = yDirection*paddleSpeed;
	}

	public void move() {
		y = y + dy;
	}

	public void draw(Graphics g) {
		if (point1 < 11 && point2 < 11) {
			g.setColor(Color.white); //colore paddle1
		} else {
			g.setColor(Color.black); //colore paddle1
		}
		
		g.fillRect(x, y, width, height);
	}
	
	public void update(int pointL, int pointR) {
		point1 = pointL;
		point2 = pointR;
	}
	
	
}