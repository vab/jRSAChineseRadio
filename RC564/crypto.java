import java.awt.*;
import java.util.Random;
import java.io.*;
import java.net.*;


public class crypto extends java.applet.Applet implements Runnable {
  Image i;
  int percent = 0;
  Thread Block;
  String numKeys = "0";
  String perComplete = "0";
  String keyRate = "0";
  int totalBlocks=-1;
  long keys = 0;
  long start = 0;
  int m = 5000; //update frequency control
  long iv =0;  //iv
  long ct =0;  //cyphertext
  byte[] key = new byte[64];
  String currentBlock;
  RC5 rc5 = new RC5_32_12_8();
  Random rand = new Random();
  Button stopButton;

  public crypto() {
   super();
   Block = new Thread(Block);
   Block.start();
   }
	
  public void init() 	{
    try {
  	  i = getImage(getDocumentBase(), "ufcrypto.gif");
			} catch (Exception ex) {
			  }
		
	  byte[] ivb = new byte[64];
            ivb[0] = (byte)0x79;
            ivb[1] = (byte)0xce;
            ivb[2] = (byte)0xd5;
            ivb[3] = (byte)0xd5;
            ivb[4] = (byte)0x50;
            ivb[5] = (byte)0x75;
            ivb[6] = (byte)0xea;
            ivb[7] = (byte)0xfc;

     iv = ((ivb[7] & 0xffL) << 56) | ((ivb[6] & 0xffL) << 48) |
          ((ivb[5] & 0xffL) << 40) | ((ivb[4] & 0xffL) << 32) |
          ((ivb[3] & 0xffL) << 24) | ((ivb[2] & 0xffL) << 16) |
          ((ivb[1] & 0xffL) <<  8) | (ivb[0] & 0xffL);
		
		
		byte[] ctb = new byte[64];
		   ctb[0] = (byte)0xbf;
                   ctb[1] = (byte)0x55;
                   ctb[2] = (byte)0x01;
                   ctb[3] = (byte)0x55;
                   ctb[4] = (byte)0xdc;
                   ctb[5] = (byte)0x26;
                   ctb[6] = (byte)0xf2;
                   ctb[7] = (byte)0x4b;



      ct = ((ctb[7] & 0xffL) << 56) | ((ctb[6] & 0xffL) << 48) |
           ((ctb[5] & 0xffL) << 40) | ((ctb[4] & 0xffL) << 32) |
           ((ctb[3] & 0xffL) << 24) | ((ctb[2] & 0xffL) << 16) |
           ((ctb[1] & 0xffL) <<  8) | (ctb[0] & 0xffL);

/*      key[0] = (byte)0x00;
      key[1] = (byte)0xe5;  
      key[2] = (byte)0x1b;
      key[3] = (byte)0x9f;
      key[4] = (byte)0x9c;
      key[5] = (byte)0xc7;
      key[6] = (byte)0x18;
      key[7] = (byte)0xf9;
*/
      setLayout(new BorderLayout());
      stopButton = new Button("Stop");
      add("South", stopButton);
      newblock();
		
      resize(300,400);
      }
 
   public String toHex(byte a) {
     StringBuffer s = new StringBuffer();
     s.append(Character.forDigit((a >> 4) & 0xf, 16));
     s.append(Character.forDigit(a & 0xf, 16));
     return s.toString();
     }

  public void newblock() {
    percent = 0;
    numKeys = "0";
    perComplete = "0";
    keyRate = "0";
    keys = 0;
    start = 0;
    m = 5000; //update frequency control
	
    int r = rand.nextInt();
    int r2 = rand.nextInt();

    key[0] = (byte) (r >> 24);
    key[1] = (byte) (r >> 16);
    key[2] = (byte) (r >> 8);
    key[3] = (byte) (r);
			
    key[4] = (byte) (r2 >> 24);
    key[5] = (byte) (r2 >> 16);
    key[6] = (byte) (r2 >> 8);
    key[7] = (byte) (r2);

    currentBlock = "" + toHex(key[0]) + toHex(key[1]) + toHex(key[2]) + toHex(key[3]) + ":" + toHex(key[4]) + toHex(key[5]) + toHex(key[6]) + toHex(key[7]);
    repaint();
    totalBlocks++;
    Block = null;
    Block = new Thread(this);
    Block.start();
    }
  
  public void begin() {
    if(Block == null) {
      Block = new Thread(this);
      Block.start();
      }
    }

  public void end() {
    if(Block != null) {
      Block.stop();
      Block = null;
      }
    }

  public void paint(Graphics g) {
    g.drawImage(i, 10,10, this);
    g.drawString("UF Crypto RC5-64 Effort", 75, 150);
    g.drawString("Testing 2^22 Block", 90,175);
    g.setColor(new Color(192,192,192));
    g.fillRect(45, 195, 220, 40);
		
    g.setColor(new Color(0,0,0));
    g.drawRect(50, 200, 200, 12);
    g.setColor(new Color(0,0,255));
    g.fillRect(50, 200, (percent*2), 12);
		
    g.setColor(new Color(255,0,0));
    g.drawString(currentBlock, 90, 225);
		
    g.setColor(new Color(0,0,0));
    g.drawString("Keys Checked:", 50, 260);
    g.drawString("Percent Complete:", 50, 280);
    g.drawString("Key Rate:", 50, 300);
    g.drawString("Total Blocks:", 50,320);
		
    g.setColor(new Color(192,192,192));
    g.fillRect(185, 235, 175, 120);
    g.setColor(new Color(0,0,0));
    g.drawString(numKeys, 200,260);
    g.drawString(perComplete, 200, 280);
    g.drawString(keyRate, 200, 300);
    g.drawString("" + totalBlocks, 200,320);
		
    g.drawString("Applet By VAB [DTKI]", 85, 345);
    g.drawString("Based on RC5 Engine By Greg Hewgill", 45, 360);
    }
		
  public void update(Graphics g) {
    paint(g);
    }
		
  void updateStats(long keys, long mytime)  {
    long calc_percent = 0;
	
    calc_percent = keys/41943;
    percent = (int)calc_percent;
    numKeys = "" + keys;
    perComplete = "" + percent + "%";
    long rateKeys = keys * 1000;
    if((rateKeys != 0) && (mytime != 0)) {
      keyRate = "" + (rateKeys/mytime) + " keys/sec";
      }
    repaint();
    }		
 
  public boolean action(Event e, Object arg) {
    if(e.target == stopButton) {
      Block.stop();
      return true;
      }
    return false;
    }
		
  public void run() {
    start = System.currentTimeMillis();
    kloop: while (true) {
      rc5.setup(key);
      long pt = rc5.decrypt(ct) ^ iv;
      keys++;

      // this represents the string "The unkn"
      if(pt == 0x6e6b6e7520656854L) {
        Frame f = new Frame();
        winDlog weWon = new winDlog(f, "" + toHex(key[0]) + toHex(key[1]) + toHex(key[2]) + toHex(key[3]) + ":" + toHex(key[4]) + toHex(key[5]) + toHex(key[6]) + toHex(key[7]));
        weWon.show();
        System.exit(0);
        // Handle The Finding Of The Key Here
        //break;
        }

	if(keys > 4194303) { // 2^22 == 4194304
	  // terminate the demo after the block has been completed.
	  long now = System.currentTimeMillis();
          updateStats(keys, now - start);
	  //  Open up a conection to the CGI and report the block
/*				try {
  				Socket s = new Socket("dogwood.grove.ufl.edu", 5150);
	  			OutputStream o = s.getOutputStream();
		  		DataOutputStream out = new DataOutputStream(o);
					out.writeUTF(currentBlock);
					} catch (IOException ex) {
					  } */
         stop(); 
         newblock();
         break kloop;
          }
			
	if(keys > m) {
	  long now = System.currentTimeMillis();
          updateStats(keys, now - start);
  	  m = m + 5000;
	  }

        int i = 0;
        while (++key[i] == 0) {
          i++;
          if (i >= key.length) {
            break kloop;
            }
          }
        }
      long end = System.currentTimeMillis();
      }
}
