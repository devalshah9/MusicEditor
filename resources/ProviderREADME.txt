Music Editor Overview
=====================

Project By: 
- Sam Lazarus (slazarus@ccs.neu.edu, github: slazarus)
- Soo Jung Rhee (rhees@ccs.neu.edu, github: rhees)

Main is a class which can be used to run the entire program.

The Model
---------

IMusicEditorModel is an interface which details the methods that a model for the music editor should implement.

AMusicEditorModel is an abstract class which implements methods that are implementable using only other methods within the IMusicEditorModel interface and therefore is implementation independent. It also defines the abstract method toFixedGrid which is used in its implementation of getState. This allows subclasses to define ToFixedGrid rather than defining their own printing thus further abstracting out implementation.

FixedGrid is a class which contains a 2D array of an enum type which can be one of empty, head, or sustain. From a FixedGrid, a piece of music is easy to convert to a visual format as you just loop throgh the 2D array and output the image or text corresponding to the enum instance at the current location. For ease of conversion between models and FixedGrids, FixedGrid has a constructor which takes an ArrayList of Note.

MusicEditorModel is a concrete class extension of AMusicEditorModel which uses a ArrayList<Note> to hold all the Notes in the song. It implements the only methods AMusicEditorModel doesn't, namely: addNote, removeNote, and getNotes, which, handilly, are extremely easy due to the nature of ArrayLists.

PitchType is an enum type whos instances represent the notes on the chromatic scale.

Pitch is a class which represents a concrete pitch. It has a PitchType and an octave. Octaves must be between 0 and 999 for display purposes.

Note contains a Pitch, a start beat and a duration. It represents a pitch played at a time.

MusicEditorModelBuilder is an implementation of CompositionBuilder which creates IMusicEditorModels.

The Controller
--------------

IMusicEditorController is an interface for controllers of the music editor. 
There were originally two controllers, each taking in either a Gui view or
nonGui view and a model. But having to determine which controller to use 
depending on the circumstances was inconvenient because we had to keep changing
it. Now there's only one controller that takes in a view and a model.

KeyboardHandler handles the keyboard inputs. It maps a Runnable to an integer, 
which runs whenever the integer is called. 

MouseEventHandler handles the mouse events. We created each method for each mouse
event, such as mouse pressed. When mouse is pressed, the MouseEventPressed
will be called. Because there aren't that many mouse events, you can write
corresponding methods for all the events.

MusicEditorController intermediates between the model and the view. It handles
the mouse event as well as configures keyboard listener. 



The View
--------

IMusicEditorView is the interface which all views must comply with.

IMouseEventHandler is the interface which responds to mouse events for the view.

IMusicEditorGuiView is the sub interface that extends IMusicEditorPlayableView.

IMusicEditorPlayableView represents a playable view for the music editor. It plays and stops the music editor and gets or sets the play head.

ViewFactory is a factory class which creates views based on a string which determines the type of view.

ANonGuiView is an abstract class for non-gui views which uses an ArrayList to keep track of all the notes within the piece of music.

TextualView is an ANonGuiView subclass which consists of a string representation of a view which is printed to the console (or other Appendable).

MidiView is an ANonGuiView subclass which plays a the song using midi instruments.

GuiViewFrame is an implementation of IMusicEditorView which displays the view using java swing.

DisplayPanel is a JPanel subclass which actually renders all graphics for the GuiViewFrame.

AudioVisualView is a composite view that plays a song using midi instruments while displaying the view using java swing.

GUiViewFrameMouseListener is a MouseListener that renders the display depending on the mouse event.

MouseSelection represents a selection made by the user with the mouse. It can either be a note, or a location.
