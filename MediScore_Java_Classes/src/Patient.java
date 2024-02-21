import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Patient {
    Patient(){
    }
    public enum consciousnessTypeValue{
        ALERT(0,"Alert"),
        CVPU(3,"CVPU");
        private final int value;
        private final String type;
        consciousnessTypeValue(int value, String type){
            this.value=value;
            this.type=type;
        }
    }
    public enum respTypeValue{
        AIR(0,"Air"),
        OXYGEN(2,"Oxygen");
        private final int value;
        private final String type;

        respTypeValue(int value, String type){
            this.value=value;
            this.type=type;
        }
    }
    private consciousnessTypeValue consciousnessType=consciousnessTypeValue.ALERT;
    private respTypeValue respType = respTypeValue.OXYGEN;
    private int mediScore = -1;
    private LocalDateTime prevTime = LocalDateTime.now();
    private final int[] individualScores = {0,0,0,0};
    private int respRate = 0;
    private int spo2 = 0;
    private float temp = 0;
    private float cbg = 0;
    private boolean flag = false;
    public void setAll(respTypeValue rType, consciousnessTypeValue cType,int respRate, int spo2, float temp, float cbg, int timeSinceMeal){
        flag=false;
        this.respType = rType;
        this.consciousnessType = cType;
        try {
            setRespRate(respRate);
            setSpo2(spo2);
            setTemp(temp);
            setCbg(cbg);
            verifyTimeSinceMeal(timeSinceMeal);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage()+" Values unchanged.");
        }
        int[] scores = MediScore.calculate(respType.value,cType.value,respRate,temp,spo2,cbg,timeSinceMeal);
        individualScores[0] = scores[1];
        individualScores[1] = scores[2];
        individualScores[2] = scores[3];
        individualScores[3] = scores[4];
        if (mediScore!=-1) {
            flag = ChronoUnit.HOURS.between(prevTime, LocalDateTime.now()) <= 24 && (scores[0] - mediScore) > 2;
        }
        this.prevTime = LocalDateTime.now();
        mediScore =scores[0];
    }

    public void setRespRate(int respRate) {
        if (respRate>=0){
            this.respRate=respRate;
        } else {
            throw new IllegalArgumentException("Respiration Rate must be in range 0-100.");
        }
    }

    public void setSpo2(int spo2) {
        if (spo2>=0){
            this.spo2=spo2;
        } else {
            throw new IllegalArgumentException("SpO2 must be in range 0-100.");
        }
    }

    public void setTemp(float temp) {
        if (temp<48.0 && temp>12.0){
            this.temp=(float) (Math.round(temp * 10.0) / 10.0);
        } else {
            throw new IllegalArgumentException("Temperature must be in celsius.");
        }
    }

    public void setCbg(float cbg) {
        if (cbg>=0){
            this.cbg=cbg;
        } else {
            throw new IllegalArgumentException("CBG must be positive.");
        }
    }

    public void verifyTimeSinceMeal(int timeSinceMeal) {
        if (timeSinceMeal<0){
            throw new IllegalArgumentException("Time must be positive.");
        }
    }


    @Override
    public String toString() {
        String warning = "";
        if (flag){
            warning="\nThe patient's condition is worsening quickly.";
        }
        return "\n\nRespiration\nObservation: "+respType.type+"\nScore: "+respType.value+
                "\n\nConsciousness\nObservation: "+consciousnessType.type+"\nScore: "+consciousnessType.value+
                "\n\nRespiration Rate\nObservation: "+respRate+"\nScore: "+individualScores[0]+
                "\n\nTemperature\nObservation: "+temp+"\nScore: "+individualScores[1]+
                "\n\nSpO2\nObservation: "+spo2+"\nScore: "+individualScores[2]+
                "\n\nCBG\nObservation: "+cbg+"\nScore: "+individualScores[3]+
                "\n\nThe patient's final Medi score is "+ mediScore +warning;
    }

}
