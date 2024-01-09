const reader = require("../utils/reader");
const md5 = require('md5');

const main = (data) => {
    const room_id = data[0];
    const pass = new Array(8).fill("_")

    let i = 0;
    let num_chars_filled = 0;
    while(num_chars_filled < 8) {
        const hash = md5(room_id + i);
        if (hash.substr(0,5) === "00000" && isValidNumber(hash[5])) {
            const index = parseInt(hash[5]);
            if (pass[index] === "_") {
                pass[index] = hash[6];
                num_chars_filled++;
            }
        }
        i++;
    }
    console.log(pass.join(""));

}

const isValidNumber = (char) => {
    let val = char.charCodeAt(0) - "0".charCodeAt(0);
    return val >= 0 && val <= 7;
}

reader.readFile("input", main);