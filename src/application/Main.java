package application;

import java.io.OutputStream;

import com.sun.glass.ui.Screen;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private int[] buttons;
	private float[] axis;

	Main.InputHandler inputHandler;
	private JoystickController joystickController;

	Scene scene;
	GridPane root;

	ImageView joyLeftRight;
	ImageView joyLeftLeft;
	ImageView joyLeftTop;
	ImageView joyLeftBottom;

	ImageView joyRightRight;
	ImageView joyRightLeft;
	ImageView joyRightTop;
	ImageView joyRightBottom;
	
	ImageView Right;
	ImageView Left;
	ImageView Top;
	ImageView Bottom;

	ImageView leftStick;
	ImageView rightStick;

	ImageView lb;
	ImageView lt;
	ImageView rb;
	ImageView rt;
	
	ImageView back;
	ImageView start;
	ImageView asel;
	ImageView cross;
	
	ImageView backImage;
	ImageView frontImage;

	Circle leftJoyButton;
	Circle rightJoyButton;

	Circle F1;
	Circle F2;
	Circle F3;
	Circle F4;

	double mouseDownX = 0;
	double mouseDownY = 0;

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings({ "unused", "deprecation" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			primaryStage.setTitle("GamePad Logitech F310 Simulation");
			primaryStage.setX(Screen.getMainScreen().getX());
			primaryStage.setY(Screen.getMainScreen().getY());
			primaryStage.setWidth(Screen.getMainScreen().getWidth());
			primaryStage.setHeight(Screen.getMainScreen().getHeight());
			primaryStage.setAlwaysOnTop(false);

			buttons = new int[32];
			axis = new float[64];

			root = (GridPane) FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
			scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());
			root.setGridLinesVisible(true);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setFullScreenExitHint("");
			primaryStage.setMaximized(false);
			primaryStage.setAlwaysOnTop(false);
			primaryStage.setIconified(false);
			primaryStage.show();
			scene.setFill(Color.color(0.0, 0.0, 0.0, 0.95));

		} catch (Exception e) {
			e.printStackTrace();
		}

		ImageView exitButton = (ImageView) scene.lookup("#exitButton");

		joyLeftRight = (ImageView) scene.lookup("#leftRight");
		joyLeftLeft = (ImageView) scene.lookup("#leftLeft");
		joyLeftTop = (ImageView) scene.lookup("#leftTop");
		joyLeftBottom = (ImageView) scene.lookup("#leftBottom");

		joyRightRight = (ImageView) scene.lookup("#rightRight");
		joyRightLeft = (ImageView) scene.lookup("#rightLeft");
		joyRightTop = (ImageView) scene.lookup("#rightTop");
		joyRightBottom = (ImageView) scene.lookup("#rightBottom");

		Right = (ImageView) scene.lookup("#Right");
		Left = (ImageView) scene.lookup("#Left");
		Top = (ImageView) scene.lookup("#Top");
		Bottom = (ImageView) scene.lookup("#Bottom");
		rb = (ImageView) scene.lookup("#rb");
		rt = (ImageView) scene.lookup("#rt");
		lb = (ImageView) scene.lookup("#lb");
		lt = (ImageView) scene.lookup("#lt");
		back = (ImageView) scene.lookup("#back");
		start = (ImageView) scene.lookup("#start");
		leftStick = (ImageView) scene.lookup("#leftStick");
		rightStick = (ImageView) scene.lookup("#rightStick");
		asel = (ImageView) scene.lookup("#asel");
		cross = (ImageView) scene.lookup("#cross");
		backImage = (ImageView) scene.lookup("#backImage");
		frontImage = (ImageView) scene.lookup("#frontImage");
		

		GridPane mainGrid = (GridPane) scene.lookup("#mainGrid");

		mainGrid.setMaxWidth((Screen.getMainScreen().getWidth() / 4.7) * 3.7);
		GridPane.setMargin(mainGrid, new Insets(150, 0, 0, Screen.getMainScreen().getWidth() / 4.4));

		leftJoyButton = (Circle) scene.lookup("#leftJoyButton");
		rightJoyButton = (Circle) scene.lookup("#rightJoyButton");

		TextField passwordField = (TextField) scene.lookup("#password");
		Button passwordShow = (Button) scene.lookup("#passwordShow");
		Button enter = (Button) scene.lookup("#enter");
		passwordField.setVisible(false);
		enter.setVisible(false);

		passwordShow.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (passwordField.isVisible()) {
					passwordField.setVisible(false);
					enter.setVisible(false);

				} else {
					passwordField.setVisible(true);
					enter.setVisible(true);

				}
			}
		});
		enter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (passwordField.getText().equals("elektroland11")) {
					passwordField.setText("");

					passwordField.setVisible(false);
					enter.setVisible(false);
				} else {
					passwordField.setText("wrong");
				}
			}
		});

		Font smallFont = Font.loadFont(Main.class.getResource("/font/digital-7.ttf").toExternalForm(), 32);

		// showPanAci.setTextFill(Color.WHITE);

		F1 = (Circle) scene.lookup("#F1");
		F2 = (Circle) scene.lookup("#F2");
		F3 = (Circle) scene.lookup("#F3");
		F4 = (Circle) scene.lookup("#F4");

		exitButton.setOnMousePressed((me) -> {
			joystickController.stop();
			inputHandler.stop();
			Platform.exit();
			System.exit(0);
		});

		exitButton.setOnMouseDragged((me) -> {

		});
		exitButton.setOnMouseReleased((me) -> {
			me.consume();
		});
		exitButton.setStyle("-fx-effect: dropshadow( three-pass-box, red , 24, 0.7, 0.4, 0.4 )");

		F1.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				F1.setFill(Color.BLUE);
			}
		});
		F2.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				F2.setFill(Color.LIME);
			}
		});
		F3.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				F3.setFill(Color.RED);
			}
		});
		F4.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				F4.setFill(Color.YELLOW);
			}
		});

		leftStick.setOnMousePressed((me) -> {
			mouseDownX = me.getSceneX();
			mouseDownY = me.getSceneY();
		});

		leftStick.setOnMouseDragged((me) -> {
			double mouseDeltaX = me.getSceneX() - mouseDownX;
			double mouseDeltaY = me.getSceneY() - mouseDownY;
			if (leftStick.getTranslateY() + mouseDeltaY <= 40 && leftStick.getTranslateY() + mouseDeltaY >= -40) {
				leftStick.setTranslateY(leftStick.getTranslateY() + mouseDeltaY);
				if (leftStick.getTranslateY() + mouseDeltaY < -4) {
				}
				if (leftStick.getTranslateY() + mouseDeltaY > 4) {
				}

			}
			if (leftStick.getTranslateX() + mouseDeltaX <= 40 && leftStick.getTranslateX() + mouseDeltaX >= -40) {
				leftStick.setTranslateX(leftStick.getTranslateX() + mouseDeltaX);
				if (leftStick.getTranslateX() + mouseDeltaX < -4) {
				}
				if (leftStick.getTranslateX() + mouseDeltaX > 4) {
				}
				{
				}
			}
			mouseDownX = me.getSceneX();
			mouseDownY = me.getSceneY();

		});
		leftStick.setOnMouseReleased(me -> {
			double mouseDeltaX = me.getSceneX() - mouseDownX;
			double mouseDeltaY = me.getSceneY() - mouseDownY;
			leftStick.setTranslateY(-mouseDeltaY);
			leftStick.setTranslateX(-mouseDeltaX);
		});

		rightStick.setOnMousePressed((me) -> {
			mouseDownX = me.getSceneX();
			mouseDownY = me.getSceneY();
		});

		rightStick.setOnMouseDragged((me) -> {
			double mouseDeltaX = me.getSceneX() - mouseDownX;
			double mouseDeltaY = me.getSceneY() - mouseDownY;
			
			if (rightStick.getTranslateY() + mouseDeltaY <= 40 && rightStick.getTranslateY() + mouseDeltaY >= -40) {
				rightStick.setTranslateY(rightStick.getTranslateY() + mouseDeltaY);
				if (rightStick.getTranslateY() + mouseDeltaY < -4) {
				}
				if (rightStick.getTranslateY() + mouseDeltaY > 4) {
				}

			}
			if (rightStick.getTranslateX() + mouseDeltaX <= 40 && rightStick.getTranslateX() + mouseDeltaX >= -40) {
				rightStick.setTranslateX(rightStick.getTranslateX() + mouseDeltaX);
				if (rightStick.getTranslateX() + mouseDeltaX < -4) {
				}
				if (rightStick.getTranslateX() + mouseDeltaX > 4) {
				}
				{
				}
			}
			mouseDownX = me.getSceneX();
			mouseDownY = me.getSceneY();
		});
		rightStick.setOnMouseReleased(me -> {
			double mouseDeltaX = me.getSceneX() - mouseDownX;
			double mouseDeltaY = me.getSceneY() - mouseDownY;
			rightStick.setTranslateY(-mouseDeltaY);
			rightStick.setTranslateX(-mouseDeltaX);
		});
		joystickController = new JoystickController(buttons, axis);
		joystickController.start();
		inputHandler = new InputHandler();
		inputHandler.start();
	}

	public class InputHandler extends Thread {
		@Override
		public void run() {
			while (true) {
				if (joystickController.activeJoystick == 0
						&& joystickController.presentJoysticks[joystickController.activeJoystick] != 1) {
					backImage.setStyle("-fx-effect: dropshadow( three-pass-box, red , 12, 0.5, 0.7, 0.7 )");
					frontImage.setStyle("-fx-effect: dropshadow( three-pass-box, red , 12, 0.5, 0.7, 0.7 )");
				} else if (joystickController.presentJoysticks[joystickController.activeJoystick] == 1) {
					backImage.setStyle("-fx-effect: dropshadow( three-pass-box, cyan , 32, 0.5, 0.7, 0.7 )");
					frontImage.setStyle("-fx-effect: dropshadow( three-pass-box, cyan , 32, 0.5, 0.7, 0.7 )");
				}

				leftJoyButton.setFill(Color.DIMGRAY);
				rightJoyButton.setFill(Color.DIMGRAY);
				leftJoyButton.setStyle("");
				rightJoyButton.setStyle("");

				F1.setFill(Color.MIDNIGHTBLUE);
				F1.setStyle("");
				F2.setFill(Color.DARKGREEN);
				F2.setStyle("");
				F3.setFill(Color.DARKRED);
				F3.setStyle("");
				F4.setFill(Color.DARKORANGE);
				F4.setStyle("");

				Right.setImage(new Image("/application/nright.png"));
				Left.setImage(new Image("/application/nleft.png"));
				Top.setImage(new Image("/application/nup.png"));
				Bottom.setImage(new Image("/application/ndown.png"));
				Right.setStyle("");
				Left.setStyle("");
				Top.setStyle("");
				Bottom.setStyle("");

				rb.setStyle("");
				rt.setStyle("");
				lb.setStyle("");
				lt.setStyle("");

				asel.setStyle("");

				back.setImage(new Image("/application/buttonnormal.png"));
				start.setImage(new Image("/application/buttonnormal.png"));

				if (Main.this.axis[JoystickController.LEFT_STICK_Y] > 0.1) {
					joyLeftBottom.setImage(new Image("/application/acik_down.png"));
					joyLeftTop.setImage(new Image("/application/kapali_up.png"));
				} else if (Main.this.axis[JoystickController.LEFT_STICK_Y] < -0.1) {
					joyLeftTop.setImage(new Image("/application/acik_up.png"));
					joyLeftBottom.setImage(new Image("/application/kapali_down.png"));
				} else {
					joyLeftTop.setImage(new Image("/application/kapali_up.png"));
					joyLeftBottom.setImage(new Image("/application/kapali_down.png"));
				}

				if (Main.this.axis[JoystickController.LEFT_STICK_X] > 0.1) {
					joyLeftRight.setImage(new Image("/application/acik_right.png"));
					joyLeftLeft.setImage(new Image("/application/kapali_left.png"));
				} else if (Main.this.axis[JoystickController.LEFT_STICK_X] < -0.1) {
					joyLeftLeft.setImage(new Image("/application/acik_left.png"));
					joyLeftRight.setImage(new Image("/application/kapali_right.png"));
				} else {
					joyLeftLeft.setImage(new Image("/application/kapali_left.png"));
					joyLeftRight.setImage(new Image("/application/kapali_right.png"));
				}

				if (Main.this.axis[JoystickController.RIGHT_STICK_Y] > 0.1) {
					joyRightBottom.setImage(new Image("/application/acik_down.png"));
					joyRightTop.setImage(new Image("/application/kapali_up.png"));
				} else if (Main.this.axis[JoystickController.RIGHT_STICK_Y] < -0.1) {
					joyRightTop.setImage(new Image("/application/acik_up.png"));
					joyRightBottom.setImage(new Image("/application/kapali_down.png"));
				} else {
					joyRightTop.setImage(new Image("/application/kapali_up.png"));
					joyRightBottom.setImage(new Image("/application/kapali_down.png"));
				}

				if (Main.this.axis[JoystickController.RIGHT_STICK_X] > 0.1) {
					joyRightRight.setImage(new Image("/application/acik_right.png"));
					joyRightLeft.setImage(new Image("/application/kapali_left.png"));
				} else if (Main.this.axis[JoystickController.RIGHT_STICK_X] < -0.1) {
					joyRightLeft.setImage(new Image("/application/acik_left.png"));
					joyRightRight.setImage(new Image("/application/kapali_right.png"));
				} else {
					joyRightLeft.setImage(new Image("/application/kapali_left.png"));
					joyRightRight.setImage(new Image("/application/kapali_right.png"));
				}

				if (Main.this.joystickController.isButtonReleased(JoystickController.LEFT_STICK)) {

				}
				if (Main.this.joystickController.isButtonReleased(JoystickController.RIGHT_STICK)) {

				}

				if (Main.this.joystickController.isButtonPressed(JoystickController.X)) {
					F1.setFill(Color.BLUE);

				}
				if (Main.this.joystickController.isButtonPressed(JoystickController.Y)) {
					F4.setFill(Color.YELLOW);

				}
				if (Main.this.joystickController.isButtonPressed(JoystickController.A)) {
					F2.setFill(Color.LIME);

				}
				if (Main.this.joystickController.isButtonPressed(JoystickController.B)) {
					F3.setFill(Color.RED);

				}

				if (Main.this.joystickController.isButtonPressed(JoystickController.CROSS_RIGHT)) {
					Right.setImage(new Image("/application/bright.png"));
					Right.setStyle("-fx-effect: dropshadow( three-pass-box, MediumSpringGreen  , 10, 0.5, 0.7, 0.7 )");
				} else if (Main.this.joystickController.isButtonPressed(JoystickController.CROSS_LEFT)) {
					Left.setImage(new Image("/application/bleft.png"));
					Left.setStyle("-fx-effect: dropshadow( three-pass-box, MediumSpringGreen  , 10, 0.5, 0.7, 0.7 )");
				}
				if (Main.this.joystickController.isButtonPressed(JoystickController.CROSS_UP)) {
					Top.setImage(new Image("/application/bup.png"));
					Top.setStyle("-fx-effect: dropshadow( three-pass-box, MediumSpringGreen  , 10, 0.5, 0.7, 0.7 )");
				} else if (Main.this.joystickController.isButtonPressed(JoystickController.CROSS_DOWN)) {
					Bottom.setImage(new Image("/application/bdown.png"));
					Bottom.setStyle("-fx-effect: dropshadow( three-pass-box, MediumSpringGreen  , 10, 0.5, 0.7, 0.7 )");
				}

				if (Main.this.joystickController.isButtonPressed(JoystickController.BACK)) {
					back.setImage(new Image("/application/buttonpressed.png"));
				}

				if (Main.this.joystickController.isButtonPressed(JoystickController.START)) {
					start.setImage(new Image("/application/buttonpressed.png"));
				}
				if (Main.this.joystickController.isButtonPressed(JoystickController.SWITCH)) {

					asel.setStyle("-fx-effect: dropshadow( three-pass-box, MediumSpringGreen  , 16, 0.7, 0.7, 0.7 )");
				}
				asel.setVisible(false);

				if (Main.this.joystickController.isButtonPressed(JoystickController.RB)) {
					rb.setStyle("-fx-effect: dropshadow( three-pass-box, LawnGreen   , 16, 0.7, 0.7, 0.7 )");
				}
				if (Main.this.axis[JoystickController.RT] < 0) {
					rt.setStyle("-fx-effect: dropshadow( three-pass-box, LawnGreen   , 16, 0.7, 0.7, 0.7 )");
				}
				if (Main.this.joystickController.isButtonPressed(JoystickController.LB)) {
					lb.setStyle("-fx-effect: dropshadow( three-pass-box, LawnGreen   , 16, 0.7, 0.7, 0.7 )");
				}
				if (Main.this.axis[JoystickController.LT] > 0) {
					lt.setStyle("-fx-effect: dropshadow( three-pass-box, LawnGreen   , 16, 0.7, 0.7, 0.7 )");
				}

				if (Main.this.joystickController.isButtonPressed(JoystickController.LEFT_STICK)) {
					leftJoyButton.setStyle("-fx-effect: dropshadow( three-pass-box, LawnGreen   , 16, 0.7, 0.7, 0.7 )");
					leftJoyButton.setFill(Color.LIGHTGRAY);
				}
				if (Main.this.joystickController.isButtonPressed(JoystickController.RIGHT_STICK)) {
					rightJoyButton.setStyle("-fx-effect: dropshadow( three-pass-box, LawnGreen   , 16, 0.7, 0.7, 0.7 )");
					rightJoyButton.setFill(Color.LIGHTGRAY);
				}

				if (Main.this.joystickController.isButtonPressed(10)) {

				}

				try {
					Thread.sleep(300);
				} catch (InterruptedException ex) {

				}
			}
		}
	}
}
