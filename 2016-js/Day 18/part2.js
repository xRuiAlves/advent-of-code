const reader = require("../utils/reader");

const NUM_ROWS = 400000;
const TRAP = "^";
const SAFE = ".";

const TRAPS = Object.freeze({
    "^^.": true,
    ".^^": true,
    "^..": true,
    "..^": true
});

const nextRow = (row) => {
    row = `.${row}.`;
    const new_row = [];

    for (let i = 1; i < row.length - 1; i++) {
        new_row.push(TRAPS[row.substr(i - 1, 3)] ? TRAP : SAFE);
    }

    return new_row.join("");
}

const countSafe = (rows) => (
    rows.reduce((acc, curr) => (
        acc + curr.split("").filter(cell => cell === SAFE).length
    ), 0)
)

const main = (data) => {
    const rows = data;
    
    while (rows.length < NUM_ROWS) {
        rows.push(nextRow(rows[rows.length - 1]));
    }

    console.log(countSafe(rows));
}

reader.readFile("input", main);