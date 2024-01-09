const reader = require("../utils/reader");

const values = {};
const main = (data) => {
    data.forEach(entry => {
        entry = entry.split(" ");
        values[entry[entry.length-1]] = entry.splice(0, entry.length - 2);
    });
    const result = eval("a");
    console.log(result)
}

const eval = (exp) => {
    let rhs;
    if (!isNaN(exp)) {
        return parseInt(exp);
    } else {
        rhs = values[exp];
    }
    
    if (rhs.length === 1) {
        const res = eval(rhs[0]);
        values[exp] = [res];
        return res;
    }
    if (rhs[0] === "NOT") {
        const res = 65536 + ~eval(rhs[1]);
        values[exp] = [res];
        return res;
    }
    if (rhs[1] === "AND") {
        const res = (eval(rhs[0]) & eval(rhs[2]));
        values[exp] = [res];
        return res;
    }
    if (rhs[1] === "OR") {
        const res = (eval(rhs[0]) | eval(rhs[2]));
        values[exp] = [res];
        return res;
    }
    if (rhs[1] === "LSHIFT") {
        const res = (eval(rhs[0]) << eval(rhs[2]));
        values[exp] = [res];
        return res;
    }
    if (rhs[1] === "RSHIFT") {
        const res = (eval(rhs[0]) >>> eval(rhs[2]));
        values[exp] = [res];
        return res;
    }
}

reader.readFile("input", main);