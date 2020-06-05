/*
 * Primera entrega: léxico
 * Marcos Brian Leiva
 * Pablo Villalobos
 */
package alex;
import errors.GestionErroresTiny;


public class AnalizadorLexicoTiny implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

  private ALexOperations ops;
  private int iniCol;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int chr() {return yychar+1;}
  public int col() {return yychar-iniCol+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public AnalizadorLexicoTiny (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public AnalizadorLexicoTiny (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private AnalizadorLexicoTiny () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

  ops = new ALexOperations(this);
  iniCol = 0;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NOT_ACCEPT,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"9:8,2:2,1,9:2,2,9:18,2,9,49,8,9:2,31,50,20,21,15,14,23,10,13,16,12,11:9,29," +
"28,34,22,33,9:2,52,45,52,48,52:4,44,52:9,46,52:7,24,9,25,32,9:2,38,51,37,19" +
",4,35,47,51,7,51:2,6,17,3,18,42,51,39,36,40,41,51,5,51,43,51,26,30,27,9:654" +
"10,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,121,
"0,1:3,2,3,1,4,5,1,6,1:4,7,1:10,8,9,10,11,1,12,1,11,1:4,11:5,13:2,11:5,13,11" +
":4,13,14,11,15,14,16,17,18,19,18,13,20,21,22,23,11,24,25,26,27,28,29,30,31," +
"32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56," +
"11,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,13")[0];

	private int yy_nxt[][] = unpackFromString(72,53,
"1,2,3,4,98,104:2,58,5,6,7,8,60,9,10,11,12,109,63,104,13,14,15,16,17,18,19,2" +
"0,21,22,23,24,25,26,27,111,112,113,114,115,116,104:3,28,99,105,104,110,59,6" +
"4,104,120,-1:56,104,117,104:3,-1:3,104:2,-1:4,104,66,104,-1:15,104:6,68,104" +
":2,70:3,104,70,-1:2,104,70,-1:2,5:51,-1:11,8,60,-1:20,30,-1:30,8:2,31,-1:50" +
",8,60,-1,32,-1:60,34,-1:52,35,-1:52,36,-1:33,61,65:4,-1:3,120:2,-1:4,65:3,-" +
"1:15,65:9,120:3,65,120,-1:2,65,120,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,1" +
"04:9,70:3,104,70,-1:2,104,70,-1:11,31:2,-1:43,65:5,-1:3,120:2,-1:4,65:3,-1:" +
"15,65:9,120:3,65,120,-1:2,65,120,-1,56:48,37,56:3,-1:3,73,104:4,-1:3,104:2," +
"-1:4,104:3,-1:15,29,104:8,70:3,104,70,-1:2,104,70,-1:13,31,-1:42,65:5,-1:3," +
"120:2,-1:4,65:3,-1:15,65:5,43,65:3,120:3,65,120,-1:2,65,120,-1,62:49,37,62:" +
"2,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:4,33,104:4,70:3,104,70,-1:2,10" +
"4,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:5,38,104:3,70:3,104,70,-1:2" +
",104,70,-1:3,65:5,-1:3,120:2,-1:4,65:3,-1:15,65:2,44,65:6,120:3,65,120,-1:2" +
",65,120,-1:3,104:3,81,104,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104,70,-1:" +
"2,104,70,-1:3,65:3,50,65,-1:3,120:2,-1:4,65:3,-1:15,65:9,120:3,65,120,-1:2," +
"65,120,-1:3,65:5,-1:3,120:2,-1:4,65:3,-1:15,65:9,120:3,55,120,-1:2,65,120,-" +
"1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104,82,104:7,70:3,104,70,-1:2,104,70," +
"-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,39,104:8,70:3,104,70,-1:2,104,70,-1:" +
"3,104:5,-1:3,104:2,-1:4,104:2,40,-1:15,104:9,70:3,104,70,-1:2,104,70,-1:3,4" +
"1,104:4,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104,70,-1:2,104,70,-1:3,104:" +
"5,-1:3,104:2,-1:4,104:3,-1:15,104:4,103,104:4,70:3,104,70,-1:2,104,70,-1:3," +
"104:5,-1:3,104:2,-1:4,104:2,42,-1:15,104:9,70:3,104,70,-1:2,104,70,-1:3,104" +
":5,-1:3,104:2,-1:4,104:3,-1:15,104:5,108,104,85,104,70:3,104,70,-1:2,104,70" +
",-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:6,86,104:2,70:3,104,70,-1:2,104" +
",70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:7,87,104,70:3,104,70,-1:2,10" +
"4,70,-1:3,104:3,45,104,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104,70,-1:2,1" +
"04,70,-1:3,104,46,104:3,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104,70,-1:2," +
"104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104,89,104:7,70:3,104,70,-1:2" +
",104,70,-1:3,104,47,104:3,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104,70,-1:" +
"2,104,70,-1:3,104,92,104:3,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104,70,-1" +
":2,104,70,-1:3,104,48,104:3,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104,70,-" +
"1:2,104,70,-1:3,104,49,104:3,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104,70," +
"-1:2,104,70,-1:3,104:4,93,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104,70,-1:" +
"2,104,70,-1:3,104,51,104:3,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104,70,-1" +
":2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:2,94,104:6,70:3,104,70" +
",-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:4,95,104:4,70:3,104" +
",70,-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:3,96,104:5,70:3," +
"104,70,-1:2,104,70,-1:3,97,104:4,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104" +
",70,-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:5,52,104:3,70:3," +
"104,70,-1:2,104,70,-1:3,53,104:4,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104" +
",70,-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:5,54,104:3,70:3," +
"104,70,-1:2,104,70,-1:3,104,57,104:3,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3" +
",104,70,-1:2,104,70,-1:3,104:3,72,104,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:" +
"3,104,70,-1:2,104,70,-1:3,65:5,-1:3,120:2,-1:4,65,100,65,-1:15,65:9,120:3,6" +
"5,120,-1:2,65,120,-1:3,65:5,-1:3,120:2,-1:4,65,69,65,-1:15,65:9,120:3,65,12" +
"0,-1:2,65,120,-1:3,104:3,83,104,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104," +
"70,-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104,84,104:7,70:3,104" +
",70,-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:6,90,104:2,70:3," +
"104,70,-1:2,104,70,-1:3,65:5,-1:3,120:2,-1:4,65:3,-1:15,65:5,119,65:3,120:3" +
",65,120,-1:2,65,120,-1:3,71,65:4,-1:3,120:2,-1:4,65:3,-1:15,65:9,120:3,65,1" +
"20,-1:2,65,120,-1:3,104:3,88,104,-1:3,104:2,-1:4,104:3,-1:15,104:9,70:3,104" +
",70,-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:6,91,104:2,70:3," +
"104,70,-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104,74,104,-1:15,104:9,70:3,1" +
"04,70,-1:2,104,70,-1:3,65,67,65:3,-1:3,120:2,-1:4,65:3,-1:15,65:9,120:3,65," +
"120,-1:2,65,120,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:3,101,104:2,75,1" +
"04:2,70:3,104,70,-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:5,7" +
"6,104:3,70:3,104,70,-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,104:" +
"3,102,104:5,70:3,104,70,-1:2,104,70,-1:3,77,104:4,-1:3,104:2,-1:4,104:3,-1:" +
"15,104:9,70:3,104,70,-1:2,104,70,-1:3,104,78,104:3,-1:3,104:2,-1:4,104:3,-1" +
":15,104:9,70:3,104,70,-1:2,104,70,-1:3,104:5,-1:3,104:2,-1:4,104:3,-1:15,10" +
"4:4,79,104:3,80,70:3,104,70,-1:2,104,70,-1:3,104:2,107,104:2,-1:3,104:2,-1:" +
"4,104:3,-1:15,104:9,70:3,104,70,-1:2,104,70,-1:3,65:4,106,-1:3,120:2,-1:4,6" +
"5:3,-1:15,65:9,120:3,65,120,-1:2,65,120,-1:3,65:5,-1:3,120:2,-1:4,65:3,-1:1" +
"5,65:4,118,65:4,120:3,65,120,-1:2,65,120");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

  return ops.unidadEof();
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{iniCol = chr();}
					case -3:
						break;
					case 3:
						{}
					case -4:
						break;
					case 4:
						{return ops.unidadIdenVarFun();}
					case -5:
						break;
					case 5:
						{}
					case -6:
						break;
					case 6:
						{errores.errorLexico(fila(),col(),lexema());}
					case -7:
						break;
					case 7:
						{return ops.unidadResta();}
					case -8:
						break;
					case 8:
						{return ops.unidadEnt();}
					case -9:
						break;
					case 9:
						{return ops.unidadPunto();}
					case -10:
						break;
					case 10:
						{return ops.unidadSuma();}
					case -11:
						break;
					case 11:
						{return ops.unidadMult();}
					case -12:
						break;
					case 12:
						{return ops.unidadDiv();}
					case -13:
						break;
					case 13:
						{return ops.unidadPap();}
					case -14:
						break;
					case 14:
						{return ops.unidadPcl();}
					case -15:
						break;
					case 15:
						{return ops.unidadIgual();}
					case -16:
						break;
					case 16:
						{return ops.unidadComa();}
					case -17:
						break;
					case 17:
						{return ops.unidadCap();}
					case -18:
						break;
					case 18:
						{return ops.unidadCcl();}
					case -19:
						break;
					case 19:
						{return ops.unidadBap();}
					case -20:
						break;
					case 20:
						{return ops.unidadBcl();}
					case -21:
						break;
					case 21:
						{return ops.unidadPcoma();}
					case -22:
						break;
					case 22:
						{return ops.unidadDosPuntos();}
					case -23:
						break;
					case 23:
						{return ops.unidadVert();}
					case -24:
						break;
					case 24:
						{return ops.unidadRef();}
					case -25:
						break;
					case 25:
						{return ops.unidadDeref();}
					case -26:
						break;
					case 26:
						{return ops.unidadMayor();}
					case -27:
						break;
					case 27:
						{return ops.unidadMenor();}
					case -28:
						break;
					case 28:
						{return ops.unidadIdenTipo();}
					case -29:
						break;
					case 29:
						{return ops.unidadIf();}
					case -30:
						break;
					case 30:
						{return ops.unidadThen();}
					case -31:
						break;
					case 31:
						{return ops.unidadReal();}
					case -32:
						break;
					case 32:
						{return ops.unidadConcat();}
					case -33:
						break;
					case 33:
						{return ops.unidadOr();}
					case -34:
						break;
					case 34:
						{return ops.unidadEsIgual();}
					case -35:
						break;
					case 35:
						{return ops.unidadMayig();}
					case -36:
						break;
					case 36:
						{return ops.unidadMenig();}
					case -37:
						break;
					case 37:
						{return ops.unidadString();}
					case -38:
						break;
					case 38:
						{return ops.unidadNot();}
					case -39:
						break;
					case 39:
						{return ops.unidadInf();}
					case -40:
						break;
					case 40:
						{return ops.unidadMod();}
					case -41:
						break;
					case 41:
						{return ops.unidadFun();}
					case -42:
						break;
					case 42:
						{return ops.unidadAnd();}
					case -43:
						break;
					case 43:
						{return ops.unidadTipoInt();}
					case -44:
						break;
					case 44:
						{return ops.unidadTipoDec();}
					case -45:
						break;
					case 45:
						{return ops.unidadNull();}
					case -46:
						break;
					case 46:
						{return ops.unidadElse();}
					case -47:
						break;
					case 47:
						{return ops.unidadCase();}
					case -48:
						break;
					case 48:
						{return ops.unidadTrue();}
					case -49:
						break;
					case 49:
						{return ops.unidadType();}
					case -50:
						break;
					case 50:
						{return ops.unidadTipoBool();}
					case -51:
						break;
					case 51:
						{return ops.unidadFalse();}
					case -52:
						break;
					case 52:
						{return ops.unidadStruct();}
					case -53:
						break;
					case 53:
						{return ops.unidadReturn();}
					case -54:
						break;
					case 54:
						{return ops.unidadRep();}
					case -55:
						break;
					case 55:
						{return ops.unidadTipoString();}
					case -56:
						break;
					case 57:
						{}
					case -57:
						break;
					case 58:
						{return ops.unidadIdenVarFun();}
					case -58:
						break;
					case 59:
						{errores.errorLexico(fila(),col(),lexema());}
					case -59:
						break;
					case 60:
						{return ops.unidadEnt();}
					case -60:
						break;
					case 61:
						{return ops.unidadIdenTipo();}
					case -61:
						break;
					case 63:
						{return ops.unidadIdenVarFun();}
					case -62:
						break;
					case 64:
						{errores.errorLexico(fila(),col(),lexema());}
					case -63:
						break;
					case 65:
						{return ops.unidadIdenTipo();}
					case -64:
						break;
					case 66:
						{return ops.unidadIdenVarFun();}
					case -65:
						break;
					case 67:
						{return ops.unidadIdenTipo();}
					case -66:
						break;
					case 68:
						{return ops.unidadIdenVarFun();}
					case -67:
						break;
					case 69:
						{return ops.unidadIdenTipo();}
					case -68:
						break;
					case 70:
						{return ops.unidadIdenVarFun();}
					case -69:
						break;
					case 71:
						{return ops.unidadIdenTipo();}
					case -70:
						break;
					case 72:
						{return ops.unidadIdenVarFun();}
					case -71:
						break;
					case 73:
						{return ops.unidadIdenVarFun();}
					case -72:
						break;
					case 74:
						{return ops.unidadIdenVarFun();}
					case -73:
						break;
					case 75:
						{return ops.unidadIdenVarFun();}
					case -74:
						break;
					case 76:
						{return ops.unidadIdenVarFun();}
					case -75:
						break;
					case 77:
						{return ops.unidadIdenVarFun();}
					case -76:
						break;
					case 78:
						{return ops.unidadIdenVarFun();}
					case -77:
						break;
					case 79:
						{return ops.unidadIdenVarFun();}
					case -78:
						break;
					case 80:
						{return ops.unidadIdenVarFun();}
					case -79:
						break;
					case 81:
						{return ops.unidadIdenVarFun();}
					case -80:
						break;
					case 82:
						{return ops.unidadIdenVarFun();}
					case -81:
						break;
					case 83:
						{return ops.unidadIdenVarFun();}
					case -82:
						break;
					case 84:
						{return ops.unidadIdenVarFun();}
					case -83:
						break;
					case 85:
						{return ops.unidadIdenVarFun();}
					case -84:
						break;
					case 86:
						{return ops.unidadIdenVarFun();}
					case -85:
						break;
					case 87:
						{return ops.unidadIdenVarFun();}
					case -86:
						break;
					case 88:
						{return ops.unidadIdenVarFun();}
					case -87:
						break;
					case 89:
						{return ops.unidadIdenVarFun();}
					case -88:
						break;
					case 90:
						{return ops.unidadIdenVarFun();}
					case -89:
						break;
					case 91:
						{return ops.unidadIdenVarFun();}
					case -90:
						break;
					case 92:
						{return ops.unidadIdenVarFun();}
					case -91:
						break;
					case 93:
						{return ops.unidadIdenVarFun();}
					case -92:
						break;
					case 94:
						{return ops.unidadIdenVarFun();}
					case -93:
						break;
					case 95:
						{return ops.unidadIdenVarFun();}
					case -94:
						break;
					case 96:
						{return ops.unidadIdenVarFun();}
					case -95:
						break;
					case 97:
						{return ops.unidadIdenVarFun();}
					case -96:
						break;
					case 98:
						{return ops.unidadIdenVarFun();}
					case -97:
						break;
					case 99:
						{return ops.unidadIdenTipo();}
					case -98:
						break;
					case 100:
						{return ops.unidadIdenTipo();}
					case -99:
						break;
					case 101:
						{return ops.unidadIdenVarFun();}
					case -100:
						break;
					case 102:
						{return ops.unidadIdenVarFun();}
					case -101:
						break;
					case 103:
						{return ops.unidadIdenVarFun();}
					case -102:
						break;
					case 104:
						{return ops.unidadIdenVarFun();}
					case -103:
						break;
					case 105:
						{return ops.unidadIdenTipo();}
					case -104:
						break;
					case 106:
						{return ops.unidadIdenTipo();}
					case -105:
						break;
					case 107:
						{return ops.unidadIdenVarFun();}
					case -106:
						break;
					case 108:
						{return ops.unidadIdenVarFun();}
					case -107:
						break;
					case 109:
						{return ops.unidadIdenVarFun();}
					case -108:
						break;
					case 110:
						{return ops.unidadIdenTipo();}
					case -109:
						break;
					case 111:
						{return ops.unidadIdenVarFun();}
					case -110:
						break;
					case 112:
						{return ops.unidadIdenVarFun();}
					case -111:
						break;
					case 113:
						{return ops.unidadIdenVarFun();}
					case -112:
						break;
					case 114:
						{return ops.unidadIdenVarFun();}
					case -113:
						break;
					case 115:
						{return ops.unidadIdenVarFun();}
					case -114:
						break;
					case 116:
						{return ops.unidadIdenVarFun();}
					case -115:
						break;
					case 117:
						{return ops.unidadIdenVarFun();}
					case -116:
						break;
					case 118:
						{return ops.unidadIdenTipo();}
					case -117:
						break;
					case 119:
						{return ops.unidadIdenTipo();}
					case -118:
						break;
					case 120:
						{return ops.unidadIdenTipo();}
					case -119:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
