#!/bin/bash
SCRIPT_PATH=$(dirname `which $0`)

echo "Running scalastyle..."
sbt scalastyle
echo "Running tests..."
$SCRIPT_PATH/test.sh
echo "Verifying main..."
$SCRIPT_PATH/verify.sh
