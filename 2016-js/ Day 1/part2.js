const reader = require("../utils/reader");

let x = 0;
let y = 0;
let dir = "N";
let visited = {};
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
    visited[y] = new Set();
    visited[x].add(0);
    path.forEach(step => {
        walk(step[0], parseInt(step.substring(1, step.length)));
    });
}

const walk = (turn_dir, distance) => {
    dir = turn[turn_dir][dir];
    while (distance-- > 0) {
        switch(dir) {
            case "N":
                y ++;
                break;
            case "S":
                y --;
                break;
            case "E":
                x ++;
                break;
            case "W":
                x --;
                break;
        }
        if (!visited[y]) {
            visited[y] = new Set();
        }
        if (visited[y].has(x)) {
            console.log(Math.abs(x) + Math.abs(y));
            process.exit()
        } else {
            visited[y].add(x);
        }
    }
}

reader.readFile("input", main);