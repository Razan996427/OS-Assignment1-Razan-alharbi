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
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
}

// Class representing a process that implements Runnable to be run by a thread
class Process implements Runnable {
    private String name;
    private int burstTime;
    private int timeQuantum;
    private int remainingTime;

    // 1 Feature Add process priority attribute
    private int priority;

    public Process(String name, int burstTime, int timeQuantum, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.timeQuantum = timeQuantum;
        this.remainingTime = burstTime;

        // 1 Feature Add process priority attribute
        this.priority = priority;
    }

    @Override
    public void run() {
        int runTime = Math.min(timeQuantum, remainingTime);

        System.out.println("▶ " + name + " executing [" + runTime + "ms]");

        try {
            Thread.sleep(runTime);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        remainingTime -= runTime;

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
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    public String getName() { return name; }
    public int getBurstTime() { return burstTime; }
    public int getRemainingTime() { return remainingTime; }

    // 1 Feature Add process priority attribute
    public int getPriority() { return priority; }

    public boolean isFinished() {
        return remainingTime <= 0;
    }
}

public class SchedulerSimulation {

    // 2 Feature Add context switch counter
    static int contextSwitches = 0;

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

            // 1 Feature Add process priority attribute
            int priority = 1 + random.nextInt(5);

            Process process = new Process("P" + i, burstTime, timeQuantum, priority);

            addProcessToQueue(process, processQueue, processMap);
        }

        while (!processQueue.isEmpty()) {

            Thread currentThread = processQueue.poll();

            // 2 Feature Count context switch
            contextSwitches++;

            currentThread.start();

            try {
                currentThread.join();
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted.");
            }

            Process process = processMap.get(currentThread);

            if (!process.isFinished()) {
                if (!processQueue.isEmpty()) {
                    addProcessToQueue(process, processQueue, processMap);
                } else {
                    process.runToCompletion();
                }
            }
        }

        // 2 Feature Display total context switches
        System.out.println("\nTotal context switches: " + contextSwitches);

        System.out.println("\nALL PROCESSES COMPLETED\n");
    }

    public static void addProcessToQueue(Process process, Queue<Thread> processQueue,
                                        Map<Thread, Process> processMap) {

        Thread thread = new Thread(process);
        processQueue.add(thread);
        processMap.put(thread, process);

        // 1 Feature Show priority in ready queue
        System.out.println("➕ " + process.getName() +
                " (Priority: " + process.getPriority() + ")" +
                " added to ready queue | Burst: " + process.getBurstTime());
    }
}