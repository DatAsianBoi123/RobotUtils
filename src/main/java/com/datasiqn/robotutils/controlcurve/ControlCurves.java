package com.datasiqn.robotutils.controlcurve;

/**
 * A collection of different built-in {@link ControlCurve}s.
 * <p>
 * You can go to <a href=https://www.desmos.com/calculator/jaa4cadcpf>this Desmos graph</a> to better visualize what different control curves do.
 */
public final class ControlCurves {
    /**
     * Creates a simple linear {@link ControlCurve} with a dead zone of 0 and a minimum power of 0.
     * If you want to set these to custom values, use {@link #linear()} instead.
     * @return The created {@link ControlCurve}
     */
    public static LinearCurve simpleLinear() {
        return ControlCurves.linear().build();
    }

    /**
     * Creates a simple {@link ControlCurve} with a dead zone of 0 and a minimum power of 0 and a power of {@code power}
     * If you want to set these to custom values, use {@link #power(int)} instead.
     * @param power The power to use. For best results, use an odd power.
     * @return The created {@link ControlCurve}
     */
    public static PowerCurve simplePower(int power) {
        return ControlCurves.power(power).build();
    }

    /**
     * Creates a linear curve builder that allows you to set custom dead zone and minimum power values.
     * If you don't need to set custom values, use {@link #simpleLinear()} instead.
     * @return The builder
     */
    public static LinearCurve.Builder linear() {
        return new LinearCurve.Builder();
    }

    /**
     * Creates a power curve builder that allows you to set custom dead zone and minimum power values.
     * If you don't need to set custom values, use {@link #simplePower(int)} instead.
     * @param power The power to use. In most cases, you should use an odd power.
     * @return The builder
     */
    public static PowerCurve.Builder power(int power) {
        return new PowerCurve.Builder(power);
    }
}
