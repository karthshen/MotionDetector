package motiondetector;

import com.github.sarxos.webcam.*;
import googledrive.DriveFileManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleMotionDetector implements WebcamMotionListener {

    private Webcam webcam;
    private boolean isRunning = true;
    private int imageCount = 0;
    private WebcamMotionDetector detector;

    private final int imageMax = 5;
    private final String GOOGLE_DRIVE_DIRECTORY = "MotionDetectCaptures";

    public SimpleMotionDetector() {

        //Configure Camera
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();

        detector = new WebcamMotionDetector(Webcam.getDefault());
        detector.setInterval(100);
        detector.addMotionListener(this);
        detector.start();
    }

    public void motionDetected(WebcamMotionEvent webcamMotionEvent) {
        //Detects motion
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println("Detected Motion at " + dateFormat.format(new Date()));

        try {
            TakeImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageCount++;
        if(imageCount>=imageMax){
            try {
                ExitDetector();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void TakeImage() throws IOException {

        BufferedImage image = webcam.getImage();

        ImageIO.write(image, "PNG", new File("./Captures/capture" + imageCount + ".png"));
    }

    public boolean isRunning() {
        return isRunning;
    }

    public Webcam getCamera(){
        return webcam;
    }

    private void ExitDetector() throws IOException {
        isRunning = false;
        webcam.close();
        detector.stop();
        UploadToDrive();
        System.exit(0);
    }

    private void UploadToDrive() throws IOException {
        File folder = new File("./Captures/");
        File[] files = folder.listFiles();

        for(File file : files){
            if(file.getName().contains("capture")) {
                DriveFileManager.createGoogleFile(GOOGLE_DRIVE_DIRECTORY, "image/png", file.getName(), file);
            }
        }
    }

    public WebcamMotionDetector getDetector() {
        return detector;
    }
}
