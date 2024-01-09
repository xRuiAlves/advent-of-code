const reader = require("../utils/reader");
const md5 = require('md5');

const main = (data) => {
    const room_id = data[0];
    const pass = [];

    let i = 0;
    while(pass.length < 8) {
        const hash = md5(room_id + i);
        if (hash.substr(0,5) === "00000") {
            pass.push(hash[5]);
        }
        i++;
    }
    console.log(pass.join(""));

}

reader.readFile("input", main);