// src/CameraModule.java
import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.nio.ByteBuffer;

public class CameraModule extends Application {

    static {
        // Load the OpenCV native DLL - update path if needed
        System.load("C:\\opencv\\build\\java\\x64\\opencv_java490.dll");
    }

    private VideoCapture camera;
    private ImageView imageView;

    @Override
    public void start(Stage stage) {
        // Setup JavaFX window
        imageView = new ImageView();
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("AirDraw - Camera Feed");
        stage.setScene(scene);
        stage.show();

        // Open webcam (0 = default camera)
        camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("Error: Camera not found!");
            return;
        }

        // AnimationTimer keeps updating the frame ~60fps
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Mat frame = new Mat();
                if (camera.read(frame)) {
                    imageView.setImage(matToImage(frame));
                }
            }
        };
        timer.start();

        // Release camera when window is closed
        stage.setOnCloseRequest(e -> {
            timer.stop();
            camera.release();
        });
    }

    // Convert OpenCV Mat → JavaFX Image
    public static WritableImage matToImage(Mat mat) {
        Mat converted = new Mat();
        Imgproc.cvtColor(mat, converted, Imgproc.COLOR_BGR2RGB);
        int width = converted.cols();
        int height = converted.rows();
        byte[] data = new byte[width * height * 3];
        converted.get(0, 0, data);
        WritableImage image = new WritableImage(width, height);
        PixelWriter pw = image.getPixelWriter();
        pw.setPixels(0, 0, width, height,
            PixelFormat.getByteRgbInstance(),
            data, 0, width * 3);
        return image;
    }

    public static void main(String[] args) {
        launch(args);
    }
}