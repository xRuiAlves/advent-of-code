const reader = require("../utils/reader");

const rooms = [];

const main = (data) => {
    data.forEach(entry => {
        const raw_room = entry.split("-");
        const last = raw_room.pop();
        const data = raw_room.join("");
        const id = parseInt(last);
        const checksum = last.substring(4, last.length-1);
        rooms.push({
            id, 
            checksum,
            data
        });
    });
    let sum = 0;
    rooms.forEach(room => {
        const counts = {};
        for (let char of room.data) {
            if (!counts[char]) {
                counts[char] = 0;
            }
            counts[char]++;
        }
        let good = true;
        for (let char of room.checksum) {
            const char_count = counts[char];
            if (!char_count) {
                good = false;
                break;
            }
            delete counts[char];
            for (let key of Object.keys(counts)) {
                if (counts[key] > char_count) {
                    good = false;
                    break;
                } else if (counts[key] === char_count && key.charCodeAt(0) < char.charCodeAt(0)) {
                    good = false;
                    break;
                }
            }
            if (!good) {
                break;
            }
        }
        if (good) {
            sum += room.id;
        }
    });
    console.log(sum);
}

reader.readFile("input", main);