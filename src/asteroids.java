import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
class Missile {
	int x;
	int y;
 
	Missile() {
		Random R1 = new Random();
        // Generates a random number in range -10 to 10, both inclusive.
        // nextInt(21) generates a range of 0 to 20, both inclusive.
        // subtracting 10 we get desired range of -10 to 10.
		this.x = R1.nextInt(21);
		this.y = 10;
	}
 
	int getX() {
		return this.x;
	}
 
	int getY() {
		return this.y;
	}
 
    // update() function takes input from the user.
    // It returns true if the input is valid, false if invalid.
	boolean update(int key) {
 
 
		switch(key) {
			case KeyEvent.VK_NUMPAD8:
			this.y -= 2;
			break;
 
			case KeyEvent.VK_NUMPAD2:
			this.y += 2;
			break;
 
			case KeyEvent.VK_NUMPAD4:
			this.x -= 2;
			break;
 
			case KeyEvent.VK_NUMPAD6:
			this.x += 2;
			break;
 
			case KeyEvent.VK_NUMPAD7:
			this.y -= 1;
			this.x -= 1;
			break;
 
			case KeyEvent.VK_NUMPAD9:
			this.y -= 1;
			this.x += 1;
			break;
 
			case KeyEvent.VK_NUMPAD1:
			this.y += 1;
			this.x -= 1; 
			break;
 
			case KeyEvent.VK_NUMPAD3:
			this.x += 1;
			this.y += 1;
			break;
 
			default:
			System.out.println("\nInvalid Option. Please try again.\n");
            return false; // Invalid choice
		}
        return true;
	}
 
    // Checks if Missile is out of range. i.e. x < -10 or x > 10
    boolean outOfRange() {
        return (this.x < 0 && this.x > 20);
    }
 
    // Checks if Missile hit the ground. i.e. y < 0
    boolean hitGround() {
        return (this.y > 10);
    }
}
 
// Asteroid class which updates the movement of asteroid in every iteration.
class Asteroid {
	int x,y;
	boolean left;
 
	Asteroid() {
		Random R1 = new Random();
        // Generate a boolean to see to start from left or right.
		this.left = R1.nextBoolean();
 
		if(this.left) { // Start from left.
			this.x= 5;
			this.y= 0;
		}
		else { // Start from right.
			this.x = 15;
			this.y = 0;
		}
	}
 
    // Move the asteroid towards the ground.
	void update() {
		if(this.left) // Move towards right
			this.x += 1;
		else // Move towards left
			this.x -= 1;
 
        // Move down
		this.y += 1;
	}
 
	int getX() {
		return this.x;
	}
 
	int getY() {
		return this.y;
	}
 
    // Checks whether the asteroid hit the ground
	boolean hit() {
		return y > 10;
	}
}
 
 
class DOTP extends JFrame implements KeyListener {
 
	static int scale = 20;
	static int width = 22;
	static int height = 12;
	static Missile m;
	static Asteroid a;
	static DrawPanel p1;
 
 
	DOTP() {
		this.setSize(width*scale, height*scale);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
    
        initGame();
		p1 = new DrawPanel(m, a, scale);
		p1.setPreferredSize(new Dimension(width*scale, height*scale));
		p1.setBackground(Color.BLACK);
		this.setContentPane(p1);
        this.addKeyListener(this);
 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Asteroids!");
        pack();
		this.setVisible(true);	
 
	}
 
	static void initGame() {
	// Initialize Missile and Asteroid via Constructors
		m = new Missile();
		a = new Asteroid();
 
        // Print the initial values
		System.out.println("\nInitial Values:");
		System.out.println("Asteroid: "+a.getX()+" "+a.getY());
		System.out.println("Missile: "+m.getX()+" "+m.getY()+"\n");
	}
 
	static void update(int key) {
		boolean updated = m.update(key);
 	        if(updated) 
                a.update();
 
		if(m.getX() == a.getX() && m.getY() == a.getY()) { 
			System.out.println("\nYou win!!\nThe Asteroid is destroyed.");
			p1.setMissileColor(Color.GREEN);
			p1.update(m, a, scale);
			return;
		}			
 
		if(a.hit()) { // The asteroid hit the ground
			System.out.println("\nThe Asteroid hit the ground.\nYou lose!!\n");
			p1.setMissileColor(Color.MAGENTA);
			p1.setAsteroidColor(Color.MAGENTA);
			p1.update(m, a, scale);
			return;
		}

    	if(m.hitGround()) { // The missile hit the ground.
        	System.out.println("\nThe Missile hit the ground.\nYou lose!!\n");
        	p1.setMissileColor(Color.MAGENTA);
			p1.setAsteroidColor(Color.MAGENTA);
			p1.update(m, a, scale);
			return;
    	}

    	if(m.outOfRange()) { // The missile goes out of range.
        	System.out.println("\nThe Missile went out of range.\nThe asteroid hit the ground.\nYou lose!!\n");
        	p1.setMissileColor(Color.MAGENTA);
			p1.setAsteroidColor(Color.MAGENTA);
			p1.update(m, a, scale);
			return;
    	}

    	// Print the updated co-ordinates of Missile and Asteroid.
		p1.update(m, a, scale);
		System.out.println("\nAsteroid: "+a.getX()+" "+a.getY());
		System.out.println("Missile: "+m.getX()+" "+m.getY()+"\n");
	}
 
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		update(key);
	}
 
	public void keyReleased(KeyEvent e) {
	}
 
	public void keyTyped(KeyEvent e) {
	}	
 
	public static void main(String args[]) {
		new DOTP();
	}
}
 
class DrawPanel extends JPanel {
	Missile m;
	Asteroid a;
	int scale;
	Color aColor = Color.RED;
	Color mColor = Color.CYAN;
 
	DrawPanel(Missile m, Asteroid a, int scale) {
        super();
        this.m = m;
        this.a = a;
        this.scale = scale;
	}

	void setMissileColor(Color color) {
		this.mColor = color;
	}

	void setAsteroidColor(Color color) {
		this.aColor = color;
	}
 
	@Override
	protected void paintComponent(Graphics g) {
 
		super.paintComponent(g);
 
		g.setColor(aColor);
		g.fillRect((a.getX())*scale, a.getY()*scale, scale, scale);
 
		g.setColor(mColor);
		g.fillRect((m.getX())*scale, m.getY()*scale, scale, scale);

        System.out.println(a.getX()*scale+" "+a.getY()*scale);
 
	}
 
	void update(Missile m, Asteroid a, int scale) {
		this.m = m;
		this.a = a;
		this.scale = scale;
		this.repaint();
	}
 
}