package cs3500.music.view;

import cs3500.music.commons.Note;
import cs3500.music.commons.Octave;
import cs3500.music.commons.Pitch;
import cs3500.music.model.IMusicEditor;
import cs3500.music.model.MusicEditor;
import cs3500.music.model.ViewModel;
import cs3500.music.model.IViewModel;
import cs3500.music.util.CompositionBuilder;


public class MusicBuilder implements CompositionBuilder<IViewModel> {

  IMusicEditor editor = new MusicEditor();
  int bpm;

  @Override
  public IViewModel build() {
    IViewModel model = new ViewModel(this.editor, 0, 4);
    return model;
  }

  @Override
  public CompositionBuilder setTempo(int tempo) {
    this.bpm = 60000000/tempo;
    return this;
  }

  @Override
  public CompositionBuilder addNote(int start, int end, int instrument, int pitch, int volume) {
    Pitch notePitch = (Pitch.values()[pitch % 12]);
    Octave noteOctave = (Octave.values()[pitch / 12]);
    int duration = end - start;
    this.editor.createNewSheet();
    this.editor.addSingleNote(0, new Note(notePitch, noteOctave, true, instrument, volume) ,
            duration, start);
    return this;
  }
}
