package monoscan;

public class MultiScan {

    public static void main(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException();

        String tarAddr = args[0];

        ServerStats ss = new ServerStats();

        ExecutorService es = Executors.newfixedThreadPool(32);

        Future<boolean> future;
        LinkedList<Future<boolean>> futuresAll = new LinkedList<>();
        for (int i = 0; i < 256; i++) {
            future = es.submit(new MonoScan(tarAddr + "." + Integer.toString(i), ss));
            futuresAll.add(future);
        }

        for (Future<boolean> f : futuresAll) {
            f.get();
        }

        ss.print();
    }

}
