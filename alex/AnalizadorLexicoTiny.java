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
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
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
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

  ops = new ALexOperations(this);
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
		/* 55 */ YY_NOT_ACCEPT,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NOT_ACCEPT,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
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
		/* 113 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"3:8,4:2,1,3:2,4,3:18,4,3,48,2,3:2,26,49,15,16,10,9,18,5,8,11,7,6:9,24,23,29" +
",17,28,3:2,51,44,51,47,51:4,43,51:9,45,51:7,19,3,20,27,3:2,36,50,35,14,32,3" +
"1,46,50,30,50:2,33,12,37,13,41,50,38,34,39,40,50:3,42,50,21,25,22,3:65410,0" +
":2")[0];

	private int yy_rmap[] = unpackFromString(1,114,
"0,1:2,2,1,3,4,1,5,1:2,6,1:2,7,1:10,8,9,10,1,11,1,12,1:3,12,1,12:5,13:2,12:5" +
",13,12:4,13,14:2,15,16,17,18:2,19,13,20,21,12,22,23,24,25,26,27,28,29,30,31" +
",32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,12,51,52,53,54,55" +
",56,57,58,59,60,61,13,62,63,64,65,66")[0];

	private int yy_nxt[][] = unpackFromString(67,52,
"1,2,3,4,2,5,6,57,7,8,9,10,11,58,96,12,13,14,15,16,17,18,19,20,21,22,23,24,2" +
"5,26,62,101,105,96,107,109,110,111,112,113,96:3,27,97,102,96,106,56,61,96,1" +
"08,-1:54,3:50,-1:6,6,57,-1:20,28,-1:29,6:2,29,-1:49,6,57,-1,30,-1:48,96:2,-" +
"1:4,96,64,96,-1:15,96:13,66:3,96,66,-1:2,96,66,-1:17,32,-1:51,33,-1:51,34,-" +
"1:40,108:2,-1:4,108:3,-1:15,108:7,59,108:5,63:3,108,63,-1:2,108,63,-1:6,29:" +
"2,-1:50,96:2,-1:4,96:3,-1:15,96:13,66:3,96,66,-1:2,96,66,-1:6,108:2,-1:4,10" +
"8:3,-1:15,108:13,63:3,108,63,-1:2,108,63,-1,55:47,36,55:3,-1:8,29,-1:49,96:" +
"2,-1:4,96:3,-1:15,96:8,31,96:4,66:3,96,66,-1:2,96,66,-1:6,108:2,-1:4,108:3," +
"-1:15,108:9,42,108:3,63:3,108,63,-1:2,108,63,-1,60:48,36,60:2,-1:6,96:2,-1:" +
"4,96:3,-1:15,96,35,96:5,68,96:5,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:2,3" +
"7,-1:15,96:13,66:3,96,66,-1:2,96,66,-1:6,108:2,-1:4,108,71,108,-1:15,108:13" +
",63:3,108,63,-1:2,108,63,-1:6,108:2,-1:4,108:3,-1:15,108:8,73,108:4,63:3,10" +
"8,63,-1:2,108,63,-1:6,96:2,-1:4,96:3,-1:15,96,38,96:11,66:3,96,66,-1:2,96,6" +
"6,-1:6,108:2,-1:4,108:3,-1:15,108:5,43,108:7,63:3,108,63,-1:2,108,63,-1:6,9" +
"6:2,-1:4,96:3,-1:15,96:3,103,96:9,66:3,96,66,-1:2,96,66,-1:6,108:2,-1:4,108" +
":3,-1:15,108:3,49,108:9,63:3,108,63,-1:2,108,63,-1:6,96:2,-1:4,96:3,-1:15,9" +
"6:7,39,96:5,66:3,96,66,-1:2,96,66,-1:6,108:2,-1:4,108:3,-1:15,75,108:12,63:" +
"3,108,63,-1:2,108,63,-1:6,96:2,-1:4,96:3,-1:15,96:4,83,96:8,66:3,96,66,-1:2" +
",96,66,-1:6,108:2,-1:4,108:3,-1:15,108:7,77,108:5,63:3,108,63,-1:2,108,63,-" +
"1:6,96:2,-1:4,96:3,-1:15,96:8,100,96:4,66:3,96,66,-1:2,96,66,-1:6,108:2,-1:" +
"4,108:3,-1:15,108:13,63:3,54,63,-1:2,108,63,-1:6,96:2,-1:4,96:2,40,-1:15,96" +
":13,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:9,41,96:3,66:3,96,66" +
",-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:9,104,96,86,96,66:3,96,66,-1:2,96," +
"66,-1:6,96:2,-1:4,96:3,-1:15,96:10,87,96:2,66:3,96,66,-1:2,96,66,-1:6,96:2," +
"-1:4,96:3,-1:15,96:11,88,96,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15" +
",96:2,44,96:10,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:2,45,96:1" +
"0,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:3,46,96:9,66:3,96,66,-" +
"1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:2,92,96:10,66:3,96,66,-1:2,96,66,-1:" +
"6,96:2,-1:4,96:3,-1:15,96:2,47,96:10,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,9" +
"6:3,-1:15,96:2,48,96:10,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:" +
"2,50,96:10,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:5,93,96:7,66:" +
"3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:8,94,96:4,66:3,96,66,-1:2,9" +
"6,66,-1:6,96:2,-1:4,96:3,-1:15,96:6,95,96:6,66:3,96,66,-1:2,96,66,-1:6,96:2" +
",-1:4,96:3,-1:15,96:9,51,96:3,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:" +
"15,96:7,52,96:5,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:9,53,96:" +
"3,66:3,96,66,-1:2,96,66,-1:6,108:2,-1:4,108,65,108,-1:15,108:13,63:3,108,63" +
",-1:2,108,63,-1:6,96:2,-1:4,96:3,-1:15,96:3,85,96:9,66:3,96,66,-1:2,96,66,-" +
"1:6,96:2,-1:4,96:3,-1:15,96:4,84,96:8,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4," +
"96:3,-1:15,96:10,90,96:2,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96" +
":6,70,96:3,72,96:2,66:3,96,66,-1:2,96,66,-1:6,108:2,-1:4,108:3,-1:15,108:9," +
"67,108:3,63:3,108,63,-1:2,108,63,-1:6,96:2,-1:4,96:3,-1:15,96:4,89,96:8,66:" +
"3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:10,91,96:2,66:3,96,66,-1:2," +
"96,66,-1:6,96:2,-1:4,96:3,-1:15,96:3,74,96:9,66:3,96,66,-1:2,96,66,-1:6,108" +
":2,-1:4,108:3,-1:15,108:2,69,108:10,63:3,108,63,-1:2,108,63,-1:6,96:2,-1:4," +
"96:3,-1:15,96:9,76,96:3,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:" +
"6,99,96:6,66:3,96,66,-1:2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:7,78,96:5,66:3" +
",96,66,-1:2,96,66,-1:6,96:2,-1:4,96,79,96,-1:15,96:10,98,96:2,66:3,96,66,-1" +
":2,96,66,-1:6,96:2,-1:4,96:3,-1:15,96:2,80,96:10,66:3,96,66,-1:2,96,66,-1:6" +
",96:2,-1:4,96:3,-1:15,96:8,81,96:3,82,66:3,96,66,-1:2,96,66");

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
						{}
					case -3:
						break;
					case 3:
						{}
					case -4:
						break;
					case 4:
						{errores.errorLexico(fila(),lexema());}
					case -5:
						break;
					case 5:
						{return ops.unidadResta();}
					case -6:
						break;
					case 6:
						{return ops.unidadEnt();}
					case -7:
						break;
					case 7:
						{return ops.unidadPunto();}
					case -8:
						break;
					case 8:
						{return ops.unidadSuma();}
					case -9:
						break;
					case 9:
						{return ops.unidadMult();}
					case -10:
						break;
					case 10:
						{return ops.unidadDiv();}
					case -11:
						break;
					case 11:
						{return ops.unidadIdenVarFun();}
					case -12:
						break;
					case 12:
						{return ops.unidadPap();}
					case -13:
						break;
					case 13:
						{return ops.unidadPcl();}
					case -14:
						break;
					case 14:
						{return ops.unidadIgual();}
					case -15:
						break;
					case 15:
						{return ops.unidadComa();}
					case -16:
						break;
					case 16:
						{return ops.unidadCap();}
					case -17:
						break;
					case 17:
						{return ops.unidadCcl();}
					case -18:
						break;
					case 18:
						{return ops.unidadBap();}
					case -19:
						break;
					case 19:
						{return ops.unidadBcl();}
					case -20:
						break;
					case 20:
						{return ops.unidadPcoma();}
					case -21:
						break;
					case 21:
						{return ops.unidadDosPuntos();}
					case -22:
						break;
					case 22:
						{return ops.unidadVert();}
					case -23:
						break;
					case 23:
						{return ops.unidadRef();}
					case -24:
						break;
					case 24:
						{return ops.unidadDeref();}
					case -25:
						break;
					case 25:
						{return ops.unidadMayor();}
					case -26:
						break;
					case 26:
						{return ops.unidadMenor();}
					case -27:
						break;
					case 27:
						{return ops.unidadIdenTipo();}
					case -28:
						break;
					case 28:
						{return ops.unidadThen();}
					case -29:
						break;
					case 29:
						{return ops.unidadReal();}
					case -30:
						break;
					case 30:
						{return ops.unidadConcat();}
					case -31:
						break;
					case 31:
						{return ops.unidadOr();}
					case -32:
						break;
					case 32:
						{return ops.unidadEsIgual();}
					case -33:
						break;
					case 33:
						{return ops.unidadMayig();}
					case -34:
						break;
					case 34:
						{return ops.unidadMenig();}
					case -35:
						break;
					case 35:
						{return ops.unidadIf();}
					case -36:
						break;
					case 36:
						{return ops.unidadString();}
					case -37:
						break;
					case 37:
						{return ops.unidadMod();}
					case -38:
						break;
					case 38:
						{return ops.unidadInf();}
					case -39:
						break;
					case 39:
						{return ops.unidadFun();}
					case -40:
						break;
					case 40:
						{return ops.unidadAnd();}
					case -41:
						break;
					case 41:
						{return ops.unidadNot();}
					case -42:
						break;
					case 42:
						{return ops.unidadTipoInt();}
					case -43:
						break;
					case 43:
						{return ops.unidadTipoDec();}
					case -44:
						break;
					case 44:
						{return ops.unidadElse();}
					case -45:
						break;
					case 45:
						{return ops.unidadCase();}
					case -46:
						break;
					case 46:
						{return ops.unidadNull();}
					case -47:
						break;
					case 47:
						{return ops.unidadTrue();}
					case -48:
						break;
					case 48:
						{return ops.unidadType();}
					case -49:
						break;
					case 49:
						{return ops.unidadTipoBool();}
					case -50:
						break;
					case 50:
						{return ops.unidadFalse();}
					case -51:
						break;
					case 51:
						{return ops.unidadStruct();}
					case -52:
						break;
					case 52:
						{return ops.unidadReturn();}
					case -53:
						break;
					case 53:
						{return ops.unidadRep();}
					case -54:
						break;
					case 54:
						{return ops.unidadTipoString();}
					case -55:
						break;
					case 56:
						{errores.errorLexico(fila(),lexema());}
					case -56:
						break;
					case 57:
						{return ops.unidadEnt();}
					case -57:
						break;
					case 58:
						{return ops.unidadIdenVarFun();}
					case -58:
						break;
					case 59:
						{return ops.unidadIdenTipo();}
					case -59:
						break;
					case 61:
						{errores.errorLexico(fila(),lexema());}
					case -60:
						break;
					case 62:
						{return ops.unidadIdenVarFun();}
					case -61:
						break;
					case 63:
						{return ops.unidadIdenTipo();}
					case -62:
						break;
					case 64:
						{return ops.unidadIdenVarFun();}
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
						{return ops.unidadIdenTipo();}
					case -72:
						break;
					case 74:
						{return ops.unidadIdenVarFun();}
					case -73:
						break;
					case 75:
						{return ops.unidadIdenTipo();}
					case -74:
						break;
					case 76:
						{return ops.unidadIdenVarFun();}
					case -75:
						break;
					case 77:
						{return ops.unidadIdenTipo();}
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
						{return ops.unidadIdenTipo();}
					case -96:
						break;
					case 98:
						{return ops.unidadIdenVarFun();}
					case -97:
						break;
					case 99:
						{return ops.unidadIdenVarFun();}
					case -98:
						break;
					case 100:
						{return ops.unidadIdenVarFun();}
					case -99:
						break;
					case 101:
						{return ops.unidadIdenVarFun();}
					case -100:
						break;
					case 102:
						{return ops.unidadIdenTipo();}
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
						{return ops.unidadIdenVarFun();}
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
						{return ops.unidadIdenTipo();}
					case -107:
						break;
					case 109:
						{return ops.unidadIdenVarFun();}
					case -108:
						break;
					case 110:
						{return ops.unidadIdenVarFun();}
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
