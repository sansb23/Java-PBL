import org.opencv.core.*;
import org.opencv.imgproc.Moments;
import org.opencv.imgproc.Imgproc;

public class ObjectDetection {

    // returns a Point for the Main class to use for drawing
    public static Point detectColoredMarker(Mat frame) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);

        // HSV values for RED marker
        Scalar lowerRed = new Scalar(0, 120, 70);
        Scalar upperRed = new Scalar(10, 255, 255);
        
        Mat mask = new Mat();
        Core.inRange(hsv, lowerRed, upperRed, mask);

        java.util.List<MatOfPoint> contours = new java.util.ArrayList<>();
        Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        if (!contours.isEmpty()) {
            // Find the largest red object in view
            MatOfPoint largest = contours.stream()
                .max((a, b) -> Double.compare(Imgproc.contourArea(a), Imgproc.contourArea(b)))
                .get();

            // Ignore very small objects (noise)
            if (Imgproc.contourArea(largest) > 500) {
                Moments moments = Imgproc.moments(largest);
                int cx = (int)(moments.get_m10() / moments.get_m00());
                int cy = (int)(moments.get_m01() / moments.get_m00());
                return new Point(cx, cy);
            }
        }
        return null; 
    }
}