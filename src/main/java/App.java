import googledrive.DriveFileManager;
import motiondetector.SimpleMotionDetector;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class App {
    public static void main(String[] args) throws IOException, GeneralSecurityException {

        SimpleMotionDetector detector = new SimpleMotionDetector();
        while (detector.isRunning()) {

        }
    }
}
