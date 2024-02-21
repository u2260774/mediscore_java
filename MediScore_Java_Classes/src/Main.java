public class Main {
    public static void main(String[] args) {
        try {
            Patient typical = new Patient();
            typical.setAll(Patient.respTypeValue.AIR, Patient.consciousnessTypeValue.ALERT, 15, 95, 37.1f, 6.4f, 0);
            System.out.println(typical);
            Patient moderate = new Patient();
            moderate.setAll(Patient.respTypeValue.OXYGEN, Patient.consciousnessTypeValue.ALERT, 17, 95, 37.1f, 6.4f, 0);
            System.out.println(moderate);
            Patient severe = new Patient();
            severe.setAll(Patient.respTypeValue.OXYGEN, Patient.consciousnessTypeValue.CVPU, 23, 88, 38.5f, 6.4f, 0);
            System.out.println(severe);
            Patient gettingWorse = new Patient();
            gettingWorse.setAll(Patient.respTypeValue.AIR, Patient.consciousnessTypeValue.ALERT, 15, 95, 37.1f, 6.4f, 0);
            System.out.println(gettingWorse);
            gettingWorse.setAll(Patient.respTypeValue.OXYGEN, Patient.consciousnessTypeValue.ALERT, 15, 95, 37.1f, 9.4f, 3);
            System.out.println(gettingWorse);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
