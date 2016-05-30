package urlmapper;

public class URLMapper {

    public static List<String> mapper(List<String> name_list)
        throws InterruptedException, ExecutionException {
        // PUT CODE HERE

        ExecutorService es = Executors.newFixedThreadPool(32);

        List<Future<String>> fList = new ArrayList<>();

        for (String str : name_list) {
            fList.add(es.submit(new URLRunner(str)));
        }

        List<String> returnList = new ArrayList<>();

        for (Future<String> ft : fList) {
            String temp = ft.get();

            if (temp != null) {
                returnList.add(temp);
            }
        }

        es.shutdown();

        return returnList;
    }

}
