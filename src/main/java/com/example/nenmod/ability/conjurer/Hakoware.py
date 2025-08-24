import time

class Hakoware:
    def __init__(self, player):
        self.player = player
        self.regeneration_rate = 5  # Regenerate 1 unit every 5 seconds (this can be modified)
        self.cooldown_time = 15  # Cooldown time for using the Hakoware again (in seconds)
        self.last_used = 0  # Time when Hakoware was last used
        self.max_energy = 100  # Maximum energy or items that can be regenerated
        self.energy = self.max_energy  # Current energy or items

    def use(self):
        """Use the Hakoware to regenerate energy or items."""
        current_time = time.time()
        # Check if Hakoware is off cooldown
        if current_time - self.last_used >= self.cooldown_time:
            self.last_used = current_time
            print(f"Hakoware used! Regenerating {self.regeneration_rate} units of energy/items.")
            self.regenerate_energy(self.regeneration_rate)
            self.player.gain_xp(10)  # Award XP for using Hakoware (can be adjusted)
        else:
            print(f"Hakoware is on cooldown! Time left: {int(self.cooldown_time - (current_time - self.last_used))} seconds.")

    def regenerate_energy(self, amount):
        """Regenerate energy/items automatically."""
        if self.energy + amount > self.max_energy:
            self.energy = self.max_energy  # Ensure energy doesn't exceed max limit
            print("Energy is fully regenerated.")
        else:
            self.energy += amount
            print(f"Regenerated {amount} units. Current energy: {self.energy}/{self.max_energy}.")

    def get_energy(self):
        """Return the current energy."""
        return self.energy
