import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawingModule {
    private Canvas canvas;
    private GraphicsContext gc;
    private double lastX = -1, lastY = -1;

    public DrawingModule(double width, double height) {
        this.canvas = new Canvas(width, height);
        this.gc = canvas.getGraphicsContext2D();
        
        // Set pen style - Cyan shows up well over the camera feed
        gc.setStroke(Color.CYAN); 
        gc.setLineWidth(5);
    }

    public void updatePoints(int cx, int cy) {
        if (lastX != -1 && lastY != -1) {
            // Draws line between the last point and current point
            gc.strokeLine(lastX, lastY, cx, cy);
        }
        lastX = cx;
        lastY = cy;
    }

    public void resetTracking() {
        // Essential: stops the "connect-the-dots" line when marker is hidden
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