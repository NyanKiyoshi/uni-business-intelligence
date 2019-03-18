#!/bin/sh

export PROJECT_PATH="$(realpath $(dirname $0))"
. "${PROJECT_PATH}/build.env"

SRC_PATH="${PROJECT_PATH}/src"
LIBRARIES="lib/weka.jar:lib/annotations-17.0.0.jar"

# Change CWD to the project directory
cd $PROJECT_PATH || { echo "Failed to change dir to ${PROJECT_PATH}" 1>&2; exit 1; }

# Clean up before building
[ -d build/ ] && rm -R build
[ -d "$RELEASE_PATH" ] && rm -R "$RELEASE_PATH"

# Compile the java code to `./build` and change the CWD to that dir
echo "Building the project..." >&2
mkdir -pv build \
    && mkdir -pv "$RELEASE_PATH" \
    && javac -cp "$LIBRARIES" \
             -d build $(find $SRC_PATH -name '*.java') \
    && cd build || exit $?

echo "Copying the dependencies..." >&2
(
    IFS=:
    for f in $LIBRARIES; do
        jar xvf "../$f" || exit $?
    done
)

echo "Copying resources..." >&2
cp -v ../res/* . || exit $?

# Archive the compiled java to a jar files
echo "Compacting the compiled results..." >&2
jar cmvf "${SRC_PATH}/META-INF/MANIFEST.MF" "${RELEASE_PATH}/${PROJECT_NAME}.jar" *

