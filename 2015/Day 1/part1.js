const reader = require("../utils/reader");

const main = (data) => {
    const instructions = data[0];
    let floor = 0;
    for (let instruction of instructions) {
        floor += (instruction === "(" ? 1 : -1);
    }
    console.log(floor);
}

reader.readFile("input", main);