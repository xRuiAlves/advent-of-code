const reader = require("../utils/reader");

let x = 1;
let y = 1;

const pad = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
];

const main = (data) => {
    const code = [];
    data.forEach(entry => {
        for (let instruction of entry) {
            switch(instruction) {
                case "U":
                    y = Math.max(y-1, 0);
                    break;
                case "D":
                    y = Math.min(y+1, 2);
                    break;
                case "R":
                    x = Math.min(x+1, 2);
                    break;
                case "L":
                    x = Math.max(x-1, 0);
                    break;
            }
        };
        code.push(pad[y][x]);
    });
    console.log(code.join(""));
}

reader.readFile("input", main);