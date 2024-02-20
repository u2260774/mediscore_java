import java.time.LocalDateTime;

public class Patient {

    public enum consciousnessTypeValue{
        ALERT(0),
        CVPU(3);
        private final int value;
        consciousnessTypeValue(int value){
            this.value=value;
        }
    }
    public enum respTypeValue{
        AIR(0),
        OXYGEN(2);
        private final int value;
        respTypeValue(int value){
            this.value=value;
        }
    }
    private int consciousnessType=0;
    private int respType=0;
    private int prevScore = -1;
    private LocalDateTime currTime = LocalDateTime.now();
    private LocalDateTime prevTime = LocalDateTime.now();
    private int[] individualScores = {0,0,0,0,0,0};
    private int respRate=15;
    private int spo2=95;
    private float temp=37.1f;
    private float cbg=6.4f;
    private int timeSinceMeal =0;
    private int flag=0;
    public void setAll(respTypeValue rType, consciousnessTypeValue cType,int respRate, int spo2, float temp, float cbg, int timeSinceMeal){
        int currResp = this.respRate;
        int currSpo2 = this.spo2;
        float currTemp = this.temp;
        float currCbg = this.cbg;
        int currTime = this.timeSinceMeal;
        try {
            setRespRate(respRate);
            setSpo2(spo2);
            setTemp(temp);
            setCbg(cbg);
            setTimeSinceMeal(timeSinceMeal);
        } catch (Exception e) {
            setRespRate(currResp);
            setSpo2(currSpo2);
            setTemp(currTemp);
            setCbg(currCbg);
            setTimeSinceMeal(currTime);
            throw new IllegalArgumentException(e.getMessage()+" Values unchanged.");
        }
        this.respType = rType.value;
        this.consciousnessType=cType.value;
        this.prevTime = this.currTime;
        this.currTime = LocalDateTime.now();
    }

    Patient(){
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

    public void setTimeSinceMeal(int timeSinceMeal) {
        if (timeSinceMeal>=0){
            this.timeSinceMeal=timeSinceMeal;
        } else {
            throw new IllegalArgumentException("Time must be positive.");
        }
    }

    public int getPrevScore() {
        return prevScore;
    }
    public void setScore(int score) {
        this.prevScore = score;
    }
    public LocalDateTime getPrevTime() {
        return prevTime;
    }
    public LocalDateTime getCurrTime(){
        return currTime;
    }

    public int getConsciousnessType() {
        return consciousnessType;
    }
    public int getRespType() {
        return respType;
    }
    public int getRespRate() {
        return respRate;
    }
    public int getSpo2() {
        return spo2;
    }
    public float getTemp() {
        return temp;
    }
    public float getCbg() {
        return cbg;
    }
    public int getTimeSinceMeal() {
        return timeSinceMeal;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public void setScores(int[] score){
        individualScores=score;
    }

    @Override
    public String toString() {
        String warning = "";
        if (flag==-1){
            warning="\nThe patient's condition is worsening quickly.";
        }
        return "\n\nRespiration\nObservation: "+respType+"\nScore: "+individualScores[0]+
                "\n\nConsciousness\nObservation: "+consciousnessType+"\nScore: "+individualScores[1]+
                "\n\nRespiration Rate\nObservation: "+respRate+"\nScore: "+individualScores[2]+
                "\n\nSpO2\nObservation: "+spo2+"\nScore: "+individualScores[3]+
                "\n\nTemperature\nObservation: "+temp+"\nScore: "+individualScores[4]+
                "\n\nCBG\nObservation: "+cbg+"\nScore: "+individualScores[5]+
                "\n\nThe patient's final Medi score is "+prevScore+warning;
    }
}
