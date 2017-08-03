package com.madhouse.performad.adsapi.facebook.service.imp;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.junit.Test;

/**
 * Created by evoup on 2017/8/3.
 */
public class FacebookGeoBudgetServiceTest {

    /**
     * test regression1
     */
    @Test
    public void regressionByGeoOne() {
        SimpleRegression regression = new SimpleRegression();
        regression.addData(1d, 2d);
        regression.addData(3d, 3d);
        regression.addData(3d, 3d);
        regression.addData(1.3d, 2.3d);
        System.out.println("Intercept:" + regression.getIntercept() + "slope:" + regression.getSlope());
        System.out.println(regression.getIntercept());
        // displays intercept of regression line

        System.out.println(regression.getSlope());
        // displays slope of regression line

        System.out.println(regression.getSlopeStdErr());
        // displays slope standard error

        System.out.println(regression.predict(1.5d));
    }

    /**
     * test regression2
     */
    @Test
    public void regressionByGeoTwo() {
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        double[] y = new double[]{11.0, 12.0, 13.0, 14.0, 15.0, 16.0};
        double[][] x = new double[6][];
        x[0] = new double[]{0, 0, 0, 0, 0};
        x[1] = new double[]{2.0, 0, 0, 0, 0};
        x[2] = new double[]{0, 3.0, 0, 0, 0};
        x[3] = new double[]{0, 0, 4.0, 0, 0};
        x[4] = new double[]{0, 0, 0, 5.0, 0};
        x[5] = new double[]{0, 0, 0, 0, 6.0};
        regression.newSampleData(y, x);
        double[] beta = regression.estimateRegressionParameters();

        double[] residuals = regression.estimateResiduals();

        double[][] parametersVariance = regression.estimateRegressionParametersVariance();

        double regressandVariance = regression.estimateRegressandVariance();

        double rSquared = regression.calculateRSquared();

        double sigma = regression.estimateRegressionStandardError();
        System.out.println("prediction for 1.5 = " + predict(new double[] {1.5, 1, 1, 0, 0.3}, beta));
;    }

    private double predict(double[] data, double[] beta) {
        double result = 0;
        for (int i = 0; i < data.length; i++) {
            result += data[i] * beta[i];
        }
        return result;
    }
}
