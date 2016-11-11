package controller;

import cs3500.music.model.IMusicEditor;
import cs3500.music.view.IMusicView;

/**
 * Controller class to implement IMusicController
 */
public class MusicController implements IMusicController {

  IMusicEditor editor;
  IMusicView view;

  public MusicController(IMusicEditor editor, IMusicView view) {
    this.editor = editor;
    this.view = view;
  }

  @Override
  public void renderSong() {

  }

  @Override
  public void createKeyboardHandler() {

  }
}
