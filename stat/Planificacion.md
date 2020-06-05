# Planificación

## 0. Modificación sintaxis
	- Añadir:
		- Memoria dinámica explícita (malloc)
		- Constructores de listas (ej: new Int[100])
		- Casteo explícito?
		- Más funciones predefinidas

	- Aclarar en la descripción:
		- Ámbitos
		- Operaciones con infinito
		- Una instrucción sólo tiene efecto en las que aparecen después
			(es decir, por ejemplo 'hello(); fun hello() {};' da error

	- Eliminar entrada/salida?

## 1. Gestión de errores
	- Anotación de elementos por fila y columna (léxico y sintaxis)

	- Error de léxico: ?
		Puede haber un problema con los identificadores de tipo y variable:
		poner int en lugar de Int aparecerá como un error de léxico
		y puede que nos cueste más tratarlo que si apareciera
		como un error de identificador.

	- Error de sintaxis: intentar adivinar el error? (Falta ; , operando incorrecto...)

	- Error de tipos

	- Identificador no encontrado

## 2. Comprobación sem. estática
	- Comprobar iden
	- Comprobar tipos

## 3. Gen. código
