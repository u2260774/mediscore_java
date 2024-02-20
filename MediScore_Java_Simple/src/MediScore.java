import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MediScore {
    private LocalDateTime time = LocalDateTime.now();
    public enum respTypeValue{
        AIR(0),
        OXYGEN(2);
        private final int value;
        respTypeValue(int value){
            this.value=value;
        }
    }
    public enum consciousnessType{
        ALERT(0),
        CVPU(3);
        private final int value;
        consciousnessType(int value){
            this.value=value;
        }
    }
    private boolean attentionRequired = false;
    private String error;
    private int mediScore =0;
    private int prevScore = -1;
    public int calculateMediScore(respTypeValue respType, consciousnessType consc, int respRate, int spo2, float temp, float cbg, int timeSinceMeal){
        attentionRequired=false;
        try {
            //Set consciousness and respiration type
            mediScore +=respType.value;
            mediScore +=consc.value;
            //Set respiration rate
            if (respRate<0){
                error="Respiratory rate can not be less than 0 (time travellers excluded)";
                throw new IllegalArgumentException();
            }
            if (respRate<=8 || respRate>=25){
                mediScore += 3;
            } else if (respRate>20){
                mediScore += 2;
            } else if (respRate<12) {
                mediScore += 1;
            }
            //Set temperature
            if (temp>48.0 || temp<12.0){
                error="Temperature must be in Celsius";
                throw new IllegalArgumentException();
            }
            temp = (float) (Math.round(temp * 10.0) / 10.0);
            if (temp<=35.0){
                mediScore += 3;
            } else if ((temp<35.0 && temp<=36.0) || (temp<=39.0 && temp > 38.0)){
                mediScore += 1;
            } else if (temp>=39.1) {
                mediScore += 2;
            }
            if (spo2 > 100 || spo2 < 0) {
                error = "SpO2 range: 0-100";
                throw new IllegalArgumentException();
            }
            //Set spo2
            if (spo2 <= 83) {
                mediScore += 3;
            } else if (spo2 <= 85) {
                mediScore += 2;
            } else if (spo2 <= 87) {
                mediScore += 1;
            } else {
                if (respType.value != 0 && spo2>92) {
                    if (spo2 <= 94) {
                        mediScore += 1;
                    } else if (spo2 <= 96) {
                        mediScore += 2;
                    } else {
                        mediScore += 3;
                    }
                }
            }
            //Set CBG
            if (cbg < 0 || timeSinceMeal < 0){
                error="Time since meal or CBG can not be negative (time travellers excluded)";
                throw new IllegalArgumentException();
            }
            if (timeSinceMeal>2){
                if(cbg<=3.4||cbg>=6.0){
                    mediScore += 3;
                } else if ((cbg<=3.9)||(cbg>=5.5)){
                    mediScore += 2;
                }
            } else if (timeSinceMeal>0) {
                if(cbg<=4.5||cbg>=9.0){
                    mediScore += 3;
                } else if ((cbg>=4.5)||(cbg>=7.9)) {
                    mediScore += 2;
                }
            }
        } catch (Exception e){
            error=error+". Values unchanged.";
            throw new RuntimeException();
        }
        if (prevScore!=-1){
            if (mediScore-prevScore>=2) {
                if (ChronoUnit.HOURS.between(time, LocalDateTime.now()) <= 24) {
                    attentionRequired=true;
                }
            }
        }
        prevScore=mediScore;
        time = LocalDateTime.now();
        return mediScore;
    }

    public boolean isAttentionRequired() {
        return attentionRequired;
    }
    public String getError(){
        return error;
    }
}