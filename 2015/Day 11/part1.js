const reader = require("../utils/reader");

const main = (data) => {
    let pass = data[0];
    while (!isPassValid(pass)) {
        pass = increment(pass);
    }
    console.log(pass);
}

const increment = (pass) => {
    for (let i = pass.length - 1; i >= 0; i--) {
        if (pass[i] === "z") {
            pass[i] = "a";
            pass = setCharAt(pass, i, "a");
            continue;
        } else {
            return setCharAt(pass, i, String.fromCharCode(pass.charCodeAt(i)+1));
        }
    }
}

const isPassValid = (pass) => (
    assertion1(pass) && assertion2(pass) && assertion3(pass)
);

const assertion1 = (pass) => {
    for (let i = 0 ; i < pass.length - 2; i++) {
        const val1 = pass.charCodeAt(i);
        const val2 = pass.charCodeAt(i+1);
        const val3 = pass.charCodeAt(i+2);
        if ((val2 - val1 === 1) && (val3 - val2 === 1)) {
            return true;
        }
    }
    return false;
}

const assertion2 = (pass) => {
    for (let c of pass) {
        if (c === "i" || c === "o" || c === "l") {
            return false;
        }
    }
    return true;
}

const assertion3 = (pass) => {
    let num_overlaps = 0;
    for (let i = 0 ; i < pass.length - 1; i++) {
        if (pass[i] === pass[i+1]) {
            num_overlaps++;
            i++;
        }
    }
    return num_overlaps >= 2;
}

const setCharAt = (string, i, c) => {
    return string.substr(0, i) + c + string.substr(i+1);
}

reader.readFile("input", main);
