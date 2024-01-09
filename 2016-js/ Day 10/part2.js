const reader = require("../utils/reader");

const bots = {};
const bot_instructions = {};
const outputs = {};
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
            bot_instructions[bot_num].low = entry[5] === "bot" ? `b${entry[6]}` : `o${entry[6]}`;
            bot_instructions[bot_num].high = entry[10] === "bot" ? `b${entry[11]}` : `o${entry[11]}`;
        }
    });
    
    while (!outputs[0] || !outputs[1] || !outputs[2]) {
        Object.keys(bots).forEach(bot_num => {
            if (bots[bot_num].v1 && bots[bot_num].v2) {
                const instruction = bot_instructions[bot_num];
                if (instruction.low) {
                    const num = parseInt(instruction.low.substring(1, instruction.low.length));
                    if (instruction.low[0] === "b") {
                        assignToBot(num, Math.min(bots[bot_num].v1, bots[bot_num].v2));
                    } else {
                        assignToOutput(num, Math.min(bots[bot_num].v1, bots[bot_num].v2));
                    }
                }
                if (instruction.high) {
                    const num = parseInt(instruction.high.substring(1, instruction.high.length));
                    if (instruction.high[0] === "b") {
                        assignToBot(num, Math.max(bots[bot_num].v1, bots[bot_num].v2));
                    } else {
                        assignToOutput(num, Math.max(bots[bot_num].v1, bots[bot_num].v2));
                    }
                }
                bots[bot_num].v1 = undefined;
                bots[bot_num].v2 = undefined;
            }
        });
    }
    const res = outputs[0] * outputs[1] * outputs[2];
    console.log(res);
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

const assignToOutput = (output_num, val) => {
    if (!outputs[output_num]) {
        outputs[output_num] = {};
    }
    outputs[output_num] = val;
}

reader.readFile("input", main);