package service;

/**
 * Background auto-save service demonstrating Java Multithreading.
 * 
 * OOP & Java Concepts Demonstrated:
 * 1. Multithreading: Implements Runnable and runs as an independent Thread.
 * 2. Daemon Thread: Set as daemon so it does not block application exit.
 * 3. Encapsulation: Thread lifecycle controlled cleanly via start() and stop().
 * 4. Exception Handling: Catches InterruptedException when sleeping.
 */
public class AutoSaveService implements Runnable {
    private final AuthService authService;
    private final FeedbackService feedbackService;
    private final int intervalSeconds;
    private volatile boolean running = false;
    private Thread workerThread;

    public AutoSaveService(AuthService authService, FeedbackService feedbackService, int intervalSeconds) {
        this.authService = authService;
        this.feedbackService = feedbackService;
        this.intervalSeconds = intervalSeconds;
    }

    /**
     * Starts the auto-save background daemon thread.
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            workerThread = new Thread(this, "AutoSave-Worker");
            // Daemon thread automatically shuts down when the main program terminates
            workerThread.setDaemon(true);
            workerThread.start();
        }
    }

    /**
     * Stops the auto-save background thread.
     */
    public synchronized void stop() {
        running = false;
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(intervalSeconds * 1000L);
                if (running) {
                    authService.saveUsers();
                    feedbackService.saveFeedback();
                    // System.out.println("\n[AutoSave: Background flush completed]"); // Keep console quiet during user input
                }
            } catch (InterruptedException e) {
                // Thread interrupted on shutdown; exit cleanly
                break;
            } catch (Exception e) {
                System.err.println("[AutoSave] Error during background save: " + e.getMessage());
            }
        }
    }
}

