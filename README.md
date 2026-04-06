# AirDraw — Gesture-Controlled Virtual Drawing Board

**Team:** India | GEHU B.Tech CSE  

**Language:** Java | **GUI:** JavaFX | **Vision:** OpenCV


> Draw in the air using a colored marker — no mouse, no touch, just a webcam.

---

## Team Members

- Sanskriti Bhardwaj
- Aryan Raturi
- Divyam Singh
- Akhil Padiyar

---

## Project Structure

```
java-pbl/
├── src/
│   ├── CameraModule.java       ← Module 1: webcam capture + JavaFX display
│   ├── ObjectDetection.java    ← Module 1: color detection + coordinate tracking
│   ├── DrawingModule.java      ← Module 2: drawing lines from tracked coordinates
│   ├── UIModule.java           ← Module 3: buttons and UI layout
│   └── Main.java               ← entry point, connects all modules
├── lib/
│   └── opencv-4120.jar         ← OpenCV Java bindings ( required )
├── .gitignore
└── README.md
```

---

## Setup Instructions (Every Team Member Must Do This)

### Step 1 — Download OpenCV

1. Go to [https://opencv.org/releases/](https://opencv.org/releases/)
2. Download **OpenCV 4.12.0 – Windows**
3. Run the `.exe` and extract to `C:\opencv`
4. Verify these two files exist:
   - `C:\opencv\build\java\opencv-4120.jar`
   - `C:\opencv\build\java\x64\opencv_java4120.dll`

### Step 2 — Download JavaFX

1. Go to [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
2. Download **JavaFX 21 LTS – Windows SDK**
3. Extract to `C:\javafx`
4. Verify the folder `C:\javafx\lib\` contains `.jar` files

### Step 3 — Clone the Repo

1. Open **GitHub Desktop**
2. Go to **File → Clone Repository**
3. Paste the repo URL → choose your local folder → click **Clone**

### Step 4 — Add OpenCV jar to lib/

Copy `opencv-4120.jar` from `C:\opencv\build\java\` into the `lib\` folder inside the cloned repo.

> **Do NOT copy the `.dll` file into the repo** — everyone keeps that locally at `C:\opencv\`

### Step 5 — Configure VS Code

Open the project folder in VS Code. Make sure `.vscode/settings.json` contains:

```json
{
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}
```

---

## How to Run

```bash
# Compile all files
javac -cp "lib/opencv-4120.jar" --module-path "C:\javafx\lib" --add-modules javafx.controls src/*.java -d out/

# Run the app
java -cp "out;lib/opencv-4120.jar" --module-path "C:\javafx\lib" --add-modules javafx.controls Main
```

> On Windows use `;` as classpath separator. On Mac/Linux use `:`

---

## How Modules Connect

```
Webcam
  ↓
Module 1 — Input and Image Processing
  → CameraModule.java: captures frames, displays live feed in JavaFX
  → ObjectDetection.java: converts to HSV, applies color filter,
    detects marker, outputs (cx, cy) coordinates

  ↓
Module 2 — Tracking and Drawing
  → DrawingModule.java: receives (cx, cy), tracks movement,
    stores points, draws lines between them on canvas

  ↓
Module 3 — User Interface
  → UIModule.java: wraps canvas with Clear / Save / Exit buttons,
    handles user interactions

  ↓
Main.java
  → launches the JavaFX app, connects all modules
```

---

## Git Workflow for Team Members

### Before starting work:
```bash
# Always pull latest changes first (in GitHub Desktop: click Fetch origin → Pull)
```

### After finishing your work:
1. Open **GitHub Desktop**
2. You'll see your changed files listed on the left
3. Write a clear commit message like: `Add DrawingModule - line drawing between points`
4. Click **Commit to main**
5. Click **Push origin**


### Rules:
- **Each person works only in their own `.java` file** — avoids merge conflicts
- Never edit someone else's file without telling them
- Always pull before you start coding for the day

---

## Important Notes

- The `.dll` file is **not in the repo** — everyone has it locally at `C:\opencv\`
- The `lib/` folder contains only the `.jar` — commit that, not the DLL
- If OpenCV throws `UnsatisfiedLinkError`, your DLL path in the code is wrong — check `System.load(...)` in your file

---

## References

- [OpenCV Java Docs](https://docs.opencv.org/4.x/d9/d52/tutorial_java_dev_intro.html)
- [JavaFX Docs](https://openjfx.io/javadoc/21/)
- [MediaPipe (Python reference)](https://developers.google.com/mediapipe)
