<?php
/**
 * Facebook hiring sample test
 *
 * Question 1 / 1 (Hanoi Moves)
 *
 * There are K pegs. Each peg can hold discs in decreasing order of radius when looked from bottom to top of the peg.
 * There are N discs which have radius 1 to N; Given the initial configuration of the pegs and the final configuration
 * of the pegs, output the moves required to transform from the initial to final configuration. You are required to do
 * the transformations in minimal number of moves.
 *
 * A move consists of picking the topmost disc of any one of the pegs and placing it on top of any other peg.
 * At any point of time, the decreasing radius property of all the pegs must be maintained.
 *
 * Constraints:
 * 1<= N<=8
 * 3<= K<=5
 *
 * Input Format:
 * N K
 * 2nd line contains N integers.
 * Each integer in the second line is in the range 1 to K where the i-th integer denotes the peg to which disc of
 * radius i is present in the initial configuration.
 * 3rd line denotes the final configuration in a format similar to the initial configuration.
 *
 * Output Format:
 * The first line contains M - The minimal number of moves required to complete the transformation.
 * The following M lines describe a move, by a peg number to pick from and a peg number to place on.
 * If there are more than one solutions, it's sufficient to output any one of them. You can assume, there is always a
 * solution with less than 7 moves and the initial configuration will not be same as the final one.
 *
 * Sample Input #00:
 *
 * 2 3
 * 1 1
 * 2 2
 *
 * Sample Output #00:
 * 3
 * 1 3
 * 1 2
 * 3 2
 *
 * Sample Input #01:
 *
 * 6 4
 * 4 2 4 3 1 1
 * 1 1 1 1 1 1
 *
 * Sample Output #01:
 *
 * 5
 * 3 1
 * 4 3
 * 4 1
 * 2 1
 * 3 1
 *
 * NOTE: You need to write the full code taking all inputs are from stdin and outputs to stdout
 * If you are using "Java", the class name is "Solution"
 */

/**
 * Simple and short solution to the Hanoi moves problem but far from impressive performance.
 */

readInputFile($initialPegs, $finalPegs);
$result = run($initialPegs, $finalPegs);
echo sprintf("%d\n", count($result['movements']));
foreach ($result['movements'] as $movement) {
    echo sprintf("%d %d\n", key($movement), $movement[key($movement)]);
}

function transformLinePosition($discsNumber, $pegsNumber, $fp)
{
    $position =  array_map('intval', explode(' ', trim(fgets($fp))));
    $pegs = array_fill(1, $pegsNumber, array());
    for ($radius = $discsNumber - 1; $radius >= 0; --$radius) {
        $pegNumber = $position[$radius];
        $pegs[$pegNumber][count($pegs[$pegNumber])] = $radius;
    }

    return array('pegs' => $pegs, 'movements' => array());
}

function readInputFile(&$initialPegs, &$finalPegs)
{
    $fp = fopen('php://stdin', 'r');
    list($discsNumber, $pegsNumber) = fscanf($fp, "%d %d");
    $initialPegs = transformLinePosition($discsNumber, $pegsNumber, $fp);
    $finalPegs = transformLinePosition($discsNumber, $pegsNumber, $fp);
    fclose($fp);
}

function run($initialPegs, $finalPegs)
{
    // at start no visited positions and the start position is unvisited
    $visited = array();
    $unvisited[] = $initialPegs;

    // "there is always a solution with less than 7 moves"
    while (true) {
        foreach ($unvisited as $i => $position) {

            // mark as visited and remove from unvisited
            $visited[json_encode($position['pegs'])] = true;
            unset($unvisited[$i]);

            foreach ($position['pegs'] as $pegNumber => $peg) {
                if ($peg != array()) {
                    // get last position of peg and disc radius
                    $pegLastPos = count($peg) - 1;
                    $pegLast = $peg[$pegLastPos];

                    // check what pegs the disc can be moved
                    foreach ($position['pegs'] as $pegNumber2 => $peg2) {

                        // cannot be moved to itself, can be moved to any empty peg or with disc bigger than the one
                        // to be moved
                        if ($pegNumber != $pegNumber2 && ($peg2 == array() || $pegLast < $peg2[count($peg2)-1])) {

                            // create the new pegs "schema"
                            $newPegs = $position['pegs'];
                            $newPegs[$pegNumber2][count($newPegs[$pegNumber2])] = $pegLast;
                            unset($newPegs[$pegNumber][$pegLastPos]);

                            // check if that pegs "schema" was already visited
                            if (!isset($visited[json_encode($newPegs)])) {
                                // create the new position compose by pegs "schema" and movements
                                $newPosition = array(
                                    'pegs' => $newPegs,
                                    'movements' => array_merge(
                                        $position['movements'],
                                        array(array($pegNumber => $pegNumber2))
                                    )
                                );

                                // check if is final position
                                if ($newPegs == $finalPegs['pegs']) {
                                    return $newPosition;
                                }

                                $unvisited[] = $newPosition;
                            }
                        }
                    }
                }
            }
        }
    }
}
