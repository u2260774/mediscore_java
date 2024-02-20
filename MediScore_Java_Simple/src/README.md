## Main

Runs MediScore.java and displays mediscore or error

```bash
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
```
### Output

![java_simple output](/MediScore_Java_Simple/src/java_simple.png?raw=true)

## MediScore.java

### Imports

```bash
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
```
### Class and Enums

```bash
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
```
### Variables

```bash
    private boolean attentionRequired = false;
    private int mediScore =0;
    private int prevScore = -1;
```

### Method Parameters
```bash
    public int calculateMediScore(respTypeValue respType, consciousnessType consc, int respRate, int spo2, float temp, float cbg, int timeSinceMeal)
```

### Reset Flag and mediScore. 
```bash
{
        mediScore = 0;
        attentionRequired=false;
```
### Begin try block and set Respiration type and consciousness score.
```bash
        try {
            //Set consciousness and respiration type
            mediScore +=respType.value;
            mediScore +=consc.value;
```
### Respiration rate score
```bash
            //Set respiration rate
            if (respRate<0){
                throw new IllegalArgumentException("Respiratory rate can not be less than 0 (time travellers excluded)");
            }
            if (respRate<=8 || respRate>=25){
                mediScore += 3;
            } else if (respRate>20){
                mediScore += 2;
            } else if (respRate<12) {
                mediScore += 1;
            }
```

### Temperature score
```bash
            //Set temperature
            if (temp>48.0 || temp<12.0){
                throw new IllegalArgumentException("Temperature must be in Celsius");
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
                throw new IllegalArgumentException("SpO2 range: 0-100");
            }
```
### SpO2 score
```bash
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
```
### CBG score
```bash
            //Set CBG
            if (cbg < 0 || timeSinceMeal < 0){
                throw new IllegalArgumentException("Time since meal or CBG can not be negative (time travellers excluded)");
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
            throw new RuntimeException(e.getMessage()+". Values unchanged.");
        }
```
### Check increase in score over time
```bash
        if (prevScore!=-1){
            if (mediScore-prevScore>=2) {
                if (ChronoUnit.HOURS.between(time, LocalDateTime.now()) <= 24) {
                    attentionRequired=true;
                }
            }
        }
```
### Change values
```bash
        prevScore=mediScore;
        time = LocalDateTime.now();
        return mediScore;
    }
```

### Flag method
```bash
    public boolean isAttentionRequired() {
        return attentionRequired;
    }
}
```
