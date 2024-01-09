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
    data.forEach(triangle => {
        count += isTriangle(triangle) ? 1 : 0;
    });
    console.log(count);
}

const isTriangle = (triangle) => {
    if (triangle[0] + triangle[1] <= triangle[2]) {
        return false;
    }
    if (triangle[0] + triangle[2] <= triangle[1]) {
        return false;
    }
    if (triangle[1] + triangle[2] <= triangle[0]) {
        return false;
    }
    return true;
}

reader.readFile("input", main);