const reader = require("../utils/reader");

const bots = {};
const bot_instructions = {};
const main = (data) => {
    data = data.map(entry => entry.split(" "));
    data.forEach(entry => {
        if (entry[0] === "value") {
            const val = parseInt(entry[1]);
            const bot_num = parseInt(entry[entry.length-1]);
            assignToBot(bot_num, val);
        } else {
            const bot_num = parseInt(entry[1]);
            if (!bot_instructions[bot_num]) {
                bot_instructions[bot_num] = {};
            }
            bot_instructions[bot_num].low = entry[5] === "bot" ? parseInt(entry[6]) : undefined;
            bot_instructions[bot_num].high = entry[10] === "bot" ? parseInt(entry[11]) : undefined;
        }
    });
    
    while (true) {
        Object.keys(bots).forEach(bot_num => {
            if (bots[bot_num].v1 && bots[bot_num].v2) {
                assertObjective(bot_num);
                const instruction = bot_instructions[bot_num];
                if (instruction.low) {
                    assignToBot(instruction.low, Math.min(bots[bot_num].v1, bots[bot_num].v2));
                }
                if (instruction.high) {
                    assignToBot(instruction.high, Math.max(bots[bot_num].v1, bots[bot_num].v2));
                }
                bots[bot_num].v1 = undefined;
                bots[bot_num].v2 = undefined;
            }
        });
    }
}

const assignToBot = (bot_num, val) => {
    if (!bots[bot_num]) {
        bots[bot_num] = {};
    }
    if (!bots[bot_num].v1) {
        bots[bot_num].v1 = val;
    } else {
        bots[bot_num].v2 = val;
    }
}

const assertObjective = (bot_num) => {
    const low = Math.min(bots[bot_num].v1, bots[bot_num].v2);
    const high = Math.max(bots[bot_num].v1, bots[bot_num].v2);
    if (low === 17 && high === 61) {
        console.log(bot_num);
        process.exit();
    }
}

reader.readFile("input", main);