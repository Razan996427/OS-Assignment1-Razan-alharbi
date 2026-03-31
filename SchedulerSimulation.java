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

    // 1 Feature Add process priority
    private int priority;

    // 3 Feature Track waiting time
    private long creationTime;
    private long lastExecutionTime;
    private long waitingTime;

    public Process(String name, int burstTime, int timeQuantum, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.timeQuantum = timeQuantum;
        this.remainingTime = burstTime;

        // 1 Feature
        this.priority = priority;

        // 3 Feature
        this.creationTime = System.currentTimeMillis();
        this.lastExecutionTime = creationTime;
        this.waitingTime = 0;
    }

    @Override
    public void run() {

        // 3 Feature Calculate waiting time
        long now = System.currentTimeMillis();
        waitingTime += (now - lastExecutionTime);

        int runTime = Math.min(timeQuantum, remainingTime);

        System.out.println("▶ " + name + " executing [" + runTime + "ms]");

        try {
            Thread.sleep(runTime);
        } catch (InterruptedException e) {}

        remainingTime -= runTime;

        // 3 Feature update last execution time
        lastExecutionTime = System.currentTimeMillis();

        if (remainingTime > 0) {
            System.out.println("↻ " + name + " not finished");
        } else {
            System.out.println("✓ " + name + " finished");
        }
    }

    public void runToCompletion() {
        try {
            Thread.sleep(remainingTime);
            remainingTime = 0;
            System.out.println("✓ " + name + " finished (last process)");
        } catch (InterruptedException e) {}
    }

    public String getName() { return name; }
    public int getBurstTime() { return burstTime; }
    public int getRemainingTime() { return remainingTime; }

    // 1 Feature
    public int getPriority() { return priority; }

    // 3 Feature
    public long getWaitingTime() { return waitingTime; }

    public boolean isFinished() {
        return remainingTime <= 0;
    }
}

public class SchedulerSimulation {

    // 2 Feature Count context switches
    static int contextSwitches = 0;

    public static void main(String[] args) {

        int studentID = 445052071;
        Random random = new Random(studentID);

        int timeQuantum = 2000 + random.nextInt(4) * 1000;
        int numProcesses = 10 + random.nextInt(11);

        Queue<Thread> processQueue = new LinkedList<>();
        Map<Thread, Process> processMap = new HashMap<>();

        // 3 Feature Store unique processes
        Map<String, Process> uniqueProcesses = new HashMap<>();

        System.out.println("\n===== CPU SCHEDULER SIMULATION =====\n");

        for (int i = 1; i <= numProcesses; i++) {

            int burstTime = timeQuantum/2 + random.nextInt(2 * timeQuantum + 1);

            // 1 Feature Generate priority
            int priority = 1 + random.nextInt(5);

            Process process = new Process("P" + i, burstTime, timeQuantum, priority);

            uniqueProcesses.put(process.getName(), process);

            addProcessToQueue(process, processQueue, processMap);
        }

        while (!processQueue.isEmpty()) {

            Thread currentThread = processQueue.poll();

            // 2 Feature increment context switch
            contextSwitches++;

            currentThread.start();

            try {
                currentThread.join();
            } catch (InterruptedException e) {}

            Process process = processMap.get(currentThread);

            if (!process.isFinished()) {
                if (!processQueue.isEmpty()) {
                    addProcessToQueue(process, processQueue, processMap);
                } else {
                    process.runToCompletion();
                }
            }
        }

        // 2 Feature print context switches
        System.out.println("\nTotal context switches: " + contextSwitches);

        // 3 Feature Display summary
        System.out.println("\nProcess Summary:");
        System.out.println("Name\tBurst\tWaiting Time");

        for (Process p : uniqueProcesses.values()) {
            System.out.println(p.getName() + "\t" +
                               p.getBurstTime() + "\t" +
                               p.getWaitingTime() + " ms");
        }

        System.out.println("\nALL PROCESSES COMPLETED\n");
    }

    public static void addProcessToQueue(Process process, Queue<Thread> processQueue,
                                         Map<Thread, Process> processMap) {

        Thread thread = new Thread(process);
        processQueue.add(thread);
        processMap.put(thread, process);

        // 1 Feature show priority
        System.out.println("➕ " + process.getName() +
                " (Priority: " + process.getPriority() + ")" +
                " added to ready queue | Burst: " + process.getBurstTime());
    }
}