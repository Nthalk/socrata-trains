#!/bin/bash
SCRIPT_PATH=$(dirname `which $0`)

cd $SCRIPT_PATH/../

sbt test
