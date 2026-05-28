import javafx.application.Application;
import javafx.animation.AnimationTimer;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import javafx.scene.input.KeyCode;

import javafx.stage.Stage;
import javafx.stage.FileChooser;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.embed.swing.SwingFXUtils;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    static {
        System.load(
            "C:\\opencv\\build\\java\\x64\\opencv_java4120.dll"
        );
    }

    private VideoCapture camera;

    @Override
    public void start(Stage stage) {

        camera = new VideoCapture(0);

        ImageView cameraView = new ImageView();

        DrawingModule drawingModule =
            new DrawingModule(640, 480);

        Label title = new Label("AIRDRAW");

        title.setFont(
            Font.font(
                "Courier New",
                FontWeight.BOLD,
                18
            )
        );

        title.setTextFill(Color.CYAN);

        Button saveBtn = new Button("Save");
        Button clearBtn = new Button("Clear");

        Button cyanBtn = new Button("Cyan");
        Button redBtn = new Button("Red");
        Button greenBtn = new Button("Green");
        Button whiteBtn = new Button("White");

        Button smallBtn = new Button("Small");
        Button mediumBtn = new Button("Medium");
        Button largeBtn = new Button("Large");

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox controls = new HBox(
            10,
            title,
            spacer,
            cyanBtn,
            redBtn,
            greenBtn,
            whiteBtn,
            smallBtn,
            mediumBtn,
            largeBtn,
            clearBtn,
            saveBtn
        );

        controls.setPadding(new Insets(10));

        controls.setAlignment(Pos.CENTER_LEFT);

        controls.setStyle(
            "-fx-background-color: #111827;"
        );

        StackPane drawingArea =
            new StackPane(
                cameraView,
                drawingModule.getCanvas()
            );

        BorderPane root = new BorderPane();

        root.setTop(controls);

        root.setCenter(drawingArea);

        Scene scene = new Scene(root, 1000, 700);

        scene.setOnKeyPressed(event -> {

            if (event.getCode() == KeyCode.C) {

                drawingModule.clearCanvas();
            }
        });

        clearBtn.setOnAction(event -> {

            drawingModule.clearCanvas();
        });

        cyanBtn.setOnAction(event -> {

            drawingModule.setBrushColor(Color.CYAN);
        });

        redBtn.setOnAction(event -> {

            drawingModule.setBrushColor(Color.RED);
        });

        greenBtn.setOnAction(event -> {

            drawingModule.setBrushColor(Color.LIMEGREEN);
        });

        whiteBtn.setOnAction(event -> {

            drawingModule.setBrushColor(Color.WHITE);
        });

        smallBtn.setOnAction(event -> {

            drawingModule.setBrushSize(3);
        });

        mediumBtn.setOnAction(event -> {

            drawingModule.setBrushSize(6);
        });

        largeBtn.setOnAction(event -> {

            drawingModule.setBrushSize(12);
        });

        saveBtn.setOnAction(event -> {

            FileChooser fileChooser =
                new FileChooser();

            fileChooser.setTitle("Save Drawing");

            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                    "PNG Files",
                    "*.png"
                )
            );

            File file =
                fileChooser.showSaveDialog(stage);

            if (file != null) {

                WritableImage image =
                    drawingModule.snapshotCanvas();

                try {

                    ImageIO.write(
                        SwingFXUtils.fromFXImage(
                            image,
                            null
                        ),
                        "png",
                        file
                    );

                    System.out.println(
                        "Drawing Saved"
                    );

                } catch (IOException ex) {

                    ex.printStackTrace();
                }
            }
        });

        stage.setTitle("AirDraw");

        stage.setScene(scene);

        stage.show();

        AnimationTimer timer =
            new AnimationTimer() {

                @Override
                public void handle(long now) {

                    Mat frame = new Mat();

                    if (camera.read(frame)) {

                        Core.flip(frame, frame, 1);

                        Point markerPos =
                            ObjectDetection
                                .detectColoredMarker(frame);

                        cameraView.setImage(
                            CameraModule.matToImage(frame)
                        );

                        if (markerPos != null) {

                            drawingModule.updatePoints(
                                (int) markerPos.x,
                                (int) markerPos.y
                            );

                        } else {

                            drawingModule.resetTracking();
                        }
                    }

                    frame.release();
                }
            };

        timer.start();

        stage.setOnCloseRequest(event -> {

            timer.stop();

            camera.release();
        });
    }

    public static void main(String[] args) {

        launch(args);
    }
}