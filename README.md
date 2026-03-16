# Gesture-Controlled Virtual Drawing Board
## Project Overview

This project is a simple application that allows a user to draw on the computer screen without touching the mouse or screen. Instead of using traditional input devices, the user can draw in the air using their hand or a colored marker in front of a webcam.

The webcam captures the movement, and the system converts that movement into drawing on a virtual canvas.

This project demonstrates how computers can interact with humans using gestures instead of physical input devices.

The project is developed using **Java**, **JavaFX**, and **OpenCV**.


# Problem Statement

Normally, when we draw on a computer, we need devices like:

* Mouse
* Stylus
* Touchscreen

These devices require physical contact with the system.

In some situations, touch-based interaction is not convenient. For example:

* Online teaching and presentations
* Smart classrooms
* Public systems
* Touchless environments

So, this project aims to create a **touchless drawing system** where the user can draw using hand movement captured by a webcam.


# How the System Works

The system works in a few simple steps:

1. The webcam captures live video.
2. The system processes each frame of the video.
3. The program detects a colored marker or hand movement.
4. The system finds the position (X and Y coordinates) of the detected object.
5. These coordinates are stored.
6. The program draws lines between the points.
7. The drawing appears on the screen in real time.

This creates the effect of drawing in the air.


# Technologies Used

The following technologies are used in this project:

### Java

Java is the main programming language used to build the application.

### JavaFX

JavaFX is used to create the graphical user interface such as:

* Application window
* Drawing canvas
* Buttons (Clear, Save, Exit)

### OpenCV

OpenCV is a computer vision library used for:

* Capturing webcam input
* Processing images
* Detecting colors or objects
* Tracking movement

---

# Why This Project Is Built Using Java

Most gesture-based computer vision projects are usually developed using **Python** because Python provides many libraries that make image processing easier.

However, in this project we are implementing the system using **Java instead of Python**.

The main reasons are:

* Our academic curriculum focuses mainly on Java programming.
* Implementing computer vision in Java helps us understand the internal working of such systems.
* It allows us to combine **object-oriented programming concepts with computer vision**.
* Java also provides strong support for desktop application development through **JavaFX**.

Using Java makes the project more challenging but also more educational. It shows that gesture-based systems can also be built effectively using Java with OpenCV.


# Main Features

This application includes the following features:

* Real-time webcam input
* Detection of hand or colored marker
* Drawing on virtual canvas
* Clear drawing option
* Save drawing option
* Touchless interaction


# Project Modules

The project is divided into three main modules.

### 1. Input and Image Processing Module

This module handles the webcam input and image processing.

Responsibilities:

* Capture video from webcam
* Convert image color format
* Detect the object or marker
* Remove noise from image
* Identify contours


### 2. Tracking and Drawing Module

This module converts movement into drawing.

Responsibilities:

* Find the center point of detected object
* Track coordinates
* Store movement points
* Draw lines between points


### 3. User Interface Module

This module manages the graphical interface.

Responsibilities:

* Create application window
* Display drawing canvas
* Provide control buttons
* Handle user interactions


# Applications

This project can be used in several real-life situations such as:

* Virtual whiteboards
* Online teaching
* Interactive presentations
* Gesture-based control systems
* Human-computer interaction research


# Future Improvements

In the future, the system can be improved by adding:

* Full hand gesture recognition
* Multiple color selection using gestures
* Eraser gesture
* AI-based hand tracking
* Integration with online meeting tools


# Project Goal

The goal of this project is to demonstrate how gesture-based interaction can replace traditional input devices and create more natural ways for humans to interact with computers.


# Authors

Team Name: Avengers

Team Members:

* Sanskriti Bhardwaj
* Aryan Raturi
* Divyam Singh
* Akhil Padiyar

---

# Repository Updates

This repository will contain continuous updates including:

* Source code
* Implementation steps
* Improvements
* Bug fixes
* Project documentation

