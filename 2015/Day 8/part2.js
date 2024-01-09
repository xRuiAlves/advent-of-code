const reader = require("../utils/reader");

const main = (data) => {
    let res = 0;
    let total_size = 0;
    data.forEach(string => {
        let string_count = 2;
        total_size += string.length;
        for (let c of string) {
            if (c === "\\" || c === "\"") {
                string_count++;
            }
            string_count++;
        }
        res += string_count;
    });
    console.log(res - total_size);
}

reader.readFile("input", main);