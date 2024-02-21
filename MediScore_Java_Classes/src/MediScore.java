public class MediScore {
    public static int[] calculate(int respType, int conscType, int respRate, float temp, int spo2, float cbg, int timeSinceMeal){
        int mediScore;
        int cbgScore = 0;
        int tempScore = 0;
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

        //add up scores
        mediScore = respType+conscType+respRate+tempScore+spo2+cbgScore;

        //return mediscore and individual scores
        return new int[]{mediScore,respRate,tempScore,spo2,cbgScore};
    }
}
