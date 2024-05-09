public class Main {
    public static void main(String[] argv) throws Exception {
        if (argv.length != 1) {
            System.out.println("Usage: BO Number <bo" + "_number>");
            System.exit(1);
        }
        int boNumber = Integer.parseInt(argv[0]);
        BO.RabbitmqConfig.sendChanges(boNumber);
        HO.RabbitmqConfig.updateHo();
    }
}