package com.datasiqn.robotutils.controlcurve;

/**
 * A linear {@link ControlCurve}.
 * @see <a href=https://en.wikipedia.org/wiki/Linear_function_(calculus)>Linear Function</a>
 */
public class LinearCurve extends ControlCurve {
    private LinearCurve(Builder builder) {
        super(builder);
    }

    @Override
    protected double raw(double value) {
        return (1 - minimumPower) * value;
    }

    /**
     * The builder for a {@link LinearCurve}
     */
    public static class Builder extends ControlCurveBuilder<Builder, LinearCurve> {
        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        protected LinearCurve postBuild() {
            return new LinearCurve(this);
        }
    }
}
