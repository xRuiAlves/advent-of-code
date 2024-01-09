const reader = require("../utils/reader");
const md5 = require('md5');

const main = (data) => {
    const secret_key = data[0];
    let num = 0;
    while (true) {
        console.log(num);
        const phrase = secret_key + num;
        if(md5(phrase).substring(0,6) === "000000") {
            console.log(num);
            return;
        }
        num++;
    }
}

reader.readFile("input", main);