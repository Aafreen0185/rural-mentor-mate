# RuralReach

A Java CLI application that connects rural entrepreneurs with mentors through a compatibility-based matching algorithm. Built as part of a Computer Science Engineering course project at Chennai Institute of Technology and Applied Research.

---

## What it does

Rural entrepreneurs often lack access to experienced guidance. RuralReach addresses this by letting entrepreneurs create a profile — specifying their industry, location, and business goals — and then runs a weighted matching algorithm against a pool of registered mentors to surface the most compatible ones, ranked by compatibility score.

---

## Features

- Entrepreneur registration with industry, location, and goal tracking
- Mentor registration with expertise areas and industry specialisations
- Compatibility scoring engine using weighted criteria (industry, location, expertise, experience)
- Ranked mentor leaderboard with detailed match reports
- Full mentor profile browser
- CSV-based persistence — no database dependency

---

## Project Structure

```
RuralReach/
├── src/
│   ├── Main.java                        Entry point and CLI menu
│   ├── model/
│   │   ├── Entrepreneur.java            Entrepreneur profile with Industry enum
│   │   ├── Mentor.java                  Mentor profile with expertise and ratings
│   │   └── MatchResult.java             Compatibility result with Comparable ranking
│   ├── service/
│   │   └── MatchingEngine.java          Weighted scoring algorithm
│   └── util/
│       ├── FileManager.java             CSV read/write for mentors and entrepreneurs
│       └── ConsoleUtils.java            Input helpers and display formatting
└── data/
    ├── mentors.csv                      Mentor records
    └── entrepreneurs.csv                Entrepreneur records (generated on first run)
```

---

## Matching Algorithm

Each mentor is scored against an entrepreneur profile using four weighted criteria:

| Criteria | Weight |
|---|---|
| Industry match | 35 points |
| Location proximity | 20 points |
| Expertise keyword overlap | 30 points |
| Years of experience | 15 points |
| Rating bonus | up to 5 points |

The scoring uses keyword overlap between the entrepreneur's stated goals and the mentor's expertise areas, rewarding mentors who have direct experience in what the entrepreneur is trying to achieve. Results are sorted using Java's `Comparable` interface on `MatchResult`.

---

## Tech Stack

- Language: Java 17+
- Persistence: CSV flat files with pipe-delimited format
- Collections: ArrayList for mentor/entrepreneur lists, sorted via Collections.sort()
- OOP Concepts: Encapsulation, Enums (Industry), Comparable interface, File I/O

---

## How to Run

```bash
# Clone the repository
git clone https://github.com/Aafreen0185/rural-mentor-mate.git
cd rural-mentor-mate

# Compile
javac -d out src/model/*.java src/service/*.java src/util/*.java src/Main.java

# Run
java -cp out Main
```

---

## Sample Output

```
  ╔══════════════════════════════════════════════╗
  ║           RURALREACH v1.0                    ║
  ║   Mentor Matching for Rural Entrepreneurs     ║
  ╚══════════════════════════════════════════════╝

  Top mentor matches for Aafreen:
  ─────────────────────────────────────────
  Rank Name                 Location        Score    Grade      Experience
  ─────────────────────────────────────────
  #1   Rajesh Kumar         Chennai         92.0%    Excellent  18y exp
  #2   Suresh Iyer          Coimbatore      74.5%    Good       15y exp
  #3   Priya Nair           Kochi           61.0%    Good       12y exp
```

---

## Author

Aafreen Sheriff M
B.E. Computer Science Engineering — Chennai Institute of Technology and Applied Research
[GitHub](https://github.com/Aafreen0185) · [LeetCode](https://leetcode.com/Aafreen_Sh18)
