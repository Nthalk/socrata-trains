#!/bin/bash
SCRIPT_PATH=$(dirname `which $0`)

# Support for linux
SED=sed
if [ -x $(which gsed) ]; then
  SED=gsed
fi

cd $SCRIPT_PATH/../

cat input.txt | java -cp $(
  sbt compile "show full-classpath" |
  grep List |
  $SED -r "s/\x1B\[([0-9]{1,2}(;[0-9]{1,2})?)?[m|K]//g" | 
  $SED 's/\[info\] List(//' | 
  $SED 's/)//g' | 
  $SED 's/Attributed(//g' | 
  $SED 's/, /:/g'
) com.nthalk.socrata.trains.Main