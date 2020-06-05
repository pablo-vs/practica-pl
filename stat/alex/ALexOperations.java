package alex;

import asint.ClaseLexica;

public class ALexOperations {
  private AnalizadorLexicoTiny alex;
  public ALexOperations(AnalizadorLexicoTiny alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadEnt() {
     return new UnidadLexica(alex.fila(),ClaseLexica.ENT,alex.lexema()); 
  } 
  public UnidadLexica unidadReal() {
     return new UnidadLexica(alex.fila(),ClaseLexica.DEC,alex.lexema()); 
  } 
  public UnidadLexica unidadSuma() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MAS,"+"); 
  } 
  public UnidadLexica unidadResta() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MENOS,"-"); 
  } 
  public UnidadLexica unidadMult() {
     return new UnidadLexica(alex.fila(),ClaseLexica.POR,"*"); 
  } 
  public UnidadLexica unidadDiv() {
     return new UnidadLexica(alex.fila(),ClaseLexica.DIV,"/"); 
  } 
  public UnidadLexica unidadMod() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MOD,"mod"); 
  } 
  public UnidadLexica unidadPap() {
     return new UnidadLexica(alex.fila(),ClaseLexica.PAP,"("); 
  } 
  public UnidadLexica unidadPcl() {
     return new UnidadLexica(alex.fila(),ClaseLexica.PCL,")"); 
  } 
  public UnidadLexica unidadIgual() {
     return new UnidadLexica(alex.fila(),ClaseLexica.IGUAL,"="); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(),ClaseLexica.COMA,","); 
  } 
  public UnidadLexica unidadCap() {
     return new UnidadLexica(alex.fila(),ClaseLexica.CAP,"["); 
  } 
  public UnidadLexica unidadCcl() {
     return new UnidadLexica(alex.fila(),ClaseLexica.CCL,"]"); 
  } 
  public UnidadLexica unidadBap() {
     return new UnidadLexica(alex.fila(),ClaseLexica.BAP,"{"); 
  } 
  public UnidadLexica unidadBcl() {
     return new UnidadLexica(alex.fila(),ClaseLexica.BCL,"}"); 
  } 
  public UnidadLexica unidadPcoma() {
     return new UnidadLexica(alex.fila(),ClaseLexica.PCOMA,";"); 
  } 
  public UnidadLexica unidadPunto() {
     return new UnidadLexica(alex.fila(),ClaseLexica.PUNTO,"."); 
  } 
  public UnidadLexica unidadDosPuntos() {
     return new UnidadLexica(alex.fila(),ClaseLexica.DOSPUNTOS,":"); 
  } 
  public UnidadLexica unidadConcat() {
     return new UnidadLexica(alex.fila(),ClaseLexica.CONCAT,"++"); 
  } 
  public UnidadLexica unidadVert() {
     return new UnidadLexica(alex.fila(),ClaseLexica.VERT,"|"); 
  } 
  public UnidadLexica unidadRef() {
     return new UnidadLexica(alex.fila(),ClaseLexica.REF,"&"); 
  } 
  public UnidadLexica unidadDeref() {
     return new UnidadLexica(alex.fila(),ClaseLexica.DEREF,"^"); 
  } 
  public UnidadLexica unidadEsIgual() {
     return new UnidadLexica(alex.fila(),ClaseLexica.ES_IGUAL,"=="); 
  } 
  public UnidadLexica unidadMayor() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MAYOR,">"); 
  } 
  public UnidadLexica unidadMenor() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MENOR,"<"); 
  } 
  public UnidadLexica unidadMayig() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MAYIG,">="); 
  } 
  public UnidadLexica unidadMenig() {
     return new UnidadLexica(alex.fila(),ClaseLexica.MENIG,"<="); 
  } 
  public UnidadLexica unidadIf() {
     return new UnidadLexica(alex.fila(),ClaseLexica.IF,"if"); 
  } 
  public UnidadLexica unidadElse() {
     return new UnidadLexica(alex.fila(),ClaseLexica.ELSE,"else"); 
  } 
  public UnidadLexica unidadCase() {
     return new UnidadLexica(alex.fila(),ClaseLexica.CASE,"case"); 
  } 
  public UnidadLexica unidadThen() {
     return new UnidadLexica(alex.fila(),ClaseLexica.THEN,"->"); 
  } 
  public UnidadLexica unidadAnd() {
     return new UnidadLexica(alex.fila(),ClaseLexica.AND,"and"); 
  } 
  public UnidadLexica unidadOr() {
     return new UnidadLexica(alex.fila(),ClaseLexica.OR,"or"); 
  } 
  public UnidadLexica unidadNot() {
     return new UnidadLexica(alex.fila(),ClaseLexica.NOT,"not"); 
  } 
  public UnidadLexica unidadNull() {
     return new UnidadLexica(alex.fila(),ClaseLexica.NULL,"null"); 
  } 
  public UnidadLexica unidadRep() {
     return new UnidadLexica(alex.fila(),ClaseLexica.REPEAT,"repeat"); 
  } 
  public UnidadLexica unidadInf() {
     return new UnidadLexica(alex.fila(),ClaseLexica.INF,"inf"); 
  } 
  public UnidadLexica unidadFun() {
     return new UnidadLexica(alex.fila(),ClaseLexica.FUN,"fun"); 
  } 
  public UnidadLexica unidadTrue() {
     return new UnidadLexica(alex.fila(),ClaseLexica.TRUE,"true"); 
  } 
  public UnidadLexica unidadFalse() {
     return new UnidadLexica(alex.fila(),ClaseLexica.FALSE,"false"); 
  } 
  public UnidadLexica unidadReturn() {
     return new UnidadLexica(alex.fila(),ClaseLexica.RETURN,"return"); 
  } 
  public UnidadLexica unidadStruct() {
     return new UnidadLexica(alex.fila(),ClaseLexica.STRUCT,"struct"); 
  } 
  public UnidadLexica unidadType() {
     return new UnidadLexica(alex.fila(),ClaseLexica.TYPE,"type"); 
  } 
  public UnidadLexica unidadTipoInt() {
     return new UnidadLexica(alex.fila(),ClaseLexica.TINT,"Int"); 
  } 
  public UnidadLexica unidadTipoBool() {
     return new UnidadLexica(alex.fila(),ClaseLexica.TBOOL,"Bool"); 
  } 
  public UnidadLexica unidadTipoString() {
     return new UnidadLexica(alex.fila(),ClaseLexica.TSTRING,"String"); 
  } 
  public UnidadLexica unidadTipoDec() {
     return new UnidadLexica(alex.fila(),ClaseLexica.TDEC,"Dev"); 
  } 
  public UnidadLexica unidadString() {
     return new UnidadLexica(alex.fila(),ClaseLexica.STRING,alex.lexema()); 
  } 
  public UnidadLexica unidadIdenVarFun() {
     return new UnidadLexica(alex.fila(),ClaseLexica.IDENVARFUN,alex.lexema()); 
  } 
  public UnidadLexica unidadIdenTipo() {
     return new UnidadLexica(alex.fila(),ClaseLexica.IDENTIPO,alex.lexema()); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),ClaseLexica.EOF,"<EOF>"); 
  }
}
