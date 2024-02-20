public class Main {
    public static void main(String[] args) {
        MediScore mediScore = new MediScore();
        try {
            System.out.println(mediScore.calculateMediScore(MediScore.respTypeValue.AIR, MediScore.consciousnessType.ALERT, 15, 95, 37.1f, 5.9f, 3));
            System.out.println(mediScore.isAttentionRequired());
            System.out.println(mediScore.calculateMediScore(MediScore.respTypeValue.OXYGEN, MediScore.consciousnessType.CVPU, 15, 95, 37.12f, 7.9f, 1));
            System.out.println(mediScore.isAttentionRequired());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
