const reader = require("../utils/reader");

const main = (data) => {
    const input = data[0];
    let res = "";

    for (let i = 0; i < input.length; i++) {
        if (input[i] === "(") {
            const closing_index = input.indexOf(")", i);
            const [a, b] = input.substring(i+1, closing_index).split("x").map(num => parseInt(num));
            const to_repeat = input.substr(closing_index + 1, a);
            res += to_repeat.repeat(b);
            i = closing_index + a;
        } else {
            res += input[i];
        }
    }
    console.log(res.length);
}

const isCapitalLetter = (char) => {
    const code = char.charCodeAt(0);
    return code >= "A".charCodeAt(0) && code <= "Z".charCodeAt(0);
}


reader.readFile("input", main);