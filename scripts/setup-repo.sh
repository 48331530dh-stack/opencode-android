#!/usr/bin/env bash
# setup-repo.sh — Siapkan repo untuk build APK OpenCode Android
set -euo pipefail

REPO_URL="https://github.com/guysoft/opencode-termux"
BINARY_URL="$REPO_URL/releases/latest/download/opencode-aarch64"
ASSETS_DIR="app/src/main/assets"

echo ":: Downloading opencode aarch64 binary..."
mkdir -p "$ASSETS_DIR"
curl -L -o "$ASSETS_DIR/opencode-aarch64" "$BINARY_URL"
chmod +x "$ASSETS_DIR/opencode-aarch64"
echo "✓ Binary saved to $ASSETS_DIR/opencode-aarch64"

echo ""
echo "=== Next Steps ==="
echo "1. Push repo ke GitHub:"
echo "   git init && git add . && git commit -m 'init'"
echo "   git remote add origin <your-repo-url>"
echo "   git push -u origin main"
echo ""
echo "2. GitHub Actions akan build APK otomatis"
echo "3. Download APK dari Actions → Artifacts"
echo ""
