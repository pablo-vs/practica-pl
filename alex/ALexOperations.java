package alex;

import asint.ClaseLexica;

public class ALexOperations {
  private AnalizadorLexicoTiny alex;
  public ALexOperations(AnalizadorLexicoTiny alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadEnt() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.ENT,alex.lexema()); 
  } 
  public UnidadLexica unidadReal() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.DEC,alex.lexema()); 
  } 
  public UnidadLexica unidadSuma() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.MAS,"+"); 
  } 
  public UnidadLexica unidadResta() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.MENOS,"-"); 
  } 
  public UnidadLexica unidadMult() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.POR,"*"); 
  } 
  public UnidadLexica unidadDiv() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.DIV,"/"); 
  } 
  public UnidadLexica unidadMod() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.MOD,"mod"); 
  } 
  public UnidadLexica unidadPap() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.PAP,"("); 
  } 
  public UnidadLexica unidadPcl() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.PCL,")"); 
  } 
  public UnidadLexica unidadIgual() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.IGUAL,"="); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.COMA,","); 
  } 
  public UnidadLexica unidadCap() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.CAP,"["); 
  } 
  public UnidadLexica unidadCcl() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.CCL,"]"); 
  } 
  public UnidadLexica unidadBap() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.BAP,"{"); 
  } 
  public UnidadLexica unidadBcl() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.BCL,"}"); 
  } 
  public UnidadLexica unidadPcoma() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.PCOMA,";"); 
  } 
  public UnidadLexica unidadPunto() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.PUNTO,"."); 
  } 
  public UnidadLexica unidadDosPuntos() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.DOSPUNTOS,":"); 
  } 
  public UnidadLexica unidadConcat() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.CONCAT,"++"); 
  } 
  public UnidadLexica unidadVert() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.VERT,"|"); 
  } 
  public UnidadLexica unidadRef() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.REF,"&"); 
  } 
  public UnidadLexica unidadDeref() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.DEREF,"^"); 
  } 
  public UnidadLexica unidadEsIgual() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.ES_IGUAL,"=="); 
  } 
  public UnidadLexica unidadMayor() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.MAYOR,">"); 
  } 
  public UnidadLexica unidadMenor() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.MENOR,"<"); 
  } 
  public UnidadLexica unidadMayig() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.MAYIG,">="); 
  } 
  public UnidadLexica unidadMenig() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.MENIG,"<="); 
  } 
  public UnidadLexica unidadIf() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.IF,"if"); 
  } 
  public UnidadLexica unidadElse() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.ELSE,"else"); 
  } 
  public UnidadLexica unidadCase() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.CASE,"case"); 
  } 
  public UnidadLexica unidadThen() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.THEN,"->"); 
  } 
  public UnidadLexica unidadAnd() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.AND,"and"); 
  } 
  public UnidadLexica unidadOr() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.OR,"or"); 
  } 
  public UnidadLexica unidadNot() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.NOT,"not"); 
  } 
  public UnidadLexica unidadNull() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.NULL,"null"); 
  } 
  public UnidadLexica unidadRep() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.REPEAT,"repeat"); 
  } 
  public UnidadLexica unidadInf() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.INF,"inf"); 
  } 
  public UnidadLexica unidadFun() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.FUN,"fun"); 
  } 
  public UnidadLexica unidadTrue() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.TRUE,"true"); 
  } 
  public UnidadLexica unidadFalse() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.FALSE,"false"); 
  } 
  public UnidadLexica unidadReturn() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.RETURN,"return"); 
  } 
  public UnidadLexica unidadStruct() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.STRUCT,"struct"); 
  } 
  public UnidadLexica unidadType() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.TYPE,"type"); 
  } 
  public UnidadLexica unidadTipoInt() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.TINT,"Int"); 
  } 
  public UnidadLexica unidadTipoBool() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.TBOOL,"Bool"); 
  } 
  public UnidadLexica unidadTipoString() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.TSTRING,"String"); 
  } 
  public UnidadLexica unidadTipoDec() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.TDEC,"Dec"); 
  } 
  public UnidadLexica unidadTipoNull() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.TNULL,"Null"); 
  } 
  public UnidadLexica unidadString() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.STRING,alex.lexema()); 
  } 
  public UnidadLexica unidadIden() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.IDEN,alex.lexema()); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.col(),ClaseLexica.EOF,"<EOF>"); 
  }
}
