1) The main class to be run is QueueSimulator.java and the input file 'input.txt' 
is present at 'src/main/resources'.
Modify its contents for each input scenario and do a maven install and then run the 
QueueSimulator.java

for example, for the below input in ‘input.txt’

2
A 1 2
A 1 3
A 2 1
A 2 1


output should be like below,

Simulation start dateTime is 27:09:14:13:26 PDT
Customer - [type=A, timeOffSet=1, numbOfItems=2] enqueued in register number 1, at 27:09:14:13:27 PDT
Customer - [type=A, timeOffSet=1, numbOfItems=3] enqueued in register number 2, at 27:09:14:13:27 PDT
Customer - [type=A, timeOffSet=2, numbOfItems=1] enqueued in register number 1, at 27:09:14:13:28 PDT
Customer - [type=A, timeOffSet=2, numbOfItems=1] enqueued in register number 2, at 27:09:14:13:28 PDT
Customer - [type=A, timeOffSet=1, numbOfItems=2] dequeued from register number 1, at 27:09:14:13:29 PDT
Customer - [type=A, timeOffSet=2, numbOfItems=1] dequeued from register number 1, at 27:09:14:13:30 PDT
Customer - [type=A, timeOffSet=1, numbOfItems=3] dequeued from register number 2, at 27:09:14:13:33 PDT
Customer - [type=A, timeOffSet=2, numbOfItems=1] dequeued from register number 2, at 27:09:14:13:35 PDT
Simulation end dateTime is 27:09:14:13:35 PDT
Total Processing Time in minutes is - 9