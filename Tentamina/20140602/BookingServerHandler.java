package booking;

public class BookingServerHandler implements runnable {
    private BookingManagerSync bms;
    private Socket socket;

    public BookingServerHandler(BookingManagerSync bms, Socket socket) {
        this.bm = bm;
        this.socket = socket;
    }

    public void run() {
        try {
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            BufferedReader br =
                new BufferedReader(new InputStreamReader(is, "UTF-8"));
            BufferedWriter bw =
                new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            String[] input = br.readLine().split(" ");

            boolean isSuccess = false;

            switch (input[0].toLowerCase()) {
                case "create":
                    isSuccess = bms.create(input[1]);
                    break;
                case "book":
                    isSuccess = bms.book(input[1],
                        input[2],
                        input[3],
                        input[4],
                        input[5]);
                    break;
                case "delete":
                    isSuccess = bms.delete(input[1],
                        input[2],
                        input[3]);
                    break;
                case "show":
                    // SHOW RETURNS LIST TO CLIENT
                    String[] showData = bms.show(input[1], input[2]);
                    for (String s : showData) {
                        bw.write(s + "\n");
                    }
                    isSuccess = true;
                    break;
                case "watch":
                    // RETURN LIVE DATA
                    break;
            }

            (isSuccess) ? bw.write("success\n") : bw.write("failure\n");
            bw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
