package org.file.filemanager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PhotoController {
    @FXML
    private Label welcomeText;


    private final String IMAGE_FOLDER = "resources/images";
    private List<String> imagePaths = new ArrayList<String>();
    @FXML
    private Button uploadButton, saveButton, showButton;
    @FXML
    private ImageView imageView1, imageView2;

    File imageFolder;
    private List<String> currentAvailableImages = new ArrayList<String>();

    public void ImageController() {
        imagePaths = new ArrayList<>();
    }

    public void initialize() {
        uploadButton.setDisable(false);
        saveButton.setDisable(true);

        int imageFiles = countImagesInFolder();
        if (imageFiles >= 2) {
            showButton.setDisable(false);
        } else {
            showButton.setDisable(false);
        }
        imageFolder = new File(IMAGE_FOLDER);
        File[] files = imageFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                // Check if it's a file and not a directory
                if (file.isFile()) {
                    currentAvailableImages.add(file.toString());
                }
            }
        }

        Image image = new Image("file:resources/images/DP_tv_panel_400k.jpeg");
        imageView1.setImage(null);
        imageView2.setImage(null);
//        refreshImagePaths();
    }

    public void handleUploadButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files (*.jpg, *.jpeg, *.png)", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            Path imagePath = Paths.get(IMAGE_FOLDER, fileName);

            boolean contains = imagePaths.contains(selectedFile.toString());
            if (!contains) {
                imagePaths.add(selectedFile.toString());
                saveButton.setDisable(false);
                showButton.setDisable(imagePaths.size() >= 2);
            } else {
                showAlert("Upload Error", "Image already exist");
            }
        }
    }

    public void handleSaveButton(ActionEvent event) {
        // Check if the "resources/images" folder exists
        File imageFolder = new File(IMAGE_FOLDER);
        if (!imageFolder.exists()) {
            // Create the folder if it doesn't exist
            try {
                if (imageFolder.mkdirs()) {
                    System.out.println("Created image folder: " + IMAGE_FOLDER);
                } else {
                    System.err.println("Failed to create image folder: " + IMAGE_FOLDER);
                    // Optionally display an error message to the user here
                }
            } catch (SecurityException e) {
                System.err.println("Error creating image folder: " + e.getMessage());
                // Optionally display an error message to the user here, explaining insufficient permissions
            }
        }

        // Handle saving image data
        for (String imagePath : imagePaths) {
            try {
                // Extract filename with extension
                Path path = Paths.get(imagePath);
                String filename = path.getFileName().toString();

                // Create a new file path within the image folder
                Path newImagePath = Paths.get(IMAGE_FOLDER, filename);

                // Copy the image file to the new location
                Files.copy(path, newImagePath);
                System.out.println("Saved image: " + newImagePath);
            } catch (IOException e) {
                System.err.println("Error saving image: " + e.getMessage());
                // Optionally display an error message to the user here
            }
        }

        // Disable the Save button after saving
        saveButton.setDisable(true);

        // Display a message (assuming you have a showAlert method)
        showAlert("Images Saved!", "The uploaded images have been saved to the specified folder.");
    }


    public void handleShowButton(ActionEvent actionEvent) {
        Collections.shuffle(currentAvailableImages);
        int imageFiles = countImagesInFolder();
        if (imageFiles >= 2) {
            Image image1 = new Image("file:" + currentAvailableImages.get(0));
            Image image2 = new Image("file:" + currentAvailableImages.get(1));
            imageView1.setImage(image1);
            imageView2.setImage(image2);
        } else {
            // Handle case where there are less than 2 images
            Platform.runLater(() -> showAlert("Not Enough Images!", "Please upload at least 2 images to display randomly."));
        }
    }

    public void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public int countImagesInFolder() {
        imageFolder = new File(IMAGE_FOLDER);
        if (imageFolder.isDirectory()) {
            File[] imageFiles = imageFolder.listFiles((dir, name) -> {
                String lowercaseName = name.toLowerCase();
                return lowercaseName.endsWith(".jpg") || lowercaseName.endsWith(".jpeg") || lowercaseName.endsWith(".png");
            });

            if (imageFiles != null) {
                return imageFiles.length;
            }
        }
        return 0;
    }


}