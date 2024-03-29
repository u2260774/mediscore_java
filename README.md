# Solution in Java

## Question about the Enums

The portal specified that the input type for respiration and consciousness need to be Enums in the form of an Integer. I was not sure if the input itself was going to be an integer or if the enums needed an integer value assigned to them. 

First, I assumed that it meant the input would be an enum with an integer value, however, the CVPU comment said "non-zero if CVPU". This made me think that the input would be an integer (since enums are a 'set of predefined constants', and 'non-zero' is a set with infinite elements), and the function would use the enum values that were created to assign scores. However, doing so would make the enums redundant, since this could be done with if statements instead of enums.

I decided to use enums as the input for MediScore_Java_Simple, and integers as the input for MediScore_Java_Classes. I kept the enum classes in the code for the 'type' variable that I used in the toString() method.

## Issue with the table

Score 3 for '2 hours after eating' says "4.5 and below", and Score 2 says "4.5-5.8". I made the assumption that Score 3 was supposed to say "4.4 and below", similar to the row for 'When Fasting'.

![image](https://github.com/u2260774/mediScore-Aire/assets/126501906/11020b1a-a0a1-4df0-8aa7-20cbcfaec355)

## MediScore_Java_Simple

This solution uses a single class with a method that takes the observations and uses LocalDateTime and ChronoUnit to measure the time between observations. It only returns the mediscore and the flag. I first considered making a patient class that would have the methods needed to compute the medi score. 

This would have made it easier to create multiple patients with room for other information about the patient. However, creating methods that are not intrinsic properties of the class seemed to break the single responsibility principle, which is why I decided to create a separate program with 2 separate classes.

I still had to store information in the class to allow for comparison with previous results. I considered other methods such as:

making another method that takes 3 parameters (previous time, previous score and the return from the original method), to raise a flag 

using the same method but with optional parameters

adding 2 more parameters to the method, where a negative value in both previous time and previous score would indicate the first measurement

However, I decided to keep it this way since I liked the automation/convenience factor of this method, and moved on to work on the 2 class version and the python version.

## MediScore_Java_Classes

This program has 2 classes, a patient class and a mediscore class (with a static method, which will be used for the calculations).

The MediScore class has a static calculate() method which takes input and converts it into a score using the table. It returns the results in an array.

### MediScore.calculate(...)
```java
    public static int[] calculate(int respType, int conscType, int respRate, float temp, int spo2, float cbg, int timeSinceMeal){
    {...}
    return new int[]{respRate,spo2,tempScore,cbgScore,mediScore};
}
```

The patient class uses the calculateMediScore() method to take input, then uses the MediScore.calculate() method to get the scores, check how the patients condition changed, and stores the individual scores in an array and the mediscore in a variable. The patient class also has a toString() method to display the patients condition, and a isFlagged() method to display if the patients condition is worsening quickly.

### calculateMediScore(...)
```java
    public int calculateMediScore(respTypeValue rType, consciousnessTypeValue cType,int respRate, int spo2, float temp, float cbg, int timeSinceMeal){
    {...}
        int[] scores = MediScore.calculate(respType.value,cType.value,respRate,temp,spo2,cbg,timeSinceMeal);
        indScores[0] = scores[0];
        indScores[1] = scores[1];
        indScores[2] = scores[2];
        indScores[3] = scores[3];

        if (mediScore!=-1) {
            flag = ChronoUnit.HOURS.between(prevTime, LocalDateTime.now()) <= 24 && (scores[4] - mediScore) > 2;
        }

        this.prevTime = LocalDateTime.now();

        mediScore = scores[4];
        return mediScore;
}
```

### toString()
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

Instead of creating separate variables to store the scores of each observation, I decided to use an array, which made it easier to set the individual scores in the patient class (for use in the toString() method). 
