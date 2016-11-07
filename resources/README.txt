Anirudh Katipally

My MusicEditor model was made bottom up. It consists firstly of two enums, Pitch and Octave. I chose to represent Pitch as an enum with 12 different types,
which were the pitches given to us in the assignment. Octave goes from 0 to 10. This enum classification allows me to protect my implementation from a user
trying to give it bogus notes. From there, I made a Note class. A Note consists of a Pitch, Octave, and boolean. The Pitch and Octave use the enums
to determine the note's characteristics, and the boolean indicates whether this note is a sustain or a beginning of a new note. This is important in determining how
overlap, adding notes, and printing the state of a sheet of music. The note's fields are all private, with public methods that allow a note's respective
fields' values to be returned. It also has an overriden definition of equals and compareTo, because notes should be compared only on their pitch and
octave, and not the boolean indicating whether its a sustain or not.

From there, I have a MusicSheet class. A MusicSheet has one field, a HashMap that takes Integers as keys and maps those keys to ArrayLists of Notes.
This is how I chose to represent a piece of music essentially. If a key is not present in the hashmap, then the entire beat is a rest. If the key is present,
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
which adds a new empty music sheet to the ArrayList of sheets.

A MusicEditor can also add notes, one at a time.

A MusicEditor can take two of the sheets in its ArrayList and overlay the notes of one on top of the other. Again, special cases for notes
are handled in the code and are documented.

A MusicEditor can take two of its sheets and play one directly after the other ends.

A MusicEditor can take a note and the beat it starts at, and edit the notes length.

A MusicEditor can delete a note entirely.

Finally, a MusicEditor can get the state of any of its sheets, printed as it is above.

Above the MusicEditor, there is the interface IMusicEditor. All the methods described above are promised in the inteface, and there are 
no other public facing methods in the MusicEditor class other than what is promised in IMusicEditor.
