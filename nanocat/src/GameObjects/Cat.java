package GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Cat {

	private Vector2 position;
	private Vector2 velocity;
	private Rectangle bounds;
	private float yGrav = -1;
	private float ySpeed = 0;
	private float yPos = 200;
	private float xPos = 200;
	private boolean jump = false;
	private boolean jumped = false;
	private boolean dblJump = false;
	private boolean dblJumped = false;
	private int width;
	private int height;

	public Cat(float x, float y) {
		velocity = new Vector2(0, ySpeed);
		position = new Vector2(xPos, yPos);
		width = 200;
		height = 100;
		bounds = new Rectangle(position.x, position.y, width, height);
	}

	public void update(float delta) {

		jumpLogic(delta);
	

	}

	public void jumpLogic(float delta) {
		// Detect Regular Jump + Hold -------------------------
		if (Gdx.input.isTouched()) {
			if (jump) {
				if (ySpeed > 750) {
					jump = false;
				} else if(!dblJump && !jumped) {
					ySpeed = ySpeed + 100;
					
				}
			}
		}
		// Detect Double Jump -------------------------
		if (dblJump && !dblJumped) {
			ySpeed = 500;
			yGrav = -1;
			dblJumped = true;
			jumped = true;
		} else {
			dblJump = false;
		}

		// Detect if on ground -------------------------
		if (ySpeed == 0) {
			//yPos = 100;
			//position.y = yPos;
			//ySpeed = 0;
			dblJumped = false;
			yGrav = -1;

		} else { // Not on Ground -------------------------

			if (ySpeed == 0) { // if at apex in mid air
				yGrav = -1;
			} else { // if jumping
				yGrav -= 2;
			}
			// Scale gravity acceleration
			ySpeed += yGrav;
			yPos += ySpeed;
			// if on ground -------------------------
			if (ySpeed <= 0) {
				System.out.println(ySpeed);
				jumped = false;
				dblJump = false;
			}
					
		}// END Gravity
		
		//Terminal Velocity -------------------------
		if(ySpeed <= -1000){
			ySpeed = -1000;
		}
		if(yGrav < -100){
			yGrav = -100;
		}
		
		System.out.println(ySpeed + " + "+yGrav);
		velocity.set(0, ySpeed);
		position.add(velocity.cpy().mul(delta));
		bounds.set(this.position.x, this.position.y, this.width, this.height);
		setBounds();

	}//END JUMPLOGIC
	

	public Vector2 getPosition() {
		return position;
	}

	public void jump() {
		if (!jump && ySpeed != 0 && !dblJump) {
			System.out.println("DBLJUMP");
			dblJump = true;
		}
		jump = true;
		System.out.println("JUMP");
	}
	public void noJump(){
		jump = false;
		
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public Rectangle getBounds(){
		return bounds;
	}
	public void setBounds(){
		this.bounds = bounds;
		
	}
	public void setY(float setY){
		this.position.y = setY;
		bounds.set(this.position.x, this.position.y, this.width, this.height);
		setBounds();
	}

}
