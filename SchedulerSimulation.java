import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

// ANSI Color Codes for enhanced terminal output
class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";
}

// Class representing a process
class Process implements Runnable {
    private String name;
    private int burstTime;
    private int timeQuantum;
    private int remainingTime;

    // 1 Feature Introduce priority attribute for processes
    private int priority;

    // 2 Feature Track waiting time for each process
    private long arrivalTime;
    private long waitingTime;

    public Process(String name, int burstTime, int timeQuantum, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.timeQuantum = timeQuantum;
        this.remainingTime = burstTime;

        // 1 Feature
        this.priority = priority;

        // 2 Feature
        this.arrivalTime = System.currentTimeMillis();
        this.waitingTime = 0;
    }

    @Override
    public void run() {
        int runTime = Math.min(timeQuantum, remainingTime);

        System.out.println("▶ " + name + " executing [" + runTime + "ms]");

        try {
            Thread.sleep(runTime);
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted");
        }

        remainingTime -= runTime;

        System.out.println("Remaining time: " + remainingTime);

        // 2 Feature
        System.out.println("Waiting time so far: " + waitingTime + "ms");

        if (remainingTime <= 0) {
            System.out.println(name + " finished execution!");
        }
    }

    public void runToCompletion() {
        try {
            Thread.sleep(remainingTime);
            remainingTime = 0;

            System.out.println(name + " finished execution!");

            // 2 Feature
            System.out.println("Total waiting time: " + waitingTime + "ms");

        } catch (InterruptedException e) {
            System.out.println(name + " interrupted");
        }
    }

    public String getName() { return name; }
    public int getBurstTime() { return burstTime; }
    public int getRemainingTime() { return remainingTime; }

    // 1 Feature
    public int getPriority() { return priority; }

    public boolean isFinished() {
        return remainingTime <= 0;
    }

    // 2 Feature
    public void setArrivalTime(long time) {
        this.arrivalTime = time;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void addWaitingTime(long time) {
        this.waitingTime += time;
    }

    public long getWaitingTime() {
        return waitingTime;
    }
}

public class SchedulerSimulation {
    public static void main(String[] args) {

        int studentID = 445052071;
        Random random = new Random(studentID);

        int timeQuantum = 2000 + random.nextInt(4) * 1000;
        int numProcesses = 10 + random.nextInt(11);

        Queue<Thread> processQueue = new LinkedList<>();
        Map<Thread, Process> processMap = new HashMap<>();

        System.out.println("\n===== CPU SCHEDULER SIMULATION =====\n");

        for (int i = 1; i <= numProcesses; i++) {

            int burstTime = timeQuantum/2 + random.nextInt(2 * timeQuantum + 1);

            // 1 Feature
            int priority = 1 + random.nextInt(5);

            Process process = new Process("P" + i, burstTime, timeQuantum, priority);

            addProcessToQueue(process, processQueue, processMap);
        }

        while (!processQueue.isEmpty()) {

            Thread currentThread = processQueue.poll();

            // 2 Feature
            Process process = processMap.get(currentThread);
            long currentTime = System.currentTimeMillis();
            long wait = currentTime - process.getArrivalTime();
            process.addWaitingTime(wait);

            currentThread.start();

            try {
                currentThread.join();
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted.");
            }

            if (!process.isFinished()) {
                if (!processQueue.isEmpty()) {
                    addProcessToQueue(process, processQueue, processMap);
                } else {
                    process.runToCompletion();
                }
            }
        }

        System.out.println("\nALL PROCESSES COMPLETED\n");
    }

    public static void addProcessToQueue(Process process, Queue<Thread> processQueue, 
                                        Map<Thread, Process> processMap) {

        Thread thread = new Thread(process);

        // 2 Feature
        process.setArrivalTime(System.currentTimeMillis());

        processQueue.add(thread);
        processMap.put(thread, process);

        // 1 Feature
        System.out.println("➕ " + process.getName() + 
                           " (Priority: " + process.getPriority() + ")" +
                           " added to ready queue | Burst: " + process.getBurstTime());
    }
}