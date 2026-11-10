#!/usr/bin/env bash
# =====================================================================
# Campus Feedback System - One-Click Build & Run Script (Linux/macOS)
# =====================================================================

set -e

echo "========================================================"
echo "      Compiling Campus Feedback System (Core Java)      "
echo "========================================================"

# Create bin directory if it does not exist
mkdir -p bin

# Compile all source files
javac -d bin $(find src -name "*.java")

echo "[SUCCESS] Compilation complete. Launching application..."
echo ""

# Run application
java -cp bin main.Main

