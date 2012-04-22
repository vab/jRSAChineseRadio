import java.awt.*;

class winDlog extends Dialog {
  winDlog(Frame parent, String key) {
	  super(parent, "Key Found!", true);
		add("North", new Label("Key Found!!!!", Label.CENTER));
		add("South", new Label(key, Label.CENTER));
		resize(640,460);
		}
}

