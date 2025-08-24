
public class ScarSystem {

    private int scarSeverity = 0;
    private boolean isOvertrained = false;
    private int overtrainingThreshold = 125; 
    private int maxNenPool = 100000; 
    private int currentNenPool;
    private double nenRegenRate = 1.0;

    public void trackOvertraining(int nenPoolUsed, int naturalLimit) {
        int percentOverLimit = (nenPoolUsed * 100) / naturalLimit;
        if (percentOverLimit > overtrainingThreshold) {
            isOvertrained = true;
            updateScarSeverity(percentOverLimit);
        }
    }

    private void updateScarSeverity(int percentOverLimit) {
        if (percentOverLimit <= 150) {
            scarSeverity = 1;
        } else if (percentOverLimit <= 175) {
            scarSeverity = 2;
        } else {
            scarSeverity = 3;
        }
    }

    public void applyScarPenalties() {
        switch (scarSeverity) {
            case 1: 
                nenRegenRate = 0.9;
                break;
            case 2:
                nenRegenRate = 0.5;
                break;
            case 3:
                nenRegenRate = 0.25;
                break;
            default:
                nenRegenRate = 1.0;
                break;
        }
    }

    public void handleConcealmentPenalty() {
        if (scarSeverity >= 1) {
            System.out.println("Aura leaking due to scars!");
        }
    }

    public void recoverFromScars() {
        if (isOvertrained && scarSeverity > 0) {
            scarSeverity -= 1;
            if (scarSeverity < 0) scarSeverity = 0;
        }
    }
}
