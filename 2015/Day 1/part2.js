const reader = require("../utils/reader");

const main = (data) => {
    const instructions = data[0];
    let floor = 0;
    for (let i in instructions) {
        floor += (instructions[i] === "(" ? 1 : -1);
        if (floor < 0) {
            console.log(++i);
            return;
        }
    }
}

reader.readFile("input", main);