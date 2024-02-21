import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            Patient typical = new Patient();
            typical.setAll(0, 0, 15, 95, 37.1f, 6.4f, 0);
            System.out.println(Arrays.toString(typical.getScores()));
            System.out.println(typical);
            Patient moderate = new Patient();
            moderate.setAll(2, 0, 17, 95, 37.1f, 6.4f, 0);
            System.out.println(Arrays.toString(moderate.getScores()));
            System.out.println(moderate);
            Patient severe = new Patient();
            severe.setAll(2, 1, 23, 88, 38.5f, 6.4f, 0);
            System.out.println(Arrays.toString(severe.getScores()));
            System.out.println(severe);
            Patient gettingWorse = new Patient();
            gettingWorse.setAll(0, 0, 15, 95, 37.1f, 6.4f, 0);
            System.out.println(Arrays.toString(gettingWorse.getScores()));
            System.out.println(gettingWorse);
            gettingWorse.setAll(2, 0, 15, 95, 37.1f, 9.4f, 3);
            System.out.println(Arrays.toString(gettingWorse.getScores()));
            System.out.println(gettingWorse);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
