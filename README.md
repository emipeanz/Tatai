# TataiPrototype

### Synopsis 

The Tatai prototype is designed to help children who speak some Maori learn 
to pronunciate Maori numbers from 1-99.

This project is a prototype for the main application, Tatai, where users 
test their numeracy skills in Maori.

### Prerequsites

To run this project it is necessary to install HTK voice recognition 
from: http://htk.eng.cam.ac.uk/download.shtml
Create and account and install HTK. Make sure you also have the libaries 
provided for this project by Dr. Catherine Watson.

Install java fx from the command line by typing "sudo apt install openjfx"

## Set up and running of application

To make use of this project you will want to ensure that you have a 
functioning microphone and speakers.

After installing both of the above, you will need to place the 
TataiPrototype.jar into the folder containing the HTK material as indicated 
by the folder structure below. Make sure all direcroties and files are as
they are represented below

Documents
|	HTK
|	|	MaoriNumbers
|	|	|	HMMs
|	|	|	RecordingDir
|	|	|	user
|	|	|	GoSpeech
|	|	|	recout.mlf
|	|	|	TataiPrototype.jar

Then run the jar file from the command line by typing the following command: 
"java -jar TataiPrototype.jar &> /dev/null"

### Contributors

Maddie Beagley
Emilie Pearce