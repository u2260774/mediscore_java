## Main

Sets scores of the Patient class and gives output

```java
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
```
### Output

Output for the 'severe' patient.

<img width="226" alt="java_classes" src="https://github.com/u2260774/mediscore_java/assets/126501906/420e1caa-efc0-491a-a3b5-afd3ae0bb7c9">

## Patient.java

### Imports

```java
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
```
### Constructor and Enums

I decided to add a type field to the enums for the toString() method.

```java
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
```
### Default values and variables

The default values have been set to those of a healthy patient, and the mediScore has been set to -1 to indicate the first reading

```java
    private consciousnessTypeValue consciousnessType=consciousnessTypeValue.ALERT;
    private respTypeValue respType = respTypeValue.AIR;
    private int mediScore = -1;
    private LocalDateTime prevTime = LocalDateTime.now();
    private final int[] individualScores = {0,0,0,0};
    private int respRate = 0;
    private int spo2 = 0;
    private float temp = 0;
    private float cbg = 0;
    private boolean flag = false;
```

### setAll() method

Takes in all the observations, Stores original values, tries to set values, calls MediScore's calculate method and checks if patient condition changed quickly

```java
public void setAll(int rType, int cType,int respRate, int spo2, float temp, float cbg, int timeSinceMeal){
        // store previous values
        respTypeValue prevRType = this.respType;
        consciousnessTypeValue prevCType = this.consciousnessType;
        int prevRespRate = this.respRate;
        int prevSpo2= this.spo2;
        float prevTemp = this.temp;
        float prevCbg = this.cbg;
        // begin try block to check for errors
        try {
            setRespType(rType);
            setConsciousnessType(cType);
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
```

### Getters and setters

The setters (and verifyTimeSinceMeal()) verify inputs and send an error message accordingly. The getScore() method returns all scores, including the mediscore, in an array.

```java
    public void setRespType(int rType){
        if (rType==0){
            this.respType=respTypeValue.AIR;
        } else if (rType==2) {
            this.respType=respTypeValue.OXYGEN;
        } else {
            throw new IllegalArgumentException("Respiration type must be either Air(0) or Oxygen(2).");
        }
    }
    public void setConsciousnessType(int cType){
        if (cType==0){
            this.consciousnessType=consciousnessTypeValue.ALERT;
        } else {
            this.consciousnessType=consciousnessTypeValue.CVPU;
        }
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
```
### toString() method

Displays all scores, along with a warning if the patients condition is worsening using the flag.

```java
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
```

## MediScore.java

### Class, Method and Variables

This has cbgScore and tempScore variables since those inputs are floats and the scores need to be ints.

```java
public class MediScore {
    public static int[] calculate(int respType, int conscType, int respRate, float temp, int spo2, float cbg, int timeSinceMeal){
        int cbgScore = 0;
        int tempScore = 0;
```
### Calculating the respiration rate score
```java
           //Set respiration rate score
            if (respRate<=8 || respRate>=25){
                respRate = 3;
            } else if (respRate<=11){
                respRate = 1;
            } else if (respRate>=21) {
                respRate = 2;
            } else{
                respRate = 0;
            }
```
### Calculating the SpO2 score
```java
            //Set spo2 score
            if (spo2 <= 83) {
                spo2 = 3;
            } else if (spo2 <= 85) {
                spo2 = 2;
            } else if (spo2 <= 87) {
                spo2 = 1;
                //Check if on oxygen
            } else if (spo2 <=92 || respType == 0) {
                spo2 = 0;
            } else {
                if (spo2<=94){
                    spo2=1;
                } else if (spo2<=96) {
                    spo2=2;
                } else {
                    spo2=3;
                }
            }
```
### Calculating temperature score
```java
            //Set temperature score
            if (temp<=35.0){
                tempScore= 3;
            } else if (temp<=36.0){
                tempScore= 1;
            } else if (temp>=39.1) {
                tempScore=2;
            } else if (temp>=38.1) {
                tempScore=1;
            }
```
### Calculating cbg score
```java
            //Set CBG score, Check if fasting
            if (timeSinceMeal>2){
                if(cbg<=3.4||cbg>=6.0){
                    cbgScore= 3;
                } else if ((cbg<=3.9)||(cbg>=5.5)){
                    cbgScore= 2;
                }
            } else if (timeSinceMeal>0) {
                if(cbg<=4.4||cbg>=9.0){
                    cbgScore= 3;
                } else if ((cbg>=4.5)||(cbg>=7.9)) {
                    cbgScore= 2;
                }
            }
```
### Adding scores and returning in an array
```java
        int mediScore = respType+conscType+respRate+tempScore+spo2+cbgScore;

        //return mediscore and individual scores
        return new int[]{respRate,spo2,tempScore,cbgScore,mediScore};
    }
}
```
