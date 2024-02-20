import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            Patient typical = new Patient();
            typical.setAll(Patient.respTypeValue.AIR, Patient.consciousnessTypeValue.ALERT, 15, 95, 37.1f, 6.4f, 3);
            System.out.println(Arrays.toString(MediScore.calculate(typical)));

            Patient moderate = new Patient();
            moderate.setAll(Patient.respTypeValue.OXYGEN, Patient.consciousnessTypeValue.ALERT, 17, 95, 37.1f, 6.4f, 0);
            System.out.println(Arrays.toString(MediScore.calculate(moderate)));

            Patient severe = new Patient();
            severe.setAll(Patient.respTypeValue.OXYGEN, Patient.consciousnessTypeValue.CVPU, 23, 88, 38.5f, 6.4f, 0);
            System.out.println(Arrays.toString(MediScore.calculate(severe)));

            Patient gettingWorse = new Patient();
            gettingWorse.setAll(Patient.respTypeValue.AIR, Patient.consciousnessTypeValue.ALERT, 15, 95, 37.1f, 6.4f, 0);
            System.out.println(Arrays.toString(MediScore.calculate(gettingWorse)));
            gettingWorse.setAll(Patient.respTypeValue.OXYGEN, Patient.consciousnessTypeValue.ALERT, 15, 95, 37.1f, 6.4f, 0);
            System.out.println(Arrays.toString(MediScore.calculate(gettingWorse)));
            System.out.println(gettingWorse);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
