const reader = require("../utils/reader");

let x = 0;
let y = 0;
let dir = "N";
const turn = {
    "R": {
        "N": "E",
        "E": "S",
        "S": "W",
        "W": "N"
    },
    "L": {
        "N": "W",
        "W": "S",
        "S": "E",
        "E": "N"
    }
};

const main = (data) => {
    const path = data[0].split(", ");
    path.forEach(step => {
        walk(step[0], parseInt(step.substring(1, step.length)));
    });
    console.log(Math.abs(x) + Math.abs(y));
}

const walk = (turn_dir, distance) => {
    dir = turn[turn_dir][dir];
    switch(dir) {
        case "N":
            y += distance;
            break;
        case "S":
            y -= distance;
            break;
        case "E":
            x += distance;
            break;
        case "W":
            x -= distance;
            break;
    }
}

reader.readFile("input", main);