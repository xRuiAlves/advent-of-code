const reader = require("../utils/reader");

const spell_costs = [53, 73, 113, 173, 229];
const spell2_max_num_turns = 6;
const spell3_max_num_turns = 6;
const spell4_max_num_turns = 5;

const main = (data) => {
    data = data.map(entry => entry.split(" "));
    const boss = {
        hp: parseInt(data[0][2]),
        damage: parseInt(data[1][1])
    }

    const state = {
        hp: 50,
        mana: 500,
        spent_mana: 0,
        spell_2_turns: 0,
        spell_3_turns: 0,
        spell_4_turns: 0,
        boss_hp: boss.hp,
        boss_damage: boss.damage
    }
    const to_visit = [state];
    const win_states = [];

    while (win_states.length < 10 && to_visit.length > 0) {
        const curr = to_visit.shift();
        if (curr.win === "player") {
            win_states.push(curr.spent_mana);
            continue;
        }
        const neighbors = getNeighbors(curr);
        if (neighbors.win && neighbors.win === "boss") {
            continue;
        } else if (neighbors.win && neighbors.win === "player") {
            win_states.push(neighbors.spent_mana);
            continue;
        }
        neighbors.forEach(neighbor => {
            to_visit.push(neighbor);
        })
    }

    const least_mana_spent = win_states.reduce((prev, curr) => prev < curr ? prev : curr);
    console.log(least_mana_spent);
}

const getNeighbors = (state) => {
    const state_copy = {...state};
    applyEffects(state_copy);
    if (state_copy.boss_hp <= 0) {
        return {
            win: "player",
            spent_mana: state_copy.spent_mana
        }
    }

    if (state_copy.mana < spell_costs[0]) {
        return {win: "boss"};
    } 

    const neighbors = [];
    if (state_copy.mana >= spell_costs[0]) {
        const new_neighbor = {...state_copy};
        new_neighbor.spent_mana += spell_costs[0];
        new_neighbor.mana -= spell_costs[0];
        new_neighbor.boss_hp -= 4;
        if (new_neighbor.boss_hp <= 0) {
            neighbors.push({
                win: "player",
                spent_mana: new_neighbor.spent_mana
            });
        } else {
            const armor_boost = applyEffects(new_neighbor);
            if (new_neighbor.boss_hp <= 0) {
                neighbors.push({
                    win: "player",
                    spent_mana: new_neighbor.spent_mana
                });
            } else {
                new_neighbor.hp -= Math.max(1, new_neighbor.boss_damage - armor_boost);
                if (!isDead(new_neighbor)) {
                    neighbors.push(new_neighbor);
                }
            }
        }
    }
    if (state_copy.mana >= spell_costs[1]) {
        const new_neighbor = {...state_copy};
        new_neighbor.spent_mana += spell_costs[1];
        new_neighbor.mana -= spell_costs[1];
        new_neighbor.boss_hp -= 2;
        new_neighbor.hp += 2;
        if (new_neighbor.boss_hp <= 0) {
            neighbors.push({
                win: "player",
                spent_mana: new_neighbor.spent_mana
            });
        } else {
            const armor_boost = applyEffects(new_neighbor);
            if (new_neighbor.boss_hp <= 0) {
                neighbors.push({
                    win: "player",
                    spent_mana: new_neighbor.spent_mana
                });
            } else {
                new_neighbor.hp -= Math.max(1, new_neighbor.boss_damage - armor_boost);
                if (!isDead(new_neighbor)) {
                    neighbors.push(new_neighbor);
                }
            }
        }
    }
    if (state_copy.spell_2_turns <= 0 && state_copy.mana >= spell_costs[2]) {
        const new_neighbor = {...state_copy};
        new_neighbor.spent_mana += spell_costs[2];
        new_neighbor.mana -= spell_costs[2];
        new_neighbor.spell_2_turns = spell2_max_num_turns;
        const armor_boost = applyEffects(new_neighbor);
        if (new_neighbor.boss_hp <= 0) {
            neighbors.push({
                win: "player",
                spent_mana: new_neighbor.spent_mana
            });
        } else {
            new_neighbor.hp -= Math.max(1, new_neighbor.boss_damage - armor_boost);
            if (!isDead(new_neighbor)) {
                neighbors.push(new_neighbor);
            }
        }
    }
    if (state_copy.spell_3_turns <= 0 && state_copy.mana >= spell_costs[3]) {
        const new_neighbor = {...state_copy};
        new_neighbor.spent_mana += spell_costs[3];
        new_neighbor.mana -= spell_costs[3];
        new_neighbor.spell_3_turns = spell3_max_num_turns;
        const armor_boost = applyEffects(new_neighbor);
        if (new_neighbor.boss_hp <= 0) {
            neighbors.push({
                win: "player",
                spent_mana: new_neighbor.spent_mana
            });
        } else {
            new_neighbor.hp -= Math.max(1, new_neighbor.boss_damage - armor_boost);
            if (!isDead(new_neighbor)) {
                neighbors.push(new_neighbor);
            }
        }
    }
    if (state_copy.spell_4_turns <= 0 && state_copy.mana >= spell_costs[4]) {
        const new_neighbor = {...state_copy};
        new_neighbor.spent_mana += spell_costs[4];
        new_neighbor.mana -= spell_costs[4];
        new_neighbor.spell_4_turns = spell4_max_num_turns;
        const armor_boost = applyEffects(new_neighbor);
        if (new_neighbor.boss_hp <= 0) {
            neighbors.push({
                win: "player",
                spent_mana: new_neighbor.spent_mana
            });
        } else {
            new_neighbor.hp -= Math.max(1, new_neighbor.boss_damage - armor_boost);
            if (!isDead(new_neighbor)) {
                neighbors.push(new_neighbor);
            }
        }
    }
    return neighbors;
}

const applyEffects = (state) => {
    let armor_boost = 0;
    if (state.spell_2_turns > 0) {
        state.spell_2_turns--;
        armor_boost = 7
    }
    if (state.spell_3_turns > 0) {
        state.spell_3_turns--;
        state.boss_hp -= 3;
    }
    if (state.spell_4_turns > 0) {
        state.spell_4_turns--;
        state.mana += 101;
    }
    return armor_boost;
}

const isDead = (char) => char.hp <= 0;

reader.readFile("input", main);

