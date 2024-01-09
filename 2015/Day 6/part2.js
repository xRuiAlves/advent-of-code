const reader = require("../utils/reader");

const m_size = 1000;
const main = (data) => {
    const m = Array(m_size).fill().map(()=>Array(m_size).fill(0));

    data.forEach(instruction => {
        const tokens = instruction.split(" ");
        if (tokens[0] === "toggle") {
            let cell1 = tokens[1].split(",").map(num => parseInt(num));
            let cell2 = tokens[3].split(",").map(num => parseInt(num));
            bright(m, cell1, cell2, 2);
        } else {
            let cell1 = tokens[2].split(",").map(num => parseInt(num));
            let cell2 = tokens[4].split(",").map(num => parseInt(num));
            bright(m, cell1, cell2, tokens[1] === "on" ? 1 : -1);
        }
    });
    const brightness = countBrightness(m);
    console.log(brightness)
}

const bright = (m, cell1, cell2, value) => {
    for (let i = cell1[0]; i <= cell2[0]; i++) {
        for (let j = cell1[1]; j <= cell2[1]; j++) {
            m[i][j] = Math.max(0, m[i][j] + value);
        }
    }
}

const countBrightness = (m) => {
    let brightness = 0;
    m.forEach(row => {
        row.forEach(val => {
            brightness += val;
        });
    });
    return brightness;
}

reader.readFile("input", main);