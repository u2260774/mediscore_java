# Solution in Java

## MediScore_Java_Simple

This solution uses a single class with a method that takes the observations and uses LocalDateTime and ChronoUnit to measure the time between observations. It only returns the mediscore and the flag. I first considered making a patient class that would have the methods needed to compute the medi score. 

This would have made it easier to create multiple patients with room for other information about the patient. However, creating methods that are not intrinsic properties of the class seemed to break the single responsibility principle, which is why I decided to create a separate program with 2 separate classes.

I still had to store information in the class to allow for comparison with previous results. I considered other methods such as: 
making another method that takes 3 parameters (previous time, previous score and the return from the original method), to raise a flag 

using the same method but with optional parameters

adding 2 more parameters to the method, where a negative value in both previous time and previous score would indicate the first measurement

However, I decided that I liked the automation/convenience factor of this method, and moved on to work on the 2 class version and the python version.

## MediScore_Java_Classes

This program has 2 classes, a patient class and a mediscore class (with a static method, which will be used for the calculations). The patient class also has a toString method to display the patients condition, and if the patients condition is worsening.

Instead of creating separate variables to store the scores of each observation, I decided to use an array, which made it easier to set the individual scores in the patient class (for use in the toString() method). 

I have been considering adding a nested class which would hold score info, instead of an array. This will help in avoiding any indexing errors and make it easier to implement more features in the future. 
