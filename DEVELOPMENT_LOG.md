# Development Log

## Instructions
Document your development process as you work on the assignment. Add entries showing:
- What you worked on
- Problems you encountered
- How you solved them
- Time spent

**Requirements**: Minimum 5 entries showing progression over time.

---

## Example Entry Format:
## Development Log

---

### Entry 1 - March 29, 2026, 8:45 PM
**What I did**: Started working on the assignment and explored the base code

**Details**:
- Opened the project in IntelliJ
- Read through SchedulerSimulation and Process classes
- Tried to understand how Round-Robin scheduling is implemented
- Ran the program for the first time

**Challenges**:
At the beginning, I was confused about how threads are actually being executed and how the queue works

**Solution**:
I traced the code step by step and focused on the run() method and how Thread.start() works

**Time spent**: 2 hour

---

### Entry 2 - March 30, 2026, 12:20 AM
**What I did**: Worked on Feature 1 (Process Priority)

**Details**:
- Added a priority variable inside the Process class
- Generated random values between 1 and 5
- Updated constructor and getter
- Modified output to display priority when adding to queue

**Challenges**:
I wasn't sure where exactly to add the priority in the constructor without breaking the code

**Solution**:
I carefully updated the constructor parameters and checked all places where Process is created

**Time spent**: 2 hours

---

### Entry 3 - March 30, 2026, 11:40 PM
**What I did**: Implemented Feature 2 (Context Switch Counter)

**Details**:
- Added static variable to count context switches
- Incremented it every time a process starts execution
- Printed total number at the end

**Challenges**:
I first added the counter in the wrong place, so the number was incorrect

**Solution**:
Moved the increment before calling start() so it counts correctly

**Time spent**: 2 hours

---

### Entry 4 - March 31, 2026, 1:50 AM
**What I did**: Started working on Feature 3 (Waiting Time)

**Details**:
- Added arrivalTime and waitingTime variables
- Used System.currentTimeMillis()
- Tried calculating waiting time for each process

**Challenges**:
This was the hardest part because I didn’t understand how to calculate waiting time correctly

**Solution**:
I reviewed the logic again and realized I should calculate total time minus burst time

**Time spent**: 2 hours

---

### Entry 5 - March 31, 2026, 11:30 PM
**What I did**: Fixed waiting time calculation and tested full program

**Details**:
- Corrected waiting time logic
- Added summary table at the end
- Tested output multiple times

**Challenges**:
The output format was messy at first

**Solution**:
Adjusted print statements to make it cleaner and readable

**Time spent**: 1 hour

---

### Entry 6 - April 1, 2026, 2:40 AM
**What I did**: Final testing and review before submission

**Details**:
- Reviewed all features (Priority, Context Switch, Waiting Time)
- Verified output matches expected behavior
- Made small improvements to formatting

**Challenges**:
Just making sure everything works together without bugs

**Solution**:
Ran multiple tests and checked values manually

**Time spent**: 45 minutes

---

## Summary

**Total time spent on assignment**: ~8 hours

**Most challenging part**: Waiting time calculation and understanding thread behavior

**Most interesting learning**: How Round-Robin scheduling works in practice using threads

**What I would do differently next time**: Start earlier and divide the work across more days
