# Assignment 4. Patient Zero

**Student ID:** 251496@astanait.edu.kz
**Name:** Medina Maratova  
**Group:** SE-2519

## Program output

The following results were generated using my personal data with `java -cp out Main`:

```text
Mutations: 75
Shared core: 919
Marker hits: 6
First hit: 1686
Border length: 3
Shared words: 353
Tests needed: 28
Cheapest testing: 118
Longest chain: 27
```

All three example datasets (tiny, small, and dense) passed the automated check using `java tools/Check.java`. All five missions also agreed with the lab console on my personal data.

### Outbreak report

![Patient Zero lab console](output/outbreak.png)

## Questions

### Mission 1 — Edit Distance

The cell `d[i][j]` represents the minimum number of operations needed to transform the first `i` letters of strain A into the first `j` letters of strain B. If the last letters match, I use the diagonal cell without adding a cost; otherwise, I add one to the minimum of the deletion, insertion, and replacement options. The first row and column represent transforming an empty string.

### Mission 2 — Longest Common Subsequence

A longest common subsequence contains letters in the same order, but they do not need to be next to each other. A longest common substring requires all its letters to be consecutive. My Shared core is 919 because the LCS can match letters at different positions, including when insertions or deletions have shifted their positions.

### Mission 3 — Prefix Function and KMP

The prefix function stores the length of the longest proper prefix that is also a suffix for each prefix of the marker. KMP uses this information after a mismatch to avoid checking previously processed text characters again. My Border length is **3**, meaning the longest proper prefix of my marker that is also its suffix contains three letters.

### Mission 4 — Rabin–Karp

Two different strings can have the same hash value, which is called a hash collision. Therefore, after finding equal hashes, I compare the actual letters to confirm the match. If many windows have the same hash, the algorithm performs more character comparisons and can become much slower, reaching quadratic time in the worst case for fixed window length.

### Mission 5 — Dynamic Programming on a Tree

For each person, I calculate two states: the minimum testing cost when the person is tested and when the person is not tested. If the person is tested, each child can be tested or skipped; if the person is not tested, all children must be tested to cover the infection links. A greedy strategy based only on the number of links may fail because it ignores testing costs and the effect of decisions on other people.

## AI use

I used **ChatGPT (OpenAI)** to understand the assignment requirements, explain dynamic programming and string algorithms, and assist with implementing and reviewing the Java code. I also used ChatGPT to understand the test results, debug problems, and prepare explanations for the README.

I tested the implementations using the provided automated checker and laboratory console. The final submitted code and explanations were reviewed as part of my assignment work.

## Timing (Mission 7)

The following measurements were obtained using `java -cp out Bench`. Times are in milliseconds and represent averages of three runs.

### Naive Search vs KMP

| Algorithm | Text length | Time (ms) |
|---|---:|---:|
| Naive Search | 20,000 | 8.9 |
| Naive Search | 40,000 | 13.7 |
| Naive Search | 80,000 | 27.5 |
| KMP | 20,000 | 1.5 |
| KMP | 40,000 | 0.9 |
| KMP | 80,000 | 0.3 |

### Edit Distance

| Strain length | Time (ms) |
|---|---:|
| 500 | 3.8 |
| 1,000 | 2.8 |
| 2,000 | 4.4 |
| 4,000 | 17.8 |

### Timing analysis

Naive search becomes slower as the text length increases because it may compare the same characters many times. KMP has linear theoretical complexity, although its measured times vary because of JVM optimization and measurement overhead. Edit Distance has quadratic time complexity for two equally long strings, so doubling the input length is expected to increase the computational work by approximately four times for sufficiently large inputs.

## Complexity

Let `n` be the length of a text or the first strain, `m` the length of the pattern or second strain, `k` the rolling-hash window length, and `v` the number of people.

| Algorithm | Time complexity | Space complexity | Assumptions |
|---|---|---|---|
| Edit Distance | O(nm) | O(nm) | Full DP table |
| LCS | O(nm) | O(nm) | Full DP table |
| Prefix Function and KMP | O(n + m) | O(m + h) | h is the number of matches stored |
| Rabin–Karp (Shared Words) | Expected O(n + m + nk) | O(m) | Rolling hash, fixed k = 12, average constant-time hash lookup; character verification |
| Tree DP | O(v) | O(v) | The infection graph is a tree with v−1 edges |

For Rabin–Karp, hash collisions can increase the running time to O(nmk) in the worst case because many candidate windows may require character-by-character verification.

## Before you submit

- [x] Implemented Missions 1–5.
- [x] All three example datasets passed the automated checker.
- [x] Generated the nine output values using personal data.
- [x] All five missions agreed with the lab console.
- [x] Completed timing measurements.
- [x] Filled in the algorithm explanations and complexity table.
- [x] Documented AI use.
- [ ] Confirmed `output/outbreak.png` is saved in the repository.
- [ ] Verified Git commits and pushed the project to the `main` branch.
- [ ] Confirmed the GitHub repository is public.
- [ ] Submitted the GitHub link in Moodle.