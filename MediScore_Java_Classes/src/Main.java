public class Main {
    public static void main(String[] args) {
        try {
            Patient typical = new Patient();
            System.out.println(typical.calculateMediScore(Patient.respTypeValue.AIR.value, Patient.consciousnessTypeValue.ALERT.value, 15, 95, 37.1f, 6.4f, 0));
            System.out.println(typical.isFlagged());
            Patient moderate = new Patient();
            System.out.println(moderate.calculateMediScore(Patient.respTypeValue.OXYGEN.value, Patient.consciousnessTypeValue.ALERT.value, 17, 95, 37.1f, 6.4f, 0));
            System.out.println(moderate.isFlagged());
            Patient severe = new Patient();
            System.out.println(severe.calculateMediScore(Patient.respTypeValue.OXYGEN.value, 1, 23, 88, 38.5f, 6.4f, 0));
            System.out.println(severe.isFlagged());
            Patient gettingWorse = new Patient();
            System.out.println(gettingWorse.calculateMediScore(Patient.respTypeValue.AIR.value, Patient.consciousnessTypeValue.ALERT.value, 15, 95, 37.1f, 6.4f, 0));
            System.out.println(gettingWorse.isFlagged());
            System.out.println(gettingWorse.calculateMediScore(Patient.respTypeValue.OXYGEN.value, Patient.consciousnessTypeValue.ALERT.value, 15, 95, 37.1f, 9.4f, 3));
            System.out.println(gettingWorse.isFlagged());
            System.out.println(gettingWorse);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
