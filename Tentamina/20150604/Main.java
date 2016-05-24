package monoscan;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException();

        String tarAddr = args[0]; // "130.235.209" on test

        ServerStats servers = new ServerStats();
        MonoScan ms = new MonoScan(servers);

        ms.scan(tarAddr);
        servers.print();
    }

}
