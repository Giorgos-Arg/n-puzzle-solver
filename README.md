# n-puzzle solver

This java program solves the n-puzzle problem using the A* algorithm and a choice of four different heuristics.

## Description

n-puzzle is a sliding tiles puzzle represented by an n×n grid where each cell is an integer in [1, n*n].
There is also a special tile which is empty and is represented by the number 0.

The goal is to rearrange the tiles to a desired configuration (i.e. in Ascending order but with the empty tile placed on
the right bottom of the grid).

The goal is achieved by swapping the empty tile with another tile in all possible directions (i.e. up, down, left, right).

Some configurations are impossible to solve.

## Usage

```bash
java NPuzzleSolver <heuristic> <filePath>
```

## Heuristic options:

| options | heuristic                            |
| ------- | ------------------------------------ |
| -m      | Manhattan distance                   |
| -mt     | Number of misplaced tiles            |
| -rc     | Number of tiles out of row or column |
| -lc     | Linear conflict                      |

## Input file example

```bash
4
1  2  4  12
5  6  3  0
9  10 8  7
13 14 11 15
```

## Output Example

```bash
java NPuzzleSolver -m data/4x4/puzzle4x4-10.txt

Initial board:
+-----+-----+-----+-----+
|  1  |  2  |  4  |  12 |
+-----+-----+-----+-----+
|  5  |  6  |  3  |  0  |
+-----+-----+-----+-----+
|  9  |  10 |  8  |  7  |
+-----+-----+-----+-----+
|  13 |  14 |  11 |  15 |
+-----+-----+-----+-----+

1. move blank: UP
+-----+-----+-----+-----+
|  1  |  2  |  4  |  0  |
+-----+-----+-----+-----+
|  5  |  6  |  3  |  12 |
+-----+-----+-----+-----+
|  9  |  10 |  8  |  7  |
+-----+-----+-----+-----+
|  13 |  14 |  11 |  15 |
+-----+-----+-----+-----+

2. move blank: LEFT
+-----+-----+-----+-----+
|  1  |  2  |  0  |  4  |
+-----+-----+-----+-----+
|  5  |  6  |  3  |  12 |
+-----+-----+-----+-----+
|  9  |  10 |  8  |  7  |
+-----+-----+-----+-----+
|  13 |  14 |  11 |  15 |
+-----+-----+-----+-----+

3. move blank: DOWN
+-----+-----+-----+-----+
|  1  |  2  |  3  |  4  |
+-----+-----+-----+-----+
|  5  |  6  |  0  |  12 |
+-----+-----+-----+-----+
|  9  |  10 |  8  |  7  |
+-----+-----+-----+-----+
|  13 |  14 |  11 |  15 |
+-----+-----+-----+-----+

4. move blank: DOWN
+-----+-----+-----+-----+
|  1  |  2  |  3  |  4  |
+-----+-----+-----+-----+
|  5  |  6  |  8  |  12 |
+-----+-----+-----+-----+
|  9  |  10 |  0  |  7  |
+-----+-----+-----+-----+
|  13 |  14 |  11 |  15 |
+-----+-----+-----+-----+

5. move blank: RIGHT
+-----+-----+-----+-----+
|  1  |  2  |  3  |  4  |
+-----+-----+-----+-----+
|  5  |  6  |  8  |  12 |
+-----+-----+-----+-----+
|  9  |  10 |  7  |  0  |
+-----+-----+-----+-----+
|  13 |  14 |  11 |  15 |
+-----+-----+-----+-----+

6. move blank: UP
+-----+-----+-----+-----+
|  1  |  2  |  3  |  4  |
+-----+-----+-----+-----+
|  5  |  6  |  8  |  0  |
+-----+-----+-----+-----+
|  9  |  10 |  7  |  12 |
+-----+-----+-----+-----+
|  13 |  14 |  11 |  15 |
+-----+-----+-----+-----+

7. move blank: LEFT
+-----+-----+-----+-----+
|  1  |  2  |  3  |  4  |
+-----+-----+-----+-----+
|  5  |  6  |  0  |  8  |
+-----+-----+-----+-----+
|  9  |  10 |  7  |  12 |
+-----+-----+-----+-----+
|  13 |  14 |  11 |  15 |
+-----+-----+-----+-----+

8. move blank: DOWN
+-----+-----+-----+-----+
|  1  |  2  |  3  |  4  |
+-----+-----+-----+-----+
|  5  |  6  |  7  |  8  |
+-----+-----+-----+-----+
|  9  |  10 |  0  |  12 |
+-----+-----+-----+-----+
|  13 |  14 |  11 |  15 |
+-----+-----+-----+-----+

9. move blank: DOWN
+-----+-----+-----+-----+
|  1  |  2  |  3  |  4  |
+-----+-----+-----+-----+
|  5  |  6  |  7  |  8  |
+-----+-----+-----+-----+
|  9  |  10 |  11 |  12 |
+-----+-----+-----+-----+
|  13 |  14 |  0  |  15 |
+-----+-----+-----+-----+

10. move blank: RIGHT
+-----+-----+-----+-----+
|  1  |  2  |  3  |  4  |
+-----+-----+-----+-----+
|  5  |  6  |  7  |  8  |
+-----+-----+-----+-----+
|  9  |  10 |  11 |  12 |
+-----+-----+-----+-----+
|  13 |  14 |  15 |  0  |
+-----+-----+-----+-----+

Puzzle solved successfully!

Number of states in the search space: 153

Total solution time: 0.004 seconds
```

## Author

Giorgos Argyrides (g.aryrides@outlook)
