//**********************************************************

import java.io.*;
import javax.sound.sampled.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;	//per utilizzare EventListner
import java.awt.image.*;  	//per utilizzare BufferedImage
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*; //per utilizzare JPanel

public class GamePanel extends JPanel implements Runnable{

	//definizione costanti
	static final int GAME_WIDTH = 700;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (1.25));
	static final Dimension SCREEN_SIZE1 = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	
	static final int PADDLE_WIDTH = 15;
	static final int PADDLE_HEIGHT = 100;  //orizzontale
	
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;  //palla rotonda
	
	
	//remarks from Mr. Alcorn:
	//The problem you noticed about the paddle not going all the way to the top 
	//was left in because without it good players could monopolize the game. 
	//Our motto was "if you can't fix it call it a feature."
	static final int BORDER_OFFSET = 20 ; 
	// il paddle non tocca i bordi superiore ed inferiore se OFFSET >0
	static final int DISTANZA = 20; // =0 i paddle sono sul bordo del campo;
	
	Thread gameThread; //Thread eseguibile
	BufferedImage buffer; //awg.image 
	Graphics graphics;
	Score score = new Score();
	
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	
	int pointL;
	int pointR;
	int hit = 0;
	
	File Boundary = new File("boundary.wav");
    File Paddle = new File("paddle.wav");
    File Point = new File("point.wav");
    
	// istanza "paddle1" dalla classe Paddle
	
	GamePanel(){ //costruttore
		Random random = new Random();
		//creo una istanza "ball" dalla classe Ball al centro dello schermo ma ad una altezza casuale
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
		
		
		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE1);
		gameThread = new Thread(this);
		gameThread.start();
		
		newPaddles();
		
		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE1);
		
		//----- aggiungi al Panel un "listner", un ascoltatore di eventi da tastiera -----
		//
		this.addKeyListener(new AL());
		//
		//--------------------------------------------------------------------------------
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void newPaddles() {
		//creo una istanza "paddle1" dalla classe Paddle
		paddle1 = new Paddle(((GAME_WIDTH)-(PADDLE_WIDTH)-(10)),((GAME_HEIGHT/2)-(PADDLE_HEIGHT-DISTANZA/2)),PADDLE_WIDTH,PADDLE_HEIGHT);
		paddle2 = new Paddle((10),((GAME_HEIGHT/2)-(PADDLE_HEIGHT-DISTANZA/2)),PADDLE_WIDTH,PADDLE_HEIGHT);

	}
	
//------------------------------- non toccare -------------------------------
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		buffer = new BufferedImage(getWidth(),getHeight(), BufferedImage.TYPE_INT_RGB);
		graphics = buffer.getGraphics();
		
		draw(graphics);
		
		g.drawImage(buffer,0,0,this);
		
	}
//----------------------------------------------------------------------------


	public void draw(Graphics g) {
		
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g); 
		score.draw(g);
		
		// disegna altri oggetti qui
		
        // the following line helps with animation ---------------------------
        Toolkit.getDefaultToolkit().sync(); 
        // This method ensures that the display is up-to-date. 
        // It is useful for animation: timing the painting operation 
        // should be performed by calling Toolkit.sync() 
        // after each paint to ensure the drawing commands 
        // are flushed to the graphics card. ---------------------------------  
	}

	public void move() {
		
		paddle1.move();
		paddle2.move();
		ball.move();
		
		// muovi altri oggetti qui
		
	}
	
	public void playSound(File Sound) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Sound));
            clip.start();

        }
        catch (Exception e) {

        }
    } //end playSound()
	

	public void checkCollision() {
		
	
		//---- stops paddles at window edges ----------------
		if(paddle1.y <= 0)
			paddle1.y = 0;

		if(paddle1.y >= GAME_HEIGHT-PADDLE_HEIGHT) 
			paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT; 
		
		if(paddle2.y <= 0)
			paddle2.y = 0;

		if(paddle2.y >= GAME_HEIGHT-PADDLE_HEIGHT) 
			paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT; 
		
		if(ball.y <=0) {
			ball.dy= -ball.dy;
			playSound(Boundary);
		}

		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
			ball.dy= -ball.dy;
			playSound(Boundary);
		
		}
		
		//----- la palla rimbalza quando tocca i margini destro e sinistro della finestra ------
		if(ball.x <=0) {
			ball.dx= -ball.dx;
			pointL = pointL + 1;
			System.out.println("Sinistra toccato");
			System.out.println(pointL + " - " + pointR);
			
			if (pointL < 12 && pointR < 12) {
				ball.move(GAME_WIDTH/2, GAME_HEIGHT/2);
				
				if (pointL != 12 && pointR != 12) {
					
					newPaddles();
					hit = 0;
					ball.reset(1);
				}
			}
			
			playSound(Point);
			score.update(pointL, pointR, GAME_WIDTH, GAME_HEIGHT);
			
		}

		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			
			ball.dx= -ball.dx;	
			pointR = pointR + 1;
			System.out.println("Destra toccato");
			System.out.println(pointL + " - " + pointR);
			
			if (pointL < 12 && pointR < 12) {
				ball.move(GAME_WIDTH/2, GAME_HEIGHT/2);
				
				if (pointL != 12 && pointR != 12) {
					
					newPaddles();
					hit = 0;
					ball.reset(2);
				}
			}
			
			playSound(Point);
			score.update(pointL, pointR, GAME_WIDTH, GAME_HEIGHT);
			
		}
		
		
		
		if (ball.intersects(paddle1) && (pointR < 11 && pointL < 11)) {
			
			 	ball.dx = -ball.dx;
			 
	            hit++;
	            if(hit == 4) {


	                ball.setDX(1.6);

	            }
	            if(hit == 12)


	                ball.setDX(2.0);

	            double can;
	            if((ball.y+(BALL_DIAMETER/2))<=paddle1.y+PADDLE_HEIGHT/8) can=1.6;
	            else{
	                if((ball.y+(BALL_DIAMETER/2))<=paddle1.y+(PADDLE_HEIGHT/8)*2) can=1.4;
	                else{
	                    if((ball.y+(BALL_DIAMETER/2))<=paddle1.y+(PADDLE_HEIGHT/8)*3) can=0.7;
	                    else{
	                        if((ball.y+(BALL_DIAMETER/2))<=paddle1.y+(PADDLE_HEIGHT/8)*5) can=0;
	                        else{
	                            if((ball.y+(BALL_DIAMETER/2))<=paddle1.y+(PADDLE_HEIGHT/8)*6) can=-0.7;
	                            else{
	                                if((ball.y+(BALL_DIAMETER/2))<=paddle1.y+(PADDLE_HEIGHT/8)*7) can=-1.4;
	                                else can=-1.6;
	                            }
	                        }
	                    }
	                }
	            }
	            ball.setDY(can);
	            
	            playSound(Paddle);

			
		}
		
		if (ball.intersects(paddle2) && (pointR < 11 && pointL < 11)) {
			
			ball.dx= -ball.dx;
            hit++;

            if(hit == 4) {


                ball.setDX(1.6);

            }
            if(hit == 12)

                ball.setDX(2.0);


            double can;
            if((ball.y+(BALL_DIAMETER/2))<=paddle2.y+PADDLE_HEIGHT/8) can=1.6;
            else{
                if((ball.y+(BALL_DIAMETER/2))<=paddle2.y+(PADDLE_HEIGHT/8)*2) can=1.4;
                else{
                    if((ball.y+(BALL_DIAMETER/2))<=paddle2.y+(PADDLE_HEIGHT/8)*3) can=0.7;
                    else{
                        if((ball.y+(BALL_DIAMETER/2))<=paddle2.y+(PADDLE_HEIGHT/8)*5) can=0;
                        else{
                            if((ball.y+(BALL_DIAMETER/2))<=paddle2.y+(PADDLE_HEIGHT/8)*6) can=-0.7;
                            else{
                                if((ball.y+(BALL_DIAMETER/2))<=paddle2.y+(PADDLE_HEIGHT/8)*7) can=-1.4;
                                else can=-1.6;
                            }
                        }
                    }
                }
            }
            ball.setDY(can);

            playSound(Paddle);

		}
		//---------------------------------------------------
		
	
	} // end checkCollision()

	public void run() { //game loop

		long lastTime = System.nanoTime();
		double amountOfFPS = 60.0; // frames in 1 second
		double duration = 1000000000/amountOfFPS; //interval (time in ns) beetween 2 frames
		double delta = 0;
		
		score.update(pointL, pointR, GAME_WIDTH, GAME_HEIGHT);

		while(true) { //per sempre
			long now = System.nanoTime();
			delta += (now -lastTime)/duration; // tempo trascorso è > intervallo? se sì, incrementa delta
			lastTime = now;

			if(delta >=1) {
			
				move();  //calls move() method for paddle1...
				checkCollision(); //checks collisions of paddles and boundary
			
				repaint(); //is used to tell a component (gamepanel) to repaint itself.
				paddle1.update(pointL, pointR);
				paddle2.update(pointL, pointR);
				delta--;
			} //end if
		} //end while
		
	} //end run()
	
	
	public class AL extends KeyAdapter{  
		//l’Adapter è un Listner che implementa tutte le funzioni {}
		//
		//KeyAdapter implementa i 3 metodi:
		//KeyPressed, KeyTyped, KeyReleased 
		//di KeyLisner senza che l'utente debba ridefinirli tutti
		//l’utente implementa solo quelli che usa

		//questo metodo SPOSTA il paddle quando il tasto è premuto
		public void keyPressed(KeyEvent e) {
			
			//paddle1.keyPressed(e);
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				paddle1.setDeltaY(-1);
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				paddle1.setDeltaY(+1);
			}
			if(e.getKeyCode()==KeyEvent.VK_W) {
				paddle2.setDeltaY(-1);
			}
			
			if(e.getKeyCode()==KeyEvent.VK_S) {
				paddle2.setDeltaY(+1);
			}
			
			
		}
		
		//questo metodo FERMA il paddle rilasciando il tasto, azzerando il DeltaX
		public void keyReleased(KeyEvent e) {
			
			//paddle1.keyReleased(e);
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				paddle1.setDeltaY(0);
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				paddle1.setDeltaY(0);
			}
			
			if(e.getKeyCode()==KeyEvent.VK_W) {
				paddle2.setDeltaY(0);
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				paddle2.setDeltaY(0);
			}
		
		}
	
	} //end public class AL
	
} //end GamePanel