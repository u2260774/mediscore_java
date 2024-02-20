## Main

Uses the MediScore and Patient class and gives output

```bash
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MediScore mediScore = new MediScore();
        try {
            Patient typical = new Patient();
            typical.setAll(Patient.respTypeValue.AIR, Patient.consciousnessTypeValue.ALERT, 15, 95, 37.1f, 6.4f, 3);
            System.out.println(Arrays.toString(mediScore.calculate(typical)));

            Patient moderate = new Patient();
            moderate.setAll(Patient.respTypeValue.OXYGEN, Patient.consciousnessTypeValue.ALERT, 17, 95, 37.1f, 6.4f, 0);
            System.out.println(Arrays.toString(mediScore.calculate(moderate)));

            Patient severe = new Patient();
            severe.setAll(Patient.respTypeValue.OXYGEN, Patient.consciousnessTypeValue.CVPU, 23, 88, 38.5f, 6.4f, 0);
            System.out.println(Arrays.toString(mediScore.calculate(severe)));

            Patient gettingWorse = new Patient();
            gettingWorse.setAll(Patient.respTypeValue.AIR, Patient.consciousnessTypeValue.ALERT, 15, 95, 37.1f, 6.4f, 0);
            System.out.println(Arrays.toString(mediScore.calculate(gettingWorse)));
            gettingWorse.setAll(Patient.respTypeValue.OXYGEN, Patient.consciousnessTypeValue.ALERT, 15, 95, 37.1f, 6.4f, 0);
            System.out.println(Arrays.toString(mediScore.calculate(gettingWorse)));
            System.out.println(gettingWorse);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
```
### Output

![java_simple output](/MediScore_Java_Classes/src/java_classes.png?raw=true)

## Patient.java

### Imports

```bash
import java.time.LocalDateTime;
```
### Class and Enums

```bash
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
```
### Variables

```bash
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
```

### setAll() method
```bash
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
```

### Default Constructor
```bash
{
   Patient(){
    }
```
### Getters and setters
```bash
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
```
### toString() method
```bash
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
```

## MediScore.java

### Imports

```bash
import java.time.temporal.ChronoUnit;
```

### Class, Method and Variables
```bash
public class MediScore {
    public int[] calculate(Patient p){
        int mediScore = 0;
        int respType = p.getRespType();
        int respRate = p.getRespRate();
        int consciousness = p.getConsciousnessType();
        float temp = p.getTemp();
        int spo2 = p.getSpo2();
        float cbg = p.getCbg();
        int timeSinceMeal = p.getTimeSinceMeal();
        int prevScore = p.getPrevScore();
        int flag = 0;
        int[] scores = {0,0,0,0,0,0};
```
### Inserting respiration and consciousness into the scores array
```bash
            //Set consciousness and respiration type
            scores[0]=respType;
            scores[1]=consciousness;
```
### Inserting Respiration Rate score
```bash
            //Set respiration rate score
            if (respRate<=8 || respRate>=25){
                scores[2] = 3;
            } else if (respRate>20){
                scores[2] = 2;
            } else if (respRate<12) {
                scores[2] = 1;
            }
```
### Inserting SpO2 score increase in score over time
```bash
        if (spo2 <= 83) {
                scores[3] = 3;
            } else if (spo2 <= 85) {
                scores[3]= 2;
            } else if (spo2 <= 87) {
                scores[3]= 1;
            } else {
                //Check if on oxygen
                if (respType != 0 && spo2>92) {
                    if (spo2 <= 94) {
                        scores[3]= 1;
                    } else if (spo2 <= 96) {
                        scores[3]= 2;
                    } else {
                        scores[3]= 3;
                    }
                }
            }
```
### Inserting temperature score
```bash
            //Set temperature score
            if (temp<=35.0){
                scores[4]= 3;
            } else if ((temp<35.0 && temp<=36.0) || (temp<=39.0 && temp > 38.0)){
                scores[4]= 1;
            } else if (temp>=39.1) {
                scores[4]= 2;
            }
```
### Inserting CBG score
```bash
            if (timeSinceMeal>2){
                if(cbg<=3.4||cbg>=6.0){
                    scores[5]= 3;
                } else if ((cbg<=3.9)||(cbg>=5.5)){
                    scores[5]= 2;
                }
            } else if (timeSinceMeal>0) {
                if(cbg<=4.4||cbg>=9.0){
                    scores[5]= 3;
                } else if ((cbg>=4.5)||(cbg>=7.9)) {
                    scores[5]= 2;
                }
            }
```
### Adding scores
```bash
        for (int i : scores){
            mediScore+=i;
        }
```
### Comparing to previous score
```bash
       //Check if previous score is set
        if (prevScore!=-1){
            if (mediScore-prevScore>=2) {
                if (ChronoUnit.HOURS.between(p.getPrevTime(), p.getCurrTime()) <= 24) {
                    flag = -1;
                }
            }
        }
```
### Setting patient variables and returning
```bash
        //set patient scores
        p.setScores(scores);
        //raise flag
        p.setFlag(flag);
        //set final mediscore
        p.setScore(mediScore);
        //return mediscore and flag
        return new int[]{mediScore,flag};
    }
}
```
