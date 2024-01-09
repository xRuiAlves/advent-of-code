const reader = require("../utils/reader");

const main = (data) => {
    const msg_len = data[0].length;
    const msg = [];
    for (let i = 0; i < msg_len; i++) {
        const counts = {};
        data.forEach(entry => {
            const char = entry[i];
            if (!counts[char]) {
                counts[char] = 0;
            }
            counts[char]++;
        });
        let min;
        let min_count = Infinity;
        Object.keys(counts).forEach(k => {
            if (counts[k] < min_count) {
                min = k;
                min_count = counts[k];
            }
        });
        msg.push(min);
    }
    console.log(msg.join(""));
}

reader.readFile("input", main);