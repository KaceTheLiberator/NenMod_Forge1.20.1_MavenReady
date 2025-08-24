import random

class StatusEffects:
    def __init__(self):
        self.status = random.choice([None, "burn", "freeze", "stun", "poison", "shock"])

    def apply_effect(self):
        """Apply debuff to opponent."""
        if self.status == "burn":
            return {"burn_damage": 5}  # Burn damage over time
        elif self.status == "freeze":
            return {"freeze_penalty": 0.5}  # Reduced damage or speed
        elif self.status == "stun":
            return {"stun": True}  # Temporarily disable the opponent
        elif self.status == "poison":
            return {"poison_damage": 3}  # Poison damage over time
        elif self.status == "shock":
            return {"shock_penalty": 0.4}  # Reduced ability usage or movement speed
        return {}
