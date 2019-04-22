package application;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.*;
import org.lwjgl.glfw.*;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import static org.lwjgl.glfw.GLFW.*;

public class JoystickController extends Thread {
	public static final int A = 0;
	public static final int B = 1;
	public static final int X = 2;
	public static final int Y = 3;
	public static final int LB = 4;
	public static final int RB = 5;
	public static final int BACK = 6;
	public static final int START = 7;
	public static final int SWITCH = 8;
	public static final int LEFT_STICK = 8;
	public static final int RIGHT_STICK = 9;

	public static final int LEFT_STICK_X = 0;
	public static final int LEFT_STICK_Y = 1;
	public static final int RIGHT_STICK_X = 4;
	public static final int RIGHT_STICK_Y = 3;
	public static final int LT = 2;
	public static final int RT = 2;
	public static final int CROSS_X = 6;
	public static final int CROSS_Y = 7;

	public static final int CROSS_UP = 10;
	public static final int CROSS_DOWN = 12;
	public static final int CROSS_LEFT = 13;
	public static final int CROSS_RIGHT = 11;

	public static int[] presentJoysticks;
	public static int activeJoystick;

	private ByteBuffer buttons;
	private FloatBuffer axis;

	private int buttonsArr[];
	private int buttonsTmp[];
	private float axisArr[];
	@FXML
	private ImageView backImage;

	public JoystickController(int[] buttonsArr, float[] axisArr) {
		this.buttonsArr = buttonsArr;
		this.axisArr = axisArr;

		buttons = ByteBuffer.allocate(32);
		axis = FloatBuffer.allocate(256);

		buttonsArr = new int[32];
		buttonsTmp = new int[32];
		axisArr = new float[64];

		// Joystick controller must run as daemon.
		setDaemon(true);

		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		// glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (glfwInit() == GLFW_TRUE) {
			System.out.println("GLFW Initialized: Version " + Version.getVersion());
		} else {
			System.out.println("Unable to initialize GLFW!");
		}

		activeJoystick = 0;
		presentJoysticks = new int[GLFW_JOYSTICK_LAST + 1];
		for (int i = GLFW_JOYSTICK_1; i < GLFW_JOYSTICK_LAST + 1; i++) {
			presentJoysticks[i] = 0;
		}

		for (int i = 0; i < buttonsArr.length; i++) {
			buttonsArr[i] = 0;
		}

		for (int i = 0; i < buttonsTmp.length; i++) {
			buttonsTmp[i] = 0;
		}

		for (int i = 0; i < axisArr.length; i++) {
			axisArr[i] = 0f;
		}
	}

	// Searches for connected controllers.
	private void searchForJoysticks() {
		for (int i = GLFW_JOYSTICK_1; i < GLFW_JOYSTICK_LAST + 1; i++) {
			if (glfwJoystickPresent(i) == GLFW_TRUE) {
				presentJoysticks[i] = 1;
			} else {
				presentJoysticks[i] = 0;
			}
		}
	}

	// Fetches data from active controller.
	private void getData() {
		buttons = glfwGetJoystickButtons(activeJoystick);
		axis = glfwGetJoystickAxes(activeJoystick);

		int buttonID = 0;
		while (buttons.hasRemaining()) {
			int state = buttons.get();
			if (state == GLFW_PRESS) {
				buttonsArr[buttonID] = 1;
				buttonsTmp[buttonID] = 1;
				System.out.println(buttonID);
			} else {
				buttonsArr[buttonID] = 0;
			}
			buttonID++;
		}

		int axisID = 0;
		while (axis.hasRemaining()) {
			float state = axis.get();
			if (state < -0.80f) {
				System.out.println(axisID);
				axisArr[axisID] = -1f;
			} else if (state < -0.20f) {
				System.out.println(axisID);
				axisArr[axisID] = state;
			} else if (state > 0.80f) {
				System.out.println(axisID);
				axisArr[axisID] = 1f;
			} else if (state > 0.20f) {
				axisArr[axisID] = state;
				System.out.println(axisID);
			} else {
				System.out.println(axisID);
				axisArr[axisID] = 0f;
			}

			axisID++;
		}
	}

	public void setActiveJoystick(int i) {
		this.activeJoystick = i;
	}

	public boolean isButtonPressed(int i) {
		if (buttonsArr[i] == 1)
			return true;
		return false;
	}

	public boolean isButtonReleased(int i) {
		if (buttonsArr[i] == 0 && buttonsTmp[i] == 1) {
			buttonsTmp[i] = 0;
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		try {
			while (true) {
				this.activeJoystick = 0;
				searchForJoysticks();

				if (presentJoysticks[activeJoystick] == 1) {

					getData();
				} else {
					continue;
				}

				try {
					Thread.sleep(10);
				} catch (InterruptedException ex) {
					Logger.getLogger(JoystickController.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		} finally {
			glfwTerminate();
		}
	}
}
