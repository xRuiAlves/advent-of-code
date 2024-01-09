const reader = require("../utils/reader");

const multiplier = 252533;
const divider = 33554393;
const start = 20151125;
const main = (data) => {
    data = data[0].split(" ");
    const row = parseInt(data[data.length-3]);
    const col = parseInt(data[data.length-1]);
    let x = 1;
    let y = 1;
    let curr = start;
    while (true) {
        if (y === 1) {
            y = x+y;
            x = 1;
        } else {
            y--;
            x++;
        }
        curr = (curr*multiplier) % divider;
        if (x === col && y === row) {
            console.log(curr);
            return;
        }
    }
}

reader.readFile("input", main);