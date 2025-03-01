package com.datasiqn.robotutils.controlcurve;

/**
 * A power {@link ControlCurve}
 * @see <a href=https://en.wikipedia.org/wiki/Exponentiation>Power Function</a>
 */
public class PowerCurve extends ControlCurve {
    private final int power;

    private PowerCurve(PowerCurve.Builder builder) {
        super(builder);

        this.power = builder.power;
    }

    /**
     * Gets the power of this curve
     * @return The power
     */
    public int getPower() {
        return power;
    }

    @Override
    protected double raw(double value) {
        return ((powerMultiplier - minimumPower) / Math.pow(1 - deadZone, power)) * Math.pow(value - deadZone, power);
    }

    /**
     * The builder for a {@link PowerCurve}
     */
    public static class Builder extends ControlCurveBuilder<Builder, PowerCurve> {
        private final int power;

        /**
         * Creates a new builder with a power of {@code power} and the {@code minimumValue} and {@code deadZone} set to 0
         * @param power The power to use. In most cases, you should use an odd power.
         */
        public Builder(int power) {
            this.power = power;
        }

        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        protected PowerCurve postBuild() {
            if (power < 1) throw new IllegalStateException("power cannot be smaller than 1");

            return new PowerCurve(this);
        }
    }
}
