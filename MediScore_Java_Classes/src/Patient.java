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
    private final int[] indScores = {0,0,0,0};
    private int respRate = 0;
    private int spo2 = 0;
    private float temp = 0;
    private float cbg = 0;
    private boolean flag = false;
    public void setAll(respTypeValue rType, consciousnessTypeValue cType,int respRate, int spo2, float temp, float cbg, int timeSinceMeal){
        // store previous values
        respTypeValue prevRType = this.respType;
        consciousnessTypeValue prevCType = this.consciousnessType;
        int prevRespRate = this.respRate;
        int prevSpo2= this.spo2;
        float prevTemp = this.temp;
        float prevCbg = this.cbg;
        // begin try block to check for errors
        try {
            this.respType = rType;
            this.consciousnessType = cType;
            setRespRate(respRate);
            setSpo2(spo2);
            setTemp(temp);
            setCbg(cbg);
            verifyTimeSinceMeal(timeSinceMeal);
            // catch error, reset values and return error
        } catch (Exception e) {
            this.respType=prevRType;
            this.consciousnessType=prevCType;
            this.respRate=prevRespRate;
            this.spo2=prevSpo2;
            this.temp=prevTemp;
            this.cbg=prevCbg;
            throw new IllegalArgumentException(e.getMessage()+" Values unchanged.");
        }
        // start calculations and store return
        int[] scores = MediScore.calculate(respType.value,cType.value,respRate,temp,spo2,cbg,timeSinceMeal);
        // set individual scores
        indScores[0] = scores[0];
        indScores[1] = scores[1];
        indScores[2] = scores[2];
        indScores[3] = scores[3];
        // check if first recording and set flag
        if (mediScore!=-1) {
            flag = ChronoUnit.HOURS.between(prevTime, LocalDateTime.now()) <= 24 && (scores[4] - mediScore) > 2;
        }
        // set recording time to current time
        this.prevTime = LocalDateTime.now();
        // set mediscore
        mediScore =scores[4];
    }

    public void setRespRate(int respRate) {
        if (respRate>=0){
            this.respRate=respRate;
        } else {
            throw new IllegalArgumentException("Respiration Rate must be in positive digits.");
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
            this.cbg=(float) (Math.round(cbg * 10.0) / 10.0);
        } else {
            throw new IllegalArgumentException("CBG must be positive.");
        }
    }

    public void verifyTimeSinceMeal(int timeSinceMeal) {
        if (timeSinceMeal<0){
            throw new IllegalArgumentException("Time must be positive.");
        }
    }

    public int[] getScores(){
        return new int[]{respType.value,consciousnessType.value,indScores[0],indScores[1],indScores[2],indScores[3],mediScore};
    }

    @Override
    public String toString() {
        String warning = "";
        if (flag){
            warning="\nThe patient's condition is worsening quickly.";
        }
        return "\n\nRespiration\nObservation: "+respType.type+"\nScore: "+respType.value+
                "\n\nConsciousness\nObservation: "+consciousnessType.type+"\nScore: "+consciousnessType.value+
                "\n\nRespiration Rate\nObservation: "+respRate+"\nScore: "+ indScores[0]+
                "\n\nSpO2\nObservation: "+spo2+"\nScore: "+ indScores[1]+
                "\n\nTemperature\nObservation: "+temp+"\nScore: "+ indScores[2]+
                "\n\nCBG\nObservation: "+cbg+"\nScore: "+ indScores[3]+
                "\n\nThe patient's final Medi score is "+ mediScore +warning;
    }

}
