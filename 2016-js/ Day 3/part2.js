const reader = require("../utils/reader");

const main = (data) => {
    data = data.map(entry => {
        entry = entry.split(" ");
        for (let i = 0; i < entry.length; i++) {
            if (entry[i] === "") {
                entry.splice(i, 1);
                i--;
            }
        }
        return entry.map(num => parseInt(num));
    });
    let count = 0;
    for (let i = 0; i < data.length; i += 3) {
        count += isTriangle(data[i][0], data[i+1][0], data[i+2][0]) ? 1 : 0;
        count += isTriangle(data[i][1], data[i+1][1], data[i+2][1]) ? 1 : 0;
        count += isTriangle(data[i][2], data[i+1][2], data[i+2][2]) ? 1 : 0;
    }
    console.log(count);
}

const isTriangle = (side1, side2, side3) => {
    if (side1 + side2 <= side3) {
        return false;
    }
    if (side1 + side3 <= side2) {
        return false;
    }
    if (side2 + side3 <= side1) {
        return false;
    }
    return true;
}

reader.readFile("input", main);