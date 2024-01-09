const reader = require("../utils/reader");

const values = {};
const main = (data) => {
    data.forEach(entry => {
        entry = entry.split(" ");
        values[entry[entry.length-1]] = {
            exp: entry.splice(0, entry.length - 2),
            value: undefined
        }
    });
    const a_val = eval("a");
    Object.keys(values).forEach(entry => {
        values[entry].value = 0;
    });
    values["b"].value = a_val;
    const result = eval("a");
    console.log(result);
}

const eval = (exp) => {
    let node;
    if (!isNaN(exp)) {
        return parseInt(exp);
    } else if (values[exp].value) {
        return values[exp].value;
    } else {
        node = values[exp];
    }
    
    if (node.exp.length === 1) {
        values[exp].value  = eval(node.exp[0]);
        return values[exp].value;
    }
    if (node.exp[0] === "NOT") {
        values[exp].value  = 65536 + ~eval(node.exp[1]);
        return values[exp].value;
    }
    if (node.exp[1] === "AND") {
        values[exp].value  = (eval(node.exp[0]) & eval(node.exp[2]));
        return values[exp].value;
    }
    if (node.exp[1] === "OR") {
        values[exp].value  = (eval(node.exp[0]) | eval(node.exp[2]));
        return values[exp].value;
    }
    if (node.exp[1] === "LSHIFT") {
        values[exp].value = (eval(node.exp[0]) << eval(node.exp[2]));
        return values[exp].value;
    }
    if (node.exp[1] === "RSHIFT") {
        values[exp].value = (eval(node.exp[0]) >>> eval(node.exp[2]));
        return values[exp].value;
    }
}

reader.readFile("input", main);