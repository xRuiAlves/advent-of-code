const reader = require("../utils/reader");
const md5 = require("md5");

const stretched_hashes = {};

const main = (data) => {
    const input = data[0];
    let count = 0;
    let key_count = 0;

    while(key_count < 64) {
        if (isKey(input, count)) {
            key_count++;
        }
        count++;
    }

    console.log(count - 1);
}

const stretched_hash = (str) => {
    if (stretched_hashes[str]) {
        return stretched_hashes[str];
    }
    const key = str;

    for (let i = 0; i <= 2016; i++) {
        str = md5(str);
    }

    stretched_hashes[key] = str;
    return str;
}

const get3 = (str) => {
    for (let i = 0; i < str.length - 2; i++) {
        if (str[i] === str[i+1] && str[i] === str[i+2]) {
            return str[i];
        }
    }
    return false;
}

const has5 = (str, char) => {
    for (let i = 0; i < str.length - 4; i++) {
        if (char === str[i] && char === str[i+1] && char === str[i+2] && char === str[i+3] && char === str[i+4]) {
            return true;
        }
    }
    return false;
}

const isKey = (input, count) => {
    const char = get3(stretched_hash(input + count));
    if (char) {
        for (let i = 1; i <= 1000; i++) {
            if (has5(stretched_hash(input + (count + i)), char)) {
                return true;
            }
        }
    }
    return false;
}

reader.readFile("input", main);