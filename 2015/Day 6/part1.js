const reader = require("../utils/reader");

const m_size = 1000;
const main = (data) => {
    const m = Array(m_size).fill().map(()=>Array(m_size).fill(0));

    data.forEach(instruction => {
        const tokens = instruction.split(" ");
        if (tokens[0] === "toggle") {
            let cell1 = tokens[1].split(",").map(num => parseInt(num));
            let cell2 = tokens[3].split(",").map(num => parseInt(num));
            applyToggle(m, cell1, cell2);
        } else {
            let cell1 = tokens[2].split(",").map(num => parseInt(num));
            let cell2 = tokens[4].split(",").map(num => parseInt(num));
            flip(m, cell1, cell2, tokens[1] === "on" ? 1 : 0);
        }
    });
    const num_lit = countLit(m);
    console.log(num_lit)
}

const applyToggle = (m, cell1, cell2) => {
    for (let i = cell1[0]; i <= cell2[0]; i++) {
        for (let j = cell1[1]; j <= cell2[1]; j++) {
            m[i][j] ^= 1;
        }
    }
}

const flip = (m, cell1, cell2, value) => {
    for (let i = cell1[0]; i <= cell2[0]; i++) {
        for (let j = cell1[1]; j <= cell2[1]; j++) {
            m[i][j] = value;
        }
    }
}

const countLit = (m) => {
    let count = 0;
    m.forEach(row => {
        row.forEach(val => {
            count += val;
        });
    });
    return count;
}

reader.readFile("input", main);