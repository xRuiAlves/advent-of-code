const reader = require("../utils/reader");

let x = 0;
let y = 2;

const pad = [
    ["_", "_", "1", "_", "_"],
    ["_", "2", "3", "4", "_"],
    ["5", "6", "7", "8", "9"],
    ["_", "A", "B", "C", "_"],
    ["_", "_", "D", "_", "_"]
];

const main = (data) => {
    const code = [];
    data.forEach(entry => {
        for (let instruction of entry) {
            switch(instruction) {
                case "U":
                    if (y > 0 && pad[y-1][x] !== "_") {
                        y--;
                    }
                    break;
                case "D":
                    if (y < 4 && pad[y+1][x] !== "_") {
                        y++;
                    }
                    break;
                case "R":
                    if (x < 4 && pad[y][x+1] !== "_") {
                        x++;
                    }
                    break;
                case "L":
                    if (x > 0 && pad[y][x-1] !== "_") {
                        x--;
                    }
                    break;
            }
        };
        code.push(pad[y][x]);
    });
    console.log(code.join(""));
}

reader.readFile("input", main);