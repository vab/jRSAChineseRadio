// Generic RC5 interface to support different implementations.
// Assumes 32 bit RC5 word size.

interface RC5 {
  public int keySize();
  public long encrypt(long pt);
  public long decrypt(long ct);
  public void setup(byte[] K);
}
