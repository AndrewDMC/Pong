import java.awt.*;

public class Score extends Rectangle {
	
	private String point1;
	private String point2;
	private int width_w;
	private int height_w;
	int point1int;
	int point2int;
	private String perma1;
	private String perma2;
	
	public void update(int pointL, int pointR, int GAME_WIDTH, int GAME_HEIGHT) {
		point1int = pointL;
		point1 = Integer.toString(pointL);
		point2int = pointR;
		point2 = Integer.toString(pointR);
		width_w = GAME_WIDTH;
		height_w = GAME_HEIGHT;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", 50, 50));
		g.fillRect(x, y, height, width); 
		
		if (point2int < 12 & point1int < 12) {
			g.drawString(point1, ((width_w)-(this.width)-(200)), height_w / 7);
			g.drawString(point2, ((width_w)-(this.width)-(200)) / 2, height_w / 7);
			perma1 = point1;
			perma2 = point2;
			
			g.drawString(" ", ((width_w)-(this.width)-(500)), height_w - 600 );
			g.drawString(" ", ((width_w)-(this.width)-(500)), height_w - 500 );
			g.drawString(" ", ((width_w)-(this.width)-(500)), height_w - 400);
			g.drawString(" ", ((width_w)-(this.width)-(500)), height_w - 300);
			g.drawString(" ", ((width_w)-(this.width)-(500)), height_w - 200);
			
		} else {
			g.drawString(perma1, ((width_w)-(this.width)-(200)), height_w / 7);
			g.drawString(perma2, ((width_w)-(this.width)-(200)) / 2, height_w / 7);
			
			g.drawString("Andrea de Amicis", ((width_w)-(this.width)-(500)), height_w - 600 );
			g.drawString("Matteo Cicolini", ((width_w)-(this.width)-(500)), height_w - 500 );
			g.drawString("Lorenzo Barretta", ((width_w)-(this.width)-(500)), height_w - 400);
			g.drawString("Lorenzo Rusiello", ((width_w)-(this.width)-(500)), height_w - 300);
			g.drawString("Alessandro Rusiello", ((width_w)-(this.width)-(500)), height_w - 200);
			
		}
		//g.fillOval(x, y, height, width); 
	}
}
