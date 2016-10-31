package cs3500.music.view;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicSheet;
import cs3500.music.model.Note;
import cs3500.music.model.Octave;
import cs3500.music.model.Pitch;

/**
 * A view for the text representation of a Song.
 */
public class TextView implements IMusicView{

  @Override
  public void renderSong(IMusicEditor model, int index) throws IllegalArgumentException{
    MusicSheet sheet = (MusicSheet) model.getSheet(index);
    String result = renderNotes(sheet);
  }

  /**
   * Creates a String that starts with all of the present notes on the first line.
   * Following by as many beats as required to reach the farthest beat, padded with
   * spaces. The beginnings of notes are marked by X, and a sustain is marked by |. A rest is
   * simply a space. If there are no notes in the piece, the message "No notes to present"
   * will be returned instead.
   * @return the string of notes
   */
  private String renderNotes(MusicSheet sheet) {
    ArrayList<Note> notes = new ArrayList<Note>();
    for (Octave oct : Octave.values()) {
      for (Pitch pit : Pitch.values()) {
        if(oct.equals(Octave.TEN) && pit.equals(Pitch.G)) {
          break;
        }
        notes.add(new Note(pit, oct, false));
      }
    }
    String result = "";
    if (sheet.getFurthestBeat() == -1 || sheet.getHighestNote() == null
            || sheet.getLowestNote() == null) {
      return "No notes to present.";
    }

    int beginIndex = notes.indexOf(sheet.getLowestNote());
    int endIndex = notes.indexOf(sheet.getHighestNote());
    List<Note> newNotes = notes.subList(beginIndex, endIndex + 1);
    int columnLength = 5;
    result = result + "\n";
    ArrayList<Integer> beatNumbers = new ArrayList<Integer>();
    int beatNumberColumnLength = String.valueOf(sheet.getFurthestBeat()).toString().length();
    for (int n = 0; n < beatNumberColumnLength; n++) {
      result = result + " ";
    }
    result = result + "  ";
    for (int n = 0; n < newNotes.size(); n++) {
      result = result + newNotes.get(n).toString();
      for (int i = newNotes.get(n).toString().length(); i < 5; i++) {
        result = result + " ";
      }
    }
    result = result + "\n";
    for (int n = 0; n < sheet.getFurthestBeat(); n++) {
      beatNumbers.add(n);
    }
    for (int n = 0; n < beatNumbers.size(); n++) {
      Integer p = beatNumbers.get(n);
      for (int i = beatNumberColumnLength; i > p.toString().length(); i--) {
        result = result + " ";
      }
      result = result + (beatNumbers.get(n));
      result = result + "  ";
      if (!(sheet.getBeats().containsKey(beatNumbers.get(n)))) {
        for (int j = 0; j < newNotes.size(); j++) {
          result = result + "     ";
        }
      } else {
        for (int j = 0; j < newNotes.size(); j++) {
          if (sheet.getBeats().get(beatNumbers.get(n)).contains(newNotes.get(j))) {
            int indexNote = sheet.getBeats().get(beatNumbers.get(n)).indexOf(newNotes.get(j));
            if (sheet.getBeats().get(beatNumbers.get(n)).get(indexNote).getbeginningOfNote()) {
              result = result + "X" + "    ";
            } else {
              result = result + "|" + "    ";
            }
          } else {
            result = result + "     ";
          }
        }
      }
      result = result + "\n";
    }

    return result;
  }
}