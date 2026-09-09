# The MakerLab V2 — MakerLab EIA (Entrega 1)

Esta es una **copia del proyecto `TheMakerLab`** con el **mismo código y la misma
funcionalidad**, pero con las anotaciones/comentarios línea a línea **eliminados** para
poder leerlo de forma más compacta en Eclipse.

- El código es **idéntico** al del proyecto original; solo se quitaron los comentarios
  (`//`, `/* */`, `/** */`). La salida por consola es exactamente la misma.
- Nombre del proyecto Eclipse: **`TheMakerLab V2`** (distinto del original, para poder
  tener ambos importados a la vez).
- Clase principal: `co.edu.eia.makerlab.app.MakerLabApp`.

## Importar y ejecutar (Eclipse)

1. `File → Import… → General → Existing Projects into Workspace`.
2. *Select root directory* → carpeta **`TheMakerLab V2`** → **Finish**.
3. Clic derecho sobre `src/co/edu/eia/makerlab/app/MakerLabApp.java` → **Run As → Java Application**.
4. Escribe la capacidad `n` (por ejemplo `15`) cuando la pida.

## Ejecutar por consola

```bash
find src -name "*.java" > sources.txt && javac -d bin -encoding UTF-8 @sources.txt && rm sources.txt
java -cp bin co.edu.eia.makerlab.app.MakerLabApp
```

> La **documentación completa** (README detallado, comprensión del problema, UML y mapeo a
> Java) está en el proyecto original **`TheMakerLab`**, carpeta `docs/`.
