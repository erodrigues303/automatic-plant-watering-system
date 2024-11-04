import org.firmata4j.IODeviceEventListener;
import org.firmata4j.IOEvent;
import org.firmata4j.Pin;

import java.io.IOException;

public class ButtonAction implements IODeviceEventListener {
    private static Pin pumpPin;
    private static Pin btnPin;
    public ButtonAction(Pin pumpControlPin, Pin buttonPin) {
        this.pumpPin = pumpControlPin;
        this.btnPin = buttonPin;
    }

    @Override
    public void onStart(IOEvent ioEvent) {
    }

    @Override
    public void onStop(IOEvent ioEvent) {
    }

    @Override
    public void onPinChange(IOEvent ioEvent) {
        if (btnPin.getValue() == 1) {
            try {
                pumpPin.setValue(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        }
    }

    @Override
    public void onMessageReceive(IOEvent ioEvent, String s) {

    }
}