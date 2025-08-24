class PlayerSkillTree:
    def __init__(self, affinity):
        self.xp = 0
        self.affinity = affinity
        self.abilities_unlocked = []
        self.skill_tree = self.create_skill_tree()

    def create_skill_tree(self):
        """Define nodes in the skill tree for different Nen affinities."""
        skill_tree = {
            "Enhancer": {
                "HP Boost 1": 1, "Unarmed Damage 1": 1, "Armor 1": 1,
                "Unarmed Damage Affects Hatsu Damage 1": 2,
            },
            "Emitter": {
                "Ability Damage 1": 1, "Ability Speed 1": 1,
            },
        }
        return skill_tree

    def gain_xp(self, amount, is_combo=False, environment_bonus=None, status_effects=None):
        """Award XP, with bonus for combos, status effects, and environment."""
        base_xp = amount
        if is_combo:
            base_xp += 20  # Combo bonus
        if environment_bonus:
            base_xp += environment_bonus
        if status_effects:
            base_xp += 10  # XP for applying a status effect
        self.xp += base_xp
        print(f"XP gained: {base_xp}, Total XP: {self.xp}")
        for node, threshold in self.skill_tree[self.affinity].items():
            if self.xp >= threshold and node not in self.abilities_unlocked:
                self.unlock_ability(node)

    def unlock_ability(self, ability_name):
        """Unlock ability and notify the player."""
        self.abilities_unlocked.append(ability_name)
        print(f"Ability unlocked: {ability_name} in {self.affinity} branch")

    def use_hatsu(self, ability_name, damage, is_enhancer=False, unarmed_damage=0, is_combo=False, environment=None, status_effect=None):
        """Use a Hatsu ability and calculate damage, awarding XP."""
        if ability_name in self.abilities_unlocked:
            print(f"Using Hatsu ability: {ability_name}")
            if is_enhancer:
                damage += unarmed_damage  # For Enhancer, Unarmed Damage affects Hatsu damage
            print(f"Total damage dealt: {damage}")

            # Apply environment bonuses
            environment_bonus = environment.get_weather_bonus() if environment else {}
            status_effects = status_effect.apply_effect() if status_effect else {}
            
            # Award XP based on combo, environment, and status effects
            self.gain_xp(damage, is_combo, environment_bonus.get("fire_bonus", 0), status_effects.get("burn_damage", 0))
        else:
            print(f"Ability {ability_name} is locked.")
