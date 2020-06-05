package alex;

import asint.ClaseLexica;

public class ALexOperations {
  private AnalizadorLexicoTiny alex;
  public ALexOperations(AnalizadorLexicoTiny alex) {
   this.alex = alex;   
  }
  public UnidadLexica unidadEnt() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.ENT,alex.lexema()); 
  } 
  public UnidadLexica unidadReal() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.DEC,alex.lexema()); 
  } 
  public UnidadLexica unidadSuma() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.MAS,"+"); 
  } 
  public UnidadLexica unidadResta() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.MENOS,"-"); 
  } 
  public UnidadLexica unidadMult() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.POR,"*"); 
  } 
  public UnidadLexica unidadDiv() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.DIV,"/"); 
  } 
  public UnidadLexica unidadMod() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.MOD,"mod"); 
  } 
  public UnidadLexica unidadPap() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.PAP,"("); 
  } 
  public UnidadLexica unidadPcl() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.PCL,")"); 
  } 
  public UnidadLexica unidadIgual() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.IGUAL,"="); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.COMA,","); 
  } 
  public UnidadLexica unidadCap() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.CAP,"["); 
  } 
  public UnidadLexica unidadCcl() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.CCL,"]"); 
  } 
  public UnidadLexica unidadBap() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.BAP,"{"); 
  } 
  public UnidadLexica unidadBcl() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.BCL,"}"); 
  } 
  public UnidadLexica unidadPcoma() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.PCOMA,";"); 
  } 
  public UnidadLexica unidadPunto() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.PUNTO,"."); 
  } 
  public UnidadLexica unidadDosPuntos() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.DOSPUNTOS,":"); 
  } 
  public UnidadLexica unidadConcat() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.CONCAT,"++"); 
  } 
  public UnidadLexica unidadVert() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.VERT,"|"); 
  } 
  public UnidadLexica unidadRef() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.REF,"&"); 
  } 
  public UnidadLexica unidadDeref() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.DEREF,"^"); 
  } 
  public UnidadLexica unidadEsIgual() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.ES_IGUAL,"=="); 
  } 
  public UnidadLexica unidadMayor() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.MAYOR,">"); 
  } 
  public UnidadLexica unidadMenor() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.MENOR,"<"); 
  } 
  public UnidadLexica unidadMayig() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.MAYIG,">="); 
  } 
  public UnidadLexica unidadMenig() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.MENIG,"<="); 
  } 
  public UnidadLexica unidadIf() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.IF,"if"); 
  } 
  public UnidadLexica unidadElse() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.ELSE,"else"); 
  } 
  public UnidadLexica unidadCase() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.CASE,"case"); 
  } 
  public UnidadLexica unidadThen() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.THEN,"->"); 
  } 
  public UnidadLexica unidadAnd() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.AND,"and"); 
  } 
  public UnidadLexica unidadOr() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.OR,"or"); 
  } 
  public UnidadLexica unidadNot() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.NOT,"not"); 
  } 
  public UnidadLexica unidadNull() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.NULL,"null"); 
  } 
  public UnidadLexica unidadRep() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.REPEAT,"repeat"); 
  } 
  public UnidadLexica unidadInf() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.INF,"inf"); 
  } 
  public UnidadLexica unidadFun() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.FUN,"fun"); 
  } 
  public UnidadLexica unidadTrue() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.TRUE,"true"); 
  } 
  public UnidadLexica unidadFalse() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.FALSE,"false"); 
  } 
  public UnidadLexica unidadReturn() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.RETURN,"return"); 
  } 
  public UnidadLexica unidadStruct() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.STRUCT,"struct"); 
  } 
  public UnidadLexica unidadType() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.TYPE,"type"); 
  } 
  public UnidadLexica unidadTipoInt() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.TINT,"Int"); 
  } 
  public UnidadLexica unidadTipoBool() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.TBOOL,"Bool"); 
  } 
  public UnidadLexica unidadTipoString() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.TSTRING,"String"); 
  } 
  public UnidadLexica unidadTipoDec() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.TDEC,"Dev"); 
  } 
  public UnidadLexica unidadString() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.STRING,alex.lexema()); 
  } 
  public UnidadLexica unidadIdenVarFun() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.IDENVARFUN,alex.lexema()); 
  } 
  public UnidadLexica unidadIdenTipo() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.IDENTIPO,alex.lexema()); 
  } 
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.chr(),ClaseLexica.EOF,"<EOF>"); 
  }
}
