#!/bin/bash

DIR=$(dirname $0)
CMD="$DIR/jna-getting-started.sh"
CLASS="de.mobanisto.TestJNA"

exec "$CMD" "$CLASS" "$@"
