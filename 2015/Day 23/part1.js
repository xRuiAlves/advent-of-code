const reader = require("../utils/reader");

const registers = {
    a: 0,
    b: 0
}
let sp = 0;
const main = (data) => {
    const instructions = data.map(entry => entry.split(" "));
    while (sp < instructions.length) {
        const instruction = instructions[sp];
        if (instruction[0] === "hlf") {
            hlf(instruction[1]);
        } else if (instruction[0] === "tpl") {
            tpl(instruction[1]);
        } else if (instruction[0] === "inc") {
            inc(instruction[1]);
        } else if (instruction[0] === "jmp") {
            jmp(parseInt(instruction[1]));
        } else if (instruction[0] === "jie") {
            jie(instruction[1].substr(0, 1), parseInt(instruction[2]));
        } else if (instruction[0] === "jio") {
            jio(instruction[1].substr(0, 1), parseInt(instruction[2]));
        }
        sp++;
    }
    console.log(registers.b);
}

const hlf = (r) => {
    registers[r] = Math.floor(registers[r]/2);
}

const tpl = (r) => {
    registers[r] *= 3;
}

const inc = (r) => {
    registers[r]++;
}

const jmp = (offset) => {
    sp += offset - 1;
}

const jie = (r, offset) => {
    if (registers[r] % 2 === 0) {
        sp += offset - 1;
    }
}

const jio = (r, offset) => {
    if (registers[r] === 1) {
        sp += offset - 1;
    }
}

reader.readFile("input", main);

