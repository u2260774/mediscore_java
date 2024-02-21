public class Main {
    public static void main(String[] args) {
        try {
            Patient typical = new Patient();
            System.out.println(typical.calculateMediScore(0, 0, 15, 95, 37.1f, 6.4f, 0));
            System.out.println(typical.isFlagged());
            Patient moderate = new Patient();
            System.out.println(moderate.calculateMediScore(2, 0, 17, 95, 37.1f, 6.4f, 0));
            System.out.println(moderate.isFlagged());
            Patient severe = new Patient();
            System.out.println(severe.calculateMediScore(2, 1, 23, 88, 38.5f, 6.4f, 0));
            System.out.println(severe.isFlagged());
            Patient gettingWorse = new Patient();
            System.out.println(gettingWorse.calculateMediScore(0, 0, 15, 95, 37.1f, 6.4f, 0));
            System.out.println(gettingWorse.isFlagged());
            System.out.println(gettingWorse.calculateMediScore(2, 0, 15, 95, 37.1f, 9.4f, 3));
            System.out.println(gettingWorse.isFlagged());
            System.out.println(gettingWorse);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
