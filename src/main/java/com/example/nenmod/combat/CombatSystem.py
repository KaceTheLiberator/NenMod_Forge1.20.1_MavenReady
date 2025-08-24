
import random
from .environment import Environment
from .status import StatusEffects
from .Hakoware import Hakoware  # Import the Hakoware

class CombatSystem:
    def __init__(self, player):
        self.player = player
        self.opponent_hp = 100
        self.combat_round = 1
        self.environment = Environment()  # Create environmental conditions
        self.hakoware = Hakoware(self.player)  # Initialize Hakoware instance

    def player_attack(self, ability_name, damage, unarmed_damage=0, is_combo=False, status_effect=None):
        """Simulate the player's attack with a Hatsu ability and calculate damage."""
        print(f"Round {self.combat_round} - Player's Turn:")
        if ability_name in self.player.abilities_unlocked:
            print(f"Player uses {ability_name} with damage: {damage}")
            if is_combo:
                print("Combo successful! Bonus XP awarded.")
            self.opponent_hp -= damage
            print(f"Opponent's HP: {self.opponent_hp}")
            self.player.gain_xp(damage // 2, is_combo, self.environment.get_weather_bonus(), status_effect.apply_effect().get("burn_damage", 0))
        else:
            print(f"Ability {ability_name} is locked.")
            self.player.gain_xp(10)  # XP for trying an ability (even if locked)

    def opponent_attack(self, damage):
        """Simulate the opponent's counterattack."""
        print(f"Opponent attacks with {damage} damage.")
        self.player.xp -= damage // 3  # Punish player with XP loss for being hit
        print(f"Player's XP: {self.player.xp}")

    def use_hakoware(self):
        """Allow player to use Hakoware during combat."""
        if self.hakoware.get_energy() >= 10:  # Check if enough energy is available
            print("Using Hakoware during combat!")
            self.hakoware.use()
        else:
            print("Not enough energy for Hakoware!")

    def combat(self):
        """Run the combat round with combo mechanics and Hakoware integration."""
        while self.opponent_hp > 0:
            # Player's attack (example: using a combo)
            self.player_attack("Unarmed Damage Affects Hatsu Damage 1", 30, unarmed_damage=5, is_combo=True, status_effect=StatusEffects())

            # Optionally use Hakoware if needed
            self.use_hakoware()

            if self.opponent_hp <= 0:
                print("Opponent defeated!")
                break

            # Opponent's attack
            self.opponent_attack(random.randint(10, 20))
            self.combat_round += 1
