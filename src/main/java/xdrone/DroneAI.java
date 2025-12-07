package xdrone;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.platform.Platforms;

public class DroneAI {
    private static DroneAI instance;
    private Engine engine = new Engine();
    private Context pi4j;

    public static DroneAI getInstance() {
        if (instance == null)
            instance = new DroneAI();

        return instance;
    }

    public Context getGPIOContext()
    {
        return pi4j;
    }

    public void launch(String[] args)
    {
        up();
        update();
        down();
    }

    public void up()
    {
        pi4j = Pi4J.newAutoContext();

        Platforms platforms = pi4j.platforms();
        platforms.describe().print(System.out);
    }

    public void update()
    {
        while(true)
        {
            engine.update();
        }
    }

    public void down()
    {
        pi4j.shutdown();
    }
}
