const reader = require("../utils/reader");

const rooms = [];

const main = (data) => {
    data.forEach(entry => {
        const raw_room = entry.split("-");
        const last = raw_room.pop();
        const data = raw_room.join(" ");
        const id = parseInt(last);
        rooms.push({
            id,
            data
        });
    });

    rooms.forEach(room => {
        const parsed = [];
        for (let c of room.data) {
            if (c === " ") {
                parsed.push(" ");
            } else {
                let val = c.charCodeAt(0) - "a".charCodeAt(0);
                val = ((val + room.id) % ("z".charCodeAt(0) - "a".charCodeAt(0) + 1)) + "a".charCodeAt(0);
                parsed.push(String.fromCharCode(val));
            }
        }
        const msg = parsed.join("");
        if (msg.search("north") != -1) {
            console.log(room.id);
        }
    });
}

reader.readFile("input", main);