import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class DrawingModule {
    private Canvas canvas;
    private GraphicsContext gc;
    private double lastX = -1, lastY = -1;

    public DrawingModule(double width, double height) {
        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        
        // Neon Cyan for high visibility
        gc.setStroke(Color.CYAN); 
        gc.setLineWidth(6);
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
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public Canvas getCanvas() {
        return canvas;
    }
}