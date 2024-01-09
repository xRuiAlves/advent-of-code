const reader = require("../utils/reader");

let sum;
const regex = /-?\d+/g;
const main = (data) => {
    const json = data[0];
    sum = json.match(regex).map(num => parseInt(num)).reduce((prev, curr) => prev + curr);
    const json_object = JSON.parse(json);
    eval(json_object);
    console.log(sum);
}

const eval = (exp) => {
    if (Array.isArray(exp)) {
        exp.forEach(entry => {
            eval(entry);
        });
    } else if (typeof exp === "object" && Object.values(exp).includes("red")) {
        const matches = JSON.stringify(exp).match(regex);
        if (matches) {
            sum -= matches.map(num => parseInt(num)).reduce((prev, curr) => prev + curr);
        }
    } else if (typeof exp === "object") {
        Object.values(exp).forEach(entry => {
            eval(entry);
        });
    }
}

reader.readFile("input", main);
