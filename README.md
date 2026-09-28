# Java Linear Programming Parser

A Java parser that reads a Linear Programming (LP) problem from a text file and extracts it into the standard matrix form used by LP solvers.

## What It Does

Reads a `.txt` file with an LP problem in human-readable format and outputs the matrices needed to solve it:

- **c** — coefficients of the objective function
- **A** — coefficients of the technological constraints
- **b** — right-hand side values of the constraints
- **Eqin** — type of each constraint (`-1` for `≤`, `0` for `=`, `1` for `≥`)
- **MinMax** — `-1` for minimization, `1` for maximization

## Input Format

```
min z = 2x1 + 3x2
s.t
x1 + x2 >= 4
2x1 + x2 <= 10
end
```

## Output Example

```
MinMax=-1
c=[ 2  3 ]
A=[ 1  1
    2  1 ]
b=[ 4  10 ]
Eqin=[ 1  -1 ]
```

## How to Run

```bash
# 1. Set the input file path in Main.java (LP_1.txt)
# 2. Compile and run
javac src/*.java
java -cp src Main

# Output is written to LP_2.txt
```

## Tech Stack

- Java (pure, no frameworks)
- Regex-based parsing
- File I/O
