import com.datasiqn.robotutils.controlcurve.ControlCurve;
import com.datasiqn.robotutils.controlcurve.ControlCurves;
import com.datasiqn.robotutils.controlcurve.LinearCurve;
import com.datasiqn.robotutils.controlcurve.PowerCurve;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControlCurveTest {
    @Test
    public void testLinear() {
        LinearCurve curve = ControlCurves.simpleLinear();
        CurveTester tester = new CurveTester(curve);

        tester.testZeroMinMax();
        tester.exactEquals(0.2, 0.2);
        tester.exactEquals(-0.9, -0.9);

        curve = ControlCurves.linear()
                .withDeadZone(0.1)
                .withMinimumPower(0.2)
                .withPowerMultiplier(0.8)
                .build();
        tester = new CurveTester(curve);

        tester.testZeroMinMax();
        tester.exactEquals(0.08, 0);
        tester.exactEquals(-0.09, 0);
        tester.curveEquals(0.6, 0.544);
        tester.curveEquals(0.8, 0.672);
        tester.curveEquals(-0.3, -0.352);
    }

    @Test
    public void testPower() {
        PowerCurve curve = ControlCurves.simplePower(3);
        CurveTester tester = new CurveTester(curve);

        tester.testZeroMinMax();
        tester.curveEquals(0.3, 0.027);
        tester.curveEquals(0.8, 0.512);
        tester.curveEquals(-0.5, -0.125);

        curve = ControlCurves.power(5)
                .withDeadZone(0.05)
                .withMinimumPower(0.12)
                .withPowerMultiplier(0.76)
                .build();
        tester = new CurveTester(curve);

        tester.testZeroMinMax();
        tester.exactEquals(0.02, 0);
        tester.exactEquals(-0.05, 0);
        tester.curveEquals(0.24, 0.0917);
        tester.curveEquals(0.77, 0.2722);
        tester.curveEquals(-0.1, -0.0912);
        tester.curveEquals(-0.9, -0.4861);
    }

    private record CurveTester(ControlCurve curve) {
        public void testZeroMinMax() {
            exactEquals(0, 0);
            exactEquals(1, curve.getPowerMultiplier());
            exactEquals(-1, -curve.getPowerMultiplier());
        }

        public void exactEquals(double value, double expected) {
            curveEquals(value, expected, 0);
        }

        public void curveEquals(double value, double expected) {
            curveEquals(value, expected, 0.0001);
        }

        public void curveEquals(double value, double expected, double delta) {
            assertEquals(expected, curve.get(value), delta);
        }
    }
}
