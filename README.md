# The MakerLab V2 — MakerLab EIA (Entrega 1)

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

