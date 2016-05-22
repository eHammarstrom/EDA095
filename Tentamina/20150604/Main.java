package monoscan;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException();

        String tarAddr = args[0]; // "130.235.209" on test

        MonoScan ms = new MonoScan();

        ms.scan(tarAddr);
    }

}
