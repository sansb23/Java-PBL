// src/ObjectDetection.java
import org.opencv.core.*;
import org.opencv.imgproc.Moments;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class ObjectDetection extends Application {

    static {
        System.load("C:\\opencv\\build\\java\\x64\\opencv_java490.dll");
    }

    @Override
    public void start(Stage stage) {
        ImageView imageView = new ImageView();
        StackPane root = new StackPane(imageView);
        stage.setScene(new Scene(root, 640, 480));
        stage.setTitle("AirDraw - Object Detection");
        stage.show();

        VideoCapture camera = new VideoCapture(0);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Mat frame = new Mat();
                if (camera.read(frame)) {
                    Mat result = detectColoredMarker(frame);
                    imageView.setImage(CameraModule.matToImage(result));
                }
            }
        };
        timer.start();

        stage.setOnCloseRequest(e -> {
            timer.stop();
            camera.release();
        });
    }

    public static Mat detectColoredMarker(Mat frame) {
        // (a) Convert BGR → HSV
        Mat hsv = new Mat();
        Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);

        // (b) Apply color filter — these values detect RED
        // Tune these HSV values for your marker color
        Scalar lowerRed = new Scalar(0, 120, 70);
        Scalar upperRed = new Scalar(10, 255, 255);
        Mat mask = new Mat();
        Core.inRange(hsv, lowerRed, upperRed, mask);

        // (c) Detect object — find contours in the mask
        java.util.List<MatOfPoint> contours = new java.util.ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(mask, contours, hierarchy,
            Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // (d) Draw a dot on the largest detected contour
        if (!contours.isEmpty()) {
            MatOfPoint largest = contours.stream()
                .max((a, b) -> (int)(Imgproc.contourArea(a) - Imgproc.contourArea(b)))
                .get();

            Moments moments = Imgproc.moments(largest);
            if (moments.get_m00() != 0) {
                int cx = (int)(moments.get_m10() / moments.get_m00());
                int cy = (int)(moments.get_m01() / moments.get_m00());

                // Draw dot on the marker center
                Imgproc.circle(frame, new Point(cx, cy), 15,
                    new Scalar(0, 255, 0), -1); // green filled dot
                System.out.println("Marker at: (" + cx + ", " + cy + ")");
            }
        }

        return frame;
    }

    public static void main(String[] args) {
        launch(args);
    }
}