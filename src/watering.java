import org.firmata4j.I2CDevice;
import org.firmata4j.firmata.*;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class watering {
    static String recLog = "";

    public static void process() throws IOException {
        String myPort = "COM3";
        IODevice myGroveBoard = new FirmataDevice(myPort);
        Date Now;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Now = Calendar.getInstance().getTime();
        recLog = dateFormat.format(Now) + " Starting the watering process.\n";
        I2CDevice i2cComponent = myGroveBoard.getI2CDevice((byte) 0x3c);
        SSD1306 oled = new SSD1306(i2cComponent, SSD1306.Size.SSD1306_128_64);
        oled.init();
        try {
            myGroveBoard.start();
            System.out.println("Arduino Board Started for Watering Process.");
            myGroveBoard.ensureInitializationIsDone();
        } catch (Exception ex) {
            System.out.println("Couldn't connect to Arduino board.");
        } finally {
            var MoistVolt = myGroveBoard.getPin(16);
            MoistVolt.setMode(Pin.Mode.ANALOG);
            var myPump = myGroveBoard.getPin(7);
            myPump.setMode(Pin.Mode.OUTPUT);
            Now = Calendar.getInstance().getTime();
            recLog = recLog + dateFormat.format(Now) + " The moisture level is " + MoistVolt.getValue() + " .\n";
            oled.getCanvas().clear();
            oled.getCanvas().drawString(0, 0, "The pump is on!");
            oled.getCanvas().drawString(0, 20, "the moisture level is " + MoistVolt.getValue());
            oled.display();
            int count = 0;

            while (MoistVolt.getValue() > 580) {
                count++;
                if (count == 1) {
                    Now = Calendar.getInstance().getTime();
                    recLog = recLog + dateFormat.format(Now) + " The moisture level is low, watering for 2 seconds.\n";
                    oled.getCanvas().clear();
                    oled.getCanvas().drawString(0, 0, "The moisture level is low, watering for 2 seconds.");
                    oled.getCanvas().drawString(0, 20, "the moisture level is now " + MoistVolt.getValue());
                    oled.display();
                } else {
                    Now = Calendar.getInstance().getTime();
                    recLog = recLog + dateFormat.format(Now) + " The moisture level is still low, watering for 2 seconds.\n";
                    oled.getCanvas().clear();
                    oled.getCanvas().drawString(0, 0, "The moisture level is still low, watering for 2 seconds.");
                    oled.getCanvas().drawString(0, 20, "the moisture level is now " + MoistVolt.getValue());
                    oled.display();
                }

                myPump.setValue(1);
                try {
                    Thread.sleep(2000);
                } catch (Exception ex) {
                    System.out.println("sleep error.");
                    oled.getCanvas().clear();
                    oled.getCanvas().drawString(0, 20, "sleep error.");
                    oled.display();
                }

                myPump.setValue(0);
                try {
                    Thread.sleep(20000);
                } catch (Exception ex) {
                    System.out.println("sleep error.");
                    oled.getCanvas().clear();
                    oled.getCanvas().drawString(0, 20, "sleep error.");
                    oled.display();
                }

                if (count == 0) {
                    Now = Calendar.getInstance().getTime();
                    recLog = recLog + dateFormat.format(Now) + " The moisture level is sufficient, no watering is needed.\n";
                    oled.getCanvas().clear();
                    oled.getCanvas().drawString(0, 0, "The moisture level is sufficient, no watering is needed.");
                    oled.getCanvas().drawString(0, 20, "the moisture level is now " + MoistVolt.getValue());
                    oled.display();
                } else {
                    Now = Calendar.getInstance().getTime();
                    recLog = recLog + dateFormat.format(Now) + " The moisture level is now sufficient, after " + count * 2 + " seconds of watering. \n \n";
                    recLog = recLog + dateFormat.format(Now) + " The moisture level is sufficient, no watering is needed.\n";
                    oled.getCanvas().clear();
                    oled.getCanvas().drawString(0, 0, "The moisture level is now sufficient, after " + count * 2 + " seconds of watering.");
                    oled.getCanvas().drawString(0, 20, "the moisture level is now " + MoistVolt.getValue());
                    oled.display();
                }
            }

            myGroveBoard.stop();
            System.out.println("Arduino Board Stopped. End of Watering Process.");
            oled.getCanvas().clear();
            oled.getCanvas().drawString(0, 20, "Arduino Board Stopped. End of Watering Process.");
            oled.display();
        }
    }
    public static String processLog() {
        return recLog;
    }
}
