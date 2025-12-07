package xdrone;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutputProvider;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import com.pi4j.plugin.raspberrypi.platform.RaspberryPiPlatform;
import xdrone.helpers.PIN;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
/*        File directory = new File("./");
        System.out.println(directory.getAbsolutePath());*/

        //xdrone.DroneAI.getInstance().launch(args);

        final var piGpio = PiGpio.newNativeInstance();
        Context pi4j = Pi4J.newContextBuilder()
                .noAutoDetect()
                .add(new RaspberryPiPlatform() {
                    @Override
                    protected String[] getProviders() {
                        return new String[]{};
                    }
                })
                .add(PiGpioDigitalInputProvider.newInstance(piGpio),
                        PiGpioDigitalOutputProvider.newInstance(piGpio),
                        PiGpioPwmProvider.newInstance(piGpio),
                        PiGpioSerialProvider.newInstance(piGpio),
                        PiGpioSpiProvider.newInstance(piGpio),
                        LinuxFsI2CProvider.newInstance()
                )
                .build();

/*        System.out.println("Initializing the camera");
        Camera camera = new Camera();

        System.out.println("Taking a default picture");
        camera.takeStill();*/

        final var servoMotor = new ServoMotor(pi4j, PIN.PWM18);

        Scanner scan = new Scanner(System.in);

        System.out.println("start programm");
        int percent = 0;
        String input = "";
        do {
            input = scan.nextLine();

            try {
                percent = Integer.parseInt(input);

                if(percent >= 0 && percent <= 100)
                    servoMotor.setPercent(percent);
            } catch (NumberFormatException ex) {
                if(!input.equals("exit"))
                    System.out.println("error format input");
            }

        } while(!input.equals("exit"));
    }

    /**
     * Utility function to sleep for the specified amount of milliseconds.
     * An {@link InterruptedException} will be catched and ignored while setting the interrupt flag again.
     *
     * @param milliseconds Time in milliseconds to sleep
     */
    static void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
