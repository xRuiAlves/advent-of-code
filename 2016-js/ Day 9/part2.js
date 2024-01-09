const reader = require("../utils/reader");

const main = (data) => {
    const input = data[0];
    const len = doStuff(input, 0, input.length-1, 1);
    console.log(len);
}

const doStuff = (str, start, end, num_reps) => {
    let len = 0;
    for (let i = start; i <= end; i++) {
        if (str[i] === "(") {
            const closing_index = str.indexOf(")", i);
            const [num_chars, num_reps] = str.substring(i+1, closing_index).split("x").map(num => parseInt(num));
            len += doStuff(str, closing_index+1, closing_index+num_chars, num_reps);
            i += num_chars + (closing_index-i);
        } else {
            len += 1;
        }
    }
    return len * num_reps;
}


reader.readFile("input", main);