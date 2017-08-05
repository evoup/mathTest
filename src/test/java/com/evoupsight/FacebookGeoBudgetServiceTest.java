package com.evoupsight;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
        System.out.println("prediction for 1.5 = " + predict(new double[]{1.5, 1, 1, 0, 0.3}, beta));
    }

    /**
     * test regression3
     */
    @Test
    public void regressionByGeoThree() {
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        double[] y = new double[]{9, 0, 1, 3, 1, 7, 5, 0, 3, 0, 0, 2, 1, 2800};
        double[][] x = new double[14][];
        x[0] = new double[]{33.83};
        x[1] = new double[]{0.01};
        x[2] = new double[]{4.43};
        x[3] = new double[]{13.15};
        x[4] = new double[]{3.51};
        x[5] = new double[]{17.58};
        x[6] = new double[]{25.46};
        x[7] = new double[]{0.06};
        x[8] = new double[]{2.56};
        x[9] = new double[]{1.18};
        x[10] = new double[]{0.11};
        x[11] = new double[]{11.2};
        x[12] = new double[]{4.35};
        x[13] = new double[]{2009};
        regression.newSampleData(y, x);
        double[] beta = regression.estimateRegressionParameters();

        double[] residuals = regression.estimateResiduals();

        double[][] parametersVariance = regression.estimateRegressionParametersVariance();

        double regressandVariance = regression.estimateRegressandVariance();

        double rSquared = regression.calculateRSquared();

        double sigma = regression.estimateRegressionStandardError();
        System.out.println("prediction for 1.5 = " + predict(new double[]{1.5}, beta));
    }


    private double predict(double[] data, double[] beta) {
        double result = 0;
        for (int i = 0; i < data.length; i++) {
            result += data[i] * beta[i];
        }
        return result;
    }

    @Test
    public void t0() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        // Add the data from the array
        List<Double> lines = Arrays.asList(1.2, 2.4, 5.1, 4.3, 4.1,3.1,222.1);
        for( int i = 0; i < lines.size(); i++) {
            stats.addValue(lines.get(i));
        }
        double mean = stats.getMean();
        double sd = stats.getStandardDeviation();
        double median = stats.getPercentile(50);
//        Mean mean = new Mean();
//        mean.evaluate(lines.stream().mapToDouble(d -> d).toArray());
//        StandardDeviation sd = new StandardDeviation();
//        sd.evaluate(lines.stream().mapToDouble(d -> d).toArray());
        double threshold = 1.2;
        List<Double> result = lines.stream()                // convert list to stream
                .filter(line -> test(line, mean, sd, threshold))     // we dont like mkyong
                .collect(Collectors.toList());              // collect the output and convert streams to a List

        result.forEach(System.out::println);                //output : spring, node
    }

    public boolean test(double sampleValue, double mean, double sd, double threshold) {
        double multpleSd = threshold * sd;
        double absMean = Math.abs(mean);
        return sampleValue > absMean + multpleSd;
    }

//    @Test
//    private void t() throws Exception {
//        final double threshold = 2.0;
//
//        Topology t = new Topology("StandardDeviationFilter");
//
//        final Random rand = new Random();
//
//        // Produce a stream of random double values with a normal
//        // distribution, mean 0.0 and standard deviation 1.
//        TStream<Double> values = t.limitedSource(new Supplier<Double>() {
//            public Double get() {
//                return rand.nextGaussian();
//            }
//
//            private static final long serialVersionUID = 1L;
//
//
//
//        }, 100000);
//
//        /*
//         * Filters the values based on calculating the mean and standard
//         * deviation from the incoming data. In this case only outliers are
//         * present in the output stream outliers. A outlier is defined as one
//         * more than (threshold*standard deviation) from the mean.
//         *
//         * This demonstrates an anonymous functional logic class that is
//         * stateful. The two fields mean and sd maintain their values across
//         * multiple invocations of the test method, that is for multiple tuples.
//         *
//         * Note both Mean & StandardDeviation classes are serializable.
//         */
//        TStream<Double> outliers = values.filter(new Predicate<Double>() {
//
//            private static final long serialVersionUID = 1L;
//            private final Mean mean = new Mean();
//            private final StandardDeviation sd = new StandardDeviation();
//
//
//            public boolean test(Double tuple) {
//                mean.increment(tuple);
//                sd.increment(tuple);
//
//                double multpleSd = threshold * sd.getResult();
//                double absMean = Math.abs(mean.getResult());
//                double absTuple = Math.abs(tuple);
//
//                return absTuple > absMean + multpleSd;
//            }
//        });
//
//        outliers.print();
//
//        StreamsContextFactory.getEmbedded().submit(t).get();
//    }
}
