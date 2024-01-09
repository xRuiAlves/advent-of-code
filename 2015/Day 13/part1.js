const reader = require("../utils/reader");
const combinatorics = require('js-combinatorics');

const relationships = {};
const main = (data) => {
    const people = new Set();
    data.forEach(entry => {
        entry = entry.split(" ");
        const p1 = entry[0];
        const p2 = entry[entry.length-1].substring(0, entry[entry.length-1].length-1);
        const happiness = parseInt(entry[3]) * (entry[2] === "gain" ? 1 : -1);
        people.add(p1);
        if (!relationships[p1]) {
            relationships[p1] = {};
        }
        relationships[p1][p2] = happiness;
    });
    const tables = combinatorics.permutation(Array.from(people)).toArray();

    let best_table_happiness = -Infinity;
    tables.forEach(table => {
        best_table_happiness = Math.max(best_table_happiness, evalTable(table));
    });
    console.log(best_table_happiness);
}

const evalTable = (table) => {
    let value = 0;
    for (let i = 0; i < table.length; i++) {
        const p1 = table[i];
        const p2 = table[(i+1)%table.length];
        value += relationships[p1][p2];
        value += relationships[p2][p1];
    }
    return value;
}

reader.readFile("input", main);
