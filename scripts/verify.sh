#!/bin/bash
SCRIPT_PATH=$(dirname `which $0`)

echo "Verifying compiled and ran with input.txt against output.txt..."
$SCRIPT_PATH/run.sh | diff $SCRIPT_PATH/../output.txt -
echo "Done.