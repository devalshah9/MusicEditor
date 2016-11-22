Anirudh Katipally

                      ---------------------------------------------- MODEL ------------------------------------------------------------
My MusicEditor model was made bottom up. It consists firstly of two enums, Pitch and Octave. I chose to represent Pitch as an enum with 12 different types,
which were the pitches given to us in the assignment. Octave goes from 0 to 10. This enum classification allows me to protect my implementation from a user
trying to give it bogus notes. From there, I made a Note class. A Note consists of a Pitch, Octave, and boolean. The Pitch and Octave use the enums
to determine the note's characteristics, and the boolean indicates whether this note is a sustain or a beginning of a new note. This is important in determining how
overlap, adding notes, and printing the state of a sheet of music. The note's fields are all private, with public methods that allow a note's respective
fields' values to be returned. It also has an overriden definition of equals and compareTo, because notes should be compared only on their pitch and
octave, and not the boolean indicating whether its a sustain or not. To adapt to the requirements of the view which is described below, notes now also
have a volume and instrument field.

From there, I have a MusicSheet class. A MusicSheet has one field, a TreeMap that takes Integers as keys and maps those keys to ArrayLists of Notes.
This is how I chose to represent a piece of music essentially. If a key is not present in the treemap, then the entire beat is a rest. If the key is present,
then it will have the notes that are mapped to it readily available for manipulation. This representation makes the most sense to me, because a sheet of music
does not have a finite length. In a case where a data structure has no finite length, it's a good idea to use a HashMap to easily place more values and retrieve
a stored value with constant time access. Representing the values mapped to these keys as ArrayLists of Notes makes sense as well, as they do not require any special
manipulation other than simply keeping track of what they are.

A MusicSheet has several methods to manipulate it. First is adding notes. Adding notes takes in a note,  int duration, int beat, and a boolean. 
The first three fields are self explanatory, but the boolean is for a special case that I documented in my code. The way my code handles
adding notes with overlap and other such cases is also documented in my code.

A MusicSheet can also remove notes, where it will delete a note and any notes of that pitch and octave in the next beat until it reaches a rest
or it finds a note of the same pitch and octave that is a beginning of a new note. Again, all special cases are documented in my code.

A MusicSheet also has methods to get its highest and lowest notes, as well as its farthest best, which are all useful in determining the state of the
sheet in String form. 

A MusicSheet has a override for toString() that prints the state of the sheet in this format:

                  C5   C#5  D5   D#5  E5   F5   F#5  G5   G#5  A5   A#5  B5   
              "1                                               X              
              "2                                               |         X  
              "3  X                                            |         | 
              "4  |                                            |         |    
              "5  |                                                      |  
              "6                                                         |  

A MusicSheet has a method that returns its HashMap so that we can keep the field private for it.

A MusicSheet has a method that gets the duration of a note, given a note and the beat that it starts at.

And finally, a MusicSheet can determine the amountOfBeats that it has overall.

Above the MusicSheet, I have MusicEditor class. A MusicEditor has an ArrayList of MusicSheets, and can create a new sheet by calling the method for it
which adds a new empty music sheet to the ArrayList of sheets. To compromise with the requirements of the View, a MusicEditor also has a tempo field.

A MusicEditor can also add notes, one at a time.

A MusicEditor can take two of the sheets in its ArrayList and overlay the notes of one on top of the other. Again, special cases for notes
are handled in the code and are documented.

A MusicEditor can take two of its sheets and play one directly after the other ends.

A MusicEditor can take a note and the beat it starts at, and edit the notes length.

A MusicEditor can delete a note entirely.

A MusicEditor can produce a list of all of the notes in one of its Sheets through getNotes.

Finally, a MusicEditor can get the state of any of its sheets, printed as it is above.

Above the MusicEditor, there is the interface IMusicEditor. All the methods described above are promised in the inteface, and there are 
no other public facing methods in the MusicEditor class other than what is promised in IMusicEditor.

                     ---------------------------------------------- VIEW ------------------------------------------------------------
The View for this Project has three different options, a visual view, an audio view, and a console view. To get the information to render these views,
there are the classes in the util module, MusicReader and MusicBuilder which implements CompositionBuilder. A MusicReader is able to take text files
which are lists of notes that are rendered with the tempo at the top and each note below them, with their start beat, duration, instrument, frequency,
and volume listed. The Builder takes this information and produces a ViewModel.

A ViewModel is a class that acts as a barrier between the Model and View. This ViewModel carries much of the functionality of the MusicEditor,
storing all of the notes in the piece as well as its tempo and has getter methods that allows the view to access this information without
the ability to directly see the model.

The View itself has three different classes that carry the functionalities of its three different views. The AudibleView uses the MIDI API from the Java
sound library, and for each note in the piece sends a ShortMessage to a MidiReceiever which is able to play the sound directly on the computer.
The VisualView has three different main components. The NotesPanel which draws the rectangles indicating where notes are and where they begin,
the NotesLabelPanel which labels the notes on the left of the screen, and the BeatsPanel which labels beat number on the top of the screen. These are all put
together in a Scrollable panel, which is itself placed in a frame and displayed directly on the screen. Finally, the TextView is a view that outputs
to the console, rendering notes as they're rednered in the getSheetState method described in the Model.

To run the view, there is a MusicEditor runtime class that has a main method, which takes in two arguments. The first argument is tha path to the text file
containing the song information to play. The second argument is the kind of view to display, which is either 'midi', 'visual', or 'console'.


                     ---------------------------------------------- COMPOSITE ------------------------------------------------------------
To use the composite view and controller, press the 'Home' key. Fn + <- on Mac.
For 'End' press 'End'. Fn + -> for Mac.
For scrolling in any direction, use the arrow keys.
For adding a measure,