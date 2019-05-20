import googledrive.DriveQuickStart;
import motiondetector.SimpleMotionDetector;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class App {
    public static void main (String []args) throws IOException, GeneralSecurityException {

        DriveQuickStart driveStart = new DriveQuickStart();
        driveStart.QuickStart(args);

        SimpleMotionDetector detector = new SimpleMotionDetector();
        while(detector.isRunning()){

        }
    }
}
