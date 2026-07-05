# OpenCode Android APK

OpenCode dengan **free Zen models built-in** — dalam bentuk APK Android native.

> Dibungkus dari binary `guysoft/opencode-termux` (aarch64) + WebView wrapper.

## Download APK

Belum ada release. Build sendiri lewat GitHub Actions (gratis):

### Build via GitHub Actions (otomatis)

1. **Fork repo ini** ke GitHub kamu
2. **Push ke `main`** — Actions akan build APK
3. **Download APK** dari tab Actions → artifacts
4. **Sideload** ke HP Android (aktifkan "Install from unknown sources")

Atau build manual:

```bash
# 1. Download binary opencode aarch64
curl -L -o app/src/main/assets/opencode-aarch64 \
  https://github.com/guysoft/opencode-termux/releases/latest/download/opencode-aarch64
chmod +x app/src/main/assets/opencode-aarch64

# 2. Build APK (butuh Android SDK)
./gradlew assembleRelease

# APK: app/build/outputs/apk/release/app-release-unsigned.apk
```

## Fitur

- **Free models built-in**: big-pickle, deepseek-v4-flash-free, mimo-v2.5-free, nemotron-3-ultra-free, north-mini-code-free
- **No API key required** — langsung pakai
- **No external server needed** — semua jalan di device
- **WebView UI** — sama kayak OpenCode desktop
- **~100 request/hari gratis** via Zen (tanpa login)

## Cara Pakai

1. Install APK
2. Buka app → otomatis start server + buka WebView
3. Mulai coding

## Tech Stack

- **Binary**: OpenCode CLI via `guysoft/opencode-termux` (cross-compile aarch64)
- **Wrapper**: Android native (Kotlin + Jetpack Compose)
- **UI**: WebView ke localhost OpenCode server
- **CI**: GitHub Actions build APK otomatis

## License

Apache 2.0 — sama seperti OpenCode.
