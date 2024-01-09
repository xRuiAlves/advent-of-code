const reader = require("../utils/reader");

let registers = {
    a: 0,
    b: 0,
    c: 0,
    d: 0
}
let sp = 0;

const main = (data) => {
    const instructions = data.map(entry => entry.split(" "));

    while (sp < instructions.length) {
        const instruction = instructions[sp];
        switch (instruction[0]) {
        case "cpy":
            cpy(instruction[1], instruction[2]);
            break;
        case "inc":
            inc(instruction[1]);
            break;
        case "dec":
            dec(instruction[1]);
            break;
        case "jnz":
            jnz(instruction[1], instruction[2]);
            break;
        }

        sp++;
    }

    console.log(registers.a);
}

const inc = (identifier) => {
    putValue(identifier, getValue(identifier) + 1);
}

const dec = (identifier) => {
    putValue(identifier, getValue(identifier) - 1);
}

const cpy = (source, destiny) => {
    putValue(destiny, getValue(source));
}

const jnz = (identifier, jump) => {
    if (getValue(identifier) != 0) {
        sp += jump - 1;
    }
}

const getValue = (identifier) => {
    if (isNaN(identifier)) {
        return registers[identifier];
    }
    return parseInt(identifier);
}

const putValue = (identifier, value) => {
    if (isNaN(identifier)) {
        registers[identifier] = value;
    }
}

reader.readFile("input", main);