const reader = require("../utils/reader");

const main = (data) => {
    let res = 0;
    let total_size = 0;
    data.forEach(string => {
        let string_count = 0;
        total_size += string.length;
        for (let i = 1; i < string.length - 1; i++) {
            if (string[i] === "\\") {
                if (string[i+1] === "x") {
                    i += 3;
                } else {
                    i+= 1;
                }
            }
            string_count++;
        }
        res += string_count;
    });
    console.log(total_size - res);
}

reader.readFile("input", main);