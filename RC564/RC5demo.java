// RC5 demo applet.

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

public class RC5demo extends Applet {
  private TextField KeysField;
  private TextField TimeField;
  private TextField RateField;
  private Component GoButton;
  private boolean Standalone;

  public RC5demo()
  {
    Standalone = false;
  }

  public RC5demo(boolean standalone)
  {
    Standalone = standalone;
  }

  public void init()
  {
    setLayout(new BorderLayout());
    Panel displaypanel = new Panel();
    add(displaypanel);

    GridBagLayout gb = new GridBagLayout();
    displaypanel.setLayout(gb);
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.HORIZONTAL;
    gb.setConstraints(displaypanel.add(new Label("Keys tested: ", Label.RIGHT)), c);

    c.gridwidth = GridBagConstraints.REMAINDER;
    KeysField = new TextField(20);
    KeysField.setEditable(false);
    gb.setConstraints(displaypanel.add(KeysField), c);

    c.gridwidth = 1;
    gb.setConstraints(displaypanel.add(new Label("Elapsed time: ", Label.RIGHT)), c);

    c.gridwidth = GridBagConstraints.REMAINDER;
    TimeField = new TextField(20);
    TimeField.setEditable(false);
    gb.setConstraints(displaypanel.add(TimeField), c);

    c.gridwidth = 1;
    gb.setConstraints(displaypanel.add(new Label("Keyrate: ", Label.RIGHT)), c);

    c.gridwidth = GridBagConstraints.REMAINDER;
    RateField = new TextField(20);
    RateField.setEditable(false);
    gb.setConstraints(displaypanel.add(RateField), c);

    c.fill = GridBagConstraints.NONE;
    GoButton = new Button("Go!");
    gb.setConstraints(displaypanel.add(GoButton), c);

    add("Center", displaypanel);

    add("North", new Label("RC5 Java Demo Applet (gregh@lightspeed.net)", Label.CENTER));
  }

  public boolean action(Event evt, Object what)
  {
    if (evt.target == GoButton) {
      GoButton.disable();
      new Thread(new RC5test(this)).start();
    } else {
      return super.action(evt, what);
    }
    return true;
  }

  void updateStats(long keys, long time, boolean done)
  {
    if (Standalone) {
      if (done) {
        System.out.println("Keys: " + keys + " keys");
        System.out.println("Time: " + time + " ms");
        System.out.println("Rate: " + (1000*keys/time) + " keys/sec");
      }
    } else {
      KeysField.setText(Long.toString(keys) + " keys");
      TimeField.setText(Long.toString(time) + " ms");
      RateField.setText(Long.toString(1000*keys/time) + " keys/sec");
      if (done) {
        GoButton.enable();
      }
    }
  }

  public static void main(String[] args)
  {
    System.out.println("RC5 Java Demo (gregh@lightspeed.net)");
    RC5demo demo = new RC5demo(true);
    new RC5test(demo).run();
  }
}
