# Hytale Server Documentation
![Java](https://img.shields.io/badge/Java-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Hytale](https://img.shields.io/badge/Hytale-FF7175?style=for-the-badge&logo=anycubic&logoColor=white)
![Version](https://img.shields.io/badge/version-0.0.1-248cd6?labelColor=&style=for-the-badge)

Community-maintained Javadoc documentation for the Hytale Server API, designed to help plugin developers understand and work with the Hytale modding ecosystem.

> ⚠️ **Important**: This documentation was generated using LLM (Large Language Model) assistance and has been partially reviewed by humans. While we strive for accuracy, there may be errors, inaccuracies, or outdated information. **Contributions and corrections are highly encouraged!**

---

## What is This?

This project provides **Javadoc documentation** for the Hytale Server API. It does **not** contain or distribute any Hytale source code — only the documentation comments (Javadocs) that describe classes, methods, and their purposes.

The documentation is extracted from annotated sources and published in a portable JSON format (`hytale-docs.json`), which can then be injected into your local decompiled server sources using the [Javadoc Migration Plugin](#related-projects).

---

## Usage

### Applying Documentation to Your Project

1. **Set up a Hytale plugin project** using the [Hytale Gradle Plugin](https://github.com/smoldermc/hytale-plugin).

2. **Add the Javadoc Migration Plugin** to your `build.gradle.kts`:

```kotlin
plugins {
    id("fr.smolder.hytale.dev") version "0.0.4"
    id("fr.smolder.javadoc.migration") version "0.0.1"
}
```

3. **Configure the migration** to pull documentation from this repository:

```kotlin
javadocMigration {
    // Fetch documentation from the published JSON
    docsUrl.set("https://raw.githubusercontent.com/GhostRider584/hytale-docs/refs/heads/master/javadocs-export.json")
    
    // Or use a local file
    // docsFile.set(file("path/to/hytale-docs.json"))
    
    // The server JAR to inject documentation into
    newJar.set(hytale.serverJar)
    
    // Output directory for documented sources
    outputDir.set(layout.buildDirectory.dir("documented-sources"))
    
    // Filter which packages to document
    decompileFilter.set(listOf("com/hypixel/**"))
}
```

4. **Disable auto-included sources** (optional but recommended):

By default, the Hytale Gradle Plugin automatically includes decompiled sources in your IDE. If you want to use the migrated documented sources instead, disable this:

```kotlin
hytale {
    includeDecompiledSources.set(false)
}
```

5. **Run the migration task**:

```bash
./gradlew migrateJavadocs
```

This will decompile your local Hytale server JAR and inject the community documentation into the sources.

6. **Configure your IDE to use the documented sources**:

After running the migration, you need to attach the generated sources to your project:

**IntelliJ IDEA:**
- Open **Project Structure** (`Ctrl+Alt+Shift+S` or `File > Project Structure`)
- Go to **Modules** → Your module → **Dependencies**
- Find the Hytale server JAR dependency
- Click the **Edit** icon (pencil) next to it
- In **Sources**, click **+** and select: `build/documented-sources` (or wherever you configured `outputDir`)
- Click **OK** and apply changes

Alternatively, you can configure this in Gradle:

```kotlin
idea {
    module {
        // Automatically attach documented sources in IntelliJ IDEA
        sourceDirs.add(file("build/documented-sources"))
    }
}
```

Now when you navigate to Hytale classes in your IDE, you'll see the community-maintained documentation.

---

## How It Works

### Documentation Format

Documentation is stored in a portable JSON format that contains:

- **Class-level Javadocs** — Descriptions of what each class does and its architectural role
- **Method signatures** — Hashed signatures to match documentation to the correct methods across versions
- **Human-readable names** — Preserved even when obfuscation changes

### Migration Process

When you run `migrateJavadocs`:

1. Your local Hytale server JAR is decompiled using [Vineflower](https://github.com/Vineflower/vineflower)
2. The documentation JSON is loaded (from URL or local file)
3. Javadocs are matched to classes/methods using signature hashing
4. A migration report is generated showing preserved, new, and orphaned entries
5. Documented sources are output to your specified directory

This allows documentation to persist across Hytale updates, even when the API changes.

---

## Contributing

We welcome contributions! Since this documentation is partially LLM-generated, **human review and corrections are invaluable**.

### How to Contribute

1. **Fork this repository**
2. **Clone and set up the project**:
   ```bash
   git clone https://github.com/YOUR_USERNAME/hytale-docs.git
   cd hytale-docs
   ```

3. **Use the Hytale Gradle Plugin** to decompile and generate your local `documented-sources/`:
   ```bash
   ./gradlew decompileServer
   ```

4. **Edit the Javadocs** in the decompiled sources

5. **Export your changes**:
   ```bash
   ./gradlew exportJavadocs
   ```

6. **Submit a pull request** with your improvements

### Contribution Guidelines

- ✅ Fix inaccuracies or errors in existing documentation
- ✅ Add missing documentation for undocumented classes/methods
- ✅ Improve clarity and add helpful examples
- ✅ Add `@see` and `{@link}` cross-references
- ❌ Do not commit decompiled code

---

## Related Projects

| Project | Description |
|---------|-------------|
| [Hytale Gradle Plugin](https://github.com/GhostRider584/hytale-gradle-plugin) | Gradle plugin for Hytale plugin development — handles decompilation, server running, and build tasks |
| [Javadoc Migration Plugin](https://github.com/GhostRider584/javadoc-migration-plugin) | Gradle plugin for extracting, exporting, and migrating Javadoc documentation |

---

## Disclaimer

This project provides **documentation only** — no Hytale source code, binaries, or assets are distributed. All Hytale IP belongs to Hypixel Studios. If you represent Hypixel Studios and have concerns, please open an issue or contact us directly and I will address it promptly.
