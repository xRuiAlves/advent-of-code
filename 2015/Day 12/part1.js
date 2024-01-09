const reader = require("../utils/reader");

const main = (data) => {
    const json = data[0];
    const regex = /-?\d+/g
    const sum = json.match(regex).map(num => parseInt(num)).reduce((prev, curr) => prev + curr);
    console.log(sum);
}

reader.readFile("input", main);
