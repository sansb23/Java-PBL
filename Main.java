import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.videoio.VideoCapture;

public class Main extends Application {

    static {
        // Double-check this path matches your OpenCV installation
        System.load("C:\\opencv\\build\\java\\x64\\opencv_java490.dll");
    }

    private VideoCapture camera;

    @Override
    public void start(Stage stage) {
        camera = new VideoCapture(0);
        
        // Layer 1: The Camera Feed
        ImageView cameraView = new ImageView();
        
        // Layer 2: The Drawing Canvas (Transparent)
        DrawingModule drawingModule = new DrawingModule(640, 480);
        
        // Stack them: Camera is background, Canvas is foreground
        StackPane root = new StackPane(cameraView, drawingModule.getCanvas());

        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("AirDraw - Alpha Phase (No UI)");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Mat frame = new Mat();
                if (camera.read(frame)) {
                    Core.flip(frame, frame, 1); // Mirror for natural feel

                    // Get detection point
                    Point markerPos = ObjectDetection.detectColoredMarker(frame);

                    // Render camera frame
                    cameraView.setImage(CameraModule.matToImage(frame));

                    // Render drawing
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