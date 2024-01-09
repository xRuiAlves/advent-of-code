const reader = require("../utils/reader");

const race_time = 2503;
const main = (data) => {
    const reindeer = [];
    data.forEach(entry => {
        entry = entry.split(" ");
        const rein = {};
        rein.speed = parseInt(entry[3]);
        rein.uptime = parseInt(entry[6]);
        rein.downtime = parseInt(entry[13]);
        rein.getPos = (t) => {
            const num_complete_bursts = Math.floor(t/(rein.uptime + rein.downtime));
            t = t % (rein.uptime + rein.downtime);
            return rein.speed*(num_complete_bursts*rein.uptime + Math.min(rein.uptime, t));
        }
        reindeer.push(rein);
    });
    let best_rein_distance = 0;
    reindeer.forEach(rein => {
        best_rein_distance = Math.max(best_rein_distance, rein.getPos(race_time));
    });
    console.log(best_rein_distance);
}

reader.readFile("input", main);
