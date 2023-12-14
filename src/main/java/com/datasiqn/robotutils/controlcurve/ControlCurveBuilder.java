package com.datasiqn.robotutils.controlcurve;

/**
 * An abstract class used to create {@link ControlCurve}s. This should be implemented if you are creating a custom {@link ControlCurve}.
 * @param <T> The type of the implementing class
 * @param <C> The type of the target {@link ControlCurve} class
 */
public abstract class ControlCurveBuilder<T extends ControlCurveBuilder<T, C>, C extends ControlCurve> {
    protected double minimumPower;
    protected double deadZone;

    /**
     * Creates a new builder with {@code minimumPower} and {@code deadZone} set to 0
     */
    public ControlCurveBuilder() { }

    /**
     * Sets the minimum power. This is the minimum amount of power needed to get the motor to move.
     * @param minimumPower The new minimum power to use. This should in the range [0, 1)
     * @return {@code this}, for chaining
     */
    public T withMinimumPower(double minimumPower) {
        this.minimumPower = minimumPower;
        return getThis();
    }

    /**
     * Sets the dead zone. If the values fed into {@link ControlCurve#get(double)} are less than {@code deadZone}, it will treat it as a 0.
     * This is especially useful for joystick or controller axis, as they almost always have a bit of drift.
     * @param deadZone The new dead zone to use. This should be in the range [0, 1)
     * @return {@code this}, for chaining
     */
    public T withDeadZone(double deadZone) {
        this.deadZone = deadZone;
        return getThis();
    }

    /**
     * Gets the implementing object. This gets called after every single builder method.
     * Without it, custom methods would not be able to be used on implementing classes.
     * @return The implementing object
     */
    protected abstract T getThis();

    /**
     * This gets run after all validation of {@code minimumPower} and {@code deadZone} fields
     * @return The built object
     * @throws IllegalStateException If any values are invalid or outside their bounds.
     * You do not need to check bounds for the {@code minimumPower} and {@code deadZone} fields, as they are validated before calling this method.
     */
    protected abstract C postBuild();

    /**
     * Builds this {@link ControlCurve} with the specified values, or default values if they were not specified.
     * @return The newly created {@link ControlCurve}
     * @throws IllegalStateException If any values are invalid or outside their bounds
     */
    public C build() {
        if (minimumPower < 0) throw new IllegalStateException("minimum power must be positive");
        if (minimumPower >= 1) throw new IllegalStateException("minimum power cannot be greater than or equal to 1");
        if (deadZone < 0) throw new IllegalStateException("dead zone must be positive");
        if (deadZone >= 1) throw new IllegalStateException("dead zone cannot be greater than or equal to 1");

        return postBuild();
    }
}
