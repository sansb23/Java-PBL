import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import javafx.scene.image.*;
import java.nio.ByteBuffer;

public class CameraModule {

    public static WritableImage matToImage(Mat mat) {

        Mat converted = new Mat();

        Imgproc.cvtColor(mat, converted, Imgproc.COLOR_BGR2RGB);

        int width = converted.cols();
        int height = converted.rows();

        byte[] data = new byte[width * height * 3];

        converted.get(0, 0, data);

        WritableImage image = new WritableImage(width, height);

        PixelWriter pw = image.getPixelWriter();

        pw.setPixels(
            0,
            0,
            width,
            height,
            PixelFormat.getByteRgbInstance(),
            data,
            0,
            width * 3
        );

        return image;
    }
}