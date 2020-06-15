/*
 * Primera entrega: léxico
 * Marcos Brian Leiva
 * Pablo Villalobos
 */

package alex;

import errors.GestionErroresTiny;

%%
%cup
%line
%char
%class AnalizadorLexicoTiny
%unicode
%public

%{
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
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
  iniCol = 0;
%init}

letraMayus = [A-Z]
letraMinus =[a-z]
letra  = ({letraMayus}|{letraMinus})
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = (({digitoPositivo}{digito}*)|0)
parteDecimal = {digito}* {digitoPositivo}
newline = \n
separador = [ \t\r\b]|newline
comentario = #[^\n]* 
numEnt = [+-]?{parteEntera}
numReal = [+-]?{parteEntera}\.{parteDecimal}
suma = \+
resta = -
mult = \*
div = /
mod = mod
pap = \(
pcl = \)
igual = \=
coma  = \,
cap = \[
ccl = \]
bap = \{
bcl = \}
pcoma = ;
punto = \.
dospuntos = :
concat = \+\+
vert = \|
ref = \&
deref = "^"
esigual = \=\=
mayor = >
menor = <
mayig = >=
menig = <=

if = if
else = else
case = case
then = ->
and = and
or = or
not = not
null = null
rep = repeat
inf = inf
fun = fun
true = true
false = false
return = return
struct = struct
type = type
tipoInt = Int
tipoBool = Bool
tipoString = String
tipoDec = Dec
tipoNull = Null
string = (\"[^\"]*\")|('[^']*')


iden = {letra}({letra}|{digito})*


%%
{newline}				{iniCol = chr();}
{separador}				{}
{comentario}			{}
{numEnt}				{return ops.unidadEnt();}
{numReal}				{return ops.unidadReal();}
{suma}					{return ops.unidadSuma();}
{resta}					{return ops.unidadResta();}
{mult}					{return ops.unidadMult();}
{div}					{return ops.unidadDiv();}
{mod}					{return ops.unidadMod();}
{pap}					{return ops.unidadPap();}
{pcl}					{return ops.unidadPcl();}
{igual}					{return ops.unidadIgual();}
{coma}					{return ops.unidadComa();}
{cap}					{return ops.unidadCap();}
{ccl}					{return ops.unidadCcl();}
{bap}					{return ops.unidadBap();}
{bcl}					{return ops.unidadBcl();}
{pcoma}					{return ops.unidadPcoma();}
{punto}					{return ops.unidadPunto();}
{dospuntos}				{return ops.unidadDosPuntos();}
{concat}				{return ops.unidadConcat();}
{vert}					{return ops.unidadVert();}
{ref}					{return ops.unidadRef();}
{deref}					{return ops.unidadDeref();}
{esigual}				{return ops.unidadEsIgual();}
{mayor}					{return ops.unidadMayor();}
{menor}					{return ops.unidadMenor();}
{mayig}					{return ops.unidadMayig();}
{menig}					{return ops.unidadMenig();}
{if}					{return ops.unidadIf();}
{else}					{return ops.unidadElse();}
{case}					{return ops.unidadCase();}
{then}					{return ops.unidadThen();}
{and}					{return ops.unidadAnd();}
{or}					{return ops.unidadOr();}
{not}					{return ops.unidadNot();}
{null}					{return ops.unidadNull();}
{rep}					{return ops.unidadRep();}
{inf}					{return ops.unidadInf();}
{fun}					{return ops.unidadFun();}
{true}					{return ops.unidadTrue();}
{false}					{return ops.unidadFalse();}
{return}				{return ops.unidadReturn();}
{struct}				{return ops.unidadStruct();}
{type}					{return ops.unidadType();}
{tipoInt}				{return ops.unidadTipoInt();}
{tipoBool}				{return ops.unidadTipoBool();}
{tipoString}			{return ops.unidadTipoString();}
{tipoDec}				{return ops.unidadTipoDec();}
{tipoNull}				{return ops.unidadTipoNull();}
{string}				{return ops.unidadString();}
{iden}					{return ops.unidadIden();}

[^]						{errores.errorLexico(fila(),col(),lexema());}
