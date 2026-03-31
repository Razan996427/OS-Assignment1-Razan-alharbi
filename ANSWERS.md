# Assignment Questions

## Instructions
Answer all 4 questions with detailed explanations. Each answer should be **3-5 sentences minimum** and demonstrate your understanding of the concepts.

---

## Question 1: Thread vs Process

**Question**: Explain the difference between a **thread** and a **process**. Why did we use threads in this assignment instead of creating separate processes?

**Your Answer:A process is a stand-alone program that runs independently and has its own resources, memory, and system state. A thread, on the other hand, is a smaller unit of execution that is part of a process and shares resources and memory with other threads. Memory sharing is one important distinction: threads share memory, which speeds up communication between threads, whereas processes have their own memory areas. Another distinction is creation overhead: threads are lighter and quicker to generate, whereas processes are more costly to develop and maintain.
Because threads enable us to effectively imitate several processes within a single Java program, we employed them in this assignment rather than processes. For instance, every process in the `SchedulerSimulation.java` is enclosed in a `Thread` object.**

[Write your answer here. Consider: What is a process? What is a thread? How do they differ in terms of memory, resources, creation overhead? Why are threads more suitable for this simulation?]

---

## Question 2: Ready Queue Behavior

**Question**: In Round-Robin scheduling, what happens when a process doesn't finish within its time quantum? Explain using an example from your program output.

**Your Answer:A process in Round-Robin scheduling is pushed back to the end of the ready queue if it does not complete within its time quantum. This ensures that all programs are treated fairly by allowing other processes to receive CPU time. The procedure will wait for its next turn before continuing with the remaining time.**

[Write your answer here. Describe the specific behavior - where does the process go? When does it run again? Give an example from your actual program output showing a process that was re-queued.]

Example from my output:
 P1 executing [4000ms]
 P1 not finished
 P1 (Priority: 2) added to ready queue | Burst: 7286

**Explanation of example:**
[Explain what's happening in the output snippet you pasted]
In this instance, process P1 began running and utilized its entire 4000 ms time quantum. It couldn't finish in a single quantum because its total burst time was 7286 ms. As a result, it was added back to the ready queue with a "not finished" mark. By doing this, P1 is prevented from controlling the CPU and other processes are given an opportunity to run. This is crucial for CPU scheduling to be responsive and equitable, particularly in time-sharing systems.
---

## Question 3: Thread States

**Question**: A thread can be in different states: **New**, **Runnable**, **Running**, **Waiting**, **Terminated**. Walk through these states for one process (P1) from your simulation.

**Your Answer:**
New:
P1 is in the New state when the thread is first created using Thread thread = new Thread(process);.
At this stage, the thread has been initialized but has not started execution yet.
Runnable:
After creation, P1 is added to the ready queue, which means it becomes Runnable.
In this state, it is ready to run but waiting for the CPU. This can be seen in the output:
 P1 (Priority: 2) added to ready queue | Burst: 7286
Running:
P1 moves to the Running state when the scheduler selects it and starts execution using currentThread.start(). 
This is shown in the output:
 P1 executing [4000ms]
Here, P1 is actually using the CPU for its time quantum.
Waiting:
While running, P1 enters a waiting state when Thread.sleep() is used inside the run method to simulate execution time. 
Also, the main thread waits using join() until P1 finishes its quantum.
If P1 does not finish within its time quantum, it is placed back in the ready queue and waits again for its next turn:
 P1 not finished
 P1 (Priority: 2) added to ready queue | Burst: 7286
Terminated:
P1 enters the Terminated state when it completes execution. This happens when its remaining time becomes zero, as shown in the output:
P1 executing [3286ms]
 P1 finished
At this stage, the thread is completely done and will not run again.


---

## Question 4: Real-World Applications

**Question**: Give **TWO** real-world examples where Round-Robin scheduling with threads would be useful. Explain why this scheduling algorithm works well for those scenarios.

**Your Answer:**

### Example 1:: Web Server

**Description**: Multiple client requests can be handled concurrently by a web server, with each request being handled by a different thread.

**Why Round-Robin works well here**: Every request is given an equal amount of CPU time thanks to round-robin scheduling. It increases responsiveness for all users by preventing any one request from controlling the system. This is comparable to our simulation, in which every process is given a set amount of time and is re-queued if it is not completed.

### Example 2:Operating System Task Scheduling

**Description**: A browser, media player, and background processes are just a few of the programs that an operating system can run concurrently.


**Why Round-Robin works well here**: By allocating a temporal slice of CPU execution to each running application, Round-Robin guarantees fairness. This maintains the system responsive and stops any program from causing the system to freeze. In our scheduler simulation, where processes execute in turn, the same idea is used.


---

## Summary

**Key concepts I understood through these questions:**
1. The memory and performance differences between threads and processes  
2. How Round-Robin scheduling uses re-queueing and time quantum to achieve fairness  
3. A thread's life cycle and state transitions

**Concepts I need to study more:**
1. Race situations and thread synchronization  
2. How to avoid deadlocks  
