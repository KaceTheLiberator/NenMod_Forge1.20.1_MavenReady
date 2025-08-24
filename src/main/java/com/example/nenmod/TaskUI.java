public class TaskUI {
    private static final String PROGRESS_PREFIX = "§7[Progress] ";

    // Update the progress bar on the action bar
    public static void updateProgressBar(Player player) {
        int runDistanceCompleted = PlayerTaskManager.runDistance.getOrDefault(player, 0);
        int sitUpsCompleted = PlayerTaskManager.sitUps.getOrDefault(player, 0);
        int pushUpsCompleted = PlayerTaskManager.pushUps.getOrDefault(player, 0);
        int squatsCompleted = PlayerTaskManager.squats.getOrDefault(player, 0);

        // Build progress strings with animation
        String runProgress = buildProgressBar(runDistanceCompleted, 1000);
        String sitUpProgress = buildProgressBar(sitUpsCompleted, 100);
        String pushUpProgress = buildProgressBar(pushUpsCompleted, 100);
        String squatProgress = buildProgressBar(squatsCompleted, 100);

        // Show progress on action bar
        player.sendActionBar(PROGRESS_PREFIX + "Run: " + runProgress + " Sit-ups: " + sitUpProgress + " Push-ups: " + pushUpProgress + " Squats: " + squatProgress);
    }

    // Build a progress bar string with animation
    private static String buildProgressBar(int completed, int total) {
        int percent = (int) ((float) completed / total * 100);
        int progress = (percent / 10);  // Divide by 10 to create chunks of 10%

        // Create an animated progress bar with changing blocks
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (i < progress) {
                bar.append("■");
            } else {
                bar.append("□");
            }
        }
        return bar.toString();
    }
}
