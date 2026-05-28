import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class DrawingModule {

    private Canvas canvas;

    private GraphicsContext gc;

    private Color brushColor = Color.CYAN;

    private double brushSize = 6;

    private double lastX = -1;
    private double lastY = -1;

    public DrawingModule(double width, double height) {

        this.canvas = new Canvas(width, height);

        this.gc = canvas.getGraphicsContext2D();

        gc.setStroke(brushColor);

        gc.setLineWidth(brushSize);

        gc.setLineCap(StrokeLineCap.ROUND);
    }

    public void updatePoints(int cx, int cy) {

        if (lastX != -1 && lastY != -1) {

            gc.strokeLine(lastX, lastY, cx, cy);
        }

        lastX = cx;
        lastY = cy;
    }

    public void resetTracking() {

        lastX = -1;
        lastY = -1;
    }

    public void clearCanvas() {

        gc.clearRect(
            0,
            0,
            canvas.getWidth(),
            canvas.getHeight()
        );
    }

    public Canvas getCanvas() {

        return canvas;
    }

    public WritableImage snapshotCanvas() {

        WritableImage image = new WritableImage(
            (int) canvas.getWidth(),
            (int) canvas.getHeight()
        );

        canvas.snapshot(null, image);

        return image;
    }

    public void setBrushColor(Color color) {

        this.brushColor = color;

        gc.setStroke(color);
    }

    public void setBrushSize(double size) {

        this.brushSize = size;

        gc.setLineWidth(size);
    }
}