import random

class Environment:
    def __init__(self):
        self.weather = random.choice(["sunny", "rain", "night", "windy", "foggy", "stormy"])  # Adding more environmental conditions

    def get_weather_bonus(self):
        """Return buffs/nerfs based on current weather conditions."""
        print(f"Weather: {self.weather}")
        if self.weather == "rain":
            return {"water_bonus": 1.5, "fire_penalty": 0.5}
        elif self.weather == "night":
            return {"stealth_bonus": 1.2, "fire_bonus": 1.3}
        elif self.weather == "sunny":
            return {"fire_bonus": 1.2, "wind_bonus": 1.1}
        elif self.weather == "windy":
            return {"wind_bonus": 1.5, "projectile_damage": 1.2}
        elif self.weather == "foggy":
            return {"stealth_bonus": 1.5, "accuracy_penalty": 0.8}
        elif self.weather == "stormy":
            return {"electric_bonus": 1.3, "water_penalty": 0.7}
        return {}
