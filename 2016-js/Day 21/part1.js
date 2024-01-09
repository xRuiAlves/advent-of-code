const reader = require("../utils/reader");

const swapPos = (str, a, b) => {
    const temp = str[a];
    str[a] = str[b];
    str[b] = temp;
    return str;
}

const swapLetter = (str, a, b) => {
    const i1 = str.indexOf(a);
    const i2 = str.indexOf(b);
    return swapPos(str, i1, i2);
}

const reverse = (str, a, b) => [
    ...str.slice(0, a),
    ...str.slice(a, b + 1).reverse(),
    ...str.slice(b + 1),
]

const move = (str, a, b) => {
    const removed = str.splice(a, 1)[0];
    str.splice(b, 0, removed);
    return str;
}

const rotateLeft = (str, steps) => {
    steps %= str.length;
    for (let i = 0; i < steps; i++) {
        const removed = str.shift();
        str.push(removed);
    }
    return str;
}

const rotateRight = (str, steps) => {
    steps %= str.length;
    for (let i = 0; i < steps; i++) {
        const removed = str.pop();
        str.splice(0, 0, removed);
    }
    return str;
}

const rotateBased = (str, letter) => {
    const i = str.indexOf(letter);
    const steps = i + 1 + (i >= 4 ? 1 : 0);
    return rotateRight(str, steps); 
}

const main = (data) => {
    let str = "abcdefgh".split("");
    data.forEach(instruction => {
        const parts = instruction.split(" ");
        if (instruction.includes("move position")) {
            str = move(str, parseInt(parts[2]), parseInt(parts[5]));
        } else if (instruction.includes("swap position")) {
            str = swapPos(str, parseInt(parts[2]), parseInt(parts[5]));
        } else if (instruction.includes("swap letter")) {
            str = swapLetter(str, parts[2], parts[5]);
        } else if (instruction.includes("rotate left")) {
            str = rotateLeft(str, parseInt(parts[2]));
        } else if (instruction.includes("rotate right")) {
            str = rotateRight(str, parseInt(parts[2]));
        } else if (instruction.includes("rotate based")) {
            str = rotateBased(str, parts[6]);
        } else if (instruction.includes("reverse")) {
            str = reverse(str, parseInt(parts[2]), parseInt(parts[4]));
        }
    });
    console.log(str.join(""));
}

reader.readFile("input", main);