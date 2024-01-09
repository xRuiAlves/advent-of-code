const reader = require("../utils/reader");
const combinatorics = require('js-combinatorics');

const nodes = {};
const main = (data) => {
    let cities = new Set();
    data.map(entry => entry.split(" ")).forEach(flight_info => {
        const origin = flight_info[0];
        const destiny = flight_info[2];
        const cost = parseInt(flight_info[4]);
        cities.add(origin);
        cities.add(destiny);
        if (!nodes[origin]) {
            nodes[origin] = {};
        }
        if (!nodes[destiny]) {
            nodes[destiny] = {};
        }
        nodes[origin][destiny] = cost;
        nodes[destiny][origin] = cost;
    });
    const paths = combinatorics.permutation(Array.from(cities)).toArray();
    let max_distance = 0;
    paths.forEach(path => {
        max_distance = Math.max(max_distance, pathCost(path));
    })
    console.log(max_distance);
}

const pathCost = (path) => {
    let cost = 0;
    for (let i = 0; i < path.length - 1; i++) {
        const origin = path[i];
        const destiny = path[i+1];
        cost += nodes[origin][destiny];
    }
    return cost;
}

reader.readFile("input", main);