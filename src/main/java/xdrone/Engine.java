package xdrone;

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;

public class Engine {

    public void update()
    {
        this.engineTest();
        this.balance();
    }

    public void balance()
    {

    }

    public void engineTest() {
        int PIN = 24;

        var pi4j = DroneAI.getInstance().getGPIOContext();

        var config = DigitalOutput.newConfigBuilder(pi4j)
                .id("servo")
                .name("Servo")
                .address(PIN)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-input");

        var servo = pi4j.create(config);

        int pressCount = 0;

        try {
            while (pressCount < 20) {
                if (servo.equals(DigitalState.HIGH)) {
                    servo.low();
                } else {
                    servo.high();
                }
                Thread.sleep(250 / (pressCount += 1));
            }
        } catch (InterruptedException exception) {
            System.out.println(exception.getMessage());
        }
    }

}
