const reader = require("../utils/reader");
const md5 = require("md5");

const hashes = {};

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

const get_hash = (str) => {
    if (hashes[str]) {
        return hashes[str];
    } else {
        const hash = md5(str);
        hashes[str] = hash;
        return hash;
    }
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
    const char = get3(get_hash(input + count));
    if (char) {
        for (let i = 1; i <= 1000; i++) {
            if (has5(get_hash(input + (count + i)), char)) {
                return true;
            }
        }
    }
    return false;
}

reader.readFile("input", main);