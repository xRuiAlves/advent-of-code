const reader = require("../utils/reader");

const main = (data) => {
    const path = data[0];
    const santas = [
        {x: 0, y: 0},
        {x: 0, y: 0}
    ]
    let santa_index = 0;
    const visited_houses = {};
    visited_houses[0] = new Set();
    visited_houses[0].add(0);
    for (direction of path) {
        if (direction === ">") {
            santas[santa_index].x++;
        } else if (direction === "<") {
            santas[santa_index].x--;
        } else if (direction === "^") {
            santas[santa_index].y++;
        } else if (direction === "v") {
            santas[santa_index].y--;
        }
        if (!visited_houses[santas[santa_index].y]) {
            visited_houses[santas[santa_index].y] = new Set();
        }   
        visited_houses[santas[santa_index].y].add(santas[santa_index].x);
        santa_index = (santa_index + 1)%2;
    }
    
    let num_visited_houses = 0;
    Object.values(visited_houses).forEach(row => {
        num_visited_houses += row.size;
    });
    console.log(num_visited_houses)
}

reader.readFile("input", main);

