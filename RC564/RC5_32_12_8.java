// RC5-32/12/8 implementation. Unrolls all important loops.

class RC5_32_12_8 implements RC5 {
  private static final int w = 32;             /* word size in bits                 */
  private static final int r = 12;             /* number of rounds                  */
  private static final int b =  8;             /* number of bytes in key            */
  private static final int c =  2;             /* number  words in key = ceil(8*b/w)*/
  private static final int t = 26;             /* size of table S = 2*(r+1) words   */
//  static int[] initS = new int[t];
  static int initS0, initS1, initS2, initS3, initS4, initS5, initS6, initS7, initS8, initS9;
  static int initS10, initS11, initS12, initS13, initS14, initS15, initS16, initS17, initS18, initS19;
  static int initS20, initS21, initS22, initS23, initS24, initS25;
//  int[] S = new int[t];                      /* expanded key table                */
  int S0, S1, S2, S3, S4, S5, S6, S7, S8, S9;
  int S10, S11, S12, S13, S14, S15, S16, S17, S18, S19;
  int S20, S21, S22, S23, S24, S25;
  private static final int P = 0xb7e15163, Q = 0x9e3779b9;  /* magic constants             */

  static {
    initS0 = P;
//    for (int i = 1; i < t; i++) {
//      initS[i] = initS[i-1] + Q;
//    }
    initS1  = initS0  + Q;
    initS2  = initS1  + Q;
    initS3  = initS2  + Q;
    initS4  = initS3  + Q;
    initS5  = initS4  + Q;
    initS6  = initS5  + Q;
    initS7  = initS6  + Q;
    initS8  = initS7  + Q;
    initS9  = initS8  + Q;
    initS10 = initS9  + Q;
    initS11 = initS10 + Q;
    initS12 = initS11 + Q;
    initS13 = initS12 + Q;
    initS14 = initS13 + Q;
    initS15 = initS14 + Q;
    initS16 = initS15 + Q;
    initS17 = initS16 + Q;
    initS18 = initS17 + Q;
    initS19 = initS18 + Q;
    initS20 = initS19 + Q;
    initS21 = initS20 + Q;
    initS22 = initS21 + Q;
    initS23 = initS22 + Q;
    initS24 = initS23 + Q;
    initS25 = initS24 + Q;
  }

  public int keySize()
  {
    return b;
  }

//  int ROTL(int x, int y)
//  {
//    return (x << (y & (w-1))) | (x >>> (w - (y & (w-1))));
//  }
//
//  int ROTR(int x, int y)
//  {
//    return (x >>> (y & (w-1))) | (x << (w - (y & (w-1))));
//  }

  public long encrypt(long pt)
  {
    int A = (int)(pt & 0xffffffffL) + S0;
    int B = (int)(pt >>> 32) + S1;
//    for (int i = 1; i <= r; i++) {
//      int x = A ^ B;
//      int y = B & (w-1);
//      A = /*ROTL(A ^ B, B)*/ ((x << y) | (x >>> (w-y))) + S[2*i];
//      x = B ^ A;
//      y = A & (w-1);
//      B = /*ROTL(B ^ A, A)*/ ((x << y) | (x >>> (w-y))) + S[2*i+1];
//    }
    int x, y;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S2 ; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S3 ;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S4 ; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S5 ;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S6 ; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S7 ;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S8 ; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S9 ;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S10; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S11;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S12; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S13;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S14; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S15;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S16; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S17;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S18; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S19;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S20; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S21;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S22; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S23;
    x = A ^ B; y = B & (w-1); A = ((x << y) | (x >>> (w-y))) + S24; x = B ^ A; y = A & (w-1); B = ((x << y) | (x >>> (w-y))) + S25;
    return ((long)B << 32) + (A & 0xffffffffL);
  }

  public long decrypt(long ct)
  {
    int A = (int)(ct & 0xffffffffL);
    int B = (int)(ct >>> 32);
//    for (int i = r; i > 0; i--) {
//      int x = B - S[2*i+1];
//      int y = A & (w-1);
//      B = /*ROTR(B - S[2*i+1], A)*/ ((x >>> y) | (x << (w-y))) ^ A;
//      x = A - S[2*i];
//      y = B & (w-1);
//      A = /*ROTR(A - S[2*i], B)*/ ((x >>> y) | (x << (w-y))) ^ B;
//    }
    int x, y;
    x = B - S25; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S24; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S23; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S22; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S21; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S20; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S19; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S18; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S17; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S16; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S15; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S14; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S13; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S12; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S11; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S10; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S9 ; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S8 ; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S7 ; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S6 ; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S5 ; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S4 ; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    x = B - S3 ; y = A & (w-1); B = ((x >>> y) | (x << (w-y))) ^ A; x = A - S2 ; y = B & (w-1); A = ((x >>> y) | (x << (w-y))) ^ B;
    return ((long)(B - S1) << 32) + ((A - S0) & 0xffffffffL);
  }

  public void setup(byte[] K)
  {
    int i, j, k, u=w/8, A, B;
    /* Initialize L, then S, then mix key into S */
//    for (i=b-1,L[c-1]=0; i!=-1; i--) L[i/u] = (L[i/u]<<8)+(K[i]&0xff);
    int L0 = ((K[3] & 0xff) << 24) | ((K[2] & 0xff) << 16) | ((K[1] & 0xff) << 8) | (K[0] & 0xff);
    int L1 = ((K[7] & 0xff) << 24) | ((K[6] & 0xff) << 16) | ((K[5] & 0xff) << 8) | (K[4] & 0xff);
//    for (A=B=i=j=k=0; k<3*t; k++) {  /* 3*t > 3*c */
//      int x = S[i] + (A+B);
//      A = S[i] = /*ROTL(S[i]+(A+B),3);*/ (x << 3) | (x >>> 29);
//      x = L[j] + (A+B);
//      int y = (A+B) & (w-1);
//      B = L[j] = /*ROTL(L[j]+(A+B),(A+B));*/ (x << y) | (x >>> (w-y));
//      /*i = (i+1) % t;*/ i++; if (i >= t) i = 0;
//      /*j = (j+1) % c;*/ j++; if (j >= c) j = 0;
//    }
    A = B = 0;
    int x, y;
    x = initS0  + (A+B); A = S0  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS1  + (A+B); A = S1  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS2  + (A+B); A = S2  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS3  + (A+B); A = S3  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS4  + (A+B); A = S4  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS5  + (A+B); A = S5  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS6  + (A+B); A = S6  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS7  + (A+B); A = S7  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS8  + (A+B); A = S8  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS9  + (A+B); A = S9  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS10 + (A+B); A = S10 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS11 + (A+B); A = S11 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS12 + (A+B); A = S12 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS13 + (A+B); A = S13 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS14 + (A+B); A = S14 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS15 + (A+B); A = S15 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS16 + (A+B); A = S16 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS17 + (A+B); A = S17 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS18 + (A+B); A = S18 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS19 + (A+B); A = S19 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS20 + (A+B); A = S20 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS21 + (A+B); A = S21 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS22 + (A+B); A = S22 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS23 + (A+B); A = S23 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = initS24 + (A+B); A = S24 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = initS25 + (A+B); A = S25 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));

    x = S0  + (A+B); A = S0  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S1  + (A+B); A = S1  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S2  + (A+B); A = S2  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S3  + (A+B); A = S3  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S4  + (A+B); A = S4  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S5  + (A+B); A = S5  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S6  + (A+B); A = S6  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S7  + (A+B); A = S7  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S8  + (A+B); A = S8  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S9  + (A+B); A = S9  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S10 + (A+B); A = S10 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S11 + (A+B); A = S11 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S12 + (A+B); A = S12 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S13 + (A+B); A = S13 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S14 + (A+B); A = S14 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S15 + (A+B); A = S15 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S16 + (A+B); A = S16 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S17 + (A+B); A = S17 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S18 + (A+B); A = S18 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S19 + (A+B); A = S19 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S20 + (A+B); A = S20 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S21 + (A+B); A = S21 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S22 + (A+B); A = S22 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S23 + (A+B); A = S23 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S24 + (A+B); A = S24 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S25 + (A+B); A = S25 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));

    x = S0  + (A+B); A = S0  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S1  + (A+B); A = S1  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S2  + (A+B); A = S2  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S3  + (A+B); A = S3  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S4  + (A+B); A = S4  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S5  + (A+B); A = S5  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S6  + (A+B); A = S6  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S7  + (A+B); A = S7  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S8  + (A+B); A = S8  = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S9  + (A+B); A = S9  = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S10 + (A+B); A = S10 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S11 + (A+B); A = S11 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S12 + (A+B); A = S12 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S13 + (A+B); A = S13 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S14 + (A+B); A = S14 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S15 + (A+B); A = S15 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S16 + (A+B); A = S16 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S17 + (A+B); A = S17 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S18 + (A+B); A = S18 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S19 + (A+B); A = S19 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S20 + (A+B); A = S20 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S21 + (A+B); A = S21 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S22 + (A+B); A = S22 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S23 + (A+B); A = S23 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
    x = S24 + (A+B); A = S24 = (x << 3) | (x >>> 29); x = L0 + (A+B); y = (A+B) & (w-1); B = L0 = (x << y) | (x >>> (w-y));
    x = S25 + (A+B); A = S25 = (x << 3) | (x >>> 29); x = L1 + (A+B); y = (A+B) & (w-1); B = L1 = (x << y) | (x >>> (w-y));
  }
}
