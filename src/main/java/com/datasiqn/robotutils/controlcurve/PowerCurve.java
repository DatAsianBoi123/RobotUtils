package com.datasiqn.robotutils.controlcurve;

import java.util.logging.Logger;

/**
 * A power {@link ControlCurve}
 * @see <a href=https://en.wikipedia.org/wiki/Exponentiation>Power Function</a>
 */
public class PowerCurve extends ControlCurve<PowerCurve.Builder, PowerCurve> {
    private final int power;

    private PowerCurve(PowerCurve.Builder builder) {
        super(builder);

        this.power = builder.power;
    }

    @Override
    protected double raw(double value) {
        return (1 - minimumPower) * Math.pow(value, power);
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
            if (power % 2 == 0) Logger.getGlobal().warning("power is a non-odd number. this may lead to unexpected behavior");

            return new PowerCurve(this);
        }
    }
}
