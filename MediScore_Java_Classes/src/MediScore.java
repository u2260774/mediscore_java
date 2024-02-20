import java.time.temporal.ChronoUnit;

public class MediScore {
    public static int[] calculate(Patient p){
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
            //Set consciousness and respiration type
            scores[0]=respType;
            scores[1]=consciousness;

            //Set respiration rate score
            if (respRate<=8 || respRate>=25){
                scores[2] = 3;
            } else if (respRate>20){
                scores[2] = 2;
            } else if (respRate<12) {
                scores[2] = 1;
            }

            //Set spo2 score
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

            //Set temperature score
            if (temp<=35.0){
                scores[4]= 3;
            } else if ((temp<35.0 && temp<=36.0) || (temp<=39.0 && temp > 38.0)){
                scores[4]= 1;
            } else if (temp>=39.1) {
                scores[4]= 2;
            }
            //Set CBG score, Check if fasting
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
        for (int i : scores){
            mediScore+=i;
        }
        //Check if previous score is set
        if (prevScore!=-1){
            if (mediScore-prevScore>=2) {
                if (ChronoUnit.HOURS.between(p.getPrevTime(), p.getCurrTime()) <= 24) {
                    flag = -1;
                }
            }
        }
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
