#!/bin/bash

# WhiteMatter build script
# zip of all necessary files will be found in release/whitematter.zip
# Tommy MacWilliam, 2010

# delete directories if existing
if [ -d whitematter ]; then
rm -r whitematter
fi

if [ -d release ]; then
rm -r release
fi

# create new directories
mkdir whitematter
mkdir release

# copy dist directory to temporary folder
cp -r dist/* whitematter

# copy libs for jdic
cp -r lib/jdic/windows whitematter/lib
cp -r lib/jdic/linux whitematter/lib
cp -r lib/jdic/sunos whitematter/lib

# clear sticklet and notebook databases
rm sticklets.udb
rm notebook.udb

# wipe databases
echo "[0]" > sticklets.udb
echo "[0]" > notebook.udb

# copy database files
cp windowsprocesses.udb whitematter
cp linuxprocesses.udb whitematter

# create windows executable
rm whitematter.exe

# replace with path to launch4j
/home/tmac/Java/launch4j/launch4j /home/tmac/Projects/whitematter/launch4j.xml

# copy windows executables
cp whitematter.exe whitematter
cp tasklist.exe whitematter

# copy linux launcher
chmod 777 whitematter.sh
cp whitematter.sh whitematter

# remove netbeans generated readme
rm whitematter/README.TXT

# create zip from temporary folder
zip -r release/whitematter.zip whitematter/*

# remove temp directory
rm -r whitematter

