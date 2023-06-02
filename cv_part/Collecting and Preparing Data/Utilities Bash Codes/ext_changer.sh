#!/usr/bin/sh

for f in *.$1; do mv -- "$f" "${f%.$1}.$2"; done

#$1 - extension to change from
#$2 - extension to change to
# use case !./ext_changer webp jpg 