import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static Vector v = new Vector();

    public static char[][] strTochar(String str, int num){
        char c[][] = new char[num][str.length()];
        int offset = 0;
        for (int i=0; i < num; i++) {
            for (int j=0; j < (str.length()/num); j++) {
                c[i][j] = str.charAt(offset++);
            }
        }
        return c;
    }

    public static void leftRotate(char arr[], int d, int n)
    {
        for (int i = 0; i < d; i++)
            leftRotatebyOne(arr, n);
    }

    public  static void leftRotatebyOne(char arr[], int n)
    {
        int i;
        char temp;
        temp = arr[0];
        for (i = 0; i < n - 1; i++)
            arr[i] = arr[i + 1];
        arr[i] = temp;
    }

    public static char[] rightFunc(char[] left, char[] right, char[] key){
    //    System.out.println("right bit generating function");
        int e[] = {32, 1, 2, 3, 4, 5,
                   4, 5, 6, 7, 8, 9,
                   8, 9, 10, 11, 12, 13,
                   12, 13, 14, 15, 16, 17,
                   16, 17, 18, 19, 20, 21,
                   20, 21, 22, 23, 24, 25,
                   24, 25, 26, 27, 28, 29,
                   28, 29, 30, 31, 32, 1};
        int pi_2[] ={35, 38, 46, 6, 43, 40, 14, 45,
                     33, 19, 26, 15, 23, 8, 22, 10,
                     12, 11, 5, 25, 27, 21, 16, 31,
                     28, 32, 34, 24, 9, 37, 2, 1};
        int p[] = {16, 7, 20, 21, 29, 12, 28, 17,
                   1, 15, 23, 26, 5, 18, 31, 10,
                   2, 8, 24, 14, 32, 27, 3, 9,
                   19, 13, 30, 6, 22, 11, 4, 25};
        char[] expandright = new char[e.length];
        for(int i=0;i<e.length;i++){
            expandright[i] = right[(e[i]-1)];
        }
    /*    System.out.println("expanded right key "+expandright.length);
        for(int i=0;i<e.length;i++){
            System.out.print(expandright[i]);
        }
        /*char c[0]  = (char) ('0' ^ '1');
        System.out.println("xor "+(int)c);
        System.out.println();*/
        char[] xor = new char[expandright.length];
        for(int i=0;i<expandright.length;i++){
            xor[i] = (char) (expandright[i] ^ key[i]);
        }
    /*    System.out.println("expanded right key "+expandright.length+" "+key.length);
        for(int i=0;i<xor.length;i++){
            System.out.print((int)xor[i]);
        }*/
       char[] pi_2right = new char[pi_2.length];
        for(int i=0;i<pi_2.length;i++){
            pi_2right[i] = xor[(pi_2[i]-1)];
        }
    /*    System.out.println("after pi_2 "+pi_2right.length+" "+xor.length);
        for(int i=0;i<pi_2right.length;i++){
            System.out.print((int)pi_2right[i]);
        }*/
        char[] p_right = new char[pi_2right.length];
        for(int i=0;i<pi_2right.length;i++){
            p_right[i] = pi_2right[(p[i]-1)];
        }
     /*   System.out.println("after function and p box "+p_right.length);
        for(int i=0;i<p_right.length;i++){
            System.out.print((int) p_right[i]);
        }*/
        char[] rightres = new char[p_right.length];
        for(int i=0;i<p_right.length;i++){
            rightres[i] = (char) (p_right[i] ^ left[i]);
        }
     /*   System.out.println("after function "+rightres.length);
        for(int i=0;i<rightres.length;i++){
            System.out.print(rightres[i]);
        }
        System.out.println("after function and p box "+p_right.length);
        for(int i=0;i<p_right.length;i++){
            System.out.print(p_right[i]);
        } */
        return rightres;
    }

    public static char[] keyFunc(char[] permutekey, int[] shift, int itrNum, int[] cp_2){
        char[] leftkey = new char[permutekey.length / 2];
        char[] rightkey = new char[permutekey.length / 2];
        for (int i = 0; i < (permutekey.length / 2); i++) { //for each iteration
            leftkey[i] = permutekey[i];
        }
           /* System.out.println("left key : ");
            for(int i=0;i<leftkey.length;i++){
                System.out.print(leftkey[i]);
            }*/
        for (int i = 0; i < (permutekey.length / 2); i++) { //for each iteration
            rightkey[i] = permutekey[i + (permutekey.length / 2)];
        }
        leftRotate(leftkey,shift[itrNum],leftkey.length);
        leftRotate(rightkey,shift[itrNum],rightkey.length);
            /*System.out.println("left key rotate : ");
            for(int i=0;i<leftkey.length;i++){
                System.out.print(leftkey[i]);
            }*/
        StringBuilder sb = new StringBuilder(2*leftkey.length);
        sb.append(leftkey);
        sb.append(rightkey);
        char newkey[] = sb.toString().toCharArray();
    /*    System.out.println("rotated key : "+newkey.length);
        for(int i=0;i<newkey.length;i++){
            System.out.print(newkey[i]);
        }*/
        char[] cp_2key = new char[cp_2.length];
        for(int i=0;i<cp_2.length;i++){
            cp_2key[i] = newkey[(cp_2[i]-1)];
        }
    /*    System.out.println("cp2 key : "+cp_2key.length);
        for(int i=0;i<cp_2key.length;i++){
            System.out.print(cp_2key[i]);
        }*/
        return cp_2key;
    }

    public static char[] iteration(char[] trans, String key){
     //   System.out.println("iteration");
        char[] keybit = key.toCharArray();
        char[] left = new char[trans.length/2];
        char[] right = new char[trans.length/2];
        for(int i=0;i<(trans.length/2);i++){
            left[i] = trans[i];
        }
        for(int i=0;i<(trans.length/2);i++){
            right[i] = trans[i+(trans.length/2)];
        }
     /*   System.out.println("left");
        for(int i=0;i<left.length;i++){
            System.out.print(left[i]);
        }
        System.out.println("right");
        for(int i=0;i<left.length;i++){
            System.out.print(right[i]);
        }*/
        int shift[] = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
        int cp_2[] = {14, 17, 11, 24, 1, 5, 3, 28,
                      15, 6, 21, 10, 23, 19, 12, 4,
                      26, 8, 16, 7, 27, 20, 13, 2,
                      41, 52, 31, 37, 47, 55, 30, 40,
                      51, 45, 33, 48, 44, 49, 39, 56,
                      34, 53, 46, 42, 50, 36, 29, 32};
        int cp_1[] = {57, 49, 41, 33, 25, 17, 9,
                      1, 58, 50, 42, 34, 26, 18,
                      10, 2, 59, 51, 43, 35, 27,
                      19, 11, 3, 60, 52, 44, 36,
                      63, 55, 47, 39, 31, 23, 15,
                      7, 62, 54, 46, 38, 30, 22,
                      14, 6, 61, 53, 45, 37, 29,
                      21, 13, 5, 28, 20, 12, 4};
        char[] permutekey = new char[cp_1.length];
        for(int i=0;i<cp_1.length;i++){
            permutekey[i] = keybit[(cp_1[i]-1)];
        }
    /*    System.out.println();
        for(int i=0;i<permutekey.length;i++){
            System.out.print(permutekey[i]);
        }*/

        //eikhan theke loop hote thakbe
        for(int itrNum=0;itrNum<16;itrNum++) {
         //   System.out.println("\niteration number "+itrNum);
            char[] cp_2key = keyFunc(permutekey,shift,itrNum,cp_2);
            v.add(cp_2key);
            char[] rightres = rightFunc(left, right, cp_2key);
            left = right;
            right = rightres;
        }
        char[] temp =left;
        left = right;
        right = temp;
        StringBuilder sb = new StringBuilder(2*left.length);
        sb.append(left);
        sb.append(right);
        char secfinal[] = sb.toString().toCharArray();
        return secfinal;
    }

    public static String encrypt(String str, String key, int pi[], int pi_1[]){
        //    System.out.println("encryption");
            char[] perarray = str.toCharArray();
            char[] transmat = new char[pi.length];
            for(int i=0;i<pi.length;i++){
                transmat[i] = perarray[(pi[i]-1)];
            }
      /*     for(int i=0;i<transmat.length;i++){
                System.out.print(transmat[i]);
            }*/
            char[] encode = iteration(transmat,key);
            char[] permuteencode = new char[encode.length];
            for(int i=0;i<encode.length;i++){
                permuteencode[i] = encode[(pi_1[i]-1)];
            }
            String encodetext = new String(permuteencode);
     //       System.out.println("\n"+encodetext);
            return encodetext;
    }

    public static String decrypt(String encode, int[] pi, int pi_1[]){
        char [] code = encode.toCharArray();
    /*    String Block = new String();
        for(int i=0;i<code.length;i++){
            String temp = Integer.toBinaryString(code[i]);
            while(temp.length()!=8){
                temp = "0"+temp;
            }
            Block += temp;
        }
        for(int i=0;i<code.length;i++){
            System.out.print("code block : "+code[i]);
        }*/
       // char[] matrix = Block.toCharArray();
        char[] transmat = new char[pi.length];
        for(int i=0;i<pi.length;i++){
            transmat[i] = code[(pi[i]-1)];
        }
        char[] left = new char[transmat.length/2];
        char[] right = new char[transmat.length/2];
        for(int i=0;i<(transmat.length/2);i++){
            left[i] = transmat[i];
        }
        for(int i=0;i<(transmat.length/2);i++){
            right[i] = transmat[i+(transmat.length/2)];
        }
        //char[] vec = ((char[]) v.elementAt(1));
        /*for(int i=0;i<v.size();i++){
            System.out.print(((char[]) v.get(i)));
            System.out.println();
        }*/
        for(int itrNum=15;itrNum>=0;itrNum--) {
         //   System.out.println("\niteration number "+itrNum);
            char[] rightres = rightFunc(left, right, ((char[]) v.get(itrNum)));
            left = right;
            right = rightres;
        }
        char[] temp =left;
        left = right;
        right = temp;
        StringBuilder sb = new StringBuilder(left.length+right.length);
        sb.append(left);
        sb.append(right);
        char secfinal[] = sb.toString().toCharArray();
        char[] decode = new char[pi_1.length];
        for(int i=0;i<pi_1.length;i++){
            decode[i] = secfinal[(pi_1[i]-1)];
        }
    /*    for(int i=0;i<decode.length;i++){
            System.out.print(decode[i]);
        }*/
    //    System.out.println("\n"+decode.length);
        String str = new String(decode);
    //    System.out.println("decoding "+str);
        return str;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        int pi[] = {58, 50, 42, 34, 26, 18, 10, 2,
                    60, 52, 44, 36, 28, 20, 12, 4,
                    62, 54, 46, 38, 30, 22, 14, 6,
                    64, 56, 48, 40, 32, 24, 16, 8,
                    57, 49, 41, 33, 25, 17, 9, 1,
                    59, 51, 43, 35, 27, 19, 11, 3,
                    61, 53, 45, 37, 29, 21, 13, 5,
                    63, 55, 47, 39, 31, 23, 15, 7};
        int pi_1[] = {40, 8, 48, 16, 56, 24, 64, 32,
                39, 7, 47, 15, 55, 23, 63, 31,
                38, 6, 46, 14, 54, 22, 62, 30,
                37, 5, 45, 13, 53, 21, 61, 29,
                36, 4, 44, 12, 52, 20, 60, 28,
                35, 3, 43, 11, 51, 19, 59, 27,
                34, 2, 42, 10, 50, 18, 58, 26,
                33, 1, 41, 9, 49, 17, 57, 25};
        System.out.print("Input\nKey : ");
        Scanner sc = new Scanner(System.in);
        String key = sc.nextLine();
        System.out.print("Plain text : ");
        String text = sc.nextLine();
        char[] keyMat = key.toCharArray();
        String keyBlock = new String();
        for(int i=0;i<keyMat.length;i++){
            String temp = Integer.toBinaryString(keyMat[i]);
            while(temp.length()!=8){
                temp = "0"+temp;
            }
            keyBlock += temp;
        }
    //    System.out.println("key block : "+keyBlock);
        int blockNum = (int) Math.ceil((1.0*text.length())/8.0);
    //    System.out.println("Block number : "+blockNum);
        while(text.length()%8.0 != 0){
            text = text + "~";
        }
    //    System.out.println("After padding : "+text +"\nBlock:");
        char[][] matrix = strTochar(text,blockNum);
    /*    for(int i=0;i<blockNum;i++){
            for(int j=0;j<(text.length()/blockNum);j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }*/
        String strBlock = new String();
        for(int i=0;i<blockNum;i++){
            for(int j=0;j<(text.length()/blockNum);j++){
                String temp = Integer.toBinaryString(matrix[i][j]);
                while(temp.length()!=8){
                    temp = "0"+temp;
                }
                strBlock += temp;
            }
        }
    //    System.out.println(strBlock);
        String str[] = new String[blockNum];
        for(int i=0;i<blockNum;i++){
            for(int j=0;j<(strBlock.length()/blockNum);j++){
                str[i]=strBlock.substring((i*64),(i+1)*64);
            }
        }
    /*    System.out.println("64 bit block");
        for(int i=0;i<blockNum;i++){
            System.out.println(str[i]);
        }*/
        String code = new String();
        for(int i=0;i<blockNum;i++){
         code += encrypt(str[i],keyBlock,pi,pi_1);
        }
        //System.out.println(code);
        String s = new String();
        for(int index = 0; index < code.length(); index+=8) {
            String temp = code.substring(index, index + 8);
            int num = Integer.parseInt(temp, 2);
            char letter = (char) num;
            s += letter;
        }
        String str1 = Normalizer.normalize(s, Normalizer.Form.NFD);
        System.out.print("\nOutput\nCiphered : ");
        System.out.println(str1);
       // String valueISO = new String(str1.getBytes("ASCII"), "US-ASCII");
       // System.out.println(valueISO);
        String destr[] = new String[blockNum];
        for(int i=0;i<blockNum;i++){
            for(int j=0;j<(code.length()/blockNum);j++){
                destr[i]=code.substring((i*64),(i+1)*64);
            }
        }
        String decode = new String();
        for(int i=0;i<blockNum;i++){
             decode += decrypt(destr[i],pi,pi_1);
        }
     //   System.out.println("\n decoding~~~~"+decode+" "+decode.length());
        String ds = new String();
        for(int index = 0; index < code.length(); index+=8) {
            String temp = decode.substring(index, index + 8);
            int num = Integer.parseInt(temp, 2);
            char letter = (char) num;
            ds += letter;
        }
        System.out.print("Deciphered : ");
        System.out.println(ds.replaceAll("~",""));
    }
}
