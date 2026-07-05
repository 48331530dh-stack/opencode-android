# OpenCode Android APK — Agent Guide

## Project Structure

```
opencode-apk/
├── .github/workflows/build.yml   # CI: build APK otomatis
├── app/
│   ├── build.gradle.kts           # Android app config
│   ├── proguard-rules.pro
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/opencode/android/
│       │   └── MainActivity.kt    # WebView wrapper + binary executor
│       └── res/values/
│           ├── strings.xml
│           └── themes.xml
├── scripts/
│   └── setup-repo.sh              # Download binary + siapkan repo
├── build.gradle.kts               # Root project config
├── settings.gradle.kts
├── gradle.properties
├── gradlew / gradlew.bat          # Gradle wrapper scripts
└── gradle/wrapper/
    └── gradle-wrapper.properties
```

## How It Works

1. Binary `opencode-aarch64` di-download dari `guysoft/opencode-termux` releases
2. Binary di-bundle ke APK sebagai asset
3. Saat app jalan, binary di-extract ke data dir
4. `ProcessBuilder` menjalankan `opencode serve --hostname 127.0.0.1 --port 4096`
5. WebView konek ke `http://127.0.0.1:4096`
6. Semua model Zen free built-in langsung tersedia

## Key Dependencies

- Binary: `guysoft/opencode-termux` (cross-compile untuk aarch64 Android)
- UI: WebView (Jetpack Compose + AndroidView)
- Server: OpenCode built-in web server

## Build Pipeline (CI/CD)

Push ke main → GitHub Actions:
1. Setup JDK 17 + Android SDK + Gradle
2. Generate Gradle wrapper
3. Download opencode aarch64 binary dari latest release
4. Build APK (assembleRelease)
5. Sign APK (jika keystore secrets ada)
6. Upload artifact / release

## Model Zen Free (Built-in)

Tanpa perlu API key:
- `opencode/big-pickle`
- `opencode/deepseek-v4-flash-free`
- `opencode/mimo-v2.5-free`
- `opencode/nemotron-3-ultra-free`
- `opencode/north-mini-code-free`

## Constraints

- ARM64 (aarch64) only — kebanyakan HP 2020+
- Butuh Android 7.0+ (API 24)
- Binary ~100-200MB
- Tidak ada signing key default — unsigned APK atau setup secrets
