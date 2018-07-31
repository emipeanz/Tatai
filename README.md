# Tatai Maori Mathematics pronunciation tool

Tatai is designed to help children who speak Maori learn to pronounce Maori numbers from 1-99 in conjunction with providing simple mathematical questions to answer. Made for use in a school enviroment where school children can use it in the class, and teacher can assist by making practice math equation lists

### Key Features
* Ability to choose difficult of mathematical questions to answer
![Image of Difficulty screen](https://github.com/emipeanz/Tatai/blob/master/docs/images/HomeScreen.png)
* Provide feedback on mathematical competency
![Image of Level Completion Screen](https://github.com/emipeanz/Tatai/blob/master/docs/images/FinishedLevelScreen.png)
* Dynamic progress bars to keep user engaged
![Image of Level Screen](https://github.com/emipeanz/Tatai/blob/master/docs/images/LevelScreen2.png)
* User can make custom equation lists or practice with randomly generated ones
![Image of Custom Screen](https://github.com/emipeanz/Tatai/blob/master/docs/images/CustomScreen.png)
* User statistics are tracked on all difficulties 
![Image of Statistics Screen](https://github.com/emipeanz/Tatai/blob/master/docs/images/StatsScreen.png)
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

To run this project, it is necessary to install HTK voice recognition 
from: http://htk.eng.cam.ac.uk/download.shtml
Create and account and install HTK. Make sure you also have the libraries 
provided for this project by Dr. Catherine Watson.

Install java fx from the command line by typing
```
sudo apt install openjfx
```

### Installing

To make use of this project you will want to ensure that you have a 
functioning microphone and speakers.

After installing both above, you will need to place the 
TataiPrototype.jar into the folder containing the HTK material as indicated 
by the folder structure below. Make sure all directories and files are as
they are represented below

Documents
|   HTK
|   |   MaoriNumbers
|   |   |   HMMs
|   |   |   RecordingDir
|   |   |   user
|   |   |   GoSpeech
|   |   |   recout.mlf
|   |   |   TataiPrototype.jar

Then run the jar file from the command line by typing the following command: 
```
java -jar TataiPrototype.jar &> /dev/null
```

## Authors

* **Emilie Pearce** - (https://github.com/emipeanz)
* **Maddie Beagley** - (https://github.com/maddiebeagley)

## Acknowledgments

* Software Engineering lecturers at the University of Auckland
