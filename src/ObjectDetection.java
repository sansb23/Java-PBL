import org.opencv.core.*;
import org.opencv.imgproc.Moments;
import org.opencv.imgproc.Imgproc;

public class ObjectDetection {

    private static double prevX = -1;
    private static double prevY = -1;

    private static int stableFrames = 0;

    public static Point detectColoredMarker(Mat frame) {

        Mat hsv = new Mat();
        Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);

        Scalar lowerRed1 = new Scalar(0, 140, 100);
        Scalar upperRed1 = new Scalar(10, 255, 255);

        Scalar lowerRed2 = new Scalar(170, 140, 100);
        Scalar upperRed2 = new Scalar(180, 255, 255);

        Mat mask1 = new Mat();
        Mat mask2 = new Mat();
        Mat mask = new Mat();

        Core.inRange(hsv, lowerRed1, upperRed1, mask1);
        Core.inRange(hsv, lowerRed2, upperRed2, mask2);

        Core.add(mask1, mask2, mask);

        Imgproc.GaussianBlur(mask, mask, new Size(5, 5), 0);

        Imgproc.erode(mask, mask, new Mat(), new Point(-1, -1), 1);
        Imgproc.dilate(mask, mask, new Mat(), new Point(-1, -1), 1);

        java.util.List<MatOfPoint> contours = new java.util.ArrayList<>();

        Imgproc.findContours(
            mask,
            contours,
            new Mat(),
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_SIMPLE
        );

        if (!contours.isEmpty()) {

            MatOfPoint largest = contours.stream()
                .max((a, b) ->
                    Double.compare(
                        Imgproc.contourArea(a),
                        Imgproc.contourArea(b)
                    )
                )
                .get();

            double area = Imgproc.contourArea(largest);

            if (area > 900) {

                Moments moments = Imgproc.moments(largest);

                if (moments.get_m00() != 0) {

                    double cx = moments.get_m10() / moments.get_m00();
                    double cy = moments.get_m01() / moments.get_m00();

                    if (prevX == -1) {
                        prevX = cx;
                        prevY = cy;
                    }

                    cx = (prevX * 0.75) + (cx * 0.25);
                    cy = (prevY * 0.75) + (cy * 0.25);

                    prevX = cx;
                    prevY = cy;

                    stableFrames++;

                    if (stableFrames < 2) {
                        return null;
                    }

                    return new Point(cx, cy);
                }
            }
        }

        stableFrames = 0;

        return null;
    }
}