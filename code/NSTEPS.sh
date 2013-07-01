#!/bin/bash

# http://www.spoj.com/problems/NSTEPS/
# will not pass due to time constraints

read testCases
cont=0
while [  $cont -lt $testCases ]
do
    read input
    input=($input)
    x=${input[0]}
    y=${input[1]}

    answer='No Number'
    # check is correct diagonal
    if [ $x -eq $y -o $x -eq $(($y + 2)) ]
    then
        xRem=$(( $x % 2 ))
        yRem=$(( $y % 2 ))
        if [ $xRem -eq 0 -a $yRem -eq 0 ]
        then
            let answer=x+y
        else
            let answer=x+y-1
        fi
    fi

    echo "$answer"

    let cont=cont+1
done
