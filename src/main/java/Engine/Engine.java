package Engine;

import com.pi4j.io.pwm.Pwm;

public class Engine {

    protected float value;
    protected Pwm pwm;

    public Engine(Pwm pmwConfig)
    {
        pwm = pmwConfig;
    }

    public void SetValue(float new_value)
    {
        value = new_value;
    }

    public float GetValue()
    {
        return value;
    }

    public void Transmit()
    {

    }
}
