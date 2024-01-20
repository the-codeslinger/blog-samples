#!/bin/bash
set -e

eval "$(jq -r '@sh "FOO=\(.foo) BAZ=\(.baz)"')"
FOOBAZ="$FOO $BAZ"
jq -n --arg foobaz "$FOOBAZ" '{"foobaz":$foobaz}'