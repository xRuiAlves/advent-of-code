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
        let max;
        let max_count = 0;
        Object.keys(counts).forEach(k => {
            if (counts[k] > max_count) {
                max = k;
                max_count = counts[k];
            }
        });
        msg.push(max);
    }
    console.log(msg.join(""));
}

reader.readFile("input", main);