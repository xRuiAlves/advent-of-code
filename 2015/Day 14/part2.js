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
        rein.pos = 0;
        rein.score = 0;
        rein.remaining_downtime = 0;
        rein.remaining_uptime = rein.uptime;
        rein.state = "RUNNING";
        reindeer.push(rein);
    });
    
    for (let t = 0; t < race_time; t++) {
        updatePositions(reindeer);
        updateScores(reindeer);
    }
    let best_rein_score = 0;
    reindeer.forEach(rein => {
        best_rein_score = Math.max(best_rein_score, rein.score);
    });
    console.log(best_rein_score);
}

const updateScores = (reindeer) => {
    const best_pos = reindeer.reduce((prev, curr) => prev.pos > curr.pos ? prev : curr).pos;
    reindeer.forEach(rein => {
        if (rein.pos === best_pos) {
            rein.score++;
        } 
    });
}

const updatePositions = (reindeer) => {
    reindeer.forEach(rein => {
        if (rein.state === "RUNNING") {
            if (rein.remaining_uptime > 0) {
                rein.pos += rein.speed;
                rein.remaining_uptime--;
            }
            if (rein.remaining_uptime === 0) {
                rein.state = "SLEEPING";
                rein.remaining_downtime = rein.downtime;
            }
        } else if (rein.state === "SLEEPING") {
            if (rein.remaining_downtime > 0) {
                rein.remaining_downtime--;
            }
            if (rein.remaining_downtime === 0) {
                rein.state = "RUNNING";
                rein.remaining_uptime = rein.uptime;
            }
        }
    })
}

reader.readFile("input", main);
