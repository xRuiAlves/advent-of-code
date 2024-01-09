const reader = require("../utils/reader");

const main = (data) => {
    const path = data[0];
    let x = 0;
    let y = 0;
    const visited_houses = {};
    visited_houses[y] = new Set();
    visited_houses[y].add(x);
    for (direction of path) {
        if (direction === ">") {
            x++;
        } else if (direction === "<") {
            x--;
        } else if (direction === "^") {
            y++;
        } else if (direction === "v") {
            y--;
        }
        if (!visited_houses[y]) {
            visited_houses[y] = new Set();
        }   
        visited_houses[y].add(x);
    }
    
    let num_visited_houses = 0;
    Object.values(visited_houses).forEach(row => {
        num_visited_houses += row.size;
    });
    console.log(num_visited_houses)
}

reader.readFile("input", main);

