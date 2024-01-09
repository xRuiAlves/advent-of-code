const reader = require("../utils/reader");

const reverse = (data) => (
    data.split("").map(char => char === "1" ? "0" : "1").reverse().join("")
)

const expand = (data) => (
    `${data}0${reverse(data)}`
)

const checksum = (data) => {
    const partialChecksum = (str) => {
        const res = [];
        for (let i = 0; i < str.length; i+=2) {
            res.push(str[i] === str[i+1] ? "1" : "0")
        }
        return res.join("");
    }

    let res = partialChecksum(data);
    while (res.length % 2 === 0) {
        res = checksum(res);
    }

    return res;
}

const main = (data) => {
    let str = data[0];
    const disk_length = 35651584;

    while (str.length < disk_length) {
        str = expand(str);
    }

    let cs = checksum(str.substr(0, disk_length));

    console.log(cs);
}

reader.readFile("input", main);