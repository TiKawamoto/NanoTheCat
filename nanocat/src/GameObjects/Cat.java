package GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Cat {

	private Vector2 position;
	private Vector2 velocity;
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
	}

	public void update(float delta) {

		jumpLogic(delta);

	}

	public void jumpLogic(float delta) {
		// Detect Regular Jump + Hold
		if (Gdx.input.isTouched()) {
			if (jump) {
				if (ySpeed > 750) {
					jump = false;
				} else if(!dblJump && !jumped) {
					ySpeed = ySpeed + 100;
					System.out.println("Touched - " + ySpeed + " " + jump);
				}
			}
		}
		// Detect Double Jump
		if (dblJump && !dblJumped) {
			ySpeed = 500;
			yGrav = -1;
			dblJumped = true;
			jumped = true;
		} else {
			dblJump = false;
		}
		// System.out.println(yPos);
		// Detect if on ground
		if (yPos <= 100 && ySpeed <= 0) {
			yPos = 100;
			position.y = yPos;
			ySpeed = 0;
			dblJumped = false;
			yGrav = -1;

		} else { // Not on Ground

			if (ySpeed == 0) { // if at apex in mid air
				yGrav = -1;
			} else { // if jumping
				yGrav -= 2;
			}
			// Scale gravity accel
			ySpeed += yGrav;
			yPos += ySpeed;
			// if on ground
			if (yPos <= 100) {
				yPos = 100;
				position.y = yPos;
				jumped = false;
			}
		}// END Gravity

		velocity.set(0, ySpeed);
		position.add(velocity.cpy().mul(delta));
		// position.set(xPos, yPos);

	}

	public Vector2 getPosition() {
		return position;
	}

	public void jump() {
		if (!jump && yPos > 100 && !dblJump) {
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

}
