package com.datasiqn.robotutils.controlcurve;

/**
 * An abstract class used to create custom control curves within a robot program.
 * The most common use of this class is for drive controls, but it can be used for many other applications.
 * Using this for drive controls allows a more fine-grain, higher-precision control over robot movement without sacrificing max speed.
 * <p>
 * There are a variety of builtin control curves that can be found in the {@link ControlCurves} class.
 */
public abstract class ControlCurve {
    protected final double minimumPower;
    protected final double deadZone;
    protected final double powerMultiplier;

    protected ControlCurve(ControlCurveBuilder<?, ?> builder) {
        this.minimumPower = builder.minimumPower;
        this.deadZone = builder.deadZone;
        this.powerMultiplier = builder.powerMultiplier;
    }

    /**
     * Gets the minimum power
     * @return The minimum power
     */
    public double getMinimumPower() {
        return minimumPower;
    }

    /**
     * Gets the dead zone
     * @return The dead zone
     */
    public double getDeadZone() {
        return deadZone;
    }

    /**
     * Gets the power multiplier
     * @return The power multiplier
     */
    public double getPowerMultiplier() {
        return powerMultiplier;
    }

    /**
     * Gets the raw curve based on the value.
     * <p>
     * Implementing classes should not include the dead zone or minimum value in this calculation.
     * Note that most curves will be required to be modified based on the max power. For an example, look at the implementation of the linear {@code ControlCurve}.
     * @param value The value that the
     * @return The new value applied to the certain curve
     */
    protected abstract double raw(double value);

    /**
     * Gets the value based on an input. For most applications, the output of this method should be fed into a motor controller.
     * @param value The value to use. This will usually be a joystick or controller axis.
     * @return The new, curved value
     */
    public double get(double value) {
        if (value >= deadZone) return raw(value) + minimumPower;
        else if (value <= -deadZone) return -raw(Math.abs(value)) - minimumPower;
        else return 0;
    }
}
