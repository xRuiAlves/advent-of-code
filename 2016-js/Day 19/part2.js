const reader = require("../utils/reader");

const main = (data) => {
    const num_elves = parseInt(data[0]);
    const log = Math.log(num_elves)/Math.log(3);
    const lower_bound = Math.pow(3, Math.floor(log));
    const higher_bound = Math.pow(3, Math.ceil(log));
    const midway = (higher_bound + lower_bound) / 2;
    if (num_elves < midway) {
        res = num_elves - lower_bound;
    } else {
        res = (midway - lower_bound) + 2*(num_elves - midway);
    }
    console.log(res);
}

reader.readFile("input", main);