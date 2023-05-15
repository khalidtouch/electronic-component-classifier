#!/usr/bin/sh

set -e

dir=$1
name=$2

ls -1 "$dir" >names
seq -w `ls -1 "$dir" | wc -l` >numbers
ls -1 "$dir" | sed -E 's/(.+)\.(.+)$/\2/g' >exts

paste names numbers exts -d '~' | sed -E 's/(.+)~(.+)~(.+)/mv "DIR\/\1" "DIR\/NAME_\2.\3"; echo Renamed \1/g' | sed "s/NAME/$name/g; s/DIR/$dir/g;" | sh

# use case !./rename.sh dir name_of_interest