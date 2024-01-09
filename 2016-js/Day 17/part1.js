const reader = require("../utils/reader");
const md5 = require("md5");

let input;

const main = (data) => {
    input = data[0];
    
    const state = {
        path: "",
        x: 0,
        y: 0
    }

    const to_visit = [state];

    while (to_visit.length !== 0) {
        const state = to_visit.shift();
        if (state.x === 3 && state.y === 3) {
            console.log(state.path);
            process.exit();
        }

        getNeighbors(state).forEach(neighbor => {
            to_visit.push(neighbor);
        });
    }
}

const getNeighbors = (state) => {
    const neighbors = [];
    const hash = md5(input + state.path);

    if (state.y > 0 && isDoorOpen(hash[0])) {
        neighbors.push({
            x: state.x,
            y: state.y - 1,
            path: state.path + "U"
        })
    }

    if (state.y < 3 && isDoorOpen(hash[1])) {
        neighbors.push({
            x: state.x,
            y: state.y + 1,
            path: state.path + "D"
        })
    }

    if (state.x > 0 && isDoorOpen(hash[2])) {
        neighbors.push({
            x: state.x - 1,
            y: state.y,
            path: state.path + "L"
        })
    }

    if (state.x < 3 && isDoorOpen(hash[3])) {
        neighbors.push({
            x: state.x + 1,
            y: state.y,
            path: state.path + "R"
        })
    }

    return neighbors;
}

const isDoorOpen = (char) => char === "b" || char === "c" || char === "d" || char === "e" || char === "f";

reader.readFile("input", main);