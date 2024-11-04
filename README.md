# Automatic-Plant-Watering-System
CONTEXT:
This project is an automated plant watering system using a Arduino microcontroller and Java. This project uses an integration of sensors, electro-mechanical components, and software to make the system function. The system reads the level of moisture in a plant’s soil, display these numbers on an OLED screen and waters the soil based on the moisture level getting rid of the redundant task of watering a plant.

TECHNICAL FEATS: 
The system reads the moisture level in the soil.
The system knows to pump water if the moisture in the soil is below a certain threshold.
The moisture level and status of the pump is be displayed on the OLED.
The system can run over days and check the moisture level multiple times.

COMPONENT LIST:
9v battery
MOSFET
Water pump
Arduino microcontroller
Sensor
Plant
Laptop



PROCEDURE:
The MOSFET is set up to the battery and pump. The senor is then connected to the MOSFET and Arduino itself. The Arduino is set up to my java program using Firmata4j api in the main class. The code has two main classes, the main class function to run the program and the watering class which reads the moisture level from the sensor and depending on the value the pump is activated for 2 seconds and then displays to the user how long it took to make the soil meet the threshold. It loops every hour after obtaining the correct moisture level checking the moisture level 24 times a day.
