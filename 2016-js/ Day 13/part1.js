const reader = require("../utils/reader");

let fav_num;
const map = [];
const bound = 50;
const main = (data) => {
    fav_num = parseInt(data[0]);
    const destiny = {x: 31, y: 39};

    for (let x = 0; x < bound; x++) {
        const row = [];
        for (let y = 0; y < bound; y++) {
            row.push(isOpen(x, y));
        }
        map.push(row);
    }
    
    const state = {
        x: 1,
        y: 1,
        depth: 0
    }

    const visited = {};
    const to_visit = [state];

    while (to_visit.length !== 0) {
        const state = to_visit.shift();
        if (state.x === destiny.x && state.y === destiny.y) {
            console.log(state.depth);
            process.exit();
        }

        if (!visited[state.x]) {
            visited[state.x] = {};
        }
        if (visited[state.x][state.y]) {
            continue;
        }
        visited[state.x][state.y] = true;

        neighbors = getNeighbors(state.x, state.y);
        neighbors.forEach(neighbor => {
            to_visit.push({
                x: neighbor.x,
                y: neighbor.y,
                depth: state.depth + 1
            });
        });
    }
}

const isOpen = (x, y) => {
    const val = (x*x + 3*x + 2*x*y + y + y*y) + fav_num;
    const binary = val.toString(2);
    let num_bits_1 = 0;
    for (let digit of binary) {
        num_bits_1 += digit === "1" ? 1 : 0;
    }
    return (num_bits_1 % 2 === 0);
}

const getNeighbors = (x, y) => {
    const neighbors = [];
    if (y < (bound-1) && map[x][y+1]) {
        neighbors.push({x: x, y: y+1});
    }
    if (y > 0 && map[x][y-1]) {
        neighbors.push({x: x, y: y-1});
    }
    if (x < (bound-1) && map[x+1][y]) {
        neighbors.push({x: x+1, y: y});
    }
    if (x > 0 && map[x-1][y]) {
        neighbors.push({x: x-1, y: y});
    }
    return neighbors;
}

reader.readFile("input", main);