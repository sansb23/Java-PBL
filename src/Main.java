import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.videoio.VideoCapture;

public class Main extends Application {

    static {
        // Updated to match OpenCV 4.12.0 
        System.load("C:\\opencv\\build\\java\\x64\\opencv_java4120.dll");
    }

    private VideoCapture camera;

    @Override
    public void start(Stage stage) {
        camera = new VideoCapture(0);
        
        ImageView cameraView = new ImageView();
        DrawingModule drawingModule = new DrawingModule(640, 480);
        
        StackPane root = new StackPane(cameraView, drawingModule.getCanvas());
        Scene scene = new Scene(root, 640, 480);

        // Demo Feature: Press 'C' to clear the drawing
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.C) {
                drawingModule.clearCanvas();
            }
        });

        stage.setTitle("AirDraw - Demo Mode (Press 'C' to Clear)");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Mat frame = new Mat();
                if (camera.read(frame)) {
                    // Mirror the frame so moving  hand right moves the cursor right
                    Core.flip(frame, frame, 1); 

                    // Get detection point from ObjectDetection
                    Point markerPos = ObjectDetection.detectColoredMarker(frame);

                    // Update UI
                    cameraView.setImage(CameraModule.matToImage(frame));

                    if (markerPos != null) {
                        drawingModule.updatePoints((int)markerPos.x, (int)markerPos.y);
                    } else {
                        drawingModule.resetTracking();
                    }
                }
                frame.release();
            }
        };
        timer.start();

        stage.setOnCloseRequest(e -> {
            timer.stop();
            camera.release();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}