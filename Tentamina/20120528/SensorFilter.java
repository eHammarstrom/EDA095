package sensor;

public class SensorFilter {
    private double[] sensors;

    public SensorFilter() {
        sensors = new double[3];
    }

    public synchronized void putValue(int id, double val) {
        while (sensors[id] != Double.MAX_VALUE) {
            try { wait(); } catch (Exception e) {}
        }
        sensors[id] = val;
        notifyAll();
    }

    public synchronized double getMedian() {
        int readyCount = 0;
        while (true) {
            for (int i = 0; i < sensors.length; i++) {
                if (sensors[i] != Double.MAX_VALUE) {
                    readyCount++;
                }
            }
            if (readyCount == 3) break;
            try { wait(); } catch (Exception e) {}
        }

        Arrays.sort(sensors);

        double median = sensors[1];

        for (double d : sensors) {
            d = Double.MAX_VALUE;
        }

        return median;
    }

}
